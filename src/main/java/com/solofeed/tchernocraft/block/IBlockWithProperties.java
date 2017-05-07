package com.solofeed.tchernocraft.block;

import net.minecraft.item.ItemStack;

/**
 * Created by Solofeed on 06/05/2017.
 */
public interface IBlockWithProperties {
    String getVariantName(ItemStack stack);
}
