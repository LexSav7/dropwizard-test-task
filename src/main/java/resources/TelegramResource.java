package resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.client.utils.URIBuilder;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Path("/v1/message/telegram")
//TODO make it better
public class TelegramResource {

    @PUT
    @Timed
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String sendMessage(String message) {

        //Can be done by creating object for Telegram message, just wanted to try a different approach with ObjectMapper
        String botMessage = getMessageFromJSON(message);

        URI telegramUri = buildTelegramApiUri(botMessage);
        Response response = callTelegramApi(telegramUri);

        if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
            return "Success! " + response.getStatus();
        } else {
            return "ERROR! " + response.getStatus();
        }
    }

    private Response callTelegramApi(URI uri) {
        Client client = ClientBuilder.newClient();
        WebTarget telegramResource = client.target(uri);

        return telegramResource.request().accept(MediaType.APPLICATION_JSON).get();
    }

    private URI buildTelegramApiUri(String message) {
        String botApiKey = "1100733171:AAHOt_qOoPa1Fq57kQHFvikaI7ATELavGc4";
        String command = "sendMessage";

        URIBuilder builder = null;
        URI uri = null;

        try {
            builder = new URIBuilder("https://api.telegram.org/bot" + botApiKey +"/" + command);
            builder.addParameter("chat_id", "410958457");
            builder.addParameter("text", message);
            uri = builder.build();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return uri;
    }

    private String getMessageFromJSON(String json) {
        ObjectNode node = null;

        try {
            node = new ObjectMapper().readValue(json, ObjectNode.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String message = "";
        if (node != null) message = node.get("message").asText();

        return message;
    }
}
