package no.greenall.podstakannik.endpoint;

import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Responsibility: Test Resource.class.
 */
public class ResourceTest {

    @Test
    public void it_exists() {
        assertNotNull(new Resource());
    }

    @Test
    public void test_base() {
        Resource resource = new Resource();
        assertEquals(Response.Status.OK.getStatusCode(), resource.base().getStatus());
    }
}
