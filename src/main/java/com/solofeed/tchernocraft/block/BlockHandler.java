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
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
    /**
     * item variant extensions
     */
    private static final String INVENTORY = "inventory";
    /**
     * All Tchernocraft blocks
     */
    public static List<Block> BLOCKS;

    /**
     * private constructor
     */
    private BlockHandler(){
        throw new UnsupportedOperationException("BlockHandler constructor must never be called");
    }

    /**
     * Registers all blocks
     */
    public static void registerBlocks() {
        Tchernocraft.LOGGER.info("Registering blocks ...");
        // instanciates all BLOCKS
        initBlocks();
        // register item in forge's registry
        BLOCKS.forEach(BlockHandler::register);
        Tchernocraft.LOGGER.info("All blocks registered !");
    }

    /**
     * Register all blocks models and textures
     */
    public static void registerRenders(){
        Tchernocraft.LOGGER.info("Registering block renders ...");
        for(Block block : BLOCKS){
            // handles blocks with custom properties
            if(block instanceof ITchernocraftBlockWithProperties){
                StateMapperBase mapper = new DefaultStateMapper();
                ImmutableList<IBlockState> values = block.getBlockState().getValidStates();
                for(IBlockState state : values) {
                    String name = mapper.getPropertyString(state.getProperties());
                    registerRender(block, block.getMetaFromState(state), name);
                }
            } else {
                // handles "normal" blocks
                registerRender(block);
            }
        }
        Tchernocraft.LOGGER.info("All block renders registered !");
    }

    /**
     * Gets all mod blocks
     * @return list of tchernocraft blocks
     */
    public static Block getBlock(Class clazz){
            return BLOCKS.stream()
                    .filter(b -> b.getClass().equals(clazz))
                    .findFirst()
                    .orElse(null);
    }

    /**
     * Instanciates all blocks
     */
    private static void initBlocks(){
        // retrives all block classes in blocks package
        Set<Class<?>> blockClasses = ReflectionUtils.getClasses(BLOCKS_LOCATION, TchernocraftBlock.class);
        BLOCKS = blockClasses.stream().map(bClass -> {
            // and instanciates them
            try {
                return initBlock((Class<Block>) bClass);
            } catch (Exception e) {
                throw new IllegalArgumentException("Error while instanciating blocks", e);
            }
        }).collect(Collectors.toList());
    }

    /**
     * Instanciates a class block. Set the registry name, the unlocalized name and its creative tab.
     * All blocks must implements the {@link ITchernocraftBlock} interface
     *
     * @param bClass block class to instanciate
     * @return {@link Block} block instanciated
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static Block initBlock(Class<? extends Block> bClass) throws IllegalAccessException, InstantiationException {
        Block block = Block.class.cast(bClass.newInstance());
        if(block instanceof ITchernocraftBlock){
            ITchernocraftBlock tBlock = (ITchernocraftBlock) block;
            block.setRegistryName(new ResourceLocation(Tchernocraft.MOD_ID, tBlock.getName()));
            block.setUnlocalizedName(block.getRegistryName().getResourcePath());
            block.setCreativeTab(tBlock.getCreativeTabs());
            return block;
        } else {
            throw new IllegalArgumentException("Tried to instanciate invalid class " + bClass.getCanonicalName() + " into block");
        }
    }

    /**
     * Registers a block in the game registry and generate its representing item, if the block has custom properties,
     * all of its variant models are registered too.
     *
     * @param block block to register in registry
     */
    private static void register(Block block){
        // register the block
        GameRegistry.register(block);
        // if block has properties we get his custom itemblock else we get a standard one
        ItemBlock item = block instanceof ITchernocraftBlockWithProperties ? ((ITchernocraftBlockWithProperties) block).getItemBlock() : new ItemBlock(block);
        //register the block's associated item
        GameRegistry.register(item.setRegistryName(block.getRegistryName()));
        Tchernocraft.LOGGER.info("Registered block " + block.getRegistryName().getResourcePath());
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
        Tchernocraft.LOGGER.info("Registered render for block " + location.getResourcePath() + "#" + location.getVariant());
    }
}
