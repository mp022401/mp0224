package mp0224.rental.price.inline;

import mp0224.rental.price.PeriodicDiscount;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class NoDiscount extends PeriodicDiscountBase {

    NoDiscount() {

    }

    @Override
    protected List<LocalDate> _getDiscountDates(LocalDate pStart, LocalDate pEnd) {
        return new ArrayList<>();
    }
}
