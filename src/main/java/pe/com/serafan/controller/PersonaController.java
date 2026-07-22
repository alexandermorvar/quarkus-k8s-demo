package pe.com.serafan.controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.com.serafan.dto.request.PersonaFiltro;
import pe.com.serafan.dto.request.PersonaRequest;
import pe.com.serafan.dto.response.PageResponse;
import pe.com.serafan.dto.response.PersonaResponse;
import pe.com.serafan.service.PersonaService;

@Path("/api/personas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PersonaController {

    @Inject
    PersonaService service;

    @POST
    public Response crear(@Valid PersonaRequest request) {

        PersonaResponse response = service.crear(request);

        return Response
                .status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

//    @GET
//    public PageResponse<PersonaResponse> listar(
//            @QueryParam("page")
//            @DefaultValue("0")
//            int page,
//
//            @QueryParam("size")
//            @DefaultValue("10")
//            int size
//    ) {
//
//        return service.listar(page, size);
//    }

    @GET
    @Path("/{id}")
    public PersonaResponse buscarPorId(
            @PathParam("id") Long id
    ) {

        return service.buscarPorId(id);
    }

    @PUT
    @Path("/{id}")
    public PersonaResponse actualizar(
            @PathParam("id") Long id,
            PersonaRequest request
    ) {

        return service.actualizar(id, request);
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Long id) {
        service.eliminar(id);
        return Response.noContent().build();
    }

    @GET
    public PageResponse<PersonaResponse> listar(
            @Valid @BeanParam PersonaFiltro filtro
    ){
        return service.listar(filtro);
    }

    @GET
    @Path("/version")
    @Produces(MediaType.TEXT_PLAIN)
    public String version() {
        return "Version 1.0.1";
    }
}