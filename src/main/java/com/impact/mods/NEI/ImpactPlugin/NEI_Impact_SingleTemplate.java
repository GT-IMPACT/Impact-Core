package com.impact.mods.NEI.ImpactPlugin;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.guihook.GuiContainerManager;
import codechicken.nei.guihook.IContainerInputHandler;
import codechicken.nei.guihook.IContainerTooltipHandler;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class NEI_Impact_SingleTemplate extends TemplateRecipeHandler {

    public static ItemStack[] amInputs;
    public static int[] amXPosInputs;
    public static int[] amYPosInputs;
    public static ItemStack[] amOutputs;
    public static int[] amXPosOutputs;
    public static int[] amYPosOutputs;
    public static int[] amReactangle;
    public static boolean amOverGUI;

    static {
        GuiContainerManager.addInputHandler(new GT_RectHandler());
        GuiContainerManager.addTooltipHandler(new GT_RectHandler());
    }

    public NEI_Impact_SingleTemplate(int[] Reactangle, ItemStack[] Inputs, int[] xPosInputs, int[] yPosInputs, ItemStack[] Outputs, int[] xPosOutputs, int[] yPosOutputs, boolean OverGUI) {
        amReactangle = Reactangle;
        amInputs = Inputs;
        amXPosInputs = xPosInputs;
        amYPosInputs = yPosInputs;
        amOutputs = Outputs;
        amXPosOutputs = xPosOutputs;
        amYPosOutputs = yPosOutputs;
        amOverGUI = OverGUI;
        this.transferRects.add(new RecipeTransferRect(new Rectangle(amReactangle[0], amReactangle[1], amReactangle[2], amReactangle[3]), getOverlayIdentifier()));
    }

    public static void drawText(int aX, int aY, String aString, int aColor) {
        Minecraft.getMinecraft().fontRenderer.drawString(aString, aX, aY, aColor);
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
        if (!amOverGUI)
            GuiDraw.drawTexturedModalRect(-4, -8, 1, 3, 174, 79);
    }

    public int recipiesPerPage() {
        return 1;
    }

    public List<String> handleItemTooltip(GuiRecipe gui, ItemStack aStack, List<String> currenttip, int aRecipeIndex) {
        return currenttip;
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
            this.items = (tDisplayStacks.toArray(new ItemStack[0]));
            if (this.items.length == 0) {
                this.items = new ItemStack[]{new ItemStack(Blocks.fire)};
            }
            this.permutated = true;
            setPermutationToRender(0);
        }
    }

    public class CachedDefaultRecipe extends CachedRecipe {
        public final List<PositionedStack> mOutputs = new ArrayList();
        public final List<PositionedStack> mInputs = new ArrayList();

        public CachedDefaultRecipe() {
            super();
            if (amInputs != null) {
                if (amInputs.length == 1) {
                    this.mInputs.add(new FixedPositionedStack(amInputs[0], amXPosInputs[0], amYPosInputs[0]));
                } else if (amInputs.length == 2) {
                    this.mInputs.add(new FixedPositionedStack(amInputs[0], amXPosInputs[0], amYPosInputs[0]));
                    this.mInputs.add(new FixedPositionedStack(amInputs[1], amXPosInputs[1], amYPosInputs[1]));
                } else if (amInputs.length == 3) {
                    this.mInputs.add(new FixedPositionedStack(amInputs[0], amXPosInputs[0], amYPosInputs[0]));
                    this.mInputs.add(new FixedPositionedStack(amInputs[1], amXPosInputs[1], amYPosInputs[1]));
                    this.mInputs.add(new FixedPositionedStack(amInputs[2], amXPosInputs[2], amYPosInputs[2]));
                }
            }
            if (amOutputs != null) {
                if (amOutputs.length == 1) {
                    this.mOutputs.add(new FixedPositionedStack(amOutputs[0], amXPosOutputs[0], amYPosOutputs[0]));
                } else if (amOutputs.length == 2) {
                    this.mOutputs.add(new FixedPositionedStack(amOutputs[0], amXPosOutputs[0], amYPosOutputs[0]));
                    this.mOutputs.add(new FixedPositionedStack(amOutputs[1], amXPosOutputs[1], amYPosOutputs[1]));
                } else if (amOutputs.length == 3) {
                    this.mOutputs.add(new FixedPositionedStack(amOutputs[0], amXPosOutputs[0], amYPosOutputs[0]));
                    this.mOutputs.add(new FixedPositionedStack(amOutputs[1], amXPosOutputs[1], amYPosOutputs[1]));
                    this.mOutputs.add(new FixedPositionedStack(amOutputs[2], amXPosOutputs[2], amYPosOutputs[2]));
                }
            }
        }

        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(NEI_Impact_SingleTemplate.this.cycleticks / 10, this.mInputs);
        }

        public PositionedStack getResult() {
            return null;
        }

        public List<PositionedStack> getOtherStacks() {
            return this.mOutputs;
        }
    }
}
