package com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines;

import com.impact.common.te.TE_TheMill;
import com.impact.impact;
import com.impact.loader.ItemRegistery;
import com.impact.mods.gregtech.GT_RecipeMaps;
import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.enums.Texture;
import com.impact.mods.gregtech.gui.base.GT_GUIContainerMT_Machine;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.Utilits;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Structure;
import com.impact.util.vector.Vector3ic;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.gui.GT_Slot_Output;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import java.awt.*;

import static com.impact.common.item.Core_List_Items.*;
import static gregtech.api.enums.GT_Values.RES_PATH_GUI;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlock;

public class GTMTE_TheMill extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_TheMill> {
	
	
	static Block CASING = Casing_Helper.sCaseCore3;
	static int CASING_META = 2;
	static ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META + 32];
	static int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;
	
	public GTMTE_TheMill(int aID, String aNameRegional) {
		super(aID, "impact.multis.the_mill", aNameRegional, 5);
		impact.I_RA.addTheMillRecipes(DropCrashedStone.get(1),
				new ItemStack[]{
						Utilits.is(OrePrefixes.dustImpure, Materials.Calcite),
						Utilits.is(OrePrefixes.dustImpure, Materials.Gypsum)
				},
				new int[]{10000, 10000}, 20 * 30
		);
		impact.I_RA.addTheMillRecipes(DropCrashedMetallic.get(1),
				new ItemStack[]{
						Utilits.is(OrePrefixes.dustImpure, Materials.Iron),
						Utilits.is(OrePrefixes.dustImpure, Materials.Copper),
						Utilits.is(OrePrefixes.dustImpure, Materials.Tin),
						Utilits.is(OrePrefixes.dustImpure, Materials.Lead)
				},
				new int[]{10000, 10000, 10000, 10000}, 20 * 30
		);
		impact.I_RA.addTheMillRecipes(DropCrashedCoal.get(1),
				new ItemStack[]{
						Utilits.is(OrePrefixes.dustImpure, Materials.Coal),
						Utilits.is(OrePrefixes.dustImpure, Materials.Lignite),
						Utilits.is(OrePrefixes.dustImpure, Materials.Sulfur)
				},
				new int[]{10000, 10000, 10000}, 20 * 30)
		;
	}
	
	public GTMTE_TheMill(String aName) {
		super(aName, 5);
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		return null;
	}
	
	@Override
	public IStructureDefinition<GTMTE_TheMill> getStructureDefinition() {
		return StructureDefinition.<GTMTE_TheMill>builder()
				.addShape("main", new String[][]{
						{"         ", "         ", "   CCC   ", "   C~C   ", "   CCC   ", "         ", "         ", "         ", "   CCC   ", "  CCCCC  ", "  CC CC  ", "  CC CC  "},
						{"         ", "   CCC   ", "  C   C  ", "  C   C  ", "  C   C  ", "   CCC   ", "   CCC   ", "   CCC   ", " CC   CC ", " C     C ", " C     C ", " C     C "},
						{"   CCC   ", "  C   C  ", " C     C ", " C     C ", " C     C ", "  CCCCC  ", "  C   C  ", "  C   C  ", " C     C ", "C       C", "C       C", "C       C"},
						{"  CCCCC  ", " C     C ", "C       C", "C       C", "C       C", " CCCCCCC ", " C     C ", " C     C ", "C       C", "C       C", "C       C", "C       C"},
						{"  CCCCC  ", " C     C ", "C       C", "C       C", "C       C", " CCCCCCC ", " C     C ", " C     C ", "C       C", "C       C", "C       C", "C       C"},
						{"  CCCCC  ", " C     C ", "C       C", "C       C", "C       C", " CCCCCCC ", " C     C ", " C     C ", "C       C", "C       C", "C       C", "C       C"},
						{"   CCC   ", "  C   C  ", " C     C ", " C     C ", " C     C ", "  CC CC  ", "  C   C  ", "  C   C  ", " C     C ", "C       C", "C       C", "C       C"},
						{"         ", "   CCC   ", "  C   C  ", "  C   C  ", "  C   C  ", "   CCC   ", "   CCC   ", "   CCC   ", " CC C CC ", " C  C  C ", " C  C  C ", " C  C  C "},
						{"         ", "         ", "   CCC   ", "   CCC   ", "   CCC   ", "         ", "         ", "         ", "   CCC   ", "  CCCCC  ", "  CCCCC  ", "  CCCCC  "}}
				)
				.addElement('C', ofBlock(CASING, CASING_META))
				.build();
	}
	
	public boolean isFacingValid(byte aFacing) {
		return aFacing > 1;
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_TheMill(mName);
	}
	
	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		return aSide == aFacing ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(Texture.Icons.OVERLAY_PIPELINE_FLUID_FRONT)} :
				aSide == GT_Utility.getOppositeSide(aFacing) ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PIPE_OUT)} :
						new ITexture[]{INDEX_CASE};
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
		if (iAm.isActive() && (aTick & 0x7) == 0) {
			
			IInventory tTileEntity = iAm.getIInventoryAtSide(iAm.getBackFacing());
			if (tTileEntity != null) {
				if (mInventory[1] != null) {
					GT_Utility.moveOneItemStack(iAm, tTileEntity, (byte) 1, (byte) 1, null, false, (byte) 64, (byte) 1, (byte) 64, (byte) 1);
				}
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
					this.mInventory[absi].stackSize = Math.min(this.mInventory[absi].getMaxStackSize(), this.mInventory[absi].stackSize + this.mOutputItems[i].stackSize);
				}
			}
		}
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
	
	@Override
	public boolean checkRecipe(ItemStack itemStack) {
		if (mInventory[0] != null) {
			ItemStack[] slot0 = {mInventory[0]};
			GT_Recipe tRecipe = getRecipeMap().findRecipe(getBaseMetaTileEntity(), cashedRecipe, false, false, 0, null, slot0);
			if (tRecipe != null) {
				mInventory[0].stackSize--;
				if (mInventory[0].stackSize <= 0) {
					mInventory[0] = null;
				}
				cashedRecipe = tRecipe;
				mEfficiency = mEfficiencyIncrease = 10000;
				mMaxProgresstime = tRecipe.mDuration / 4;
				mOutputItems = tRecipe.mOutputs;
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean machineStructure(IGregTechTileEntity thisController) {
		this.noMaintenance();
		boolean check = checkPiece(4, 3, 0);
		for (int x = -6; x < 6; x++) {
			for (int y = -6; y < 6; y++) {
				Vector3ic vec = Structure.goBuild(thisController, x, y, 1);
				if (x == 0 && y == 0) {
					continue;
				}
				if (Structure.getBlock(thisController, vec) != Blocks.air) {
					check = false;
				}
			}
		}
		Vector3ic vec = Structure.goBuild(thisController, 0, 0, 1);
		if (check) {
			if (thisController.isServerSide()) {
				Structure.setBlock(thisController, vec, ItemRegistery.TheWind, 0);
				TileEntity te = Structure.getTE(thisController, vec);
				if (te instanceof TE_TheMill) {
					TE_TheMill te_theMill = (TE_TheMill) te;
					te_theMill.facing = thisController.getFrontFacing();
				}
			}
		} else {
			Structure.setBlock(thisController, vec, Blocks.air, 0);
		}
		return check;
	}
	
	@Override
	public void inValidate() {
		Vector3ic vec = Structure.goBuild(getBaseMetaTileEntity(), 0, 0, 1);
		Structure.setBlock(getBaseMetaTileEntity(), vec, Blocks.air, 0);
		super.inValidate();
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 4, 3, 0);
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