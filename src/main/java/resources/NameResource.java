package resources;

import com.codahale.metrics.annotation.Timed;
import data.Person;
import dao.NameDAO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/v1/name")
public class NameResource {

    NameDAO nameDAO;

    public NameResource(NameDAO nameDAO) {
        this.nameDAO = nameDAO;
    }

    @PUT
    @Timed
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String saveName(@NotNull @Valid Person person) {
        nameDAO.saveName(person.getName());
        return String.format("Hello %s. Next year you will be %d.", person.getName(), person.getAge() + 1);
    }

    @GET
    @Timed
    @Produces(MediaType.TEXT_PLAIN)
    public String getNames() {
        List<String> names = nameDAO.getNames();

        if (names != null && !names.isEmpty()) {
            return "Names: " + names.toString();

        } else return "No names";
    }
}
