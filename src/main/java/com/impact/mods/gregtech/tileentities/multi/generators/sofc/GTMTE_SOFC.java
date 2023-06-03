package com.impact.mods.gregtech.tileentities.multi.generators.sofc;

import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static com.impact.loader.ItemRegistery.CeramicBlock;

abstract class GTMTE_SOFC extends GT_MetaTileEntity_MultiBlockBase {

	public final Block CASING = GregTech_API.sBlockCasings4;
	public final Block CERAMIC = CeramicBlock;
	public final int[] CERAMIC_META = new int[]{0, 1, 2};
	public final int[] CASING_META = new int[]{1, 2, 0};
	public final int[] CASING_TEXTURE_ID = new int[]{49, 50, 48};

	public final int[] OXYGEN_PER_SEC = new int[]{400, 1200, 2000};
	public final int[] EU_PER_TICK = new int[]{2048, 8192, 32768};

	public final String[] CASING_STRING = new String[]{"Clean Stainless Steel Casing", "Stable Titanium Machine Casing", "Robust Tungstensteel Machine Casing"};
	public final String[] CERAMIC_STRING = new String[]{"YSZ Ceramic Unit", "GDC Ceramic Unit", "LSCF Ceramic Unit"};
	public int fuelConsumption = 0;

	public GTMTE_SOFC(int aID, String aName, String aNameRegional) {
		super(aID, aName, aNameRegional);
	}

	public GTMTE_SOFC(String aName) {
		super(aName);
	}

	abstract int getTier();

	@Override
	public String[] getDescription() {
		final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("sofc" + getTier());
		b.addInfo("info.0", "Oxidizes gas fuels to generate electricity without polluting the environment")
				.addTypeGenerator()
				.addTypeSteam()
				.addInfo("info.1", "Steam production requires the SOFC to heat up completely first")
				.addInfo("info.2", "Additionally requires Oxygen gas")
				.addInfo("info.3", "Outputs Energy and Steam or SH Steam")
				.addSeparator()
				.beginStructureBlock(3, 3, 5)
				.addController()
				.addDynamoHatch("other.5" , "Single Amp. (max x2), Multi Amp. (max x1)")
				.addCasingInfo("case", CASING_STRING[getTier()])
				.addOtherStructurePart("other.0", CERAMIC_STRING[getTier()], "other.1", "3x, Center 1x1x3")
				.addOtherStructurePart("other.2", "Reinforced Glass", "other.3", "6x, touching the electrolyte units on the horizontal sides")
				.addMaintenanceHatch()
				.addInputHatch("other.4" , "Any casing, required", 2)
				.addOutputHatch()
				.signAndFinalize();
		if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			return b.getInformation();
		} else {
			return b.getStructureInformation();
		}
	}

	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		return aSide == aFacing ? new ITexture[]{
                Textures.BlockIcons.CASING_BLOCKS[CASING_TEXTURE_ID[getTier()]],
                TextureFactory.of(aActive ? Textures.BlockIcons.OVERLAY_FRONT_HEAT_EXCHANGER_ACTIVE : Textures.BlockIcons.OVERLAY_FRONT_HEAT_EXCHANGER)
        } : new ITexture[]{Textures.BlockIcons.CASING_BLOCKS[CASING_TEXTURE_ID[getTier()]]};
	}

	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, "SO Generator " + (getTier() + 1), "MultiblockDisplay.png");
	}

	public int calculateSteamOutput () {
		if (EU_PER_TICK[getTier()] > 2048) {
			return EU_PER_TICK[getTier()] * 35 / 100 * 20 ;
		} else {
			return EU_PER_TICK[getTier()] * 35 / 100 * 2 * 20;
		}
	}

	@Override
	public boolean isCorrectMachinePart(ItemStack stack) {
		return true;
	}

	@Override
	public boolean checkRecipe(ItemStack stack) {
		final ArrayList<FluidStack> storedFluids = super.getStoredFluids();
		Collection<GT_Recipe> recipeList = GT_Recipe.GT_Recipe_Map.sTurbineFuels.mRecipeList;

		if ((storedFluids.size() > 0 && recipeList != null)) {

			final Iterator<FluidStack> fluidsIterator = storedFluids.iterator();
			while (fluidsIterator.hasNext()) {

				final FluidStack hatchFluid = fluidsIterator.next();
				final Iterator<GT_Recipe> recipeIterator = recipeList.iterator();
				while (recipeIterator.hasNext()) {

					final GT_Recipe aFuel = recipeIterator.next();
					FluidStack liquid;
					if ((liquid = GT_Utility.getFluidForFilledItem(aFuel.getRepresentativeInput(0), true)) != null && hatchFluid.isFluidEqual(liquid)) {

						fuelConsumption = liquid.amount = (EU_PER_TICK[getTier()] * 20) / aFuel.mSpecialValue;

						if (super.depleteInput(liquid)) {

							if (!super.depleteInput(Materials.Oxygen.getGas(OXYGEN_PER_SEC[getTier()]))) {
								super.mEUt        = 0;
								super.mEfficiency = 0;
								return false;
							}

							super.mEUt                = EU_PER_TICK[getTier()];
							super.mMaxProgresstime    = 20;
							super.mEfficiencyIncrease = 1000;
							if (super.mEfficiency == getMaxEfficiency(null)) {
								if (getTier() == 0) {
									super.addOutput(GT_ModHandler.getSteam(calculateSteamOutput()));
								} else {
									super.addOutput(FluidRegistry.getFluidStack("ic2superheatedsteam", calculateSteamOutput()));
								}
							}
							return true;
						}
					}
				}
			}
		}

		super.mEUt        = 0;
		super.mEfficiency = 0;
		return false;
	}

	public Vector3ic rotateOffsetVector(Vector3ic forgeDirection, int x, int y, int z) {
		final Vector3i offset = new Vector3i();

		// either direction on z-axis
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
		// either direction on x-axis
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
		// either direction on y-axis
		if (forgeDirection.y() == -1) {
			offset.x = x;
			offset.y = z;
			offset.z = y;
		}

		return offset;
	}

	@Override
	public boolean checkMachine(IGregTechTileEntity thisController, ItemStack guiSlotItem) {
		// Figure out the vector for the direction the back face of the controller is facing
		final Vector3ic forgeDirection = new Vector3i(ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX, ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY, ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ);
		int minCasingAmount = 12;
		boolean formationChecklist = true; // if this is still true at the end, machine is good to go :)

		// Front slice
		for (int X = -1; X <= 1; X++) {
			for (int Y = -1; Y <= 1; Y++) {
				if (X == 0 && Y == 0) {
					continue; // is controller
				}

				// Get next TE
				final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, 0);
				IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());

				// Tries to add TE as either of those kinds of hatches.
				// The number is the texture index number for the texture that needs to be painted over the hatch texture (TAE for GT++)
				if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID[getTier()]) && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID[getTier()]) && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID[getTier()])) {

					// If it's not a hatch, is it the right casing for this machine? Check block and block meta.
					if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING) && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META[getTier()])) {
						// Seems to be valid casing. Decrement counter.
						minCasingAmount--;
					} else {
						formationChecklist = false;
					}
				}
			}
		}

		// Middle three slices
		for (int X = -1; X <= 1; X++) {
			for (int Y = -1; Y <= 1; Y++) {
				for (int Z = -1; Z >= -3; Z--) {
					final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);
					if (X == 0 && Y == 0) {
						if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) != CERAMIC) && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) != CERAMIC_META[getTier()])) {
							formationChecklist = false;
						}
						continue;
					}
					if (Y == 0 && (X == -1 || X == 1)) {
						if (!thisController.getBlockOffset(offset.x(), offset.y(), offset.z()).getUnlocalizedName().equals("blockAlloyGlass")) {
							formationChecklist = false;
						}
						continue;
					}
					// Get next TE
					IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());// x, y ,z

					// Tries to add TE as either of those kinds of hatches.
					// The number is the texture index number for the texture that needs to be painted over the hatch texture (TAE for GT++)
					if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID[getTier()]) && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID[getTier()]) && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID[getTier()])) {

						// If it's not a hatch, is it the right casing for this machine? Check block and block meta.
						if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING) && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META[getTier()])) {
							// Seems to be valid casing. Decrement counter.
							minCasingAmount--;
						} else {
							formationChecklist = false;
						}
					}
				}
			}
		}

		// Back slice
		for (int X = -1; X <= 1; X++) {
			for (int Y = -1; Y <= 1; Y++) {
				// Get next TE
				final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, -4);
				IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());// x, y ,z

				// Tries to add TE as either of those kinds of hatches.
				// The number is the texture index number for the texture that needs to be painted over the hatch texture (TAE for GT++)
				if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID[getTier()]) && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID[getTier()]) && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID[getTier()]) && !super.addDynamoToMachineList(currentTE, CASING_TEXTURE_ID[getTier()])) {

					// If it's not a hatch, is it the right casing for this machine? Check block and block meta.
					if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING) && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META[getTier()])) {
						// Seems to be valid casing. Decrement counter.
						minCasingAmount--;
					} else {
						formationChecklist = false;
					}
				}
			}
		}

		if (this.mInputHatches.size() < 2) {
			formationChecklist = false;
		}
		if (this.mMaintenanceHatches.size() < 1) {
			formationChecklist = false;
		}
		if (this.mDynamoHatches.size() > 2) {
			formationChecklist = false;
		}
		if (this.mDynamoHatchesMulti.size() > 1) {
			formationChecklist = false;
		}

		return formationChecklist;
	}

	public String[] getInfoData() {

		return new String[]{
				StatCollector.translateToLocal("GT5U.engine.output") + ": " + EnumChatFormatting.GREEN + this.mEUt * this.mEfficiency / 10000 + EnumChatFormatting.RESET + " EU/t",
				StatCollector.translateToLocal("GT5U.engine.efficiency") + ": " + EnumChatFormatting.YELLOW + (float) this.mEfficiency / 100.0F + EnumChatFormatting.YELLOW + " %",
				(getTier() == 0 ? "Output Steam: " : "Output SH Steam: ") + (((float) this.mEfficiency / 100.0F == 100) ? EnumChatFormatting.GREEN + "" + calculateSteamOutput() + EnumChatFormatting.RESET + " L/s" : EnumChatFormatting.GREEN + "0" + EnumChatFormatting.RESET + " L/s"),
				"Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus()) ? EnumChatFormatting.GREEN + "No Problems" + EnumChatFormatting.RESET : EnumChatFormatting.RED + "Has Problems" + EnumChatFormatting.RESET),
				"Fuel supply: " + ((this.mEUt * this.mEfficiency / 10000 >= 1) ? EnumChatFormatting.RED + "" + fuelConsumption + EnumChatFormatting.RESET + " L/s" : EnumChatFormatting.RED + "0" + EnumChatFormatting.RESET + " L/s"),
				"Oxygen supply: " + ((this.mEUt * this.mEfficiency / 10000 >= 1) ? EnumChatFormatting.RED + "" + OXYGEN_PER_SEC[getTier()] + EnumChatFormatting.RESET + " L/s" : EnumChatFormatting.RED + "0" + EnumChatFormatting.RESET + " L/s")
		};
	}

	@Override
	public int getMaxEfficiency(ItemStack stack) {
		return 10000;
	}

	@Override
	public int getPollutionPerTick(ItemStack stack) {
		return 0;
	}

	@Override
	public int getDamageToComponent(ItemStack stack) {
		return 0;
	}

	@Override
	public boolean explodesOnComponentBreak(ItemStack stack) {
		return false;
	}
}
