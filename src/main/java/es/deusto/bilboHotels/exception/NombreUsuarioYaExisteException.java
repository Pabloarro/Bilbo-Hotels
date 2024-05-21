package es.deusto.bilboHotels.exception;

public class NombreUsuarioYaExisteException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NombreUsuarioYaExisteException() {
        super();
    }

    public NombreUsuarioYaExisteException(String message) {
        super(message);
    }

    public NombreUsuarioYaExisteException(String message, Throwable cause) {
        super(message, cause);
    }

}
