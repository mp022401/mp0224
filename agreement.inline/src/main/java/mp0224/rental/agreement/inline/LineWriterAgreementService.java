package mp0224.rental.agreement.inline;

import mp0224.rental.agreement.AgreementService;
import mp0224.rental.core.LineWriter;
import mp0224.rental.price.ToolPriceDetails;
import mp0224.rental.tool.Tool;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

/**
 * Implementation of the {@link AgreementService} that prints out the agreement to the provided {@link LineWriter}
 * supplied via the constructor {@link #LineWriterAgreementService(LineWriter)}
 *
 * By providing the {@link LineWriter} via the constructor, the {@link AgreementService#generateAgreement(ToolPriceDetails)} method
 * becomes completely rendering agnostic and as such does not enforce a rendering style that requires repeated calls
 * to a println method.
 */
public class LineWriterAgreementService implements AgreementService {

    private final LineWriter m_LineWriter;

    public LineWriterAgreementService(LineWriter pWriter) {

        m_LineWriter = pWriter;
    }

    @Override
    public void generateAgreement(ToolPriceDetails pToolPriceDetails) {

        Tool lclTool = pToolPriceDetails.getTool();
        String lclType = lclTool.getType().toString();

        println("Tool code", lclTool.getCode());
        println("Tool type", (lclType.length() > 1 ? lclType.substring(0, 1).toUpperCase() + lclType.substring(1).toLowerCase() : lclType.toUpperCase()));
        println("Tool brand", lclTool.getBrand().getName());
        println("Rental days", formatNumber(pToolPriceDetails.getRentalDays()));
        println("Check out date", formatDate(pToolPriceDetails.getCheckoutDate()));
        println("Due date", formatDate(pToolPriceDetails.getDueDate()));
        println("Daily rental charge", formatCurrency(pToolPriceDetails.getDailyRentalCharge()));
        println("Charge days", formatNumber(pToolPriceDetails.getChargeDays()));
        println("Pre-discount charge", formatCurrency(pToolPriceDetails.getPreDiscountCharge()));
        println("Discount percent", formatPercent(pToolPriceDetails.getDiscountPercent()));
        println("Discount amount", formatCurrency(pToolPriceDetails.getDiscountAmount()));
        println("Final charge", formatCurrency(pToolPriceDetails.getFinalCharge()));


    }

    private void println(String pAttribute, String pValue) {
        m_LineWriter.println(pAttribute + ": " + pValue);
    }

    private String formatNumber(int pNumber) {
        return NumberFormat.getInstance().format(pNumber);
    }

    private String formatDate(LocalDate pDate) {

        return pDate.getMonthValue() + "/" + pDate.getDayOfMonth() + "/" + (pDate.getYear() + "").substring(2);
    }

    /**
     * Currencies are in whole number of cents.
     *
     * @param pValue
     * @return
     */
    private String formatCurrency(int pValue) {

        int lclDollars = pValue / 100;
        int lclCents = pValue % 100;

        String lclText = lclDollars + "." + (lclCents < 10 ? "0" + lclCents : lclCents);

        // Still need commas for 1,000s
        return NumberFormat.getCurrencyInstance().format(Double.parseDouble(lclText));

    }

    private String formatPercent(int pValue) {
        return pValue + "%";
    }
}
