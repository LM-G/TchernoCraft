package com.solofeed.tchernocraft.block.blocks;

import com.solofeed.tchernocraft.block.TchernocraftBlock;
import com.solofeed.tchernocraft.constant.HarvestLevel;
import com.solofeed.tchernocraft.constant.Tool;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

@TchernocraftBlock(name = RedstoniumOre.NAME)
public class RedstoniumOre extends Block{
    public final static String NAME = "redstonium_ore";
    private final static Material material = Material.ROCK;

    public RedstoniumOre() {
        super(material);

        setHardness(25.0f);
        setSoundType(SoundType.GROUND);
        setLightLevel(0.75f);
        setHarvestLevel(Tool.PICKAXE, HarvestLevel.DIAMOND);
        setResistance(40.0f);
    }
}
