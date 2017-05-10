package com.solofeed.tchernocraft.block.blocks;

import com.solofeed.tchernocraft.Tchernocraft;
import com.solofeed.tchernocraft.block.ITchernocraftBlock;
import com.solofeed.tchernocraft.block.TchernocraftBlock;
import com.solofeed.tchernocraft.constant.HarvestLevel;
import com.solofeed.tchernocraft.constant.Tool;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

@TchernocraftBlock
public class RedstoniumOre extends Block implements ITchernocraftBlock{
    private final static Material material = Material.ROCK;
    /**
     * Block's name
     */
    public final static String NAME = "redstonium_ore";

    public RedstoniumOre() {
        super(material);
        // it's a really solid block
        setHardness(25.0f);
        setResistance(40.0f);
        setHarvestLevel(Tool.PICKAXE, HarvestLevel.DIAMOND);
        setSoundType(SoundType.STONE);
        // it emits a little bit of light due to small radiations
        setLightLevel(0.05f);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
