package com.solofeed.tchernocraft.block;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.solofeed.tchernocraft.Tchernocraft;
import com.solofeed.tchernocraft.util.ReflectionUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
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
        Tchernocraft.LOGGER.info("Registering blocks ...");
        // instanciates all blocks
        initBlocks();
        // register item in forge's registry
        blocks.forEach(BlockHandler::register);
        Tchernocraft.LOGGER.info("All blocks registered");
    }

    /**
     * Register all model and textures
     */
    public static void registerRenders(){
        Tchernocraft.LOGGER.info("Registering block renders ...");
        for(Block block : blocks){
            if(block instanceof IBlockWithProperties){
                /*((IBlockWithProperties) block).getProperties().stream()
                        .flatMap(type -> type.getAllowedValues().stream())
                        .forEach(variant -> registerRender(block, variant.getMeta(), variant.getName()));*/
                StateMapperBase mapper = new DefaultStateMapper();
                ImmutableList<IBlockState> values = block.getBlockState().getValidStates();
                for(IBlockState state : values) {
                    String name = mapper.getPropertyString(state.getProperties());
                    registerRender(block, block.getMetaFromState(state), name);
                }
            } else {
                registerRender(block);
            }
        }
        Tchernocraft.LOGGER.info("All block renders registered");
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
     * Instanciates all blocks
     */
    private static void initBlocks(){
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
                // onglet par défaut
                if(tab == null){
                    tab = Tchernocraft.creativeTab;
                }
                block.setCreativeTab(tab);
                return block;
            } catch (Exception e) {
                throw new IllegalArgumentException("Error while instanciating blocks", e);
            }
        }).collect(Collectors.toList());
    }



    /**
     * Registers a block in the game registry and generate its representing item, if the block has custom properties,
     * all of its models are registered too.
     */
    private static void register(Block block){
        // register the block
        GameRegistry.register(block);
        // if block has properties we get his custom itemblock else we get a standard one
        ItemBlock item = block instanceof IBlockWithProperties ? ((IBlockWithProperties) block).getItemBlock() : new ItemBlock(block);
        //register the block's associated item
        GameRegistry.register(item.setRegistryName(block.getRegistryName()));
        Tchernocraft.LOGGER.info("Registered block " + block.getUnlocalizedName());
    }

    /**
     * Tells Minecraft where to look to find models and textures
     * @param block item to register
     */
    private static void registerRender(Block block) {
        ResourceLocation resourceLocation = Preconditions.checkNotNull(block.getRegistryName(), "A block  resource location is missing");
        ModelResourceLocation location = new ModelResourceLocation(resourceLocation, INVENTORY);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, location);
        Tchernocraft.LOGGER.info("Registered render for block " + location.getResourcePath());
    }

    /**
     * Tells Minecraft where to look to find variant models theirs custom name and textures
     * @param block item to register
     * @param meta corresponding meta
     * @param name variant name
     */
    private static void registerRender(Block block, int meta, String name) {
        ResourceLocation resourceLocation = Preconditions.checkNotNull(block.getRegistryName(), "A block  resource location is missing");
        ModelResourceLocation location = new ModelResourceLocation(resourceLocation, name);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, location);
        Tchernocraft.LOGGER.info("Registered render for block " + location.getResourcePath());
    }
}
