package mp0224.rental.module;

import mp0224.rental.agreement.AgreementService;
import mp0224.rental.price.PriceService;
import mp0224.rental.price.ToolPriceDetails;
import mp0224.rental.tool.Brand;
import mp0224.rental.tool.Tool;
import mp0224.rental.tool.ToolService;
import mp0224.rental.tool.ToolType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class RentalModuleTest {

    private static final Tool s_Tool1 = new Tool() {
        @Override
        public String getCode() {
            return "code";
        }

        @Override
        public ToolType getType() {
            return new ToolType() {
            };
        }

        @Override
        public Brand getBrand() {
            return new Brand() {
                @Override
                public String getName() {
                    return "brand";
                }
            };
        }
    };

    private static final LocalDate s_Checkout = LocalDate.of(2024, 1, 1);

    private static final int s_Days = 10;

    private static final int s_Discount = 50;

    private static final ToolPriceDetails s_ToolDetails = new ToolPriceDetails() {
        @Override
        public Tool getTool() {
            return s_Tool1;
        }

        @Override
        public int getRentalDays() {
            return s_Days;
        }

        @Override
        public LocalDate getCheckoutDate() {
            return s_Checkout;
        }

        @Override
        public LocalDate getDueDate() {
            return getCheckoutDate().plusDays(s_ToolDetails.getRentalDays() - 1);
        }

        @Override
        public int getDailyRentalCharge() {
            return 3;
        }

        @Override
        public int getChargeDays() {
            return s_Days / 2;
        }

        @Override
        public int getPreDiscountCharge() {
            return 456;
        }

        @Override
        public int getDiscountPercent() {
            return 37;
        }

        @Override
        public int getDiscountAmount() {
            return 678;
        }

        @Override
        public int getFinalCharge() {
            return 321;
        }
    };

    private static int s_CallOrder = 0;

    private static class FakeToolService implements ToolService {

        private int m_CallOrder;

        public FakeToolService() {
            m_CallOrder = 0;
        }

        @Override
        public Tool getTool(String pCode) {
            m_CallOrder = ++s_CallOrder;
            return s_Tool1;

        }

        int getCallOrder() {
            return m_CallOrder;
        }
    }

    private static class FakePriceService implements PriceService {

        private int m_CallOrder;

        public FakePriceService() {
            m_CallOrder = 0;
        }

        @Override
        public ToolPriceDetails getPrice(Tool pTool, LocalDate pCheckoutDate, int pRentalDays, int pClerkDiscount) {

            m_CallOrder = ++s_CallOrder;

            assertEquals(s_Tool1, pTool);
            assertEquals(s_Checkout, pCheckoutDate);
            assertEquals(s_Days, pRentalDays);
            assertEquals(s_Discount, pClerkDiscount);

            return s_ToolDetails;
        }

        int getCallOrder() {
            return m_CallOrder;
        }
    }

    private static class FakeAgreementService implements AgreementService {

        private int m_CallOrder;
        public FakeAgreementService() {
            m_CallOrder = 0;
        }

        @Override
        public void generateAgreement(ToolPriceDetails pToolPriceDetails) {

            m_CallOrder = ++s_CallOrder;
            /*
            Just want to make sure it's the same instance. No need to check
            the individual fields on this type
             */
            assertEquals(s_ToolDetails, pToolPriceDetails);
        }

        int getCallOrder() {
            return m_CallOrder;
        }
    }

    private static final FakeToolService s_ToolService = new FakeToolService();
    private static final FakePriceService s_PriceService = new FakePriceService();
    private static final FakeAgreementService s_AgreementService = new FakeAgreementService();

    private static final RentalModule s_RentalModule = new RentalModule(s_ToolService, s_PriceService, s_AgreementService);


    public RentalModuleTest() {

    }

    /**
     * Just test to ensure the {@link RentalModule} is calling things in the correct order and is
     * passing the correct data. Detailed validation of the funtion of each of the services is
     * not done here.
     */
    @Test
    public void testRentalModule() {

        s_RentalModule.generateAgreement(s_Tool1.getCode(), s_Checkout, s_Days, s_Discount);

        assertEquals(1, s_ToolService.getCallOrder());
        assertEquals(2, s_PriceService.getCallOrder());
        assertEquals(3, s_AgreementService.getCallOrder());
    }
}
