package com.impact.mods.gregtech.tileentities.multi.matrixsystem;

import appeng.tile.crafting.TileCraftingStorageTile;
import appeng.tile.crafting.TileCraftingTile;
import com.github.technus.tectech.mechanics.alignment.enumerable.ExtendedFacing;
import com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer;
import com.github.technus.tectech.mechanics.structure.IStructureDefinition;
import com.github.technus.tectech.mechanics.structure.StructureDefinition;
import com.impact.impact;
import com.impact.loader.ItemRegistery;
import com.impact.mods.gregtech.GT_RecipeMaps;
import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.gui.base.GT_Container_MultiParallelMachine;
import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.multis.OverclockCalculate;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

import static com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer.registerMetaClass;
import static com.github.technus.tectech.mechanics.structure.StructureUtility.ofBlock;
import static com.impact.mods.gregtech.blocks.Build_Casing_Helper.ME_CASING;
import static com.impact.util.vector.Structure.getIGTE;
import static com.impact.util.vector.Structure.getTE;
import static gregtech.api.enums.GT_Values.V;
import static net.minecraft.util.EnumChatFormatting.*;

public class GTMTE_MESystemProvider extends GT_MetaTileEntity_MultiParallelBlockBase {
	
	public static Block CASING = Casing_Helper.sCaseCore3;
	public static int CASING_META = ME_CASING.getMeta();
	private final List<GTMTE_AE_Connector> aeConnectors = new ArrayList<>();
	private final List<GTMTE_Hatch_MESystemMPChamber> mpChambers = new ArrayList<>();
	public List<TileCraftingTile> AE_CPU_UNIT = new ArrayList<>();
	public List<TileCraftingStorageTile> AE_CPU_CRAFT = new ArrayList<>();
	public int mSpeedUp = 0;
	public int mMatrixParticlesSummary = 0;
	ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META + 32];
	int CASING_TEXTURE_ID = ME_CASING.getIDCasing();
	
	//region Register
	public GTMTE_MESystemProvider(int aID, String aNameRegional) {
		super(aID, "impact.multis.mesystemprovider", aNameRegional);
		run();
	}
	
	public GTMTE_MESystemProvider(String aName) {
		super(aName);
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		run();
		return new GTMTE_MESystemProvider(this.mName);
	}
	//endregion
	
	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		return aSide == aFacing ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(aActive ? Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1)} : new ITexture[]{INDEX_CASE};
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName());
	}
	
	@Override
	public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GT_Container_MultiParallelMachine(aPlayerInventory, aBaseMetaTileEntity);
	}
	
	public void run() {
		// TODO: 31.10.2021
		impact.I_RA.addMESPRecipes(new ItemStack[]{GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockInterface", 1L, 0)},
				GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 440), 20 * 2, 2048, 5
		);
		
		registerMetaClass(GTMTE_MESystemProvider.class, new IMultiblockInfoContainer<GTMTE_MESystemProvider>() {
			//region Structure
			private final IStructureDefinition<GTMTE_MESystemProvider> definition =
					StructureDefinition.<GTMTE_MESystemProvider>builder()
							.addShape("main", new String[][]{
									{"ADDDA ", "ADDDA ", "ADDDA ", "AAAAA "},
									{"ADDDA ", "AFEFAA", "AFEFA~", "AAAAAA"},
									{"ADDDA ", "AFEFAA", "AFEFAA", "AAAAAA"},
									{"ADDDA ", "ADDDA ", "ADDDA ", "AAAAA "}
							})
							.addElement('A', ofBlock(CASING, CASING_META))
							.addElement('B', ofBlock(ItemRegistery.MPSystem, 0))
							.addElement('D', ofBlock(ItemRegistery.IGlassBlock))
							.addElement('E', ofBlock(Block.getBlockFromItem(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingStorage", 1L, 0).getItem()), 0))
							.addElement('F', ofBlock(Block.getBlockFromItem(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingUnit", 1L, 0).getItem()), 1))
							.build();
			private final String[] desc = new String[]{
					RED + "Impact Details:",
			};
			
			//endregion
			@Override
			public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_MESystemProvider tileEntity, ExtendedFacing aSide) {
				IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
				definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide, base.getXCoord(), base.getYCoord(), base.getZCoord(), 5, 2, 1, hintsOnly);
			}
			
			@Override
			public String[] getDescription(ItemStack stackSize) {
				return desc;
			}
		});
	}
	
	@Override
	public String[] getDescription() {
		final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("me_system_provider");
		b
				.addTypeMachine("name", "ME System Provider")
				.addSeparator()
				.addController()
				.addEnergyHatch()
				.addMaintenanceHatch()
				.addCasingInfo("case", "ME Construction Casing")
				.signAndFinalize();
		if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			return b.getInformation();
		} else {
			return b.getStructureInformation();
		}
	}
	
	@Override
	public GT_Recipe.GT_Recipe_Map getRecipeMap() {
		return GT_RecipeMaps.sMESystemProvider;
	}
	
	@Override
	public boolean checkRecipe(ItemStack aStack) {
		ArrayList<ItemStack> tInputList;
		ItemStack[] tInputs;
		
		if (!checkAE()) {
			return false;
		}
		
		tInputList = this.getStoredInputs();
		tInputs    = tInputList.toArray(new ItemStack[0]);
		
		if (tInputList.size() > 0) {
			long nominalV = getMaxInputVoltage();
			byte tTier = (byte) Math.max(1, GT_Utility.getTier(nominalV));
			GT_Recipe tRecipe;
			tRecipe = getRecipeMap().findRecipe(this.getBaseMetaTileEntity(), false, V[tTier], null, tInputs);
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
		if (iAm.isServerSide() && aTick % 40 == 0) {
			
			if (aeConnectors.size() > 0) {
				int checker = 0;
				for (TileCraftingStorageTile te : AE_CPU_CRAFT) {
					if (te != null && te.isPowered()) checker++;
				}
				aeConnectors.get(0).getBaseMetaTileEntity().setActive(checker == 4);
			}
			
			if (mpChambers.size() > 0) {
				GTMTE_Hatch_MESystemMPChamber ch = mpChambers.get(0);
				if (ch.getMPSummary() >= 1000) {
					if ((mMatrixParticlesSummary + 1000) <= 99_900) {
						mMatrixParticlesSummary += 1000;
						ch.subMPSummary(1000);
					}
				}
			}
		}
	}
	
	@Override
	public boolean machineStructure(IGregTechTileEntity iAm) {
		aeConnectors.clear();
		mpChambers.clear();
		AE_CPU_UNIT.clear();
		AE_CPU_CRAFT.clear();
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
						//setBlock(iAm, offset, CASING, CASING_META);
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
						if (z != -2 && z != 1 && y != 2) {
							if (x == -3) {
								TileEntity aeCPU = getTE(iAm, offset);
								if (aeCPU instanceof TileCraftingStorageTile) {
									TileCraftingStorageTile craft = (TileCraftingStorageTile) aeCPU;
									AE_CPU_CRAFT.add(craft);
									int bytes = craft.getStorageBytes() / 1024;
									int preSpeedUp = bytes == 64 ? 4 : bytes == 16 ? 3 : bytes == 4 ? 2 : 1;
									if (mSpeedUp == 1) {
										mSpeedUp = preSpeedUp;
									} else if (mSpeedUp > preSpeedUp) {
										mSpeedUp = preSpeedUp;
									}
								} else {
									formationChecklist = false;
								}
								continue;
							} else {
								TileEntity aeCPU = getTE(iAm, offset);
								if (aeCPU instanceof TileCraftingTile) {
									AE_CPU_UNIT.add((TileCraftingTile) aeCPU);
								} else {
									formationChecklist = false;
								}
								continue;
							}
						} else {
							if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == ItemRegistery.IGlassBlock)) {
							} else if (!addAEConnectors(currentTE, CASING_TEXTURE_ID)) {
								formationChecklist = false;
							}
							continue;
						}
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
		
		if (AE_CPU_UNIT.size() != 8) formationChecklist = false;
		if (AE_CPU_CRAFT.size() != 4) formationChecklist = false;
		if (aeConnectors.size() != 1) formationChecklist = false;
		if (mpChambers.size() != 1) formationChecklist = false;
		
		for (TileCraftingTile te : AE_CPU_UNIT) {
			try {
				te.updateStatus(null);
				te.setBigAccelerator(formationChecklist, mSpeedUp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (!formationChecklist) mSpeedUp = 1;
		
		return formationChecklist;
	}
	
	public boolean checkAE() {
		int checker = 0;
		for (TileCraftingTile te : AE_CPU_CRAFT) {
			if (te.isPowered()) checker++;
		}
		return checker == 4;
	}
	
	public boolean addAEConnectors(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
		if (aTileEntity == null) {
			return false;
		} else {
			final IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
			if (aMetaTileEntity == null) {
				return false;
			} else if (aMetaTileEntity instanceof GTMTE_AE_Connector) {
				((GTMTE_AE_Connector) aMetaTileEntity).updateTexture(aBaseCasingIndex);
				return aeConnectors.add(((GTMTE_AE_Connector) aMetaTileEntity));
			} else {
				return false;
			}
		}
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
}