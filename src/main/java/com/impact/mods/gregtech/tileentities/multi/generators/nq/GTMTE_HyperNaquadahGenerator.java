package com.impact.mods.gregtech.tileentities.multi.generators.nq;

import com.impact.common.block.blocks.Block_NqTether;
import com.impact.common.block.blocks.Block_QuantumStuff;
import com.impact.mods.gregtech.blocks.ImpactCasings;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.gui.base.GTC_ImpactBase;
import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.util.string.Language;
import com.impact.util.vector.Structure;
import com.impact.util.vector.Vector3ic;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import java.util.ArrayList;
import java.util.Collection;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.FluidStack;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static gregtech.api.enums.GT_Values.RA;
import static java.text.NumberFormat.getNumberInstance;
import static space.impact.structures.structure.StructureUtility.*;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_HyperNaquadahGenerator extends GTMTE_Impact_BlockBase<GTMTE_HyperNaquadahGenerator> {

    private static IStructureDefinition<GTMTE_HyperNaquadahGenerator> STRUCTURE_DEFINITION;

    private static final ICasing CASING = ImpactCasings.NQ_CASING;

    private static final StructureRequirements HATCHES_C = StructureRequirements.builder(ImpactCasings.NQ_CASING, 5)
            .build();

    public boolean Stuff;
    public int EU_PER_TICK = 134217728;
    protected int fuelConsumption = 0;
    private int casingCount = 0;

    public GTMTE_HyperNaquadahGenerator(int aID, String aNameRegional) {
        super(aID, "impact.multimachine.naqgen", aNameRegional);
        new Runner().run();
    }

    public GTMTE_HyperNaquadahGenerator(String aName) {
        super(aName);
    }

    @Override
    public IStructureDefinition<GTMTE_HyperNaquadahGenerator> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition
                    .<GTMTE_HyperNaquadahGenerator>builder()
                    .addShape("main", new String[][] {
                            { "               ", "      CCC      ", "    CC   CC    ", "   C       C   ", "  C         C  ", "  C         C  ", " C           C ", " C           C ", " C           C ", "  C         C  ", "  C         C  ", "   C       C   ", "    CC   CC    ", "      C~C      ", "               " },
                            { "      CCC      ", "    CCBBBCC    ", "   CBBAAABBC   ", "  CBAA   AABC  ", " CBA       ABC ", " CBA       ABC ", "CBA         ABC", "CBA         ABC", "CBA         ABC", " CBA       ABC ", " CBA       ABC ", "  CBAA   AABC  ", "   CBBAAABBC   ", "    CCBBBCC    ", "      CCC      " },
                            { "               ", "      CCC      ", "    CC   CC    ", "   C       C   ", "  C         C  ", "  C         C  ", " C           C ", " C           C ", " C           C ", "  C         C  ", "  C         C  ", "   C       C   ", "    CC   CC    ", "      CCC      ", "               " }
                    })
                    .addElement('A', ImpactCasings.NQ_CASING.asElement())
                    .addElement('B', ofBlock(Block_NqTether.INSTANCE, 0))
                    .addElement('C', withHint(GTStructureUtility.buildHatchAdder(GTMTE_HyperNaquadahGenerator.class)
                            .allow(HatchElement.Input, HatchElement.Output, HatchElement.Maintenance,
                                    HatchElement.Energy, ImpactHatchElement.Muffler, ImpactHatchElement.Dynamo,
                                    ImpactHatchElement.LaserInput, ImpactHatchElement.LaserOutput)
                            .buildAndChain(HATCHES_C, t -> t.casingCount++), HintColor.GREEN))
                    .build();
        }
        return STRUCTURE_DEFINITION;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_HyperNaquadahGenerator(mName);
    }

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing,
            final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == aFacing
                ? new ITexture[] { CASING.getTexture(),
                        TextureFactory.of(aActive ? Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1) }
                : new ITexture[] { CASING.getTexture() };
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GTC_ImpactBase(aPlayerInventory, aBaseMetaTileEntity);
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, "Hyper Generator", "MultiParallelBlockGUI.png");
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity iGregTechTileEntity) {
        casingCount = 0;
        boolean check = checkPiece(7, 13, 0);
        if (!HATCHES_C.isSatisfied(this, casingCount))
            check = false;
        return check;
    }

    @Override
    public boolean checkRecipe(ItemStack itemStack) {
        final ArrayList<FluidStack> storedFluids = super.getStoredFluids();
        Collection<GT_Recipe> recipeList = GT_Recipe.GT_Recipe_Map.sHyperGenerator.mRecipeList;
        if ((storedFluids.size() > 0 && recipeList != null)) {
            for (FluidStack hatchFluid : storedFluids) {
                for (GT_Recipe aFuel : recipeList) {
                    FluidStack liquid; // Register FluidStack (name fluid from oredict cell materials, amount)
                    if ((liquid = GT_Utility.getFluidForFilledItem(aFuel.getRepresentativeInput(0), true)) != null
                            && hatchFluid.isFluidEqual(liquid)) { // check: fluid cell and fluid without cell
                        fuelConsumption = liquid.amount = aFuel.mSpecialValue; // set Amount: FUEL_PER_SECOND
                        if (super.depleteInput(liquid)) {
                            super.mMaxProgresstime = 20; // 1 Second
                            super.mEfficiencyIncrease = 500; // 500 - 5% per cycle
                            if (mEfficiency > 9000) {
                                super.mEUt = EU_PER_TICK;
                            } else {
                                super.mEUt = 0;
                            } // UV * 256A
                            quantumStuff(true);
                            return true;
                        }
                    }
                }
            }
        }
        super.mEUt = 0;
        super.mEfficiency = 0;
        quantumStuff(false);
        return false;
    }

    @Override
    public void onPostTick(IGregTechTileEntity base, long tick) {
        super.onPostTick(base, tick);
        if (base.isServerSide() && mStartUpCheck < 0) {
            quantumStuff(base.isActive());
        }
    }

    @Override
    public void stopMachine() {
        super.stopMachine();
        quantumStuff(false);
    }

    @Override
    public void onRemoval() {
        quantumStuff(false, false);
        super.onRemoval();
    }

    private void quantumStuff(boolean active) {
        quantumStuff(mMachine, active);
    }

    private void quantumStuff(boolean shouldExist, boolean active) {
        IGregTechTileEntity base = getBaseMetaTileEntity();
        if (base != null && base.isServerSide() && base.getWorld() != null) {
            Vector3ic vec = Structure.goBuild(base, 0, 6, -1);
            Block current = Structure.getBlock(base, vec);
            if (shouldExist) {
                int meta = active ? Block_QuantumStuff.ACTIVE_META : Block_QuantumStuff.INACTIVE_META;
                if (current == Block_QuantumStuff.INSTANCE) {
                    if (base.getMetaIDOffset(vec.x(), vec.y(), vec.z()) == meta)
                        return;
                } else if (!base.getWorld().isAirBlock(base.getXCoord() + vec.x(), base.getYCoord() + vec.y(),
                        base.getZCoord() + vec.z())) {
                    return;
                }
                Structure.setBlock(base, vec, Block_QuantumStuff.INSTANCE, meta);
            } else {
                if (current != Block_QuantumStuff.INSTANCE)
                    return;
                Structure.setAir(base, vec);
            }
            int x = base.getXCoord() + vec.x();
            int y = base.getYCoord() + vec.y();
            int z = base.getZCoord() + vec.z();
            base.getWorld().markBlockRangeForRenderUpdate(x - 2, y - 2, z - 2, x + 2, y + 2, z + 2);
        }
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder(mName)
                .addInfo("Multi-Amperes generator")
                .addMachineType("Generator")
                .addInfo(Language.langGetEUTick(getNumberInstance().format(EU_PER_TICK)))
                .addSeparator()
                .addController("See the structure hologram")
                .addOtherStructurePart("Dynamo Hatch", "Any casing")
                .addOtherStructurePart("Maintenance Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Input Hatch", "Any casing; maximum 3")
                .addCasing("", CASING, false)
                .addOtherStructurePart("Tether Core", "for contain core Nq")
                .addStructureHint("Casing or compatible hatch", HintColor.GREEN)
                .toolTipFinisher();
    }

    @Override
    public String[] getInfoData() {

        return new String[] {
                "Total Output: " + EnumChatFormatting.GREEN + getNumberInstance().format(super.mEUt)
                        + EnumChatFormatting.RESET + " EU/t",
                "Efficiency: " + EnumChatFormatting.YELLOW + (float) this.mEfficiency / 100.0F
                        + EnumChatFormatting.YELLOW + " %",
                "Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus())
                        ? EnumChatFormatting.GREEN + "No Problems" + EnumChatFormatting.RESET
                        : EnumChatFormatting.RED + "Has Problems" + EnumChatFormatting.RESET),
                "Fuel supply: " + EnumChatFormatting.RED + "" + fuelConsumption + EnumChatFormatting.RESET + " L/s"
        };
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        buildPiece(stackSize, hintsOnly, 7, 13, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 7, 13, 0, budget, environment);
    }

    public static class Runner implements Runnable {

        public int[] FUEL_PER_SECOND = new int[] { // for 1A
                10,
                5,
                2,
        };

        public ItemStack[] FUEL_NAME = new ItemStack[] {
                GT_OreDictUnificator.get(OrePrefixes.cell, Materials.HyperFuelI, 1),
                GT_OreDictUnificator.get(OrePrefixes.cell, Materials.HyperFuelII, 1),
                GT_OreDictUnificator.get(OrePrefixes.cell, Materials.HyperFuelIII, 1),
        };

        @Override
        public void run() {
            for (int i = 0; i < FUEL_NAME.length; i++) {
                RA.addFuel(FUEL_NAME[i],
                        GT_Utility.getFluidForFilledItem(FUEL_NAME[i], true) == null
                                ? GT_Utility.getContainerItem(FUEL_NAME[i], true)
                                : null,
                        FUEL_PER_SECOND[i] * 256, 10);
            }
        }
    }

    @Override
    public boolean hasSeparate() {
        return false;
    }
}
