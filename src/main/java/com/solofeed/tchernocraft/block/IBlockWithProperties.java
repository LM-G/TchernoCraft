package com.solofeed.tchernocraft.block;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 * ModBlock with custom meta data
 */
public interface IBlockWithProperties {
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
