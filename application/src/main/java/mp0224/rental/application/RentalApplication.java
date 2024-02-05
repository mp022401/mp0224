package mp0224.rental.application;

import mp0224.rental.agreement.inline.LineWriterAgreementService;
import mp0224.rental.core.LineWriter;
import mp0224.rental.module.RentalModule;
import mp0224.rental.price.PeriodicDiscount;
import mp0224.rental.price.inline.*;
import mp0224.rental.tool.Tool;
import mp0224.rental.tool.ToolType;
import mp0224.rental.tool.inline.BrandEnum;
import mp0224.rental.tool.inline.InlineTool;
import mp0224.rental.tool.inline.InlineToolService;
import mp0224.rental.tool.inline.ToolTypeEnum;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * This does the 'dirty work', by which means dealing with the interface implementations so that the remainder of the application
 * classes can be 'pure' insofar as the latter only need work with interfaces.
 *
 * It basically assembles all the necessary parts and 'injects' those parts (implementations) where they are required.
 *
 * In a web server environment (i.e. no application), this would be replaced by a shim that would intercept web specific
 * request (eg. HttpServletRequest), extract the necessary information from them and convert them into whatever types
 * are required by {@link RentalModule#generateAgreement(String, LocalDate, int, int)} method.
 *
 * Likewise, an implementation of HttpServletResponse would implement the {@link LineWriter} interface and convert the
 * println calls into HTML so the agreement could be rendered in a web browser.
 *
 * The important thing is that no downstream processes would need to change to accommodate this.
 */
public class RentalApplication implements LineWriter {

    private final static int s_ARG_TOOL_CODE = 0;
    private final static int s_ARG_CHECKOUT_DATE = 1;
    private final static int s_ARG_DAYS = 2;
    private final static int s_ARG_DISCOUNT = 3;
    private final static String s_CHNS = "CHNS";
    private final static String s_LADW = "LADW";
    private final static String s_JAKD = "JAKD";
    private final static String s_JAKR = "JAKR";

    private final static Map<String, Tool> s_ToolMap = new HashMap<>() {
        {

            put(s_CHNS, new InlineTool(s_CHNS, ToolTypeEnum.CHAINSAW, BrandEnum.STIHL));
            put(s_LADW, new InlineTool(s_LADW, ToolTypeEnum.LADDER, BrandEnum.WERNER));
            put(s_JAKD, new InlineTool(s_JAKD, ToolTypeEnum.JACKHAMMER, BrandEnum.DEWALT));
            put(s_JAKR, new InlineTool(s_JAKR, ToolTypeEnum.JACKHAMMER, BrandEnum.RIDGID));

        }
    };

    /**
     * Instead of populating based on charges, the following map is populated base on discounts (i.e. the inverse)
     */
    private final static Map<ToolType, PeriodicDiscount> s_DiscountMap = new HashMap<>() {
        {
            put(ToolTypeEnum.LADDER, new AggregatePeriodicDiscount(new July4Discount(), new LaborDayDiscount()));
            put(ToolTypeEnum.CHAINSAW, new WeekendDiscount());
            put(ToolTypeEnum.JACKHAMMER, new AggregatePeriodicDiscount(new WeekendDiscount(), new July4Discount(), new LaborDayDiscount()));

        }
    };

    private final static Map<ToolType, Integer> s_PriceMap = new HashMap<>() {
        {
            put(ToolTypeEnum.LADDER, Integer.valueOf(199));
            put(ToolTypeEnum.CHAINSAW, Integer.valueOf(149));
            put(ToolTypeEnum.JACKHAMMER, Integer.valueOf(299));
        }
    };

    private static LineWriter s_LineWriter;

    private final static String s_Help = "Parameters: Tool Code (string), Checkout Date (string - MM/DD/YYYY), Rental Days (int), Discount% (int)";

    static {
        s_LineWriter = null;
    }

    /**
     * Positional parameters are as follows
     *
     * 1. Tool code
     * 2. Check out date
     * 3. Number of rental days
     * 5. Clerk discount
     *
     * @param argv
     */
    public static void main(String argv[]) {

        new RentalApplication(argv);

    }

    protected RentalApplication(String argv[]) {

        validateAndRun(argv);

    }

    public static void setLineWriter(LineWriter pWriter) {
        s_LineWriter = pWriter;
    }

    /**
     * The validation done here is not business level, so for example, whilst checking that a string can be converted
     * to an integer (eg. as in the case for clerk discount), it will not check to see whether it is within the
     * correct range (i.e. between 0 and 100 for clerk discount).
     *
     * @param argv
     */
    protected void validateAndRun(String argv[]) {

        if (argv.length > 0 && argv.length != 4) {

            throw new InsufficentNumberOfArgumentsException(argv.length);

        } else if (argv.length == 0) {

            printHelp();

        } else {

            String lclToolCode = argv[s_ARG_TOOL_CODE].toUpperCase();

            String lclDateArg = argv[s_ARG_CHECKOUT_DATE];

            LocalDate lclCheckoutDate = null;

            try {

                String[] lclParts = lclDateArg.split("/");

                if (lclParts.length != 3) {
                    throw new DateFormatException(lclDateArg);
                }

                lclCheckoutDate = LocalDate.of(Integer.parseInt(lclParts[2]), Integer.parseInt(lclParts[0]), Integer.parseInt(lclParts[1]));

            } catch (DateFormatException e) { // if throwing a 'known' exception, no need to wrap it up

                throw e;

            } catch (Exception e) {

                throw new DateFormatException(lclDateArg, e);
            }

            int lclRentalDays = 0;

            try {

                lclRentalDays = Integer.parseInt(argv[s_ARG_DAYS]);

            } catch (NumberFormatException e) {

                throw new RentalDaysFormatException(argv[s_ARG_DAYS]);
            }

            int lclDiscount = 0;

            try {

                lclDiscount = Integer.parseInt(argv[s_ARG_DISCOUNT]);

            } catch (NumberFormatException e) {

                throw new DiscountFormatException(argv[s_ARG_DISCOUNT]);
            }

            run(lclToolCode, lclCheckoutDate, lclRentalDays, lclDiscount);

        }
    }


    protected void run(String pToolCode, LocalDate pCheckoutDate, int pDays, int pDiscount) {

        InlineToolService lclToolService = new InlineToolService(getToolMap());

        InlinePeriodicDiscountService lclDiscountService = new InlinePeriodicDiscountService(getDiscountMap());

        InlinePriceService lclPriceService = new InlinePriceService(getDiscountPriceMap(), lclDiscountService);

        LineWriterAgreementService lclAgreementSerivce = new LineWriterAgreementService(this);

        new RentalModule(lclToolService, lclPriceService, lclAgreementSerivce).generateAgreement(pToolCode, pCheckoutDate, pDays, pDiscount);

    }

    protected Map<String, Tool> getToolMap() {

        return s_ToolMap;
    }

    protected Map<ToolType, PeriodicDiscount> getDiscountMap() {

        return s_DiscountMap;
    }

    protected Map<ToolType, Integer> getDiscountPriceMap() {

        return s_PriceMap;
    }

    protected void printHelp() {

        System.out.println(s_Help);

    }

    @Override
    public void println(String pText) {

        if (s_LineWriter == null) {
            System.out.println(pText);
        } else {
            s_LineWriter.println(pText);
        }
    }
}
