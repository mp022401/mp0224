package mp0224.rental.agreement.inline;

import mp0224.rental.agreement.AgreementService;
import mp0224.rental.core.LineWriter;
import mp0224.rental.price.ToolPriceDetails;
import mp0224.rental.tool.Brand;
import mp0224.rental.tool.Tool;
import mp0224.rental.tool.ToolType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LineWriterAgreementServiceTest {

    private enum ToolTypes implements ToolType {
        TOOL_TYPE_1
    }

    private static Tool s_Tool = new Tool() {
        @Override
        public String getCode() {
            return "toolcode1";
        }

        @Override
        public ToolType getType() {

            return ToolTypes.TOOL_TYPE_1;
        }

        @Override
        public Brand getBrand() {
            return new Brand() {
                @Override
                public String getName() {
                    return "brand1";
                }
            };
        }
    };

    private static class LineWriterPlaceholder implements LineWriter {

        private String m_NewLine;
        private StringBuilder m_Writer;

        public LineWriterPlaceholder() {

            m_NewLine = "";
        }

        private void setWriter(StringBuilder pBuilder) {
            m_Writer = pBuilder;
        }


        @Override
        public void println(String pText) {

            m_Writer.append(m_NewLine);
            m_Writer.append(pText);
            m_NewLine  = "\n";
        }

        @Override
        public String toString() {
            return m_Writer.toString();
        }
    }

    private final static LineWriterPlaceholder s_LineWriterPlaceHolder = new LineWriterPlaceholder();

    private static final AgreementService s_AgreementService = new LineWriterAgreementService(s_LineWriterPlaceHolder);

    private static final ToolPriceDetails s_ToolPriceDetails = new ToolPriceDetails() {
        @Override
        public Tool getTool() {
            return s_Tool;
        }

        @Override
        public int getRentalDays() {
            return 9998;
        }

        @Override
        public LocalDate getCheckoutDate() {
            return LocalDate.of(2024, 1, 1);
        }

        @Override
        public LocalDate getDueDate() {
            return LocalDate.of(2024, 1, 2);
        }

        @Override
        public int getDailyRentalCharge() {
            return 56;
        }

        @Override
        public int getChargeDays() {
            return 5557;
        }

        @Override
        public int getPreDiscountCharge() {
            return 1234;
        }

        @Override
        public int getDiscountPercent() {
            return 37;
        }

        @Override
        public int getDiscountAmount() {
            return 5432;
        }

        @Override
        public int getFinalCharge() {
            return 2468;
        }
    };

    private static String[] s_ExpectedOutput = new String[]{
            "Tool code: toolcode1",
            "Tool type: Tool_type_1",
            "Tool brand: brand1",
            "Rental days: 9,998",
            "Check out date: 1/1/24",
            "Due date: 1/2/24",
            "Daily rental charge: $0.56",
            "Charge days: 5,557",
            "Pre-discount charge: $12.34",
            "Discount percent: 37%",
            "Discount amount: $54.32",
            "Final charge: $24.68"
    };

    public LineWriterAgreementServiceTest() {

    }

    @Test
    public void testLineWriter() {

        StringBuilder lclOutput = new StringBuilder();

        s_LineWriterPlaceHolder.setWriter(lclOutput);

        s_AgreementService.generateAgreement(s_ToolPriceDetails);

        String[] lclEntries = s_LineWriterPlaceHolder.toString().split("\n");

        assertEquals(s_ExpectedOutput.length, lclEntries.length);

        for (int i = 0; i < s_ExpectedOutput.length; i++) {

            assertEquals(s_ExpectedOutput[i], lclEntries[i]);
        }

    }
}
