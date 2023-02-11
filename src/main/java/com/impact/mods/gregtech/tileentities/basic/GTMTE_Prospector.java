package com.impact.mods.gregtech.tileentities.basic;

import com.detrav.net.DetravNetwork;
import com.detrav.net.ProspectingPacket;
import com.google.common.base.Objects;
import com.impact.common.oregeneration.OreGenerator;
import com.impact.common.oregeneration.generator.OreVeinGenerator;
import com.impact.common.oregeneration.generator.OresRegionGenerator;
import com.impact.core.Impact_API;
import com.impact.util.Utilits;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Utility;
import gregtech.common.GT_UndergroundOil;
import gregtech.common.tileentities.machines.basic.GT_MetaTileEntity_AdvSeismicProspector;
import ic2.core.Ic2Items;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
		if (te.isServerSide()) {
			ItemStack aStack = aPlayer.getCurrentEquippedItem();
			
			if (!ready && (GT_Utility.consumeItems(aPlayer, aStack, Item.getItemFromBlock(Blocks.tnt), 16)
					|| GT_Utility.consumeItems(aPlayer, aStack, Ic2Items.industrialTnt.getItem(), 8)
					|| GT_Utility.consumeItems(aPlayer, aStack, Materials.Glyceryl, 4)
					|| GT_Utility.consumeItems(aPlayer, aStack, ItemList.Block_Powderbarrel.getItem(), 2) )) {
				
				this.ready = true;
				this.mMaxProgresstime = (aPlayer.capabilities.isCreativeMode ? 20 : 800);
				
			} else if (ready && mMaxProgresstime == 0 && aStack == null) {
				
				int data = mode == 2 ? 2 : mode + 4;
				try {
					int cX = (int) aPlayer.posX >> 4;
					int cZ = (int) aPlayer.posZ >> 4;
					int size = mTier + 5;
					List<Chunk> chunks = new ArrayList<>();
					aPlayer.addChatMessage(new ChatComponentText("Scanning..."));
					
					for (int i = -size; i <= size; ++i) {
						for (int j = -size; j <= size; ++j) {
							if (i != -size && i != size && j != -size && j != size) {
								chunks.add(te.getWorld().getChunkFromChunkCoords(cX + i, cZ + j));
							}
						}
					}
					--size;
					ProspectingPacket packet = new ProspectingPacket(cX, cZ, (int) aPlayer.posX, (int) aPlayer.posZ, size, data);
					
					for (Chunk c : chunks) {
						for(int x = 0; x < 16; ++x) {
							for (int z = 0; z < 16; ++z) {
									switch (data) {
										case 2:
											if (x != 0 && z != 0) {
												FluidStack fStack = GT_UndergroundOil.undergroundOil(te.getWorld().getChunkFromBlockCoords(c.xPosition * 16 + x, c.zPosition * 16 + z), -1.0F);
												if (fStack.amount > 0) {
													packet.addBlock(c.xPosition * 16 + x, 1, c.zPosition * 16 + z, (short)fStack.getFluidID());
													packet.addBlock(c.xPosition * 16 + x, 2, c.zPosition * 16 + z, (short)fStack.amount);
												}
											}
											break;
										case 4:
										case 5:
											Chunk chunkCurr = te.getWorld().getChunkFromBlockCoords(c.xPosition * 16 + x, c.zPosition * 16 + z);
											ChunkCoordIntPair chunkPosition = chunkCurr.getChunkCoordIntPair();
											int xRegCurrent = (chunkPosition.chunkXPos >> 5) % 512;
											int zRegCurrent = (chunkPosition.chunkZPos >> 5) % 512;
											OresRegionGenerator currentRegion = new OresRegionGenerator(xRegCurrent, zRegCurrent, te.getWorld().provider.dimensionId);
											int dimID = getBaseMetaTileEntity().getWorld().provider.dimensionId;
											int idHash = Objects.hashCode(currentRegion.xRegion, currentRegion.zRegion, dimID);
											if (!Impact_API.regionsOres.containsKey(idHash)) {
												currentRegion.createVeins();
												Impact_API.regionsOres.put(idHash, currentRegion);
											}
											
											int layer = data - 4;
											OreVeinGenerator oreVein = OreGenerator.getVein(chunkCurr, layer);
											if (oreVein != null) {
												int sizeVein = OreGenerator.sizeChunk(chunkCurr, layer) / 1000;
												packet.addBlock(c.xPosition * 16 + x, 1, c.zPosition * 16 + z, (short) oreVein.oreVeinID);
												packet.addBlock(c.xPosition * 16 + x, 2, c.zPosition * 16 + z, (short) sizeVein);
											}
									}
							}
						}
					}
					
					packet.level = mTier + 4;
					DetravNetwork.INSTANCE.sendToPlayer(packet, (EntityPlayerMP) aPlayer);
				} catch (Exception e) {}
			}
		}
		return true;
	}
}