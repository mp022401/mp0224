package mp0224.rental.tool.inline;

import mp0224.rental.tool.Tool;
import mp0224.rental.tool.ToolNotFoundException;
import mp0224.rental.tool.ToolService;

import java.util.Map;

/**
 * Implements the {@link ToolService} and simply uses the {@link Map} type as a tool code / tool reference.
 * Whilst this {@link Map} can be implemented by a simple look up map (eg. {@link java.util.HashMap}, there is no
 * reason why the map cannot be implemented by something that points to a database.
 */
public class InlineToolService implements ToolService {

    /**
     * Map of tool codes and their corresponding tool
     */
    private final Map<String, Tool> m_ToolMap;

    public InlineToolService(Map<String, Tool> pToolMap) {

        m_ToolMap = pToolMap;
    }


    /**
     * Retrieve the tool from the map, throw a {@link ToolNotFoundException} if it not found
     * @param pCode
     * @return
     */
    @Override
    public Tool getTool(String pCode) {

        Tool lclReturn = m_ToolMap.get(pCode);

        if (lclReturn == null) {
            throw new ToolNotFoundException(pCode);
        } else {
            return lclReturn;
        }
    }
}
