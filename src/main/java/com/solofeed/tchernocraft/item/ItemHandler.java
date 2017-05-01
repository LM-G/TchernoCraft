package com.solofeed.tchernocraft.item;

import com.google.common.reflect.ClassPath;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Tchernocraft's item handler
 */
public final class ItemHandler {
    /**
     * Relative location of Item classes
     */
    private static final String ITEMS_LOCATION = "com.solofeed.tchernocraft.item.items";
    private static final String INVENTORY = "inventory";
    private static List<Item> dustItems;

    /**
     * private constructor
     */
    private ItemHandler(){}

    /**
     * Initialize all tchernocraft's items in forge registry
     */
    public static void init(){
        registerDusts();
        registerWeapons();
        registerTools();
        registerPotions();
        registerMiscellaneous();
    }

    /**
     * Register all model and textures
     */
    public static void registerRenders(){
        dustItems.forEach(ItemHandler::registerRender);
    }

    /**
     * Gets all mod items
     * @return list of tchernocraft items
     */
    public List<Item> getItems(){
      List<Item> items = new ArrayList<>();
      items.addAll(dustItems);
      return items;
    }

    /**
     * Registers all dust items
     */
    private static void registerDusts() {
        // we get all dust classes
        Set<Class<?>> dustClasses = getClasses(DustItem.class);
        dustItems = dustClasses.stream().map(c -> {
            // and we instanciate them before registering them in forge registry
            try {
                return Item.class.cast(c.newInstance());
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }).collect(Collectors.toList());

        // register item in forge's registry
        dustItems.forEach(GameRegistry::register);
    }

    /**
     * Registers all weapons items
     */
    private static void registerWeapons(){

    }

    /**
     * Registers all tools items
     */
    private static void registerTools(){

    }

    /**
     * Registers all potion items
     */
    private static void registerPotions(){

    }

    /**
     * Registers all miscellaneous items
     */
    private static void registerMiscellaneous(){

    }

    /**
     * Tells Minecraft where to look to find models and textures
     * @param item item to register
     */
    private static void registerRender(Item item){
        ResourceLocation resourceLocation = item.getRegistryName();
        if(resourceLocation == null){
            throw new IllegalArgumentException("A resource location is missing");
        }
        ModelResourceLocation location = new ModelResourceLocation(resourceLocation, INVENTORY);
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, location);
    }

    /**
     * Gets all specified annotated classes
     * @param clazz annotation
     * @return all annotated mathching classes
     */
    private static Set<Class<?>> getClasses(final Class<? extends Annotation> clazz) {
        Set<Class<?>> classes = new HashSet<>();
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            ClassPath classpath = ClassPath.from(loader);
            classes = classpath.getTopLevelClasses(ITEMS_LOCATION)
                    .stream()
                    .map(ClassPath.ClassInfo::load)
                    .filter(c -> c.isAnnotationPresent(clazz))
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }
}
