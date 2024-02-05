package mp0224.rental.price;

/**
 * The 'spec' asks for an 'instructive, user friendly message'. Such a message has been added here but does
 * not lend itself to internationalization. However, this probably does not matter so much since the end user
 * should never see such an exception, and hopefully the developers, even if English is not their first language will
 * get the gist of the message.
 */
public class ClerkDiscountException extends PriceException {

    public ClerkDiscountException(int pDiscount) {
        super("Clerk discount is not in range (" + Integer.toString(pDiscount) + ")");
    }
}
