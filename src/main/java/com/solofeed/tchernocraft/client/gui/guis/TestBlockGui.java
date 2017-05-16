package com.solofeed.tchernocraft.client.gui.guis;

import com.solofeed.tchernocraft.Tchernocraft;
import com.solofeed.tchernocraft.container.containers.TestBlockContainer;
import com.solofeed.tchernocraft.tileentity.tileentities.TestBlockTileEntity;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Solofeed on 14/05/2017.
 */
public class TestBlockGui extends GuiContainer {
    public static final String TEXTURE_LOCATION = "textures/gui/container/test_block.png";

    private static final int X_SIZE = 176;
    private static final int Y_SIZE = 166;


    public TestBlockGui(IInventory playerInv, TestBlockTileEntity tileEntity) {
        super(new TestBlockContainer(playerInv, tileEntity));

        xSize = X_SIZE;
        ySize = Y_SIZE;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1F, 1F, 1F, 1F);
        mc.getTextureManager().bindTexture(new ResourceLocation(Tchernocraft.MOD_ID, TEXTURE_LOCATION));
        drawTexturedModalRect(guiLeft, guiTop, 0,0,xSize, ySize);
    }
}
