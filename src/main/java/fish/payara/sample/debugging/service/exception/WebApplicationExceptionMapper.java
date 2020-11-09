package fish.payara.sample.debugging.service.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.Provider;

/**
 * @author Matt Gill
 */
@Provider
public class WebApplicationExceptionMapper extends CustomExceptionMapper<WebApplicationException> {

    @Override
    protected ResponseBuilder buildResponse(WebApplicationException exception) {
        return Response
                .status(exception.getResponse().getStatus())
                .entity(new ServiceError(exception));
    }

}