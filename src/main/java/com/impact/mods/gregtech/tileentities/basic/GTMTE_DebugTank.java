package com.impact.mods.gregtech.tileentities.basic;

import com.impact.util.string.Language;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_StorageTank;
import gregtech.api.objects.GT_RenderedTexture;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;

import java.text.NumberFormat;

public class GTMTE_DebugTank extends GT_MetaTileEntity_StorageTank {
	
	public boolean OutputFluid = false;
	
	public GTMTE_DebugTank(int aID, String aName, String aNameRegional, int aTier) {
		super(aID, aName, aNameRegional, aTier, 3, "");
	}
	
	public GTMTE_DebugTank(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
		super(aName, aTier, 3, aDescription, aTextures);
	}
	
	public GTMTE_DebugTank(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
		super(aName, aTier, 3, aDescription, aTextures);
	}
	
	@Override
	protected Textures.BlockIcons textureOverlay() {
		return Textures.BlockIcons.OVERLAY_STANK;
	}
	
	@Override
	protected Textures.BlockIcons textureGlowOverlay() {
		return Textures.BlockIcons.OVERLAY_STANK_GLOW;
	}
	
	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_DebugTank(mName, mTier, mDescriptionArray, mTextures);
	}
	
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setBoolean("OutputFluid", this.OutputFluid);
	}
	
	
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		this.OutputFluid = aNBT.getBoolean("OutputFluid");
	}
	
	public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
		this.OutputFluid = true;
		super.onPostTick(aBaseMetaTileEntity, aTick);
		if (this.getBaseMetaTileEntity().isServerSide()) {
			IFluidHandler tTileEntity = aBaseMetaTileEntity
					.getITankContainerAtSide(aBaseMetaTileEntity.getFrontFacing());
			if (tTileEntity != null) {
				
				if (this.OutputFluid) {
					for (boolean temp = true; temp && mFluid != null; ) {
						mFluid.amount = 1000000;
						temp          = false;
						FluidStack tDrained = mFluid.copy();
						int tFilledAmount = tTileEntity
								.fill(ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()),
										tDrained, false
								);
						if (tFilledAmount > 0) {
							temp = true;
							tTileEntity.fill(ForgeDirection.getOrientation(aBaseMetaTileEntity.getBackFacing()),
									aBaseMetaTileEntity
											.drain(ForgeDirection.getOrientation(aBaseMetaTileEntity.getFrontFacing()),
													tFilledAmount, true
											), true
							);
						}
					}
				}
			}
		}
	}
	
	@Override
	public boolean isLiquidInput(byte aSide) {
		return aSide != getBaseMetaTileEntity().getFrontFacing();
	}
	
	@Override
	public boolean isLiquidOutput(byte aSide) {
		return aSide == getBaseMetaTileEntity().getFrontFacing();
	}
	
	public String[] getDescription() {
		return new String[] {"Creative Tank for tests", "Fill this through a Universal Cell or other Tank and get infinite fluid"};
	}
	
	@Override
	public ITexture[][][] getTextureSet(ITexture[] iTextures) {
		return new ITexture[0][][];
	}
	
	@Override
	public String[] getInfoData() {
		
		if (mFluid == null) {
			return new String[]{
					EnumChatFormatting.BLUE + "Super Tank" + EnumChatFormatting.RESET,
					"Stored Fluid:",
					EnumChatFormatting.GOLD + "No Fluid" + EnumChatFormatting.RESET,
					EnumChatFormatting.GREEN + Integer.toString(0) + " L" + EnumChatFormatting.RESET + " " +
							EnumChatFormatting.YELLOW + NumberFormat.getNumberInstance().format(getCapacity())
							+ " L" + EnumChatFormatting.RESET
			};
		}
		return new String[]{
				EnumChatFormatting.BLUE + "Super Tank" + EnumChatFormatting.RESET,
				"Stored Fluid:",
				EnumChatFormatting.GOLD + mFluid.getLocalizedName() + EnumChatFormatting.RESET,
				EnumChatFormatting.GREEN + NumberFormat.getNumberInstance().format(mFluid.amount) + " L"
						+ EnumChatFormatting.RESET + " " +
						EnumChatFormatting.YELLOW + NumberFormat.getNumberInstance().format(getCapacity())
						+ " L" + EnumChatFormatting.RESET
		};
	}
	
	public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
		return true;
	}
	
	public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
		return true;
	}
	
	public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
		if (aBaseMetaTileEntity.isClientSide()) {
			return true;
		} else {
			aBaseMetaTileEntity.openGUI(aPlayer);
			return true;
		}
	}
	
	public boolean isSimpleMachine() {
		return true;
	}
	
	public boolean isFacingValid(byte aFacing) {
		return true;
	}
	
	public boolean isAccessAllowed(EntityPlayer aPlayer) {
		return true;
	}
	
	public final byte getUpdateData() {
		return 0;
	}
	
	public boolean doesFillContainers() {
		return true;
	}
	
	public boolean doesEmptyContainers() {
		return true;
	}
	
	public boolean canTankBeFilled() {
		return true;
	}
	
	public boolean canTankBeEmptied() {
		return true;
	}
	
	public boolean displaysItemStack() {
		return true;
	}
	
	public boolean displaysStackSize() {
		return false;
	}
	
	@Override
	public int getCapacity() {
		return 10000000;
	}
}
