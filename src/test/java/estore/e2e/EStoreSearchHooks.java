package estore.e2e;

import java.io.IOException;

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
}
