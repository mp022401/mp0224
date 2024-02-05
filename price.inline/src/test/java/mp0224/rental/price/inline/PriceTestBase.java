package mp0224.rental.price.inline;

import mp0224.rental.price.PeriodicDiscount;
import mp0224.rental.price.PeriodicDiscountService;
import mp0224.rental.price.PriceService;
import mp0224.rental.tool.Brand;
import mp0224.rental.tool.Tool;
import mp0224.rental.tool.ToolType;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class PriceTestBase {

    protected final static String s_TOOL_CODE1 = "tc1";
    protected final static String s_TOOL_CODE2 = "tc2";
    protected final static String s_TOOL_CODE3 = "tc3";
    protected final static String s_TOOL_CODE_NO_PRICE = "tc_no_price";

    protected enum ToolTypes implements ToolType {

        TOOL_TYPE_1,
        TOOL_TYPE_2,
        TOOL_TYPE_3,
        TOOL_WITH_NO_PRICE,
    }

    protected enum Brands implements Brand {
        BRAND1, BRAND2, BRAND3
        ;


        @Override
        public String getName() {
            return name();
        }
    }

    protected enum Tools implements Tool {

        TOOL1(s_TOOL_CODE1, ToolTypes.TOOL_TYPE_1, Brands.BRAND1),
        TOOL2(s_TOOL_CODE2, ToolTypes.TOOL_TYPE_2, Brands.BRAND2),
        TOOL3(s_TOOL_CODE3, ToolTypes.TOOL_TYPE_3, Brands.BRAND3),
        TOOL_NO_PRICE(s_TOOL_CODE3, ToolTypes.TOOL_WITH_NO_PRICE, Brands.BRAND3),
        ;

        protected final String m_Code;
        protected final ToolType m_Type;

        protected final Brand m_Brand;


        Tools(String pCode, ToolType pType, Brand pBrand) {
            m_Code = pCode;
            m_Type = pType;
            m_Brand = pBrand;
        }
        @Override
        public String getCode() {
            return m_Code;
        }

        @Override
        public ToolType getType() {
            return m_Type;
        }

        @Override
        public Brand getBrand() {
            return m_Brand;
        }
    }

    protected static final Map<ToolType, PeriodicDiscount> s_DiscountMap = new HashMap<>(){
        {
            put(ToolTypes.TOOL_TYPE_1, new AggregatePeriodicDiscount(new WeekendDiscount(), new July4Discount(), new LaborDayDiscount()));
            put(ToolTypes.TOOL_TYPE_2, new NoDiscount());
            put(ToolTypes.TOOL_TYPE_3, new WeekendDiscount());
        }
    };

    protected static final PeriodicDiscountService s_DiscountService = new InlinePeriodicDiscountService(s_DiscountMap);

    protected static final Map<ToolType, Integer> s_PriceMap = new HashMap<>() {
        {
            put(ToolTypes.TOOL_TYPE_1, 1);
            put(ToolTypes.TOOL_TYPE_2, 2);
            put(ToolTypes.TOOL_TYPE_3, 4);
        }
    };

    protected static final PriceService s_PriceService = new InlinePriceService(s_PriceMap, s_DiscountService);

    protected PriceTestBase() {

    }

    /**
     * Convenience method for creating {@link LocalDate} instances
     *
     * @param pYear
     * @param pMonth
     * @param pDay
     * @return
     */
    protected LocalDate ld(int pYear, int pMonth, int pDay) {
        return LocalDate.of(pYear, pMonth, pDay);
    }

    /**
     * Convenience method for creating {@link LocalDate} instances
     *
     * @param pYYYYMMDD
     * @return
     */
    protected LocalDate ld(int pYYYYMMDD) {

        String lclDate = Integer.toString(pYYYYMMDD);

        int yyyy = Integer.parseInt(lclDate.substring(0, 4));
        int mm = Integer.parseInt(lclDate.substring(4, 6));
        int dd = Integer.parseInt(lclDate.substring(6));

        return ld(yyyy, mm, dd);
    }
}
