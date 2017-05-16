package com.solofeed.tchernocraft.tileentity.tileentities;

import com.solofeed.tchernocraft.Tchernocraft;
import com.solofeed.tchernocraft.block.blocks.TestBlock;
import com.solofeed.tchernocraft.tileentity.ITchernocraftTagEnum;
import com.solofeed.tchernocraft.tileentity.TchernocraftAbstractTileEntity;
import com.solofeed.tchernocraft.tileentity.TchernocraftTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * Created by Solofeed on 14/05/2017.
 */
@TchernocraftTileEntity(id=TestBlock.NAME)
public class TestBlockTileEntity extends TchernocraftAbstractTileEntity implements ITickable, ICapabilityProvider {
    public TestBlockTileEntity(){
        tags.put(EnumProperties.COOLDOWN, Integer.valueOf(0));
        tags.put(EnumProperties.ITEM_STACK_HANDLER, new ItemStackHandler());
    }

    @Override
    public void update() {
        if(isWorldLocal()){
            int cooldown = ((int) tags.get(EnumProperties.COOLDOWN) + 1) % 100;
            tags.put(EnumProperties.COOLDOWN, cooldown);
            if(cooldown % 10 == 0){
                Tchernocraft.LOGGER.info("Cooldown : " + cooldown);
            }
        }
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        T result;
        if(Objects.equals(capability, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)){
            result = (T) tags.get(EnumProperties.ITEM_STACK_HANDLER);
        } else {
            result = super.getCapability(capability, facing);
        }
        return result;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return Objects.equals(capability, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) || super.hasCapability(capability, facing);
    }

    public enum EnumProperties implements ITchernocraftTagEnum {
        COOLDOWN("COOLDOWN"),
        ITEM_STACK_HANDLER("ITEM_STACK_HANDLER");

        private final String name;

        EnumProperties(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
