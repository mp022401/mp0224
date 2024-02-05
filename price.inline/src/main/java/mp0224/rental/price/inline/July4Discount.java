package mp0224.rental.price.inline;

import mp0224.rental.price.PeriodicDiscount;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;
import java.util.ArrayList;
import java.util.List;

public class July4Discount extends PeriodicDiscountBase {

    private static TemporalQuery<LocalDate> s_Query = new TemporalQuery<LocalDate>() {
        @Override
        public LocalDate queryFrom(TemporalAccessor pTemporal) {

            LocalDate lclDate = LocalDate.from(pTemporal);

            LocalDate lclJuly4 = getJuly4(lclDate);

            if (lclDate.isAfter(lclJuly4)) {
                lclJuly4 = getJuly4(LocalDate.of(lclJuly4.getYear() + 1, 7, 4));
            }

            return lclJuly4;
        }

        private LocalDate getJuly4(LocalDate pDate) {

            LocalDate lclJuly = pDate.withMonth(Month.JULY.getValue());

            LocalDate lclActual = lclJuly.withDayOfMonth(4);

            LocalDate lclAdjusted = null;

            if (lclActual.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                lclAdjusted = lclActual.plusDays(1L);
            } else if (lclActual.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
                lclAdjusted = lclActual.minusDays(1L);
            } else {
                lclAdjusted = lclActual;
            }

            return lclAdjusted;

        }
    };

    public July4Discount() {

    }

    @Override
    protected List<LocalDate> _getDiscountDates(LocalDate pStart, LocalDate pEnd) {

        ArrayList<LocalDate> lclReturn = new ArrayList<>();

        LocalDate lclCurrent = pStart;

        while (true) {

            LocalDate lclNextJuly4 = lclCurrent.query(s_Query);

            if (lclNextJuly4.isAfter(pEnd)) {
                break;
            } else {
                lclReturn.add(lclNextJuly4);
                lclCurrent = lclNextJuly4.plusMonths(11L); // Should get us close enough to next year's 4th
            }

        }

        return lclReturn;

    }
}
