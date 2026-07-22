package pe.com.serafan.exception.business;

public class EmailDuplicadoException extends RuntimeException {

    public EmailDuplicadoException(String email) {
        super("Ya existe una persona con el email " + email);
    }
}