package com.solofeed.tchernocraft.proxy;

import com.solofeed.tchernocraft.Tchernocraft;
import com.solofeed.tchernocraft.block.BlockHandler;
import com.solofeed.tchernocraft.client.gui.GuiHandler;
import com.solofeed.tchernocraft.item.ItemHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

/**
 * Created by Louis-Marie on 30/04/2017.
 */
public class ClientProxy extends ServerProxy{
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        ItemHandler.registerRenders();
        BlockHandler.registerRenders();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        NetworkRegistry.INSTANCE.registerGuiHandler(Tchernocraft.INSTANCE, new GuiHandler());
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}
