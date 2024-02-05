package mp0224.rental.core;

/**
 * General project exception. Should be implemented by more specific exceptions, hence all constructors are
 * protected.
 *
 */
public class RentalException extends RuntimeException {

    protected RentalException(String message) {
        super(message);
    }

    public RentalException(String message, Throwable cause) {
        super(message, cause);
    }
}
