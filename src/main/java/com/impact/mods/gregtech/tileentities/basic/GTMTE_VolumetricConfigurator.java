package com.impact.mods.gregtech.tileentities.basic;

import com.impact.mods.gregtech.gui.volumetricconfig.Container_VolumetricConfigurator;
import com.impact.mods.gregtech.gui.volumetricconfig.GUI_VolumetricConfigurator;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_TieredMachineBlock;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Utility;
import gregtech.common.items.GT_VolumetricFlask;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GTMTE_VolumetricConfigurator extends GT_MetaTileEntity_TieredMachineBlock {
	
	public int exampleCapacity = 1000;
	
	public GTMTE_VolumetricConfigurator(int aID, String aNameRegional) {
		super(aID, "impact.basic.volumetric_regulator", aNameRegional, 1, 3, "The machine is used to configure volumetric flasks");
	}
	
	public GTMTE_VolumetricConfigurator(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
		super(aName, aTier, 3, aDescription, aTextures);
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity te) {
		return new GTMTE_VolumetricConfigurator(mName, mTier, mDescriptionArray, mTextures);
	}
	
	@Override
	public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
		if (aBaseMetaTileEntity.isClientSide()) return true;
		aBaseMetaTileEntity.openGUI(aPlayer);
		return true;
	}
	
	@Override
	public ITexture[] getTexture(IGregTechTileEntity te, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
		if (aSide == aFacing) {
			return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1], TextureFactory.of(Textures.BlockIcons.OVERLAY_PIPE_OUT)};
		}
		return aSide == 1
				? new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1],
				TextureFactory.of(Textures.BlockIcons.OVERLAY_QCHEST)}
				: new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1]};
	}
	
	@Override
	public ITexture[][][] getTextureSet(ITexture[] aTextures) {
		return new ITexture[0][0][0];
	}
	
	@Override
	public Object getServerGUI(int aID, InventoryPlayer p, IGregTechTileEntity te) {
		return new Container_VolumetricConfigurator(p, te);
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer p, IGregTechTileEntity te) {
		return new GUI_VolumetricConfigurator(p, te, getLocalName());
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
	public boolean isValidSlot(int aIndex) {
		return true;
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity te, long aTick) {
		super.onPostTick(te, aTick);
		if (te.isServerSide()) {
			
			for (ItemStack is : mInventory) {
				if (is != null && !(is.getItem() instanceof GT_VolumetricFlask)) {
					return;
				}
			}
			
			if (mInventory[0] != null) {
				ItemStack is = mInventory[0];
				if (is.getItem() instanceof GT_VolumetricFlask) {
					GT_VolumetricFlask volumetric = (GT_VolumetricFlask) is.getItem();
					exampleCapacity = volumetric.getCapacity(is);
				}
			}
			
			boolean m2IsEmpty = true;
			GT_VolumetricFlask m2 = null;
			
			if (mInventory[1] != null) {
				if (mInventory[1].getItem() instanceof GT_VolumetricFlask) {
					m2IsEmpty = false;
					m2 = (GT_VolumetricFlask) mInventory[1].getItem();
					m2.setCapacity(mInventory[1], exampleCapacity);
				}
				
				if (mInventory[2] == null) {
					mInventory[2] = mInventory[1].copy();
					mInventory[2].stackSize = 0;
				} else {
					ItemStack is2 = mInventory[2];
					if (is2.getItem() instanceof GT_VolumetricFlask) {
						GT_VolumetricFlask volumetric = (GT_VolumetricFlask) is2.getItem();
						int cap1 = volumetric.getCapacity(is2);
						if (!m2IsEmpty) {
							if (cap1 == m2.getCapacity(mInventory[1])) {
								if (mInventory[2].stackSize + 1 <= 64) {
									mInventory[2].stackSize++;
									mInventory[1].stackSize--;
								}
							}
						}
					}
				}
				if (mInventory[1] != null && mInventory[1].stackSize <= 0) {
					mInventory[1] = null;
				}
			}
			IInventory tTileEntity = te.getIInventoryAtSide(te.getFrontFacing());
			if (tTileEntity != null) {
				if (mInventory[2] != null) {
					GT_Utility.moveOneItemStack(te, tTileEntity, te.getFrontFacing(), te.getBackFacing(), null, false, (byte) 64, (byte) 1, (byte) 64, (byte) 1);
				}
			}
		}
	}
	
	@Override
	public void saveNBTData(NBTTagCompound n) {
		n.setInteger("exampleCapacity", exampleCapacity);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound n) {
		exampleCapacity = n.getInteger("exampleCapacity");
	}
	
	@Override
	public boolean allowPullStack(IGregTechTileEntity te, int i, byte b, ItemStack itemStack) {
		return i == 2 && itemStack.getItem() instanceof GT_VolumetricFlask;
	}
	
	@Override
	public boolean allowPutStack(IGregTechTileEntity te, int i, byte b, ItemStack itemStack) {
		return i == 1 && itemStack.getItem() instanceof GT_VolumetricFlask;
	}
	
	@Override
	public boolean isSimpleMachine() {
		return true;
	}
}