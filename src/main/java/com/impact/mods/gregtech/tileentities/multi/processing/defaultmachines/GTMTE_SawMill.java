package com.impact.mods.gregtech.tileentities.multi.processing.defaultmachines;

import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
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
import org.lwjgl.input.Keyboard;

import static com.impact.loader.ItemRegistery.SawMillBlock;
import static com.impact.mods.gregtech.enums.Texture.Icons.*;

public class GTMTE_SawMill extends GT_MetaTileEntity_MultiParallelBlockBase {
	
	public static String mModed;
	Block CASING = Casing_Helper.sCaseCore2;
	byte CASING_META = 9;
	ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][16 + CASING_META];
	int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;
	
	public GTMTE_SawMill(int aID, String aNameRegional) {
		super(aID, "impact.multimachine.sawmill", aNameRegional);
	}
	
	public GTMTE_SawMill(String aName) {
		super(aName);
	}
	
	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		return aSide == aBaseMetaTileEntity.getBackFacing() ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(aActive ? SAW_ACTIVE : SAW)} : aSide == aFacing ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(aActive ? SAW_FRONT_ACTIVE : SAW_FRONT)} : new ITexture[]{INDEX_CASE};
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_SawMill(this.mName);
	}
	
	@Override
	public String[] getDescription() {
		final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("saw_mill");
		b
				.addInfo("info.1", "Nooo! Do not saw me..")
				.addTypeMachine("name", "Saw Mill")
				.addSeparator()
				.addController()
				.addEnergyHatch()
				.addInputBus(1)
				.addOutputBus(1)
				.addInputHatch(1)
				.addCasingInfo("case", "Wooden Casing")
				.addOtherStructurePart("other.0", "Saw Mill Conveyor", "other.1", "Bottom middle")
				.signAndFinalize();
		if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			return b.getInformation();
		} else {
			return b.getStructureInformation();
		}
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png", mModed);
		
	}
	
	@Override
	public boolean checkRecipe(ItemStack itemStack) {
		this.mWrench        = true;
		this.mScrewdriver   = true;
		this.mSoftHammer    = true;
		this.mHardHammer    = true;
		this.mSolderingTool = true;
		this.mCrowbar       = true;
		return impactRecipe();
	}
	
	
	@Override
	public boolean machineStructure(IGregTechTileEntity thisController) {
		final Vector3ic forgeDirection = new Vector3i(
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ
		);
		
		boolean formationChecklist = true; // Если все ок, машина собралась
		
		for (byte X = 0; X <= 2; X++) {
			for (byte Y = -1; Y <= 1; Y++) {
				for (byte Z = 0; Z >= -4; Z--) {
					
					if (X == 0 && Z == 0 && Y == 0) {
						continue;
					}
					
					if (X == 1 && Y == 0) {
						continue;
					}
					if (X == 0 && Y == 0 && !(Z == -4)) {
						continue;
					}
					if (X == 2 && Y == 0 && !(Z == 0 || Z == -4)) {
						continue;
					}
					if (X == 1 && Y == 1 && !(Z == -1 || Z == -3)) {
						continue;
					}
					if ((X == 0 || X == 2) && Y == -1 && !(Z == -0 || Z == -4)) {
						continue;
					}
					
					final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);
					
					if (X == 1 && Y == -1) {
						if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == SawMillBlock)) {
						} else {
							formationChecklist = false;
						}
						continue;
					}
					
					IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
					if (!super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {
						if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
								&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
						} else {
							formationChecklist = false;
						}
					}
				}
			}
		}
		
		if (this.mInputBusses.size() > 1) {
			formationChecklist = false;
		}
		if (this.mInputHatches.size() > 1) {
			formationChecklist = false;
		}
		if (this.mOutputBusses.size() > 2) {
			formationChecklist = false;
		}
		if (this.mEnergyHatches.size() != 1) {
			formationChecklist = false;
		}
		mWrench        = true;
		mScrewdriver   = true;
		mSoftHammer    = true;
		mHardHammer    = true;
		mSolderingTool = true;
		mCrowbar       = true;
		return formationChecklist;
	}
	
	@Override
	public int getPollutionPerTick(ItemStack aStack) {
		return 0;
	}
	
	@Override
	public GT_Recipe.GT_Recipe_Map getRecipeMap() {
		return mMode == 0 ? GT_Recipe.GT_Recipe_Map.sSawMill0 : mMode == 1 ? GT_Recipe.GT_Recipe_Map.sSawMill1 : GT_Recipe.GT_Recipe_Map.sSawMill2;
	}
	
	@Override
	public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
		
		mMode++;
		if (mMode > 2) {
			mMode = 0;
		}
		
		mModed = (mMode == 0 ? " Planks & Sawdust " : mMode == 1 ? " Wood Pulp & Sawdust " : " Only Sawdust ");
		GT_Utility.sendChatToPlayer(aPlayer, "Mode:" + EnumChatFormatting.GREEN + mModed);
	}
}