package mp0224.rental.price;

import java.time.LocalDate;

/**
 * Thrown if a supplied end date falls before the start date
 */
public class EndDateBeforeStartDateException extends PriceException {

    public EndDateBeforeStartDateException(LocalDate pStart, LocalDate pEnd) {
        super("The end date (" + pEnd + ") falls before the start date (" + pStart + ")");
    }
}
