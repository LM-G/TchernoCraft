package com.solofeed.tchernocraft.block.blocks;

import com.solofeed.tchernocraft.block.TchernocraftBlock;
import com.solofeed.tchernocraft.constant.HarvestLevel;
import com.solofeed.tchernocraft.constant.Tool;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

@TchernocraftBlock(name = RedstoniumOre.NAME)
public class RedstoniumOre extends Block {
    private final static Material material = Material.ROCK;

    public final static String NAME = "redstonium_ore";

    public RedstoniumOre() {
        super(material);
        setHardness(25.0f);
        setSoundType(SoundType.STONE);
        setLightLevel(0.1f);
        setLightOpacity(2);
        setHarvestLevel(Tool.PICKAXE, HarvestLevel.DIAMOND);
        setResistance(40.0f);
    }
}
