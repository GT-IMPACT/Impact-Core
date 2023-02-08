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

import static com.impact.mods.gregtech.enums.Texture.Icons.PRL_HATCH_RED;
import static com.impact.mods.gregtech.enums.Texture.Icons.PRL_HATCH_YELLOW;
import static gregtech.api.enums.Dyes.MACHINE_METAL;

public class GTMTE_ParallelHatch_Output extends GT_MetaTileEntity_Hatch implements IParallelOut {
	
	public int mMaxParallel;
	public String machineName;
	public String address;
	
	public IParallelIn pin = null;
	public IPosition pinPos = null;
	
	public boolean isConnected = false;
	
	public GTMTE_ParallelHatch_Output(int aID, String aName, String aNameRegional, int aTier,
									  int aMaxParallel) {
		super(aID, aName, aNameRegional, aTier, 0, new String[]{
				Utilits.impactTag(),
				"Parallel points transmitter",
				"Used in Parallel Computer"
		});
		mMaxParallel = aMaxParallel;
	}
	
	public GTMTE_ParallelHatch_Output(String aName, int aTier, String[] aDescription,
									  ITexture[][][] aTextures, int aMaxParallel) {
		super(aName, aTier, 0, aDescription, aTextures);
		mMaxParallel = aMaxParallel;
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity iGregTechTileEntity) {
		return new GTMTE_ParallelHatch_Output(mName, mTier, mDescriptionArray, mTextures, mMaxParallel);
	}
	
	@Override
	public ITexture[] getTexturesActive(ITexture aBaseTexture) {
		return new ITexture[]{aBaseTexture,
				TextureFactory.of(PRL_HATCH_YELLOW, Dyes.getModulation(getBaseMetaTileEntity().getColorization(), MACHINE_METAL.getRGBA())),
		};
	}
	
	@Override
	public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
		return new ITexture[]{aBaseTexture,
				TextureFactory.of(PRL_HATCH_RED, Dyes.getModulation(getBaseMetaTileEntity().getColorization(), MACHINE_METAL.getRGBA())),
		};
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
	public boolean isOutputFacing(byte aSide) {
		return aSide == getBaseMetaTileEntity().getFrontFacing();
	}
	
	@Override
	public boolean isInputFacing(byte aSide) {
		return false;
	}
	
	@Override
	public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
		if (getBaseMetaTileEntity().isServerSide()) {
			if (aPlayer.capabilities.isCreativeMode) {
				GT_Utility.sendChatToPlayer(aPlayer, "Debug recipe: " + isConnected);
			}
		}
	}
	
	@Override
	public void inValidate() {
		onPostDisconnect();
		super.inValidate();
	}
	
	@Override
	public void onNotePadRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		super.onNotePadRightClick(aSide, aPlayer, aX, aY, aZ);
		if (getBaseMetaTileEntity().isServerSide()) {
			getBaseMetaTileEntity().setActive(false);
			
			ItemStack tCurrentItem = aPlayer.inventory.getCurrentItem();
			if (ItemList.Tool_NoteBook.getItem() == tCurrentItem.getItem()) {
				
				GT_ModHandler.damageOrDechargeItem(tCurrentItem, 1, 100, aPlayer);
				
				NBTTagCompound nbt = tCurrentItem.getTagCompound();
				
				if (nbt == null) {
					nbt = new NBTTagCompound();
					tCurrentItem.setTagCompound(nbt);
				}
				
				NBTTagCompound posNbt = new PositionObject(getBaseMetaTileEntity()).saveToNBT();
				nbt.setTag("parallel", posNbt);
				
				GT_Utility.sendChatToPlayer(aPlayer, EnumChatFormatting.YELLOW + "Connection start..");
			}
		}
	}
	
	@Override
	public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
		if (aBaseMetaTileEntity.isServerSide()) {
			machineName = "No target machine";
			address = "no target address";
			findIn(pinPos);
		}
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity te, long aTick) {
		super.onPostTick(te, aTick);
	}
	
	private void findIn(IPosition pos) {
		IGregTechTileEntity te = getBaseMetaTileEntity();
		if (te != null) {
			IGregTechTileEntity dest = PositionObject.getIGregTechTileEntity(te, pos);
			
			if (dest != null && dest.getMetaTileEntity() != null && dest.getMetaTileEntity() instanceof IParallelIn) {
				pin = (IParallelIn) dest.getMetaTileEntity();
				if (pin.getParallel() == getParallel()) {
					pin.onConnect(this);
				}
			}
		}
	}
	
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setBoolean("isConnected", this.isConnected);
		
		aNBT.setString("machineName", this.machineName);
		aNBT.setString("address", this.address);
		
		if (pinPos != null) {
			aNBT.setTag("parallelIn", pinPos.saveToNBT());
		}
	}
	
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		this.isConnected = aNBT.getBoolean("isConnected");
		
		machineName = aNBT.getString("machineName");
		address = aNBT.getString("address");
		
		NBTTagCompound parallelIn = aNBT.getCompoundTag("parallelIn");
		if (parallelIn != null) {
			pinPos = PositionObject.loadFromNBT(parallelIn);
		}
	}
	
	@Override
	public void onConnect(@NotNull IParallelIn pin) {
		this.pin = pin;
		this.pinPos = pin.getPosition();
	}
	
	@Override
	public void updateStatus(boolean isConnected) {
		this.isConnected = isConnected;
		IGregTechTileEntity te = getBaseMetaTileEntity();
		if (te != null) {
			te.setActive(isConnected);
		}
		if (pin != null) {
			pin.updateConnectionStatus(isConnected);
		}
	}
	
	@Override
	public void onPostDisconnect() {
		machineName = "No target machine";
		address = "no target address";
		if (pin != null) {
			pin.updateConnectionStatus(false);
		}
	}
	
	@Override
	public int getParallel() {
		return mMaxParallel;
	}
	
	@Override
	public void changeCurrentParallel(int parallels) {
		if (pin != null) {
			pin.updateParallelAmount(parallels);
		}
	}
	
	@Override
	public void updateData(@NotNull String name, @NotNull String address) {
		this.address = address;
		this.machineName = name;
	}
}