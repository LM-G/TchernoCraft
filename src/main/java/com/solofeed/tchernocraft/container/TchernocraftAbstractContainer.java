package com.solofeed.tchernocraft.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.IItemHandler;

/**
 * Created by Solofeed on 14/05/2017.
 */
public abstract class TchernocraftAbstractContainer extends Container {
    protected IItemHandler itemHandler;
    protected static final Coord2D DEFAULT_PLAYER_INVENTORY_COORDS = new Coord2D(8,84);
    protected static final Coord2D DEFAULT_PLAYER_ITEM_BAR_COORDS = new Coord2D(8,99);

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return false;
    }

    protected void generateDefaultSlots(IInventory playerInv){
        ContainerHelper.create(DEFAULT_PLAYER_INVENTORY_COORDS, 3, 9, playerInv).forEach(this::addSlotToContainer);
        ContainerHelper.create(DEFAULT_PLAYER_ITEM_BAR_COORDS, 1, 9, playerInv).forEach(this::addSlotToContainer);
    }
}
