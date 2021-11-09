package com.impact.mods.gregtech.tileentities.multi.generators.nuclear;

import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.GregTech_API;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.decorateBlock;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlock;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlockHint;

public class GTMTE_NuclearReactorIII extends GTMTE_NuclearReactorBase<GTMTE_NuclearReactorIII> {
	
	static final Block GENERAL_CASING = GregTech_API.sBlockCasings3;
	static final int GENERAL_CASING_META = 12;
	static final Block SECOND_CASING = GregTech_API.sBlockCasings2;
	static final int SECOND_CASING_META = 13;
	static final int TEXTURE_HATCH = 44;
	static IStructureDefinition<GTMTE_NuclearReactorBase<GTMTE_NuclearReactorIII>> definition =
			StructureDefinition.<GTMTE_NuclearReactorBase<GTMTE_NuclearReactorIII>>builder()
					.addShape("main", new String[][]{
							{"         ", "    A    ", "   AAA   ", "   AAA   ", "  AAAAA  ", "  AA~AA  "},
							{"  AAAAA  ", "  BAAAB  ", "  B   B  ", "  B   B  ", " AB   BA ", " AAAAAAA "},
							{" AEEEEEA ", " B     B ", " B     B ", " B     B ", "AB     BA", "AAAAAAAAA"},
							{" AEEEEEA ", " A     A ", "A       A", "A       A", "A       A", "AAAAAAAAA"},
							{" AEEEEEA ", "AA     AA", "A       A", "A       A", "A       A", "AAAAAAAAA"},
							{" AEEEEEA ", " A     A ", "A       A", "A       A", "A       A", "AAAAAAAAA"},
							{" AEEEEEA ", " B     B ", " B     B ", " B     B ", "AB     BA", "AAAAAAAAA"},
							{"  AAAAA  ", "  BAAAB  ", "  B   B  ", "  B   B  ", " AB   BA ", " AAAAAAA "},
							{"         ", "    A    ", "   AAA   ", "   AAA   ", "  AAAAA  ", "  AAAAA  "}
					})
					.addElement('A', ofBlock(GENERAL_CASING, GENERAL_CASING_META))
					.addElement('B', ofBlock(SECOND_CASING, SECOND_CASING_META))
					.addElement('E', ofBlockHint(decorateBlock[2], 1))
					.build();
	
	public GTMTE_NuclearReactorIII(int aID, String aNameRegional) {
		super(aID, "impact.multis.nuclear3", aNameRegional);
	}
	
	public GTMTE_NuclearReactorIII(String aName) {
		super(aName);
	}
	
	@Override
	int tierReactor() {
		return 3;
	}
	
	@Override
	public int maxTemperature() {
		return 1_000_000;
	}
	
	@Override
	public int coefficientReactor() {
		return 1400;
	}
	
	@Override
	public IStructureDefinition<GTMTE_NuclearReactorBase<GTMTE_NuclearReactorIII>> getStructureDefinition() {
		return definition;
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_NuclearReactorIII(super.mName);
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 4, 5, 0);
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
		int ID = 0;
		final Vector3ic forgeDirection = new Vector3i(
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ
		);
		
		for (x = -4; x <= 4; x++) {
			for (z = 0; z >= -8; z--) {
				if (x == 0 && z == 0) {
					continue;
				}
				if ((x == -4 || x == -3 || x == 3 || x == 4) && (z == 0 || z == -8)) {
					continue;
				}
				if ((x == -4 || x == 4) && (z == -1 || z == -7)) {
					continue;
				}
				final Vector3ic offset = rotateOffsetVector(forgeDirection, x, 0, z);
				IGregTechTileEntity currentTE = thisController
						.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				if (!addToMachineList(currentTE, TEXTURE_HATCH) && !addMachineHull(currentTE)) {
					if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == GENERAL_CASING)
							&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == GENERAL_CASING_META)) {
					} else {
						checkStructure = false;
					}
				}
			}
		}
		
		for (x = -4; x <= 4; x++) {
			for (z = 0; z >= -8; z--) {
				if ((x == -4 || x == -3 || x == 3 || x == 4) && (z == 0 || z == -8)) {
					continue;
				}
				if ((x == -4 || x == 4) && (z == -1 || z == -7)) {
					continue;
				}
				final Vector3ic offset = rotateOffsetVector(forgeDirection, x, 1, z);
				IGregTechTileEntity currentTE = thisController
						.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				if (((x == -3 || x == 3) && (z == -2 || z == -6)) ||
						((x == -2 || x == 2) && (z == -1 || z == -7))) {
					if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == SECOND_CASING)
							&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == SECOND_CASING_META)) {
					} else {
						checkStructure = false;
					}
					continue;
				}
				if (x > -4 && x < 4 && z < 0 && z > -8) {
					if ((x == -3 || x == 3) && (z == -1 || z == -7)) {
						if (!addToMachineList(currentTE, TEXTURE_HATCH)) {
							if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == GENERAL_CASING)
									&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == GENERAL_CASING_META)) {
							} else {
								checkStructure = false;
							}
						}
						continue;
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
		
		for (x = -4; x <= 4; x++) {
			for (z = 0; z >= -8; z--) {
				for (y = 2; y <= 3; y++) {
					if ((x == -4 || x == -3 || x == 3 || x == 4) && (z == 0 || z == -8)) {
						continue;
					}
					if ((x == -4 || x == 4) && (z == -1 || z == -7)) {
						continue;
					}
					if ((x == -2 || x == 2) && (z == 0 || z == -8) ||
							(x == -3 || x == 3) && (z == -1 || z == -7) ||
							(x == -4 || x == 4) && (z == -2 || z == -6)) {
						continue;
					}
					final Vector3ic offset = rotateOffsetVector(forgeDirection, x, y, z);
					IGregTechTileEntity currentTE = thisController
							.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
					if (((x == -3 || x == 3) && (z == -2 || z == -6)) ||
							((x == -2 || x == 2) && (z == -1 || z == -7))) {
						if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == SECOND_CASING)
								&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == SECOND_CASING_META)) {
						} else {
							checkStructure = false;
						}
						continue;
					}
					if (x > -4 && x < 4 && z < 0 && z > -8) {
						if ((x == -3 || x == 3) && (z == -1 || z == -7)) {
							if (!addToMachineList(currentTE, TEXTURE_HATCH)) {
								if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == GENERAL_CASING)
										&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == GENERAL_CASING_META)) {
								} else {
									checkStructure = false;
								}
							}
							continue;
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
		}
		
		for (x = -4; x <= 4; x++) {
			for (z = 0; z >= -8; z--) {
				if ((x == -4 || x == -3 || x == 3 || x == 4) && (z == 0 || z == -8)) {
					continue;
				}
				if ((x == -4 || x == 4) && (z == -1 || z == -7)) {
					continue;
				}
				if ((x == -2 || x == -1 || x == 1 || x == 2) && (z == 0 || z == -8) ||
						(x == -3 || x == 3) && (z == -1 || z == -7) ||
						(x == -4 || x == 4) && (z == -2 || z == -3 || z == -5 || z == -6)) {
					continue;
				}
				final Vector3ic offset = rotateOffsetVector(forgeDirection, x, 4, z);
				if (((x == -3 || x == 3) && (z == -2 || z == -6)) ||
						((x == -2 || x == 2) && (z == -1 || z == -7))) {
					if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == SECOND_CASING)
							&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == SECOND_CASING_META)) {
					} else {
						checkStructure = false;
					}
					continue;
				}
				if (x > -3 && x < 3 && z < -1 && z > -7) {
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
		
		for (x = -3; x <= 3; x++) {
			for (z = -1; z >= -7; z--) {
				if ((x == -3 || x == 3) && (z == -1 || z == -7)) {
					continue;
				}
				final Vector3ic offset = rotateOffsetVector(forgeDirection, x, 5, z);
				IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				if (x > -3 && x < 3 && z < -1 && z > -7) {
					if (checkRodHatches(currentTE, TEXTURE_HATCH, ID)) {
						ID++;
					} else {
						if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == GENERAL_CASING)
								&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == GENERAL_CASING_META)) {
						} else {
							checkStructure = false;
						}
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
		
		if (mOutputHatches.size() > 24) {
			checkStructure = false;
		}
		
		if (mInputHatches.size() > 6) {
			checkStructure = false;
		}
		
		if (mMachineHull.size() > 1) {
			checkStructure = false;
		}
		
		return checkStructure;
	}
}