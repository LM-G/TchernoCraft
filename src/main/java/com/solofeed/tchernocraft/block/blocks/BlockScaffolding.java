package com.solofeed.tchernocraft.block.blocks;

import com.solofeed.tchernocraft.block.ITchernocraftBlock;
import com.solofeed.tchernocraft.block.TchernocraftBlock;
import com.solofeed.tchernocraft.constant.HarvestLevel;
import com.solofeed.tchernocraft.constant.Tool;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * {@see http://greyminecraftcoder.blogspot.fr/2013/07/rendering-transparent-blocks.html}
 */
@TchernocraftBlock
public class BlockScaffolding extends Block implements ITchernocraftBlock{
    public final static String NAME = "scaffolding";
    private final static Material material = Material.IRON;

    public BlockScaffolding() {
        super(material);

        setHardness(10.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel(Tool.PICKAXE, HarvestLevel.DIAMOND);
        setResistance(50.0f);
        setLightOpacity(4);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer(){
        return BlockRenderLayer.CUTOUT ;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state){
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        // gets the neighboor blockstate following the given side
        IBlockState sideBlockState = blockAccess.getBlockState(pos.offset(side));
        // gets the corresponding block
        Block sideBlock = iblockstate.getBlock();

        // we render the side only if the next block is not another scaffholding block
        return !Objects.equals(this, sideBlock);
    }
}
