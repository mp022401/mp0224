package mp0224.rental.tool.inline;

import mp0224.rental.tool.Brand;
import mp0224.rental.tool.Tool;
import mp0224.rental.tool.ToolType;

/**
 * Simply implements the tool {@link Tool} interface and its attributes
 */
public class InlineTool implements Tool {

    private final String m_Code;
    private final ToolType m_Type;
    private final Brand m_Brand;

    public InlineTool(String pCode, ToolType pType, Brand pBrand) {

        m_Code = pCode;
        m_Type = pType;
        m_Brand = pBrand;
    }
    @Override
    public String getCode() {
        return m_Code;
    }

    @Override
    public ToolType getType() {
        return m_Type;
    }

    @Override
    public Brand getBrand() {
        return m_Brand;
    }
}
