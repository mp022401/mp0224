package mp0224.rental.price.inline;

import mp0224.rental.price.PeriodicDiscount;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class AggregatePeriodicDiscount implements PeriodicDiscount {

    private final PeriodicDiscount[] m_Discounts;

    public AggregatePeriodicDiscount(PeriodicDiscount... pDiscounts) {

        m_Discounts = pDiscounts != null && pDiscounts.length > 0 ? pDiscounts : new PeriodicDiscount[]{new NoDiscount()};

    }

    @Override
    public List<LocalDate> getDiscountDates(LocalDate pStart, LocalDate pEnd) {

        List<LocalDate> lclReturn = null;

        for (PeriodicDiscount lclDiscount : m_Discounts) {

            if (lclReturn == null) {

                lclReturn = lclDiscount.getDiscountDates(pStart, pEnd);

            } else {

                for (LocalDate lclDate : lclDiscount.getDiscountDates(pStart, pEnd)) {

                    if (!lclReturn.contains(lclDate)) {
                        lclReturn.add(lclDate);
                    }
                }

            }

        }

        Collections.sort(lclReturn);

        return lclReturn;
    }
}
