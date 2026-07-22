package pe.com.serafan.exception.business;

public class PersonaNoEncontradaException extends RuntimeException {

    public PersonaNoEncontradaException(Long id) {
        super("No existe una persona con id " + id);
    }

}