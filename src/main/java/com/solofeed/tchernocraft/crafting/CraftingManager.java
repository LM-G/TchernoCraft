package com.solofeed.tchernocraft.crafting;

import com.solofeed.tchernocraft.block.BlockHandler;
import com.solofeed.tchernocraft.block.blocks.RedstoniumOre;
import com.solofeed.tchernocraft.item.ItemHandler;
import com.solofeed.tchernocraft.item.items.RedstoniteDust;
import com.solofeed.tchernocraft.item.items.RedstoniteIngot;
import com.solofeed.tchernocraft.item.items.RedstoniumDust;
import com.solofeed.tchernocraft.item.items.RedstoniumIngot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * TODO refactoring, abstraction etc
 * Created by Louis-Marie on 02/05/2017.
 */
public final class CraftingManager {
    /**
     * private constructor
     */
    private CraftingManager(){
        throw new IllegalAccessError("CraftingManager constructor must never be called");
    }

    public static void registerRecipes(){
        addSmelting(RedstoniumDust.class, RedstoniumIngot.class, 1, 50.0f);
        addSmelting(RedstoniumOre.class, RedstoniumIngot.class, 1, 25.0f);
        addSmelting(RedstoniteDust.class, RedstoniteIngot.class, 1, 100.0f);
    }

    private static void addSmelting(Class<?> input, Class<?> output, int amount,  float xp){
        ItemStack in = getItemStack(input);
        ItemStack out = getItemStack(output);
        out.setCount(amount);
        GameRegistry.addSmelting(in, out, xp);
    }

    private static ItemStack getItemStack(Class<?> itemBlockClass){
        ItemStack result;
        if(Item.class.isAssignableFrom(itemBlockClass)){
            result = new ItemStack(ItemHandler.getItem(itemBlockClass));
        } else {
            result = new ItemStack(BlockHandler.getBlock(itemBlockClass));
        }
        return result;
    }
}
