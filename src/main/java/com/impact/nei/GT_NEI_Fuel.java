package com.impact.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.guihook.GuiContainerManager;
import codechicken.nei.guihook.IContainerInputHandler;
import codechicken.nei.guihook.IContainerTooltipHandler;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cpw.mods.fml.common.event.FMLInterModComms;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.objects.ItemData;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GT_NEI_Fuel extends TemplateRecipeHandler {

    public static final int sOffsetX = 5;
    public static final int sOffsetY = 11;

    static {
        GuiContainerManager.addInputHandler(new GT_RectHandler());
        GuiContainerManager.addTooltipHandler(new GT_RectHandler());
    }


    public GT_NEI_Fuel() {
        this.transferRects.add(new RecipeTransferRect(new Rectangle(75, 32, 18, 18), getOverlayIdentifier()));
        if (!NEI_Impact_Config.sIsAdded) {
            FMLInterModComms.sendRuntimeMessage(GT_Values.GT, "NEIPlugins", "register-crafting-handler", "gregtech@" + getRecipeName() + "@" + getOverlayIdentifier());
            GuiCraftingRecipe.craftinghandlers.add(this);
            GuiUsageRecipe.usagehandlers.add(this);
        }
    }

    public static void drawText(int aX, int aY, String aString, int aColor) {
        Minecraft.getMinecraft().fontRenderer.drawString(aString, aX, aY, aColor);
    }

    public TemplateRecipeHandler newInstance() {
        return new GT_NEI_Fuel();
    }

    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(getOverlayIdentifier())) {
            this.arecipes.add(new CachedDefaultRecipe());
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack aResult) {

        ArrayList<ItemStack> tResults = new ArrayList();
        tResults.add(aResult);
        tResults.add(GT_OreDictUnificator.get(true, aResult));

        CachedDefaultRecipe tNEIRecipe = new CachedDefaultRecipe();
        for (ItemStack tStack : tResults) {
            if (tNEIRecipe.contains(tNEIRecipe.mOutputs, tStack)) {
                this.arecipes.add(tNEIRecipe);
                break;
            }
        }

    }

    public void loadUsageRecipes(ItemStack aInput) {

        ArrayList<ItemStack> tInputs = new ArrayList();
        tInputs.add(aInput);
        tInputs.add(GT_OreDictUnificator.get(false, aInput));

        CachedDefaultRecipe tNEIRecipe = new CachedDefaultRecipe();
        for (ItemStack tStack : tInputs) {
            if (tNEIRecipe.contains(tNEIRecipe.mInputs, tStack)) {
                this.arecipes.add(tNEIRecipe);
                break;
            }
        }
    }

    public String getOverlayIdentifier() {
        return "";
    }

    public void drawBackground(int recipe) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(-4, -8, 1, 3, 174, 79);
    }

    public int recipiesPerPage() {
        return 1;
    }

    public String getRecipeName() {
        return "Rocket Fuel Production";
    }

    public String getGuiTexture() {
        return "gregtech:textures/gui/basic/RocketFuel.png";
    }

    public List<String> handleItemTooltip(GuiRecipe gui, ItemStack aStack, List<String> currenttip, int aRecipeIndex) {
        return currenttip;
    }

    public void drawExtras(int aRecipeIndex) {
        drawText(4, 85, "For any Rockets", -16777216);
        drawText(4, 95, "Need to make the rocket fuel", -16777216);
        drawText(4, 105, "Need to fill the canister", -16777216);
        drawText(4, 115, "Need to fuel the rocket", -16777216);
    }

    public static class GT_RectHandler implements IContainerInputHandler, IContainerTooltipHandler {
        public boolean mouseClicked(GuiContainer gui, int mousex, int mousey, int button) {
            return false;
        }

        public boolean lastKeyTyped(GuiContainer gui, char keyChar, int keyCode) {
            return false;
        }

        public List<String> handleTooltip(GuiContainer gui, int mousex, int mousey, List<String> currenttip) {

            return currenttip;
        }

        public List<String> handleItemDisplayName(GuiContainer gui, ItemStack itemstack, List<String> currenttip) {
            return currenttip;
        }

        public List<String> handleItemTooltip(GuiContainer gui, ItemStack itemstack, int mousex, int mousey, List<String> currenttip) {
            return currenttip;
        }

        public boolean keyTyped(GuiContainer gui, char keyChar, int keyCode) {
            return false;
        }

        public void onKeyTyped(GuiContainer gui, char keyChar, int keyID) {
        }

        public void onMouseClicked(GuiContainer gui, int mousex, int mousey, int button) {
        }

        public void onMouseUp(GuiContainer gui, int mousex, int mousey, int button) {
        }

        public boolean mouseScrolled(GuiContainer gui, int mousex, int mousey, int scrolled) {
            return false;
        }

        public void onMouseScrolled(GuiContainer gui, int mousex, int mousey, int scrolled) {
        }

        public void onMouseDragged(GuiContainer gui, int mousex, int mousey, int button, long heldTime) {
        }
    }

    public class FixedPositionedStack extends PositionedStack {

        public final int mChance;
        public boolean permutated = false;

        public FixedPositionedStack(Object object, int x, int y) {
            this(object, x, y, 0);
        }

        public FixedPositionedStack(Object object, int x, int y, int aChance) {
            super(object, x, y, true);
            this.mChance = aChance;
        }

        public void generatePermutations() {
            if (this.permutated) {
                return;
            }
            ArrayList<ItemStack> tDisplayStacks = new ArrayList();
            for (ItemStack tStack : this.items) {
                if (GT_Utility.isStackValid(tStack)) {
                    if (tStack.getItemDamage() == 32767) {
                        List<ItemStack> permutations = codechicken.nei.ItemList.itemMap.get(tStack.getItem());
                        if (!permutations.isEmpty()) {
                            ItemStack stack;
                            for (Iterator i$ = permutations.iterator(); i$.hasNext(); tDisplayStacks.add(GT_Utility.copyAmount(tStack.stackSize, stack))) {
                                stack = (ItemStack) i$.next();
                            }
                        } else {
                            ItemStack base = new ItemStack(tStack.getItem(), tStack.stackSize);
                            base.stackTagCompound = tStack.stackTagCompound;
                            tDisplayStacks.add(base);
                        }
                    } else {
                        tDisplayStacks.add(GT_Utility.copy(tStack));
                    }
                }
            }
            this.items = ((ItemStack[]) tDisplayStacks.toArray(new ItemStack[0]));
            if (this.items.length == 0) {
                this.items = new ItemStack[]{new ItemStack(Blocks.fire)};
            }
            this.permutated = true;
            setPermutationToRender(0);
        }
    }

    public class CachedDefaultRecipe
            extends CachedRecipe {
        public final List<PositionedStack> mOutputs = new ArrayList();
        public final List<PositionedStack> mInputs = new ArrayList();

        public CachedDefaultRecipe() {
            super();
            String[] modid = new String[] {"GalacticraftCore", "GalacticraftCore", "GalacticraftCore", "GalaxySpace", "GalaxySpace", "GalaxySpace", "GalaxySpace", "GalaxySpace"};
            String[] item = new String[] {"item.spaceship", "item.spaceshipTier2", "item.itemTier3Rocket", "item.Tier4Rocket", "item.Tier5Rocket", "item.Tier6Rocket", "item.Tier7Rocket", "item.Tier8Rocket"};

            //for(int i = 0; i < modid.length; i++)
            this.mInputs.add(new FixedPositionedStack(GT_ModHandler.getModItem(modid[0], item[0], 1L), 25, 22));
            this.mInputs.add(new FixedPositionedStack(GT_Utility.getFluidDisplayStack(new FluidStack(ItemList.sRocketFuel, 1000), true), 125, 22));
            this.mOutputs.add(new FixedPositionedStack(GT_ModHandler.getModItem("GalacticraftCore", "item.fuelCanisterPartial", 1L, 1), 75, 22));
        }

        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(GT_NEI_Fuel.this.cycleticks / 10, this.mInputs);
        }

        public PositionedStack getResult() {
            return null;
        }

        public List<PositionedStack> getOtherStacks() {
            return this.mOutputs;
        }
    }
}
