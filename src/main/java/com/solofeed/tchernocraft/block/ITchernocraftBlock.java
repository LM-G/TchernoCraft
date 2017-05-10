package com.solofeed.tchernocraft.block;

import com.solofeed.tchernocraft.Tchernocraft;
import net.minecraft.creativetab.CreativeTabs;

/**
 * Tchernocraft's block interface. All mod's block implements this interface.
 */
public interface ITchernocraftBlock {
    /**
     * Get the block's name
     * @return the block's name
     */
    String getName();

    /**
     * Get the block's creative tab
     * @return the tab in which the block will be displayed in creative mod
     */
    default CreativeTabs getCreativeTabs(){
        return Tchernocraft.creativeTab;
    }
}
