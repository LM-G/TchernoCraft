package com.solofeed.tchernocraft.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

/**
 * Created by Solofeed on 14/05/2017.
 */
public abstract class TchernocraftAbstractContainer extends Container {
    protected static final Coord2D DEFAULT_PLAYER_INVENTORY_COORDS = new Coord2D(8,84);
    protected static final Coord2D DEFAULT_PLAYER_ITEM_BAR_COORDS = new Coord2D(8,142);

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return false;
    }

    /**
     * Generates player inventory slots and player hotbar slots
     * @param playerInv player's inventory
     * @param offset id offset for handling real contrainer's slot index
     */
    protected void generatePlayerSlots(IInventory playerInv, int offset){
        // player inventory slots
        ContainerHelper.create(DEFAULT_PLAYER_INVENTORY_COORDS, 3, 9, offset, playerInv).forEach(this::addSlotToContainer);
        // player hotbar slots
        ContainerHelper.create(DEFAULT_PLAYER_ITEM_BAR_COORDS, 1, 9, 0,playerInv).forEach(this::addSlotToContainer);
    }
}
