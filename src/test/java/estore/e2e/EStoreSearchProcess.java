package estore.e2e;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import estore.presentation.ConsoleMessages;

public class EStoreSearchProcess {
    private static final long OUTPUT_TIMEOUT_MILLIS = 30000;

    private final String artifact;
    private final List<ApplicationInteraction> interactions;
    private final Object outputLock = new Object();
    private final StringBuilder outputBuffer = new StringBuilder();
    private final Deque<String> pendingInputs = new ArrayDeque<String>();

    private Process process;
    private BufferedWriter input;
    private Thread outputReader;
    private boolean outputClosed;

    public EStoreSearchProcess(String artifact, List<ApplicationInteraction> interactions) {
        if (artifact == null || artifact.isEmpty()) {
            throw new IllegalStateException("The packaged E-Search artifact is not configured");
        }

        this.artifact = artifact;
        this.interactions = interactions;
    }

    public void start(String catalogFile) throws IOException, TimeoutException {
        if (isRunning()) {
            throw new IllegalStateException("E-Search is already running.");
        }

        String jacocoAgent = System.getProperty("estore.jacoco.agent");
        String executionDataFile = System.getProperty("estore.jacoco.destfile");
        if (jacocoAgent == null || jacocoAgent.isEmpty()
                || executionDataFile == null || executionDataFile.isEmpty()) {
            throw new IllegalStateException("JaCoCo agent and execution-data paths must be configured");
        }

        Path agentPath = Paths.get(jacocoAgent).toAbsolutePath();
        Path executionDataPath = Paths.get(executionDataFile).toAbsolutePath();
        Files.createDirectories(executionDataPath.getParent());

        ProcessBuilder processBuilder = new ProcessBuilder(
            "java",
            "-javaagent:" + agentPath + "=destfile=" + executionDataPath,
            "-jar",
            Paths.get(artifact).toAbsolutePath().toString(),
            catalogFile
        );

        processBuilder.directory(new File(catalogFile).getAbsoluteFile().getParentFile());
        processBuilder.environment().put(ConsoleMessages.TEST_MODE_ENV_VAR, "true");
        processBuilder.redirectErrorStream(true);

        process = processBuilder.start();

        input = new BufferedWriter(
            new OutputStreamWriter(
                process.getOutputStream(),
                StandardCharsets.UTF_8
            )
        );

        synchronized (outputLock) {
            outputBuffer.setLength(0);
            outputClosed = false;
        }
        
        startOutputReader();

        try {
            recordInteraction(null, waitForDelimiter());
        } catch (IOException | TimeoutException exception) {
            stop();
            throw exception;
        }
    }

    public void sendInput(String userInput) throws IOException {
        ensureRunning();

        if (userInput == null) {
            throw new IllegalArgumentException("Input must not be null.");
        }

        this.input.write(userInput);
        this.input.newLine();
        this.input.flush();
        synchronized (pendingInputs) {
            pendingInputs.addLast(userInput);
        }
    }

    public boolean hasPendingInputs() {
        synchronized (pendingInputs) {
            return !pendingInputs.isEmpty();
        }
    }

    public void capturePendingInteractions() throws IOException, TimeoutException {
        while (true) {
            String nextInput;
            synchronized (pendingInputs) {
                nextInput = pendingInputs.peekFirst();
            }
            if (nextInput == null) {
                return;
            }

            String interactionOutput = waitForDelimiter();
            synchronized (pendingInputs) {
                pendingInputs.removeFirst();
            }
            recordInteraction(nextInput, interactionOutput);
        }
    }

    public String waitForDelimiter() throws IOException, TimeoutException {
        long deadline = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(OUTPUT_TIMEOUT_MILLIS);
        String delimiter = ConsoleMessages.TEST_MODE_DELIMITER;

        synchronized (outputLock) {
            while (true) {
                int delimiterIndex = outputBuffer.indexOf(delimiter);
                if (delimiterIndex >= 0) {
                    String captured = outputBuffer.substring(0, delimiterIndex);
                    outputBuffer.delete(0, delimiterIndex + delimiter.length());
                    return captured;
                }

                if (outputClosed) {
                    throw new IOException("E-Search exited before emitting the test output delimiter.");
                }

                long remainingNanos = deadline - System.nanoTime();
                if (remainingNanos <= 0) {
                    throw new TimeoutException("Timed out after " + OUTPUT_TIMEOUT_MILLIS
                            + " ms waiting for E-Search output delimiter.");
                }

                long remainingMillis = TimeUnit.NANOSECONDS.toMillis(remainingNanos);
                int remainingNanosPart = (int) (remainingNanos
                        - TimeUnit.MILLISECONDS.toNanos(remainingMillis));
                try {
                    outputLock.wait(remainingMillis, remainingNanosPart);
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    throw new IOException("Interrupted while waiting for E-Search output.", exception);
                }
            }
        }
    }

    private void recordInteraction(String input, String output) {
        int currentSequence = this.interactions.size();
        this.interactions.add(new ApplicationInteraction(currentSequence, input, output));
    }

    public String getOutput() {
        if (interactions.isEmpty()) {
            return "";
        }
        return interactions.get(interactions.size() - 1).getOutput();
    }

    public boolean isRunning() {
        return process != null && process.isAlive();
    }

    public void stop() {
        if (process == null) {
            return;
        }

        Process stoppedProcess = process;
        closeInput();
        stoppedProcess.destroy();
        try {
            if (!stoppedProcess.waitFor(5, TimeUnit.SECONDS)) {
                stoppedProcess.destroyForcibly();
                stoppedProcess.waitFor();
            }
            if (outputReader != null) {
                outputReader.join(2000);
            }
        } catch (InterruptedException exception) {
            stoppedProcess.destroyForcibly();
            Thread.currentThread().interrupt();
        }
        process = null;
        input = null;
        outputReader = null;
    }

    private void closeInput() {
        try {
            if (input != null) {
                input.close();
            }
        } catch (IOException ignored) {
        }
    }

    private void startOutputReader() {
        Process startedProcess = process;
        outputReader = new Thread(() -> {
            try (InputStreamReader reader = new InputStreamReader(
                    startedProcess.getInputStream(), StandardCharsets.UTF_8)) {
                char[] buffer = new char[1024];
                int charactersRead;

                while ((charactersRead = reader.read(buffer)) != -1) {
                    synchronized (outputLock) {
                        outputBuffer.append(buffer, 0, charactersRead);
                        outputLock.notifyAll();
                    }
                }
            } catch (IOException ignored) {
                // Process shutdown can close the stream while the reader is active.
            } finally {
                synchronized (outputLock) {
                    outputClosed = true;
                    outputLock.notifyAll();
                }
            }
        });

        outputReader.setDaemon(true);
        outputReader.start();
    }

    private void ensureRunning() {
        if (!isRunning()) {
            throw new IllegalStateException("E-Search is not running.");
        }
    }
}
