package pe.com.serafan;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.com.serafan.entity.Persona;
import pe.com.serafan.integration.PostgresTestResource;
import pe.com.serafan.repository.PersonaRepository;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@QuarkusTestResource(PostgresTestResource.class)
class PersonaResourceTest {

    @Inject
    PersonaRepository repository;

    @BeforeEach
    @Transactional
    void prepararDatos() {

        repository.deleteAll();
        Persona persona = new Persona();
        persona.setNombre("Juan");
        persona.setApellido("Perez");
        persona.setEdad(35);
        persona.setEmail("juan@test.com");

        System.out.println("Antes: " + repository.count());

        repository.persist(persona);
        repository.flush();

        System.out.println("Después: " + repository.count());
    }

    @AfterEach
    @Transactional
    void limpiar() {
        repository.deleteAll();
    }

    @Test
    void debeListarPersonas() {

        given()
                .when()
                .get("/api/personas")
                .then()
                .statusCode(200)
                .body("page", notNullValue())
                .body("size", notNullValue())
                .body("content.size()", equalTo(1))
                .body("content[0].nombre", equalTo("Juan"))
                .body("content[0].apellido", equalTo("Perez"))
                .body("content[0].edad", equalTo(35))
                .body("content[0].email", equalTo("juan@test.com"))
                .body("totalElements", greaterThanOrEqualTo(0))
                .body("totalPages", greaterThanOrEqualTo(0));

    }

    @Test
    void debeCrearPersona() {

        String json = """
            {
                "nombre":"Carlos",
                "apellido":"Perez",
                "edad":30,
                "email":"carlos@test.com"
            }
            """;

        given()
                .contentType("application/json")
                .body(json)
                .when()
                .post("/api/personas")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("nombre", equalTo("Carlos"))
                .body("apellido", equalTo("Perez"))
                .body("edad", equalTo(30))
                .body("email", equalTo("carlos@test.com"));

        assertEquals(2, repository.count());
    }

    @Test
    void debeEliminarPersona() {

        given()

                .when()
                .delete("/api/personas/1")

                .then()
                .statusCode(404);

    }

   @Test
    void noDebeCrearPersonaConNombreVacio() {

        String json = """
            {
                "nombre":"",
                "apellido":"Perez",
                "edad":30,
                "email":"correo@test.com"
            }
            """;

        given()
                .contentType("application/json")
                .body(json)
                .when()
                .post("/api/personas")
                .then()
                .statusCode(400);

    }
}