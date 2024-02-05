package mp0224.rental.price.inline;

import mp0224.rental.price.EndDateBeforeStartDateException;
import mp0224.rental.price.PeriodicDiscount;
import mp0224.rental.tool.Brand;
import mp0224.rental.tool.Tool;
import mp0224.rental.tool.ToolType;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class DiscountTest extends PriceTestBase {

    /**
     * Make sure July 4th holiday day falls on the right day (including those times when it falls during a weekend)
     */
    @Test
    public void testJuly4th() {

        July4Discount lclDiscount = new July4Discount();

        /*
        Ensure that the current adjustment is made when July 4th falls either on a Saturday or Sunday
         */
        List<LocalDate> lclDates = lclDiscount.getDiscountDates(ld(20240201), ld(20280201));

        assertEquals(4, lclDates.size());

        assertEquals(DayOfWeek.THURSDAY, lclDates.get(0).getDayOfWeek());
        assertEquals(DayOfWeek.FRIDAY, lclDates.get(1).getDayOfWeek());
        assertEquals(DayOfWeek.FRIDAY, lclDates.get(2).getDayOfWeek());
        assertEquals(DayOfWeek.MONDAY, lclDates.get(3).getDayOfWeek());

        /*
        Ensure that July 4th is found on the correct day, given that, per the specification, despite
        actually being on the 4th, in 2026, the true discount day will actually be on the preceding Friday
        and so should not be counted here. Same for the end date, since although falling on the 4th,\
        the discount day will be on the following Monday (5th). As the 5th is not included in this range
        then there should be no discount days despite specifying July 4th twice.
         */
        lclDates = lclDiscount.getDiscountDates(ld(20260704), ld(20270704));

        assertEquals(0, lclDates.size());

    }

    @Test
    public void testLaborDay() {

        PeriodicDiscount lclDiscount = new LaborDayDiscount();

        List<LocalDate> lclDates = lclDiscount.getDiscountDates(LocalDate.of(2024, 2, 1), LocalDate.of(2028, 2, 1));

        assertEquals(4, lclDates.size());
        assertEquals(DayOfWeek.MONDAY, lclDates.get(0).getDayOfWeek());
        assertEquals(LocalDate.of(2024, 9, 2), lclDates.get(0));
        assertEquals(DayOfWeek.MONDAY, lclDates.get(1).getDayOfWeek());
        assertEquals(LocalDate.of(2025, 9, 1), lclDates.get(1));
        assertEquals(DayOfWeek.MONDAY, lclDates.get(2).getDayOfWeek());
        assertEquals(LocalDate.of(2026, 9, 7), lclDates.get(2));
        assertEquals(DayOfWeek.MONDAY, lclDates.get(3).getDayOfWeek());
        assertEquals(LocalDate.of(2027, 9, 6), lclDates.get(3));
    }

    @Test
    public void testWeekends() {

        PeriodicDiscount lclDiscount = new WeekendDiscount();

        List<LocalDate> lclDates = lclDiscount.getDiscountDates(LocalDate.of(2024, 2, 1), LocalDate.of(2028, 2, 1));

        LocalDate lclSaturday = lclDates.get(0);
        LocalDate lclSunday = lclDates.get(1);

        assertEquals(DayOfWeek.SATURDAY, lclSaturday.getDayOfWeek());
        assertEquals(DayOfWeek.SUNDAY, lclSunday.getDayOfWeek());

        int i = 2;

        while (i < lclDates.size()) {

            lclSaturday = lclSaturday.plusDays(7);
            lclSunday = lclSunday.plusDays(7);

            assertEquals(DayOfWeek.SATURDAY, lclDates.get(i).getDayOfWeek());
            assertEquals(DayOfWeek.SUNDAY, lclDates.get(i + 1).getDayOfWeek());

            i+=2;

        }

    }

    /**
     * A {@link NoDiscount} type is returned from the {@link mp0224.rental.price.PeriodicDiscountService} is no
     * formal discount is found for the provided {@link mp0224.rental.tool.Tool}
     */
    @Test
    public void testNoDiscount() {

        PeriodicDiscount lclDiscount = new NoDiscount();

        List<LocalDate> lclDates = lclDiscount.getDiscountDates(ld(20240201), ld(20280201));

        assertEquals(0, lclDates.size());

        /*
        Also make sure the discount service returns a NoDiscount type when one is not found for the Tool type
        supplied.
         */

        assertEquals(NoDiscount.class, s_DiscountService.getPeriodicDiscount(new ToolType() {
        }).getClass());

    }

    /**
     * From above, it's known that July 4th falls on a weekend in both 2026 (Saturday) and 2027 (Sunday), use
     * that information here.
     */
    @Test
    public void testAggregateDiscounts() {


        PeriodicDiscount lclDiscount = new AggregatePeriodicDiscount(new July4Discount(), new WeekendDiscount());


        List<LocalDate> lcl2026Dates = lclDiscount.getDiscountDates(LocalDate.of(2026, 7, 1), LocalDate.of(2026, 7, 7));

        assertEquals(3, lcl2026Dates.size());

        assertEquals(LocalDate.of(2026, 7, 3), lcl2026Dates.get(0));
        assertEquals(LocalDate.of(2026, 7, 4), lcl2026Dates.get(1));
        assertEquals(LocalDate.of(2026, 7, 5), lcl2026Dates.get(2));


        List<LocalDate> lcl2027Dates = lclDiscount.getDiscountDates(LocalDate.of(2027, 7, 1), LocalDate.of(2027, 7, 7));

        assertEquals(LocalDate.of(2027, 7, 3), lcl2027Dates.get(0));
        assertEquals(LocalDate.of(2027, 7, 4), lcl2027Dates.get(1));
        assertEquals(LocalDate.of(2027, 7, 5), lcl2027Dates.get(2));


    }

    /**
     * When a sub-class returns null when implementing {@link PeriodicDiscountBase#_getDiscountDates(LocalDate, LocalDate)}
     * ensure it's handled without a {@link NullPointerException} being thrown
     */
    @Test
    public void testNullDiscount() {

        assertNotNull(new NullDiscount().getDiscountDates(ld(20240101), ld(20240102)));
        
    }

    @Test
    public void testExceptions() {

        assertThrows(EndDateBeforeStartDateException.class, () -> new NoDiscount().getDiscountDates(ld(20240102), ld(20240101)));
        assertThrows(EndDateBeforeStartDateException.class, () -> new July4Discount().getDiscountDates(ld(20240102), ld(20240101)));
        assertThrows(EndDateBeforeStartDateException.class, () -> new LaborDayDiscount().getDiscountDates(ld(20240102), ld(20240101)));
        assertThrows(EndDateBeforeStartDateException.class, () -> new WeekendDiscount().getDiscountDates(ld(20240102), ld(20240101)));
        assertThrows(EndDateBeforeStartDateException.class, () -> new AggregatePeriodicDiscount().getDiscountDates(ld(20240102), ld(20240101)));
        assertThrows(EndDateBeforeStartDateException.class, () -> new NullDiscount().getDiscountDates(ld(20240102), ld(20240101)));

    }


}


