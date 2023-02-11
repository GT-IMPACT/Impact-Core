package com.impact.mods.gregtech.tileentities.multi.units;

import com.impact.core.Impact_API;
import com.impact.impact;
import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.enums.Texture;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.network.IPacketString;
import com.impact.util.PositionObject;
import com.impact.util.Utilits;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import java.util.ArrayList;
import java.util.List;

import static com.impact.mods.gregtech.blocks.Build_Casing_Helper.AEROSTATE_PLATFORM;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlock;

public class GTMTE_Aerostat extends GTMTE_Impact_BlockBase<GTMTE_Aerostat> implements IPacketString {
	
	public final static int MAX_BUFFER = 100_000;
	static Block CASING = Casing_Helper.sCaseCore3;
	static int CASING_META = AEROSTATE_PLATFORM.getMeta();
	static ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META + 32];
	static int CASING_TEXTURE_ID = AEROSTATE_PLATFORM.getIDCasing();
	static IStructureDefinition<GTMTE_Aerostat> definition =
			StructureDefinition.<GTMTE_Aerostat>builder()
					.addShape("main", new String[][]{
							{"AAA"},
							{"A~A"},
							{"AAA"},
					})
					.addElement('A', ofBlock(CASING, CASING_META))
					.build();
	public int timer = 20;
	public int curID = 0;
	public boolean firstOpen = true;
	public boolean isFullBuffer = false;
	public int curBuffer = 0;
	public String playerName = "";
	public String aerName = "";
	public List<GTMTE_Aerostat> currentLocationPlatforms = new ArrayList<>();
	
	public GTMTE_Aerostat(int aID, String aNameRegional) {
		super(aID, "impact.multis.aerostat", aNameRegional);
	}
	
	public GTMTE_Aerostat(String aName) {
		super(aName);
	}
	
	public static List<GTMTE_Aerostat> getRadiusAeroStates(String owner, IGregTechTileEntity te) {
		List<GTMTE_Aerostat> as = new ArrayList<>();
		for (String name : Impact_API.sAerostat.keySet()) {
			PositionObject location = new PositionObject(Impact_API.sAerostat.get(name));
			PositionObject thisPos = new PositionObject(te);
			IGregTechTileEntity teTarget = te.getIGregTechTileEntity(location.xPos, location.yPos, location.zPos);
			if (teTarget == null) continue;
			if (teTarget.getMetaTileEntity() instanceof GTMTE_Aerostat) {
				GTMTE_Aerostat gtmte_aerostat = (GTMTE_Aerostat) teTarget.getMetaTileEntity();
				if (gtmte_aerostat.playerName.equals(owner)) {
					int distance = Utilits.distanceBetween3D(thisPos.xPos, location.xPos, thisPos.yPos, location.yPos, thisPos.zPos, location.zPos);
					if (distance >= 16 && distance <= 512) {
						as.add(gtmte_aerostat);
					}
				}
			}
		}
		return as;
	}
	
	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		return aSide == 1 ? new ITexture[]{INDEX_CASE, TextureFactory.of(Texture.Icons.PLATFORM_AEROSTATE_OVERLAY)} : new ITexture[]{INDEX_CASE};
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_Aerostat(this.mName);
	}
	
	public boolean isFacingValid(byte aFacing) {
		return aFacing > 1;
	}
	
	@Override
	public IStructureDefinition<GTMTE_Aerostat> getStructureDefinition() {
		return definition;
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
		mWrench = mScrewdriver = mSoftHammer = mHardHammer = mSolderingTool = mCrowbar = true;
		if (thisController.getYCoord() < 60) {
			return false;
		}
		
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
				
				if (!super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)) {
					if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING) && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
					} else {
						formationChecklist = false;
					}
				}
			}
		}
		
		if (mInputHatches.size() != 1) {
			formationChecklist = false;
		}
		
		return formationChecklist;
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
		super.onPostTick(aBaseMetaTileEntity, aTick);
		if (aBaseMetaTileEntity.isServerSide() && mMachine) {
			if (aTick % 20 == 0 && curBuffer < MAX_BUFFER - 1000) {
				isFullBuffer = false;
			}
			if (!isFullBuffer && (depleteInput(Materials.NatruralGas.getGas(1000)) || depleteInput(Materials.Gas.getGas(1000)))) {
				curBuffer += 1000;
				if (curBuffer + 1000 > MAX_BUFFER) {
					curBuffer    = MAX_BUFFER;
					isFullBuffer = true;
				}
			}
		}
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("aerostate");
		b
				.addInfo("info.0", "Moving over a distance")
				.addInfo("info.1", "Natural gas is used as fuel")
				.addInfo("info.2", "Minimum height (Y coord) for work: 60")
				.addTypeMachine("type", "Moving Machine")
				.addController()
				.sizeStructure(3, 1, 3)
				.addInputHatch(1)
				.addCasingInfo("case.1", "Aerostate Platform Casing", 7)
				.signAndFinalize();
		return b;
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
				impact.proxy.addClientSideChatMessages("Set location name: \"" + aerName + "\"");
				Impact_API.sAerostat.put(aerName, thisLocation.getCoords());
				curID = 1;
			} else {
//				Utilits.sendChatByTE(getBaseMetaTileEntity(), "This name is already in use!");
			}
		} catch (Exception e) {
			e.printStackTrace();
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
//		if (!aerName.equals("")) {
//			Utilits.sendChatByTE(getBaseMetaTileEntity(), "Remove Station: \"" + aerName + "\"");
//		}
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
	
	public int getDistanceTravel() {
		int distance = 0;
		int id = 1;
		for (GTMTE_Aerostat aerostat : currentLocationPlatforms) {
			if (id == curID) {
				PositionObject thisPos = new PositionObject(getBaseMetaTileEntity());
				PositionObject newPos = new PositionObject(aerostat.getBaseMetaTileEntity());
				distance = Utilits.distanceBetween3D(thisPos.xPos, newPos.xPos, thisPos.yPos, newPos.yPos, thisPos.zPos, newPos.zPos);
				if (distance >= 16 && thisPos.dPos == newPos.dPos) {
					break;
				}
			}
			id++;
		}
		return distance;
	}
	
	public void toTravel(EntityPlayer aPlayer) {
		
		int id = 1;
		for (GTMTE_Aerostat aerostat : currentLocationPlatforms) {
			if (id == curID) {
				PositionObject thisPos = new PositionObject(getBaseMetaTileEntity());
				PositionObject newPos = new PositionObject(aerostat.getBaseMetaTileEntity());
				int distance = Utilits.distanceBetween3D(thisPos.xPos, newPos.xPos, thisPos.yPos, newPos.yPos, thisPos.zPos, newPos.zPos);
				if (distance >= 16 && thisPos.dPos == newPos.dPos) {
					if (curBuffer - distance * 25 > 0) {
						curBuffer -= distance * 25;
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
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 1, 0, 1);
	}
	
	@Override
	public void update(String... str) {
		setLocationName(str[0]);
	}
	
	@Override
	public boolean hasSeparate() {
		return false;
	}
}