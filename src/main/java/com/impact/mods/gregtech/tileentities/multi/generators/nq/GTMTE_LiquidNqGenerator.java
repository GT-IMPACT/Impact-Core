package com.impact.mods.gregtech.tileentities.multi.generators.nq;

import com.impact.common.block.blocks.Block_NqTether;
import com.impact.common.block.blocks.Block_QuantumStuff;
import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.util.string.Language;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Structure;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;

import static com.impact.loader.ItemRegistery.IGlassBlock;
import static com.impact.loader.ItemRegistery.InsideBlock;
import static gregtech.api.enums.GT_Values.RA;
import static java.text.NumberFormat.getNumberInstance;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlock;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlockAnyMeta;

public class GTMTE_LiquidNqGenerator extends GTMTE_Impact_BlockBase<GTMTE_LiquidNqGenerator> {
	
	
	static IStructureDefinition<GTMTE_LiquidNqGenerator> definition =
			StructureDefinition.<GTMTE_LiquidNqGenerator>builder()
					.addShape("main", new String[][]{
							{"       ", " 00 00 ", " 0   0 ", "       ", " 0   0 ", " 00 00 ", "       "},
							{"  000  ", " 00000 ", "0000000", "000~000", "0000000", " 00000 ", "  000  "},
							{"  222  ", " 0   0 ", "2 1 1 2", "2 131 2", "2 1 1 2", " 0   0 ", "  222  "},
							{"  222  ", " 0   0 ", "2     2", "2     2", "2     2", " 0   0 ", "  222  "},
							{"  222  ", " 0   0 ", "2     2", "2     2", "2     2", " 0   0 ", "  222  "},
							{"  222  ", " 0   0 ", "2     2", "2     2", "2     2", " 0   0 ", "  222  "},
							{"  222  ", " 0   0 ", "2 1 1 2", "2 131 2", "2 1 1 2", " 0   0 ", "  222  "},
							{"  000  ", " 00000 ", "0000000", "0000000", "0000000", " 00000 ", "  000  "},
							{"       ", " 00 00 ", " 00000 ", "  000  ", " 00000 ", " 00 00 ", "       "}
					})
					.addElement('0', ofBlock(Casing_Helper.sCaseCore2, 10))
					.addElement('1', ofBlock(InsideBlock, 0))
					.addElement('2', ofBlockAnyMeta(IGlassBlock))
					.addElement('3', ofBlock(Block_NqTether.INSTANCE, 0))
					.build();
	public int EU_PER_TICK = 524288;
	protected int fuelConsumption = 0;
	Block CASING = Casing_Helper.sCaseCore2;
	byte CASING_META = 10;
	ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META + 16];
	int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;
	
	public GTMTE_LiquidNqGenerator(int aID, String aNameRegional) {
		super(aID, "impact.multimachine.liquidnaqgen", aNameRegional);
		new FuelNqGenerator().run();
	}
	
	public GTMTE_LiquidNqGenerator(String aName) {
		super(aName);
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 3, 3, 1);
	}
	
	@Override
	public IStructureDefinition<GTMTE_LiquidNqGenerator> getStructureDefinition() {
		return definition;
	}
	
	public boolean isFacingValid(byte aFacing) {
		return true;
	}
	
	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		return aSide == aFacing ? new ITexture[]{INDEX_CASE, TextureFactory.of(aActive ? Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1)} : new ITexture[]{INDEX_CASE};
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_LiquidNqGenerator(this.mName);
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
		b
				.addMultiAmpGen()
				.addTypeGenerator()
				.addInfo(Language.langGetEUTick(getNumberInstance().format(EU_PER_TICK)), true)
				.addSeparator()
				.addController()
				.addDynamoHatch()
				.addMaintenanceHatch()
				.addInputHatch(3)
				.addCasingInfo("case", "Naquadah Base Casing and I-Glass")
				.addOtherStructurePart("other.0", "Naquadah Chamber Casing", "other.1", "inside structure")
				.addOtherStructurePart("other.2", "Tether Core", "other.3", "for contain core Nq")
				.signAndFinalize();
		return b;
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, "Liquid Nq Generator", "MultiParallelBlockGUI.png");
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
	public boolean machineStructure(IGregTechTileEntity thisController) {
		final Vector3ic forgeDirection = new Vector3i(
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ
		);
		
		int minCasingAmount = 12;
		boolean formationChecklist = true;
		
		for (byte X = -2; X <= 2; X++) {
			for (byte Y = -2; Y <= 2; Y++) {
				
				final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, 1);
				
				if (X == -2 && Y == 0 || X == 2 && Y == 0 || X == 0 && Y == -2 || X == 0 && Y == 2) {
					continue;
				}
				if (X > -2 && X < 2 && Y > -2 && Y < 2) {
					continue;
				}
				
				IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {
					
					if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
							&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
						minCasingAmount--;
					} else {
						formationChecklist = false;
					}
				}
			}
		}
		
		for (byte X = -3; X <= 3; X++) {
			for (byte Y = -3; Y <= 3; Y++) {
				
				final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, 0);
				
				if (X == 0 && Y == 0) {
					continue;
				}
				if (X == -3 && Y == -3 || X == 3 && Y == 3 || X == 3 && Y == -3 || X == -3 && Y == 3) {
					continue;
				}
				if (X == 3 && Y == -2 || X == 3 && Y == 2 || X == -3 && Y == -2 || X == -3 && Y == 2) {
					continue;
				}
				if (X == 2 && Y == -3 || X == 2 && Y == 3 || X == -2 && Y == -3 || X == -2 && Y == 3) {
					continue;
				}
				
				IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {
					
					if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
							&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
						minCasingAmount--;
					} else {
						formationChecklist = false;
					}
				}
			}
		}
		
		for (byte X = -3; X <= 3; X++) {
			for (byte Y = -3; Y <= 3; Y++) {
				for (int Z = -1; Z >= -5; Z--) {
					
					final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);
					
					if (X == -3 && Y == -3 || X == 3 && Y == 3 || X == 3 && Y == -3 || X == -3 && Y == 3) {
						continue;
					}
					if (X == 3 && Y == -2 || X == 3 && Y == 2 || X == -3 && Y == -2 || X == -3 && Y == 2) {
						continue;
					}
					if (X == 2 && Y == -3 || X == 2 && Y == 3 || X == -2 && Y == -3 || X == -2 && Y == 3) {
						continue;
					}
					
					if (X == 0 && Y == 0 && (Z == -1 || Z == -5)) {
						if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == Block_NqTether.INSTANCE
								&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 0)) {
						} else {
							formationChecklist = false;
						}
						continue;
					}
					
					if ((Y >= -1 && Y <= 1 && (X == 3 || X == -3)) || (X >= -1 && X <= 1 && (Y == 3 || Y == -3))) {
						if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock) {
						} else {
							formationChecklist = false;
						}
						continue;
					}
					
					if ((X >= -2 && X <= 2 && Y >= -2 && Y <= 2) && !(X == 2 && Y == 2 || X == -2 && Y == 2 || X == 2 && Y == -2 || X == -2 && Y == -2)) {
						if ((X == 1 || X == -1) && (Z == -1 || Z == -5) && (Y >= -1 && Y <= 1)) {
							if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == InsideBlock)
									&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 0)) {
								
							} else {
								formationChecklist = false;
							}
							continue;
						}
						continue;
					}
					
					IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
					if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {
						
						if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
								&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
							minCasingAmount--;
						} else {
							formationChecklist = false;
						}
					}
				}
			}
		}
		
		for (byte X = -3; X <= 3; X++) {
			for (byte Y = -3; Y <= 3; Y++) {
				
				final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, -6);
				
				if (X == -3 && Y == -3 || X == 3 && Y == 3 || X == 3 && Y == -3 || X == -3 && Y == 3) {
					continue;
				}
				if (X == 3 && Y == -2 || X == 3 && Y == 2 || X == -3 && Y == -2 || X == -3 && Y == 2) {
					continue;
				}
				if (X == 2 && Y == -3 || X == 2 && Y == 3 || X == -2 && Y == -3 || X == -2 && Y == 3) {
					continue;
				}
				
				IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {
					
					if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
							&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
						minCasingAmount--;
					} else {
						formationChecklist = false;
					}
				}
			}
		}
		
		for (byte X = -2; X <= 2; X++) {
			for (byte Y = -2; Y <= 2; Y++) {
				
				final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, -7);
				
				if (X == -2 && Y == 0 || X == 2 && Y == 0 || X == 0 && Y == -2 || X == 0 && Y == 2) {
					continue;
				}
				
				IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addDynamoToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {
					
					if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
							&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
						minCasingAmount--;
					} else {
						formationChecklist = false;
					}
				}
			}
		}
		
		if (this.mInputBusses.size() != 0) {
			formationChecklist = false;
		}
		if (this.mInputHatches.size() > 3) {
			formationChecklist = false;
		}
		if (this.mOutputBusses.size() != 0) {
			formationChecklist = false;
		}
		if (this.mOutputHatches.size() != 0) {
			formationChecklist = false;
		}
		if (this.mMufflerHatches.size() != 0) {
			formationChecklist = false;
		}
		if (this.mEnergyHatches.size() != 0) {
			formationChecklist = false;
		}
		if (this.mDynamoHatches.size() > 1) {
			formationChecklist = false;
		}
		if (this.mMaintenanceHatches.size() != 1) {
			formationChecklist = false;
		}
		return formationChecklist;
	}
	
	@Override
	public boolean checkRecipe(ItemStack itemStack) {
		final ArrayList<FluidStack> storedFluids = super.getStoredFluids();
		Collection<GT_Recipe> recipeList = GT_Recipe.GT_Recipe_Map.sLiquidNqGenerator.mRecipeList;
		if ((storedFluids.size() > 0 && recipeList != null)) {
			for (FluidStack hatchFluid : storedFluids) {
				for (GT_Recipe aFuel : recipeList) {
					FluidStack liquid; // Register FluidStack (name fluid from oredict cell materials, amount)
					if ((liquid = GT_Utility.getFluidForFilledItem(aFuel.getRepresentativeInput(0), true)) != null
							&& hatchFluid.isFluidEqual(liquid)) { // check: fluid cell and fluid without cell
						fuelConsumption = liquid.amount = aFuel.mSpecialValue; // set Amount: FUEL_PER_SECOND
						if (super.depleteInput(liquid)) {
							super.mMaxProgresstime    = 20; // 1 Second
							super.mEfficiencyIncrease = 500; // 500 - 5% per cycle
							if (mEfficiency > 9000) {
								super.mEUt = EU_PER_TICK; //LuV * 16A
							}
							quantumStuff(true);
							return true;
						}
					}
				}
			}
		}
		super.mEUt        = 0;
		super.mEfficiency = 0;
		quantumStuff(false);
		return false;
	}
	
	private void quantumStuff(boolean shouldExist) {
		IGregTechTileEntity base = getBaseMetaTileEntity();
		if (base != null && base.getWorld() != null) {
			Vector3ic vec = Structure.goBuild(base, 0, 0, -3);
			Block block = Structure.getBlock(base, vec);
			if (shouldExist) {
				if (block != null) {
					Structure.setBlock(base, vec, Block_QuantumStuff.INSTANCE, 0);
				}
			} else {
				try {
					Block qStaff = Block.getBlockFromItem(GT_ModHandler.getModItem("tectech", "tile.quantumStuff", 1).getItem());
					Structure.setBlock(base, vec, qStaff, 0);
				} catch (Exception e) {
					Structure.setBlock(base, vec, Block_QuantumStuff.INSTANCE, 0);
				}
			}
		}
	}
	
	@Override
	public String[] getInfoData() {
		return new String[]{
				"Total Output: " + EnumChatFormatting.GREEN + NumberFormat.getNumberInstance().format(super.mEUt) + EnumChatFormatting.RESET + " EU/t",
				"Efficiency: " + EnumChatFormatting.YELLOW + (float) this.mEfficiency / 100.0F + EnumChatFormatting.YELLOW + " %",
				"Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus()) ? EnumChatFormatting.GREEN + "No Problems" + EnumChatFormatting.RESET : EnumChatFormatting.RED + "Has Problems" + EnumChatFormatting.RESET),
				"Fuel supply: " + EnumChatFormatting.RED + "" + fuelConsumption + EnumChatFormatting.RESET + " L/s"
		};
	}
	
	
	@Override
	public boolean hasSeparate() {
		return false;
	}
	
	static class FuelNqGenerator implements Runnable {
		
		public int[] FUEL_PER_SECOND = new int[]{ //for 1A
				20,
				10,
				5,
		};
		
		public ItemStack[] FUEL_NAME = new ItemStack[]{
				GT_OreDictUnificator.get(OrePrefixes.cell, Materials.NaquadahHeavyFuel, 1),
				GT_OreDictUnificator.get(OrePrefixes.cell, Materials.NaquadahMediumFuel, 1),
				GT_OreDictUnificator.get(OrePrefixes.cell, Materials.NaquadahLightFuel, 1),
		};
		
		@Override
		public void run() {
			for (int i = 0; i < FUEL_NAME.length; i++) {
				RA.addFuel(FUEL_NAME[i], GT_Utility.getFluidForFilledItem(FUEL_NAME[i], true) == null ? GT_Utility.getContainerItem(FUEL_NAME[i], true) : null, FUEL_PER_SECOND[i] * 16, 8);
			}
		}
	}
}