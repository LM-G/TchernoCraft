package com.solofeed.tchernocraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Tchernocraft's tile entity which holds all tags
 */
public abstract class TchernocraftAbstractTileEntity extends TileEntity{
    /** tags properties of the tile entity */
    protected Map<ITchernocraftTagEnum, Object> tags = new HashMap<>();

    /**
     * Determines if the running INSTANCE is the client side or the server side
     * @return true if we are on server side, false else
     */
    protected boolean isWorldLocal(){
        return hasWorld() && world.isRemote == false;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        for(Map.Entry<ITchernocraftTagEnum, Object> entry : tags.entrySet()){
            String entryName = entry.getKey().getName();
            Object entryValue = entry.getValue();
            if(entryValue instanceof Integer){
                entry.setValue((compound.getInteger(entryName)));
            } else if(entryValue instanceof ItemStackHandler){
                ((ItemStackHandler) entryValue).deserializeNBT(compound.getCompoundTag(entryName));
            }
        }
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        for(Map.Entry<ITchernocraftTagEnum, Object> entry : tags.entrySet()){
            String name = entry.getKey().getName();
            Object entryValue = entry.getValue();
            if(entryValue instanceof Integer){
                compound.setInteger(name, (Integer) entryValue);
            } else if(entryValue instanceof ItemStackHandler){
                compound.setTag(name, ((ItemStackHandler) entryValue).serializeNBT());
            }
        }
        return super.writeToNBT(compound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        this.readFromNBT(tag);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return nbt;
    }

    @Override
    public NBTTagCompound getTileData() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return nbt;
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        int metadata = getBlockMetadata();
        return  new SPacketUpdateTileEntity(pos, metadata, nbt);
    }
}
