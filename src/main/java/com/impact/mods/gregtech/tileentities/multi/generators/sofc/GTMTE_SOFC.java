package com.impact.mods.gregtech.tileentities.multi.generators.sofc;

import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import gregtech.api.enums.Casings;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_EnhancedMultiBlockBase;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.api.util.HatchElementBuilder;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import ic2.core.Ic2Items;
import ic2.core.util.StackUtil;
import java.util.ArrayList;
import java.util.Collection;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import space.impact.structures.alignment.constructable.ISurvivalConstructable;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.AutoPlaceEnvironment;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.IStructureElement;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.CeramicBlock;
import static space.impact.structures.structure.StructureUtility.ofBlock;
import static space.impact.structures.structure.StructureUtility.ofBlockAnyMeta;
import static space.impact.structures.structure.StructureUtility.withHint;

abstract class GTMTE_SOFC<T extends GTMTE_SOFC<T>>
        extends GT_MetaTileEntity_EnhancedMultiBlockBase<T>
        implements ISurvivalConstructable {

    private static final ICasing[] CASINGS = {
            Casings.CLEAN_STAINLESS_STEEL, Casings.STABLE_TITANIUM, Casings.ROBUST_TUNGSTENSTEEL
    };
    private IStructureDefinition<T> structure;

    public final int[] OXYGEN_PER_SEC = new int[] { 400, 1200, 2000 };
    public final int[] EU_PER_TICK = new int[] { 2048, 8192, 32768 };

    public final String[] CERAMIC_STRING = new String[] { "YSZ Ceramic Unit", "GDC Ceramic Unit", "LSCF Ceramic Unit" };
    public int fuelConsumption = 0;

    public GTMTE_SOFC(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GTMTE_SOFC(String aName) {
        super(aName);
    }

    abstract int getTier();

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        int tier = getTier();
        return new MultiblockTooltipBuilder(mName)
                .addInfo("Oxidizes gas fuels to generate electricity without polluting the environment")
                .addMachineType("Generator")
                .addMachineType("Steam Producer")
                .addInfo("Steam production requires the SOFC to heat up completely first")
                .addInfo("Additionally requires Oxygen gas")
                .addInfo("Outputs Energy and Steam or SH Steam")
                .addSeparator()
                .beginStructureBlock(3, 3, 5, false)
                .addController("See the structure hologram")
                .addOtherStructurePart("Dynamo Hatch", "Any casing")
                .addRequirements(requirements(), "Any casing position")
                .addOtherStructurePart(CERAMIC_STRING[tier], "3x, Center 1x1x3")
                .addOtherStructurePart("Reinforced Glass", "6x, touching the electrolyte units on the horizontal sides")
                .addOtherStructurePart("Maintenance Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("I/O Hatch", "Any casing")
                .addStructureHint("Casing or I/O or maintenance hatch", HintColor.GREEN)
                .addStructureHint("Casing or I/O, maintenance or dynamo hatch", HintColor.BLUE)
                .toolTipFinisher();
    }

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing,
            final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == aFacing ? new ITexture[] {
                CASINGS[getTier()].getTexture(),
                TextureFactory.of(aActive ? Textures.BlockIcons.OVERLAY_FRONT_HEAT_EXCHANGER_ACTIVE
                        : Textures.BlockIcons.OVERLAY_FRONT_HEAT_EXCHANGER)
        } : new ITexture[] { CASINGS[getTier()].getTexture() };
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity,
                "SO Generator " + (getTier() + 1), "MultiblockDisplay.png");
    }

    public int calculateSteamOutput() {
        if (EU_PER_TICK[getTier()] > 2048) {
            return EU_PER_TICK[getTier()] * 35 / 100 * 20;
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

        if ((!storedFluids.isEmpty() && recipeList != null)) {

            for (FluidStack hatchFluid : storedFluids) {

                for (GT_Recipe aFuel : recipeList) {

                    FluidStack liquid;
                    if ((liquid = GT_Utility.getFluidForFilledItem(aFuel.getRepresentativeInput(0), true)) != null
                            && hatchFluid.isFluidEqual(liquid)) {

                        fuelConsumption = liquid.amount = (EU_PER_TICK[getTier()] * 20) / aFuel.mSpecialValue;

                        if (super.depleteInput(liquid)) {

                            if (!super.depleteInput(Materials.Oxygen.getGas(OXYGEN_PER_SEC[getTier()]))) {
                                super.mEUt = 0;
                                super.mEfficiency = 0;
                                return false;
                            }

                            super.mEUt = EU_PER_TICK[getTier()];
                            super.mMaxProgresstime = 20;
                            super.mEfficiencyIncrease = 1000;
                            if (super.mEfficiency == getMaxEfficiency(null)) {
                                if (getTier() == 0) {
                                    super.addOutput(GT_ModHandler.getSteam(calculateSteamOutput()));
                                } else {
                                    super.addOutput(
                                            FluidRegistry.getFluidStack("ic2superheatedsteam", calculateSteamOutput()));
                                }
                            }
                            return true;
                        }
                    }
                }
            }
        }

        super.mEUt = 0;
        super.mEfficiency = 0;
        return false;
    }

    private StructureRequirements requirements() {
        return StructureRequirements.builder(CASINGS[getTier()], 0)
                .atLeast(HatchElement.Maintenance)
                .atLeast(ImpactHatchElement.FluidInput, 2).build();
    }

    @Override
    public IStructureDefinition<T> getStructureDefinition() {
        if (structure == null) {
            structure = StructureDefinition.<T>builder()
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addShape("main", new String[][] {
                            { "CCC", "C~C", "CCC" },
                            { "CCC", "GEG", "CCC" },
                            { "CCC", "GEG", "CCC" },
                            { "CCC", "GEG", "CCC" },
                            { "DDD", "DDD", "DDD" }
                    })
                    .addElement('C', withHint(new HatchElementBuilder<T>()
                            .allow(HatchElement.Input, HatchElement.Output).buildAndChain(requirements()),
                            HintColor.GREEN))
                    .addElement('D', withHint(new HatchElementBuilder<T>()
                            .allow(HatchElement.Input, HatchElement.Output, ImpactHatchElement.Dynamo)
                            .buildAndChain(requirements()), HintColor.BLUE))
                    .addElement('E', ceramicElement())
                    .addElement('G', ofBlockAnyMeta(StackUtil.getBlock(Ic2Items.reinforcedGlass)))
                    .build();
        }
        return structure;
    }

    private IStructureElement<T> ceramicElement() {
        int metadata = getTier();
        IStructureElement<T> ceramic = ofBlock(CeramicBlock, metadata);
        return new IStructureElement<T>() {
            @Override
            public boolean check(T machine, World world, int x, int y, int z) {
                // Existing structures allow any ceramic tier or another block with the tier's
                // metadata.
                return world.getBlock(x, y, z) == CeramicBlock || world.getBlockMetadata(x, y, z) == metadata;
            }

            @Override
            public boolean couldBeValid(T machine, World world, int x, int y, int z, ItemStack trigger) {
                return check(machine, world, x, y, z);
            }

            @Override
            public boolean spawnHint(T machine, World world, int x, int y, int z, ItemStack trigger) {
                return ceramic.spawnHint(machine, world, x, y, z, trigger);
            }

            @Override
            public boolean placeBlock(T machine, World world, int x, int y, int z, ItemStack trigger) {
                return check(machine, world, x, y, z) || ceramic.placeBlock(machine, world, x, y, z, trigger);
            }

            @Override
            public BlocksToPlace getBlocksToPlace(T machine, World world, int x, int y, int z,
                    ItemStack trigger, AutoPlaceEnvironment environment) {
                return ceramic.getBlocksToPlace(machine, world, x, y, z, trigger, environment);
            }
        };
    }

    @Override
    public void construct(ItemStack trigger, boolean hintsOnly) {
        buildPiece("main", trigger, hintsOnly, 1, 1, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget, ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 1, 1, 0, budget, environment);
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity controller, ItemStack stack) {
        return checkPiece("main", 1, 1, 0) && requirements().isSatisfied(this, 0)
                && mDynamoHatches.size() <= 2 && mDynamoHatchesMulti.size() <= 1;
    }

    public String[] getInfoData() {

        return new String[] {
                StatCollector.translateToLocal("GT5U.engine.output") + ": " + EnumChatFormatting.GREEN
                        + this.mEUt * this.mEfficiency / 10000 + EnumChatFormatting.RESET + " EU/t",
                StatCollector.translateToLocal("GT5U.engine.efficiency") + ": " + EnumChatFormatting.YELLOW
                        + (float) this.mEfficiency / 100.0F + EnumChatFormatting.YELLOW + " %",
                (getTier() == 0 ? "Output Steam: " : "Output SH Steam: ") + (((float) this.mEfficiency / 100.0F == 100)
                        ? EnumChatFormatting.GREEN + "" + calculateSteamOutput() + EnumChatFormatting.RESET + " L/s"
                        : EnumChatFormatting.GREEN + "0" + EnumChatFormatting.RESET + " L/s"),
                "Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus())
                        ? EnumChatFormatting.GREEN + "No Problems" + EnumChatFormatting.RESET
                        : EnumChatFormatting.RED + "Has Problems" + EnumChatFormatting.RESET),
                "Fuel supply: " + ((this.mEUt * this.mEfficiency / 10000 >= 1)
                        ? EnumChatFormatting.RED + "" + fuelConsumption + EnumChatFormatting.RESET + " L/s"
                        : EnumChatFormatting.RED + "0" + EnumChatFormatting.RESET + " L/s"),
                "Oxygen supply: " + ((this.mEUt * this.mEfficiency / 10000 >= 1)
                        ? EnumChatFormatting.RED + "" + OXYGEN_PER_SEC[getTier()] + EnumChatFormatting.RESET + " L/s"
                        : EnumChatFormatting.RED + "0" + EnumChatFormatting.RESET + " L/s")
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
