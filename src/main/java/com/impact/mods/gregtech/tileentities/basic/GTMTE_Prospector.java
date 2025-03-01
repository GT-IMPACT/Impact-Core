package com.impact.mods.gregtech.tileentities.basic;

import com.impact.addon.vw.VirtualWorldScan;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Utility;
import ic2.core.Ic2Items;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import space.gtimpact.virtual_world.api.VirtualAPI;

public class GTMTE_Prospector extends GT_MetaTileEntity_BasicMachine {
	
	boolean ready = false;
	public int type = 0;
	public int layer = 0;

	private EntityPlayer playerHandler = null;

	public GTMTE_Prospector(int aID, String aNameRegional, int aTier) {
		super(aID, "impact.basic.prospector." + aTier, aNameRegional, aTier, 1, // amperage
				"",
				1, // input slot count
				1, // output slot count
				"Default.png", // GUI name
				"", // NEI name
				TextureFactory.of(Textures.BlockIcons.OVERLAY_SIDE_ROCK_BREAKER_ACTIVE),
				TextureFactory.of(Textures.BlockIcons.OVERLAY_SIDE_ROCK_BREAKER),
				TextureFactory.of(Textures.BlockIcons.OVERLAY_TOP_ROCK_BREAKER_ACTIVE),
				TextureFactory.of(Textures.BlockIcons.OVERLAY_TOP_ROCK_BREAKER),
				TextureFactory.of(Textures.BlockIcons.OVERLAY_FRONT_ROCK_BREAKER_ACTIVE),
				TextureFactory.of(Textures.BlockIcons.OVERLAY_FRONT_ROCK_BREAKER),
				TextureFactory.of(Textures.BlockIcons.OVERLAY_BOTTOM_ROCK_BREAKER_ACTIVE),
				TextureFactory.of(Textures.BlockIcons.OVERLAY_BOTTOM_ROCK_BREAKER)
		);
	}
	
	public String[] getDescription() {
		return new String[]{
				"Scan: radius " + (mTier + 5) + " chunks",
				"Use Screwdriver to change mode (Fluid, Ore Layer 0-1)",
				"Place, activate with explosives",
				"2 Powderbarrels, 4 Glyceryl Trinitrate, 16 TNT, or 8 ITNT"
		};
	}
	
	protected GTMTE_Prospector(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures, String aGUIName, String aNEIName) {
		super(aName, aTier, 1, aDescription, aTextures, 1, 1, aGUIName, aNEIName);
	}
	
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_Prospector(this.mName, this.mTier, this.mDescriptionArray, this.mTextures,
				this.mGUIName, this.mNEIName);
	}

	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setInteger("type", type);
		aNBT.setInteger("layer", layer);
		aNBT.setBoolean("ready", ready);

		if (playerHandler != null) {
			aNBT.setString("playerHandler", playerHandler.getGameProfile().getName());
		}
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		type = aNBT.getInteger("type");
		layer = aNBT.getInteger("layer");
		ready = aNBT.getBoolean("ready");
		String name = aNBT.getString("playerHandler");
		if (name != null) {
			playerHandler = MinecraftServer.getServer().getConfigurationManager().func_152612_a(name);
		}
	}

	@Override
	public void inValidate() {
		this.ready = false;
		super.inValidate();
	}

	@Override
	public void onPostTick(IGregTechTileEntity te, long aTick) {
		super.onPostTick(te, aTick);
		if (te.isServerSide() && aTick % 20 == 0) {
			if (ready && mMaxProgresstime == 0) {
				if (playerHandler != null) {
					VirtualWorldScan.scanStart(te, mTier, playerHandler);
					playerHandler = null;
					ready = false;
				}
			}
		}
	}

	@Override
	public boolean onRightclick(IGregTechTileEntity te, EntityPlayer aPlayer) {
		if (te.isServerSide()) {
			ItemStack aStack = aPlayer.getCurrentEquippedItem();
			if (!ready && (GT_Utility.consumeItems(aPlayer, aStack, Item.getItemFromBlock(Blocks.tnt), 16)
					|| GT_Utility.consumeItems(aPlayer, aStack, Ic2Items.industrialTnt.getItem(), 8)
					|| GT_Utility.consumeItems(aPlayer, aStack, Materials.Glyceryl, 4)
					|| GT_Utility.consumeItems(aPlayer, aStack, ItemList.Block_Powderbarrel.getItem(), 2))) {
				
				this.ready = true;
				this.mMaxProgresstime = (aPlayer.capabilities.isCreativeMode ? 20 : 1200);
				playerHandler = aPlayer;
			}
		}
		return true;
	}
}
