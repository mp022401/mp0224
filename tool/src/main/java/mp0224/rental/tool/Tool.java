package mp0224.rental.tool;

/**
 * A tool available for rent
 */
public interface Tool {

    String getCode();

    ToolType getType();

    Brand getBrand();
}
