package com.impact.mods.gregtech.tileentities.multi.units;

import buildcraft.api.core.IAreaProvider;
import buildcraft.core.Box;
import buildcraft.core.lib.utils.Utils;
import com.github.technus.tectech.mechanics.alignment.enumerable.ExtendedFacing;
import com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer;
import com.github.technus.tectech.mechanics.structure.IStructureDefinition;
import com.github.technus.tectech.mechanics.structure.StructureDefinition;
import com.impact.impact;
import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.enums.Texture;
import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.Utilits;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import cpw.mods.fml.common.Loader;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

import static com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer.registerMetaClass;
import static com.github.technus.tectech.mechanics.structure.StructureUtility.ofBlock;
import static com.impact.loader.ItemRegistery.*;

public class GTMTE_Filler extends GT_MetaTileEntity_MultiParallelBlockBase {
	
	public static String mModed;
	Block CASING = Casing_Helper.sCaseCore2;
	byte CASING_META = 14;
	ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META + 16];
	int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;
	boolean isConnect = false;
	
	private int endX, endY, endZ;
	private int curX, curY, curZ;
	private boolean isVoidDrop = false;
	
	public GTMTE_Filler(int aID, String aNameRegional) {
		super(aID, "impact.multis.filler", aNameRegional);
		holo();
	}
	
	public GTMTE_Filler(String aName) {
		super(aName);
	}
	
	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		return aSide == aFacing ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(aActive ? Texture.Icons.OVERLAY_SPACE_ELEVATOR_ACTIVE : Texture.Icons.OVERLAY_SPACE_ELEVATOR)} : new ITexture[]{INDEX_CASE};
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_Filler(this.mName);
	}
	
	public boolean isFacingValid(byte aFacing) {
		return true;
	}
	
	@Override
	public String[] getDescription() {
		final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
		b.signAndFinalize(": " + EnumChatFormatting.RED + "IMPACT");
		if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			return b.getInformation();
		} else {
			return b.getStructureInformation();
		}
	}
	
	public void holo() {
		registerMetaClass(GTMTE_Filler.class, new IMultiblockInfoContainer<GTMTE_Filler>() {
			//region Structure
			private final IStructureDefinition<GTMTE_Filler> definition =
					StructureDefinition.<GTMTE_Filler>builder()
							.addShape("main", new String[][]{
									{"A"}})
							.addElement('A', ofBlock(CASING, CASING_META))
							.addElement('B', ofBlock(SpaceElevatorBlock))
							.build();
			private final String[] desc = new String[]{
					EnumChatFormatting.RED + "Impact Details:",
			};
			//endregion
			
			@Override
			public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_Filler tileEntity, ExtendedFacing aSide) {
				IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
				definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide, base.getXCoord(), base.getYCoord(), base.getZCoord(), 3, 4, 3, hintsOnly);
			}
			
			@Override
			public String[] getDescription(ItemStack stackSize) {
				return desc;
			}
		});
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), "MultiParallelBlockGUI.png");
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setInteger("curX", curX);
		aNBT.setInteger("curY", curY);
		aNBT.setInteger("curZ", curZ);
		
		aNBT.setInteger("endX", endX);
		aNBT.setInteger("endY", endY);
		aNBT.setInteger("endZ", endZ);
		
		aNBT.setBoolean("isVoidDrop", isVoidDrop);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		curX = aNBT.getInteger("curX");
		curY = aNBT.getInteger("curY");
		curZ = aNBT.getInteger("curZ");
		
		endX = aNBT.getInteger("endX");
		endY = aNBT.getInteger("endY");
		endZ = aNBT.getInteger("endZ");
		
		isVoidDrop = aNBT.getBoolean("isVoidDrop");
	}
	
	@Override
	public boolean machineStructure(IGregTechTileEntity thisController) {
		final Vector3ic forgeDirection = new Vector3i(
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ
		);
		
		boolean formationChecklist = true;
		
		for (int X = 0; X <= 1; X++) {
			if (X == 0) {
				continue;
			}
			final Vector3ic offset = rotateOffsetVector(forgeDirection, X, 0, 0);
			
			IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
			if (!super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
					&& !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
					&& !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {
				if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
						&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
				}/* else {
					formationChecklist = false;
				}*/
			}
		}
		mWrench        = true;
		mScrewdriver   = true;
		mSoftHammer    = true;
		mHardHammer    = true;
		mSolderingTool = true;
		mCrowbar       = true; //todo Пересмотреть мейнтенанс
		return formationChecklist;
	}
	
	
	private ArrayList<ItemStack> getBlockDrops(final Block oreBlock, int posX, int posY, int posZ) {
		final int blockMeta = getBaseMetaTileEntity().getMetaID(posX, posY, posZ);
		return oreBlock.getDrops(getBaseMetaTileEntity().getWorld(), posX, posY, posZ, blockMeta, 0);
	}
	
	public void mineBlock(int x, int y, int z) {
		IGregTechTileEntity iAm = getBaseMetaTileEntity();
		x = iAm.getXCoord() + x;
		y = iAm.getYCoord() + y;
		z = iAm.getZCoord() + z;
		
		ArrayList<ItemStack> drops = getBlockDrops(iAm.getBlock(x, y, z), x, y, z);
		//iAm.getWorld().setBlock(x, y, z, decorateBlock[2], 1, 2);
		impact.proxy.hint_particle(iAm.getWorld(), x, y, z, UtilBlock, 2, 2);
		if (isVoidDrop) {
			iAm.getWorld().setBlockToAir(x, y, z);
		}
		
		if (!isVoidDrop && !drops.isEmpty()) {
			iAm.getWorld().setBlockToAir(x, y, z);
		}
		
		for (ItemStack drop : drops) {
			addOutput(drop);
		}
	}
	
	public void addBound(World w, int x, int y, int z) {
		IGregTechTileEntity iAm = getBaseMetaTileEntity();
		
		final Vector3ic forgeDirection = new Vector3i(
				ForgeDirection.getOrientation(iAm.getBackFacing()).offsetX,
				ForgeDirection.getOrientation(iAm.getBackFacing()).offsetY,
				ForgeDirection.getOrientation(iAm.getBackFacing()).offsetZ
		);
		
		int age = 20 * 5;
		
		Vector3ic offset = rotateOffsetVector(forgeDirection, 0, 0, -1);
		impact.proxy.hint_particle(w, offset.x() + x, offset.y() + y, offset.z() + z, decorateBlock[2], 1, age);
		
		offset = rotateOffsetVector(forgeDirection, 0, 0, -endZ);
		impact.proxy.hint_particle(w, offset.x() + x, offset.y() + y, offset.z() + z, decorateBlock[2], 1, age);
		
		offset = rotateOffsetVector(forgeDirection, endX, 0, -1);
		impact.proxy.hint_particle(w, offset.x() + x, offset.y() + y, offset.z() + z, decorateBlock[2], 1, age);
		
		offset = rotateOffsetVector(forgeDirection, endX, 0, -endZ);
		impact.proxy.hint_particle(w, offset.x() + x, offset.y() + y, offset.z() + z, decorateBlock[2], 1, age);
		
		offset = rotateOffsetVector(forgeDirection, 0, endY, -1);
		impact.proxy.hint_particle(w, offset.x() + x, offset.y() + y, offset.z() + z, decorateBlock[2], 1, age);
		
		offset = rotateOffsetVector(forgeDirection, 0, endY, -endZ);
		impact.proxy.hint_particle(w, offset.x() + x, offset.y() + y, offset.z() + z, decorateBlock[2], 1, age);
		
		offset = rotateOffsetVector(forgeDirection, endX, endY, -1);
		impact.proxy.hint_particle(w, offset.x() + x, offset.y() + y, offset.z() + z, decorateBlock[2], 1, age);
		
		offset = rotateOffsetVector(forgeDirection, endX, endY, -endZ);
		impact.proxy.hint_particle(w, offset.x() + x, offset.y() + y, offset.z() + z, decorateBlock[2], 1, age);
		
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity iAm, long aTick) {
		super.onPostTick(iAm, aTick);
		if (iAm.isServerSide()) {
			int x = iAm.getXCoord();
			int y = iAm.getYCoord();
			int z = iAm.getZCoord();
			World w = iAm.getWorld();
			
			if (aTick == 1 || aTick % (20 * 5) == 0) {
				addBound(w, x, y, z);
			}
			if (iAm.isActive() && aTick % 2 == 0) {
				curX--;
				if (curX < 0) {
					curX = endX;
					curZ++;
					if (curZ >= 0) {
						curZ = -endZ;
						curY--;
						if (curY < 0) {
							curY = endY;
						}
					}
				}
				final Vector3ic forgeDirection = new Vector3i(
						ForgeDirection.getOrientation(iAm.getBackFacing()).offsetX,
						ForgeDirection.getOrientation(iAm.getBackFacing()).offsetY,
						ForgeDirection.getOrientation(iAm.getBackFacing()).offsetZ
				);
				Vector3ic offset = rotateOffsetVector(forgeDirection, curX, curY, curZ);
				mineBlock(offset.x(), offset.y(), offset.z());
			}
		}
	}
	
	@Override
	public void onFirstTick(IGregTechTileEntity iAm) {
		super.onFirstTick(iAm);
		if (iAm.isServerSide()) {
			Vector3ic forgeDirection = new Vector3i(
					ForgeDirection.getOrientation(iAm.getBackFacing()).offsetX,
					ForgeDirection.getOrientation(iAm.getBackFacing()).offsetY,
					ForgeDirection.getOrientation(iAm.getBackFacing()).offsetZ
			);
			Vector3ic offset = rotateOffsetVector(forgeDirection, 0, 0, -1);
			World w = iAm.getWorld();
			
			endX = 5;
			endY = 5;
			endZ = 5;
			
			TileEntity te = w.getTileEntity(offset.x() + iAm.getXCoord(), offset.y() + iAm.getYCoord(), offset.z() + iAm.getZCoord());
			
			
			if (Loader.isModLoaded("BuildCraft|Core") && te instanceof IAreaProvider) {
				IAreaProvider a = Utils.getNearbyAreaProvider(w, iAm.getXCoord(), iAm.getYCoord(), iAm.getZCoord());
				if (a != null) {
					Box box = new Box();
					box.initialize(a);
					a.removeFromWorld();
					
					endX = box.sizeX() - 1;
					endY = box.sizeY() - 1;
					endZ = box.sizeZ();
					
					Utilits.sendChatByTE(iAm, " " + endX + " " + endY + " " + endZ);
				}
			}
		}
	}
	
	@Override
	public void onNotePadRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		super.onNotePadRightClick(aSide, aPlayer, aX, aY, aZ);
	}
	
	@Override
	public boolean checkRecipe(ItemStack itemStack) {
		mMaxProgresstime         = 2;
		this.mEUt                = 0;
		this.mEfficiencyIncrease = 10000;
		this.mEfficiency         = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
		return this.mEfficiency >= 10000;
	}
	
	@Override
	public int getPollutionPerTick(ItemStack aStack) {
		return 0;
	}
	
	public boolean isMe(int x, int y, int z) {
		return x == 0 && y == 0 && z == 0;
	}
	
	@Override
	public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
		isVoidDrop = !isVoidDrop;
		GT_Utility.sendChatToPlayer(aPlayer, "Save Mode " + (isVoidDrop ? "Disabled" : "Enabled"));
	}
}