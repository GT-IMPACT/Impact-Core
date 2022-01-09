package com.impact.mods.gregtech.items.tools;

import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.SubTag;
import gregtech.api.interfaces.IOreRecipeRegistrator;
import gregtech.api.items.GT_MetaGenerated_Tool;
import gregtech.api.util.GT_ModHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GTMG_Tool_WorkRadius extends GT_MetaGenerated_Tool {

    public static GTMG_Tool_WorkRadius INSTANCE;
    public static Map<Integer, IImpact_Tools> ITool;

    public static final int FORGE_HAMMER = 0;
    public static final int LUMBER_AXE = 2;

    public GTMG_Tool_WorkRadius() {
        super("impact.metatool.01");
        INSTANCE = this;
        ITool = new LinkedHashMap<>();
        addTool(FORGE_HAMMER, "Forge Hammer", "", (IImpact_Tools) new ForgeHammer(1, 0));
        addTool(LUMBER_AXE, "Lumber Axe", "", (IImpact_Tools) new LumberAxe(1, 0));
        
        GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(FORGE_HAMMER, 1, Materials.Flint, Materials.Wood, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"FFF", "FFF", "TS ", 'S', OrePrefixes.stick.get(Materials.Wood), 'F', new ItemStack(Items.flint, 1), 'T', new ItemStack(Items.string, 1)});
        GT_ModHandler.addCraftingRecipe(INSTANCE.getToolWithStats(LUMBER_AXE, 1, Materials.Flint, Materials.Wood, null), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"FFT", "FS ", "S  ", 'S', OrePrefixes.stick.get(Materials.Wood), 'F', new ItemStack(Items.flint, 1), 'T', new ItemStack(Items.string, 1)});
    }

    public ItemStack addTool(int aID, String aEnglish, String aToolTip, IImpact_Tools aTools, Object... aOreDictNames) {
        ITool.put(aID, aTools);
        ITool.put(aID + 1, aTools);
        return addTool(aID, aEnglish, aToolTip, aTools.getTools(), aOreDictNames);
    }
    
    @Override
    public void addAdditionalToolTips(List aList, ItemStack aStack, EntityPlayer aPlayer) {
        super.addAdditionalToolTips(aList, aStack, aPlayer);
        ITool.get(aStack.getItemDamage()).addAdditionalToolTips(aList, aStack, aPlayer);
    }
    
    @Override
    public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
        if (ITool.get(stack.getItemDamage()) != null) {
            return ITool.get(stack.getItemDamage()).startBreak(stack, x, y, z, player);
        }
        return super.onBlockStartBreak(stack, x, y, z, player);
    }
    
    public boolean canAdDrop(ItemStack stack) {
        return ITool.get(stack.getItemDamage()).canAdDrop(stack);
    }
    
    @Override
    public boolean onItemUseFirst(ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ) {
        return ITool.get(aStack.getItemDamage()).onItemUseFirst(aStack, aPlayer, aWorld, aX, aY, aZ, aSide, hitX, hitY, hitZ);
    }
    
    @Override
    public boolean onItemUse(ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int aSide, float hitX, float hitY, float hitZ) {
        return ITool.get(aStack.getItemDamage()).onItemUse(aStack, aPlayer, aWorld, aX, aY, aZ, aSide, hitX, hitY, hitZ);
    }
    
   
    public static class ProccessToolHead implements IOreRecipeRegistrator {
        public ProccessToolHead() {
            OrePrefixes.toolHeadHammer.add(this);
            OrePrefixes.toolHeadAxe.add(this);
        }
    
        @Override
        public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
            switch (aPrefix) {
                case toolHeadHammer:
                    GT_ModHandler.addCraftingRecipe(
                            GTMG_Tool_WorkRadius.INSTANCE.getToolWithStats(GTMG_Tool_WorkRadius.FORGE_HAMMER, 1, aMaterial, aMaterial.mHandleMaterial, null),
                            new Object[]{"XSX", "XSX", "hSf", 'X', OrePrefixes.plateTriple.get(aMaterial), 'S', OrePrefixes.stickLong.get(aMaterial.mHandleMaterial)});
                    break;
                case toolHeadAxe:
                    GT_ModHandler.addCraftingRecipe(
                            GTMG_Tool_WorkRadius.INSTANCE.getToolWithStats(GTMG_Tool_WorkRadius.LUMBER_AXE, 1, aMaterial, aMaterial.mHandleMaterial, null),
                            new Object[]{"XXf", "XS ", "hS ", 'X', OrePrefixes.plateTriple.get(aMaterial), 'S', OrePrefixes.stickLong.get(aMaterial.mHandleMaterial)});
                    break;
            }
        }
    }
}