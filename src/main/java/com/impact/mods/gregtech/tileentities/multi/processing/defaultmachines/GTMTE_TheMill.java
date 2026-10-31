package com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines;

import codechicken.nei.recipe.GuiCraftingRecipe;
import com.impact.common.te.TE_TheMill;
import com.impact.impact;
import com.impact.mods.gregtech.GT_RecipeMaps;
import com.impact.mods.gregtech.blocks.ImpactCasings;
import com.impact.mods.gregtech.enums.Texture;
import com.impact.mods.gregtech.gui.base.GT_GUIContainerMT_Machine;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.util.Utilits;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.gui.GT_Slot_Output;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.common.items.GT_MetaGenerated_Tool_01;
import gregtech.common.tools.GT_Tool_Axe;
import java.awt.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import space.impact.structures.alignment.IAlignmentLimits;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.AutoPlaceEnvironment;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.IStructureElement;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static com.impact.common.item.Core_List_Items.*;
import static com.impact.loader.ItemRegistery.TheWind;
import static gregtech.api.enums.GT_Values.RES_PATH_GUI;
import static space.impact.structures.structure.StructureUtility.ofBlockAnyMeta;
import static space.impact.structures.structure.StructureUtility.ofHint;

public class GTMTE_TheMill extends GTMTE_Impact_BlockBase<GTMTE_TheMill> {

    private static final ICasing CASING = ImpactCasings.MILL;

    public GTMTE_TheMill(int aID, String aNameRegional) {
        super(aID, "impact.multis.the_mill", aNameRegional, 5);
        enabledMaintenance = false;
        impact.I_RA.addTheMillRecipes(
                DropCrashedStone.get(1),
                new ItemStack[] {
                        Utilits.is(OrePrefixes.dustImpure, Materials.Calcite),
                        Utilits.is(OrePrefixes.dustImpure, Materials.Gypsum)
                },
                new int[] { 10000, 10000 }, 20 * 10);
        impact.I_RA.addTheMillRecipes(DropCrashedMetallic.get(1),
                new ItemStack[] {
                        Utilits.is(OrePrefixes.dustImpure, Materials.Iron),
                        Utilits.is(OrePrefixes.dustImpure, Materials.Copper),
                        Utilits.is(OrePrefixes.dustImpure, Materials.Tin),
                        Utilits.is(OrePrefixes.dustImpure, Materials.Lead)
                },
                new int[] { 10000, 10000, 10000, 10000 }, 20 * 10);
        impact.I_RA.addTheMillRecipes(DropCrashedCoal.get(1),
                new ItemStack[] {
                        Utilits.is(OrePrefixes.dustImpure, Materials.Coal),
                        Utilits.is(OrePrefixes.dustImpure, Materials.Lignite),
                        Utilits.is(OrePrefixes.dustImpure, Materials.Sulfur)
                },
                new int[] { 10000, 10000, 10000 }, 20 * 10);
    }

    public GTMTE_TheMill(String aName) {
        super(aName, 5);
        enabledMaintenance = false;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder(mName)
                .addInfo("Is a primitive windmill")
                .addMachineType("Wind Mill")
                .addInfo("The wind is whirling, something is crushing")
                .addSeparator()
                .addController("See the structure hologram")
                .beginStructureBlock(9, 12, 9, false)
                .addCasingInfoMin("The Mill Wood Planks", 260, false)
                .addStructureHint("The Mill Rotor", HintColor.GREEN)
                .toolTipFinisher();
    }

    private static IStructureDefinition<GTMTE_TheMill> definition;

    @Override
    public IStructureDefinition<GTMTE_TheMill> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_TheMill>builder()
                    .addShape("main", new String[][] {
                            { "------------", "------------", "------------", "------------", "------------", "------A-----", "------------", "------------", "------------", "------------", "------------", "------------", "            ", "            " },
                            { "            ", "            ", "            ", "            ", "     CCC    ", "     C~C    ", "     CCC    ", "            ", "            ", "            ", "     CCC    ", "    CCCCC   ", "    CC CC   ", "    CC CC   " },
                            { "            ", "            ", "            ", "     CCC    ", "    C   C   ", "    C   C   ", "    C   C   ", "     CCC    ", "     CCC    ", "     CCC    ", "   CC   CC  ", "   C     C  ", "   C     C  ", "   C     C  " },
                            { "            ", "            ", "     CCC    ", "    C   C   ", "   C     C  ", "   C     C  ", "   C     C  ", "    CCCCC   ", "    C   C   ", "    C   C   ", "   C     C  ", "  C       C ", "  C       C ", "  C       C " },
                            { "            ", "            ", "    CCCCC   ", "   C     C  ", "  C       C ", "  C       C ", "  C       C ", "   CCCCCCC  ", "   C     C  ", "   C     C  ", "  C       C ", "  C       C ", "  C       C ", "  C       C " },
                            { "            ", "            ", "    CCCCC   ", "   C     C  ", "  C       C ", "  C       C ", "  C       C ", "   CCCCCCC  ", "   C     C  ", "   C     C  ", "  C       C ", "  C       C ", "  C       C ", "  C       C " },
                            { "            ", "            ", "    CCCCC   ", "   C     C  ", "  C       C ", "  C       C ", "  C       C ", "   CCCCCCC  ", "   C     C  ", "   C     C  ", "  C       C ", "  C       C ", "  C       C ", "  C       C " },
                            { "            ", "            ", "     CCC    ", "    C   C   ", "   C     C  ", "   C     C  ", "   C     C  ", "    CC CC   ", "    C   C   ", "    C   C   ", "   C     C  ", "  C       C ", "  C       C ", "  C       C " },
                            { "            ", "            ", "            ", "     CCC    ", "    C   C   ", "    C   C   ", "    C   C   ", "     CCC    ", "     CCC    ", "     CCC    ", "   CC C CC  ", "   C  C  C  ", "   C  C  C  ", "   C  C  C  " },
                            { "            ", "            ", "            ", "            ", "     CCC    ", "     CCC    ", "     CCC    ", "            ", "            ", "            ", "     CCC    ", "    CCCCC   ", "    CCCCC   ", "    CCCCC   " }
                    })
                    .addElement('A', rotorElement())
                    .addElement('C', CASING.asElement())
                    .build();
        }
        return definition;
    }

    protected IAlignmentLimits getInitialAlignmentLimits() {
        return (direction, rotation, flip) -> (direction.ordinal() > 1) && rotation.isNotRotated()
                && flip.isNotFlipped();
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_TheMill(mName);
    }

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing,
            final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == aFacing
                ? new ITexture[] { CASING.getTexture(), TextureFactory.of(Texture.Icons.OVERLAY_PIPELINE_FLUID_FRONT) }
                : aSide == GT_Utility.getOppositeSide(aFacing)
                        ? new ITexture[] { CASING.getTexture(),
                                TextureFactory.of(Textures.BlockIcons.OVERLAY_PIPE_OUT) }
                        : new ITexture[] { CASING.getTexture() };
    }

    @Override
    public void onLeftclick(IGregTechTileEntity te, EntityPlayer aPlayer) {
        super.onLeftclick(te, aPlayer);
        if (aPlayer.getCurrentEquippedItem() != null) {
            ItemStack is = aPlayer.getCurrentEquippedItem();
            if (is.getItem() instanceof GT_MetaGenerated_Tool_01) {
                GT_MetaGenerated_Tool_01 tool = (GT_MetaGenerated_Tool_01) is.getItem();
                if (tool.getToolStats(is) instanceof GT_Tool_Axe) {
                    te.getBlock(te.getXCoord(), te.getYCoord(), te.getZCoord())
                            .harvestBlock(te.getWorld(), aPlayer, te.getXCoord(), te.getYCoord(), te.getZCoord(),
                                    te.getMetaTileID());
                }
            }
        }
    }

    @Override
    public void onPostTick(IGregTechTileEntity iAm, long aTick) {
        if (iAm.isServerSide() && iAm.isActive()) {
            if (mProgresstime >= mMaxProgresstime - 1) {
                this.addOutputProducts();
                this.mOutputItems = null;
            }
        }
        super.onPostTick(iAm, aTick);
        if (iAm.isServerSide() && (aTick % 20) == 0) {
            IInventory tTileEntity = iAm.getIInventoryAtSide(iAm.getBackFacing());
            if (tTileEntity != null) {
                GT_Utility.moveOneItemStack(iAm, tTileEntity, (byte) 1, (byte) 1, null, false, (byte) 64, (byte) 1,
                        (byte) 64, (byte) 1);
            }
        }
    }

    private void addOutputProducts() {
        if (this.mOutputItems != null) {
            int limit = Math.min(this.mOutputItems.length, 4);
            for (int i = 0; i < limit; ++i) {
                int absi = 1 + i;
                if (this.mInventory[absi] == null) {
                    this.mInventory[absi] = GT_Utility.copy(this.mOutputItems[i]);
                } else if (GT_Utility.areStacksEqual(this.mInventory[absi], this.mOutputItems[i])) {
                    this.mInventory[absi].stackSize = Math.min(this.mInventory[absi].getMaxStackSize(),
                            this.mInventory[absi].stackSize + this.mOutputItems[i].stackSize);
                }
            }
        }
    }

    @Override
    public boolean isValidSlot(int aIndex) {
        // Slot 0 is an input here and must also drop when the controller is broken.
        return aIndex >= 0 && aIndex < mInventory.length;
    }

    @Override
    public boolean inputStack(IGregTechTileEntity te, int slotIndex, int side, ItemStack stack) {
        return slotIndex == 0;
    }

    @Override
    public boolean outputStack(IGregTechTileEntity te, int slotIndex, int side, ItemStack stack) {
        return slotIndex > 0;
    }

    @Override
    public GT_Recipe.GT_Recipe_Map getRecipeMap() {
        return GT_RecipeMaps.sTheMill;
    }

    protected boolean canOutput(ItemStack... aOutputs) {
        if (aOutputs == null)
            return true;
        ItemStack[] tOutputSlots = { mInventory[1], mInventory[2], mInventory[3], mInventory[4] };
        for (int i = 0; i < tOutputSlots.length && i < aOutputs.length; i++)
            if (tOutputSlots[i] != null && aOutputs[i] != null
                    && (!GT_Utility.areStacksEqual(tOutputSlots[i], aOutputs[i], false)
                            || tOutputSlots[i].stackSize + aOutputs[i].stackSize > tOutputSlots[i].getMaxStackSize())) {
                return false;
            }
        return true;
    }

    @Override
    public boolean checkRecipe(ItemStack itemStack) {
        if (mInventory[0] != null) {
            GT_Recipe tRecipe = getRecipeMap().findRecipe(getBaseMetaTileEntity(), false, false, 0, null,
                    mInventory[0]);
            if ((tRecipe != null) && (canOutput(tRecipe.mOutputs))
                    && (tRecipe.isRecipeInputEqual(true, null, mInventory[0]))) {

                if (mInventory[0] != null && mInventory[0].stackSize <= 0) {
                    mInventory[0] = null;
                }

                mEfficiency = mEfficiencyIncrease = 10000;
                mMaxProgresstime = tRecipe.mDuration;
                mOutputItems = tRecipe.mOutputs;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity gt) {
        if (!checkPiece(6, 5, 1)) {
            return false;
        }
        int[] offset = new int[3];
        getExtendedFacing().getWorldOffset(new int[] { 0, 0, -1 }, offset);
        TE_TheMill rotor = (TE_TheMill) gt.getWorld().getTileEntity(
                gt.getXCoord() + offset[0], gt.getYCoord() + offset[1], gt.getZCoord() + offset[2]);
        if (gt.isServerSide() && rotor.getFacing() != gt.getFrontFacing()) {
            rotor.setFacing(gt.getFrontFacing());
        }
        return true;
    }

    private static IStructureElement<GTMTE_TheMill> rotorElement() {
        IStructureElement<GTMTE_TheMill> rotor = ofBlockAnyMeta(TheWind);
        return new IStructureElement<GTMTE_TheMill>() {
            @Override
            public boolean check(GTMTE_TheMill machine, World world, int x, int y, int z) {
                return world.getTileEntity(x, y, z) instanceof TE_TheMill;
            }

            @Override
            public boolean couldBeValid(GTMTE_TheMill machine, World world, int x, int y, int z, ItemStack trigger) {
                return check(machine, world, x, y, z);
            }

            @Override
            public boolean spawnHint(GTMTE_TheMill machine, World world, int x, int y, int z, ItemStack trigger) {
                return ofHint(HintColor.GREEN).spawnHint(machine, world, x, y, z, trigger);
            }

            @Override
            public boolean placeBlock(GTMTE_TheMill machine, World world, int x, int y, int z, ItemStack trigger) {
                return check(machine, world, x, y, z) || rotor.placeBlock(machine, world, x, y, z, trigger);
            }

            @Override
            public BlocksToPlace getBlocksToPlace(GTMTE_TheMill machine, World world, int x, int y, int z,
                    ItemStack trigger, AutoPlaceEnvironment environment) {
                return rotor.getBlocksToPlace(machine, world, x, y, z, trigger, environment);
            }
        };
    }

    @Override
    public void inValidate() {
        super.inValidate();
    }

    @Override
    public void construct(ItemStack itemStack, boolean b) {
        buildPiece(itemStack, b, 6, 5, 1);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 6, 5, 1, budget, environment);
    }

    @Override
    public boolean hasSeparate() {
        return false;
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new Container(aPlayerInventory, aBaseMetaTileEntity);
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI(new Container(aPlayerInventory, aBaseMetaTileEntity));
    }

    private static class GUI extends GT_GUIContainerMT_Machine {

        public GUI(GT_ContainerMetaTile_Machine aContainer) {
            super(aContainer, RES_PATH_GUI + "basic/" + "TheMill.png");
        }

        @Override
        protected void drawGuiContainerForegroundLayer(int par1, int par2) {
            fontRendererObj.drawString("The Mill", 6, 4, Color.WHITE.hashCode());
        }

        @Override
        protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
            super.drawGuiContainerBackgroundLayer(par1, par2, par3);
            int x = (width - xSize) / 2;
            int y = (height - ySize) / 2;
            drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
            double work = (double) mContainer.mProgressTime / (double) mContainer.mMaxProgressTime;
            drawTexturedModalRect(x + 68, y + 32, 176, 1, Math.min(20, (int) (work * 20)), 16);
        }

        @Override
        public void drawScreen(int mouseX, int mouseY, float par3) {
            super.drawScreen(mouseX, mouseY, par3);
            getTooltip(mouseX, mouseY, 69, 32, 16, 20, new String[] {
                    "Recipes"
            });
        }

        @Override
        protected void mouseClicked(int mouseX, int mouseY, int par3) {
            super.mouseClicked(mouseX, mouseY, par3);
            int x = (this.width - this.xSize) / 2;
            int y = (this.height - this.ySize) / 2;
            int xx = x + 69;
            int yy = y + 32;
            if (xx <= mouseX && xx + 20 >= mouseX && yy <= mouseY && yy + 16 >= mouseY) {
                GuiCraftingRecipe.openRecipeGui(GT_RecipeMaps.sTheMill.mUnlocalizedName);
            }
        }
    }

    private static class Container extends GT_ContainerMetaTile_Machine {

        public Container(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
            super(aInventoryPlayer, aTileEntity);
        }

        @Override
        public void addSlots(InventoryPlayer aInventoryPlayer) {
            addSlotToContainer(new Slot(this.mTileEntity, 0, 44, 32));
            addSlotToContainer(new GT_Slot_Output(this.mTileEntity, 1, 98, 23));
            addSlotToContainer(new GT_Slot_Output(this.mTileEntity, 2, 116, 23));
            addSlotToContainer(new GT_Slot_Output(this.mTileEntity, 3, 98, 41));
            addSlotToContainer(new GT_Slot_Output(this.mTileEntity, 4, 116, 41));
        }

        @Override
        public int getSlotCount() {
            return 5;
        }

        @Override
        public int getShiftClickSlotCount() {
            return 5;
        }
    }
}
