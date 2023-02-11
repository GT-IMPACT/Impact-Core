package com.impact.mods.gregtech.tileentities.multi.ores;

import com.impact.common.oregeneration.OreGenerator;
import com.impact.common.oregeneration.OreVein;
import com.impact.common.oregeneration.generator.OreChunkGenerator;
import com.impact.common.oregeneration.generator.OresRegionGenerator;
import com.impact.mods.gregtech.enums.Texture;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
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
import java.util.List;

import static com.impact.util.multis.GT_StructureUtility.ofFrame;
import static com.impact.util.multis.GT_StructureUtility.ofHatchAdder;
import static space.impact.api.multiblocks.structure.StructureUtility.*;

public class GTMTE_BasicMiner extends GTMTE_Impact_BlockBase<GTMTE_BasicMiner> {
	
	static IStructureDefinition<GTMTE_BasicMiner> definition =
			StructureDefinition.<GTMTE_BasicMiner>builder()
					.addShape("main", new String[][]{
							{"     ", "     ", "     ", "     ", "     ", "     ", "     ", "     ", "     ", " G G ", " G G ", " G G "},
							{"     ", "     ", "     ", "  G  ", "  G  ", "  G  ", " GAG ", " G G ", " G G ", "GA~AG", "G   G", "G   G"},
							{"  G  ", "  G  ", "  G  ", " GAG ", " GAG ", " GAG ", " AAA ", "     ", "     ", " ACA ", "     ", "     "},
							{"     ", "     ", "     ", "  G  ", "  G  ", "  G  ", " GAG ", " G G ", " G G ", "GAAAG", "G   G", "G   G"},
							{"     ", "     ", "     ", "     ", "     ", "     ", "     ", "     ", "     ", " G G ", " G G ", " G G "},
					})
					.addElement('G', lazy(t -> ofFrame(Materials.Steel)))
					.addElement('C', ofHatchAdder(GTMTE_BasicMiner::checkHatch, 16, ImpactAPI.RED))
					.addElement('A', ofChain(
									ofBlock(GregTech_API.sBlockCasings2, 0),
									ofHatchAdder(GTMTE_BasicMiner::addToMachineList, 16, GregTech_API.sBlockCasings2, 0)
							)
					)
					.build();
	private final List<GTMTE_OreHatch> hatch = new ArrayList<>();
	public int cycleIncrease = 0, sizeVeinPreStart = 0, drillLevel = 0;
	public OreChunkGenerator oreChunkGenerator = null;
	public OreVein oreVein = OreGenerator.empty;
	public int layer = 1;
	public double cashedDecrement = 0d;
	ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][16];
	
	public GTMTE_BasicMiner(int aID, String aNameRegional) {
		super(aID, "impact.multis.miner.basic", aNameRegional);
	}
	
	public GTMTE_BasicMiner(String aName) {
		super(aName);
	}
	
	@Override
	public void onFirstTick(IGregTechTileEntity te) {
		super.onFirstTick(te);
		initOreProperty(te);
		increaseLayer(te);
	}
	
	public void increaseLayer(IGregTechTileEntity te) {
		if (te.isServerSide()) {
			oreChunkGenerator.sizeOreChunk -= cycleIncrease;
			if (oreChunkGenerator.sizeOreChunk <= 0) {
				oreChunkGenerator.sizeOreChunk = 0;
			}
			cycleIncrease = 0;
		}
	}
	
	public void initOreProperty(IGregTechTileEntity te) {
		if (te.isServerSide()) {
			oreChunkGenerator = OreGenerator.getChunkFromIGT(te, layer);
			if (oreChunkGenerator != null) {
				sizeVeinPreStart = oreChunkGenerator.sizeOreChunk;
				if (sizeVeinPreStart > 0) {
					oreVein = OreGenerator.getOreVein(te, layer);
				} else {
					oreVein = OreGenerator.empty;
				}
			}
		}
	}
	
	@Override
	public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
		return aSide == aFacing ? new ITexture[]{INDEX_CASE, TextureFactory.of(aActive ? Texture.Icons.OVERLAY_BASIC_MINER_ACTIVE : Texture.Icons.OVERLAY_BASIC_MINER)} : new ITexture[]{INDEX_CASE};
	}
	
	@Override
	public IStructureDefinition<GTMTE_BasicMiner> getStructureDefinition() {
		return definition;
	}
	
	@Override
	public void onRemoval() {
		super.onRemoval();
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
					if (meta instanceof GTMTE_BasicMiner) {
						size++;
					}
				}
			}
		}
		if (size > 1) return false;
		hatch.clear();
		boolean check = checkPiece(2, 9, 1);// TODO: 02.12.2021
		if (hatch.size() != 1) check = false;
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
				if (aTick % 20 == 5 && !hatch.isEmpty() && hatch.get(0) != null) {
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
		if (oreChunkGenerator == null || oreChunkGenerator.sizeOreChunk <= 0) return false;
		if (oreVein == OreGenerator.empty) return false;
		boolean check = hatch.get(0).ready;
		if ((sizeVeinPreStart - cycleIncrease) <= 0) {
			increaseLayer(getBaseMetaTileEntity());
			initOreProperty(getBaseMetaTileEntity());
			return false;
		}
		if (check) {
			List<ItemStack> output = new ArrayList<>();
			IGregTechTileEntity te = getBaseMetaTileEntity();
			int chancePrimary = 500;
			int tier = Math.max(1, getTierEnergyHatch());
			int startEU = 3 * (2 << (tier << 1));
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
					output.add(drillHeadDrop);
					GT_Recipe tRecipe = GT_Recipe.GT_Recipe_Map.sMaceratorRecipes.findRecipe(getBaseMetaTileEntity(), false, voltage, null, is.get(ore));
					if (tRecipe != null) {
						for (int i = 0; i < tRecipe.mOutputs.length; i++) {
							ItemStack recipeOutput = tRecipe.mOutputs[i].copy();
							if (i == 0 && te.getRandomNumber(10000) < chancePrimary) {
								output.add(recipeOutput);
							}
						}
					}
				}
			}
			this.mEfficiency         = getCurrentEfficiency(null);
			this.mEfficiencyIncrease = 10000;
			this.mEUt                = -startEU;
			this.mMaxProgresstime    = 400 / (1 << (tier - 1));
			this.mMaxProgresstime    = Math.max(2, this.mMaxProgresstime);
			this.mOutputItems        = output.toArray(new ItemStack[0]);
		}
		GTMTE_OreHatch oreHatch = hatch.get(0);
		if (oreHatch != null) {
			oreHatch.cycleDrill(check);
		}
		
		double utilization = 1.0D;
		for (int i = 0; i < drillLevel; i++) {
			utilization *= 0.9D;
		}
		
		double aa = utilization + cashedDecrement;
		cycleIncrease   = (int) Math.floor(aa);
		cashedDecrement = aa - cycleIncrease;
		
		if (cycleIncrease >= 1) {
			sizeVeinPreStart -= cycleIncrease;
			increaseLayer(getBaseMetaTileEntity());
		}
		return check;
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("basic_miner");
		b.addInfo("info.0", "Factorio Miner??")
				.addTypeMachine("name", "Miner")
				.addInfo("info.1", "Mining takes place in current Chunk")
				.addInfo("info.2", "There is only ONE miner in one Chunk")
				.addInfo("info.3", "Use a screwdriver to change the Ore Layer (0-1)")
				.addSeparator()
				.addController()
				.sizeStructure(5, 12, 5)
				.addMaintenanceHatch()
				.addOutputBus(1)
				.addInputHatch(1)
				.addEnergyHatch(2)
				.addCasingInfo("case.0", "Solid Steel Machine Casing", 11)
				.addCasingInfo("case.1", "Steel Frame Box", 51)
				.addRedHint("Miner Drill Hatch")
				.signAndFinalize();
		return b;
	}
	
	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_BasicMiner(mName);
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
				initOreProperty(te);
				GT_Utility.sendChatToPlayer(aPlayer, "Layer set: " + (layer));
			}
		}
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) { // TODO: 02.12.2021
		buildPiece(itemStack, b, 2, 9, 1);
	}
	
	@Override
	public boolean hasSeparate() {
		return false;
	}
}