package com.impact.mods.gregtech.tileentities.multi.units;

import bloodasp.galacticgreg.GT_TileEntity_Ores_Space;
import buildcraft.api.core.IAreaProvider;
import buildcraft.core.Box;
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
import com.impact.util.vector.Structure;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import cpw.mods.fml.common.Loader;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Utility;
import gregtech.common.blocks.GT_TileEntity_Ores;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

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
	
	private int xMax, yMax, zMax;
	private int xMin, yMin, zMin;
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
		
		aNBT.setInteger("xMin", xMin);
		aNBT.setInteger("yMin", yMin);
		aNBT.setInteger("zMin", zMin);
		
		aNBT.setInteger("xMax", xMax);
		aNBT.setInteger("yMax", yMax);
		aNBT.setInteger("zMax", zMax);
		
		aNBT.setBoolean("isVoidDrop", isVoidDrop);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		curX = aNBT.getInteger("curX");
		curY = aNBT.getInteger("curY");
		curZ = aNBT.getInteger("curZ");
		
		xMin = aNBT.getInteger("xMin");
		yMin = aNBT.getInteger("yMin");
		zMin = aNBT.getInteger("zMin");
		
		xMax = aNBT.getInteger("xMax");
		yMax = aNBT.getInteger("yMax");
		zMax = aNBT.getInteger("zMax");
		
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
		ArrayList<ItemStack> is = new ArrayList<>();
		
		TileEntity te = getBaseMetaTileEntity().getTileEntity(posX, posY, posZ);
		if (te instanceof GT_TileEntity_Ores) {
			return is;
		}
		Block block = getBaseMetaTileEntity().getBlock(posX, posY, posZ);
		if (block != null) {
			final int blockMeta = getBaseMetaTileEntity().getMetaID(posX, posY, posZ);
			is.addAll(block.getDrops(getBaseMetaTileEntity().getWorld(), posX, posY, posZ, blockMeta, 0));
		}
		return is;
	}
	
	public void mineBlock(int x, int y, int z) {
		IGregTechTileEntity iAm = getBaseMetaTileEntity();
		ArrayList<ItemStack> drops = getBlockDrops(null, x, y, z);
		impact.proxy.hint_particle(iAm.getWorld(), x, y, z, decorateBlock[2], 1, 18);
		
		if (iAm.getBlock(x, y, z) == Blocks.bedrock) return;
		
		if (isVoidDrop) {
			iAm.getWorld().setBlock(x, y, z, Blocks.air, 0, 2);
			for (ItemStack drop : drops) {
				addOutput(drop);
			}
		} else {
			TileEntity te = iAm.getTileEntity(x, y, z);
			if (te == null || te instanceof GT_TileEntity_Ores) {
				iAm.getWorld().setBlock(x, y, z, Blocks.air, 0, 2);
			}
		}
	}
	
	public void addBound(World w, int x, int y, int z) {
		
		int age = 20 * 5;
		
		impact.proxy.hint_particle(w, xMin, yMin, zMin, decorateBlock[2], 2, age); // x0 y0 z0 green
		impact.proxy.hint_particle(w, xMax, yMax, zMax, decorateBlock[2], 3, age); // x1 y1 z1 blue
		
		impact.proxy.hint_particle(w, xMax, yMin, zMin, decorateBlock[2], 1, age); // x1 y0 z0
		impact.proxy.hint_particle(w, xMin, yMax, zMin, decorateBlock[2], 1, age); // x0 y1 z0
		impact.proxy.hint_particle(w, xMin, yMin, zMax, decorateBlock[2], 1, age); // x0 y0 z1
		
		impact.proxy.hint_particle(w, xMax, yMin, zMax, decorateBlock[2], 1, age); // x1 y0 z1
		impact.proxy.hint_particle(w, xMin, yMax, zMax, decorateBlock[2], 1, age); // x0 y1 z1
		impact.proxy.hint_particle(w, xMax, yMax, zMin, decorateBlock[2], 1, age); // x1 y1 z0
		
		
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
			if (iAm.isActive() && aTick % 20 == 0) {
				
				if (curY == yMin && curX == xMin && curZ == zMin) {
					return;
				}
				
//				curX--;
//				if (curX < xMin) {
//					curX = xMax;
//					curZ--;
//					if (curZ < zMin) {
//						curZ = zMax;
//						curY--;
//						if (curY < yMin) {
//							curY = yMax;
//						}
//					}
//				}
				
				
				if (curY < yMin) {
					curY = yMax;
				}
				
				if (curY == 0 && curX == 0 && curZ == 0) return;
				
				for (curX = xMax; curX >= xMin; --curX) {
					for (curZ = zMax; curZ >= zMin; --curZ) {
						mineBlock(curX, curY, curZ);
					}
				}
				curY--;
			}
		}
	}
	
	@Override
	public void onFirstTick(IGregTechTileEntity iAm) {
		super.onFirstTick(iAm);
		if (iAm.isServerSide()) {
			
			TileEntity te = iAm.getTileEntityAtSide(iAm.getBackFacing());
			
			if (Loader.isModLoaded("BuildCraft|Core") && te instanceof IAreaProvider) {
				IAreaProvider mark = (IAreaProvider) te;
				Box box = new Box();
				box.initialize(mark);
				mark.removeFromWorld();
				
				xMax = box.xMax;
				yMax = box.yMax;
				zMax = box.zMax;
				
				xMin = box.xMin;
				yMin = box.yMin;
				zMin = box.zMin;
				
				curX = xMax;
				curY = yMax;
				curZ = zMax;
//				int v = (xMax - xMin + 1) * (yMax - yMin + 1) * (zMax - zMin + 1);
//				mEUt = -(v * 10);
			}
		}
	}
	
	@Override
	public void onNotePadRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		super.onNotePadRightClick(aSide, aPlayer, aX, aY, aZ);
		curX = xMax;
		curY = yMax;
		curZ = zMax;
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