package mp0224.rental.price;

import mp0224.rental.tool.ToolType;

/**
 * This is thrown if a supplied tool {@link mp0224.rental.tool.Tool) (or rather its {@link ToolType}) does
 * not have a default price associated with it.
 */
public class DefaultPriceNotFoundException extends PriceException {

    public DefaultPriceNotFoundException(ToolType pToolType) {

        super("Default price not found for tool type (" + pToolType + ")");
    }
}
