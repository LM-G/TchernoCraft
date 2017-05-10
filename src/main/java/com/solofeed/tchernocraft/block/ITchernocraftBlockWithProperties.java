package com.solofeed.tchernocraft.block;

import com.solofeed.tchernocraft.constant.IBlockType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Tchernocraft Block with custom properties
 */
public interface ITchernocraftBlockWithProperties extends ITchernocraftBlock{
    /**
     * Gets the corresponding item block
     * @return item block
     */
    ItemBlock getItemBlock();

    /**
     * Gets the variant matching block's item stack name
     * @param stack item stack matching the item block
     * @return variant name
     */
    String getVariantName(ItemStack stack);
}
