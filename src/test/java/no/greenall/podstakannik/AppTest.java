package no.greenall.podstakannik;

import no.greenall.podstakannik.no.greenall.podstakannik.testUtils.PortSelector;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Responsibility: Test the top-level application.
 */
public class AppTest {
    private static final String LOCALHOST = "127.0.0.1";
    private static int port;
    private static String appURI;
    private static App app;

    @BeforeClass
    public static void setup() throws Exception {
        port = PortSelector.randomFree();
        appURI = LOCALHOST + ":" + port + "/";
        app = new App(port, "resources");
        app.startAsync();
    }

    @Test
    public void it_exists() {
        assertNotNull(new App(port, "resources"));
    }

}
