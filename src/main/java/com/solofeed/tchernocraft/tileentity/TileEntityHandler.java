package com.solofeed.tchernocraft.tileentity;

import com.solofeed.tchernocraft.Tchernocraft;
import com.solofeed.tchernocraft.util.ReflectionUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

/**
 * Tchernocraft's tile entities handler
 */
public final class TileEntityHandler {
    /** Relative location of block classes */
    private static final String TILE_ENTITIES_LOCATION = "com.solofeed.tchernocraft.tileentity.tileentities";

    /** private constructor */
    private TileEntityHandler(){
        throw new UnsupportedOperationException("TileEntityHandler constructor must never be called");
    }

    /**
     * Registers all tile entities
     */
    public static void registerTileEntities() {
        Tchernocraft.LOGGER.info("Registering tile entities ...");
        Set<Class<?>> tileEntityClasses = ReflectionUtils.getClasses(TILE_ENTITIES_LOCATION, TchernocraftTileEntity.class);
        for(Class<?> tClass : tileEntityClasses){
            register(tClass.asSubclass(TileEntity.class));
        }
        Tchernocraft.LOGGER.info("All tile entities registered !");
    }

    /**
     * Register a tile entity and set its id
     * @param tClass tile entity class to push in registry
     */
    private static void register(Class<? extends TileEntity> tClass){
        TchernocraftTileEntity annotation = tClass.getAnnotation(TchernocraftTileEntity.class);
        if(annotation != null && StringUtils.isNotBlank(annotation.id())){
            String id = Tchernocraft.MOD_ID + ":" + annotation.id();
            GameRegistry.registerTileEntity(tClass, id);
        } else {
            throw new IllegalArgumentException("Error while instanciating tile entities, invalid class declaration");
        }
    }
}
