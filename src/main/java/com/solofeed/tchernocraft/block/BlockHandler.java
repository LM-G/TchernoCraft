package com.solofeed.tchernocraft.block;

import com.solofeed.tchernocraft.Tchernocraft;
import com.solofeed.tchernocraft.util.ReflectionUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Tchernocraft's block handler
 */
public final class BlockHandler {
    /**
     * Relative location of block classes
     */
    private static final String BLOCKS_LOCATION = "com.solofeed.tchernocraft.block.blocks";
    private static final String INVENTORY = "inventory";
    private static List<Block> blocks;

    /**
     * private constructor
     */
    private BlockHandler(){
        throw new IllegalAccessError("BlockHandler constructor must never be called");
    }

    /**
     * Registers all blocks
     */
    public static void registerBlocks() {
        final Class<TchernocraftBlock> tchernocraftBlockClass = TchernocraftBlock.class;
        // we get all block classes
        Set<Class<?>> blockClasses = ReflectionUtils.getClasses(BLOCKS_LOCATION, tchernocraftBlockClass);
        blocks = blockClasses.stream().map(c -> {
            // and we instanciate them before registering them in forge registry
            try {
                Block block = Block.class.cast(c.newInstance());
                TchernocraftBlock annotation = c.getAnnotation(tchernocraftBlockClass);
                block.setRegistryName(new ResourceLocation(Tchernocraft.MOD_ID, annotation.name()));
                block.setUnlocalizedName(block.getRegistryName().getResourcePath());
                CreativeTabs tab = Tchernocraft.creativeTabs.stream()
                        .filter(t -> StringUtils.equals(t.getTabLabel(), annotation.tab()))
                        .findFirst().orElse(null);
                if(tab == null){
                    tab = Tchernocraft.creativeTab;
                }
                block.setCreativeTab(tab);
                return block;
            } catch (Exception e) {
                throw new IllegalArgumentException("Error while instanciating blocks", e);
            }
        }).collect(Collectors.toList());

        // register item in forge's registry
        blocks.forEach(BlockHandler::register);
    }

    /**
     * Register all model and textures
     */
    public static void registerRenders(){
        getBlocks().forEach(BlockHandler::registerRender);
    }

    /**
     * Gets all mod blocks
     * @return list of tchernocraft blocks
     */
    public static Block getBlock(Class clazz){
            return blocks.stream()
                    .filter(b -> b.getClass().equals(clazz))
                    .findFirst()
                    .orElse(null);
    }

    /**
     * Gets all mod blocks
     * @return list of tchernocraft blocks
     */
    public static List<Block> getBlocks(){
        return blocks;
    }



    /**
     * Registers a block in the gfameregistry and generate its representing item
     */
    private static void register(Block block){
        GameRegistry.register(block);
        ItemBlock item = new ItemBlock(block);
        item.setRegistryName(block.getRegistryName());
        GameRegistry.register(item);
    }

    /**
     * Registers all weapons blocks
     */
    private static void registerWeapons(){

    }

    /**
     * Registers all tools blocks
     */
    private static void registerTools(){

    }

    /**
     * Registers all potion blocks
     */
    private static void registerPotions(){

    }

    /**
     * Registers all miscellaneous blocks
     */
    private static void registerMiscellaneous(){

    }

    /**
     * Tells Minecraft where to look to find models and textures
     * @param block item to register
     */
    private static void registerRender(Block block){
        ResourceLocation resourceLocation = block.getRegistryName();
        if(resourceLocation == null){
            throw new IllegalArgumentException("A block  resource location is missing");
        }
        ModelResourceLocation location = new ModelResourceLocation(resourceLocation, INVENTORY);
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
                .register(Item.getItemFromBlock(block), 0, location);
    }
}
