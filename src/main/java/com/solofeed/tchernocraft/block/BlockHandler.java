package com.solofeed.tchernocraft.block;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import com.google.common.base.Preconditions;
import com.solofeed.tchernocraft.Tchernocraft;
import com.solofeed.tchernocraft.TchernocraftHandler;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Tchernocraft's block handler
 */
public final class BlockHandler extends TchernocraftHandler {
    /**
     * Relative location of block classes
     */
    private static final String INVENTORY = "inventory";
    private static List<Block> blocks = new ArrayList<>();

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
        ServiceLoader<TchernocraftBlock> blockClasses = ServiceLoader.load(TchernocraftBlock.class);
        for (TchernocraftBlock tchernocraftBlock : blockClasses) {
            try {
                Block block = Block.class.cast(tchernocraftBlock.getClass().newInstance());
                block.setRegistryName(new ResourceLocation(Tchernocraft.MOD_ID, tchernocraftBlock.getName()));
                block.setUnlocalizedName(block.getRegistryName().getResourcePath());
                block.setCreativeTab(getCreativeTabFromCurrentElement(tchernocraftBlock.getTab()));
                blocks.add(block);
            } catch (Exception e) {
                throw new IllegalArgumentException("Error while instanciating blocks", e);
            }
		}

        // register item in forge's registry
        blocks.forEach(BlockHandler::register);
    }

    /**
     * Register all model and textures
     */
    public static void registerRenders(){
        try{
            getBlocks().forEach(BlockHandler::registerRender);
        } catch(Exception e){

        }
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
        ItemBlock item = block instanceof IBlockWithProperties ? ((IBlockWithProperties) block).getItemBlock() : new ItemBlock(block);
        item.setRegistryName(block.getRegistryName());
        GameRegistry.register(item);
    }

    /**
     * Tells Minecraft where to look to find models and textures
     * @param block item to register
     */
    private static void registerRender(Block block) {
        ResourceLocation resourceLocation = Preconditions.checkNotNull(block.getRegistryName(), "A block  resource location is missing");
        ModelResourceLocation location = new ModelResourceLocation(resourceLocation, INVENTORY);
        ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
        mesher.register(Item.getItemFromBlock(block), 0, location);
    }
}
