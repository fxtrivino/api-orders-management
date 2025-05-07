package co.apexglobal.ordersmng.exception;

public class OrdenNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public OrdenNotFoundException(String message) {
        super(message);
    }
}
