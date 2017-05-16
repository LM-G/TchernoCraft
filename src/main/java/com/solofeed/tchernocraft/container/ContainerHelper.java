package com.solofeed.tchernocraft.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Solofeed on 14/05/2017.
 */
public final class ContainerHelper {
    /** offset in x or y coordonate to add to a slot to find the next one */
    private static final int SLOT_OFFSET = 18;
    /** private constructor */
    private ContainerHelper(){
        throw new UnsupportedOperationException("TileEntityHandler constructor must never be called");
    }

    public static List<SlotItemHandler> create(Coord2D initPoint, int rows, int columns, int offset,IItemHandler handler){
        List<SlotItemHandler> slots = new ArrayList<>();
        int xPos = initPoint.getX();
        int yPos = initPoint.getY();
        for(int y = 0; y < rows; y++){
            for(int x = 0; x < columns; x++){
                SlotItemHandler slot = new SlotItemHandler(handler, x + y * columns + offset, xPos + x * SLOT_OFFSET, yPos + y * SLOT_OFFSET);
                slots.add(slot);
            }
        }
        return slots;
    }

    public static List<Slot> create(Coord2D initPoint, int rows, int columns, int offset,IInventory inventory){
        List<Slot> slots = new ArrayList<>();
        int xPos = initPoint.getX();
        int yPos = initPoint.getY();
        for(int y = 0; y < rows; y++){
            for(int x = 0; x < columns; x++){
                Slot slot = new Slot(inventory, x + y * columns + offset, xPos + x * SLOT_OFFSET, yPos + y * SLOT_OFFSET);
                slots.add(slot);
            }
        }
        return slots;
    }
}
