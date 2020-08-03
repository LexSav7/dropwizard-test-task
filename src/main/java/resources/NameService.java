package resources;

import com.codahale.metrics.annotation.Timed;
import data.Person;
import dummyDB.NamesDB;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/v1/name")
public class NameService {

    @PUT
    @Timed
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String saveName(Person person) {
        NamesDB.save(person.getName());
        return "Hello " + person.getName() + ". Next year you will be " + (person.getAge() + 1);
    }

    @GET
    @Timed
    @Produces(MediaType.TEXT_PLAIN)
    public String getNames() {
        List<String> names = NamesDB.getAll();

        if (names != null && !names.isEmpty()) {
            return "Names: " + names.toString();

        } else return "No names";
    }
}
