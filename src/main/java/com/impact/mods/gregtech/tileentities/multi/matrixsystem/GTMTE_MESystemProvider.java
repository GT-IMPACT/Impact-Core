package com.impact.mods.gregtech.tileentities.multi.matrixsystem;


import com.impact.loader.ItemRegistery;
import com.impact.mods.gregtech.GT_RecipeMaps;
import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.gui.base.GTC_ImpactBase;
import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.util.multis.OverclockCalculate;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import java.util.ArrayList;
import java.util.List;

import static com.impact.mods.gregtech.blocks.Build_Casing_Helper.ME_CASING;
import static com.impact.util.vector.Structure.*;
import static gregtech.api.enums.GT_Values.V;
import static net.minecraft.util.EnumChatFormatting.*;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlock;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlockAnyMeta;

public class GTMTE_MESystemProvider extends GTMTE_Impact_BlockBase<GTMTE_MESystemProvider> {
	
	public static Block CASING = Casing_Helper.sCaseCore3;
	public static int CASING_META = ME_CASING.getMeta();
	private final List<GTMTE_Hatch_MESystemMPChamber> mpChambers = new ArrayList<>();
	public int mSpeedUp = 0;
	public int mMatrixParticlesSummary = 0;
	ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META + 32];
	int CASING_TEXTURE_ID = ME_CASING.getIDCasing();
	static IStructureDefinition<GTMTE_MESystemProvider> definition =
			StructureDefinition.<GTMTE_MESystemProvider>builder()
					.addShape("main", new String[][]{
							{"ADDDA ", "ADDDA ", "ADDDA ", "AAAAA "},
							{"ADDDA ", "A   AA", "A   A~", "AAAAAA"},
							{"ADDDA ", "A   AA", "A   AA", "AAAAAA"},
							{"ADDDA ", "ADDDA ", "ADDDA ", "AAAAA "}
					})
					.addElement('A', ofBlock(CASING, CASING_META))
					.addElement('B', ofBlock(ItemRegistery.MPSystem, 0))
					.addElement('D', ofBlockAnyMeta(ItemRegistery.IGlassBlock))
					.build();
	
	public GTMTE_MESystemProvider(int aID, String aNameRegional) {
		super(aID, "impact.multis.mesystemprovider", aNameRegional);
	}
	
	public GTMTE_MESystemProvider(String aName) {
		super(aName);
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_MESystemProvider(this.mName);
	}
	
	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		return aSide == aFacing ? new ITexture[]{INDEX_CASE, TextureFactory.of(aActive ? Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1)} : new ITexture[]{INDEX_CASE};
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName());
	}
	
	@Override
	public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GTC_ImpactBase(aPlayerInventory, aBaseMetaTileEntity);
	}
	
	@Override
	public IStructureDefinition<GTMTE_MESystemProvider> getStructureDefinition() {
		return definition;
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("me_system_provider");
		b
				.addInfo("info.0", "Consume Stable Matrix Particles")
				.addTypeMachine("name", "ME System Provider")
				.addInfo("info.1", "Accelerate ME GPU and Crafting ME Components")
				.addSeparator()
				.addController()
				.addEnergyHatch()
				.addMaintenanceHatch()
				.addCasingInfo("case", "ME Construction Casing")
				.addOtherStructurePart("other.0", "ME Connector", "other.1", "Any IGlass (max x1)")
				.addOtherStructurePart("other.2", "Matrix Particles Chamber", "other.3", "Any Casing (max x1)")
				.signAndFinalize();
		return b;
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 5, 2, 1);
	}
	
	@Override
	public GT_Recipe.GT_Recipe_Map getRecipeMap() {
		return GT_RecipeMaps.sMESystemProvider;
	}
	
	@Override
	public boolean checkRecipe(ItemStack aStack) {
		ArrayList<ItemStack> tInputList;
		ItemStack[] tInputs;

		tInputList = this.getStoredInputs();
		tInputs    = tInputList.toArray(new ItemStack[0]);
		
		if (tInputList.size() > 0) {
			long nominalV = getMaxInputVoltage();
			byte tTier = (byte) Math.max(1, GT_Utility.getTier(nominalV));
			GT_Recipe tRecipe = getRecipeMap().findRecipe(this.getBaseMetaTileEntity(), false, V[tTier], null, tInputs);
			if (tRecipe != null && (mMatrixParticlesSummary - tRecipe.mSpecialValue >= 0)) {
				ArrayList<ItemStack> outputItems = new ArrayList<>();
				boolean found_Recipe = false;
				int processed = 0;
				while ((this.getStoredFluids().size() | this.getStoredInputs().size()) > 0 && processed < 1) {
					if ((tRecipe.mEUt * (processed + 1L)) < nominalV && tRecipe.isRecipeInputEqual(true, null, tInputs)) {
						found_Recipe = true;
						
						for (int i = 0; i < tRecipe.mOutputs.length; i++) {
							outputItems.add(tRecipe.getOutput(i));
						}
						++processed;
					} else {
						break;
					}
				}
				if (found_Recipe) {
					this.mEfficiency         = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
					this.mEfficiencyIncrease = 10000;
					long actualEUT = (long) (tRecipe.mEUt) * processed;
					mMatrixParticlesSummary -= tRecipe.mSpecialValue * processed;
					
					OverclockCalculate.calculateOverclockedNessMulti((int) actualEUT, tRecipe.mDuration, 1, nominalV, this);
					
					this.mMaxProgresstime = this.mMaxProgresstime / this.mSpeedUp;
					if (this.mMaxProgresstime < 1) this.mMaxProgresstime = 1;
					if (this.mMaxProgresstime == Integer.MAX_VALUE - 1 && this.mEUt == Integer.MAX_VALUE - 1) return false;
					if (this.mEUt > 0) this.mEUt = (-this.mEUt);
					
					this.mOutputItems = outputItems.toArray(new ItemStack[0]);
					this.updateSlots();
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity iAm, long aTick) {
		super.onPostTick(iAm, aTick);
		if (iAm.isServerSide() && aTick % 8 == 0) {
			if (mpChambers.size() > 0) {
				GTMTE_Hatch_MESystemMPChamber ch = mpChambers.get(0);
				if (ch.getMPSummary() >= 1000) {
					if ((mMatrixParticlesSummary + 1000) <= 100_000) {
						mMatrixParticlesSummary += 1000;
						ch.subMPSummary(1000);
					}
				}
			}
		}
	}
	
	@Override
	public boolean machineStructure(IGregTechTileEntity iAm) {
		mpChambers.clear();
		mSpeedUp = 1;
		//region Structure
		final Vector3ic forgeDirection = new Vector3i(
				ForgeDirection.getOrientation(iAm.getBackFacing()).offsetX,
				ForgeDirection.getOrientation(iAm.getBackFacing()).offsetY,
				ForgeDirection.getOrientation(iAm.getBackFacing()).offsetZ
		);
		
		boolean formationChecklist = true;
		
		Vector3ic offset;
		IGregTechTileEntity currentTE;
		int x, y, z;
		
		for (z = -1; z <= 0; z++) {
			for (y = -1; y <= 1; y++) {
				
				if (z == 0 && y == 0) continue;
				
				offset    = rotateOffsetVector(forgeDirection, 0, y, z);
				currentTE = getIGTE(iAm, offset);
				
				if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !addMPChambers(currentTE, CASING_TEXTURE_ID)
						&& !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)) {
					if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
							&& (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
					} else {
						formationChecklist = false;
					}
				}
			}
		}
		for (x = -5; x <= -1; x++) {
			for (z = -2; z <= 1; z++) {
				for (y = -1; y <= 2; y++) {
					offset    = rotateOffsetVector(forgeDirection, x, y, z);
					currentTE = getIGTE(iAm, offset);
					if (x != -5 && x != -1 && y != -1) {
						if (z == -2 || z == 1 || y == 2) {
							if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == ItemRegistery.IGlassBlock)) {
								// :P
							} else {
								formationChecklist = false;
							}
						}
						continue;
					}
					
					if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !addMPChambers(currentTE, CASING_TEXTURE_ID)
							&& !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)) {
						if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
								&& (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
						} else {
							formationChecklist = false;
						}
					}
				}
			}
		}
		
		if (mpChambers.size() != 1) formationChecklist = false;
		if (!formationChecklist) mSpeedUp = 1;
		
		return formationChecklist;
	}
	
	public boolean addMPChambers(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
		if (aTileEntity == null) {
			return false;
		} else {
			final IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
			if (aMetaTileEntity == null) {
				return false;
			} else if (aMetaTileEntity instanceof GTMTE_Hatch_MESystemMPChamber) {
				((GTMTE_Hatch_MESystemMPChamber) aMetaTileEntity).updateTexture(aBaseCasingIndex);
				return mpChambers.add(((GTMTE_Hatch_MESystemMPChamber) aMetaTileEntity));
			} else {
				return false;
			}
		}
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setInteger("mMatrixParticlesSummary", mMatrixParticlesSummary);
		aNBT.setInteger("mSpeedUp", mSpeedUp);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		mMatrixParticlesSummary = aNBT.getInteger("mMatrixParticlesSummary");
		mSpeedUp                = aNBT.getInteger("mSpeedUp");
	}
	
	public String[] getInfoData() {
		return new String[]{
				"Usage Energy: " + RED + -mEUt + RESET + " EU/t",
				"Max Voltage: " + YELLOW + getMaxInputVoltage() + RESET + " EU/t ",
				"Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus()) ? GREEN + "Good " + YELLOW + mEfficiency / 100.0F + " %" + RESET : RED + "Has Problems " + mEfficiency / 100.0F + " %" + RESET),
		};
	}
	
	
	@Override
	public boolean hasSeparate() {
		return false;
	}
}