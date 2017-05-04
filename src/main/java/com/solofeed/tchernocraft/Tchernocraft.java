package com.solofeed.tchernocraft;

import com.solofeed.tchernocraft.creativetab.TchernocraftTab;
import com.solofeed.tchernocraft.proxy.IProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.Arrays;
import java.util.List;


@Mod(modid = Tchernocraft.MOD_ID, version = Tchernocraft.VERSION, name = Tchernocraft.MOD_NAME)
public class Tchernocraft {
    public static final String MOD_ID = "tchernocraft";
    public static final String MOD_NAME = "Tchernocraft";
    public static final String VERSION = "1.0";
    public static final String CLIENT_PROXY_CLASS = "com.solofeed.tchernocraft.proxy.ClientProxy";
    public static final String SERVER_PROXY_CLASS = "com.solofeed.tchernocraft.proxy.ServerProxy";

    @Mod.Instance(MOD_ID)
    public static Tchernocraft instance = new Tchernocraft();

    @SidedProxy(clientSide = CLIENT_PROXY_CLASS, serverSide = SERVER_PROXY_CLASS)
    public static IProxy proxy;

    public static final TchernocraftTab creativeTab = new TchernocraftTab();
    public static final List<CreativeTabs> creativeTabs = Arrays.asList(creativeTab);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
