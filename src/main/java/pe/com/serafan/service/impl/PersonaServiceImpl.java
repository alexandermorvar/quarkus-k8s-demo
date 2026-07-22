package pe.com.serafan.service.impl;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import pe.com.serafan.dto.request.PersonaFiltro;
import pe.com.serafan.dto.request.PersonaRequest;
import pe.com.serafan.dto.response.PageResponse;
import pe.com.serafan.dto.response.PersonaResponse;
import pe.com.serafan.entity.Persona;
import pe.com.serafan.exception.business.EmailDuplicadoException;
import pe.com.serafan.exception.business.PersonaNoEncontradaException;
import pe.com.serafan.mapper.PersonaMapper;
import pe.com.serafan.repository.PersonaRepository;
import pe.com.serafan.service.PersonaService;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class PersonaServiceImpl implements PersonaService {

    @Inject
    PersonaRepository repository;

    @Inject
    PersonaMapper mapper;

    @Override
    @Transactional
    public PersonaResponse crear(PersonaRequest request) {

        repository.findByEmail(request.getEmail())
                .ifPresent(p -> {
//                    throw new IllegalArgumentException("Ya existe una persona con ese email");
                    throw new EmailDuplicadoException(request.getEmail());
                });

        Persona persona = mapper.toEntity(request);

        repository.persist(persona);

        return mapper.toResponse(persona);
    }

//    @Override
//    public PageResponse<PersonaResponse> listar(
//            int page,
//            int size
//    ) {
//
//        PanacheQuery<Persona> query =
////                repository.buscarTodos();
//                repository.buscarActivos();
//
//        long total = query.count();
//
//        List<PersonaResponse> personas =
//                query.page(page, size)
//                        .list()
//                        .stream()
//                        .map(mapper::toResponse)
//                        .toList();
//
//
//        return new PageResponse<>(
//                personas,
//                page,
//                size,
//                total
//        );
//    }

    @Override
    public PageResponse<PersonaResponse> listar(
            PersonaFiltro filtro
    ) {

        PanacheQuery<Persona> query =
                repository.buscar(filtro);

        long total = query.count();

        List<PersonaResponse> personas =
                query.page(
                                filtro.getPage(),
                                filtro.getSize()
                        )
                        .list()
                        .stream()
                        .map(mapper::toResponse)
                        .toList();

        return new PageResponse<>(
                personas,
                filtro.getPage(),
                filtro.getSize(),
                total
        );

    }

    @Override
    public PersonaResponse buscarPorId(Long id) {
        Persona persona = repository.findById(id);

        if (persona == null) {
            throw new PersonaNoEncontradaException(id);
        }

        return mapper.toResponse(persona);
    }

    @Override
    @Transactional
    public PersonaResponse actualizar(
            Long id,
            PersonaRequest request
    ) {

        Persona persona = repository.findById(id);


        if (persona == null) {
            throw new PersonaNoEncontradaException(id);
        }


        repository.findByEmail(request.getEmail())
                .ifPresent(existente -> {

                    if (!existente.getId().equals(id)) {
                        throw new EmailDuplicadoException(
                                request.getEmail()
                        );
                    }

                });


        persona.setNombre(request.getNombre());
        persona.setApellido(request.getApellido());
        persona.setEmail(request.getEmail());
        persona.setEdad(request.getEdad());


        return mapper.toResponse(persona);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {

        Persona persona = repository.findById(id);

        if (persona == null || Boolean.FALSE.equals(persona.getActivo())) {
            throw new PersonaNoEncontradaException(id);
        }

        persona.setActivo(false);
        persona.setFechaEliminacion(LocalDateTime.now());
    }
}