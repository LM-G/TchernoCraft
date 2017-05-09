package com.solofeed.tchernocraft.block;

import com.solofeed.tchernocraft.constant.IBlockType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Block with custom meta data
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

    /**
     * Gets all properties defining all block metadata
     * @param <T>
     * @return
     */
    <T extends Enum<T> & IBlockType> List<IProperty<T>> getProperties();
}
