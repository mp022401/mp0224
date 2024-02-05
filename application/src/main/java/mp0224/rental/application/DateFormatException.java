package mp0224.rental.application;

/**
 * Thrown if the date supplied to the application is not of the current format
 */
public class DateFormatException extends ApplicationException {

    private final static String s_Message = "The supplied date, %s, if of an incorrect format, needs to be MM/DD/YYYY";

    public DateFormatException(String pDate) {
        super(String.format(s_Message, pDate));
    }

    protected DateFormatException(String pDate, Throwable cause) {
        super(String.format(s_Message, pDate), cause);
    }
}
