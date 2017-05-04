package com.solofeed.tchernocraft.creativetab;

import com.solofeed.tchernocraft.item.ItemHandler;
import com.solofeed.tchernocraft.item.items.RedstoniumDust;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.MissingResourceException;

/**
 * Created by Louis-Marie on 01/05/2017.
 */
public class TchernocraftTab extends CreativeTabs {
    public static final String NAME = "tchernocraft_tab";
    public TchernocraftTab() {
        super(NAME);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getTabIconItem() {
        Item itemToDisplay = ItemHandler.getItem(RedstoniumDust.NAME);
        if(itemToDisplay == null){
            throw new MissingResourceException("Missing item", RedstoniumDust.class.getName(), RedstoniumDust.NAME);
        }
        return new ItemStack(itemToDisplay);
    }
}
