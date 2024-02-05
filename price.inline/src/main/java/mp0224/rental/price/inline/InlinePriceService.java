package mp0224.rental.price.inline;

import mp0224.rental.price.*;
import mp0224.rental.tool.Tool;
import mp0224.rental.tool.ToolType;

import java.time.LocalDate;
import java.util.Map;

public class InlinePriceService implements PriceService {

    private final Map<ToolType, Integer> m_DefaultPriceMap;
    private final PeriodicDiscountService m_DiscountService;
    public InlinePriceService(Map<ToolType, Integer> pDefaultPriceMap, PeriodicDiscountService pDiscountService) {

        m_DefaultPriceMap = pDefaultPriceMap;
        m_DiscountService = pDiscountService;
    }

    @Override
    public ToolPriceDetails getPrice(Tool pTool, LocalDate pCheckoutDate, int pRentalDays, int pClerkDiscount) {

        if (pRentalDays < 1) {
            throw new RentalDaysException(pRentalDays);
        }

        if (pClerkDiscount < 0 || pClerkDiscount > 100) {
            throw new ClerkDiscountException(pClerkDiscount);
        }

        Integer lclDefaultPrice = m_DefaultPriceMap.get(pTool.getType());

        if (lclDefaultPrice == null) {
            throw new DefaultPriceNotFoundException(pTool.getType());
        }

        /*
        The '- 1' ensures start and end date are inclusive
         */
        LocalDate lclEndDateInclusive = pCheckoutDate.plusDays(Integer.toUnsignedLong(pRentalDays - 1));

        PeriodicDiscount lclDiscount = m_DiscountService.getPeriodicDiscount(pTool.getType());

        int lclChargeDays = pRentalDays - lclDiscount.getDiscountDates(pCheckoutDate, lclEndDateInclusive).size();

        return new InlineToolPriceDetails(
                pTool,
                pRentalDays,
                pCheckoutDate,
                lclEndDateInclusive,
                lclDefaultPrice,
                lclChargeDays,
                pClerkDiscount

        );
    }
}
