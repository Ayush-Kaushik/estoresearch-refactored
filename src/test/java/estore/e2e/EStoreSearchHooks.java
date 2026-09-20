package estore.e2e;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;

import java.io.IOException;

public class EStoreSearchHooks {

    private final EStoreSearchWorld world = EStoreSearchWorld.current();

    @BeforeAll
    public static void beforeAll() throws IOException {
        EStoreSearchWorld.startSuite();
    }

    @AfterAll
    public static void afterAll() throws IOException {
        EStoreSearchWorld.stopSuite();
    }

    @Before
    public void beforeScenario() throws IOException {
        world.startScenario();
    }

    @After
    public void afterScenario() throws IOException {
        world.stopScenario();
    }
}