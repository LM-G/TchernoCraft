package com.solofeed.tchernocraft.block;

import com.google.common.base.Preconditions;
import com.solofeed.tchernocraft.Tchernocraft;
import com.solofeed.tchernocraft.util.ReflectionUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
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
                ((IBlockWithProperties) block).getProperties().stream()
                        .flatMap(type -> type.getAllowedValues().stream())
                        .forEach(variant -> registerRender(block, variant.getMeta(), variant.getName()));
                /*
                StateMapperBase b = new DefaultStateMapper();
                BlockStateContainer bsc = block.getBlockState();
                ImmutableList<IBlockState> values = bsc.getValidStates();
                for(IBlockState state : values) {
                    String str = b.getPropertyString(state.getProperties());
                    registerRender(block, block.getMetaFromState(state), str);
                }
                 */
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
     * Registers a block in the game registry and generate its representing item, if the block has custom properties,
     * all of its models are registered too.
     */
    private static void register(Block block){
        GameRegistry.register(block);
        Tchernocraft.LOGGER.info("Registered block" + block.getUnlocalizedName());
        // if block has properties we get his custom itemblock else we get a standard one
        ItemBlock item = block instanceof IBlockWithProperties ? ((IBlockWithProperties) block).getItemBlock() : new ItemBlock(block);
        item.setRegistryName(block.getRegistryName());
        GameRegistry.register(item);
        Tchernocraft.LOGGER.info("Registered item mathcing block " + block.getUnlocalizedName());
    }

    /**
     * Tells Minecraft where to look to find models and textures
     * @param block item to register
     */
    private static void registerRender(Block block) {
        ResourceLocation resourceLocation = Preconditions.checkNotNull(block.getRegistryName(), "A block  resource location is missing");
        ModelResourceLocation location = new ModelResourceLocation(resourceLocation, INVENTORY);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, location);
        Tchernocraft.LOGGER.info("Registered renbder for block " + block.getUnlocalizedName());
    }

    /**
     *
     * @param block item to register
     */
    /**
     * Tells Minecraft where to look to find variant models theirs custom name and textures
     * @param block item to register
     * @param meta corresponding meta
     * @param name variant name
     */
    private static void registerRender(Block block, int meta, String name) {
        ResourceLocation resourceLocation = Preconditions.checkNotNull(block.getRegistryName(), "A block  resource location is missing");
        ModelResourceLocation location = new ModelResourceLocation(resourceLocation, name);
        Item item = Item.getItemFromBlock(block);
        ModelLoader.setCustomModelResourceLocation(item, meta, location);
        Tchernocraft.LOGGER.info("Registered render for block " + location.getResourcePath() + "#" + name);

        ModelBakery.registerItemVariants(item, location);
        Tchernocraft.LOGGER.info("Registered variant render for item " + location.getResourcePath() + "#" + name);
    }
}
