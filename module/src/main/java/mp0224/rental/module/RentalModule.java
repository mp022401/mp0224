package mp0224.rental.module;

import mp0224.rental.agreement.AgreementService;
import mp0224.rental.price.PriceService;
import mp0224.rental.price.ToolPriceDetails;
import mp0224.rental.tool.Tool;
import mp0224.rental.tool.ToolService;

import java.time.LocalDate;

/**
 * The 'module' class represents the highest level of business flow orchestration. It only deals with interfaces
 * the implementation of which has been instantiated by the caller.
 */
public class RentalModule {

    private final ToolService m_ToolService;
    private final PriceService m_PriceService;
    private final AgreementService m_AgreementService;

    public RentalModule(ToolService pToolService, PriceService pPriceSerive, AgreementService pAgreementService) {

        m_ToolService = pToolService;
        m_PriceService = pPriceSerive;
        m_AgreementService = pAgreementService;
    }

    /**
     * Since the module only deals with abstrations, the logic here is now quite simple.
     *
     * @param pToolCode
     * @param pCheckoutDate
     * @param pDays
     * @param pDiscount
     */
    public void generateAgreement(String pToolCode, LocalDate pCheckoutDate, int pDays, int pDiscount) {

        Tool lclTool = m_ToolService.getTool(pToolCode);

        ToolPriceDetails lclPriceDetails = m_PriceService.getPrice(lclTool, pCheckoutDate, pDays, pDiscount);

        m_AgreementService.generateAgreement(lclPriceDetails);
    }
}
