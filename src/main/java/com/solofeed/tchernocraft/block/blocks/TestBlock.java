package com.solofeed.tchernocraft.block.blocks;

import com.solofeed.tchernocraft.block.IBlockWithProperties;
import com.solofeed.tchernocraft.block.TchernocraftBlock;
import com.solofeed.tchernocraft.constant.IBlockType;
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
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Solofeed on 07/05/2017.
 * For testing purpose
 */
@TchernocraftBlock(name = TestBlock.NAME)
public class TestBlock extends Block implements IBlockWithProperties {
    public final static String NAME = "test_block";

    private final static Material MATERIAL = Material.ROCK;
    private final static IProperty<EnumType> TYPE = PropertyEnum.create("type", EnumType.class);

    public TestBlock() {
        super(MATERIAL);
        setSoundType(SoundType.METAL);
        this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, EnumType.VARIANT_A));
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        super.onBlockAdded(worldIn, pos, state);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{TYPE});
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE).getMeta();
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(TYPE, EnumType.values()[meta]);
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
    public ItemBlock getItemBlock() {
        return new ItemMultiTexture(this, this, this::getVariantName);
    }

    @Override
    public String getVariantName(ItemStack stack) {
        return TYPE.getAllowedValues().stream()
                .filter(e -> e.getMeta() == stack.getMetadata())
                .findFirst()
                .orElseThrow(NullPointerException::new)
                .getName();
    }

    @Override
    public List<IProperty<EnumType>> getProperties() {
        return Arrays.asList(TYPE);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(Item.getItemFromBlock(this), 1, getMetaFromState(world.getBlockState(pos)));
    }

    public enum EnumType implements IBlockType {
        VARIANT_A(0, "a"),
        VARIANT_B(1, "b");

        private final int meta;
        private final String name;

        EnumType(int meta, String name) {
            this.meta = meta;
            this.name = name;
        }

        @Override
        public int getMeta() {
            return meta;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
