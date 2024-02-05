package mp0224.rental.agreement;

import mp0224.rental.price.ToolPriceDetails;

/**
 * Renders the agreement
 */
public interface AgreementService {

    void generateAgreement(ToolPriceDetails pToolPriceDetails);
}
