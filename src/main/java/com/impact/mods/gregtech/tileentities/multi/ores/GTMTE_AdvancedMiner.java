package com.impact.mods.gregtech.tileentities.multi.ores;

import com.impact.common.oregeneration.OreGenerator;
import com.impact.common.oregeneration.OreVein;
import com.impact.common.oregeneration.generator.OreChunkGenerator;
import com.impact.common.oregeneration.generator.OreVeinGenerator;
import com.impact.common.oregeneration.generator.OresRegionGenerator;
import com.impact.mods.gregtech.enums.Texture;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.mods.gregtech.tileentities.multi.ores.hatches.GTMTE_EnrichmentUnit;
import com.impact.mods.gregtech.tileentities.multi.ores.hatches.GTMTE_OreHatch;
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
import gregtech.api.objects.XSTR;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
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

public class GTMTE_AdvancedMiner extends GTMTE_Impact_BlockBase<GTMTE_AdvancedMiner> {
	

	private final List<GTMTE_OreHatch> hatch = new ArrayList<>();
	private final List<GTMTE_EnrichmentUnit> enrich = new ArrayList<>();
	public int cycleIncrease = 0, sizeVeinPreStart = 0, drillLevel = 0;
	public OreVeinGenerator oreVeinGenerator = null;
	public List<OreChunkGenerator> chunksGenerator = new ArrayList<>();
	public OreVein oreVein = OreGenerator.empty;
	public int layer = 1;
	public double cashedDecrement = 0d;
	static Block CASING = GregTech_API.sBlockCasings8;
	static byte CASING_META = 3;
	static ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[1][48 + CASING_META];
	static int CASING_TEXTURE_ID = CASING_META + 48 + 128;
	private GT_Recipe cashedMaceratorRecipe;
	private GT_Recipe cashedFlotationRecipe;
	
	static IStructureDefinition<GTMTE_AdvancedMiner> definition =
			StructureDefinition.<GTMTE_AdvancedMiner>builder()
					.addShape("main", new String[][]{
							{"       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", " B   B ", " B   B ", " B   B "},
							{"       ", "       ", "       ", "       ", "       ", "       ", "       ", "  B B  ", "  B B  ", " BB BB ", " BA~AB ", "BBBBBBB", "B     B", "B     B"},
							{"       ", "       ", "       ", "   B   ", "   B   ", "   B   ", "  AAA  ", " BBBBB ", " B   B ", " B   B ", " AAAAA ", " B   B ", "       ", "       "},
							{"   B   ", "   B   ", "   B   ", "  BAB  ", "  BAB  ", "  BAB  ", "  AAA  ", "  B B  ", "       ", "       ", " AACAA ", " B   B ", "       ", "       "},
							{"       ", "       ", "       ", "   B   ", "   B   ", "   B   ", "  AAA  ", " BBBBB ", " B   B ", " B   B ", " AAAAA ", " B   B ", "       ", "       "},
							{"       ", "       ", "       ", "       ", "       ", "       ", "       ", "  B B  ", "  B B  ", " BB BB ", " BAAAB ", "BBBBBBB", "B     B", "B     B"},
							{"       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", " B   B ", " B   B ", " B   B "}
					})
					.addElement('B', lazy(t -> ofFrame(Materials.HSLA)))
					.addElement('C', ofHatchAdder(GTMTE_AdvancedMiner::checkHatch, CASING_TEXTURE_ID, ImpactAPI.RED))
					.addElement('A', ofChain(
									ofBlock(CASING, CASING_META),
									ofHatchAdder(GTMTE_AdvancedMiner::addToMachineList, CASING_TEXTURE_ID, CASING, CASING_META),
									ofHatchAdder(GTMTE_AdvancedMiner::checkEnrich, CASING_TEXTURE_ID, CASING, CASING_META)
							)
					)
					.build();
	
	public GTMTE_AdvancedMiner(int aID, String aNameRegional) {
		super(aID, "impact.multis.miner.advanced", aNameRegional);
	}
	
	public GTMTE_AdvancedMiner(String aName) {
		super(aName);
	}
	
	@Override
	public void onFirstTick(IGregTechTileEntity te) {
		super.onFirstTick(te);
		increaseLayer(te);
		initOreProperty(te);
	}
	
	public void increaseLayer(IGregTechTileEntity te) {
		if (te.isServerSide()) {
			if (cycleIncrease <= 0) return;
			for (OreChunkGenerator oreChunkGenerator : chunksGenerator) {
				if (oreChunkGenerator.sizeOreChunk >= cycleIncrease) {
					oreChunkGenerator.sizeOreChunk -= cycleIncrease;
					cycleIncrease = 0;
					checkEmptyChunk(oreChunkGenerator);
					break;
				} else {
					int preSize = cycleIncrease - oreChunkGenerator.sizeOreChunk;
					oreChunkGenerator.sizeOreChunk -= preSize;
					cycleIncrease -= preSize;
					checkEmptyChunk(oreChunkGenerator);
				}
			}
		}
	}
	
	private void checkEmptyChunk(OreChunkGenerator chunk) {
		if (chunk.sizeOreChunk <= 0) {
			chunk.sizeOreChunk = 0;
		}
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
		return aSide == aFacing ? new ITexture[]{INDEX_CASE, TextureFactory.of(aActive ? Texture.Icons.OVERLAY_BASIC_MINER_ACTIVE : Texture.Icons.OVERLAY_BASIC_MINER)} : new ITexture[]{INDEX_CASE};
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
		boolean check = checkPiece(3, 10, 1);
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
				if (aTick % 20 == 5 && hatch.size() > 0 && hatch.get(0) != null) {
					if (!hatch.get(0).ready && hatch.get(0).drillCoefficient == 0) {
						stopMachine();
					}
				}
			}
		}
	}
	
	private int byDrillHead(ItemStack stack) {
		drillLevel = hatch.get(0).drillCoefficient;
		return stack.stackSize + drillLevel;
	}
	
	@Override
	public boolean checkRecipe(ItemStack aStack) {
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
			int tier = Math.max(1, getTierEnergyHatch());
			int startEU = 3 * (2 << (tier << 1));
			List<ItemStack> output = new ArrayList<>();
			List<ItemStack> outputEnrich = new ArrayList<>();
			IGregTechTileEntity te = getBaseMetaTileEntity();
			int chancePrimary = 5000;
			int euTotal = 0;
			long voltage = getMaxInputVoltage();
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
							.findRecipe(getBaseMetaTileEntity(), cashedMaceratorRecipe, false, voltage, null, new ItemStack[]{is.get(ore)});
					if (tRecipe != null) {
						cashedMaceratorRecipe = tRecipe;
						if (voltage <= euTotal + startEU + tRecipe.mEUt) break;
						euTotal += tRecipe.mEUt;
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
									.findRecipe(this.getBaseMetaTileEntity(), cashedFlotationRecipe, false, false, voltage, eFluid, new ItemStack[]{stack});
							if (tRecipe != null && tRecipe.isRecipeInputEqual(true, eFluid, stack)) {
								cashedFlotationRecipe = tRecipe;
								if (voltage <= euTotal + startEU + tRecipe.mEUt) break;
								euTotal += tRecipe.mEUt;
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
			this.mEUt                = -(startEU + euTotal);
			this.mMaxProgresstime    = 400 / (1 << (tier - 1));
			this.mMaxProgresstime    = Math.max(2, this.mMaxProgresstime);
			this.mOutputItems        = output.toArray(new ItemStack[0]);
		}
		hatch.get(0).cycleDrill(check);
		
		double utilization = 1.0D;
		for (int i = 0; i < drillLevel; i++) {
			utilization *= 0.9D;
		}
		
		double aa = utilization + cashedDecrement;
		cycleIncrease = (int) Math.floor(aa);
		cashedDecrement = aa - cycleIncrease;
		
		if (cycleIncrease >= 1) {
			sizeVeinPreStart -= cycleIncrease;
			increaseLayer(getBaseMetaTileEntity());
		}
		return check;
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("adv_miner");
		b		.addInfo("info.0", "Factorio Miner??")
				.addTypeMachine("name", "Miner")
				.addInfo("info.1", "Mining takes place in 16 (4x4 zone) Chunks (Full Vein)")
				.addInfo("info.2", "There is only ONE miner in one Chunk")
				.addInfo("info.3", "Use a screwdriver to change the Ore Layer (0-2)")
				.addSeparator()
				.addController()
				.sizeStructure(7, 13, 7)
				.addMaintenanceHatch()
				.addOutputBus(1)
				.addInputHatch(1)
				.addEnergyHatch(2)
				.addCasingInfo("case.0", "HSLA Machine Casing", 25)
				.addOtherStructurePart("case.1", "HSLA Steel Frame Box", "case.2", "Any Frame Box")
				.addOtherStructurePartAny("case.3", "Miner Enrichment Unit")
				.addRedHint("Miner Drill Hatch")
				.signAndFinalize()	;
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
		aNBT.setInteger("drillLevel", drillLevel);
		aNBT.setDouble("cashedDecrement", cashedDecrement);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		cycleIncrease    = aNBT.getInteger("cycleIncrease");
		sizeVeinPreStart = aNBT.getInteger("sizeVeinPreStart");
		layer            = aNBT.getInteger("layer");
		drillLevel       = aNBT.getInteger("drillLevel");
		cashedDecrement  = aNBT.getDouble("cashedDecrement");
	}
	
	@Override
	public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
		IGregTechTileEntity te = getBaseMetaTileEntity();
		if (te.isServerSide()) {
			if (!aPlayer.isSneaking()) {
				increaseLayer(te);
				layer++;
				if (layer >= OresRegionGenerator.layers) {
					layer = 0;
				}
				initOreProperty(getBaseMetaTileEntity());
				GT_Utility.sendChatToPlayer(aPlayer, "Layer set: " + (layer));
			}
		}
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 3, 10, 1);
	}
	
	@Override
	public boolean hasSeparate() {
		return false;
	}
}