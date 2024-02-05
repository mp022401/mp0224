package mp0224.rental.tool.inline;

import mp0224.rental.tool.Tool;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InlineToolTest {

    public static final String s_CODE = "code";

    @Test
    public void testTool() {

        Tool lclTool = new InlineTool(s_CODE, ToolTypeEnum.CHAINSAW, BrandEnum.DEWALT);

        assertEquals(s_CODE, lclTool.getCode());
        assertEquals(ToolTypeEnum.CHAINSAW, lclTool.getType());
        assertEquals(BrandEnum.DEWALT, lclTool.getBrand());
    }
}
