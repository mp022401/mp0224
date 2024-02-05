package mp0224.rental.price;


/**
 * Thrown if the number of rental days requested is not one or more.
 */
public class RentalDaysException extends PriceException {

    /**
     * See {@link ClerkDiscountException} for 'user friendly message' writeup.
     *
     * @param pDays
     */
    public RentalDaysException(int pDays) {
        super("Rental days need to be 1 or more (" + Integer.toString(pDays) + ")");
    }
}
