package mp0224.rental.application;

/**
 * This is thrown if the rental days provided is non-numeric. This will not check to see whether the rental days
 * are less than 1 since that is a business level validation.
 */
public class RentalDaysFormatException extends IntegerFormatException {

    private final static String s_Message = "The supplied rental days value, %s, if of an incorrect format";

    protected RentalDaysFormatException(String pValue) {
        super(s_Message, pValue);
    }
}
