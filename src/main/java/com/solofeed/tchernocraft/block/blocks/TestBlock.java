package com.solofeed.tchernocraft.block.blocks;

import com.solofeed.tchernocraft.Tchernocraft;
import com.solofeed.tchernocraft.block.ITchernocraftBlockWithProperties;
import com.solofeed.tchernocraft.block.TchernocraftBlock;
import com.solofeed.tchernocraft.client.gui.TchernocraftGuis;
import com.solofeed.tchernocraft.constant.IBlockType;
import com.solofeed.tchernocraft.tileentity.tileentities.TestBlockTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
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
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Solofeed on 07/05/2017.
 * For testing purpose
 */
@TchernocraftBlock
public class TestBlock extends Block implements ITchernocraftBlockWithProperties, ITileEntityProvider {
    /** Block's registry name */
    public final static String NAME = "test_block";
    /** Block's material */
    private final static Material MATERIAL = Material.ROCK;
    /** Block's type */
    private final static IProperty<EnumType> TYPE = PropertyEnum.create("type", EnumType.class);
    /** Block's facing direction */
    private final static PropertyDirection FACING = PropertyDirection.create("facing");
    /** Block's possible state displayed in inventory */
    private static List<IBlockState> INVENTORY_STATES;
    /** TestBlock's constructor */
    public TestBlock() {
        super(MATERIAL);
        setSoundType(SoundType.METAL);
        // we set only north facing state for both types A and B as only states availble in inventory
        IBlockState INVENTORY_STATE_A = blockState.getBaseState().withProperty(TYPE, EnumType.VARIANT_A).withProperty(FACING, EnumFacing.NORTH);
        IBlockState INVENTORY_STATE_B = blockState.getBaseState().withProperty(TYPE, EnumType.VARIANT_B).withProperty(FACING, EnumFacing.NORTH);
        INVENTORY_STATES = Arrays.asList(INVENTORY_STATE_A, INVENTORY_STATE_B);
        setDefaultState(INVENTORY_STATE_A);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        // we determines the state in which the new block will be depending on the player orientation and the TYPE on the sub item block
        EnumFacing blockFacing = EnumFacing.getDirectionFromEntityLiving(pos, placer);
        EnumType blockType = TYPE.getAllowedValues().stream().filter(type -> type.getMeta() == meta / FACING.getAllowedValues().size()).findFirst().orElse(EnumType.VARIANT_A);
        return blockState.getBaseState().withProperty(FACING, blockFacing).withProperty(TYPE, blockType);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{TYPE, FACING});
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int typeMeta = state.getValue(TYPE).getMeta();
        int facingMeta = state.getValue(FACING).ordinal();
        return typeMeta * EnumFacing.values().length + facingMeta;
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumType type = EnumType.values()[(meta / EnumFacing.values().length) % EnumType.values().length];
        EnumFacing facing = EnumFacing.values()[meta % EnumFacing.values().length];
        IBlockState state = this.getDefaultState().withProperty(TYPE, type).withProperty(FACING, facing);
        return state;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list) {
        for(IBlockState inventoryState: INVENTORY_STATES){
            list.add(new ItemStack(this, 1, getMetaFromState(inventoryState)));
        }
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
        // variant name depends only on the block's type
        int typeMeta = stack.getMetadata() / FACING.getAllowedValues().size();
        return TYPE.getAllowedValues().stream()
                .filter(e -> e.getMeta() == typeMeta)
                .findFirst()
                .orElse(EnumType.VARIANT_A)
                .getName();
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        int meta = getMetaFromState(world.getBlockState(pos)) / FACING.getAllowedValues().size();
        IBlockState pickState = INVENTORY_STATES.stream().filter(s -> s.getValue(TYPE).getMeta() == meta).findFirst().orElse(INVENTORY_STATES.get(0));
        return new ItemStack(Item.getItemFromBlock(this), 1, getMetaFromState(pickState));
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TestBlockTileEntity();
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TestBlockTileEntity();
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TestBlockTileEntity tileEntity = (TestBlockTileEntity) worldIn.getTileEntity(pos);
        IItemHandler itemHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        for(int i =0; i < itemHandler.getSlots() - 1; i++){
            ItemStack stack = itemHandler.getStackInSlot(i);
            InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack);
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote){
            playerIn.openGui(Tchernocraft.INSTANCE, TchernocraftGuis.TEST_BLOCK, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
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
