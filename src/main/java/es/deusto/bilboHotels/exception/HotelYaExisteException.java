package es.deusto.bilboHotels.exception;

public class HotelYaExisteException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public HotelYaExisteException() {
        super();
    }

    public HotelYaExisteException(String message) {
        super(message);
    }

    public HotelYaExisteException(String message, Throwable cause) {
        super(message, cause);
    }

}
