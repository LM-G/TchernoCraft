package com.solofeed.tchernocraft.container.containers;

import com.solofeed.tchernocraft.container.ContainerHelper;
import com.solofeed.tchernocraft.container.Coord2D;
import com.solofeed.tchernocraft.container.TchernocraftAbstractContainer;
import com.solofeed.tchernocraft.tileentity.tileentities.TestBlockTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.ItemStackHandler;

/**
 * Created by Solofeed on 14/05/2017.
 */
public class TestBlockContainer extends TchernocraftAbstractContainer {
    private static final int ROWS = 3;
    private static final int COLUMNS = 3;
    private static final Coord2D SELF_INVENTORY_COORDS = new Coord2D(62,17);

    private TestBlockTileEntity tileEntity;

    public TestBlockContainer(IInventory playerInv, TestBlockTileEntity tileEntity) {
        this.tileEntity = tileEntity;
        int nbSlots = ROWS * COLUMNS;
        // generate test block inventory slots
        ContainerHelper.create(SELF_INVENTORY_COORDS, ROWS, COLUMNS, 0,new ItemStackHandler(nbSlots)).forEach(this::addSlotToContainer);
        // generates default player inventory and hotbar slots
        generatePlayerSlots(playerInv, ROWS * COLUMNS);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
