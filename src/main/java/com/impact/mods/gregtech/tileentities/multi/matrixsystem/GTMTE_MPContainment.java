package com.impact.mods.gregtech.tileentities.multi.matrixsystem;

import net.minecraft.util.StatCollector;

import gregtech.api.logic.ProcessingLogic;
import gregtech.api.logic.TimedProcessingLogic;
import com.impact.common.item.Core_Items3;
import com.impact.impact;
import com.impact.loader.ItemRegistery;
import com.impact.mods.gregtech.blocks.ImpactCasings;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.util.ImpactMultiblockTooltipBuilder;
import com.impact.util.Utilits;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import cpw.mods.fml.common.Optional;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_InputBus;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_Utility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import java.util.ArrayList;
import java.util.List;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static net.minecraft.util.EnumChatFormatting.GREEN;
import static net.minecraft.util.EnumChatFormatting.RED;
import static net.minecraft.util.EnumChatFormatting.RESET;
import static net.minecraft.util.EnumChatFormatting.YELLOW;
import static net.minecraft.util.StatCollector.translateToLocalFormatted;
import static space.impact.structures.structure.StructureUtility.ofBlock;
import static space.impact.structures.structure.StructureUtility.ofBlockAnyMeta;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_MPContainment extends GTMTE_Impact_BlockBase<GTMTE_MPContainment> {

    private static final ICasing CASING = ImpactCasings.LAB_SAFELG_CASING;

    private int casingCount;

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 28)
            .atLeast(HatchElement.Maintenance).build();
    private static final StructureRequirements OUTPUT = StructureRequirements.builder(CASING, 0)
            .atLeast(HatchElement.OutputBus).build();
    private static IStructureDefinition<GTMTE_MPContainment> definition;
    public int mMPStable = 0;
    public ArrayList<Vector3ic> vectors = new ArrayList<>();

    // region Register
    public GTMTE_MPContainment(int aID, String aNameRegional) {
        super(aID, "impact.multis.matrixcontainment", aNameRegional);
    }

    public GTMTE_MPContainment(String aName) {
        super(aName);
    }

    @Override
    public IStructureDefinition<GTMTE_MPContainment> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_MPContainment>builder()
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addShape("main", new String[][] {
                            { "      C", "CCCCC~C", "C    CC" },
                            { "CCCCCCC", "CRRRR G", "CCCCCCC" },
                            { "CRRRRCC", "G   T G", "CRRRRCC" },
                            { "CCCCCCC", "CRRRR G", "CCCCCCC" },
                            { "      C", "CCCCCOC", "C    CC" }
                    })
                    .addElement('C', withHint(GTStructureUtility.buildHatchAdder(GTMTE_MPContainment.class)
                            .allow(HatchElement.Maintenance, HatchElement.Energy, ImpactHatchElement.LaserInput,
                                    ImpactHatchElement.InputBus)
                            .autoPlace(ImpactHatchElement.PowerInput, HatchElement.Maintenance, ImpactHatchElement.InputBus)
                            .buildAndChain(SHELL, machine -> machine.casingCount++), HintColor.LIGHT_BLUE))
                    .addElement('R', ofBlock(ItemRegistery.MPSystem, 0))
                    .addElement('G', ofBlockAnyMeta(ItemRegistery.IGlassBlock))
                    .addElement('T', withHint(ofBlock(ItemRegistery.MPTransducer, 0), HintColor.PURPLE))
                    .addElement('O',
                            GTStructureUtility.buildHatchAdder(GTMTE_MPContainment.class).hint(HintColor.ORANGE)
                                    .build(OUTPUT))
                    .build();
        }
        return definition;
    }

    @Override
    public void construct(ItemStack itemStack, boolean b) {
        buildPiece(itemStack, b, 5, 1, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 5, 1, 0, budget, environment);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_MPContainment(this.mName);
    }
    // endregion

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing,
            final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == aFacing
                ? new ITexture[] { CASING.getTexture(),
                        TextureFactory.of(aActive ? Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1) }
                : new ITexture[] { CASING.getTexture() };
    }


    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new ImpactMultiblockTooltipBuilder(mName)
                .useCompactStructure()
                .addMachineType("Containment")
                .addInfo("Fills portable cells with 1000 stable Matrix Particles each.")
                .addInfo("Power consumption: 1920 EU/t.")
                .addInfo("Requires low gravity.")
                .beginStructureBlock(7, 3, 5, false)
                .addController("front, second from the right, middle layer")
                .addStructureSection("Components")
                .addStructurePartInfo(CASING, "%s+; fill unused casing positions", SHELL.getMinimumCasings())
                .addOtherStructurePart(new ItemStack(ItemRegistery.MPSystem, 1, 0), "exactly 16; inner chamber")
                .addOtherStructurePart("I-Glass", "exactly 4; middle layer; any color")
                .addOtherStructurePart(new ItemStack(ItemRegistery.MPTransducer, 1, 0), "exactly 1; middle layer, 1 block left and 2 blocks behind the controller")
                .addStructureSection("Hatches and buses")
                .addHatchInfo("Maintenance Hatch", "exactly 1; casing positions")
                .addHatchInfo("Energy Hatches", "optional; casing positions")
                .addHatchInfo("Input Buses", "optional; casing positions")
                .addHatchInfo("Output Bus", "exactly 1; rear wall, directly behind the controller")
                .addStructureHint("Output Bus", HintColor.ORANGE)
                .addStructureHint("Matrix Transducer", HintColor.PURPLE)
                .addStructureHint("Casings or hatches/buses", HintColor.LIGHT_BLUE)
                .addHatchChannelInfo()
                .toolTipFinisher();
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new TimedProcessingLogic(1920, () -> 20);
    }

    @Override
    public void onPostTick(IGregTechTileEntity iAm, long aTick) {
        super.onPostTick(iAm, aTick);

        if (iAm.isServerSide() && aTick % 20 == 0) {

            if (!iAm.isActive())
                mMPStable = 0;

            if (iAm.isActive()) {
                mMPStable = Math.min(mMPStable, 100_000);
                int amount = 0;
                for (GT_MetaTileEntity_Hatch_InputBus bus : mInputBusses) {
                    if (bus.mInventory.length > 0) {
                        for (ItemStack is : bus.mInventory) {
                            if (GT_Utility.areStacksEqual(is, Core_Items3.getInstance().get(0, 1))) {
                                amount += is.stackSize;
                            }
                        }
                    }
                }
                for (int i = 0; i < amount; i++) {
                    if (mMPStable >= 1000 && depleteInput(Core_Items3.getInstance().get(0, 1))) {
                        mMPStable -= 1000;
                        addOutput(Core_Items3.getInstance().get(1, 1));
                        Vector3ic core = vectors.get(0);

                        final Vector3ic forgeDirection = new Vector3i(
                                ForgeDirection.getOrientation(iAm.getBackFacing()).offsetX,
                                ForgeDirection.getOrientation(iAm.getBackFacing()).offsetY,
                                ForgeDirection.getOrientation(iAm.getBackFacing()).offsetZ);
                        final Vector3ic offset = rotateOffsetVector(forgeDirection, -1, 0, -2);
                        impact.proxy.nodeBolt(iAm.getWorld(), core.x(), core.y(), core.z(),
                                offset.x(), offset.y(), offset.z(), 60, 10.0F, 1);
                    } else
                        break;
                }
            }
        }
    }

    public void setMP(int amount) {
        mMPStable += amount;
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity iAm) {
        casingCount = 0;
        vectors.clear();
        if (!Utilits.isLowGravity(iAm)) {
            return false;
        }
        boolean formed = checkPiece(5, 1, 0)
                && mMaintenanceHatches.size() == 1 && SHELL.isSatisfied(this, casingCount);
        if (formed) {
            int[] offset = new int[3];
            getExtendedFacing().getWorldOffset(new int[] { -1, 0, 2 }, offset);
            vectors.add(new Vector3i(iAm.getXCoord() + offset[0], iAm.getYCoord() + offset[1],
                    iAm.getZCoord() + offset[2]));
        }
        return formed;
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("mMPStable", mMPStable);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        mMPStable = aNBT.getInteger("mMPStable");
    }

    public String[] getInfoData() {
        return new String[] {
                "Usage Energy: " + RED + -mEUt + RESET + " EU/t",
                "Max Voltage: " + YELLOW + getMaxInputVoltage() + RESET + " EU/t ",
                "Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus())
                        ? GREEN + "Good " + YELLOW + mEfficiency / 100.0F + " %" + RESET
                        : RED + "Has Problems " + mEfficiency / 100.0F + " %" + RESET),
        };
    }

    @Override
    public int getSupportedProcessingModes() {
        return 0;
    }

    @Override
    @Optional.Method(modid = "Waila")
    public void getWailaBody(ItemStack stack, List<String> tooltip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        super.getWailaBody(stack, tooltip, accessor, config);
        NBTTagCompound tag = accessor.getNBTData();
        tooltip.add(translateToLocalFormatted("impact.waila.matrix.particles",
                GT_Utility.formatNumbers(tag.getInteger("mMPStable"))));
    }

    @Override
    @Optional.Method(modid = "Waila")
    public void getWailaNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag,
            World world, int x, int y, int z) {
        super.getWailaNBTData(player, tile, tag, world, x, y, z);
        tag.setInteger("mMPStable", mMPStable);
    }
}
