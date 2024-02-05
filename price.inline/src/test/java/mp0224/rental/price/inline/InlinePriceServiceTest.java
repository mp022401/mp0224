package mp0224.rental.price.inline;

import mp0224.rental.price.*;
import mp0224.rental.tool.Brand;
import mp0224.rental.tool.Tool;
import mp0224.rental.tool.ToolType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class InlinePriceServiceTest extends PriceTestBase {


    @Test
    public void textExceptions() {

        assertThrows(ClerkDiscountException.class, ()->  s_PriceService.getPrice(Tools.TOOL1, LocalDate.of(2024, 1, 4), 4000, 200));
        assertThrows(RentalDaysException.class, ()->  s_PriceService.getPrice(Tools.TOOL1, LocalDate.of(2024, 1, 4), 0, 0));
        assertThrows(DefaultPriceNotFoundException.class, ()->  s_PriceService.getPrice(Tools.TOOL_NO_PRICE, LocalDate.of(2024, 1, 4), 1, 0));
    }

    @Test
    public void testService() {

        /*
        Check charges for a tool that is free on weekends, and is only rented for the weekend.
         */
        callPriceService(Tools.TOOL3, 20240224, 2, 50,
                Tools.TOOL3,
                2,
                20240224,
                20240225,
                4,
                0,
                0,
                50,
                0,
                0
                );

        /*
        As above, but keep until Monday
         */
        callPriceService(Tools.TOOL3, 20240224, 3, 50,
                Tools.TOOL3,
                3,
                20240224,
                20240226,
                s_PriceMap.get(Tools.TOOL3.getType()).intValue(),
                1,
                s_PriceMap.get(Tools.TOOL3.getType()).intValue() * 1,
                50,
                2, // 50% of a single daily charge
                2
        );


        /*
        Test a rental that happens when July 4th falls on a weekend (7/4/2026 - Saturday)
         */
        callPriceService(Tools.TOOL1, 20260702, 5, 0,
                Tools.TOOL1,
                5,
                20260702,
                20260706,
                s_PriceMap.get(Tools.TOOL1.getType()).intValue(),
                2,
                s_PriceMap.get(Tools.TOOL1.getType()).intValue() * 2, // 2 = charge days,
                0,
                0,
                2
                );
    }

    protected void callPriceService(Tool pTool, int pCheckoutDate, int pRentalDays, int pDiscountPercent, // provided values
            /* expected values... */
                                    Tool pExpectedTool,
                                    int pExpectedRentalDays,
                                    int pExpectedCheckoutDate,
                                    int pExpectedDueDate,
                                    int pExpectedDailyRentalCharge,
                                    int pExpectedChargeDays,
                                    int pExpectedPreDiscountCharge,
                                    int pExpectedDiscountPercent,
                                    int pExpectedDiscountAmount,
                                    int pExpectedFinalCharge) {

        ToolPriceDetails lclToolPriceDetails = s_PriceService.getPrice(pTool, ld(pCheckoutDate), pRentalDays, pDiscountPercent);

        assertEquals(pExpectedTool, lclToolPriceDetails.getTool());
        assertEquals(pExpectedRentalDays, lclToolPriceDetails.getRentalDays());
        assertEquals(ld(pExpectedCheckoutDate), lclToolPriceDetails.getCheckoutDate());
        assertEquals(ld(pExpectedDueDate), lclToolPriceDetails.getDueDate());
        assertEquals(pExpectedDailyRentalCharge, lclToolPriceDetails.getDailyRentalCharge());
        assertEquals(pExpectedChargeDays, lclToolPriceDetails.getChargeDays());
        assertEquals(pExpectedPreDiscountCharge, lclToolPriceDetails.getPreDiscountCharge());
        assertEquals(pExpectedDiscountPercent, lclToolPriceDetails.getDiscountPercent());
        assertEquals(pExpectedDiscountAmount, lclToolPriceDetails.getDiscountAmount());
        assertEquals(pExpectedFinalCharge, lclToolPriceDetails.getFinalCharge());


    }


}
