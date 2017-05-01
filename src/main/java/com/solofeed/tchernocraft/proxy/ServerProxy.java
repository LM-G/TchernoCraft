package com.solofeed.tchernocraft.proxy;

import com.solofeed.tchernocraft.block.BlockHandler;
import com.solofeed.tchernocraft.item.ItemHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


public class ServerProxy implements IProxy{
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        ItemHandler.init();
        BlockHandler.init();
    }
    @Override
    public void init(FMLInitializationEvent event) {

    }
    @Override
    public void postInit(FMLPostInitializationEvent event) {

    }
}
