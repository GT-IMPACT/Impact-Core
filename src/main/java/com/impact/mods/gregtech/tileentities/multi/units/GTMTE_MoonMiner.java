package com.impact.mods.gregtech.tileentities.multi.units;

import com.impact.mods.gregtech.blocks.ImpactCasings;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_Utility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fluids.FluidStack;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.CollisionBlock;
import static space.impact.structures.structure.StructureUtility.ofBlock;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_MoonMiner extends GTMTE_Impact_BlockBase<GTMTE_MoonMiner> {

    private static final ICasing CASING = ImpactCasings.MOON_MINER_CASING;
    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 0)
            .atLeast(HatchElement.Maintenance).build();
    private static IStructureDefinition<GTMTE_MoonMiner> definition;

    public GTMTE_MoonMiner(int aID, String aNameRegional) {
        super(aID, "impact.multimachine.blockminer", aNameRegional);
    }

    public GTMTE_MoonMiner(String aName) {
        super(aName);
    }

    @Override
    public boolean checkRecipe(ItemStack guiSlotItem) {
        boolean check = true;
        this.mMaxProgresstime = 20;
        this.mEfficiency = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
        this.mEfficiencyIncrease = 10000;
        int tier = Math.max(1, GT_Utility.getTier(getMaxInputVoltage()));
        this.mEUt = -4 * (1 << (tier << 1));
        if (!depleteInput(new FluidStack(ItemList.sDrillingFluid, 200))) {
            mMaxProgresstime = 0;
            this.mEUt = 0;
            this.mEfficiency = 0;
            check = false;
        }
        return check;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
        Random rand = new Random();// 3 4 5 (0-2)
        if (aTick % 20 == 0 && getBaseMetaTileEntity().isActive()) {
            // addOutput(Blockstack(GCBlocks.blockMoon, (Math.max(1,
            // GT_Utility.getTier(getMaxInputVoltage())) - 1), rand.nextInt(3) + 3));
        }
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder(mName)
                .addInfo(EnumChatFormatting.RED + "MultiBlock in dev [WIP]")
                .addStructureHint("Casing or compatible hatch", HintColor.GREEN)
                .addStructureHint("Collision block or compatible hatch", HintColor.BLUE)
                .toolTipFinisher();
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        int ID = getBaseMetaTileEntity().getWorld().provider.dimensionId;
        if (!DimensionManager.getProvider(ID).getClass().getName().contains("Moon")) {
            return false;
        }
        if (getBaseMetaTileEntity().getYCoord() > 70) {
            return false;
        }
        boolean formationChecklist = checkPiece(2, 0, 1) && SHELL.isSatisfied(this, 0);

        if (this.mInputHatches.size() > 1) {
            formationChecklist = false;
        }
        if (this.mOutputBusses.size() > 1) {
            formationChecklist = false;
        }
        if (this.mEnergyHatches.size() > 1) {
            formationChecklist = false;
        }
        if (this.mMaintenanceHatches.size() != 1) {
            formationChecklist = false;
        }

        return formationChecklist;
    }

    @Override
    public int getPollutionPerTick(ItemStack itemStack) {
        return 0;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_MoonMiner(this.mName);
    }

    @Override
    public IStructureDefinition<GTMTE_MoonMiner> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_MoonMiner>builder()
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addShape("main", new String[][] {
                            { "C   C", "C   C", "C   C" },
                            { " C~C ", "CCCCC", "     " },
                            { " CCC ", "CCCCC", "  B  " },
                            { " CCC ", "CCCCC", "     " },
                            { "C   C", "C   C", "C   C" }
                    })
                    .addElement('C', withHint(GTStructureUtility.buildHatchAdder(GTMTE_MoonMiner.class)
                            .allow(HatchElement.Input, HatchElement.Output, HatchElement.Energy,
                                    ImpactHatchElement.LaserInput, ImpactHatchElement.Muffler)
                            .buildAndChain(SHELL), HintColor.GREEN))
                    .addElement('B', withHint(GTStructureUtility.buildHatchAdder(GTMTE_MoonMiner.class)
                            .allow(HatchElement.Input, HatchElement.Output, HatchElement.Energy,
                                    ImpactHatchElement.LaserInput, ImpactHatchElement.Muffler)
                            .buildAndChain(SHELL, ofBlock(CollisionBlock, 0), t -> {
                            }), HintColor.BLUE))
                    .build();
        }
        return definition;
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
    public void construct(ItemStack itemStack, boolean b) {
        buildPiece(itemStack, b, 2, 0, 1);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 2, 0, 1, budget, environment);
    }

    @Override
    public boolean hasSeparate() {
        return false;
    }
}
