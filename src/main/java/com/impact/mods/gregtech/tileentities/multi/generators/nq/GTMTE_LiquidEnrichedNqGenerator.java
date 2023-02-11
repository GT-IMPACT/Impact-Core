package com.impact.mods.gregtech.tileentities.multi.generators.nq;

import com.impact.common.block.blocks.Block_NqTether;
import com.impact.common.block.blocks.Block_QuantumStuff;
import com.impact.mods.gregtech.gui.base.GTC_ImpactBase;
import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.util.string.Language;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Structure;
import com.impact.util.vector.Vector3ic;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.FluidStack;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;

import static com.impact.loader.ItemRegistery.IGlassBlock;
import static com.impact.loader.ItemRegistery.InsideBlock;
import static com.impact.mods.gregtech.blocks.Casing_Helper.sCaseCore2;
import static com.impact.util.multis.GT_StructureUtility.ofHatchAdder;
import static gregtech.api.enums.GT_Values.RA;
import static java.text.NumberFormat.getNumberInstance;
import static space.impact.api.multiblocks.structure.StructureUtility.*;

public class GTMTE_LiquidEnrichedNqGenerator extends GTMTE_Impact_BlockBase<GTMTE_LiquidEnrichedNqGenerator> {
	
	private static final String[] description = new String[]{
			EnumChatFormatting.RED + "Impact Details:",
			"- Naquadah Base Casing",
			"- Naquadah Chamber Casing",
			"- I-Glass (any glass)",
			"- Tether Core",
			"- Hatches (any Casing)",
			"- Dynamo (any Casing)",
	};
	
	private static final Block CASING = sCaseCore2;
	private static final byte CASING_META = 10;
	private static final ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META + 16];
	private static final int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;
	private static final IStructureDefinition<GTMTE_LiquidEnrichedNqGenerator> STRUCTURE_DEFINITION =
			StructureDefinition.<GTMTE_LiquidEnrichedNqGenerator>builder()
					.addShape("main", new String[][]{
							{"           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "   AA~AA   ", "   AAAAA   "},
							{"           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "   AAAAA   ", "  A     A  ", "  AAAAAAA  "},
							{"           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "   ABABA   ", "  AA   AA  ", " A       A ", " AAAAAAAAA "},
							{"     A     ", "     A     ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "  ABBABBA  ", " AA     AA ", "A         A", "AAAAAAAAAAA"},
							{"           ", "     A     ", "     B     ", "     B     ", "     B     ", "     B     ", "     B     ", "     B     ", "     B     ", "     B     ", "  BBABABB  ", " A  DBD  A ", "A   DDD   A", "AAAAAAAAAAA"},
							{"   A   A   ", "   AACAA   ", "    B B    ", "    B B    ", "    B B    ", "    B B    ", "    B B    ", "    B B    ", "    B B    ", "    B B    ", "  AAB BAA  ", " A  B B  A ", "A   D D   A", "AAAAADAAAAA"},
							{"           ", "     A     ", "     B     ", "     B     ", "     B     ", "     B     ", "     B     ", "     B     ", "     B     ", "     B     ", "  BBABABB  ", " A  DBD  A ", "A   DDD   A", "AAAAAAAAAAA"},
							{"     A     ", "     A     ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "  ABBABBA  ", " AA     AA ", "A         A", "AAAAAAAAAAA"},
							{"           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "   ABABA   ", "  AA   AA  ", " A       A ", " AAAAAAAAA "},
							{"           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "   AAAAA   ", "  A     A  ", "  AAAAAAA  "},
							{"           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "           ", "   AAAAA   ", "   AAAAA   "}
					})
					.addElement('A', ofChain(
							ofHatchAdder(GTMTE_LiquidEnrichedNqGenerator::addToMachineList,
									CASING_TEXTURE_ID, CASING, 10
							),
							onElementPass(t -> t.casingCount++, ofBlock(CASING, 10))
					))
					.addElement('B', ofBlockAnyMeta(IGlassBlock))
					.addElement('C', ofBlock(Block_NqTether.INSTANCE, 0))
					.addElement('D', ofBlock(InsideBlock, 0))
					.build();
	public boolean Stuff;
	public int EU_PER_TICK = 8388608;
	protected int fuelConsumption = 0;
	private int casingCount = 0;
	
	public GTMTE_LiquidEnrichedNqGenerator(int aID, String aNameRegional) {
		super(aID, "impact.multimachine.liquidenrichednqgenerator", aNameRegional);
		new Runner().run();
	}
	
	public GTMTE_LiquidEnrichedNqGenerator(String aName) {
		super(aName);
	}
	
	@Override
	public IStructureDefinition<GTMTE_LiquidEnrichedNqGenerator> getStructureDefinition() {
		return STRUCTURE_DEFINITION;
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_LiquidEnrichedNqGenerator(mName);
	}
	
	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		return aSide == aFacing ? new ITexture[]{INDEX_CASE, TextureFactory.of(aActive ? Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1)} : new ITexture[]{INDEX_CASE};
	}
	
	@Override
	public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GTC_ImpactBase(aPlayerInventory, aBaseMetaTileEntity, this);
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, "Liquid Nq+ Generator", "MultiParallelBlockGUI.png");
	}
	
	@Override
	public boolean machineStructure(IGregTechTileEntity iGregTechTileEntity) {
		casingCount = 0;
		boolean check = checkPiece(5, 12, 0);
		if (casingCount < 5) check = false;
		return check;
	}
	
	@Override
	public boolean checkRecipe(ItemStack itemStack) {
		final ArrayList<FluidStack> storedFluids = super.getStoredFluids();
		Collection<GT_Recipe> recipeList = GT_Recipe.GT_Recipe_Map.sLiquidENqGenerator.mRecipeList;
		if ((storedFluids.size() > 0 && recipeList != null)) {
			for (FluidStack hatchFluid : storedFluids) {
				for (GT_Recipe aFuel : recipeList) {
					FluidStack liquid; // Register FluidStack (name fluid from oredict cell materials, amount)
					if ((liquid = GT_Utility.getFluidForFilledItem(aFuel.getRepresentativeInput(0), true)) != null && hatchFluid.isFluidEqual(liquid)) { // check: fluid cell and fluid without cell
						fuelConsumption = liquid.amount = aFuel.mSpecialValue; // set Amount: FUEL_PER_SECOND
						if (super.depleteInput(liquid)) {
							super.mMaxProgresstime    = 20; // 1 Second
							super.mEfficiencyIncrease = 500; // 500 - 5% per cycle
							if (mEfficiency > 9000) {
								super.mEUt        = EU_PER_TICK;
							} else {
								super.mEUt        = 0;
							} // ZPM * 64A
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
			Vector3ic vec = Structure.goBuild(base, 0, 13, -5);
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
	protected MultiBlockTooltipBuilder createTooltip() {
		final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
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
	public String[] getInfoData() {
		return new String[]{
				"Total Output: " + EnumChatFormatting.GREEN + NumberFormat.getNumberInstance().format(super.mEUt) + EnumChatFormatting.RESET + " EU/t",
				"Efficiency: " + EnumChatFormatting.YELLOW + (float) this.mEfficiency / 100.0F + EnumChatFormatting.YELLOW + " %",
				"Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus()) ? EnumChatFormatting.GREEN + "No Problems" + EnumChatFormatting.RESET : EnumChatFormatting.RED + "Has Problems" + EnumChatFormatting.RESET),
				"Fuel supply: " + EnumChatFormatting.RED + "" + fuelConsumption + EnumChatFormatting.RESET + " L/s"
		};
	}
	
	@Override
	public void construct(ItemStack stackSize, boolean hintsOnly) {
		buildPiece(stackSize, hintsOnly, 5, 12, 0);
	}
	
	@Override
	public String[] getStructureDescription(ItemStack stackSize) {
		return description;
	}
	
	
	@Override
	public boolean hasSeparate() {
		return false;
	}
	
	public static class Runner implements Runnable {
		
		public int[] FUEL_PER_SECOND = new int[]{ //for 1A
				20,
				10,
				5,
		};
		
		public ItemStack[] FUEL_NAME = new ItemStack[]{
				GT_OreDictUnificator.get(OrePrefixes.cell, Materials.NaquadahEHeavyFuel, 1),
				GT_OreDictUnificator.get(OrePrefixes.cell, Materials.NaquadahEMediumFuel, 1),
				GT_OreDictUnificator.get(OrePrefixes.cell, Materials.NaquadahELightFuel, 1),
		};
		
		@Override
		public void run() {
			for (int i = 0; i < FUEL_NAME.length; i++) {
				RA.addFuel(FUEL_NAME[i], GT_Utility.getFluidForFilledItem(FUEL_NAME[i], true) == null ? GT_Utility.getContainerItem(FUEL_NAME[i], true) : null, FUEL_PER_SECOND[i] * 64, 9);
			}
		}
	}
}