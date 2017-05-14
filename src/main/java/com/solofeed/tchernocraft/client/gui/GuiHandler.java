package com.solofeed.tchernocraft.client.gui;

import com.solofeed.tchernocraft.container.containers.TestBlockContainer;
import com.solofeed.tchernocraft.tileentity.tileentities.TestBlockTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

/**
 * Created by Solofeed on 14/05/2017.
 */
public class GuiHandler implements IGuiHandler{
    public static  final int TEST_BLOCK = 0;

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == TEST_BLOCK){
            return new TestBlockContainer(player.inventory, (TestBlockTileEntity) world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == TEST_BLOCK){
            return new TestBlockGui(player.inventory, (TestBlockTileEntity) world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }
}
