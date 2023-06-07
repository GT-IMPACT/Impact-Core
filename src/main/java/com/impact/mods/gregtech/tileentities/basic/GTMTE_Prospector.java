package com.impact.mods.gregtech.tileentities.basic;

import com.google.common.base.Objects;
import com.impact.common.oregeneration.OreGenerator;
import com.impact.common.oregeneration.generator.OreVeinGenerator;
import com.impact.common.oregeneration.generator.OresRegionGenerator;
import com.impact.core.Impact_API;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_UndergroundOil;
import ic2.core.Ic2Items;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class GTMTE_Prospector extends GT_MetaTileEntity_BasicMachine {
	
	boolean ready = false;
	public int mode = 0;
	
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
		aNBT.setInteger("mode", mode);
		aNBT.setBoolean("ready", ready);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		mode = aNBT.getInteger("mode");
		ready = aNBT.getBoolean("ready");
	}
	
	@Override
	public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		if (aPlayer.isSneaking()) {
			this.mode++;
			if (this.mode > 2) this.mode = 0;
			switch (mode) {
				case 0:
					aPlayer.addChatMessage(new ChatComponentText("Mode: Ores Layer 0"));
					break;
				case 1:
					aPlayer.addChatMessage(new ChatComponentText("Mode: Ores Layer 1"));
					break;
				case 2:
					aPlayer.addChatMessage(new ChatComponentText("Mode: Underground Oils"));
					break;
			}
		}
	}
	
	@Override
	public void inValidate() {
		this.ready = false;
		super.inValidate();
	}
	
	@Override
	public boolean onRightclick(IGregTechTileEntity te, EntityPlayer aPlayer) {
		return true;
	}
}