package mp0224.rental.application;

import mp0224.rental.core.RentalException;

/**
 * A base class for integer validation issues.
 */
public class IntegerFormatException extends RentalException {

    protected IntegerFormatException(String pMessage, String pValue) {
        super(String.format(pMessage, pValue));
    }
}
