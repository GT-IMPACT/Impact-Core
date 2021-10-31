package com.impact.mods.gregtech.tileentities.multi.parallelsystem;

import com.github.technus.tectech.mechanics.constructable.IConstructable;
import com.github.technus.tectech.mechanics.structure.IStructureDefinition;
import com.github.technus.tectech.mechanics.structure.StructureDefinition;
import com.github.technus.tectech.thing.metaTileEntity.multi.base.GT_MetaTileEntity_MultiblockBase_EM;
import com.impact.client.gui.GUIHandler;
import com.impact.core.Impact_API;
import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.util.PositionObject;
import com.impact.util.Utilits;
import com.impact.util.string.MultiBlockTooltipBuilder;
import gregtech.api.GregTech_API;
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
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.HashSet;

import static com.github.technus.tectech.mechanics.structure.StructureUtility.*;
import static com.impact.core.Refstrings.MODID;
import static com.impact.mods.gregtech.enums.Texture.Icons.TOWER_OVERLAY;
import static com.impact.mods.gregtech.enums.Texture.Icons.TOWER_OVERLAY_ACTIVE;

public class GTMTE_TowerCommunication extends GT_MetaTileEntity_MultiblockBase_EM implements
		IConstructable {
	
	private static final String[] description = new String[]{
			EnumChatFormatting.RED + "Impact Details:",
			" - Arbitrary structure",
			" - Communication Tower Casing",
			" - Communication Receiver (" + EnumChatFormatting.RED + "Red Point" + EnumChatFormatting.RESET + ")",
	};
	public static Block CASING = Casing_Helper.sCasePage8_3;
	public static byte CASING_META = 6;
	public static ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[8][CASING_META + 64];
	public static int CASING_TEXTURE_ID = CASING_META + 64 + 128 * 8;
	private static final IStructureDefinition<GTMTE_TowerCommunication> STRUCTURE_DEFINITION =
			StructureDefinition.<GTMTE_TowerCommunication>builder()
					.addShape("main", new String[][]{
							{"       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", " EEEEE ", "EE   EE", "E     E", "E     E", "E     E"},
							{"       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "  EEE  ", " EE EE ", " E   E ", " E   E ", " E   E ", "EE   EE", "E     E", "       ", "  AAA  ", "  AAA  "},
							{"       ", "       ", "       ", "       ", "   D   ", "  E E  ", "  E E  ", "  E E  ", "  E E  ", " EE EE ", " E   E ", "       ", "       ", "       ", "E     E", "       ", "       ", " AAAAA ", " AAAAA "},
							{"   E   ", "   E   ", "   E   ", "   E   ", "  DED  ", "   E   ", "       ", "       ", "       ", " E   E ", "       ", "       ", "       ", "       ", "E     E", "       ", "       ", " AA~AA ", " AAAAA "},
							{"       ", "       ", "       ", "       ", "   D   ", "  E E  ", "  E E  ", "  E E  ", "  E E  ", " EE EE ", " E   E ", "       ", "       ", "       ", "E     E", "       ", "       ", " AAAAA ", " AAAAA "},
							{"       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "  EEE  ", " EE EE ", " E   E ", " E   E ", " E   E ", "EE   EE", "E     E", "       ", "  AAA  ", "  AAA  "},
							{"       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", " EEEEE ", "EE   EE", "E     E", "E     E", "E     E"}
					})
					.addElement('A', ofChain(ofHatchAdder(GTMTE_TowerCommunication::addClassicToMachineList, CASING_TEXTURE_ID, CASING, CASING_META), ofBlock(CASING, CASING_META)))
					.addElement('D', ofHatchAdder(GTMTE_TowerCommunication::addCommunicationHatchToMachineList, CASING_TEXTURE_ID, CASING, CASING_META))
					.addElement('E', ofHintDeferred(() -> new IIcon[]{Textures.BlockIcons.FRAMEBOXGT.getIcon(), Textures.BlockIcons.FRAMEBOXGT.getIcon(), Textures.BlockIcons.FRAMEBOXGT.getIcon(), Textures.BlockIcons.FRAMEBOXGT.getIcon(), Textures.BlockIcons.FRAMEBOXGT.getIcon(), Textures.BlockIcons.FRAMEBOXGT.getIcon(),}, Materials.Steel.mRGBa))
					.build();
	public static int frameId = 4096 + Materials.Steel.mMetaItemSubID;
	public static int frameMeta = GregTech_API.METATILEENTITIES[frameId].getTileEntityBaseType();
	public final HashSet<GTMTE_SpaceSatellite_Transmitter> sCommunTransmitter = new HashSet<>();
	public final HashSet<GTMTE_SpaceSatellite_Receiver> sCommunReceiver = new HashSet<>();
	public boolean Stuff;
	public int casingCount = 0;
	public int mFrequency = -1;
	public int mTargetX = 0;
	public int mTargetY = 0;
	public int mTargetZ = 0;
	public int mTargetD = 0;
	public boolean mIsConnect = false;
	
	public GTMTE_TowerCommunication(int aID, String aNameRegional) {
		super(aID, "impact.multis.communicationtower", aNameRegional);
	}
	
	public GTMTE_TowerCommunication(String aName) {
		super(aName);
	}
	
	public boolean addCommunicationHatchToMachineList(IGregTechTileEntity aTileEntity,
													  int aBaseCasingIndex) {
		if (aTileEntity == null) {
			return false;
		} else {
			final IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
			if (aMetaTileEntity == null) {
				return false;
			} else if (aMetaTileEntity instanceof GTMTE_SpaceSatellite_Receiver) {
				((GTMTE_SpaceSatellite_Receiver) aMetaTileEntity).updateTexture(aBaseCasingIndex);
				return sCommunReceiver.add((GTMTE_SpaceSatellite_Receiver) aMetaTileEntity);
			} else if (aMetaTileEntity instanceof GTMTE_SpaceSatellite_Transmitter) {
				((GTMTE_SpaceSatellite_Transmitter) aMetaTileEntity).updateTexture(aBaseCasingIndex);
				return sCommunTransmitter.add((GTMTE_SpaceSatellite_Transmitter) aMetaTileEntity);
			} else {
				return false;
			}
		}
	}
	
	@Override
	protected void soundMagic(ResourceLocation activitySound) {
	}
	
	@Override
	public IStructureDefinition<GTMTE_TowerCommunication> getStructure_EM() {
		return STRUCTURE_DEFINITION;
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_TowerCommunication(mName);
	}
	
	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		return aSide == 1 ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(aActive ? TOWER_OVERLAY_ACTIVE : TOWER_OVERLAY)} : new ITexture[]{INDEX_CASE};
	}
	
	@Override
	public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return false;
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return false;
	}
	
	@Override
	public boolean checkMachine_EM(IGregTechTileEntity iGregTechTileEntity, ItemStack itemStack) {
		sCommunReceiver.clear();
		sCommunTransmitter.clear();
		casingCount = 0;
		return structureCheck_EM("main", 3, 17, 3);
	}
	
	public boolean isFacingValid(byte aFacing) {
		return aFacing > 1;
	}
	
	@Override
	public boolean checkRecipe_EM(ItemStack itemStack) {
		this.mMaxProgresstime    = 1;
		this.mEfficiency         = 10000;
		this.mEfficiencyIncrease = 10000;
		this.mEUt                = 0;
		return true;
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity iAm, long aTick) {
		super.onPostTick(iAm, aTick);
		if (iAm.isServerSide() && aTick % 20 == 0) {
			this.mIsConnect = false;
			iAm.setActive(true);
			if (iAm.isActive()) {
				ArrayList<Boolean> checker = new ArrayList<>();
				boolean active = false;
				for (GTMTE_SpaceSatellite_Receiver ph : sCommunReceiver) {
					checker.add(ph.getIsReceive());
					active = ph.getBaseMetaTileEntity().isActive();
				}
				if (active) this.mIsConnect = checker.stream().filter(b -> b).count() == 4;
			}
		}
		if (iAm.isServerSide() && aTick % 20 * 60 == 0) {
			mWrench = mScrewdriver = mSoftHammer = mHardHammer = mSolderingTool = mCrowbar = true;
		}
	}
	
	@Override
	public String[] getDescription() {
		final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("ttc");
		b
				.addTypeMachine("name", "Communication Tower")
				.addInfo("info.0", "Working radius 256 blocks")
				.addSeparator()
				.addController()
				.addOtherStructurePart("other.0", "Communication Receiver Hatch", "other.1", "Top")
				.addOtherStructurePart("other.2", "Any blocks", "other.3", "Arbitrary structure")
				.addCasingInfo("case", "Tower Casing")
				.signAndFinalize();
		if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			return b.getInformation();
		} else {
			return b.getStructureInformation();
		}
	}
	
	@Override
	public String[] getInfoData() {
		return new String[]{"Frequency: " + mFrequency + ""};
	}
	
	@Override
	public void construct(ItemStack stackSize, boolean hintsOnly) {
		structureBuild_EM("main", 3, 17, 3, hintsOnly, stackSize);
	}
	
	@Override
	public String[] getStructureDescription(ItemStack stackSize) {
		return description;
	}
	
	@Override
	public void onNotePadRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		super.onNotePadRightClick(aSide, aPlayer, aX, aY, aZ);
		IGregTechTileEntity iAm = getBaseMetaTileEntity();
		PositionObject pos = new PositionObject(iAm);
		if (!aPlayer.isSneaking()) {
			aPlayer.openGui(MODID, GUIHandler.GUI_ID_LapTop, iAm.getWorld(), pos.xPos, pos.yPos, pos.zPos);
		}
	}
	
	public void setFrequency(int aFreq, EntityPlayer aPlayer) {
		mFrequency = aFreq;
		Impact_API.sCommunicationTower.put(Utilits.inToStringUUID(aFreq, aPlayer), new PositionObject(getBaseMetaTileEntity()).getCoords());
		GT_Utility.sendChatToPlayer(aPlayer, "Frequency: " + aFreq);
	}
	
	public void setCoord(PositionObject pos) {
		this.mTargetX = pos.xPos;
		this.mTargetY = pos.yPos;
		this.mTargetZ = pos.zPos;
		this.mTargetD = pos.dPos;
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setInteger("mTargetX", this.mTargetX);
		aNBT.setInteger("mTargetY", this.mTargetY);
		aNBT.setInteger("mTargetZ", this.mTargetZ);
		aNBT.setInteger("mTargetD", this.mTargetD);
		aNBT.setInteger("mFrequency", this.mFrequency);
		aNBT.setBoolean("mIsReceive", this.mIsConnect);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		this.mTargetX   = aNBT.getInteger("mTargetX");
		this.mTargetY   = aNBT.getInteger("mTargetY");
		this.mTargetZ   = aNBT.getInteger("mTargetZ");
		this.mTargetD   = aNBT.getInteger("mTargetD");
		this.mFrequency = aNBT.getInteger("mFrequency");
		this.mIsConnect = aNBT.getBoolean("mIsReceive");
	}
}