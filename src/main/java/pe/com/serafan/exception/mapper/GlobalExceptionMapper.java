package pe.com.serafan.exception.mapper;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import pe.com.serafan.exception.business.EmailDuplicadoException;
import pe.com.serafan.exception.business.PersonaNoEncontradaException;
import pe.com.serafan.exception.model.ApiError;

import java.time.LocalDateTime;

@Provider
public class GlobalExceptionMapper
        implements ExceptionMapper<RuntimeException> {


    @Context
    UriInfo uriInfo;


    @Override
    public Response toResponse(RuntimeException exception) {


        if (exception instanceof PersonaNoEncontradaException) {

            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(
                            new ApiError(
                                    LocalDateTime.now(),
                                    404,
                                    "PERSON_NOT_FOUND",
                                    exception.getMessage(),
                                    uriInfo.getPath()
                            )
                    )
                    .build();

        }


        if (exception instanceof EmailDuplicadoException) {

            return Response
                    .status(Response.Status.CONFLICT)
                    .entity(
                            new ApiError(
                                    LocalDateTime.now(),
                                    409,
                                    "EMAIL_ALREADY_EXISTS",
                                    exception.getMessage(),
                                    uriInfo.getPath()
                            )
                    )
                    .build();
        }


        return Response
                .status(500)
                .entity(
                        new ApiError(
                                LocalDateTime.now(),
                                500,
                                "INTERNAL_ERROR",
                                exception.getMessage(),
                                uriInfo.getPath()
                        )
                )
                .build();
    }
}