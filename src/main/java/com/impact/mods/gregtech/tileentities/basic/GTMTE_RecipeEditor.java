package com.impact.mods.gregtech.tileentities.basic;

import com.impact.impact;
import com.impact.network.ToClient_String;
import com.impact.recipe.gui.RecipeContainer;
import com.impact.recipe.gui.RecipeGuiContainer;
import com.impact.recipe.json.RecipeJson;
import com.impact.recipe.maps.RecipesJson;
import com.impact.util.Utilits;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicTank;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_TieredMachineBlock;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

import static com.impact.recipe.maps.RecipesJson.recipeTest;

public class GTMTE_RecipeEditor extends GT_MetaTileEntity_BasicTank {
	
	public String nameGui = "";
	public GT_Recipe.GT_Recipe_Map map = null;
	public int voltage, time, special;
	public int[] chance = new int[8];
	public boolean chanceEnabled;
	
	public GTMTE_RecipeEditor(int aID, String aNameRegional, int aTier) {
		super(aID, "allo_nahui", aNameRegional, aTier, 50, new String[] {
				"   In GUI:",
				"- Inputs items: SHIFT + R/L CLICK +/- stacksize",
				"- Inputs/Outputs: MIDDLE CLICK - enabled/disabled ore dictionary",
				"- Outputs slots: SHIFT + R/L CLICK +/- chance",
		});
	}
	
	public GTMTE_RecipeEditor(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
		super(aName, aTier, 50, aDescription, aTextures);
	}
	
	public GTMTE_RecipeEditor(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
		super(aName, aTier, 50, aDescription, aTextures);
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new RecipeGuiContainer(aPlayerInventory, aBaseMetaTileEntity, "");
	}
	
	@Override
	public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new RecipeContainer(aPlayerInventory, aBaseMetaTileEntity);
	}
	
	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		
		return new GTMTE_RecipeEditor(mName, mTier, mDescriptionArray, mTextures);
	}
	
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
	}
	
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
	}
	
	public void onPostTick(IGregTechTileEntity igt, long aTick) {
		super.onPostTick(igt, aTick);

		
	}
	
	@Override
	public void onFirstTick(IGregTechTileEntity igt) {
		super.onFirstTick(igt);
		nameGui = "";
		IGregTechTileEntity te = igt.getIGregTechTileEntityAtSide(igt.getBackFacing());
		IMetaTileEntity meta = te.getMetaTileEntity();
		if (meta instanceof GT_MetaTileEntity_MultiBlockBase) {
			GT_MetaTileEntity_MultiBlockBase multi = (GT_MetaTileEntity_MultiBlockBase) meta;
			map = multi.getRecipeMap();
			nameGui = multi.getInventoryName();
		} else if (meta instanceof GT_MetaTileEntity_BasicMachine) {
			GT_MetaTileEntity_BasicMachine single = (GT_MetaTileEntity_BasicMachine) meta;
			map = single.getRecipeList();
			nameGui = single.getInventoryName();
		}
	}
	
	public void save() {
		try {
			if (map == null) {
				impact.proxy.addClientSideChatMessages("Error! Please add recipe, map!");
				return;
			}
			
			List<ItemStack> inputI = new ArrayList<>();
			List<ItemStack> outputI = new ArrayList<>();
			List<FluidStack> inputF = new ArrayList<>();
			List<FluidStack> outputF = new ArrayList<>();
			int chanceID = 0;
			
			for (int i = 0; i < mInventory.length; i++) {
				if (mInventory[i] != null) {
					if (i < 16) {
						inputI.add(mInventory[i]);
					} else if (i < 24) {
						outputI.add(mInventory[i]);
						chanceID++;
					} else if (i < 32) {
						FluidStack fs = GT_Utility.getFluidFromDisplayStack(mInventory[i]);
						if (fs != null) {
							inputF.add(fs);
						}
					} else if (i < 48) {
						FluidStack fs = GT_Utility.getFluidFromDisplayStack(mInventory[i]);
						if (fs != null) {
							outputF.add(fs);
						}
					}
				}
			}
			
			ItemStack[] in = inputI.toArray(new ItemStack[0]);
			ItemStack[] out = outputI.toArray(new ItemStack[0]);
			FluidStack[] intF = inputF.toArray(new FluidStack[0]);
			FluidStack[] outF = outputF.toArray(new FluidStack[0]);
			
			if (time == 0 || voltage == 0) {
				if (getBaseMetaTileEntity().isServerSide()) {
					impact.proxy.addClientSideChatMessages("Error Recipe! Check Time or Voltage");
				}
				return;
			}
			int[] newChance = new int[out.length];
			if (chance.length >= map.mUsualOutputCount) {
				for (int reChance = 0; reChance < out.length; reChance++) {
					newChance[reChance] = chance[reChance];
				}
			}
			
			if (map.mUsualInputCount < in.length || map.mUsualOutputCount < out.length) {
				if (getBaseMetaTileEntity().isServerSide()) {
					impact.proxy.addClientSideChatMessages("Error Recipe! Check Inputs or Outputs (Maximum Inputs/Outputs)");
				}
				return;
			}
			
			if (map.mMinimalInputFluids > intF.length || map.mMinimalInputItems > in.length) {
				if (getBaseMetaTileEntity().isServerSide()) {
					impact.proxy.addClientSideChatMessages("Error Recipe! Check Inputs (Minimum Inputs)");
				}
				return;
			}
			
			GT_Recipe recipe = map.addRecipe(false, in, out, chanceEnabled ? newChance : null, intF, outF, time, voltage, special);
			
			if (recipe == null) {
				if (getBaseMetaTileEntity().isServerSide()) {
					impact.proxy.addClientSideChatMessages("Error Recipe!");
				}
				return;
			}
			
			RecipeJson recipeJson = new RecipeJson(false, in, out, chanceEnabled ? newChance : null, intF, outF, time, voltage, special);
			RecipesJson.preSave(map.mUnlocalizedName, recipeJson);
			if (getBaseMetaTileEntity().isServerSide()) {
				impact.proxy.addClientSideChatMessages("Recipe Added");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onRightclick(IGregTechTileEntity te, EntityPlayer aPlayer, byte aSide, float aX, float aY, float aZ) {
//		if (te.isServerSide()) {
//			if (map != null) {
//				new ToClient_String(nameGui, map.mNEIName).sendToClients();
//			}
//		}
		return super.onRightclick(te, aPlayer, aSide, aX, aY, aZ);
	}
	
	@Override
	public boolean isLiquidInput(byte aSide) {
		return aSide != getBaseMetaTileEntity().getFrontFacing();
	}
	
	@Override
	public boolean isLiquidOutput(byte aSide) {
		return aSide == getBaseMetaTileEntity().getFrontFacing();
	}
	
	@Override
	public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing,
								 byte aColorIndex, boolean aActive, boolean aRedstone) {
		return aSide != aFacing
				? aSide == 1
				? new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1],
				TextureFactory.of(Textures.BlockIcons.OVERLAY_STANK)}
				: new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1]}
				: aActive
				? getTexturesActive(Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1])
				: getTexturesInactive(Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1]);
	}
	
	public ITexture[] getTexturesActive(ITexture aBaseTexture) {
		return new ITexture[]{aBaseTexture,
				TextureFactory.of(Textures.BlockIcons.OVERLAY_PIPE_OUT)};
	}
	
	public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
		return new ITexture[]{aBaseTexture,
				TextureFactory.of(Textures.BlockIcons.OVERLAY_PIPE_OUT)};
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
