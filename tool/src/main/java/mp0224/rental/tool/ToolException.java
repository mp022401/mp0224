package mp0224.rental.tool;

import mp0224.rental.core.RentalException;

/**
 * General domain exception. Should be implemented by more specific exceptions, hence all constructors are
 * protected.
 */
public class ToolException extends RentalException {

    protected ToolException(String message) {
        super(message);
    }

}
