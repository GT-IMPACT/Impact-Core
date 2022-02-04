package com.impact.mods.gregtech.tileentities.basic.ae.craftsup;

import com.impact.client.gui.GuiIntegerBox;
import com.impact.network.ToServer_Integer;
import com.impact.network.special.IAESupplierPacker;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lombok.AllArgsConstructor;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUI_SuppliersAE {
	
	@SideOnly(Side.CLIENT)
	public static class Client extends GuiContainer implements IAESupplierPacker {
		
		private static final ResourceLocation BACKGROUND = new ResourceLocation("gregtech:textures/gui/BlankScreen.png");
		private final List<GuiIntegerBox> fields = new ArrayList<>();
		private final Server container;
		private GuiIntegerBox minBound;
		private GuiIntegerBox craftAmount;
		private GuiButton apply;
		private int slotID = 0;
		private ItemStack stackRender;
		
		public Client(Server container) {
			super(container);
			this.container = container;
		}
		
		@SuppressWarnings("unchecked")
		public void initGui() {
			super.initGui();
			this.buttonList.add(this.apply = new GuiButton(0, this.guiLeft + 128, this.guiTop + 51, 38, 20, "Accept"));
			
			minBound = new GuiIntegerBox(fontRendererObj, guiLeft + 10, guiTop + 30, 59, fontRendererObj.FONT_HEIGHT + 4, 1_000_000);
			minBound.setMaxStringLength(16);
			minBound.setTextColor(16777215);
			minBound.setVisible(true);
			minBound.setText("0");
			
			craftAmount = new GuiIntegerBox(fontRendererObj, guiLeft + 10, guiTop + 60, 59, fontRendererObj.FONT_HEIGHT + 4, 1_000);
			craftAmount.setMaxStringLength(16);
			craftAmount.setTextColor(16777215);
			craftAmount.setVisible(true);
			craftAmount.setText("0");
			
			fields.add(minBound);
			fields.add(craftAmount);
		}
		
		@Override
		protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
			fontRendererObj.drawString("Min bound: ", 10, 20, Color.BLACK.hashCode());
			fontRendererObj.drawString("Amount order: ", 10, 50, Color.BLACK.hashCode());
		}
		
		protected final void drawGuiContainerBackgroundLayer(float f, int x, int y) {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			mc.getTextureManager().bindTexture(BACKGROUND);
			drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
			
			try {
				Long.parseLong(minBound.getText());
				Long.parseLong(craftAmount.getText());
				apply.enabled = (minBound.getText().length() > 0 && craftAmount.getText().length() > 0);
			} catch (NumberFormatException e) {
				apply.enabled = false;
			}
			
			for (GuiTextField af : fields) {
				af.drawTextBox();
			}
			
			GL11.glPushMatrix();
			itemRender.zLevel = 100.0F;
			ItemStack item = stackRender != null ? stackRender : new ItemStack(Items.string);
			itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), item, this.guiLeft + 128, this.guiTop + 10);
			itemRender.zLevel = 0.0F;
			GL11.glPopMatrix();
			
		}
		
		@Override
		protected void mouseClicked(int mouseX, int mouseY, int par3) {
			super.mouseClicked(mouseX, mouseY, par3);
			for (GuiTextField f : fields) {
				f.mouseClicked(mouseX, mouseY, par3);
			}
		}
		
		@Override
		protected void keyTyped(char ch, int id) {
			super.keyTyped(ch, id);
			for (GuiTextField f : fields) {
				f.textboxKeyTyped(ch, id);
			}
			if (craftAmount.getText().isEmpty() || minBound.getText().isEmpty()) {
				return;
			}
			try {
				Integer.parseInt(craftAmount.getText());
				Integer.parseInt(minBound.getText());
			} catch (Exception ignored) {
				minBound.setText("1");
				craftAmount.setText("1");
			}
		}
		
		protected void actionPerformed(GuiButton btn) {
			try {
				if (btn == this.apply) {
					int mAmount = Integer.parseInt(minBound.getText());
					int cAmount = Integer.parseInt(craftAmount.getText());
					new ToServer_Integer(this.container.x, this.container.y, this.container.z, slotID, mAmount, cAmount).sendToServer();
					this.mc.thePlayer.closeScreen();
				}
			} catch (NumberFormatException var3) {
				this.minBound.setText("1");
				this.craftAmount.setText("1");
			}
		}
		
		@Override
		public void update(int slotID, ItemStack stack, int makeOrderAmount, int orderCount) {
			this.slotID      = slotID;
			this.stackRender = stack;
			this.minBound.setText(makeOrderAmount + "");
			this.craftAmount.setText(orderCount + "");
		}
	}
	
	@AllArgsConstructor
	public static class Server extends Container {
		int x, y, z;
		
		public boolean canInteractWith(EntityPlayer p) {
			return true;
		}
	}
}