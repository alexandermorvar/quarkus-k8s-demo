package pe.com.serafan.mapper;

import org.mapstruct.Mapper;
import pe.com.serafan.dto.request.PersonaRequest;
import pe.com.serafan.dto.response.PersonaResponse;
import pe.com.serafan.entity.Persona;

@Mapper(componentModel = "cdi")
public interface PersonaMapper {

    Persona toEntity(PersonaRequest request);

    PersonaResponse toResponse(Persona persona);
}