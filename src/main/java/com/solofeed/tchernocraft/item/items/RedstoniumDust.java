package com.solofeed.tchernocraft.item.items;

import com.solofeed.tchernocraft.item.TchernocraftItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;
import org.apache.logging.log4j.Level;

/**
 * RedstoniumOre dust item
 */
@TchernocraftItem(name = RedstoniumDust.NAME)
public class RedstoniumDust extends Item{
    public final static String NAME = "redstonium_dust";

    public RedstoniumDust(){
        super();
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        FMLLog.getLogger().log(Level.INFO, player.getName() + " right clicked " + getUnlocalizedName());
        return super.onItemRightClick(world, player, hand);
    }
}
