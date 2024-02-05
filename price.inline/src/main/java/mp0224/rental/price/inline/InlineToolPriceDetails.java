package mp0224.rental.price.inline;

import mp0224.rental.price.ToolPriceDetails;
import mp0224.rental.tool.Tool;

import java.time.LocalDate;


class InlineToolPriceDetails implements ToolPriceDetails {

    private final Tool m_Tool;

    private final int m_RentalDays;

    private final LocalDate m_CheckoutDate;
    private final LocalDate m_DueDate;

    private final int m_DailyRentalCharge;

    private final int m_ChargeDays;

    private final int m_DiscountPercent;

    InlineToolPriceDetails(Tool pTool,
                           int pRentalDays,
                           LocalDate pCheckoutDate,
                           LocalDate pDueDate,
                           int pDailyRentalCharge,
                           int pChargeDays,
                           int pDiscountPercent) {

        m_Tool = pTool;
        m_RentalDays = pRentalDays;
        m_CheckoutDate = pCheckoutDate;
        m_DueDate = pDueDate;
        m_DailyRentalCharge = pDailyRentalCharge;
        m_ChargeDays = pChargeDays;
        m_DiscountPercent = pDiscountPercent;

    }

    @Override
    public Tool getTool() {
        return m_Tool;
    }

    @Override
    public int getRentalDays() {
        return m_RentalDays;
    }

    @Override
    public LocalDate getCheckoutDate() {
        return m_CheckoutDate;
    }

    @Override
    public LocalDate getDueDate() {
        return m_DueDate;
    }

    @Override
    public int getDailyRentalCharge() {
        return m_DailyRentalCharge;
    }

    @Override
    public int getChargeDays() {
        return m_ChargeDays;
    }


    @Override
    public int getDiscountPercent() {
        return m_DiscountPercent;
    }

}
