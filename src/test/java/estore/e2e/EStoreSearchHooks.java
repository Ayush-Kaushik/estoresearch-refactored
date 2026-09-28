package estore.e2e;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;

public class EStoreSearchHooks {
    private final EStoreSearchWorld eStoreSearchWorld;

    public EStoreSearchHooks(EStoreSearchWorld eStoreSearchWorld) {
        this.eStoreSearchWorld = eStoreSearchWorld;
    }

    @Before 
    public void beforeScenario() throws IOException {
        this.eStoreSearchWorld.startScenario();
    }

    @AfterStep
    public void captureInteraction() throws IOException, TimeoutException {
        this.eStoreSearchWorld.capturePendingInteractions();
    }

    @After
    public void afterScenario() throws IOException {
        this.eStoreSearchWorld.stopScenario();
    }
}
