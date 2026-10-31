package com.impact.mods.gregtech.tileentities.multi.generators.nq;

import com.impact.common.block.blocks.Block_NqTether;
import com.impact.common.block.blocks.Block_QuantumStuff;
import com.impact.mods.gregtech.blocks.ImpactCasings;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.util.string.Language;
import com.impact.util.vector.Structure;
import com.impact.util.vector.Vector3i;
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
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.FluidStack;
import space.impact.structures.alignment.IAlignmentLimits;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.IGlassBlock;
import static com.impact.loader.ItemRegistery.InsideBlock;
import static gregtech.api.enums.GT_Values.RA;
import static java.text.NumberFormat.getNumberInstance;
import static space.impact.structures.structure.StructureUtility.ofBlock;
import static space.impact.structures.structure.StructureUtility.ofBlockAnyMeta;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_LiquidNqGenerator extends GTMTE_Impact_BlockBase<GTMTE_LiquidNqGenerator> {

    private static final ICasing CASING = ImpactCasings.NQ_CASING;
    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 0)
            .atLeast(HatchElement.Maintenance).build();
    private static IStructureDefinition<GTMTE_LiquidNqGenerator> definition;
    public int EU_PER_TICK = 524288;
    protected int fuelConsumption = 0;

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
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 3, 3, 1, budget, environment);
    }

    @Override
    public IStructureDefinition<GTMTE_LiquidNqGenerator> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_LiquidNqGenerator>builder()
                    .addShape("main", new String[][] {
                            { "       ", " EE EE ", " E   E ", "       ", " E   E ", " EE EE ", "       " },
                            { "  EEE  ", " EEEEE ", "EEEEEEE", "EEE~EEE", "EEEEEEE", " EEEEE ", "  EEE  " },
                            { "  GGG  ", " E   E ", "G D D G", "G DCD G", "G D D G", " E   E ", "  GGG  " },
                            { "  GGG  ", " E   E ", "G     G", "G     G", "G     G", " E   E ", "  GGG  " },
                            { "  GGG  ", " E   E ", "G     G", "G     G", "G     G", " E   E ", "  GGG  " },
                            { "  GGG  ", " E   E ", "G     G", "G     G", "G     G", " E   E ", "  GGG  " },
                            { "  GGG  ", " E   E ", "G D D G", "G DCD G", "G D D G", " E   E ", "  GGG  " },
                            { "  EEE  ", " EEEEE ", "EEEEEEE", "EEEEEEE", "EEEEEEE", " EEEEE ", "  EEE  " },
                            { "       ", " BB BB ", " BBBBB ", "  BBB  ", " BBBBB ", " BB BB ", "       " }
                    })
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addElement('G', ofBlockAnyMeta(IGlassBlock))
                    .addElement('C', ofBlock(Block_NqTether.INSTANCE, 0))
                    .addElement('D', ofBlock(InsideBlock, 0))
                    .addElement('B', withHint(GTStructureUtility.buildHatchAdder(GTMTE_LiquidNqGenerator.class)
                            .allow(HatchElement.Input, ImpactHatchElement.Muffler, ImpactHatchElement.Dynamo,
                                    ImpactHatchElement.LaserOutput, HatchElement.Output)
                            .buildAndChain(SHELL), HintColor.GREEN))
                    .addElement('E', withHint(GTStructureUtility.buildHatchAdder(GTMTE_LiquidNqGenerator.class)
                            .allow(HatchElement.Input, ImpactHatchElement.Muffler, HatchElement.Output)
                            .buildAndChain(SHELL), HintColor.BLUE))
                    .build();
        }
        return definition;
    }

    protected IAlignmentLimits getInitialAlignmentLimits() {
        return (direction, rotation, flip) -> (true) && rotation.isNotRotated() && flip.isNotFlipped();
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
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_LiquidNqGenerator(this.mName);
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
                .addOtherStructurePart("Naquadah Chamber Casing", "inside structure")
                .addOtherStructurePart("Tether Core", "for contain core Nq")
                .addStructureHint("Casing or I/O, maintenance, muffler or dynamo hatch", HintColor.GREEN)
                .addStructureHint("Casing or I/O, maintenance or muffler hatch", HintColor.BLUE)
                .toolTipFinisher();
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

        boolean formationChecklist = checkPiece(3, 3, 1) && SHELL.isSatisfied(this, 0);

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
                            super.mMaxProgresstime = 20; // 1 Second
                            super.mEfficiencyIncrease = 500; // 500 - 5% per cycle
                            if (mEfficiency > 9000) {
                                super.mEUt = EU_PER_TICK; // LuV * 16A
                            }
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
            Vector3ic vec = Structure.goBuild(base, 0, 0, -3);
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
    public String[] getInfoData() {
        return new String[] {
                "Total Output: " + EnumChatFormatting.GREEN + NumberFormat.getNumberInstance().format(super.mEUt)
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
    public boolean hasSeparate() {
        return false;
    }

    static class FuelNqGenerator implements Runnable {

        public int[] FUEL_PER_SECOND = new int[] { // for 1A
                10,
                5,
                2,
        };

        public ItemStack[] FUEL_NAME = new ItemStack[] {
                GT_OreDictUnificator.get(OrePrefixes.cell, Materials.NaquadahHeavyFuel, 1),
                GT_OreDictUnificator.get(OrePrefixes.cell, Materials.NaquadahMediumFuel, 1),
                GT_OreDictUnificator.get(OrePrefixes.cell, Materials.NaquadahLightFuel, 1),
        };

        @Override
        public void run() {
            for (int i = 0; i < FUEL_NAME.length; i++) {
                RA.addFuel(FUEL_NAME[i],
                        GT_Utility.getFluidForFilledItem(FUEL_NAME[i], true) == null
                                ? GT_Utility.getContainerItem(FUEL_NAME[i], true)
                                : null,
                        FUEL_PER_SECOND[i] * 16, 8);
            }
        }
    }
}
