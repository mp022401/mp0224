package mp0224.rental.tool;

/**
 * A service for looking up a Tool {@link Tool} based on the code provided
 */
public interface ToolService {

    Tool getTool(String pCode);
}
