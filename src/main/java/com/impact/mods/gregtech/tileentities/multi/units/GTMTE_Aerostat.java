package com.impact.mods.gregtech.tileentities.multi.units;

import com.github.technus.tectech.mechanics.alignment.enumerable.ExtendedFacing;
import com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer;
import com.github.technus.tectech.mechanics.structure.IStructureDefinition;
import com.github.technus.tectech.mechanics.structure.StructureDefinition;
import com.impact.core.Impact_API;
import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.enums.Texture;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.network.ImpactNetwork;
import com.impact.network.ImpactPacketStringArray;
import com.impact.util.PositionObject;
import com.impact.util.Utilits;
import com.impact.util.vector.Structure;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import cpw.mods.fml.common.network.NetworkRegistry;
import gregtech.api.enums.Materials;
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
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.*;

import static com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer.registerMetaClass;
import static com.github.technus.tectech.mechanics.structure.StructureUtility.ofBlock;
import static com.impact.loader.ItemRegistery.SpaceElevatorBlock;
import static com.impact.mods.gregtech.blocks.Build_Casing_Helper.AEROSTATE_PLATFORM;
import static com.impact.mods.gregtech.blocks.Build_Casing_Helper.ME_CASING;

public class GTMTE_Aerostat extends GT_MetaTileEntity_MultiParallelBlockBase {
	
	public final static int MAX_BUFFER = 50_000;
	public int timer = 20;
	public int curID = 0;
	public boolean firstOpen = true;
	public boolean isFullBuffer = false;
	public int curBuffer = 0;
	public String playerName = "";
	public String aerName = "";
	Block CASING = Casing_Helper.sCaseCore3;
	int CASING_META = AEROSTATE_PLATFORM.getMeta();
	ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META + 32];
	int CASING_TEXTURE_ID = AEROSTATE_PLATFORM.getIDCasing();
	
	public GTMTE_Aerostat(int aID, String aNameRegional) {
		super(aID, "impact.multis.aerostat", aNameRegional);
		holo();
	}
	
	public GTMTE_Aerostat(String aName) {
		super(aName);
	}
	
	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		return aSide == 1 ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(Texture.Icons.PLATFORM_AEROSTATE_OVERLAY)} : new ITexture[]{INDEX_CASE};
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_Aerostat(this.mName);
	}
	
	public boolean isFacingValid(byte aFacing) {
		return aFacing > 1;
	}
	
	@Override
	public String[] getDescription() {
		return null;
	}
	
	public void holo() {
		registerMetaClass(GTMTE_Aerostat.class, new IMultiblockInfoContainer<GTMTE_Aerostat>() {
			//region Structure
			private final IStructureDefinition<GTMTE_Aerostat> definition =
					StructureDefinition.<GTMTE_Aerostat>builder()
							.addShape("main", new String[][]{
									{"AAA"},
									{"A~A"},
									{"AAA"},
							})
							.addElement('A', ofBlock(CASING, CASING_META))
							.addElement('B', ofBlock(SpaceElevatorBlock))
							.build();
			private final String[] desc = new String[]{
					EnumChatFormatting.RED + "Impact Details:",
					"- Space Elevator Casing",
					"- Space Elevator Hawser",
					"- Hatches (any Space Elevator Casing)",
			};
			//endregion
			
			@Override
			public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_Aerostat tileEntity, ExtendedFacing aSide) {
				IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
				definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide, base.getXCoord(), base.getYCoord(), base.getZCoord(), 1, 0, 1, hintsOnly);
			}
			
			@Override
			public String[] getDescription(ItemStack stackSize) {
				return desc;
			}
		});
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return null;
	}
	
	@Override
	public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return null;
	}
	
	@Override
	public boolean machineStructure(IGregTechTileEntity thisController) {
		
//		if (thisController.getYCoord() < 100) {
//			return false;
//		}
		
		final Vector3ic forgeDirection = new Vector3i(
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ
		);
		
		boolean formationChecklist = true;
		
		for (int X = -1; X <= 1; X++) {
			for (int Z = -1; Z <= 1; Z++) {
				if (X == 0 && Z == 0) {
					continue;
				}
				final Vector3ic offset = rotateOffsetVector(forgeDirection, X, 0, Z);
				IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				
				//Utilits.setBlock(thisController, offset.x(), offset.y(), offset.z(), CASING, CASING_META);
				
				if (!super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {
					if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING) && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
					} else {
						formationChecklist = false;
					}
				}
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
	
	@Override
	public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
		super.onPostTick(aBaseMetaTileEntity, aTick);
		if (aBaseMetaTileEntity.isServerSide() && mMachine) {
			
			if (aTick % 20 == 0 && curBuffer < MAX_BUFFER - 100) {
				isFullBuffer = false;
			}
			if (!isFullBuffer && depleteInput(Materials.Gas.getGas(100))) {
				curBuffer += 100;
				if (curBuffer + 100 > MAX_BUFFER) {
					curBuffer    = MAX_BUFFER;
					isFullBuffer = true;
				}
			}
		}
	}
	
	public static List<GTMTE_Aerostat> getRadiusAeroStates(String owner, IGregTechTileEntity te) {
		List<GTMTE_Aerostat> as = new ArrayList<>();
		for (String name : Impact_API.sAerostat.keySet()) {
			PositionObject location = new PositionObject(Impact_API.sAerostat.get(name));
			PositionObject thisPos = new PositionObject(te);
			IGregTechTileEntity teTarget = Structure.getIGTEno(te, location.toVec3());
			if (teTarget.getMetaTileEntity() instanceof GTMTE_Aerostat) {
				GTMTE_Aerostat gtmte_aerostat = (GTMTE_Aerostat) teTarget.getMetaTileEntity();
				if (gtmte_aerostat.playerName.equals(owner)) {
					int distance = Utilits.distanceBetween3D(thisPos.xPos, location.xPos, thisPos.yPos, location.yPos, thisPos.zPos, location.zPos);
					if (distance >= 16 && distance <= 256) {
						as.add(gtmte_aerostat);
					}
				}
			}
		}
		return as;
	}
	
	@Override
	public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
		return mMachine && super.onRightclick(aBaseMetaTileEntity, aPlayer);
	}
	
	public void setLocationName(String name) {
		try {
			PositionObject thisLocation = new PositionObject(getBaseMetaTileEntity());
			if (!Impact_API.sAerostat.containsKey(name)) {
				Impact_API.sAerostat.remove(aerName);
				aerName = name;
				Utilits.sendChatByTE(getBaseMetaTileEntity(), "Set location name: \"" + aerName + "\"");
				Impact_API.sAerostat.put(aerName, thisLocation.getCoords());
				curID = 1;
			} else {
				Utilits.sendChatByTE(getBaseMetaTileEntity(), "Такое название уже используется!");
			}
		} catch (Exception e) {
			Utilits.sendChatByTE(getBaseMetaTileEntity(), "CRASH!");
		}
	}
	
	@Override
	public void onFirstTick(IGregTechTileEntity te) {
		super.onFirstTick(te);
		if (te.isServerSide()) {
			if (!te.getOwnerName().isEmpty()) {
				playerName = te.getOwnerName();
			}
			if (!aerName.isEmpty()) {
				setLocationName(aerName);
			}
		}
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setInteger("timer", timer);
		aNBT.setInteger("curBuffer", curBuffer);
		aNBT.setInteger("curID", curID);
		aNBT.setString("aerName", aerName);
		aNBT.setString("playerName", playerName);
		aNBT.setBoolean("firstOpen", firstOpen);
	}
	
	@Override
	public void inValidate() {
		Impact_API.sAerostat.remove(aerName);
		if (!aerName.equals("")) { // TODO: 31.10.2021 debug
			Utilits.sendChatByTE(getBaseMetaTileEntity(), "Remove Station: \"" + aerName + "\"");
		}
		super.inValidate();
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		timer      = aNBT.getInteger("timer");
		curID      = aNBT.getInteger("curID");
		curBuffer  = aNBT.getInteger("curBuffer");
		firstOpen  = aNBT.getBoolean("firstOpen");
		aerName    = aNBT.getString("aerName");
		playerName = aNBT.getString("playerName");
	}
	
	@Override
	public void onNotePadRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		super.onNotePadRightClick(aSide, aPlayer, aX, aY, aZ);
		if (aPlayer.capabilities.isCreativeMode) {
			for (String name : Impact_API.sAerostat.keySet()) {
				GT_Utility.sendChatToPlayer(aPlayer, "Name: " + EnumChatFormatting.RED + name);
			}
		}
	}
	
	@Override
	public boolean checkRecipe(ItemStack itemStack) {
		return false;
	}
	
	public void toTravel(EntityPlayer aPlayer) {

		int id = 1;
		for (GTMTE_Aerostat aerostat : getRadiusAeroStates(playerName, getBaseMetaTileEntity())) {
			if (id == curID) {
				PositionObject thisPos = new PositionObject(getBaseMetaTileEntity());
				PositionObject newPos = new PositionObject(aerostat.getBaseMetaTileEntity());
				int distance = Utilits.distanceBetween3D(thisPos.xPos, newPos.xPos, thisPos.yPos, newPos.yPos, thisPos.zPos, newPos.zPos);
				if (distance >= 16 && thisPos.dPos == newPos.dPos) {
					if (curBuffer - distance * 100 > 0) {
						curBuffer -= distance * 100;
						aPlayer.setPositionAndUpdate(newPos.xPos + 0.5D, newPos.yPos + 1, newPos.zPos + 0.5D);
					} else {
						GT_Utility.sendChatToPlayer(aPlayer, "No Fuel");
					}
				} else {
					GT_Utility.sendChatToPlayer(aPlayer, "Error Distance");
				}
				return;
			}
			id++;
		}
	}
	
	public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
		GT_Utility.sendChatToPlayer(aPlayer, "Owner " + getBaseMetaTileEntity().getOwnerName());
		GT_Utility.sendChatToPlayer(aPlayer, "NameCustom " + playerName);
	}
}