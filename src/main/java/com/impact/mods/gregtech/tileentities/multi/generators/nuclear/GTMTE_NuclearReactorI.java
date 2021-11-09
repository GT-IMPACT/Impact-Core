package com.impact.mods.gregtech.tileentities.multi.generators.nuclear;

import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.GregTech_API;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.decorateBlock;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlock;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlockHint;

public class GTMTE_NuclearReactorI extends GTMTE_NuclearReactorBase<GTMTE_NuclearReactorI> {
	
	static final Block GENERAL_CASING = GregTech_API.sBlockCasings3;
	static final int GENERAL_CASING_META = 12;
	static final Block SECOND_CASING = GregTech_API.sBlockCasings2;
	static final int SECOND_CASING_META = 13;
	static final int TEXTURE_HATCH = 44;
	static IStructureDefinition<GTMTE_NuclearReactorBase<GTMTE_NuclearReactorI>> definition =
			StructureDefinition.<GTMTE_NuclearReactorBase<GTMTE_NuclearReactorI>>builder()
					.addShape("main", new String[][]{
							{"     ", "  A  ", " AAA ", " A~A "},
							{" AAA ", " BAB ", "AB BA", "AAAAA"},
							{" ACA ", "AA AA", "A   A", "AAAAA"},
							{" AAA ", " BAB ", "AB BA", "AAAAA"},
							{"     ", "  A  ", " AAA ", " AAA "}
					})
					.addElement('A', ofBlock(GENERAL_CASING, GENERAL_CASING_META))
					.addElement('B', ofBlock(SECOND_CASING, SECOND_CASING_META))
					.addElement('C', ofBlockHint(decorateBlock[2], 1))
					.build();
	
	public GTMTE_NuclearReactorI(int aID, String aNameRegional) {
		super(aID, "impact.multis.nuclear1", aNameRegional);
	}
	
	public GTMTE_NuclearReactorI(String aName) {
		super(aName);
	}
	
	@Override
	public IStructureDefinition<GTMTE_NuclearReactorBase<GTMTE_NuclearReactorI>> getStructureDefinition() {
		return definition;
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_NuclearReactorI(super.mName);
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 2, 3, 0);
	}
	
	@Override
	int tierReactor() {
		return 1;
	}
	
	@Override
	public int maxTemperature() {
		return 10_000;
	}
	
	@Override
	public int coefficientReactor() {
		return 200;
	}
	

	
	public boolean checkMachineFunction(IGregTechTileEntity thisController) {
		this.mWrench        = true;
		this.mScrewdriver   = true;
		this.mSoftHammer    = true;
		this.mHardHammer    = true;
		this.mSolderingTool = true;
		this.mCrowbar       = true;
		boolean checkStructure = true;
		int x, y, z;
		final Vector3ic forgeDirection = new Vector3i(
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ
		);
		int ID = 0;
		for (x = -2; x <= 2; x++) {
			for (z = 0; z >= -4; z--) {
				if (x == 0 && z == 0) {
					continue;
				}
				if ((x == -2 || x == 2) && (z == 0 || z == -4)) {
					continue;
				}
				final Vector3ic offset = rotateOffsetVector(forgeDirection, x, 0, z);
				IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				
				if (!addToMachineList(currentTE, TEXTURE_HATCH) && !addMachineHull(currentTE)) {
					if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == GENERAL_CASING)
							&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == GENERAL_CASING_META)) {
					} else {
						checkStructure = false;
					}
				}
			}
		}
		
		for (x = -2; x <= 2; x++) {
			for (z = 0; z >= -4; z--) {
				if ((x == -2 || x == 2) && (z == 0 || z == -4)) {
					continue;
				}
				if (x == 0 && (z == -1 || z == -2 || z == -3)) {
					continue;
				}
				if ((x == -1 || x == 1) && z == -2) {
					continue;
				}
				
				
				final Vector3ic offset = rotateOffsetVector(forgeDirection, x, 1, z);
				if ((x == -1 || x == 1) && (z == -1 || z == -3)) {
					if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == SECOND_CASING)
							&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == SECOND_CASING_META)) {
					} else {
						checkStructure = false;
					}
					continue;
				}
				
				IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				if (!addToMachineList(currentTE, TEXTURE_HATCH)) {
					if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == GENERAL_CASING)
							&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == GENERAL_CASING_META)) {
					} else {
						checkStructure = false;
					}
				}
			}
		}
		
		for (x = -2; x <= 2; x++) {
			for (z = 0; z >= -4; z--) {
				if ((x == -2 || x == 2) && (z == 0 || z == -4)) {
					continue;
				}
				if (x == 0 && z == -2) {
					continue;
				}
				if ((x == -1 || x == 1) && (z == 0 || z == -4)) {
					continue;
				}
				
				if ((x == -2 || x == 2) && (z == -1 || z == -3)) {
					continue;
				}
				
				final Vector3ic offset = rotateOffsetVector(forgeDirection, x, 2, z);
				if ((x == -1 || x == 1) && (z == -1 || z == -3)) {
					if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == SECOND_CASING)
							&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == SECOND_CASING_META)) {
					} else {
						checkStructure = false;
					}
					continue;
				}
				
				IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				if (!addToMachineList(currentTE, TEXTURE_HATCH)) {
					if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == GENERAL_CASING)
							&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == GENERAL_CASING_META)) {
					} else {
						checkStructure = false;
					}
				}
			}
		}
		
		for (x = -1; x <= 1; x++) {
			for (z = -1; z >= -3; z--) {
				final Vector3ic offset = rotateOffsetVector(forgeDirection, x, 3, z);
				IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				
				if (x == 0 && z == -2) {
					if (checkRodHatches(currentTE, TEXTURE_HATCH, ID)) {
						ID++;
					} else {
						checkStructure = false;
					}
					continue;
				}
				if (!addToMachineList(currentTE, TEXTURE_HATCH)) {
					if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == GENERAL_CASING)
							&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == GENERAL_CASING_META)) {
					} else {
						checkStructure = false;
					}
				}
			}
		}
		
		if (mOutputHatches.size() > 6) {
			checkStructure = false;
		}
		
		if (mInputHatches.size() > 2) {
			checkStructure = false;
		}
		
		if (mMachineHull.size() > 1) {
			checkStructure = false;
		}
		
		return checkStructure;
	}
}