package com.solofeed.tchernocraft.util;

import com.solofeed.tchernocraft.Tchernocraft;
import org.apache.commons.lang3.StringUtils;

import net.minecraft.creativetab.CreativeTabs;

/**
 * Global Handler
 */
public class TchernocraftHandler {


	/**
	 * Return the CreativeTabs associated with the specified name
	 * @param creativeTabName
	 * @return
	 */
	public static CreativeTabs getCreativeTab(String creativeTabName) {
        CreativeTabs creativeTab = Tchernocraft.creativeTabs.stream()
                .filter(t -> StringUtils.equals(t.getTabLabel(), creativeTabName))
                .findFirst().orElse(null);
        return creativeTab == null ? Tchernocraft.creativeTab : creativeTab;
	}
}
