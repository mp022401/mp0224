package mp0224.rental.application;

/**
 * This is thrown if the clerk discount is not an integer. It does not check to see whether it's between 0 and 100
 * since that check is done in the business logic of the pricing module.
 */
public class DiscountFormatException extends IntegerFormatException {

    private final static String s_Message = "The supplied discount value, %s, if of an incorrect format";

    protected DiscountFormatException(String pValue) {
        super(s_Message, pValue);
    }
}