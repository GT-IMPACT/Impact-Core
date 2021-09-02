package com.impact.mods.gregtech.tileentities.multi.photonsystem;

import com.github.technus.tectech.mechanics.alignment.enumerable.ExtendedFacing;
import com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer;
import com.github.technus.tectech.mechanics.structure.IStructureDefinition;
import com.github.technus.tectech.mechanics.structure.StructureDefinition;
import com.impact.common.item.Core_Items3;
import com.impact.impact;
import com.impact.loader.ItemRegistery;
import com.impact.mods.gregtech.GT_RecipeMaps;
import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.Utilits;
import com.impact.util.multis.OverclockCalculate;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

import static com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer.registerMetaClass;
import static com.github.technus.tectech.mechanics.structure.StructureUtility.ofBlock;
import static gregtech.api.enums.GT_Values.V;
import static net.minecraft.util.EnumChatFormatting.*;

public class GTMTE_MESystemProvider extends GT_MetaTileEntity_MultiParallelBlockBase {

    public static Block CASING = Casing_Helper.sCaseCore2;
    public static byte CASING_META = 15;
    ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META + 16];
    int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;

    public int mPhotonsSummary = 0;

    //region Register
    public GTMTE_MESystemProvider(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
        run();
    }

    public GTMTE_MESystemProvider(String aName) {
        super(aName);
        run();
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        run();
        return new GTMTE_MESystemProvider(this.mName);
    }
    //endregion

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide,
                                 final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == aFacing ? new ITexture[]{INDEX_CASE,
                new GT_RenderedTexture(aActive ? Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1)}
                : new ITexture[]{INDEX_CASE};
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
//        return new GUI_SuperParallelComputer(aPlayerInventory, aBaseMetaTileEntity, getLocalName());
        return false;
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
//        return new Container_SuperParallelComputer(aPlayerInventory, aBaseMetaTileEntity, this);
        return false;
    }

    public void run() {

        impact.I_RA.addMESPRecipes(new ItemStack[]{GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockInterface", 1L, 0)},
                GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 440), 20*2, 2048, 5);

        registerMetaClass(GTMTE_MESystemProvider.class,
                new IMultiblockInfoContainer<GTMTE_MESystemProvider>() {
                    //region Structure
                    private final IStructureDefinition<GTMTE_MESystemProvider> definition =
                            StructureDefinition.<GTMTE_MESystemProvider>builder()
                                    .addShape("main", new String[][]{
                                            {"AA AA", "AB~BA", "AA AA"},
                                            {"ABBBA", "D   D", "ABBBA"},
                                            {"AA AA", "ABBBA", "AA AA"},
                                    })
                                    .addElement('A', ofBlock(CASING, CASING_META))
                                    .addElement('B', ofBlock(ItemRegistery.photonSystem, 0))
                                    .addElement('D', ofBlock(ItemRegistery.IGlassBlock))
                                    .build();
                    private final String[] desc = new String[]{
                            RED + "Impact Details:",
                    };

                    //endregion
                    @Override
                    public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_MESystemProvider tileEntity, ExtendedFacing aSide) {
                        IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
                        definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
                                base.getXCoord(), base.getYCoord(), base.getZCoord(),
                                2, 1, 0, hintsOnly);
                    }

                    @Override
                    public String[] getDescription(ItemStack stackSize) {
                        return desc;
                    }
                });
    }

    @Override
    public String[] getDescription() {
        final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
        b
                .addTypeMachine("psc.name") //Parametric Diffuser
                .addSeparator()
                .addController()
                .addEnergyHatch("psc.hatches")
                .addMaintenanceHatch("psc.hatches")
                .addOtherStructurePart("psc.other.0", "psc.other.1")
                .addOtherStructurePart("psc.other.2", "psc.other.3")
                .addOtherStructurePart("psc.other.4", "psc.other.5")
                .addCasingInfo("psc.case")
                .signAndFinalize();
        if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            return b.getInformation();
        } else {
            return b.getStructureInformation();
        }
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_RecipeMaps.sMESystemProvider;
    }

    @Override
    public boolean checkRecipe(ItemStack aStack) {
        ArrayList<ItemStack> tInputList;
        ItemStack[] tInputs;
        if (mInputBusses.size() > 1) {
            for (int countBus = 1; countBus < mInputBusses.size(); countBus++) {
                if (modeBuses == 0) {

                    ArrayList<ItemStack> tBusItems = new ArrayList<>();
                    mInputBusses.get(countBus).mRecipeMap = getRecipeMap();
                    if (isValidMetaTileEntity(mInputBusses.get(countBus))) {
                        for (int i = mInputBusses.get(countBus).getBaseMetaTileEntity().getSizeInventory() - 1; i >= 0; i--) {
                            if (mInputBusses.get(countBus).getBaseMetaTileEntity().getStackInSlot(i) != null) {
                                tBusItems.add(mInputBusses.get(countBus).getBaseMetaTileEntity().getStackInSlot(i));
                            }
                        }
                    }
                    tInputList = this.getStoredInputs();
                    tInputs = tBusItems.toArray(new ItemStack[]{});
                } else {
                    tInputList = this.getStoredInputs();
                    tInputs = tInputList.toArray(new ItemStack[tInputList.size()]);
                }
                if (tInputList.size() > 0) {
                    long nominalV = getMaxInputVoltage();
                    byte tTier = (byte) Math.max(1, GT_Utility.getTier(nominalV));
                    GT_Recipe tRecipe;
                    tRecipe = getRecipeMap().findRecipe(this.getBaseMetaTileEntity(), false, V[tTier], null, tInputs);
                    int x = 0;
                    if (tRecipe != null && (mPhotonsSummary - tRecipe.mSpecialValue >= 0)) {


                        ArrayList<ItemStack> outputItems = new ArrayList<>();
                        boolean found_Recipe = false;
                        int processed = 0;
                        while ((this.getStoredFluids().size() | this.getStoredInputs().size()) > 0 && processed < 1) {
                            if ((tRecipe.mEUt * (processed + 1L)) < nominalV && tRecipe.isRecipeInputEqual(true, null, tInputs)) {
                                found_Recipe = true;

                                for (int i = 0; i < tRecipe.mOutputs.length; i++) {
                                    outputItems.add(tRecipe.getOutput(i));
                                }
                                ++processed;
                            } else {
                                break;
                            }
                        }
                        if (found_Recipe) {
                            this.mEfficiency = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
                            this.mEfficiencyIncrease = 10000;
                            long actualEUT = (long) (tRecipe.mEUt) * processed;
                            mPhotonsSummary -= tRecipe.mSpecialValue * processed;

                            OverclockCalculate.calculateOverclockedNessMulti((int) actualEUT, tRecipe.mDuration, 1, nominalV, this);

                            if (this.mMaxProgresstime == Integer.MAX_VALUE - 1 && this.mEUt == Integer.MAX_VALUE - 1) {
                                return false;
                            }
                            if (this.mEUt > 0) {
                                this.mEUt = (-this.mEUt);
                            }
                            this.mOutputItems = new ItemStack[outputItems.size()];
                            this.mOutputItems = outputItems.toArray(this.mOutputItems);
                            Utilits.sendChatByTE(getBaseMetaTileEntity(), "Производится: " + mOutputItems[0].getDisplayName());
                            Utilits.sendChatByTE(getBaseMetaTileEntity(), "Буфер фотонов: " + mPhotonsSummary + " | Фотонов потрачено: " + tRecipe.mSpecialValue);
                            this.updateSlots();
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    @Override
    public void onPostTick(IGregTechTileEntity iAm, long aTick) {
        super.onPostTick(iAm, aTick);
        int amount = 0;
        if (iAm.isServerSide() && aTick % 40 == 0) {

            if (mInputBusses.size() > 0) {
                if (mInputBusses.get(0).mInventory.length > 0) {
                    for (ItemStack is : mInputBusses.get(0).mInventory) {
                        if (GT_Utility.areStacksEqual(is, Core_Items3.getInstance().get(1, 1))) {
                            amount += is.stackSize;
                        }
                    }
                }
                for (int i = 0; i < amount; i++) {
                    if (mPhotonsSummary <= 99_900 && depleteInput(Core_Items3.getInstance().get(1, 1))) {
                        mPhotonsSummary += 1000;
                        addOutput(Core_Items3.getInstance().get(0, 1));
                    } else break;
                }
            }
        }
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity iAm) {

//        if (!Utilits.isLowGravity(iAm)) {
//            return false;
//        }
        //region Structure
        final Vector3ic forgeDirection = new Vector3i(
                ForgeDirection.getOrientation(iAm.getBackFacing()).offsetX,
                ForgeDirection.getOrientation(iAm.getBackFacing()).offsetY,
                ForgeDirection.getOrientation(iAm.getBackFacing()).offsetZ);

        boolean formationChecklist = true;

        Vector3ic offset;
        IGregTechTileEntity currentTE;

        for (int x = 0; x <= 5; x++) {
            offset = rotateOffsetVector(forgeDirection, x, 0, 0);
            currentTE = iAm.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());

            if (x==0) continue;

            if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
                    && !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
                    && !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)
                    && !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)) {
                if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                        && (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
                } else {
                    formationChecklist = false;
                }
            }
        }

        return formationChecklist;
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("mPhotonsSummary", mPhotonsSummary);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        mPhotonsSummary = aNBT.getInteger("mPhotonsSummary");
    }

    public String[] getInfoData() {
        return new String[]{
                "Usage Energy: " + RED + -mEUt + RESET + " EU/t",
                "Max Voltage: " + YELLOW + getMaxInputVoltage() + RESET + " EU/t ",
                "Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus()) ? GREEN + "Good "
                        + YELLOW + mEfficiency / 100.0F + " %" + RESET
                        : RED + "Has Problems " + mEfficiency / 100.0F + " %" + RESET),
        };
    }
}