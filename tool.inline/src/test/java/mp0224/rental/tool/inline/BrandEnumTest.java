package mp0224.rental.tool.inline;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BrandEnumTest {

    @Test
    public void testBrandPositive() {

        assertEquals("DeWalt", BrandEnum.DEWALT.getName());
    }

}
