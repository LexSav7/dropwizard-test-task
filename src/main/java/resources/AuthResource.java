package resources;

import data.User;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.auth.Auth;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicInteger;

@RolesAllowed({"ADMIN"})
@Path("/v1/Auth")
public class AuthResource {

    private static AtomicInteger counter = new AtomicInteger();

    @GET
    @Timed
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String authenticate(@Auth User user) {
       return "Authorization success " + counter.incrementAndGet();
    }
}
