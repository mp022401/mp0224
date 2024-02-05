package mp0224.rental.tool.inline;

import mp0224.rental.tool.ToolType;

/**
 * {@link ToolType} types can exist in other enumerations or even populated by persistent sources (so long as those
 * sources fulfill the interface contract defined in {@link ToolType}).
 */
public enum ToolTypeEnum implements ToolType {

    CHAINSAW, LADDER, JACKHAMMER
}
