package com.impact.mods.gregtech.tileentities.covers;

import com.impact.mods.gregtech.tileentities.multi.storage.GTMTE_LapPowerStation;
import gregtech.api.enums.GT_Values;
import gregtech.api.gui.GT_GUICover;
import gregtech.api.gui.widgets.GT_GuiIcon;
import gregtech.api.gui.widgets.GT_GuiIconCheckButton;
import gregtech.api.gui.widgets.GT_GuiIntegerTextBox;
import gregtech.api.interfaces.IGuiScreen;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicBatteryBuffer;
import gregtech.api.net.GT_Packet_TileEntityCover;
import gregtech.api.util.GT_CoverBehavior;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import ic2.api.item.IElectricItem;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class GTC_AdvEUDetector extends GT_CoverBehavior {
	
	public int doCoverThings(byte aSide, byte aInputRedstone, int aCoverID, int aCoverVariable, ICoverable aTileEntity, long aTimer) {
		int tScale = 0;
		long maxCapacity = 0;
		long tStored = 0;
		if (aTileEntity instanceof IGregTechTileEntity) {
			IGregTechTileEntity tTileEntity = (IGregTechTileEntity) aTileEntity;
			IMetaTileEntity mTileEntity = tTileEntity.getMetaTileEntity();
			
			if (mTileEntity instanceof GTMTE_LapPowerStation) {
				GTMTE_LapPowerStation buffer = (GTMTE_LapPowerStation) mTileEntity;
				maxCapacity = buffer.capacity;
				tStored = buffer.stored;
			} else if (mTileEntity instanceof GT_MetaTileEntity_BasicBatteryBuffer) {
				
				maxCapacity = aTileEntity.getEUCapacity();
				tStored = aTileEntity.getStoredEU();
				GT_MetaTileEntity_BasicBatteryBuffer buffer = (GT_MetaTileEntity_BasicBatteryBuffer) mTileEntity;
				if (buffer.mInventory != null) {
					for (ItemStack aStack : buffer.mInventory) {
						if (GT_ModHandler.isElectricItem(aStack)) {
							if (aStack.getItem() instanceof GT_MetaBase_Item) {
								Long[] stats = ((GT_MetaBase_Item) aStack.getItem()).getElectricStats(aStack);
								if (stats != null) {
									maxCapacity += stats[0];
									tStored = tStored + ((GT_MetaBase_Item) aStack.getItem()).getRealCharge(aStack);
								}
							} else if (aStack.getItem() instanceof IElectricItem) {
								tStored += (long) ic2.api.item.ElectricItem.manager.getCharge(aStack);
								maxCapacity += (long) ((IElectricItem) aStack.getItem()).getMaxCharge(aStack);
							}
						}
					}
				}
			}
		}
		tScale = (int) ((double) tStored / (double) maxCapacity * 100d);
		if (aCoverVariable > 0) {
			aTileEntity.setOutputRedstoneSignal(aSide, (byte) (tScale >= aCoverVariable ? 15 : 0));
		} else {
			int checker = Math.abs(aCoverVariable);
			aTileEntity.setOutputRedstoneSignal(aSide, (byte) (tScale <= checker ? 15 : 0));
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
		return 20;
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
		private final GT_GuiIntegerTextBox pBox;
		private int coverVariable;
		
		public GUI(byte aSide, int aCoverID, int aCoverVariable, ICoverable aTileEntity) {
			super(aTileEntity, 176, 107, GT_Utility.intToStack(aCoverID));
			this.side          = aSide;
			this.coverID       = aCoverID;
			this.coverVariable = aCoverVariable;
			new GT_GuiIconCheckButton(this, 0, startX, startY, GT_GuiIcon.REDSTONE_ON, GT_GuiIcon.REDSTONE_OFF);
			this.pBox = new GT_GuiIntegerTextBox(this, 1, startX, startY + spaceY + 2, spaceX * 4 - 3, 12);
			pBox.setText(String.valueOf(Math.abs(coverVariable)));
			pBox.setMaxStringLength(4);
		}
		
		@Override
		public void drawExtras(int mouseX, int mouseY, float parTicks) {
			super.drawExtras(mouseX, mouseY, parTicks);
			String s2;
			if (coverVariable < 0) s2 = trans("INVERTED","Inverted");
			else s2 = trans("NORMAL","Normal");
			this.getFontRenderer().drawString(s2,  startX + spaceX, 4 + startY, 0xFF555555);
			this.getFontRenderer().drawString("% (Limit)", startX + spaceX * 4, 4 + startY + spaceY, 0xFF555555);
			if (coverVariable < 0) {
				this.getFontRenderer().drawString("Signal = (S / C * 100%) " + EnumChatFormatting.RED + "<=" + EnumChatFormatting.RESET + " Limit", startX, 4 + startY + spaceY * 2, 0xFF555555);
			} else {
				this.getFontRenderer().drawString("Signal = (S / C * 100%) " + EnumChatFormatting.GREEN + ">=" + EnumChatFormatting.RESET + " Limit", startX, 4 + startY + spaceY * 2, 0xFF555555);
			}
			this.getFontRenderer().drawString("S = Buffer Stored", startX, 16 + startY + spaceY * 2, 0xFF555555);
			this.getFontRenderer().drawString("C = Buffer Capacity", startX, 28 + startY + spaceY * 2, 0xFF555555);
		}
		
		@Override
		protected void onInitGui(int guiLeft, int guiTop, int gui_width, int gui_height) {
			updateButtons();
		}
		
		public void buttonClicked(GuiButton btn){
			if (btn.id == 0) {
				coverVariable = getNewCoverVariable(btn.id, true);
				pBox.setText(String.valueOf(Math.abs(coverVariable)));
				GT_Values.NW.sendToServer(new GT_Packet_TileEntityCover(side, coverID, coverVariable, tile));
			}
			updateButtons();
		}
		
		private void updateButtons(){
			for (Object o : buttonList) {
				boolean checker = coverVariable > 0;
				((GT_GuiIconCheckButton) o).setChecked(checker);
			}
		}
		
		private int getNewCoverVariable(int id, boolean checked) {
			if (id == 0) {
				return -coverVariable;
			}
			return coverVariable;
		}
		
		private boolean isEnabled(int id) {
			if (id == 0) return coverVariable != 0;
			return (coverVariable >> 1) == id;
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
				coverVariable = Math.min((int) Math.max(-100, i), 100);
			}
			pBox.setText(String.valueOf(Math.abs(coverVariable)));
			GT_Values.NW.sendToServer(new GT_Packet_TileEntityCover(side, coverID, coverVariable, tile));
		}
		
		@Override
		public void resetTextBox(GT_GuiIntegerTextBox box) {
			box.setText(String.valueOf(coverVariable));
		}
		
		private class GT_GuiIntegerTextBoxWithMinus extends GT_GuiIntegerTextBox {
			
			public GT_GuiIntegerTextBoxWithMinus(IGuiScreen gui, int id, int x, int y, int width, int height) {
				super(gui, id, x, y, width, height);
			}
			
			@Override
			public boolean validChar(char c, int key) {
				if (getCursorPosition() == 0 && key == 12) // minus first allowed.
					return true;
				return super.validChar(c, key);
			}
		}
		
	}
}