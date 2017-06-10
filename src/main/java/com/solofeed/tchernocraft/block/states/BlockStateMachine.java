package com.solofeed.tchernocraft.block.states;

import com.solofeed.tchernocraft.core.BaseTierEnum;
import com.solofeed.tchernocraft.core.RecipeTypeEnum;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

/**
 * Created by Louis-Marie on 18/05/2017.
 */
public class BlockStateMachine  extends ExtendedBlockState{
    public static final PropertyBool PROPERTY_ACTIVE = PropertyBool.create("active");
    public static final PropertyEnum<BaseTierEnum> PROPERTY_TIER = PropertyEnum.create("tier", BaseTierEnum.class);
    public static final PropertyEnum<RecipeTypeEnum> PROPERTY_RECIPE = PropertyEnum.create("recipe", RecipeTypeEnum.class);

    public BlockStateMachine(Block block, PropertyEnum typeProperty) {
        super(block, new IProperty[]{ typeProperty, PROPERTY_ACTIVE, PROPERTY_TIER, PROPERTY_RECIPE }, new IUnlistedProperty[] {});
    }
}
