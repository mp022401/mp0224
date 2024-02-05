package mp0224.rental.tool.inline;

import mp0224.rental.tool.Brand;

/**
 * {@link Brand} types can exist in other enumerations or even populated by persistent sources (so long as those
 * sources fulfill the interface contract defined in {@link Brand}).
 */
public enum BrandEnum implements Brand {

    STIHL, RIDGID, DEWALT("DeWalt"), WERNER;

    private final String m_Name;

    BrandEnum() {
        this(null);
    }

    BrandEnum(String pName) {

        if (pName != null) {
            m_Name = pName;
        } else {
            m_Name = name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
        }
    }


    @Override
    public String getName() {
        return m_Name;
    }
}
