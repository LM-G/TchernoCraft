package com.solofeed.tchernocraft.proxy;

import com.solofeed.tchernocraft.block.BlockHandler;
import com.solofeed.tchernocraft.crafting.CraftingManager;
import com.solofeed.tchernocraft.item.ItemHandler;
import com.solofeed.tchernocraft.tileentity.TileEntityHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


public class ServerProxy implements IProxy{
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        ItemHandler.registerItems();
        BlockHandler.registerBlocks();
        TileEntityHandler.registerTileEntities();
    }
    @Override
    public void init(FMLInitializationEvent event) {
        CraftingManager.registerRecipes();
    }
    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }
}
