package no.greenall.podstakannik;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.sun.javafx.fxml.builder.URLBuilder;
import no.greenall.podstakannik.no.greenall.podstakannik.testUtils.PortSelector;
import org.apache.http.client.utils.URIBuilder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
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

    @AfterClass
    public static void tearDown() throws Exception {
        app.stop();
    }

    @Test
    public void it_exists() {
        assertNotNull(new App(port, "resources"));
    }

    @Test
    public void it_responds_to_get_request_on_base_uri() throws URISyntaxException, UnirestException {
        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost(LOCALHOST)
                .setPort(port)
                .setPath("/")
                .build();
        HttpResponse<String> response = Unirest.get(uri.toString()).asString();
        assertEquals(Response.Status.OK.toString(), response.getStatusText());
    }

}
