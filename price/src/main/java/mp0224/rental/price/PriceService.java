package mp0224.rental.price;


import mp0224.rental.tool.Tool;

import java.time.LocalDate;

/**
 * Returns pricing details for the given tool. The {@link ToolPriceDetails} is slightly denormalized insofar as
 * it contains pricing information that can otherwise be calculated in components that need such information.
 *
 */
public interface PriceService {

    ToolPriceDetails getPrice(Tool pTool, LocalDate pCheckoutDate, int pRentalDays, int pClerkDiscount);
}
