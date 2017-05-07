package com.solofeed.tchernocraft.block.blocks;

import com.solofeed.tchernocraft.block.BlockWithProperties;
import com.solofeed.tchernocraft.block.TchernocraftBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * Created by Solofeed on 07/05/2017.
 * For testing purpose
 */
@TchernocraftBlock(name = TestBlock.NAME)
public class TestBlock extends Block implements BlockWithProperties {
    public final static String NAME = "test_block";

    private final static Material MATERIAL = Material.ROCK;
    private final static IProperty<EnumType> STATE = PropertyEnum.create("state", EnumType.class);

    public TestBlock() {
        super(MATERIAL);
        setSoundType(SoundType.GROUND);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{ STATE });
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(STATE).getMeta();
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list) {
        Arrays.stream(EnumType.values())
                .forEach(enumType -> list.add(new ItemStack(this, 1, enumType.getMeta())));
    }

    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
        super.onBlockClicked(worldIn, pos, playerIn);
    }

    @Override
    public String getVariantName(ItemStack stack) {
        return EnumType.getByMeta(stack.getMetadata()).getName();
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(Item.getItemFromBlock(this), 1, getMetaFromState(world.getBlockState(pos)));
    }

    public enum EnumType implements IStringSerializable{
        VARIANT_A(0, "a"),
        VARIANT_B(1, "b");

        private static final EnumType[] META_LOOKUP = Stream.of(values())
                .sorted(Comparator.comparing(EnumType::getMeta))
                .toArray(EnumType[]::new);

        private final int meta;
        private final String name;

        EnumType(int meta, String name) {
            this.meta = meta;
            this.name = name;
        }

        public int getMeta() {
            return meta;
        }

        @Override
        public String getName() {
            return name;
        }

        public static EnumType getByMeta(int meta) {
            if (meta < 0 || meta >= META_LOOKUP.length) {
                meta = 0;
            }

            return META_LOOKUP[meta];
        }
    }
}
