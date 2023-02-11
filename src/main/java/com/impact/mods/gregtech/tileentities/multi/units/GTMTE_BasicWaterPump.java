package com.impact.mods.gregtech.tileentities.multi.units;

import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.tileentities.hatches.GT_MetaTileEntity_Primitive_Hatch_Output;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_ModHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import space.impact.api.ImpactAPI;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import java.util.ArrayList;

import static com.impact.mods.gregtech.blocks.Casing_Helper.sCaseCore2;
import static com.impact.util.multis.GT_StructureUtility.ofFrame;
import static com.impact.util.multis.GT_StructureUtility.ofHatchAdder;
import static gregtech.api.enums.GT_Values.W;
import static net.minecraftforge.common.BiomeDictionary.Type.*;
import static space.impact.api.multiblocks.structure.StructureUtility.*;

public class GTMTE_BasicWaterPump extends GTMTE_Impact_BlockBase<GTMTE_BasicWaterPump> {
	
	public ArrayList<GT_MetaTileEntity_Primitive_Hatch_Output> mOutputHatches1 = new ArrayList<>();
	
	protected int boimeWater;
	static Block CASING = Casing_Helper.sCaseCore2;
	static byte CASING_META = 7;
	static ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][16 + CASING_META];
	static int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;
	static IStructureDefinition<GTMTE_BasicWaterPump> definition =
			StructureDefinition.<GTMTE_BasicWaterPump>builder()
					.addShape("main", new String[][]{
							{" 1  ", " 1  ", "000 ",},
							{"1111", "1  1", "0200",},
							{" 1  ", " 1  ", "0000",},
					})
					.addElement('1', lazy(t -> ofFrame(Materials.Wood)))
					.addElement('0', ofBlock(sCaseCore2, 7))
					.addElement('2', ofChain(
							ofHatchAdder(GTMTE_BasicWaterPump::addPrimOutputToMachineList, CASING_TEXTURE_ID, ImpactAPI.BLUE),
							ofHatchAdder(GTMTE_BasicWaterPump::addOutputToMachineList, CASING_TEXTURE_ID, ImpactAPI.BLUE)
					))
					.build();
	
	public GTMTE_BasicWaterPump(int aID, String aNameRegional) {
		super(aID, "impact.multimachine.basicwaterpump", aNameRegional);
	}
	
	public GTMTE_BasicWaterPump(String aName) {
		super(aName);
	}
	
	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		return aSide == aFacing ? new ITexture[]{INDEX_CASE, TextureFactory.of(Textures.BlockIcons.OVERLAY_PUMP)} : new ITexture[]{INDEX_CASE};
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_BasicWaterPump(this.mName);
	}
	
	@Override
	protected MultiBlockTooltipBuilder getTooltip() {
		final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("primitive_pump");
		b
				.addInfo("info.0", "Drilling water from ground")
				.addTypeMachine("name", "Water Pump")
				.addInfo("info.1", "Water output depends on the hatch")
				.addInfo("info.2", "Pump Hatch: Biome Coefficient * 1")
				.addInfo("info.3", "ULV Output Hatch: Biome Coefficient * 2")
				.addInfo("info.4", "LV Output Hatch: Biome Coefficient * 3")
				.addSeparator()
				.addinfoB("info.5", "Biome Coefficient:")
				.addinfoBTab("info.6", "Ocean, River - 1000 L/s")
				.addinfoBTab("info.7", "Taiga - 175 L/s")
				.addinfoBTab("info.8", "Jungle - 350 L/s")
				.addinfoBTab("info.9", "Swampland - 800 L/s")
				.addinfoBTab("info.10", "Snow, Iceland - 300 L/s")
				.addinfoBTab("info.11", "Beach - 170 L/s")
				.addinfoBTab("info.12", "Plans, Forest - 250 L/s")
				.addinfoBTab("info.13", "Hills, Mountains, Savana, Desert, Mesa - 100 L/s")
				.addSeparator()
				.addController()
				.addCasingInfo("case", "Primitive Pump Deck and Wood Frame Box")
				.signAndFinalize(true);
		return b;
	}
	
	@Override
	public IStructureDefinition<GTMTE_BasicWaterPump> getStructureDefinition() {
		return definition;
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return null;
	}
	
	@Override
	public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return null;
	}
	
	@Override
	public boolean checkRecipe(ItemStack aStack) {
		this.mEfficiency = 10000;
		if (this.mEfficiency > 0) {
			if (getTierFluidHatch() == 0) {
				addOutput1(GT_ModHandler.getWater(getWaterInBiomes() * (getTierFluidHatch() + 1)));
			}
			addOutput(GT_ModHandler.getWater(getWaterInBiomes() * (getTierFluidHatch() + 1)));
		}
		this.mEUt             = 0;
		this.mMaxProgresstime = 20;
		this.mWrench          = true;
		this.mScrewdriver     = true;
		this.mSoftHammer      = true;
		this.mHardHammer      = true;
		this.mSolderingTool   = true;
		this.mCrowbar         = true;
		return true;
	}
	
	public Vector3ic rotateOffsetVector(Vector3ic forgeDirection, int x, int y, int z) {
		final Vector3i offset = new Vector3i();
		
		// В любом направлении по оси Z
		if (forgeDirection.x() == 0 && forgeDirection.z() == -1) {
			offset.x = x;
			offset.y = y;
			offset.z = z;
		}
		if (forgeDirection.x() == 0 && forgeDirection.z() == 1) {
			offset.x = -x;
			offset.y = y;
			offset.z = -z;
		}
		// В любом направлении по оси X
		if (forgeDirection.x() == -1 && forgeDirection.z() == 0) {
			offset.x = z;
			offset.y = y;
			offset.z = -x;
		}
		if (forgeDirection.x() == 1 && forgeDirection.z() == 0) {
			offset.x = -z;
			offset.y = y;
			offset.z = x;
		}
		// в любом направлении по оси Y
		if (forgeDirection.y() == -1) {
			offset.x = x;
			offset.y = z;
			offset.z = y;
		}
		
		return offset;
	}
	
	public boolean machineStructure(IGregTechTileEntity thisController) {
		mOutputHatches1.clear();
		if (BiomeDictionary.isBiomeOfType(thisController.getBiome(), WATER)) {
			this.boimeWater = 1000;
		}
		
		if (BiomeDictionary.isBiomeOfType(thisController.getBiome(), CONIFEROUS)) {
			this.boimeWater = 175;
		}
		
		if (BiomeDictionary.isBiomeOfType(thisController.getBiome(), JUNGLE)) {
			this.boimeWater = 350;
		}
		
		if (BiomeDictionary.isBiomeOfType(thisController.getBiome(), SWAMP)) {
			this.boimeWater = 800;
		}
		
		if (BiomeDictionary.isBiomeOfType(thisController.getBiome(), SNOWY)) {
			this.boimeWater = 300;
		}
		
		if (BiomeDictionary.isBiomeOfType(thisController.getBiome(), BEACH)) {
			this.boimeWater = 170;
		}
		
		if (BiomeDictionary.isBiomeOfType(thisController.getBiome(), PLAINS) || BiomeDictionary.isBiomeOfType(thisController.getBiome(), FOREST)) {
			this.boimeWater = 250;
		}
		
		if (BiomeDictionary.isBiomeOfType(thisController.getBiome(), HILLS)
				|| BiomeDictionary.isBiomeOfType(thisController.getBiome(), MOUNTAIN)
				|| BiomeDictionary.isBiomeOfType(thisController.getBiome(), SAVANNA)
				|| BiomeDictionary.isBiomeOfType(thisController.getBiome(), SANDY)
				|| BiomeDictionary.isBiomeOfType(thisController.getBiome(), MESA)) {
			this.boimeWater = 100;
		}
		
		int frameMeta = GregTech_API.METATILEENTITIES[4905] != null ? GregTech_API.METATILEENTITIES[4905].getTileEntityBaseType() : W;
		
		// Вычисляем вектор направления, в котором находится задняя поверхность контроллера
		final Vector3ic forgeDirection = new Vector3i(
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ
		);
		
		int minCasingAmount = 12; // Минимальное количество кейсов
		boolean formationChecklist = true; // Если все ок, машина собралась
		
		for (byte X = 0; X >= -3; X--) {
			for (byte Z = 0; Z >= -2; Z--) {
				
				if (X == 0 && Z == 0) {
					continue;
				}
				
				final Vector3ic offset = rotateOffsetVector(forgeDirection, X, 0, Z);
				
				if (X == -2 && Z == -1) {
					IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
					if ((!super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID))
							&& (!addPrimOutputToMachineList(currentTE, CASING_TEXTURE_ID))) {
						if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
								&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
						} else {
							formationChecklist = false;
						}
					}
					continue;
				}
				
				if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
						&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
					minCasingAmount--;
				} else {
					formationChecklist = false;
					
				}
			}
		}
		
		final Vector3ic offset = rotateOffsetVector(forgeDirection, -2, 1, 0);
		if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == GregTech_API.sBlockMachines)
				&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == frameMeta)) {
			minCasingAmount--;
		} else {
			formationChecklist = false;
		}
		
		final Vector3ic offset2 = rotateOffsetVector(forgeDirection, -2, 1, -2);
		if ((thisController.getBlockOffset(offset2.x(), offset2.y(), offset2.z()) == GregTech_API.sBlockMachines)
				&& (thisController.getMetaIDOffset(offset2.x(), offset2.y(), offset2.z()) == frameMeta)) {
			minCasingAmount--;
		} else {
			formationChecklist = false;
		}
		
		final Vector3ic offset3 = rotateOffsetVector(forgeDirection, -3, 1, -1);
		if ((thisController.getBlockOffset(offset3.x(), offset3.y(), offset3.z()) == GregTech_API.sBlockMachines)
				&& (thisController.getMetaIDOffset(offset3.x(), offset3.y(), offset3.z()) == frameMeta)) {
			minCasingAmount--;
		} else {
			formationChecklist = false;
		}
		
		final Vector3ic offset4 = rotateOffsetVector(forgeDirection, 0, 1, -1);
		if ((thisController.getBlockOffset(offset4.x(), offset4.y(), offset4.z()) == GregTech_API.sBlockMachines)
				&& (thisController.getMetaIDOffset(offset4.x(), offset4.y(), offset4.z()) == frameMeta)) {
			minCasingAmount--;
		} else {
			formationChecklist = false;
		}
		
		final Vector3ic offset5 = rotateOffsetVector(forgeDirection, -2, 2, 0);
		if ((thisController.getBlockOffset(offset5.x(), offset5.y(), offset5.z()) == GregTech_API.sBlockMachines)
				&& (thisController.getMetaIDOffset(offset5.x(), offset5.y(), offset5.z()) == frameMeta)) {
			minCasingAmount--;
		} else {
			formationChecklist = false;
		}
		
		final Vector3ic offset6 = rotateOffsetVector(forgeDirection, -2, 2, -2);
		if ((thisController.getBlockOffset(offset6.x(), offset6.y(), offset6.z()) == GregTech_API.sBlockMachines)
				&& (thisController.getMetaIDOffset(offset6.x(), offset6.y(), offset6.z()) == frameMeta)) {
			minCasingAmount--;
		} else {
			formationChecklist = false;
		}
		
		for (byte X = 0; X >= -3; X--) {
			final Vector3ic offset7 = rotateOffsetVector(forgeDirection, X, 2, -1);
			if ((thisController.getBlockOffset(offset7.x(), offset7.y(), offset7.z()) == GregTech_API.sBlockMachines)
					&& (thisController.getMetaIDOffset(offset7.x(), offset7.y(), offset7.z()) == frameMeta)) {
				minCasingAmount--;
			} else {
				formationChecklist = false;
			}
		}
		
		mWrench        = true;
		mScrewdriver   = true;
		mSoftHammer    = true;
		mHardHammer    = true;
		mSolderingTool = true;
		mCrowbar       = true;
		
		if (getTierFluidHatch() > 2) {
			formationChecklist = false;
		}
		
		return formationChecklist;
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		return null;
	}
	
	public int getTierFluidHatch() {
		int Tier = 0;
		for (GT_MetaTileEntity_Hatch_Output tHatch : mOutputHatches) {
			if (isValidMetaTileEntity(tHatch)) {
				Tier = tHatch.mTier + 1;
			}
		}
		for (GT_MetaTileEntity_Primitive_Hatch_Output tHatch : mOutputHatches1) {
			if (isValidMetaTileEntity(tHatch)) {
				Tier = tHatch.mTier;
			}
		}
		return Tier;
	}
	
	public int getWaterInBiomes() {
		return this.boimeWater;
	}
	
	public int Parallel() {
		return 0;
	}
	
	@Override
	public int getPollutionPerTick(ItemStack aStack) {
		return 0;
	}
	
	private boolean dumpFluid(FluidStack copiedFluidStack, boolean restrictiveHatchesOnly) {
		for (GT_MetaTileEntity_Primitive_Hatch_Output tHatch : mOutputHatches1) {
			if (!isValidMetaTileEntity(tHatch) || (restrictiveHatchesOnly && tHatch.mMode == 0)) {
				continue;
			}
			if (GT_ModHandler.isSteam(copiedFluidStack)) {
				if (!tHatch.outputsSteam()) {
					continue;
				}
			} else {
				if (!tHatch.outputsLiquids()) {
					continue;
				}
				if (tHatch.isFluidLocked() && tHatch.getLockedFluidName() != null && !tHatch
						.getLockedFluidName().equals(copiedFluidStack.getUnlocalizedName())) {
					continue;
				}
			}
			int tAmount = tHatch.fill(copiedFluidStack, false);
			if (tAmount >= copiedFluidStack.amount) {
				boolean filled = tHatch.fill(copiedFluidStack, true) >= copiedFluidStack.amount;
				tHatch.onEmptyingContainerWhenEmpty();
				return filled;
			} else if (tAmount > 0) {
				copiedFluidStack.amount = copiedFluidStack.amount - tHatch.fill(copiedFluidStack, true);
				tHatch.onEmptyingContainerWhenEmpty();
			}
		}
		return false;
	}
	
	public boolean addOutput1(FluidStack aLiquid) {
		if (aLiquid == null) {
			return false;
		} else {
			FluidStack copiedFluidStack = aLiquid.copy();
			if (!this.dumpFluid(copiedFluidStack, true)) {
				this.dumpFluid(copiedFluidStack, false);
			}
			
			return false;
		}
	}
	
	public boolean addPrimOutputToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
		if (aTileEntity == null) {
			return false;
		}
		IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
		if (aMetaTileEntity == null) {
			return false;
		}
		if (aMetaTileEntity instanceof GT_MetaTileEntity_Primitive_Hatch_Output) {
			((GT_MetaTileEntity_Hatch) aMetaTileEntity).updateTexture(aBaseCasingIndex);
			return mOutputHatches1.add((GT_MetaTileEntity_Primitive_Hatch_Output) aMetaTileEntity);
		}
		return false;
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 3, 2, 0);
	}
	
	@Override
	public boolean hasSeparate() {
		return false;
	}
}