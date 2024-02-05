package mp0224.rental.price;

import mp0224.rental.tool.Tool;

import java.time.LocalDate;

/**
 * Provides all the pricing details necessary to render an agreement. Also includes some convenience default methods
 * for calculating derived values.
 */
public interface ToolPriceDetails {

    Tool getTool();

    /**
     * Rental days - Specified at checkout
     *
     * @return
     */
    int getRentalDays();

    /**
     * Check out date - Specified at checkout
     *
     * @return
     */
    LocalDate getCheckoutDate();

    /**
     * Due date - Calculated from checkout date and rental days.
     *
     * @return
     */
    LocalDate getDueDate();

    /**
     * Daily rental charge - Amount per day, specified by the tool type.
     *
     * @return
     */
    int getDailyRentalCharge();

    /**
     * Charge days - Count of chargeable days, from day after checkout through and including due
     * date, excluding “no charge” days as specified by the tool type.
     *
     * @return
     */
    int getChargeDays();

    /**
     * Pre-discount charge - Calculated as charge days X daily charge. Resulting total rounded half up
     * to cents.
     *
     * @return
     */
    default int getPreDiscountCharge() {
        return getDailyRentalCharge() * getChargeDays();
    }

    /**
     * Discount percent - Specified at checkout.
     *
     * @return
     */
    int getDiscountPercent();

    /**
     * Discount amount - calculated from discount % and pre-discount charge. Resulting amount
     * rounded half up to cents.
     *
     * @return
     */
    default int getDiscountAmount() {
        return (getPreDiscountCharge() * 10 * getDiscountPercent() / 100 + 5) / 10;
    }

    /**
     * Calculated as pre-discount charge - discount amount.
     *
     * @return
     */
    default int getFinalCharge() {
        return getPreDiscountCharge() - getDiscountAmount();
    }

}
