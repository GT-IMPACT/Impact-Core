package com.impact.mods.gregtech.tileentities.covers;

import gregtech.api.enums.GT_Values;
import gregtech.api.gui.GT_GUICover;
import gregtech.api.gui.widgets.GT_GuiIntegerTextBox;
import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.net.GT_Packet_TileEntityCover;
import gregtech.api.objects.GT_ItemStack;
import gregtech.api.util.GT_CoverBehavior;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidHandler;

public class GTC_AdvFluidDetectorBound extends GT_CoverBehavior {
	
	public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer) {
		FluidDetectorTankData tanks = FluidDetectorTankData.read(aTileEntity);
		if (tanks.capacity == 0) {
			aTileEntity.setOutputRedstoneSignal(aSide, (byte) 0);
			return aCoverVariable;
		}
		if (tanks.capacity < aCoverVariable) {
			aCoverVariable = (int) tanks.capacity;
		}
		boolean active = aCoverVariable > 0
				? tanks.amount >= aCoverVariable
				: tanks.amount <= Math.abs((long) aCoverVariable);
		aTileEntity.setOutputRedstoneSignal(aSide, (byte) (active ? 15 : 0));
		return aCoverVariable;
	}

	public boolean isCoverPlaceable(byte aSide, GT_ItemStack aStack, ICoverable aTileEntity) {
		if(!(aTileEntity instanceof IFluidHandler)) {
			return false;
		}
		return true;
	}

	public int onCoverScrewdriverclick(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		return aCoverVariable;
	}
	
	public boolean letsEnergyIn(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		return true;
	}
	
	public boolean letsEnergyOut(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		return true;
	}
	
	public boolean letsFluidIn(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity) {
		return true;
	}
	
	public boolean letsFluidOut(byte aSide, int aCoverID, int aCoverVariable, Fluid aFluid, ICoverable aTileEntity) {
		return true;
	}
	
	public boolean letsItemsIn(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity) {
		return true;
	}
	
	public boolean letsItemsOut(byte aSide, int aCoverID, int aCoverVariable, int aSlot, ICoverable aTileEntity) {
		return true;
	}
	
	public boolean manipulatesSidedRedstoneOutput(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		return true;
	}
	
	public int getTickRate(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
		return 5;
	}
	
	@Override
	public boolean hasCoverGUI() {
		return true;
	}
	
	@Override
	public Object getClientGUI(byte aSide, int aCoverID, int coverData, ICoverable aTileEntity) {
		return new GUI(aSide, aCoverID, coverData, aTileEntity);
	}
	
	private class GUI extends GT_GUICover {
		private final static int startX = 10;
		private final static int startY = 25;
		private final static int spaceX = 18;
		private final static int spaceY = 18;
		private final byte side;
		private final int coverID;
		private final GT_GuiIntegerTextBox fBox, pBox;
		private int coverVariable, percent;
		private long maxCapacity;
		
		public GUI(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
			super(aTileEntity, 176, 107, GT_Utility.intToStack(aCoverID));

			this.header = "Fluid Detector - Bound";

			this.side          = aSide;
			this.coverID       = aCoverID;
			this.coverVariable = aCoverVariable;
			this.fBox          = new GT_GuiIntegerTextBox(this, 1, startX, startY + 2, spaceX * 4 - 3, 12);
			fBox.setText(String.valueOf(Math.abs(coverVariable)));
			fBox.setMaxStringLength(12);
			this.pBox = new GT_GuiIntegerTextBox(this, 2, startX, startY + spaceY + 2, spaceX * 4 - 3, 12);
			this.maxCapacity = FluidDetectorTankData.read(aTileEntity).guiCapacity();
			percent = (int) ((double) coverVariable / (double) maxCapacity * 100d);
			pBox.setText(String.valueOf(Math.abs(percent)));
			pBox.setMaxStringLength(3);
		}
		
		@Override
		public void drawExtras(int mouseX, int mouseY, float parTicks) {
			super.drawExtras(mouseX, mouseY, parTicks);
			this.getFontRenderer().drawString("L (Limit)", startX + spaceX * 4, 4 + startY, 0xFF555555);
			this.getFontRenderer().drawString("% (Limit)", startX + spaceX * 4, 4 + startY + spaceY, 0xFF555555);
		}
		
		@Override
		protected void onInitGui(int guiLeft, int guiTop, int gui_width, int gui_height) {
			fBox.setFocused(true);
		}
		
		@Override
		public void onMouseWheel(int x, int y, int delta) {
			for (GT_GuiIntegerTextBox box : textBoxes) {
				if (box.isFocused()) {
					int step = Math.max(1, Math.abs(delta / 120));
					step = (isShiftKeyDown() ? 1000 : isCtrlKeyDown() ? 50 : 1) * (delta > 0 ? step : -step);
					long i;
					try {
						i = Long.parseLong(box.getText());
					} catch (NumberFormatException e) {
						return;
					}
					i = i + step;
					if (i > Integer.MAX_VALUE)
						i = Integer.MAX_VALUE;
					else if (i < Integer.MIN_VALUE)
						i = Integer.MIN_VALUE;
					
					box.setText(String.valueOf(i));
					return;
				}
			}
		}
		
		@Override
		public void applyTextBox(GT_GuiIntegerTextBox box) {
			long i;
			String s = box.getText().trim();
			try {
				i = Long.parseLong(s);
			} catch (NumberFormatException e) {
				resetTextBox(box);
				return;
			}
			if (i > Integer.MAX_VALUE) {
				i = Integer.MAX_VALUE;
			} else if (i < Integer.MIN_VALUE) {
				i = Integer.MIN_VALUE;
			}
			
			if (box.id == 1) {
				coverVariable = (int) Math.min(i, maxCapacity);
				percent       = (int) ((double) coverVariable / (double) maxCapacity * 100d);
			} else if (box.id == 2) {
				percent       = Math.min((int) i, 100);
				coverVariable = (int) ((double) percent * (double) maxCapacity / 100d);
			}
			
			fBox.setText(String.valueOf(coverVariable));
			pBox.setText(String.valueOf(percent));
			GT_Values.NW.sendToServer(new GT_Packet_TileEntityCover(side, coverID, coverVariable, tile));
		}
		
		@Override
		public void resetTextBox(GT_GuiIntegerTextBox box) {
			box.setText(String.valueOf(coverVariable));
		}
	}
}
