package com.solofeed.tchernocraft.block.blocks;

import com.solofeed.tchernocraft.block.ITchernocraftBlockWithProperties;
import com.solofeed.tchernocraft.block.TchernocraftBlock;
import com.solofeed.tchernocraft.constant.IBlockType;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.Arrays;

/**
 * Created by Solofeed on 07/05/2017.
 * For testing purpose
 */
@TchernocraftBlock
public class TestBlock extends Block implements ITchernocraftBlockWithProperties {
    public final static String NAME = "test_block";

    private final static Material MATERIAL = Material.ROCK;
    private final static IProperty<EnumType> TYPE = PropertyEnum.create("type", EnumType.class);
    private final static PropertyDirection FACING = PropertyDirection.create("facing");

    public TestBlock() {
        super(MATERIAL);
        setSoundType(SoundType.METAL);
        IBlockState state = this.blockState.getBaseState()
                .withProperty(TYPE, EnumType.VARIANT_A)
                .withProperty(FACING, EnumFacing.NORTH);
        this.setDefaultState(state);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState()
                .withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer))
                .withProperty(TYPE, getStateFromMeta(meta * EnumFacing.values().length).getValue(TYPE));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{TYPE, FACING});
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int typeMeta = state.getValue(TYPE).getMeta();
        int facingMeta = state.getValue(FACING).ordinal();
        return typeMeta * EnumFacing.values().length + facingMeta; //Stores the type the EnumFacing in the meta
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumType type = EnumType.values()[(meta / EnumFacing.values().length) % EnumType.values().length]; //Gets the type from the meta
        EnumFacing facing = EnumFacing.values()[meta % EnumFacing.values().length]; //Gets the EnumFacing from the meta
        return this.getDefaultState().withProperty(TYPE, type).withProperty(FACING, facing); //Returns the correct state
    }

    @Override
    public int damageDropped(IBlockState state) {
        return (getMetaFromState(state) / EnumFacing.values().length);
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
