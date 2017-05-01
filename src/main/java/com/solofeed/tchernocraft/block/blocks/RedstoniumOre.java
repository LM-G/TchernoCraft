package com.solofeed.tchernocraft.block.blocks;

import com.solofeed.tchernocraft.block.OreBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

@OreBlock(RedstoniumOre.REGISTRY_NAME)
public class RedstoniumOre extends Block{
    private final static String NAME = "redstonium_dust";
    public final static String REGISTRY_NAME = "redstoniumdust";

    public RedstoniumOre(Material materialIn) {
        super(materialIn);
    }
}
