package com.solofeed.tchernocraft.block.blocks;

import com.solofeed.tchernocraft.block.ITchernocraftBlock;
import com.solofeed.tchernocraft.block.ModBlock;
import com.solofeed.tchernocraft.constant.HarvestLevel;
import com.solofeed.tchernocraft.constant.Tool;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

@ModBlock
public class Scaffolding extends Block implements ITchernocraftBlock {
    public final static String NAME = "scaffolding";
    private final static Material material = Material.IRON;

    @Override
    public String getName(){
    	return NAME;
    }
    
    public Scaffolding() {
        super(material);

        setHardness(10.0f);
        setSoundType(SoundType.METAL);
        setHarvestLevel(Tool.PICKAXE, HarvestLevel.DIAMOND);
        setResistance(50.0f);
        setLightOpacity(4);
    }

    @Override
    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return false;
    }
}
