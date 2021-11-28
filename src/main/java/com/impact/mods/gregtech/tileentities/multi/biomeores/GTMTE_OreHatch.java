package com.impact.mods.gregtech.tileentities.multi.biomeores;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.objects.GT_RenderedTexture;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import static com.impact.loader.ItemRegistery.CollisionBlock;

public class GTMTE_OreHatch extends GT_MetaTileEntity_Hatch {
	
	public boolean goPipeGo = false;
	
	public GTMTE_OreHatch(int aID, String name, int tier) {
		super(aID, "impact.hatch.miner_hatch." + tier, name, tier, 0, new String[]{
				"Throws headband of the drill and begins resource extraction"
		});
	}
	
	public GTMTE_OreHatch(String aName, int tier, String[] aDescription, ITexture[][][] aTextures) {
		super(aName, tier, 0, aDescription, aTextures);
	}
	
	@Override
	public ITexture[] getTexturesActive(ITexture aBaseTexture) {
		return new ITexture[]{aBaseTexture, new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PUMP)};
	}
	
	@Override
	public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
		return new ITexture[]{aBaseTexture, new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PUMP)};
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity te, long aTick) {
		super.onPostTick(te, aTick);
		if (te.isServerSide() && aTick % 20 == 0) {
			miningPipe(te, goPipeGo);
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
		aNBT.setBoolean("goPipeGo", goPipeGo);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		goPipeGo = aNBT.getBoolean("goPipeGo");
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