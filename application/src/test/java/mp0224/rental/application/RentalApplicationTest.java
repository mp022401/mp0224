package mp0224.rental.application;

import mp0224.rental.core.LineWriter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RentalApplicationTest {

    private static String[] s_ExpectedOutput = new String[]{
            "Tool code: CHNS",
            "Tool type: Chainsaw",
            "Tool brand: Stihl",
            "Rental days: 6,465",
            "Check out date: 4/3/24",
            "Due date: 12/14/41",
            "Daily rental charge: $1.49",
            "Charge days: 4,618",
            "Pre-discount charge: $6,880.82",
            "Discount percent: 51%",
            "Discount amount: $3,509.22",
            "Final charge: $3,371.60"
    };

    private static class StringBuilderLineWriter implements LineWriter {
        private final StringBuilder m_Builder;
        private String m_NewLine;
        public StringBuilderLineWriter(StringBuilder pBuilder) {

            m_Builder = pBuilder;
            m_NewLine = "";
        }

        @Override
        public void println(String pText) {
            m_Builder.append(m_NewLine);
            m_Builder.append(pText);
            m_NewLine = "\n";
        }

        @Override
        public String toString() {
            return m_Builder.toString();
        }
    }

    private final static String s_Help = "Parameters: Tool Code (string), Checkout Date (string - MM/DD/YYYY), Rental Days (int), Discount% (int)";

    public RentalApplicationTest() {

    }

    @Test
    public void testExceptions() {

        assertThrows(InsufficentNumberOfArgumentsException.class, ()-> RentalApplication.main(new String[]{"1"}));
        assertThrows(InsufficentNumberOfArgumentsException.class, ()-> RentalApplication.main(new String[]{"1", "2", "3", "4", "5", "6"}));

        assertThrows(DateFormatException.class, ()-> RentalApplication.main(new String[]{"toolcode", "asdsada", "3", "4"}));
        assertThrows(RentalDaysFormatException.class, ()-> RentalApplication.main(new String[]{"toolcode", "4/3/2024", "abc", "4"}));
        assertThrows(DiscountFormatException.class, ()-> RentalApplication.main(new String[]{"toolcode", "4/3/2024", "23", "a"}));
    }

    @Test
    public void testApplication() {

        StringBuilder lclOutput = new StringBuilder();

        RentalApplication.setLineWriter(new StringBuilderLineWriter(lclOutput));

        RentalApplication.main(new String[]{"CHNS", "4/3/2024", "6465", "51"});

        String[] lclEntries = lclOutput.toString().split("\n");

        assertEquals(s_ExpectedOutput.length, lclEntries.length);

        for (int i = 0; i < s_ExpectedOutput.length; i++) {
            assertEquals(s_ExpectedOutput[i], lclEntries[i]);
        }

    }

    @Test
    public void testApplicationHelp() {

        assertThrows(RuntimeException.class, () -> new RentalApplication(new String[]{}) {

            @Override
            protected void printHelp() {
                super.printHelp(); // helps with coverage
                throw new RuntimeException("print");
            }
        });
    }
}
