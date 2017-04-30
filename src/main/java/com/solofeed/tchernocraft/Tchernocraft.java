package com.solofeed.tchernocraft;

import com.solofeed.tchernocraft.proxy.ServerProxy;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import org.apache.logging.log4j.Level;


@Mod(modid = Tchernocraft.MOD_ID, version = Tchernocraft.VERSION, dependencies = Tchernocraft.DEPENDENCIES, name = Tchernocraft.MOD_NAME)
public class Tchernocraft {
    public static final String MOD_ID = "tchernocraft";
    public static final String MOD_NAME = "Tchernocraft";
    public static final String VERSION = "@VERSION@";
    public static final String DEPENDENCIES = "required-after:forge@[13.20.0.2285]";
    public static final String RESOURCE_PREFIX = MOD_ID.toLowerCase() + ":";
    public static final String CLIENT_PROXY_CLASS = "com.solofeed.tchernocraft.proxy.ClientProxy";
    public static final String SERVER_PROXY_CLASS = "com.solofeed.tchernocraft.proxy.ServerProxy";

    @Instance(MOD_ID)
    public static Tchernocraft instance;

    @SidedProxy(clientSide = CLIENT_PROXY_CLASS, serverSide = SERVER_PROXY_CLASS)
    public static ServerProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
        // some example code
        System.out.println("DIRT BLOCK >> "+Blocks.DIRT.getUnlocalizedName());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent tick){
        FMLLog.getLogger().log(Level.INFO, "Hello world");
    }
}
