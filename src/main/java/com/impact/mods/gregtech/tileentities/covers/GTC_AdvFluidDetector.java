package com.impact.mods.gregtech.tileentities.covers;

import gregtech.api.enums.GT_Values;
import gregtech.api.gui.GT_GUICover;
import gregtech.api.gui.widgets.GT_GuiIntegerTextBox;
import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.net.GT_Packet_TileEntityCover;
import gregtech.api.util.GT_CoverBehavior;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class GTC_AdvFluidDetector extends GT_CoverBehavior {
	
	public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer) {
		if ((aTileEntity instanceof IFluidHandler)) {
			FluidTankInfo[] tTanks = ((IFluidHandler) aTileEntity).getTankInfo(ForgeDirection.UNKNOWN);
			int tMax = 0;
			int tUsed = 0;
			if (tTanks != null) {
				for (FluidTankInfo tTank : tTanks) {
					if (tTank != null) {
						tMax = tTank.capacity;
						FluidStack tLiquid = tTank.fluid;
						if (tLiquid != null) {
							tUsed = tLiquid.amount;
						}
					}
				}
			}
			if (tMax < aCoverVariable) {
				aCoverVariable = tMax;
			} else {
				if (tUsed >= aCoverVariable) {
					aTileEntity.setOutputRedstoneSignal(aSide, (byte) 15);
				} else {
					aTileEntity.setOutputRedstoneSignal(aSide, (byte) 0);
				}
			}
		} else {
			aTileEntity.setOutputRedstoneSignal(aSide, (byte) 0);
		}
		return aCoverVariable;
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
		private final GT_GuiIntegerTextBox fBox;
		private int coverVariable;
		
		public GUI(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
			super(aTileEntity, 176, 107, GT_Utility.intToStack(aCoverID));
			this.side          = aSide;
			this.coverID       = aCoverID;
			this.coverVariable = aCoverVariable;
			this.fBox          = new GT_GuiIntegerTextBox(this, 2, startX + spaceX * 0, startY + spaceY * 0 + 2, spaceX * 4 - 3, 12);
			fBox.setText(String.valueOf(coverVariable));
			fBox.setMaxStringLength(12);
		}
		
		@Override
		public void drawExtras(int mouseX, int mouseY, float parTicks) {
			super.drawExtras(mouseX, mouseY, parTicks);
			this.getFontRenderer().drawString("Limit", startX + spaceX * 4, 4 + startY + spaceY * 0, 0xFF555555);
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
			coverVariable = (int) i;
			fBox.setText(String.valueOf(coverVariable));
			GT_Values.NW.sendToServer(new GT_Packet_TileEntityCover(side, coverID, coverVariable, tile));
		}
		
		@Override
		public void resetTextBox(GT_GuiIntegerTextBox box) {
			box.setText(String.valueOf(coverVariable));
		}
	}
}