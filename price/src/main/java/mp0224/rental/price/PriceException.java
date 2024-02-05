package mp0224.rental.price;

import mp0224.rental.core.RentalException;

/**
 * General domain exception. Should be implemented by more specific exceptions, hence all constructors are
 * protected.
 */
public class PriceException extends RentalException {

    protected PriceException(String message) {
        super(message);
    }

}
