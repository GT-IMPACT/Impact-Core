package com.impact.mods.gregtech.tileentities.multi.units;

import com.impact.core.Impact_API;
import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.enums.Texture;
import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.PositionObject;
import com.impact.util.Utilits;
import com.impact.util.string.MultiBlockTooltipBuilder;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import java.util.List;

import static com.impact.loader.ItemRegistery.SpaceElevatorBlock;
import static com.impact.util.Utilits.isValidDim;
import static com.impact.util.multis.GT_StructureUtility.ofHatchAdder;
import static space.impact.api.multiblocks.structure.StructureUtility.*;

public class GTMTE_SpaceElevator extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_SpaceElevator> {
	
	public static String mModed;
	static Block CASING = Casing_Helper.sCaseCore2;
	static int CASING_META = 14;
	static ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META + 16];
	static int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;
	private static final IStructureDefinition<GTMTE_SpaceElevator> STRUCTURE_DEFINITION =
			StructureDefinition.<GTMTE_SpaceElevator>builder()
					.addShape("main", new String[][]{
							{" AAAAA ", " A   A ", " A   A ", " A   A ", " AAAAA ", " AAAAA "},
							{"AAAAAAA", "AA   AA", "AA   AA", "AA   AA", "AAAAAAA", "AAAAAAA"},
							{"AA   AA", "       ", "       ", "       ", "AAAAAAA", "AAAAAAA"},
							{"AA   AA", "       ", "       ", "       ", "AAA~AAA", "AAABAAA"},
							{"AA   AA", "       ", "       ", "       ", "AAAAAAA", "AAAAAAA"},
							{"AAAAAAA", "AA   AA", "AA   AA", "AA   AA", "AAAAAAA", "AAAAAAA"},
							{" AAAAA ", " A   A ", " A   A ", " A   A ", " AAAAA ", " AAAAA "}
					})
					.addElement('A', ofChain(
							ofBlock(CASING, CASING_META),
							ofHatchAdder(GTMTE_SpaceElevator::addEnergyInputToMachineList, CASING_TEXTURE_ID, CASING, CASING_META)
					))
					.addElement('B', ofBlockAnyMeta(SpaceElevatorBlock))
					.build();
	boolean isConnect = false;
	
	public GTMTE_SpaceElevator(int aID, String aNameRegional) {
		super(aID, "impact.multimachine.spaceelevator", aNameRegional);
	}
	
	public GTMTE_SpaceElevator(String aName) {
		super(aName);
	}
	
	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		return aSide == 1 ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(aActive ? Texture.Icons.OVERLAY_SPACE_ELEVATOR_ACTIVE : Texture.Icons.OVERLAY_SPACE_ELEVATOR)} : new ITexture[]{INDEX_CASE};
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_SpaceElevator(this.mName);
	}
	
	public boolean isFacingValid(byte aFacing) {
		return aFacing > 1;
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png");
	}
	
	@Override
	public boolean machineStructure(IGregTechTileEntity thisController) {
		boolean formationChecklist = checkPiece(3, 4, 3);
		mWrench        = true;
		mScrewdriver   = true;
		mSoftHammer    = true;
		mHardHammer    = true;
		mSolderingTool = true;
		mCrowbar       = true; //todo Пересмотреть мейнтенанс
		return formationChecklist;
	}
	
	public boolean checkCurrentDimension(int dim) {
		return dim == getBaseMetaTileEntity().getWorld().provider.dimensionId;
	}
	
	public boolean isDimensionalTeleportAvailable() {
		return GT_Utility.isRealDimension(this.mTargetD) && GT_Utility.isRealDimension(getBaseMetaTileEntity().getWorld().provider.dimensionId);
	}
	
	public void teleportEntity() {
		IGregTechTileEntity iAm = getBaseMetaTileEntity();
		List entities_in_box = iAm.getWorld().getEntitiesWithinAABB(Entity.class, Utilits.setBoxAABB(iAm, 1.5));
		for (Object tObject : entities_in_box) {
			if (((tObject instanceof Entity)) && (!((Entity) tObject).isDead)) {
				Entity tEntity = (Entity) tObject;
				if (tEntity.ridingEntity != null) {
					tEntity.mountEntity(null);
				}
				if (tEntity.riddenByEntity != null) {
					tEntity.riddenByEntity.mountEntity(null);
				}
				if (iAm.getRedstone() && (checkCurrentDimension(mTargetD) || (!isDimensionalTeleportAvailable()) || (!GT_Utility.moveEntityToDimensionAtCoords(tEntity, mTargetD, mTargetX + 0.5D, mTargetY + 1.5D, mTargetZ + 0.5D)))) {
					if ((tEntity instanceof EntityLivingBase)) {
						((EntityLivingBase) tEntity).setPositionAndUpdate(mTargetX + 0.5D, mTargetY + 1.5D, mTargetZ + 0.5D);
					} else {
						tEntity.setPosition(mTargetX + 0.5D, mTargetY + 1.5D, mTargetZ + 0.5D);
					}
					mIsConnect = false;
				}
			}
		}
	}
	
	public void connect(IGregTechTileEntity tTile) {
		if (tTile.getMetaTileEntity() instanceof GTMTE_SpaceElevator) {
			GTMTE_SpaceElevator space = (GTMTE_SpaceElevator) tTile.getMetaTileEntity();
			PositionObject pos = new PositionObject(getBaseMetaTileEntity());
			space.setCoord(pos);
			this.isConnect = true;
		}
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
		super.onPostTick(aBaseMetaTileEntity, aTick);
		if (aBaseMetaTileEntity.isServerSide() && aBaseMetaTileEntity.isActive()) {
			if (aTick % 20 * 3 == 0) {
				IGregTechTileEntity tTile = null;
				if (this.mTargetD == getBaseMetaTileEntity().getWorld().provider.dimensionId) {
					tTile = getBaseMetaTileEntity().getIGregTechTileEntity(this.mTargetX, this.mTargetY, this.mTargetZ);
				} else {
					World tWorld = DimensionManager.getWorld(this.mTargetD);
					TileEntity te = tWorld != null ? tWorld.getTileEntity(this.mTargetX, this.mTargetY, this.mTargetZ) : null;
					tTile = te instanceof IGregTechTileEntity ? (IGregTechTileEntity) te : null;
				}
				if (tTile != null) {
					connect(tTile);
					if (this.isConnect) {
						teleportEntity();
						this.isConnect = false;
					}
				}
			}
		}
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("multi_elevator");
		b.addInfo("info.0", "Teleportation on Space Satellite")
				.addTypeMachine("name", "Space Elevator")
				.addInfo("info.1", "Setup is done using Laptop")
				.addInfo("info.2", "Send a redstone signal to teleport")
				.addInfo("info.3", "Passive usage: 1920 EU/t")
				.addController()
				.addEnergyHatch(2)
				.addCasingInfo("case", "Space Elevator Casing", 150)
				.addOtherStructurePart("other.0", "Space Elevator Hawser", "other.1", "Center below Controller")
				.signAndFinalize();
		return b;
	}
	
	public int[] getCoords() {
		return Utilits.getCoordsBaseMTE(getBaseMetaTileEntity());
	}
	
	public void getFrequency(EntityPlayer aPlayer) {
		int[] coords = Impact_API.sElevatorSpace.get(Utilits.inToStringUUID(1, aPlayer));
		if (coords == null) {
			GT_Utility.sendChatToPlayer(aPlayer, EnumChatFormatting.RED + "Failed Load Position");
			return;
		}
		PositionObject pos = new PositionObject(coords);
		GT_Utility.sendChatToPlayer(aPlayer, "Load Position");
		setCoord(pos);
	}
	
	public void setFrequency(EntityPlayer aPlayer) {
		Impact_API.sElevatorSpace.put(Utilits.inToStringUUID(1, aPlayer), getCoords());
		GT_Utility.sendChatToPlayer(aPlayer, "Save Position");
	}
	
	@Override
	public void onNotePadRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		super.onNotePadRightClick(aSide, aPlayer, aX, aY, aZ);
		ItemStack tCurrentItem = aPlayer.inventory.getCurrentItem();
		if (ItemList.Tool_NoteBook.getItem() == tCurrentItem.getItem()) {
			int dimId = getBaseMetaTileEntity().getWorld().provider.dimensionId;
			if (isValidDim(dimId, "Orbit") || isValidDim(dimId, "Space") ||
					isValidDim(dimId, "SS") || isValidDim(dimId, "SpaceStation")) {
				getFrequency(aPlayer);
			}
			if (dimId == 0) {
				setFrequency(aPlayer);
			}
			GT_ModHandler.damageOrDechargeItem(tCurrentItem, 1, 100, aPlayer);
		}
	}
	
	@Override
	public boolean checkRecipe(ItemStack itemStack) {
		this.mMaxProgresstime    = 1;
		this.mEUt                = -1920;
		this.mEfficiencyIncrease = 10000;
		this.mEfficiency         = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
		return this.mEfficiency >= 10000;
	}
	
	@Override
	public void construct(ItemStack stackSize, boolean hintsOnly) {
		buildPiece(stackSize, hintsOnly, 3, 4, 3);
	}
	
	@Override
	public IStructureDefinition<GTMTE_SpaceElevator> getStructureDefinition() {
		return STRUCTURE_DEFINITION;
	}
}