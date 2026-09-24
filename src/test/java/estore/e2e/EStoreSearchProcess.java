package estore.e2e;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class EStoreSearchProcess {

    private final String artifact;

    private Process process;
    private BufferedWriter input;
    private ByteArrayOutputStream output;
    private Thread outputReader;

    public EStoreSearchProcess(String artifact) {
        if (artifact == null || artifact.isEmpty()) {
            throw new IllegalStateException("The packaged E-Search artifact is not configured");
        }

        this.artifact = artifact;
    }

    public void start(String catalogFile) throws IOException {
        if (isRunning()) {
            throw new IllegalStateException("E-Search is already running.");
        }

        ProcessBuilder processBuilder = new ProcessBuilder(
            "java",
            "-jar",
            artifact,
            catalogFile
        );

        processBuilder.redirectErrorStream(true);

        process = processBuilder.start();

        input = new BufferedWriter(
            new OutputStreamWriter(
                process.getOutputStream(),
                StandardCharsets.UTF_8
            )
        );

        output = new ByteArrayOutputStream();

        startOutputReader();
    }

    public void sendInput(String input) throws IOException {
        ensureRunning();

        this.input.write(input);
        this.input.newLine();
        this.input.flush();
    }

    public String getOutput() {
        if (output == null) {
            return "";
        }

        return new String(
            output.toByteArray(),
            StandardCharsets.UTF_8
        );
    }

    public boolean isRunning() {
        return process != null && process.isAlive();
    }

    public void stop() {
        if (process == null) {
            return;
        }

        try {
            if (input != null) {
                input.close();
            }
        } catch (IOException ignored) {
        }

        process.destroy();

        try {
            process.waitFor();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        process = null;
        input = null;
    }

    private void startOutputReader() {
        outputReader = new Thread(() -> {
            try (InputStream stream = process.getInputStream();
                 BufferedReader reader = new BufferedReader(
                     new InputStreamReader(stream, StandardCharsets.UTF_8))) {

                char[] buffer = new char[1024];
                int charactersRead;

                while ((charactersRead = reader.read(buffer)) != -1) {
                    synchronized (output) {
                        output.write(
                            new String(buffer, 0, charactersRead)
                                .getBytes(StandardCharsets.UTF_8)
                        );
                    }
                }

            } catch (IOException ignored) {
                // Process shutdown can close the stream while the
                // reader is still running.
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
