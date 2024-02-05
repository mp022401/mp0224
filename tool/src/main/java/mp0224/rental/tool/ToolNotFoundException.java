package mp0224.rental.tool;

/**
 * This is thrown if a provided tool code does not have any tool {@link Tool} associated with it
 */
public class ToolNotFoundException extends ToolException {

    public ToolNotFoundException(String pToolCode) {
        super(pToolCode);
    }
}
