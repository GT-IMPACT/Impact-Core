package com.impact.mods.gregtech.tileentities.basic;

import com.impact.impact;
import com.impact.recipe.gui.RecipeContainer;
import com.impact.recipe.gui.RecipeContainerCrafting;
import com.impact.recipe.gui.RecipeGuiContainer;
import com.impact.recipe.gui.RecipeGuiContainerCrafting;
import com.impact.recipe.json.RecipeJson;
import com.impact.recipe.json.RecipeJsonCrafing;
import com.impact.recipe.maps.RecipesJson;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicTank;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class GTMTE_RecipeEditorCrafting extends GT_MetaTileEntity_BasicTank {
	
	public GTMTE_RecipeEditorCrafting(int aID, String aNameRegional, int aTier) {
		super(aID, "recipe_editor_crafting", aNameRegional, aTier, 10, new String[] {
				"   In GUI:",
				"- Inputs/Outputs: MIDDLE CLICK - enabled/disabled ore dictionary",
		});
	}
	
	public GTMTE_RecipeEditorCrafting(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
		super(aName, aTier, 10, aDescription, aTextures);
	}
	
	public GTMTE_RecipeEditorCrafting(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
		super(aName, aTier, 10, aDescription, aTextures);
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new RecipeGuiContainerCrafting(aPlayerInventory, aBaseMetaTileEntity, "");
	}
	
	@Override
	public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new RecipeContainerCrafting(aPlayerInventory, aBaseMetaTileEntity);
	}
	
	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_RecipeEditorCrafting(mName, mTier, mDescriptionArray, mTextures);
	}
	
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
	}
	
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
	}
	
	
	
	public void save() {
		try {
			worktableRecipe();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void worktableRecipe() {
		
		List<ItemStack> inputI = new ArrayList<>();
		ItemStack outputI = mInventory[9];
		int check = 0;
		for (int i = 0; i < mInventory.length; i++) {
			if (i <= 8) {
				if (mInventory[i] != null) {
					mInventory[i].stackSize = 1;
					check++;
				}
				inputI.add(mInventory[i]);
			}
		}
		
		if (check == 0)  {
			impact.proxy.addClientSideChatMessages("Input not found!");
			return;
		}
		
		if (inputI.isEmpty()) {
			impact.proxy.addClientSideChatMessages("Input not found!");
			return;
		}
		
		if (outputI == null) {
			impact.proxy.addClientSideChatMessages("Output not found!");
			return;
		} else {
			outputI.stackSize = 1;
		}
		
		ItemStack[] in = inputI.toArray(new ItemStack[0]);
		
		RecipeJsonCrafing recipe = new RecipeJsonCrafing(in, outputI);
	
		
		final char[] slot = new char[9];
		List<Character> chars = new ArrayList<>();
		List<ItemStack> stacks = new ArrayList<>();
		for (int i = 0; i < recipe.inputItem.length; i++) {
			slot[i] = recipe.inputItem[i] != null ? Integer.toString(i).toCharArray()[0] : ' ';
			if (recipe.inputItem[i] != null) {
				chars.add(slot[i]);
				stacks.add(recipe.inputItem[i]);
			}
		}
		
		String first = slot[0] + "" + slot[1] + "" + (recipe.inputItem[2] == null ? "" : slot[2]);
		String second = slot[3] + "" + slot[4] + "" + (recipe.inputItem[5] == null ? "" : slot[5]);
		String third = (recipe.inputItem[6] == null ? "" : slot[6]) + "" +
						(recipe.inputItem[7] == null ? "" : slot[7]) + "" +
						(recipe.inputItem[8] == null ? "" : slot[8]);
		
		int size = 2 + (third.isEmpty() ? 0 : 1);
		
		Object[] shapeRecipe = new Object[size + chars.size() + stacks.size()];
		
		shapeRecipe[0] = first;
		shapeRecipe[1] = second;
		if (size > 2) {
			shapeRecipe[2] = third;
		}
		
		int indexSlot = 0;
		for (int i = size; i < shapeRecipe.length; i += 2) {
			shapeRecipe[i + 1] = stacks.get(indexSlot);
			shapeRecipe[i] = chars.get(indexSlot++);
		}
		GT_ModHandler.addCraftingRecipe(recipe.outputItem, shapeRecipe);
		RecipesJson.preSaveCrafting(recipe);
		impact.proxy.addClientSideChatMessages("Recipe Added");
	}

	
	@Override
	public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing,
								 byte aColorIndex, boolean aActive, boolean aRedstone) {
		return aSide != aFacing
				? aSide == 1
				? new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1],
				new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_STANK)}
				: new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1]}
				: aActive
				? getTexturesActive(Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1])
				: getTexturesInactive(Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1]);
	}
	
	public ITexture[] getTexturesActive(ITexture aBaseTexture) {
		return new ITexture[]{aBaseTexture,
				new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PIPE_OUT)};
	}
	
	public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
		return new ITexture[]{aBaseTexture,
				new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PIPE_OUT)};
	}
	
	public String[] getDescription() {
		return this.mDescriptionArray;
	}
	
	@Override
	public ITexture[][][] getTextureSet(ITexture[] iTextures) {
		return new ITexture[0][][];
	}
	
	public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
		return true;
	}
	
	public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
		return true;
	}
	
	public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
		if (aBaseMetaTileEntity.isClientSide()) {
			return true;
		} else {
			aBaseMetaTileEntity.openGUI(aPlayer);
			return true;
		}
	}
	
	public boolean isSimpleMachine() {
		return true;
	}
	
	public boolean isFacingValid(byte aFacing) {
		return true;
	}
	
	public boolean isAccessAllowed(EntityPlayer aPlayer) {
		return true;
	}
	
	public final byte getUpdateData() {
		return 0;
	}
	
	public boolean doesFillContainers() {
		return false;
	}
	
	public boolean doesEmptyContainers() {
		return false;
	}
	
	public boolean canTankBeFilled() {
		return false;
	}
	
	public boolean canTankBeEmptied() {
		return false;
	}
	
	public boolean displaysItemStack() {
		return false;
	}
	
	public boolean displaysStackSize() {
		return false;
	}
	
	@Override
	public int getCapacity() {
		return 10000000;
	}
}
