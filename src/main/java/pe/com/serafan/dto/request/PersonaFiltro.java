package pe.com.serafan.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

public class PersonaFiltro {

    @QueryParam("nombre")
    private String nombre;

    @QueryParam("apellido")
    private String apellido;

    @QueryParam("email")
    private String email;

    @QueryParam("page")
    @DefaultValue("0")
    @Min(value = 0, message = "La página no puede ser negativa")
    private Integer page;

    @QueryParam("size")
    @DefaultValue("10")
    @Min(value = 1, message = "El tamaño mínimo es 1")
    @Max(value = 100, message = "El tamaño máximo es 100")
    private Integer size;

    @QueryParam("sort")
    @DefaultValue("id")
    private String sort;

    @QueryParam("direction")
    @DefaultValue("asc")
    @Pattern(
            regexp = "asc|desc",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "direction debe ser asc o desc"
    )

    private String direction;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}