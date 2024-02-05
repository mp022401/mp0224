package mp0224.rental.price.inline;

import mp0224.rental.price.EndDateBeforeStartDateException;
import mp0224.rental.price.PeriodicDiscount;

import java.time.LocalDate;
import java.util.List;

public abstract class PeriodicDiscountBase implements PeriodicDiscount {

    protected PeriodicDiscountBase() {

    }

    @Override
    public List<LocalDate> getDiscountDates(LocalDate pStart, LocalDate pEnd) {

        if (pEnd.isBefore(pStart)) {
            throw new EndDateBeforeStartDateException(pStart, pEnd);
        }

        List<LocalDate> lclReturn =  _getDiscountDates(pStart, pEnd);

        if (lclReturn != null) {
            return lclReturn;
        } else {
            return new NoDiscount().getDiscountDates(pStart, pEnd);
        }

    }

    protected abstract List<LocalDate>_getDiscountDates(LocalDate pStart, LocalDate pEnd);


}
