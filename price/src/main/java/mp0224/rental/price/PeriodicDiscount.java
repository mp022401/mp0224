package mp0224.rental.price;

import java.time.LocalDate;
import java.util.List;

/**
 * Returns the date for which a discount applies. In this case the discount is 100% and therefore there is no rental
 * charge for the dates returned here.
 */
public interface PeriodicDiscount {

    List<LocalDate> getDiscountDates(LocalDate pStart, LocalDate pEnd);
}
