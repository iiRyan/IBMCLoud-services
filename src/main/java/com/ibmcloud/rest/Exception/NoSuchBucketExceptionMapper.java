package com.ibmcloud.rest.Exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NoSuchBucketExceptionMapper  implements ExceptionMapper<NoSuchBucketException> {

    @Override
    public Response toResponse(NoSuchBucketException exception) {
       return Response.status(404).entity(exception.getMessage()).build();
    }
    
}
