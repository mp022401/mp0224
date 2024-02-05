package mp0224.rental.price.inline;

import mp0224.rental.price.PeriodicDiscount;
import mp0224.rental.price.PeriodicDiscountService;
import mp0224.rental.tool.Tool;
import mp0224.rental.tool.ToolType;

import java.util.Map;

public class InlinePeriodicDiscountService implements PeriodicDiscountService {

    private final Map<ToolType, PeriodicDiscount> m_DiscountMap;
    public InlinePeriodicDiscountService(Map<ToolType, PeriodicDiscount> pDiscountMap) {
        m_DiscountMap = pDiscountMap;

    }

    @Override
    public PeriodicDiscount getPeriodicDiscount(ToolType pToolType) {

        PeriodicDiscount lclReturn = m_DiscountMap.get(pToolType);

        if (lclReturn != null) {

            return lclReturn;

        } else {

            return new NoDiscount();
        }
    }
}
