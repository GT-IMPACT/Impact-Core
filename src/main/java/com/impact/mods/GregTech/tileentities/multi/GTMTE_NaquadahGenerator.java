package com.impact.mods.GregTech.tileentities.multi;

import com.github.technus.tectech.mechanics.constructable.IConstructable;
import com.github.technus.tectech.mechanics.structure.IStructureDefinition;
import com.github.technus.tectech.mechanics.structure.StructureDefinition;
import com.github.technus.tectech.thing.block.QuantumStuffBlock;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.GT_MetaTileEntity_MultiblockBase_EM;
import com.impact.block.blocks.Block_QuantumStuff;
import com.impact.mods.GregTech.tileentities.multi.gui.GT_Container_MultiParallelMachine;
import com.impact.mods.GregTech.tileentities.multi.gui.GUI_BASE;
import com.impact.util.MultiBlockTooltipBuilder;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

import static com.github.technus.tectech.mechanics.structure.StructureUtility.*;
import static com.impact.mods.GregTech.casings.CORE_API.sCaseCore1;
import static com.impact.mods.GregTech.casings.CORE_API.sCaseCore2;
import static gregtech.api.GregTech_API.sBlockCasings4;
import static gregtech.api.enums.GT_Values.V;

public class GTMTE_NaquadahGenerator extends GT_MetaTileEntity_MultiblockBase_EM implements IConstructable {

    private static final String[] description = new String[]{
            EnumChatFormatting.RED + "Impact Details:",
            "- Test Casing",
            "- Upgrade Casing (Tier 1-4) or Air (no parallel)",
            "- Hatches (any PBE Casing)",
    };

    private static final IStructureDefinition<GTMTE_NaquadahGenerator> STRUCTURE_DEFINITION =
            StructureDefinition.<GTMTE_NaquadahGenerator>builder()
                    .addShape("main", new String[][]{
                            {"               ", "      CCC      ", "    CC   CC    ", "   C       C   ", "  C         C  ", "  C         C  ", " C           C ", " C           C ", " C           C ", "  C         C  ", "  C         C  ", "   C       C   ", "    CC   CC    ", "      C~C      ", "               "},
                            {"      CCC      ", "    CCBBBCC    ", "   CBBAAABBC   ", "  CBAA   AABC  ", " CBA       ABC ", " CBA       ABC ", "CBA         ABC", "CBA         ABC", "CBA         ABC", " CBA       ABC ", " CBA       ABC ", "  CBAA   AABC  ", "   CBBAAABBC   ", "    CCBBBCC    ", "      CCC      "},
                            {"               ", "      CCC      ", "    CC   CC    ", "   C       C   ", "  C         C  ", "  C         C  ", " C           C ", " C           C ", " C           C ", "  C         C  ", "  C         C  ", "   C       C   ", "    CC   CC    ", "      CCC      ", "               "}
                    })
                    .addElement('A', ofBlock(sBlockCasings4, 6))
                    .addElement('B', ofBlock(sBlockCasings4, 7))
                    .addElement('C', ofChain(
                            ofHatchAdder(GTMTE_NaquadahGenerator::addClassicToMachineList, 410, sCaseCore2, 10),
                            onElementPass(t -> t.casingCount++, ofBlock(sCaseCore2, 10))
                    ))
                    .build();


    private static Block CASING = sCaseCore1;
    private static byte CASING_META = 4;
    private static ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META];
    private static int CASING_TEXTURE_ID = CASING_META + 128 * 3;
    private int casingCount = 0;

    public GTMTE_NaquadahGenerator(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GTMTE_NaquadahGenerator(String aName) {
        super(aName);
    }

    @Override
    public IStructureDefinition<GTMTE_NaquadahGenerator> getStructure_EM() {
        return STRUCTURE_DEFINITION;
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_NaquadahGenerator(mName);
    }

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing,
                                 final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == aFacing
                ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(
                aActive
                        ? Textures.BlockIcons.MP1a
                        : Textures.BlockIcons.MP1)}
                : new ITexture[]{INDEX_CASE};
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_Container_MultiParallelMachine(aPlayerInventory, aBaseMetaTileEntity);
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png", "mModed");
    }

    @Override
    public boolean checkMachine_EM(IGregTechTileEntity iGregTechTileEntity, ItemStack itemStack) {
        casingCount = 0;
        return structureCheck_EM("main", 7, 13, 0) && casingCount >= 5;
    }

    @Override
    public boolean checkRecipe_EM(ItemStack itemStack) {
        ArrayList<ItemStack> tInputList = getStoredInputs();
        int tInputList_sS = tInputList.size();
        for (int i = 0; i < tInputList_sS - 1; i++) {
            for (int j = i + 1; j < tInputList_sS; j++) {
                if (GT_Utility.areStacksEqual((ItemStack) tInputList.get(i), (ItemStack) tInputList.get(j))) {
                    if (((ItemStack) tInputList.get(i)).stackSize >= ((ItemStack) tInputList.get(j)).stackSize) {
                        tInputList.remove(j--);
                        tInputList_sS = tInputList.size();
                    } else {
                        tInputList.remove(i--);
                        tInputList_sS = tInputList.size();
                        break;
                    }
                }
            }
        }
        tInputList.add(mInventory[1]);
        ItemStack[] inputs = tInputList.toArray(new ItemStack[tInputList.size()]);

        ArrayList<FluidStack> tFluidList = getStoredFluids();
        int tFluidList_sS = tFluidList.size();
        for (int i = 0; i < tFluidList_sS - 1; i++) {
            for (int j = i + 1; j < tFluidList_sS; j++) {
                if (GT_Utility.areFluidsEqual(tFluidList.get(i), tFluidList.get(j))) {
                    if (tFluidList.get(i).amount >= tFluidList.get(j).amount) {
                        tFluidList.remove(j--);
                        tFluidList_sS = tFluidList.size();
                    } else {
                        tFluidList.remove(i--);
                        tFluidList_sS = tFluidList.size();
                        break;
                    }
                }
            }
        }
        FluidStack[] fluids = tFluidList.toArray(new FluidStack[tFluidList.size()]);

        if (inputs.length > 0 || fluids.length > 0) {
            long voltage = getMaxInputVoltage();
            byte tier = (byte) Math.max(0, GT_Utility.getTier(voltage));
            GT_Recipe recipe = GT_Recipe.GT_Recipe_Map.sWiremillRecipes.findRecipe(getBaseMetaTileEntity(), false,
                    false, V[tier], fluids, inputs);
            if (recipe != null && recipe.isRecipeInputEqual(true, fluids, inputs)) {
                this.mEfficiency = (10000 - (getIdealStatus() - getRepairStatus()) * 1000);
                this.mEfficiencyIncrease = 10000;

                int EUt = recipe.mEUt;
                int maxProgresstime = recipe.mDuration;

                if (getRecipeMap() == GT_Recipe.GT_Recipe_Map.sSawMill0 || getRecipeMap() == GT_Recipe.GT_Recipe_Map.sSawMill1 || getRecipeMap() == GT_Recipe.GT_Recipe_Map.sSawMill2) {
                    if (tier > 1) {
                        while (EUt <= V[tier] && maxProgresstime > 2) {
                            EUt *= 2;
                            maxProgresstime /= 2;
                        }
                    }
                    if (maxProgresstime < 1) {
                        maxProgresstime = 1;
                        EUt = recipe.mEUt * recipe.mDuration / 2;
                    }
                }

                this.mEUt = -EUt;
                this.mMaxProgresstime = maxProgresstime;
                mOutputItems = new ItemStack[recipe.mOutputs.length];
                for (int i = 0; i < recipe.mOutputs.length; i++) {
                    if (getBaseMetaTileEntity().getRandomNumber(10000) < recipe.getOutputChance(i)) {
                        this.mOutputItems[i] = recipe.getOutput(i);
                    }
                }

                this.mOutputFluids = recipe.mFluidOutputs;
                this.updateSlots();
                quantumStuff(true);
                return true;
            }
        }
        quantumStuff(false);
        return false;
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
    }

    private void quantumStuff(boolean shouldExist) {
        IGregTechTileEntity base = getBaseMetaTileEntity();
        if (base != null && base.getWorld() != null) {
            int xDir = ForgeDirection.getOrientation(base.getBackFacing()).offsetX + base.getXCoord();
            int yDir = ForgeDirection.getOrientation(base.getBackFacing()).offsetY*6 + base.getYCoord();
            int zDir = ForgeDirection.getOrientation(base.getBackFacing()).offsetZ + base.getZCoord();
            Block block = base.getWorld().getBlock(xDir, yDir, zDir);
            if (shouldExist) {
                if (block != null) {
                    base.getWorld().setBlock(xDir, yDir+6, zDir, Block_QuantumStuff.INSTANCE, 0, 2);
                }
            } else {
                    base.getWorld().setBlock(xDir, yDir+6, zDir, QuantumStuffBlock.INSTANCE, 0, 2);
            }
        }
    }

    public boolean Stuff;

    @Override
    public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
        if (aPlayer.isSneaking())
        {
            quantumStuff(true);
            GT_Utility.sendChatToPlayer(aPlayer, "Enabled");

        }
        else {
            quantumStuff(false);
            GT_Utility.sendChatToPlayer(aPlayer, "Disabled");

        }

    }

    @Override
    public String[] getDescription() {
        final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
        b
                .addInfo("Multi-Amperes generator")
                .addSeparator()
                .addController()
                .addEnergyHatch("Any casing")
                .addMaintenanceHatch("Any casing")
                .addInputBus("Any casing (max x15)")
                .addOutputBus("Any casing (max x3)")
                .addMuffler("Any casing")
                .addCasingInfo("Test Casing")
                .signAndFinalize(": " + EnumChatFormatting.RED + "IMPACT");
        if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            return b.getInformation();
        } else {
            return b.getStructureInformation();
        }
    }

    @Override
    public String[] getInfoData() {
        return new String[]{
                "5454",
        };
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        structureBuild_EM("main", 7, 13, 0, hintsOnly, stackSize);
    }

    @Override
    public String[] getStructureDescription(ItemStack stackSize) {
        return description;
    }
}
