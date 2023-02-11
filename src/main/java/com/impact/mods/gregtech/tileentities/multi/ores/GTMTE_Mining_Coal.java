package com.impact.mods.gregtech.tileentities.multi.ores;

import com.impact.common.oregeneration.OreGenerator;
import com.impact.common.oregeneration.OreVein;
import com.impact.common.oregeneration.generator.OreChunkGenerator;
import com.impact.core.Config;
import com.impact.mods.gregtech.gui.base.GT_GUIContainerMT_Machine;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.mods.gregtech.tileentities.multi.ores.hatches.GTMTE_OreHatch;
import com.impact.util.string.MultiBlockTooltipBuilder;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.gui.GT_Slot_Output;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.objects.XSTR;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.chunk.Chunk;
import space.impact.api.ImpactAPI;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.impact.util.multis.GT_StructureUtility.ofFrame;
import static com.impact.util.multis.GT_StructureUtility.ofHatchAdder;
import static gregtech.api.enums.GT_Values.RES_PATH_GUI;
import static space.impact.api.multiblocks.structure.StructureUtility.lazy;

public class GTMTE_Mining_Coal extends GTMTE_Impact_BlockBase<GTMTE_Mining_Coal> {
	
	private static final int DEFAULT_WORK = 400;
	private static final int INPUT_SLOT = 0;
	private static final int OUTPUT_SLOT = 1;
	static IStructureDefinition<GTMTE_Mining_Coal> definition =
			StructureDefinition.<GTMTE_Mining_Coal>builder()
					.addShape("main", new String[][]{
							{"  A  ", "  A  ", "  A  "},
							{"     ", "  A  ", "     "},
							{"A ~ A", "AABAA", "A   A"},
							{"     ", "  A  ", "     "},
							{"  A  ", "  A  ", "  A  "}
					})
					.addElement('A', lazy(t -> ofFrame(Materials.Iron)))
					.addElement('B', ofHatchAdder(GTMTE_Mining_Coal::checkHatch, 178, ImpactAPI.RED))
					.build();
	static int TIER_MINER = 0;
	private final List<GTMTE_OreHatch> hatch = new ArrayList<>();
	public int cBurnTime = 0, maxBurnTime = 10, sizeVeinPreStart = 0;
	public int cycleIncrease = 0;
	public OreChunkGenerator oreChunkGenerator = null;
	public OreVein oreVein = OreGenerator.empty;
	
	public GTMTE_Mining_Coal(int aID, String aNameRegional) {
		super(aID, "impact.multis.miner.coal", aNameRegional);
	}
	
	public GTMTE_Mining_Coal(String aName) {
		super(aName);
	}
	
	@Override
	public Object getServerGUI(int aID, InventoryPlayer p, IGregTechTileEntity te) {
		return new CoalContainer(p, te);
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer p, IGregTechTileEntity te) {
		return new CoalGUI(new CoalContainer(p, te));
	}
	
	@Override
	public void onFirstTick(IGregTechTileEntity te) {
		super.onFirstTick(te);
		initOreProperty(te);
	}
	
	public void increaseLayer(IGregTechTileEntity te) {
		if (te.isServerSide()) {
			oreChunkGenerator.sizeOreChunk--;
			if (oreChunkGenerator.sizeOreChunk <= 0) {
				oreChunkGenerator.sizeOreChunk = 0;
			}
		}
	}
	
	public void initOreProperty(IGregTechTileEntity te) {
		if (te.isServerSide()) {
			oreChunkGenerator = OreGenerator.getChunkFromIGT(te, TIER_MINER);
			if (oreChunkGenerator != null) {
				sizeVeinPreStart = oreChunkGenerator.sizeOreChunk;
				if (sizeVeinPreStart > 0) {
					oreVein = OreGenerator.getOreVein(te, TIER_MINER);
				} else {
					oreVein = OreGenerator.empty;
				}
			}
		}
	}
	
	@Override
	public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
		if (aSide == aFacing) {
			return new ITexture[]{Textures.BlockIcons.casingTexturePages[1][50], TextureFactory.of(aActive ?
					Textures.BlockIcons.OVERLAY_BOTTOM_STEAM_FURNACE_ACTIVE : Textures.BlockIcons.OVERLAY_BOTTOM_STEAM_FURNACE)};
		} else if (aSide == 1) {
			return new ITexture[]{Textures.BlockIcons.casingTexturePages[1][50], TextureFactory.of(Textures.BlockIcons.OVERLAY_PIPE_OUT)};
		}
		return new ITexture[]{Textures.BlockIcons.casingTexturePages[1][50]};
	}
	
	@Override
	public IStructureDefinition<GTMTE_Mining_Coal> getStructureDefinition() {
		return definition;
	}
	
	@Override
	public boolean machineStructure(IGregTechTileEntity te) {
		int dimID = te.getWorld().provider.dimensionId;
		if (dimID != 0 && dimID != Config.MiningWorldID) return false;
		noMaintenance();
		te.setFrontFacing((byte) 1);
		Chunk chunk = te.getWorld().getChunkFromBlockCoords(te.getXCoord(), te.getZCoord());
		int size = 0;
		for (Object o : chunk.chunkTileEntityMap.values()) {
			if (o instanceof TileEntity) {
				TileEntity tile = (TileEntity) o;
				if (tile instanceof IGregTechTileEntity) {
					IMetaTileEntity meta = ((IGregTechTileEntity) tile).getMetaTileEntity();
					if (meta instanceof GTMTE_Mining_Coal) {
						size++;
					}
				}
			}
		}
		if (size > 1) return false;
		hatch.clear();
		boolean check = checkPiece(2, 0, 2);
		if (hatch.size() != 1) check = false;
		return check;
	}
	
	private boolean checkHatch(IGregTechTileEntity te, int index) {
		if (te == null) {
			return false;
		} else {
			IMetaTileEntity mte = te.getMetaTileEntity();
			if (mte == null) {
				return false;
			} else {
				if (mte instanceof GT_MetaTileEntity_Hatch) {
					((GT_MetaTileEntity_Hatch) mte).updateTexture(index);
				}
				if (mte instanceof GTMTE_OreHatch) {
					((GTMTE_OreHatch) mte).updateTexture(index);
					return this.hatch.add((GTMTE_OreHatch) mte);
				} else {
					return false;
				}
			}
		}
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity te, long aTick) {
		super.onPostTick(te, aTick);
		if (te.isClientSide()) {
			if (te.isActive()) {
				te.getWorld().spawnParticle("largesmoke",
						te.getOffsetX((byte) 0, 1) + XSTR.XSTR_INSTANCE.nextFloat(),
						te.getOffsetY((byte) 0, 2),
						te.getOffsetZ((byte) 0, 1) + XSTR.XSTR_INSTANCE.nextFloat(),
						0.1D, 0.6D, 0.1D
				);
			}
		} else {
			if (te.isActive() && (aTick & 0x7) == 0) {
				
				IInventory tTileEntity = te.getIInventoryAtSide((byte) 1);
				if (tTileEntity != null) {
					if (mInventory[OUTPUT_SLOT] != null) {
						GT_Utility.moveOneItemStack(te, tTileEntity, (byte) 1, (byte) 0,
								null, false, (byte) 64, (byte) 1, (byte) 64, (byte) 1
						);
					}
				}
			}
			if (cBurnTime > 0) {
				te.setActive(true);
				cBurnTime--;
			}
			if (te.isActive()) {
				if (cBurnTime <= 0 && !checkFuel()) {
					mEfficiency = mProgresstime = mMaxProgresstime = mEfficiencyIncrease = 0;
					te.setActive(false);
				}
				if (mProgresstime == DEFAULT_WORK - 1) {
					
					if (mInventory[OUTPUT_SLOT] != null) {
						mInventory[OUTPUT_SLOT].stackSize += 1;
						if (mInventory[OUTPUT_SLOT].stackSize >= 64) {
							mInventory[OUTPUT_SLOT].stackSize = 64;
						}
					} else {
						ItemStack stack = oreVein.ores.get(0);
						mInventory[OUTPUT_SLOT] = new ItemStack(stack.getItem(), 1, stack.getItemDamage());
					}
				}
			} else {
				if (aTick % 20 == 2 && hatch.size() > 0) {
					if (hatch.get(0) != null) {
						hatch.get(0).cycleDrill(false);
					}
				}
			}
		}
	}
	
	private boolean checkFuel() {
		if (hatch.size() <= 0) {
			return false;
		}
		if (hatch.get(0) == null) return false;
		boolean check = oreVein != null && hatch.get(0).ready;
		if (check) {
			if (cBurnTime <= 0 && TileEntityFurnace.getItemBurnTime(this.mInventory[INPUT_SLOT]) > 0) {
				maxBurnTime = cBurnTime = TileEntityFurnace.getItemBurnTime(this.mInventory[INPUT_SLOT]);
				mInventory[INPUT_SLOT].stackSize--;
				if (mInventory[INPUT_SLOT].stackSize <= 0) {
					mInventory[INPUT_SLOT] = null;
				}
			}
		}
		check = check && cBurnTime > 0;
		hatch.get(0).cycleDrill(check);
		return check;
	}
	
	@Override
	public boolean checkRecipe(ItemStack itemStack) {
		if (oreChunkGenerator == null || oreChunkGenerator.sizeOreChunk <= 0) return false;
		if (cBurnTime <= 0 && mInventory[INPUT_SLOT] == null && oreVein == OreGenerator.empty) {
			return false;
		}
		if (sizeVeinPreStart < 1) {
			stopMachine();
			return false;
		}
		checkFuel();
		if (cBurnTime > 0) {
			mMaxProgresstime = DEFAULT_WORK;
		} else {
			return false;
		}
		increaseLayer(getBaseMetaTileEntity());
		--sizeVeinPreStart;
		return true;
	}
	
	@Override
	public boolean isFacingValid(byte aFacing) {
		return aFacing > 1;
	}
	
	@Override
	public boolean isOutputFacing(byte aSide) {
		return aSide == 1;
	}
	
	@Override
	public boolean isInputFacing(byte aSide) {
		return aSide != 1;
	}
	
	@Override
	public boolean isValidSlot(int aIndex) {
		return aIndex == 0 || aIndex == 1;
	}
	
	@Override
	public boolean inputStack(IGregTechTileEntity te, int slotIndex, int side, ItemStack stack) {
		return side != 1 && slotIndex == 0;
	}
	
	@Override
	public boolean outputStack(IGregTechTileEntity te, int slotIndex, int side, ItemStack stack) {
		return side == 1 && slotIndex == 1;
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("coal_miner");
		b		.addInfo("info.0", "Factorio Miner??")
				.addTypeMachine("name", "Miner")
				.addInfo("info.1", "Mining takes place in current Chunk")
				.addInfo("info.2", "There is only ONE miner in one Chunk")
				.addSeparator()
				.addController()
				.sizeStructure(5, 3, 5)
				.addOtherStructurePartAny("case.0", "Iron Frame Box", 16)
				.addRedHint("Miner Drill Hatch")
				.signAndFinalize();
		return b;
	}
	
	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_Mining_Coal(mName);
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setInteger("cBurnTime", cBurnTime);
		aNBT.setInteger("maxBurnTime", maxBurnTime);
		aNBT.setInteger("cycleIncrease", cycleIncrease);
		aNBT.setInteger("sizeVeinPreStart", sizeVeinPreStart);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		cBurnTime        = aNBT.getInteger("cBurnTime");
		maxBurnTime      = aNBT.getInteger("maxBurnTime");
		cycleIncrease    = aNBT.getInteger("cycleIncrease");
		sizeVeinPreStart = aNBT.getInteger("sizeVeinPreStart");
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 2, 0, 2);
	}
	
	private static class CoalGUI extends GT_GUIContainerMT_Machine {
		
		public CoalGUI(GT_ContainerMetaTile_Machine aContainer) {
			super(aContainer, RES_PATH_GUI + "basic/" + "CoalMiner.png");
		}
		
		@Override
		protected void drawGuiContainerForegroundLayer(int par1, int par2) {
			fontRendererObj.drawString("Coal Miner", 6, 4, Color.BLACK.hashCode());
		}
		
		@Override
		protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
			super.drawGuiContainerBackgroundLayer(par1, par2, par3);
			int x = (width - xSize) / 2;
			int y = (height - ySize) / 2;
			drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
			double work = (double) mContainer.mProgressTime / (double) mContainer.mMaxProgressTime;
			drawTexturedModalRect(x + 78, y + 24, 176, 1, Math.min(20, (int) (work * 20)), 16);
			
			double burn = (double) ((CoalContainer) mContainer).burnTime / ((CoalContainer) mContainer).maxBurnTime;
			drawTexturedModalRect(x + 81, y + 60 - (int) Math.min(13d, burn * 13d), 198, 14 - (int) Math.min(13d, burn * 13d), 14, 13);
		}
	}
	
	private static class CoalContainer extends GT_ContainerMetaTile_Machine {
		
		public int cWork, burnTime, maxBurnTime;
		
		public CoalContainer(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
			super(aInventoryPlayer, aTileEntity);
		}
		
		@Override
		public void addSlots(InventoryPlayer aInventoryPlayer) {
			addSlotToContainer(new Slot(this.mTileEntity, 0, 53, 25));
			addSlotToContainer(new GT_Slot_Output(this.mTileEntity, 1, 107, 25));
		}
		
		@Override
		public void detectAndSendChanges() {
			super.detectAndSendChanges();
			if (mTileEntity.isClientSide() || mTileEntity.getMetaTileEntity() == null) return;
			GTMTE_Mining_Coal te = (GTMTE_Mining_Coal) mTileEntity.getMetaTileEntity();
			cWork       = te.mProgresstime;
			burnTime    = te.cBurnTime;
			maxBurnTime = te.maxBurnTime;
			for (Object crafter : this.crafters) {
				ICrafting ic = (ICrafting) crafter;
				ic.sendProgressBarUpdate(this, 100, cWork & 65535);
				ic.sendProgressBarUpdate(this, 101, cWork >>> 16);
				ic.sendProgressBarUpdate(this, 102, burnTime & 65535);
				ic.sendProgressBarUpdate(this, 103, burnTime >>> 16);
				ic.sendProgressBarUpdate(this, 104, maxBurnTime & 65535);
				ic.sendProgressBarUpdate(this, 105, maxBurnTime >>> 16);
			}
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void updateProgressBar(int par1, int par2) {
			super.updateProgressBar(par1, par2);
			switch (par1) {
				case 100:
					cWork = cWork & -65536 | par2;
					break;
				case 101:
					cWork = cWork & 65535 | par2 << 16;
					break;
				case 102:
					burnTime = burnTime & -65536 | par2;
					break;
				case 103:
					burnTime = burnTime & 65535 | par2 << 16;
					break;
				case 104:
					maxBurnTime = maxBurnTime & -65536 | par2;
					break;
				case 105:
					maxBurnTime = maxBurnTime & 65535 | par2 << 16;
					break;
			}
		}
		
		@Override
		public int getSlotCount() {
			return 2;
		}
		
		@Override
		public int getShiftClickSlotCount() {
			return 1;
		}
	}
	
	@Override
	public boolean hasSeparate() {
		return false;
	}
}
