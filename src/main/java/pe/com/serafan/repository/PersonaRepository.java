package pe.com.serafan.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import pe.com.serafan.dto.request.PersonaFiltro;
import pe.com.serafan.entity.Persona;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class PersonaRepository implements PanacheRepository<Persona> {

    public Optional<Persona> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }

    private static final Set<String> CAMPOS_ORDENABLES = Set.of(
            "id",
            "nombre",
            "apellido",
            "edad",
            "email"
    );

    public PanacheQuery<Persona> buscarTodos() {
        return findAll();
    }

    public PanacheQuery<Persona> buscarActivos() {
        return find("activo", true);
    }

//    PanacheQuery<Persona> buscar(PersonaFiltro filtro);

    public PanacheQuery<Persona> buscar(PersonaFiltro filtro) {

        StringBuilder jpql = new StringBuilder("activo = true");

        Map<String, Object> params = new HashMap<>();

        if (filtro.getNombre() != null && !filtro.getNombre().isBlank()) {

            jpql.append(" and lower(nombre) like :nombre");

            params.put(
                    "nombre",
                    "%" + filtro.getNombre().toLowerCase() + "%"
            );
        }

        if (filtro.getApellido() != null && !filtro.getApellido().isBlank()) {

            jpql.append(" and lower(apellido) like :apellido");

            params.put(
                    "apellido",
                    "%" + filtro.getApellido().toLowerCase() + "%"
            );
        }

        if (filtro.getEmail() != null && !filtro.getEmail().isBlank()) {

            jpql.append(" and lower(email) like :email");

            params.put(
                    "email",
                    "%" + filtro.getEmail().toLowerCase() + "%"
            );
        }

        String campoOrden = filtro.getSort();

        if (!CAMPOS_ORDENABLES.contains(campoOrden)) {
            campoOrden = "id";
        }

        Sort sort;

        if ("desc".equalsIgnoreCase(filtro.getDirection())) {

            sort = Sort.descending(campoOrden);

        } else {

            sort = Sort.ascending(campoOrden);

        }

        return find(
                jpql.toString(),
                sort,
                params
        );

    }

}