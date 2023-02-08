package com.impact.mods.gregtech.tileentities.multi.parallelsystem;

import com.impact.api.parallelsystem.IParallelIn;
import com.impact.api.parallelsystem.IParallelOut;
import com.impact.api.position.IPosition;
import com.impact.util.PositionObject;
import com.impact.util.Utilits;
import gregtech.api.enums.Dyes;
import gregtech.api.enums.ItemList;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static com.impact.mods.gregtech.enums.Texture.Icons.PRL_HATCH_RED;
import static com.impact.mods.gregtech.enums.Texture.Icons.PRL_HATCH_YELLOW;
import static gregtech.api.enums.Dyes.MACHINE_METAL;

public class GTMTE_ParallelHatch_Input extends GT_MetaTileEntity_Hatch implements IParallelIn {
	
	public int mMaxParallel = 1;
	public int mCurrentParallelIn = 1;

	public boolean isDebug;
	public String machineName;
	public String address;
	
	public IParallelOut pout = null;
	public boolean isConnected;
	
	public GTMTE_ParallelHatch_Input(int aID, String aName, String aNameRegional, int aTier, int aMaxParallel) {
		super(aID, aName, aNameRegional, aTier, 0, new String[]{
				Utilits.impactTag(),
				"Parallel points receiver",
				"Used in multis machines",
				"Reduces recipe time by a factor of " + (aTier - 4)
		});
		mMaxParallel = aMaxParallel;
		isDebug = false;
	}
	
	public GTMTE_ParallelHatch_Input(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures, int aMaxParallel) {
		super(aName, aTier, 0, aDescription, aTextures);
		mMaxParallel = aMaxParallel;
		isDebug = false;
	}
	
	@Override
	public ITexture[] getTexturesActive(ITexture aBaseTexture) {
		return new ITexture[]{aBaseTexture, TextureFactory.of(PRL_HATCH_YELLOW, Dyes.getModulation(getBaseMetaTileEntity().getColorization(), MACHINE_METAL.getRGBA())),};
	}
	
	@Override
	public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
		return new ITexture[]{aBaseTexture, TextureFactory.of(PRL_HATCH_RED, Dyes.getModulation(getBaseMetaTileEntity().getColorization(), MACHINE_METAL.getRGBA())),};
	}
	
	@Override
	public boolean isSimpleMachine() {
		return true;
	}
	
	@Override
	public boolean isFacingValid(byte aFacing) {
		return true;
	}
	
	@Override
	public boolean isAccessAllowed(EntityPlayer aPlayer) {
		return true;
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity iGregTechTileEntity) {
		isDebug = false;
		return new GTMTE_ParallelHatch_Input(mName, mTier, mDescriptionArray, mTextures, mMaxParallel);
	}
	
	@Override
	public boolean isOutputFacing(byte aSide) {
		return false;
	}
	
	@Override
	public boolean isInputFacing(byte aSide) {
		return aSide == getBaseMetaTileEntity().getFrontFacing();
	}
	
	@Override
	public void onNotePadRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		super.onNotePadRightClick(aSide, aPlayer, aX, aY, aZ);
		if (getBaseMetaTileEntity().isServerSide()) {
			ItemStack tCurrentItem = aPlayer.inventory.getCurrentItem();
			if (ItemList.Tool_NoteBook.getItem() == tCurrentItem.getItem()) {
				GT_ModHandler.damageOrDechargeItem(tCurrentItem, 1, 100, aPlayer);
				NBTTagCompound nbt = tCurrentItem.getTagCompound();
				
				NBTTagCompound posNbt = nbt.getCompoundTag("parallel");
				if (posNbt != null) {
					IPosition pos = PositionObject.loadFromNBT(posNbt);
					if (findOut(pos)) {
						nbt.removeTag("parallel");
						GT_Utility.sendChatToPlayer(aPlayer, EnumChatFormatting.GREEN + "Connection confirm");
					} else {
						GT_Utility.sendChatToPlayer(aPlayer, EnumChatFormatting.RED + "Connection Error");
					}
				}
			}
		}
	}
	
	private boolean findOut(IPosition pos) {
		IGregTechTileEntity te = getBaseMetaTileEntity();
		if (te != null) {
			IGregTechTileEntity dest = PositionObject.getIGregTechTileEntity(te, pos);
			
			if (dest != null && dest.getMetaTileEntity() != null && dest.getMetaTileEntity() instanceof IParallelOut) {
				pout = (IParallelOut) dest.getMetaTileEntity();
				
				if (pout.getParallel() == getParallel()) {
					pout.onConnect(this);
					pout.updateData(getMachineName(), getMachineAddress());
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
		if (getBaseMetaTileEntity().isServerSide()) {
			if (aPlayer.capabilities.isCreativeMode) {
				GT_Utility.sendChatToPlayer(aPlayer, "Debug recipe: " + getConnectionStatus());
			}
		}
	}
	
	@Override
	public void inValidate() {
		onDisconnect();
		super.inValidate();
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity iAm, long aTick) {
		super.onPostTick(iAm, aTick);
	}
	
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setInteger("mCurrentParallelIn", this.mCurrentParallelIn);
		aNBT.setBoolean("isConnected", this.isConnected);
		aNBT.setString("machineName", this.machineName);
		aNBT.setString("address", this.address);
	}
	
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		mCurrentParallelIn = aNBT.getInteger("mCurrentParallelIn");
		isConnected = aNBT.getBoolean("isConnected");
		machineName = aNBT.getString("machineName");
		address = aNBT.getString("address");
	}
	
	@Override
	public void onFirstTick(IGregTechTileEntity te) {
		super.onFirstTick(te);
		if (te.isServerSide()) {
			if (machineName == null) {
				machineName = "Not target machine";
			}
			if (address == null) {
				address = UUID.randomUUID().toString().substring(0, 8);
			}
		}
	}
	
	@Override
	public void updateConnectionStatus(boolean isConnected) {
		this.isConnected = isConnected;
		IGregTechTileEntity te = getBaseMetaTileEntity();
		if (te != null) {
			te.setActive(isConnected);
		}
	}
	
	@Override
	public boolean getConnectionStatus() {
		return isConnected;
	}
	
	@Override
	public boolean isValid() {
		IGregTechTileEntity te = getBaseMetaTileEntity();
		return te != null && !te.isDead() && isConnected;
	}
	
	@Override
	public void onDisconnect() {
		if (pout != null) {
			pout.onPostDisconnect();
		}
	}
	
	@Override
	public void updateParallelAmount(int parallels) {
		mCurrentParallelIn = parallels;
	}
	
	@NotNull
	@Override
	public IPosition getPosition() {
		IGregTechTileEntity te = getBaseMetaTileEntity();
		if (te != null) {
			return new PositionObject(te);
		}
		return new PositionObject(0, 0, 0);
	}
	
	@Override
	public int getParallel() {
		return mMaxParallel;
	}
	
	@Override
	public void onConnect(@NotNull IParallelOut pout) {
		this.pout = pout;
		this.pout.updateData(getMachineName(), getMachineAddress());
	}
	
	@NotNull
	@Override
	public String getMachineName() {
		return machineName;
	}
	
	@NotNull
	@Override
	public String getMachineAddress() {
		return address;
	}
}