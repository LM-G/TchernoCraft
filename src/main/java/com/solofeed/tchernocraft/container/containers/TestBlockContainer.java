package com.solofeed.tchernocraft.container.containers;

import com.solofeed.tchernocraft.container.ContainerHelper;
import com.solofeed.tchernocraft.container.Coord2D;
import com.solofeed.tchernocraft.container.TchernocraftAbstractContainer;
import com.solofeed.tchernocraft.tileentity.tileentities.TestBlockTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 * Created by Solofeed on 14/05/2017.
 */
public class TestBlockContainer extends TchernocraftAbstractContainer {
    private static final int ROWS = 3;
    private static final int COLUMNS = 3;
    private static final Coord2D SELF_INVENTORY_COORDS = new Coord2D(61,18);

    private TestBlockTileEntity tileEntity;

    public TestBlockContainer(IInventory playerInv, TestBlockTileEntity tileEntity) {
        this.tileEntity = tileEntity;
        itemHandler = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        itemHandler = new ItemStackHandler(ROWS * COLUMNS);
        // generate test block inventory slots
        ContainerHelper.create(SELF_INVENTORY_COORDS, ROWS, COLUMNS, itemHandler).forEach(this::addSlotToContainer);
        // generates default player inventory slots
        generateDefaultSlots(playerInv);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
