package resources;

import auth.Authenticator;
import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicInteger;

@Authenticator
@Path("/v1/Auth")
public class AuthResource {

//    private AtomicInteger counter = new AtomicInteger();
    private static int COUNT = 0;

    @GET
    @Timed
    @Produces(MediaType.TEXT_PLAIN)
    public String authenticate() {
       return "Authorization success " + (++COUNT);
    }
}
