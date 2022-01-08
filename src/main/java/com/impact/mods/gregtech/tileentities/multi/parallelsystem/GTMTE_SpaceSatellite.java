package com.impact.mods.gregtech.tileentities.multi.parallelsystem;

import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.input.Keyboard;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import static com.impact.util.Utilits.isValidDim;
import static micdoodle8.mods.galacticraft.core.util.ConfigManagerCore.disableSpaceStationCreation;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlock;

public class GTMTE_SpaceSatellite extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_SpaceSatellite> {
	
	public static Block CASING = Casing_Helper.sCasePage8_3;
	public static byte CASING_META = 5;
	public static ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[8][CASING_META + 64];
	public static int CASING_TEXTURE_ID = CASING_META + 64 + 128 * 8;
	static IStructureDefinition<GTMTE_SpaceSatellite> definition =
			StructureDefinition.<GTMTE_SpaceSatellite>builder()
					.addShape("main", new String[][]{
							{" A A ", " A A ", " A A ", " A A "},
							{" A A ", "AAAAA", "AA~AA", "AAAAA"},
							{" A A ", "AAAAA", "AAAAA", "AAAAA"},
							{" A A ", " A A ", " A A ", " A A "},
					})
					.addElement('A', ofBlock(CASING, CASING_META))
					.build();
	
	public GTMTE_SpaceSatellite(int aID, String aNameRegional) {
		super(aID, "impact.multis.spacesatellite", aNameRegional);
	}
	
	public GTMTE_SpaceSatellite(String aName) {
		super(aName);
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 2, 2, 1);
	}
	
	@Override
	public IStructureDefinition<GTMTE_SpaceSatellite> getStructureDefinition() {
		return definition;
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_SpaceSatellite(this.mName);
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return false;
	}
	
	@Override
	public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return false;
	}
	
	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		return aSide == aFacing ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(aActive ? Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1)} : new ITexture[]{INDEX_CASE};
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("sps");
		b
				.addTypeMachine("name", "Space Satellite")
				.addInfo(disableSpaceStationCreation ? "info.0" : "info.1",
						disableSpaceStationCreation ? "Installation on the Moon required" : "Installation on the Space Station required")
				.addController()
				.addOtherStructurePartAny("other.0", "Communication Transmitter")
				.addCasingInfo("case", "Space Satellite Casing", 48)
				.signAndFinalize();
		return b;
	}
	
	@Override
	public boolean checkRecipe(ItemStack aStack) {
		this.mMaxProgresstime    = 10;
		this.mEfficiency         = 10000;
		this.mEfficiencyIncrease = 10000;
		this.mEUt                = 0;
		return true;
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity iAm, long aTick) {
		super.onPostTick(iAm, aTick);
		boolean active;
		if (iAm.isServerSide()) {
			if (aTick % 20 == 0) {
				iAm.setActive(true);
				for (GTMTE_SpaceSatellite_Transmitter th : sCommunTransmitter) {
					active = iAm.isActive();
					th.getBaseMetaTileEntity().setActive(active);
					th.setIsTransmit(active);
				}
			}
			if (iAm.isServerSide() && aTick % 20 * 60 == 0) {
				mWrench = mScrewdriver = mSoftHammer = mHardHammer = mSolderingTool = mCrowbar = true;
			}
		}
	}
	
	@Override
	public boolean machineStructure(IGregTechTileEntity thisController) {
		int dimId = thisController.getWorld().provider.dimensionId;
		if (!disableSpaceStationCreation) {
			if (!(isValidDim(dimId, "Orbit") || isValidDim(dimId, "Space") || isValidDim(dimId, "SS") || isValidDim(dimId, "SpaceStation"))) {
				return false;
			}
		} else if (!isValidDim(dimId, "Moon")) {
			return false;
		}
		//region Structure
		final Vector3ic forgeDirection = new Vector3i(
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ
		);
		
		boolean formationChecklist = true;
		
		for (int X = -2; X <= 2; X++) {
			for (int Y = -1; Y <= 2; Y++) {
				for (int Z = 1; Z >= -2; Z--) {
					if (X == 0 && Y == 0 && Z == 0) {
						continue;
					}
					
					if ((Z == 1 || Z == -2) && !(X == -1 || X == 1)) {
						continue;
					}
					if (Y == 2 && !(X == -1 || X == 1)) {
						continue;
					}
					
					final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);
					
					IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
					if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !super.addCommunicationHatchToMachineList(currentTE, CASING_TEXTURE_ID)) {
						if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
								&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
						} else {
							formationChecklist = false;
						}
					}
				}
			}
		}
		//endregion
		
		return formationChecklist;
	}
	
	@Override
	public int getPollutionPerTick(ItemStack aStack) {
		return 0;
	}
}