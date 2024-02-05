package mp0224.rental.price.inline;

import mp0224.rental.price.PeriodicDiscount;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalQuery;
import java.util.ArrayList;
import java.util.List;

public class LaborDayDiscount extends PeriodicDiscountBase {

    private static TemporalQuery<LocalDate> s_Query = new TemporalQuery<LocalDate>() {
        @Override
        public LocalDate queryFrom(TemporalAccessor pTemporal) {

            LocalDate lclDate = LocalDate.from(pTemporal);

            LocalDate lclLaborDay = getLaborDay(lclDate);

            if (lclDate.isAfter(lclLaborDay)) {
                lclLaborDay = getLaborDay(lclLaborDay.plusYears(1L));
            }

            return lclLaborDay;
        }

        private LocalDate getLaborDay(LocalDate pDate) {

            LocalDate lclSeptember = pDate.withMonth(Month.SEPTEMBER.getValue());

            return lclSeptember.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
        }
    };

    public LaborDayDiscount() {

    }

    @Override
    protected List<LocalDate> _getDiscountDates(LocalDate pStart, LocalDate pEnd) {

        ArrayList<LocalDate> lclReturn = new ArrayList<>();

        LocalDate lclCurrent = pStart;

        while (true) {

            LocalDate lclNextLaborDay = lclCurrent.query(s_Query);

            if (lclNextLaborDay.isAfter(pEnd)) {
                break;
            } else {
                lclReturn.add(lclNextLaborDay);
                lclCurrent = lclNextLaborDay.plusDays(1L);
            }

        }

        return lclReturn;
    }
}
