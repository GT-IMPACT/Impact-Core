package com.impact.mods.GregTech.tileentities.multi.newparallelsystem;

import com.github.technus.tectech.mechanics.alignment.enumerable.ExtendedFacing;
import com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer;
import com.github.technus.tectech.mechanics.structure.IStructureDefinition;
import com.github.technus.tectech.mechanics.structure.StructureDefinition;
import com.impact.mods.GregTech.blocks.Casing_Helper;
import com.impact.mods.GregTech.tileentities.hatches.GTMTE_TankHatch;
import com.impact.mods.GregTech.tileentities.multi.debug.*;
import com.impact.util.MultiBlockTooltipBuilder;
import com.impact.util.Vector3i;
import com.impact.util.Vector3ic;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.input.Keyboard;

import java.util.HashSet;

import static com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer.registerMetaClass;
import static com.github.technus.tectech.mechanics.structure.StructureUtility.ofBlock;
import static com.impact.loader.ItemRegistery.IGlassBlock;
import static com.impact.loader.ItemRegistery.decorateBlock;

public class GTMTE_ParallelComputer extends GT_MetaTileEntity_MultiParallelBlockBase {

    Block CASING = Casing_Helper.sCaseCore2;
    byte CASING_META = 12;
    ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META + 16];
    int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;

    //region Register
    public GTMTE_ParallelComputer(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
        run();
    }

    public GTMTE_ParallelComputer(String aName) {
        super(aName);
        run();
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        run();
        return new GTMTE_ParallelComputer(this.mName);
    }

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide,
                                 final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == aFacing ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(aActive ? Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1)} : new ITexture[]{INDEX_CASE};
    }

    public void run() {
        registerMetaClass(GTMTE_ParallelComputer.class, new IMultiblockInfoContainer<GTMTE_ParallelComputer>() {
            //region Structure
            private final IStructureDefinition<GTMTE_ParallelComputer> definition =
                    StructureDefinition.<GTMTE_ParallelComputer>builder()
                            .addShape("main", new String[][]{
                                    {"AAAAA","AAAAA","AA~AA","AAAAA","AAAAA"},
                                    {"CCCCC","CBBBC","CBBBC","CBBBC","CCCCC"},
                                    {"CCCCC","CBBBC","CBBBC","CBBBC","CCCCC"},
                                    {"CCCCC","CBBBC","CBBBC","CBBBC","CCCCC"},
                                    {"CCCCC","CBBBC","CBBBC","CBBBC","CCCCC"},
                                    {"CCCCC","CBBBC","CBBBC","CBBBC","CCCCC"},
                                    {"CCCCC","CBBBC","CBBBC","CBBBC","CCCCC"},
                                    {"CCCCC","CBBBC","CBBBC","CBBBC","CCCCC"},
                                    {"AAAAA","AAAAA","AAAAA","AAAAA","AAAAA"}
                            })
                            .addElement('A', ofBlock(CASING, CASING_META))
                            .addElement('B', ofBlock(Casing_Helper.sCaseCore1, 0))
                            .addElement('C', ofBlock(IGlassBlock))
                            .build();
            private final String[] desc = new String[]{
                    EnumChatFormatting.RED + "Impact Details:",
                    //todo
            };
            //endregion
            @Override
            public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_ParallelComputer tileEntity, ExtendedFacing aSide) {
                IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
                definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
                        base.getXCoord(), base.getYCoord(), base.getZCoord(),
                        2, 2, 0, hintsOnly);
            }
            @Override
            public String[] getDescription(ItemStack stackSize) {
                return desc;
            }
        });

    }
    //endregion

    @Override
    public String[] getDescription() {
        final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
        b
                .addInfo("Digging rock on the moon, it's simple")
                .addTypeMachine("Block Digger")
                .addSeparator()
                .addController()
                .addEnergyHatch("Any casing")
                .addMaintenanceHatch("Any casing")
                .addOutputBus("Any casing (max x1)")
                .addInputHatch("Any casing (max x1)")
                .addOtherStructurePart("Block of Digger (x1)", "to the very center from below")
                .addCasingInfo("Moon Miner Casing")
                .signAndFinalize(": " + EnumChatFormatting.RED + "IMPACT");
        if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            return b.getInformation();
        } else {
            return b.getStructureInformation();
        }
    }

    @Override
    public boolean checkRecipe(ItemStack aStack) {



        this.mMaxProgresstime = 20;
        this.mEfficiency = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
        this.mEfficiencyIncrease = 10000;
        this.mEUt = -20;


        return true;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);

        for(GTMTE_ParallelHatch_Output ph : sParallHatchesOut) {
            if ((getCurrentCapacityPP() - ph.getAskInputHatch()) > 0) {
                mCurrentCapacityPP -= ph.getAskInputHatch();
                ph.setCurrentParallelOut(ph.getAskInputHatch());
            }
        }
    }

    public int mCurrentCapacityPP = getMaxCapacityPP();
    public int mMaxCapacityPP = 0;

    public int getCurrentCapacityPP() {
        return mCurrentCapacityPP;
    }

    public void setMaxCapacityPP(int setMax) {
        mCurrentCapacityPP = setMax;
    }

    public int getMaxCapacityPP() {
        return mMaxCapacityPP;
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity thisController, ItemStack aStack) {
        sParallHatchesOut.clear();
        int aTotalParallelCapacity = 0;

        //region Structure
        TThatches();
        final Vector3ic forgeDirection = new Vector3i(
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ);

        boolean formationChecklist = true;

        for (int X = -2; X <= 2; X++) {
            for (int Y = -2; Y <= 2; Y++) {
                if (X == 0 && Y == 0) {
                    continue;
                }

                final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, 0);
                final IGregTechTileEntity currentTE =
                        thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());

                if (X > -2 && X < 2 && Y > -2 && Y < 2) {
                    if (!super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !addParallHatchToMachineList(currentTE, CASING_TEXTURE_ID)) {
                        if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING) {

                        } else {
                            formationChecklist = false;
                        }
                    }
                } else {
                    if (!super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)) {

                        if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING) {
                        } else {
                            formationChecklist = false;
                        }
                    }
                }
            }
        }

        for (int X = -2; X <= 2; X++) {
            for (int Y = -2; Y <= 2; Y++) {
                for (int Z = -1; Z >= -7; Z--) {
                    final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);
                    if (X > -2 && X < 2 && Y > -2 && Y < 2) {
                        final int meta = thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z());

                        if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == Casing_Helper.sCaseCore1) {
                            switch (meta) {
                                case 0:
                                    aTotalParallelCapacity += 4;
                                    break;
                                case 1:
                                    aTotalParallelCapacity += 16;
                                    break;
                                case 2:
                                    aTotalParallelCapacity += 64;
                                    break;
                                case 3:
                                    aTotalParallelCapacity += 256;
                                    break;
                            }
                        } else {
                            formationChecklist = false;
                        }
                        continue;
                    }

                    final IGregTechTileEntity currentTE =
                            thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());

                    if (X == -2 && Y == -2 || X == 2 && Y == 2 || X == -2 && Y == 2 || X == 2 && Y == -2) {
                        if (!(thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock)) {
                            formationChecklist = false;
                        }
                    } else {
                        if (!super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
                                && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {
                            if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING) {
                            } else if (!(thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock)) {
                                formationChecklist = false;
                            }
                        }
                    }
                }
            }
        }
        for (int X = -2; X <= 2; X++) {
            for (int Y = -2; Y <= 2; Y++) {
                // Get next TE
                final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, -8);
                final IGregTechTileEntity currentTE =
                        thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
                if (X > -2 && X < 2 && Y > -2 && Y < 2) {
                    if (!super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)) {
                        if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING) {
                        } else {
                            formationChecklist = false;
                        }
                    }
                } else {
                    if (!super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)) {
                        if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING) {
                        } else {
                            formationChecklist = false;
                        }
                    }
                }
            }
        }
        //endregion

        setMaxCapacityPP(aTotalParallelCapacity);

        this.mWrench = true;
        this.mScrewdriver = true;
        this.mSoftHammer = true;
        this.mHardHammer = true;
        this.mSolderingTool = true;
        this.mCrowbar = true;

        return formationChecklist;
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("mMaxCapacityPP", mMaxCapacityPP);
        aNBT.setInteger("mCurrentCapacityPP", mCurrentCapacityPP);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        mMaxCapacityPP = aNBT.getInteger("mMaxCapacityPP");
        mCurrentCapacityPP = aNBT.getInteger("mCurrentCapacityPP");
    }

    @Override
    public int getParallel() {
        if (getMaxCapacityPP() < 4) return -1;
        return getMaxCapacityPP();
    }

    @Override
    public int getPollutionPerTick(ItemStack aStack) {
        return 0;
    }
}
