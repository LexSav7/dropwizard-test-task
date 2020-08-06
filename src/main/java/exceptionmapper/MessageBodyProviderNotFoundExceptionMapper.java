package exceptionmapper;

import org.glassfish.jersey.message.internal.MessageBodyProviderNotFoundException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

//TODO Hello, Valera. This exception mapper does not with javax validation, don't know why. Left it for future.

@Provider
public class MessageBodyProviderNotFoundExceptionMapper implements ExceptionMapper<MessageBodyProviderNotFoundException> {

    @Override
    public Response toResponse(MessageBodyProviderNotFoundException exception) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("400 badly formatted")
                .type(MediaType.TEXT_PLAIN)
                .build();
    }
}