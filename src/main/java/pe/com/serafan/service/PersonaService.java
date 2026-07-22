package pe.com.serafan.service;

import pe.com.serafan.dto.request.PersonaFiltro;
import pe.com.serafan.dto.request.PersonaRequest;
import pe.com.serafan.dto.response.PageResponse;
import pe.com.serafan.dto.response.PersonaResponse;

public interface PersonaService {

    PersonaResponse crear(PersonaRequest request);

//    PageResponse<PersonaResponse> listar(
//            int page,
//            int size
//    );

    PageResponse<PersonaResponse> listar(PersonaFiltro filtro);

    PersonaResponse buscarPorId(Long id);

    PersonaResponse actualizar(Long id, PersonaRequest request);

    void eliminar(Long id);

}