package com.solofeed.tchernocraft.util;

import com.solofeed.tchernocraft.Tchernocraft;
import net.minecraft.creativetab.CreativeTabs;
import org.apache.commons.lang3.StringUtils;

/**
 * Global utilitary
 */
public final class TchernocraftUtils {
    /**
     * Private constructor
     */
    private TchernocraftUtils(){
        throw new UnsupportedOperationException("TchernocraftUtils class should never be instanciated");
    }

    /**
     * Get the matching creative tab
     * @param name creative tab's nanme
     * @return {@link CreativeTabs} the creative tab
     */
    public static CreativeTabs getCreativeTabs(String name){
        return Tchernocraft.creativeTabs.stream()
                .filter(t -> StringUtils.equals(t.getTabLabel(),name))
                .findFirst().orElse(null);
    }
}
