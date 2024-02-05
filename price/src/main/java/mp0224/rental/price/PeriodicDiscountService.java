package mp0224.rental.price;

import mp0224.rental.tool.ToolType;

/**
 * Returns the discount(s) in effect for the tool type {@link mp0224.rental.tool.ToolType}.
 */
public interface PeriodicDiscountService {

    /**
     * The contract here is to return a discount that's appropriate for the tool type  {@link ToolType} provided.
     * It is assumed that if a specific discount is not found then, instead of
     * throwing an error, it is assumed that there is no discount for that tool / tool attributes.
     *
     * @param pTool
     * @return
     */
    PeriodicDiscount getPeriodicDiscount(ToolType pTool);
}
