package mp0224.rental.price.inline;

import mp0224.rental.price.PeriodicDiscount;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class WeekendDiscount extends PeriodicDiscountBase {

    public WeekendDiscount() {

    }

    @Override
    protected List<LocalDate> _getDiscountDates(LocalDate pStart, LocalDate pEnd) {

        return pStart.datesUntil(pEnd.plusDays(1L)) // add a day because the range does not include the end date
                .filter(pDate->pDate.getDayOfWeek().equals(DayOfWeek.SATURDAY) || pDate.getDayOfWeek().equals(DayOfWeek.SUNDAY))
                .collect(Collectors.toList());

    }
}
