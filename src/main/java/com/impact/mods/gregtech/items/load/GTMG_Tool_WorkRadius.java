package com.impact.mods.gregtech.items.load;

import com.impact.mods.gregtech.items.Behaviour_ForgeHammer;
import com.impact.mods.gregtech.items.Behaviour_LumberAxe;
import com.impact.mods.gregtech.items.IImpact_Tools;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.SubTag;
import gregtech.api.interfaces.IOreRecipeRegistrator;
import gregtech.api.items.GT_MetaGenerated_Tool;
import gregtech.api.util.GT_ModHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.LinkedHashMap;
import java.util.Map;

public class GTMG_Tool_WorkRadius extends GT_MetaGenerated_Tool {

    public static GTMG_Tool_WorkRadius INSTANCE;
    public static Map<Integer, IImpact_Tools> ITool;

    public static final int FORGE_HAMMER = 0;
    public static final int LUMBER_AXE = 4;

    public GTMG_Tool_WorkRadius() {
        super("impact.metatool.01");
        INSTANCE = this;
        ITool = new LinkedHashMap<>();
        addTool(FORGE_HAMMER, "Forge Hammer", "", (IImpact_Tools) new Behaviour_ForgeHammer(1, 0));
        addTool(LUMBER_AXE, "Lumber Axe", "", (IImpact_Tools) new Behaviour_LumberAxe(1, 0));

        GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(FORGE_HAMMER, 1, Materials.Flint, Materials.Wood, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"FFF", " S ", " SS", 'S', OrePrefixes.stick.get(Materials.Wood), 'F', new ItemStack(Items.flint, 1)});
        GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(LUMBER_AXE, 1, Materials.Flint, Materials.Wood, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"FFF", " S ", "SS ", 'S', OrePrefixes.stick.get(Materials.Wood), 'F', new ItemStack(Items.flint, 1)});
    }

    public ItemStack addTool(int aID, String aEnglish, String aToolTip, IImpact_Tools aTools, Object... aOreDictNames) {
        ITool.put(aID, aTools);
        ITool.put(aID + 1, aTools);
        return addTool(aID, aEnglish, aToolTip, aTools.getTools(), aOreDictNames);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
        if (ITool.get(stack.getItemDamage()) != null) {
            return ITool.get(stack.getItemDamage()).startBreak(stack, x, y, z, player);
        }
        return super.onBlockStartBreak(stack, x, y, z, player);
    }

    public static class ProccessToolHead implements IOreRecipeRegistrator {
        public ProccessToolHead() {
            OrePrefixes.toolHeadHammer.add(this);
            OrePrefixes.toolHeadAxe.add(this);
        }

        @Override
        public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
            boolean aNoWorking = aMaterial.contains(SubTag.NO_WORKING);

            switch (aPrefix) {
                case toolHeadHammer:
                GT_ModHandler.addShapelessCraftingRecipe(GTMG_Tool_WorkRadius.INSTANCE.getToolWithStats(
                        GTMG_Tool_WorkRadius.FORGE_HAMMER, 1, aMaterial, aMaterial.mHandleMaterial, null),
                        new Object[]{
                                aOreDictName, OrePrefixes.stickLong.get(aMaterial.mHandleMaterial)
                        });
                break;
                case toolHeadAxe:
                    GT_ModHandler.addShapelessCraftingRecipe(GTMG_Tool_WorkRadius.INSTANCE.getToolWithStats(
                                    GTMG_Tool_WorkRadius.LUMBER_AXE, 1, aMaterial, aMaterial.mHandleMaterial, null),
                            new Object[]{
                                    aOreDictName, OrePrefixes.stickLong.get(aMaterial.mHandleMaterial)
                            });
                    break;
            }

        }
    }
}