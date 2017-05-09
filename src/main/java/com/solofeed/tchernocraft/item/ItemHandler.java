package com.solofeed.tchernocraft.item;

import com.solofeed.tchernocraft.Tchernocraft;
import com.solofeed.tchernocraft.util.ReflectionUtils;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.commons.lang3.StringUtils;

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

    private static List<Item> items;

    /**
     * private constructor
     */
    private ItemHandler(){
        throw new IllegalAccessError("ItemHandler constructor must never be called");
    }

    /**
     * Registers all items
     */
    public static void registerItems() {
        final Class<TchernocraftItem> tchernocraftItemClass = TchernocraftItem.class;
        // we get all item classes
        Set<Class<?>> itemClasses = ReflectionUtils.getClasses(ITEMS_LOCATION, tchernocraftItemClass);
        items = itemClasses.stream().map(c -> {
            // and we instanciate them before registering them in forge registry
            try {
                Item item = Item.class.cast(c.newInstance());
                TchernocraftItem annotation = c.getAnnotation(tchernocraftItemClass);
                item.setRegistryName(new ResourceLocation(Tchernocraft.MOD_ID, annotation.name()));
                item.setUnlocalizedName(item.getRegistryName().getResourcePath());
                CreativeTabs tab = Tchernocraft.creativeTabs.stream()
                        .filter(t -> StringUtils.equals(t.getTabLabel(), annotation.tab()))
                        .findFirst().orElse(null);
                if(tab == null){
                    tab = Tchernocraft.creativeTab;
                }
                item.setCreativeTab(tab);
                return item;
            } catch (Exception e) {
                throw new IllegalArgumentException("Error while instanciating item", e);
            }
        }).collect(Collectors.toList());

        // register item in forge's registry
        items.forEach(GameRegistry::register);
    }

    /**
     * Register all model and textures
     */
    public static void registerRenders(){
        getItems().forEach(ItemHandler::registerRender);
    }

    public static Item getItem(String name){
        return items.stream()
                .filter(i -> i.getRegistryName() != null && StringUtils.equals(i.getRegistryName().getResourcePath(), name))
                .findFirst()
                .orElse(null);
    }

    public static Item getItem(Class clazz){
        return items.stream()
                .filter(i -> i.getClass().equals(clazz))
                .findFirst()
                .orElse(null);
    }

    /**
     * Gets all mod items
     * @return list of tchernocraft items
     */
    public static List<Item> getItems(){
      return items;
    }

    /**
     * Tells Minecraft where to look to find models and textures
     * @param item item to register
     */
    private static void registerRender(Item item){
        ResourceLocation resourceLocation = item.getRegistryName();
        if(resourceLocation == null){
            throw new IllegalArgumentException("A item resource location is missing");
        }
        ModelResourceLocation location = new ModelResourceLocation(resourceLocation, INVENTORY);
        ModelBakery.registerItemVariants(item, location);
        ModelLoader.setCustomMeshDefinition(item, stack -> location);
    }
}
