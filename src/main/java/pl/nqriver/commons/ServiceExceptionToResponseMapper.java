package pl.nqriver.commons;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ServiceExceptionToResponseMapper implements ExceptionMapper<ServiceException> {

    @Override
    public Response toResponse(ServiceException exception) {
        ServiceErrorResponse response = new ServiceErrorResponse(exception.getCode().getMessage(), exception.getCode().getCode());
        return Response.status(exception.getCode().getStatus()).entity(response).build();
    }


}
