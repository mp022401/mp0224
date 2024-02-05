package mp0224.rental.price.inline;

import java.time.LocalDate;
import java.util.List;

public class NullDiscount extends PeriodicDiscountBase {

    NullDiscount() {

    }

    @Override
    protected List<LocalDate> _getDiscountDates(LocalDate pStart, LocalDate pEnd) {
        return null;
    }
}
