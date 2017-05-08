package com.solofeed.tchernocraft.block.blocks;

import com.google.auto.service.AutoService;
import com.solofeed.tchernocraft.block.ITchernocraftBlock;
import com.solofeed.tchernocraft.constant.HarvestLevel;
import com.solofeed.tchernocraft.constant.Tool;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

@AutoService(ITchernocraftBlock.class)
public class RedstoniumOre extends Block implements ITchernocraftBlock {
    private final static Material material = Material.ROCK;

    public final static String NAME = "redstonium_ore";

    @Override
    public String getName(){
    	return NAME;
    }
    
    public RedstoniumOre() {
        super(material);
        setHardness(25.0f);
        setSoundType(SoundType.GROUND);
        setLightLevel(0.1f);
        setLightOpacity(2);
        setHarvestLevel(Tool.PICKAXE, HarvestLevel.DIAMOND);
        setResistance(40.0f);
    }
}
