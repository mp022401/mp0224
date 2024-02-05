package mp0224.rental.tool.inline;

import mp0224.rental.tool.Tool;
import mp0224.rental.tool.ToolNotFoundException;
import mp0224.rental.tool.ToolService;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class InlineToolServiceTest {

    private final static String s_CHNS = "CHNS";
    private final static String s_LADW = "LADW";
    private final static String s_JAKD = "JAKD";
    private final static String s_JAKR = "JAKR";


    private final static Map<String, Tool> s_ToolMap = new HashMap<>() {
        {

            put(s_CHNS, new InlineTool(s_CHNS, ToolTypeEnum.CHAINSAW, BrandEnum.STIHL));
            put(s_LADW, new InlineTool(s_LADW, ToolTypeEnum.LADDER, BrandEnum.WERNER));
            put(s_JAKD, new InlineTool(s_JAKD, ToolTypeEnum.JACKHAMMER, BrandEnum.DEWALT));
            put(s_JAKR, new InlineTool(s_JAKR, ToolTypeEnum.JACKHAMMER, BrandEnum.RIDGID));

        }
    };



    private static final ToolService s_ToolService = new InlineToolService(s_ToolMap);


    @Test
    public void testToolFound() {

        assertNotNull(s_ToolService.getTool(s_CHNS));

    }

    @Test
    public void testToolNotFound() {

        assertThrows(ToolNotFoundException.class, () -> s_ToolService.getTool("asdasdasdasdasd"));
    }

    @Test
    public void testBrandName() {

        assertEquals(BrandEnum.STIHL, s_ToolMap.get(s_CHNS).getBrand());
    }


    @Test
    public void testToolTypeChainsaw() {
        assertEquals(ToolTypeEnum.CHAINSAW, s_ToolMap.get(s_CHNS).getType());
    }

    @Test
    public void testToolTypeJackhammer() {
        assertEquals(ToolTypeEnum.JACKHAMMER, s_ToolMap.get(s_JAKD).getType());
    }

}
