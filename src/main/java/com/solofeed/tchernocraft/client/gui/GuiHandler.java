package com.solofeed.tchernocraft.client.gui;

import com.solofeed.tchernocraft.client.gui.guis.GuiBlockTest;
import com.solofeed.tchernocraft.container.containers.TestBlockContainer;
import com.solofeed.tchernocraft.tileentity.tileentities.TestBlockTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

import static com.solofeed.tchernocraft.client.gui.TchernocraftGuis.GUI_BLOCK_TEST;

/**
 * Created by Solofeed on 14/05/2017.
 */
public class GuiHandler implements IGuiHandler{
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        Object element = null;
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        if(tileEntity != null) {
            if (ID == GUI_BLOCK_TEST) {
                element = new TestBlockContainer(player.inventory, (TestBlockTileEntity) tileEntity);
            }
        }

        return element;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == GUI_BLOCK_TEST){
            return new GuiBlockTest(player.inventory, (TestBlockTileEntity) world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }
}
