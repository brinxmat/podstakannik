package no.greenall.podstakannik.endpoint;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Responsibility: Provide resources for application.
 */
@Singleton
@Path("/")
public final class Resource {
    @GET
    @Produces("text/plain")
    public Response base(){
        return Response.ok().build();
    }
}
