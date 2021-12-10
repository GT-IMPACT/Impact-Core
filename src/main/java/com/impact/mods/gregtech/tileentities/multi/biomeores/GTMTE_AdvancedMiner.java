package com.impact.mods.gregtech.tileentities.multi.biomeores;

import com.impact.common.oregeneration.OreGenerator;
import com.impact.common.oregeneration.OreVein;
import com.impact.common.oregeneration.generator.OreChunkGenerator;
import com.impact.common.oregeneration.generator.OreVeinGenerator;
import com.impact.impact;
import com.impact.mods.gregtech.enums.Texture;
import com.impact.mods.gregtech.tileentities.multi.biomeores.hatches.GTMTE_EnrichmentUnit;
import com.impact.mods.gregtech.tileentities.multi.biomeores.hatches.GTMTE_OreHatch;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.string.MultiBlockTooltipBuilder;
import gregtech.api.GregTech_API;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.objects.XSTR;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fluids.FluidStack;
import space.impact.api.ImpactAPI;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.impact.util.multis.GT_StructureUtility.ofFrame;
import static com.impact.util.multis.GT_StructureUtility.ofHatchAdder;
import static space.impact.api.multiblocks.structure.StructureUtility.*;

public class GTMTE_AdvancedMiner extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_AdvancedMiner> {
	
	static IStructureDefinition<GTMTE_AdvancedMiner> definition =
			StructureDefinition.<GTMTE_AdvancedMiner>builder()
					.addShape("main", new String[][]{
							{"     ", "     ", "     ", "     ", "     ", "     ", "     ", "     ", "     ", " G G ", " G G ", " G G "},
							{"     ", "     ", "     ", "  G  ", "  G  ", "  G  ", " GAG ", " G G ", " G G ", "GA~AG", "G   G", "G   G"},
							{"  G  ", "  G  ", "  G  ", " GAG ", " GAG ", " GAG ", " AAA ", "     ", "     ", " ACA ", "     ", "     "},
							{"     ", "     ", "     ", "  G  ", "  G  ", "  G  ", " GAG ", " G G ", " G G ", "GAAAG", "G   G", "G   G"},
							{"     ", "     ", "     ", "     ", "     ", "     ", "     ", "     ", "     ", " G G ", " G G ", " G G "},
					})
					.addElement('G', lazy(t -> ofFrame(Materials.Steel)))
					.addElement('C', ofHatchAdder(GTMTE_AdvancedMiner::checkHatch, 16, ImpactAPI.RED))
					.addElement('A', ofChain(
									ofBlock(GregTech_API.sBlockCasings2, 0),
									ofHatchAdder(GTMTE_AdvancedMiner::addToMachineList, 16, GregTech_API.sBlockCasings2, 0),
									ofHatchAdder(GTMTE_AdvancedMiner::checkEnrich, 16, GregTech_API.sBlockCasings2, 0)
							)
					)
					.build();
	private final List<GTMTE_OreHatch> hatch = new ArrayList<>();
	private final List<GTMTE_EnrichmentUnit> enrich = new ArrayList<>();
	public int cycleIncrease = 0, sizeVeinPreStart = 0;
	public OreVeinGenerator oreVeinGenerator = null;
	public List<OreChunkGenerator> chunksGenerator = new ArrayList<>();
	public OreVein oreVein = OreGenerator.empty;
	public int layer = 2;
	ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][16];
	
	public GTMTE_AdvancedMiner(int aID, String aNameRegional) {
		super(aID, "impact.multis.miner.advanced", aNameRegional);
	}
	
	public GTMTE_AdvancedMiner(String aName) {
		super(aName);
	}
	
	@Override
	public void onFirstTick(IGregTechTileEntity te) {
		super.onFirstTick(te);
		initOreProperty(te);
		increaseLayer(te);
	}
	
	public void increaseLayer(IGregTechTileEntity te) {
		if (cycleIncrease <= 0) return;
		Chunk ch = te.getWorld().getChunkFromBlockCoords(te.getXCoord(), te.getZCoord());
		OreVeinGenerator veinLocal = OreGenerator.getVein(ch, layer);
		if (veinLocal != null) {
			for (OreChunkGenerator oreChunkGenerator : veinLocal.oreChunkGenerators) {
				if (oreChunkGenerator.sizeOreChunk > 0) {
					int localSum = oreChunkGenerator.sizeOreChunk - cycleIncrease;
					if (localSum >= 0) {
						
						Chunk chunk = te.getWorld().getChunkFromChunkCoords(oreChunkGenerator.xChunk, oreChunkGenerator.zChunk);
						OreGenerator.amountIncrease(chunk, layer, cycleIncrease);

						break;
					} else {
						cycleIncrease -= oreChunkGenerator.sizeOreChunk;
						
						if (cycleIncrease <= 0) {
							break;
						}
						
						Chunk chunk = te.getWorld().getChunkFromChunkCoords(oreChunkGenerator.xChunk, oreChunkGenerator.zChunk);
						OreGenerator.amountIncrease(chunk, layer, oreChunkGenerator.sizeOreChunk);
						
					}
				}
			}
		}
		cycleIncrease = 0;
	}
	
	public void initOreProperty(IGregTechTileEntity te) {
		if (te.isServerSide()) {
			Chunk ch = te.getWorld().getChunkFromBlockCoords(te.getXCoord(), te.getZCoord());
			cycleIncrease = 0;
			sizeVeinPreStart = 0;
			chunksGenerator.clear();
			oreVeinGenerator = OreGenerator.getVein(ch, layer);
			if (oreVeinGenerator != null) {
				for (OreChunkGenerator oreChunkGenerator : oreVeinGenerator.oreChunkGenerators) {
					sizeVeinPreStart += oreChunkGenerator.sizeOreChunk;
					chunksGenerator.add(oreChunkGenerator);
				}
				if (sizeVeinPreStart > 0) {
					oreVein = OreGenerator.getOreVein(te, layer);
				} else {
					oreVein = OreGenerator.empty;
				}
			} else {
				oreVein = OreGenerator.empty;
			}
		}
	}
	
	@Override
	public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
		return aSide == aFacing ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(aActive ? Texture.Icons.OVERLAY_BASIC_MINER_ACTIVE : Texture.Icons.OVERLAY_BASIC_MINER)} : new ITexture[]{INDEX_CASE};
	}
	
	@Override
	public IStructureDefinition<GTMTE_AdvancedMiner> getStructureDefinition() {
		return definition;
	}
	
	public boolean machineStructure(IGregTechTileEntity te) {
		te.setFrontFacing((byte) 1);
		Chunk chunk = te.getWorld().getChunkFromBlockCoords(te.getXCoord(), te.getZCoord());
		int size = 0;
		for (Object o : chunk.chunkTileEntityMap.values()) {
			if (o instanceof TileEntity) {
				TileEntity tile = (TileEntity) o;
				if (tile instanceof IGregTechTileEntity) {
					IMetaTileEntity meta = ((IGregTechTileEntity) tile).getMetaTileEntity();
					if (meta instanceof GTMTE_Mining_Coal) {
						size++;
					}
				}
			}
		}
		if (size > 1) return false;
		hatch.clear();
		enrich.clear();
		boolean check = checkPiece(2, 9, 1);// TODO: 02.12.2021
		if (hatch.size() != 1) check = false;
		if (enrich.size() != 1) check = false;
		return check;
	}
	
	private boolean checkHatch(IGregTechTileEntity te, int index) {
		if (te == null) {
			return false;
		} else {
			IMetaTileEntity mte = te.getMetaTileEntity();
			if (mte == null) {
				return false;
			} else {
				if (mte instanceof GT_MetaTileEntity_Hatch) {
					((GT_MetaTileEntity_Hatch) mte).updateTexture(index);
				}
				if (mte instanceof GTMTE_OreHatch) {
					((GTMTE_OreHatch) mte).updateTexture(index);
					return this.hatch.add((GTMTE_OreHatch) mte);
				} else {
					return false;
				}
			}
		}
	}
	
	private boolean checkEnrich(IGregTechTileEntity te, int index) {
		if (te == null) {
			return false;
		} else {
			IMetaTileEntity mte = te.getMetaTileEntity();
			if (mte == null) {
				return false;
			} else {
				if (mte instanceof GT_MetaTileEntity_Hatch) {
					((GT_MetaTileEntity_Hatch) mte).updateTexture(index);
				}
				if (mte instanceof GTMTE_EnrichmentUnit) {
					((GTMTE_EnrichmentUnit) mte).updateTexture(index);
					return this.enrich.add((GTMTE_EnrichmentUnit) mte);
				} else {
					return false;
				}
			}
		}
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity te, long aTick) {
		super.onPostTick(te, aTick);
		if (te.isClientSide()) {
			if (te.isActive()) {
				te.getWorld().spawnParticle("largesmoke",
						te.getOffsetX((byte) 0, 1) + XSTR.XSTR_INSTANCE.nextFloat(),
						te.getOffsetY((byte) 0, 2),
						te.getOffsetZ((byte) 0, 1) + XSTR.XSTR_INSTANCE.nextFloat(),
						0.1D, 0.6D, 0.1D
				);
			}
		} else {
			if (te.isActive()) {
				if (aTick % 20 == 5 && hatch.get(0) != null) {
					if (!hatch.get(0).ready && hatch.get(0).drillCoefficient == 0) {
						stopMachine();
					}
				}
			}
		}
	}
	
	private int byDrillHead(ItemStack stack) {
		return stack.stackSize + hatch.get(0).drillCoefficient;
	}
	
	@Override
	public boolean checkRecipe(ItemStack aStack) {
		long start = System.currentTimeMillis();
		if (hatch.get(0) == null) return false;
		if (oreVeinGenerator == null) return false;
		if (oreVein == OreGenerator.empty) return false;
		boolean check = hatch.get(0).ready;
		if ((sizeVeinPreStart - cycleIncrease) <= 0) {
			increaseLayer(getBaseMetaTileEntity());
			initOreProperty(getBaseMetaTileEntity());
			return false;
		}
		if (check) {
			List<ItemStack> output = new ArrayList<>();
			List<ItemStack> outputEnrich = new ArrayList<>();
			IGregTechTileEntity te = getBaseMetaTileEntity();
			int chancePrimary = 500;
//			long voltage = getMaxInputVoltage();
			long voltage = 1920;
			if (!depleteInput(new FluidStack(ItemList.sDrillingFluid, 50))) {
				return false;
			}
			if (oreVein.specialFluid != null) {
				if (!depleteInput(oreVein.specialFluid)) {
					return false;
				}
			}
			List<ItemStack> is = oreVein.ores;
			for (int ore = 0; ore < is.size(); ore++) {
				if (te.getRandomNumber(10000) < oreVein.chanceOres[ore]) {
					ItemStack drillHeadDrop = new ItemStack(is.get(ore).getItem(), byDrillHead(is.get(ore)), is.get(ore).getItemDamage());
					outputEnrich.add(drillHeadDrop);
					GT_Recipe tRecipe = GT_Recipe.GT_Recipe_Map.sMaceratorRecipes
							.findRecipe(getBaseMetaTileEntity(), false, voltage, null, is.get(ore));
					if (tRecipe != null) {
						voltage-=tRecipe.mEUt;
						for (int i = 0; i < tRecipe.mOutputs.length; i++) {
							ItemStack recipeOutput = tRecipe.mOutputs[i].copy();
							if (i == 0 && te.getRandomNumber(10000) < chancePrimary) {
								output.add(recipeOutput);
							}
						}
					}
				}
			}
			
			if (enrich.size() > 0) {
				for (GTMTE_EnrichmentUnit e : enrich) {
					for (ItemStack stack : outputEnrich) {
						int size = stack.stackSize;
						for (int i = 0; i < size; i++) {
							if (e.getFluid() == null) break;
							FluidStack[] eFluid = new FluidStack[]{e.getFluid()};
							GT_Recipe tRecipe = GT_Recipe.GT_Recipe_Map.sFlotationUnitRecipes
									.findRecipe(this.getBaseMetaTileEntity(), false, false, voltage, eFluid, stack);
							if (tRecipe != null && tRecipe.isRecipeInputEqual(true, eFluid, stack)) {
								voltage -= tRecipe.mEUt;
								output.addAll(Arrays.asList(tRecipe.mOutputs));
								e.updateSlots();
							}
						}
						if (stack.stackSize > 0) {
							output.add(stack);
						}
					}
				}
			}
			
			if (output.isEmpty()) {
				output.addAll(outputEnrich);
			}
			
			this.mEfficiency         = getCurrentEfficiency(null);
			this.mEfficiencyIncrease = 10000;
			int tier = Math.max(1, GT_Utility.getTier(voltage));
//			this.mEUt = -3 * (1 << (tier << 1));
			this.mMaxProgresstime = 400 / (1 << (tier - 1));
			this.mMaxProgresstime = Math.max(2, this.mMaxProgresstime);
			this.mOutputItems     = output.toArray(new ItemStack[0]);
		}
		hatch.get(0).cycleDrill(check);
		this.cycleIncrease += 10000;
		impact.proxy.addClientSideChatMessages("" + (System.currentTimeMillis() - start));
		return check;
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("adv_miner");
		b.addInfo("0", "WIP").signAndFinalize();
		return b;
	}
	
	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_AdvancedMiner(mName);
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setInteger("cycleIncrease", cycleIncrease);
		aNBT.setInteger("sizeVeinPreStart", sizeVeinPreStart);
		aNBT.setInteger("layer", layer);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		cycleIncrease    = aNBT.getInteger("cycleIncrease");
		sizeVeinPreStart = aNBT.getInteger("sizeVeinPreStart");
		layer            = aNBT.getInteger("layer");
	}
	
	@Override
	public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
		IGregTechTileEntity te = getBaseMetaTileEntity();
		if (te.isServerSide()) {
			if (!aPlayer.isSneaking()) {
				increaseLayer(te);
				layer++;
				if (layer > 2) {
					layer = 0;
				}
				initOreProperty(te);
				GT_Utility.sendChatToPlayer(aPlayer, "Layer set: " + (layer));
			}
		}
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) { // TODO: 02.12.2021
		buildPiece(itemStack, b, 2, 9, 1);
	}
}