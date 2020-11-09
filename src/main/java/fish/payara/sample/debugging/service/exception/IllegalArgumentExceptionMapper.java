package fish.payara.sample.debugging.service.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.Provider;

/**
 * @author Matt Gill
 */
@Provider
public class IllegalArgumentExceptionMapper extends CustomExceptionMapper<IllegalArgumentException> {

    @Override
    protected ResponseBuilder buildResponse(IllegalArgumentException exception) {
        return Response
                .status(404)
                .entity(new ServiceError(exception));
    }

}