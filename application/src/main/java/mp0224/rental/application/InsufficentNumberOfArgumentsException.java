package mp0224.rental.application;

/**
 * Is thrown the number of arguments passed into the application is not either 0 or 4 (0 for help text)
 */
public class InsufficentNumberOfArgumentsException extends ApplicationException {

    private final static String s_Message = "An insufficient number of application arguments were supplied (%s)";

    public InsufficentNumberOfArgumentsException(int pSupplied) {
        super(String.format(s_Message, pSupplied));
    }
}
