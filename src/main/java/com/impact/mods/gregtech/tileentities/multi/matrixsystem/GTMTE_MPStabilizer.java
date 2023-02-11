package com.impact.mods.gregtech.tileentities.multi.matrixsystem;

import com.impact.impact;
import com.impact.loader.ItemRegistery;
import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.gui.base.GTC_ImpactBase;
import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.network.special.ToClient_LaserPush;
import com.impact.util.Utilits;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.render.TextureFactory;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import static net.minecraft.util.EnumChatFormatting.*;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlock;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlockAnyMeta;

public class GTMTE_MPStabilizer extends GTMTE_Impact_BlockBase<GTMTE_MPStabilizer> {
	
	public static Block CASING = Casing_Helper.sCaseCore2;
	public static byte CASING_META = 15;
	static IStructureDefinition<GTMTE_MPStabilizer> definition =
			StructureDefinition.<GTMTE_MPStabilizer>builder()
					.addShape("main", new String[][]{
							{"AA AA", "AB~BA", "AA AA"},
							{"ABBBA", "D   D", "ABBBA"},
							{"AA AA", "ABBBA", "AA AA"},
					})
					.addElement('A', ofBlock(CASING, CASING_META))
					.addElement('B', ofBlock(ItemRegistery.MPSystem, 0))
					.addElement('D', ofBlockAnyMeta(ItemRegistery.IGlassBlock))
					.build();
	public int mMPSummary = 0;
	public boolean mMPBeam = false;
	public int mRangeToContainer = 0;
	public boolean mCheckContainer = true;
	public GTMTE_MPContainment mMPContainment;
	ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META + 16];
	int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;
	
	//region Register
	public GTMTE_MPStabilizer(int aID, String aNameRegional) {
		super(aID, "impact.multis.matrixstabilizer", aNameRegional);
	}
	
	public GTMTE_MPStabilizer(String aName) {
		super(aName);
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_MPStabilizer(this.mName);
	}
	//endregion
	
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
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("matrix_stabilizer");
		b
				.addInfo("info.0", "Stabilizes unstable Matrix Particles")
				.addTypeMachine("name", "Stabilizer")
				.addInfo("info.1", "Max range to Matrix Particle Containment: 30 blocks")
				.addInfo("info.2", "Constant power consumption: 1,920 EU/t")
				.addInfo("info.3", "Need Low Gravity")
				.addSeparator()
				.addController()
				.addEnergyHatch()
				.addMaintenanceHatch()
				.addCasingInfo("case", "Lab-Safe Low Gravity Casing", 22)
				.addOtherStructurePartAny("glass", "Any I-Glass")
				.addOtherStructurePartAny("reflector", "Matrix Particle Reflector", 11)
				.signAndFinalize();
		return b;
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 2, 1, 0);
	}
    
    @Override
    public IStructureDefinition<GTMTE_MPStabilizer> getStructureDefinition() {
        return definition;
    }
    
    @Override
	public boolean checkRecipe(ItemStack aStack) {
		this.mEUt = -1920;
		this.mEfficiency = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
		this.mMaxProgresstime = 40;
		return true;
	}
	
	public void addBound(IGregTechTileEntity iAm) {
		int x = iAm.getXCoord();
		int y = iAm.getYCoord();
		int z = iAm.getZCoord();
		
		final Vector3ic forgeDirection = new Vector3i(
				ForgeDirection.getOrientation(iAm.getBackFacing()).offsetX,
				ForgeDirection.getOrientation(iAm.getBackFacing()).offsetY,
				ForgeDirection.getOrientation(iAm.getBackFacing()).offsetZ
		);
		if (iAm.isActive()) {
			
			Vector3ic offset = rotateOffsetVector(forgeDirection, 0, 0, -1);
			Vector3ic offsetToStabilizer = rotateOffsetVector(forgeDirection, mRangeToContainer - 1, 0, -1);
			
			new ToClient_LaserPush(iAm.getWorld().provider.dimensionId, new Vector3i(offset.x() + x, offset.y() + y, offset.z() + z),
					new Vector3i(offsetToStabilizer.x() + x, offsetToStabilizer.y() + y, offsetToStabilizer.z() + z), 0x770ED0, 1, 20*2, 1, 1).sendToClients();
		}
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity iAm, long aTick) {
		super.onPostTick(iAm, aTick);
		
		if (iAm.isServerSide() && aTick % 20 * 5 == 0) {
			
			if (!iAm.isActive()) mMPSummary = 0;
			
			final Vector3ic forgeDirection = new Vector3i(
					ForgeDirection.getOrientation(iAm.getBackFacing()).offsetX,
					ForgeDirection.getOrientation(iAm.getBackFacing()).offsetY,
					ForgeDirection.getOrientation(iAm.getBackFacing()).offsetZ
			);
			doCheckContainer(iAm);
			
			if (iAm.isActive()) {
				Vector3ic offsetCheckBlock = rotateOffsetVector(forgeDirection, 0, 2, -1);
				impact.proxy.nodeBolt(iAm.getWorld(), iAm.getXCoord(), iAm.getYCoord(), iAm.getZCoord(), offsetCheckBlock.x(), offsetCheckBlock.y(), offsetCheckBlock.z(), 5, 10F, 1);
				impact.proxy.nodeBolt(iAm.getWorld(), iAm.getXCoord(), iAm.getYCoord(), iAm.getZCoord(), offsetCheckBlock.x(), offsetCheckBlock.y(), offsetCheckBlock.z(), 5, 10F, 1);
				
				for (Object o : iAm.getWorld().playerEntities) {
					if (o instanceof EntityPlayer) {
						EntityPlayer player1 = (EntityPlayer) o;
						if (Utilits.distanceBetween3D(iAm.getXCoord(), (int) player1.posX, iAm.getYCoord(), (int) player1.posY, iAm.getZCoord(), (int) player1.posZ) < 8) {
							impact.proxy.nodeBolt(iAm.getWorld(), iAm.getXCoord(), iAm.getYCoord(), iAm.getZCoord(), player1);
						}
					}
				}
			}
			
			if (!mCheckContainer && iAm.isActive()) {
				
				if (mMPSummary >= 1000) {
					
					Vector3ic offsetX = rotateOffsetVector(forgeDirection, mRangeToContainer, 0, 1);
					IGregTechTileEntity currentTE = iAm.getIGregTechTileEntityOffset(offsetX.x(), offsetX.y(), offsetX.z());
					
					if (currentTE != null) {
						if (currentTE.getMetaTileEntity() instanceof GTMTE_MPContainment) {
							mMPContainment  = (GTMTE_MPContainment) currentTE.getMetaTileEntity();
							mCheckContainer = false;
							if (currentTE.isActive()) {
								if (mMPSummary >= 100_000) {
									this.mEUt = -((int) GT_Values.V[4] / 4);
								}
								if (mMPContainment.mMPStable <= 99_900) {
									mMPContainment.setMP(1000);
									addBound(iAm);
									mMPSummary -= 1000;
								}
							}
						}
					} else {
						mMPContainment    = null;
						mCheckContainer   = true;
						mRangeToContainer = 0;
					}
				}
			}
		}
	}
	
	public void setMP(int amount) {
		mMPSummary += amount;
	}
	
	public void doCheckContainer(IGregTechTileEntity iAm) {
		if (mMachine && mCheckContainer) {
			
			final Vector3ic forgeDirection = new Vector3i(
					ForgeDirection.getOrientation(iAm.getBackFacing()).offsetX,
					ForgeDirection.getOrientation(iAm.getBackFacing()).offsetY,
					ForgeDirection.getOrientation(iAm.getBackFacing()).offsetZ
			);
			
			Vector3ic offsetX;
			IGregTechTileEntity currentTE = null;
			
			for (; mRangeToContainer < 30; mRangeToContainer++) {
				
				offsetX   = rotateOffsetVector(forgeDirection, mRangeToContainer, 0, 1);
				currentTE = iAm.getIGregTechTileEntityOffset(offsetX.x(), offsetX.y(), offsetX.z());
				if (currentTE != null) {
					if (currentTE.getMetaTileEntity() instanceof GTMTE_MPContainment) {
						mMPContainment  = (GTMTE_MPContainment) currentTE.getMetaTileEntity();
						mCheckContainer = false;
						break;
					}
				}
			}
			if (currentTE == null) {
				mMPContainment    = null;
				mCheckContainer   = true;
				mRangeToContainer = 0;
			}
		}
	}
	
	@Override
	public boolean machineStructure(IGregTechTileEntity iAm) {
		
		if (!Utilits.isLowGravity(iAm)) {
			return false;
		}
		//region Structure
		final Vector3ic forgeDirection = new Vector3i(
				ForgeDirection.getOrientation(iAm.getBackFacing()).offsetX,
				ForgeDirection.getOrientation(iAm.getBackFacing()).offsetY,
				ForgeDirection.getOrientation(iAm.getBackFacing()).offsetZ
		);
		
		boolean formationChecklist = true;
		
		for (int x = -2; x <= 2; x++) {
			for (int y = 0; y <= 2; y++) {
				
				final Vector3ic offset = rotateOffsetVector(forgeDirection, x, -1, -y);
				IGregTechTileEntity currentTE = iAm.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				
				if (x == 0 && y != 1) continue;
				
				if (y == 1 && x >= -1 && x <= 1) {
					if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == ItemRegistery.MPSystem)
							&& (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 0)) {
						
					} else {
						formationChecklist = false;
					}
					continue;
				}
				
				if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)) {
					if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
							&& (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
					} else {
						formationChecklist = false;
					}
				}
			}
		}
		
		for (int x = -2; x <= 2; x++) {
			for (int y = 0; y <= 2; y++) {
				
				final Vector3ic offset = rotateOffsetVector(forgeDirection, x, 0, -y);
				IGregTechTileEntity currentTE = iAm.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				
				if (x == 0 && y == 0) continue;
				
				if (y == 1 && x >= -1 && x <= 1) continue;
				
				if (y != 1 && x >= -1 && x <= 1) {
					if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == ItemRegistery.MPSystem)
							&& (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 0)) {
					} else {
						formationChecklist = false;
					}
					continue;
				}
				
				if (y == 1) {
					if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == ItemRegistery.IGlassBlock)) {
					} else {
						formationChecklist = false;
					}
					continue;
				}
				
				if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)) {
					if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
							&& (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
					} else {
						formationChecklist = false;
					}
				}
			}
		}
		
		for (int x = -2; x <= 2; x++) {
			for (int y = 0; y <= 2; y++) {
				
				final Vector3ic offset = rotateOffsetVector(forgeDirection, x, 1, -y);
				IGregTechTileEntity currentTE = iAm.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				
				if (x == 0 && y != 1) continue;
				
				if (y == 1 && x >= -1 && x <= 1) {
					if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == ItemRegistery.MPSystem)
							&& (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 0)) {
					} else {
						formationChecklist = false;
					}
					continue;
				}
				
				if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)) {
					if ((iAm.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
							&& (iAm.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
					} else {
						formationChecklist = false;
					}
				}
			}
		}
		
		return formationChecklist;
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setInteger("mMPSummary", mMPSummary);
		aNBT.setInteger("mRangeToContainer", mRangeToContainer);
		aNBT.setBoolean("mMPBeam", mMPBeam);
		aNBT.setBoolean("mCheckContainer", mCheckContainer);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		mMPSummary        = aNBT.getInteger("mMPSummary");
		mRangeToContainer = aNBT.getInteger("mRangeToContainer");
		mMPBeam           = aNBT.getBoolean("mMPBeam");
		mCheckContainer   = aNBT.getBoolean("mCheckContainer");
	}
	
	public String[] getInfoData() {
		return new String[]{
				"Usage Energy: " + RED + -mEUt + RESET + " EU/t",
				"Max Voltage: " + YELLOW + getMaxInputVoltage() + RESET + " EU/t ",
				"Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus()) ? GREEN + "Good "
						+ YELLOW + mEfficiency / 100.0F + " %" + RESET
						: RED + "Has Problems " + mEfficiency / 100.0F + " %" + RESET),
		};
	}
	
	@Override
	public boolean hasSeparate() {
		return false;
	}
}