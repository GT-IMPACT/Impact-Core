package com.impact.mods.gregtech.tileentities.multi.ores.hatches;

import com.impact.common.item.ITieredDamagedItems;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_Container_1by1;
import gregtech.api.gui.GT_GUIContainer_1by1;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.render.TextureFactory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import static com.impact.loader.ItemRegistery.CollisionBlock;

public class GTMTE_OreHatch extends GT_MetaTileEntity_Hatch {
	
	public int drillCoefficient = 0;
	public boolean ready = false;
	
	public GTMTE_OreHatch(int aID, String name, int tier) {
		super(aID, "impact.hatch.miner_hatch." + tier, name, tier, 1, new String[]{
				"Throws headband of the drill and begins resource extraction"
		});
	}
	
	public GTMTE_OreHatch(String aName, int tier, String[] aDescription, ITexture[][][] aTextures) {
		super(aName, tier, 1, aDescription, aTextures);
	}
	
	@Override
	public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GT_Container_1by1(aPlayerInventory, aBaseMetaTileEntity);
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GT_GUIContainer_1by1(aPlayerInventory, aBaseMetaTileEntity,"Drill Hatch", "DrillHatch");
	}
	
	@Override
	public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
		if (aBaseMetaTileEntity.isClientSide()) {
			return true;
		}
		aBaseMetaTileEntity.openGUI(aPlayer);
		return true;
	}
	
	@Override
	public ITexture[] getTexturesActive(ITexture aBaseTexture) {
		return new ITexture[]{aBaseTexture, TextureFactory.of(Textures.BlockIcons.OVERLAY_PUMP)};
	}
	
	@Override
	public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
		return new ITexture[]{aBaseTexture, TextureFactory.of(Textures.BlockIcons.OVERLAY_PUMP)};
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity te, long aTick) {
		super.onPostTick(te, aTick);
		if (te.isServerSide()) {
			if (aTick % 20 == 2) {
				if (mInventory[0] != null && mInventory[0].getItem() instanceof ITieredDamagedItems) {
					ready = true;
					drillCoefficient = mInventory[0].getItemDamage();
				} else {
					ready = false;
					drillCoefficient = 0;
				}
				te.setActive(ready);
				miningPipe(te, te.isActive());
			}
		}
	}
	
	public void cycleDrill(boolean active) {
		if (active) {
			if (mInventory[0] != null) {
				int damage = mInventory[0].getTagCompound().getInteger("drillDamage");
				damage = damage - 1;
				if (damage >= 0) {
					mInventory[0].stackTagCompound.setInteger("drillDamage", damage);
				} else {
					drillCoefficient = 0;
					ready            = false;
					mInventory[0]    = null;
				}
			}
		}
	}
	
	private void miningPipe(IGregTechTileEntity te, boolean active) {
		if (active) {
			for (int dist = 1; dist < 256; dist++) {
				if (te.getAirAtSideAndDistance((byte) 0, dist)) {
					te.getWorld().setBlock(te.getXCoord(), te.getYCoord() - dist, te.getZCoord(), CollisionBlock, 0, 3);
				} else if (te.getBlockAtSideAndDistance((byte) 0, dist) != CollisionBlock) {
					break;
				}
			}
		} else {
			for (int dist = 1; dist < 256; dist++) {
				if (te.getBlockAtSideAndDistance((byte) 0, dist) == CollisionBlock) {
					te.getWorld().setBlockToAir(te.getXCoord(), te.getYCoord() - dist, te.getZCoord());
				} else {
					break;
				}
			}
		}
	}
	
	@Override
	public void inValidate() {
		IGregTechTileEntity te = getBaseMetaTileEntity();
		if (te.isServerSide()) {
			miningPipe(te, false);
		}
		super.inValidate();
	}
	
	@Override
	public boolean isFacingValid(byte aFacing) {
		return aFacing == 0;
	}
	
	@Override
	public boolean isSimpleMachine() {
		return true;
	}
	
	@Override
	public boolean isAccessAllowed(EntityPlayer aPlayer) {
		return true;
	}
	
	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_OreHatch(mName, mTier, mDescriptionArray, mTextures);
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setInteger("drillCoefficient", drillCoefficient);
		aNBT.setBoolean("ready", ready);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		drillCoefficient = aNBT.getInteger("drillCoefficient");
		ready = aNBT.getBoolean("ready");
	}
	
	@Override
	public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
		return false;
	}
	
	@Override
	public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
		return false;
	}
}