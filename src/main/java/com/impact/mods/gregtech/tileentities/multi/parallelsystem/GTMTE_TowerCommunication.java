package com.impact.mods.gregtech.tileentities.multi.parallelsystem;

import com.impact.api.satellite.IDistributor;
import com.impact.api.satellite.IReceiver;
import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.util.string.MultiBlockTooltipBuilder;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_LanguageManager;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import org.jetbrains.annotations.NotNull;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.impact.mods.gregtech.enums.Texture.Icons.TOWER_OVERLAY;
import static com.impact.mods.gregtech.enums.Texture.Icons.TOWER_OVERLAY_ACTIVE;
import static com.impact.util.multis.GT_StructureUtility.ofFrame;
import static com.impact.util.multis.GT_StructureUtility.ofHatchAdder;
import static space.impact.api.multiblocks.structure.StructureUtility.*;

public class GTMTE_TowerCommunication extends GTMTE_Impact_BlockBase<GTMTE_TowerCommunication> implements IDistributor {
	
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
					.addElement('A', ofChain(
							ofHatchAdder(GTMTE_TowerCommunication::addToMachineList, CASING_TEXTURE_ID, CASING, CASING_META),
							ofBlock(CASING, CASING_META)
					))
					.addElement('D', ofHatchAdder(GTMTE_TowerCommunication::addCommunicationHatchToMachineList, CASING_TEXTURE_ID, CASING, CASING_META))
					.addElement('E', lazy(t -> ofFrame(Materials.Steel)))
					.build();
	
	private final HashSet<IReceiver> receivers = new HashSet<>();
	public final HashSet<GTMTE_CommunicationTower_Receiver> sCommunReceiver = new HashSet<>();
	private int timeOutCheckArea;
	
	public GTMTE_TowerCommunication(int aID, String aNameRegional) {
		super(aID, "impact.multis.communicationtower", aNameRegional);
	}
	
	public GTMTE_TowerCommunication(String aName) {
		super(aName);
	}
	
	public boolean addCommunicationHatchToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
		if (aTileEntity == null) {
			return false;
		} else {
			final IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
			if (aMetaTileEntity == null) {
				return false;
			} else if (aMetaTileEntity instanceof GTMTE_CommunicationTower_Receiver) {
				((GTMTE_CommunicationTower_Receiver) aMetaTileEntity).updateTexture(aBaseCasingIndex);
				return sCommunReceiver.add((GTMTE_CommunicationTower_Receiver) aMetaTileEntity);
			} else {
				return false;
			}
		}
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_TowerCommunication(mName);
	}
	
	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		return aSide == 1 ? new ITexture[]{INDEX_CASE, TextureFactory.of(aActive ? TOWER_OVERLAY_ACTIVE : TOWER_OVERLAY)} : new ITexture[]{INDEX_CASE};
	}
	private boolean isConnected;
	
	@Override
	public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return null;
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return null;
	}
	
	@Override
	public boolean machineStructure(IGregTechTileEntity thisController) {
		boolean formationCheckList = checkPiece(3, 17, 3);
		if (sCommunReceiver.size() != 4) {
			formationCheckList = false;
		}
		return formationCheckList;
	}
	
	public boolean isFacingValid(byte aFacing) {
		return aFacing > 1;
	}
	
	@Override
	public boolean checkRecipe(ItemStack itemStack) {
		this.mMaxProgresstime    = 20;
		this.mEfficiency         = 10000;
		this.mEfficiencyIncrease = 10000;
		this.mEUt                = 0;
		return true;
	}
	
	@Override
	public void clearHatches() {
		sCommunReceiver.clear();
		super.clearHatches();
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("ttc");
		b
				.addTypeMachine("name", "Communication Tower")
				.addInfo("info.0", "Working radius 256 blocks")
				.addSeparator()
				.addController()
				.addOtherStructurePart("other.0", "Communication Receiver Hatch", "other.1", "Top")
				.addOtherStructurePart("other.2", "Steel Frame Box", "other.3", "Frames")
				.addCasingInfo("case", "Tower Casing", 41)
				.signAndFinalize();
		return b;
	}
	
	@Override
	public IStructureDefinition<GTMTE_TowerCommunication> getStructureDefinition() {
		return STRUCTURE_DEFINITION;
	}
	
	@Override
	public String[] getInfoData() {
		
		List<String> list = new ArrayList<>();
		list.add("Timeout rescan: " + timeOutCheckArea + "s");
		list.add("Connection: " + (isConnected ? "YES" : "NO"));
		
		int index = 0;
		for (IReceiver receiver : receivers) {
			if (receiver instanceof IMetaTileEntity) {
				index++;
				IMetaTileEntity mte = (IMetaTileEntity) receiver;
				String name = GT_LanguageManager.getTranslation("gt.blockmachines." + mte.getMetaName() + ".name");
				IGregTechTileEntity te = mte.getBaseMetaTileEntity();
				String title = String.format("%d. %s (X: %d, Y: %d, Z: %d)", index, name, te.getXCoord(), te.getYCoord(), te.getZCoord());
				list.add(title);
			}
		}
		
		return list.toArray(new String[0]);
	}
	
	@Override
	public void construct(ItemStack stackSize, boolean hintsOnly) {
		buildPiece(stackSize, hintsOnly, 3, 17, 3);
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setBoolean("isConnected", isConnected);
		aNBT.setInteger("timeOutCheckArea", timeOutCheckArea);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		isConnected      = aNBT.getBoolean("isConnected");
		timeOutCheckArea = aNBT.getInteger("timeOutCheckArea");
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity iAm, long aTick) {
		super.onPostTick(iAm, aTick);
		if (iAm.isServerSide() && aTick % 100 == 0) {
			
			
			ArrayList<Boolean> checker = new ArrayList<>();
			
			for (GTMTE_CommunicationTower_Receiver ph : sCommunReceiver) {
				checker.add(ph.hasConnected() && ph.getBaseMetaTileEntity().isActive());
			}
			boolean isConnect = checker.stream().filter(b -> b).count() == 4 && iAm.isActive();
			if (isConnect != isConnected) {
				isConnected = isConnect;
				notifyConnections();
			}
		}
		
		if (iAm.isServerSide() && aTick % 20 == 0 && isConnected) {
			if (--timeOutCheckArea <= 0) {
				checkZoneAndNotify();
				timeOutCheckArea = 30;
			}
		}
		
		if (iAm.isServerSide() && aTick % 20 * 60 == 0) {
			noMaintenance();
		}
	}
	
	@Override
	public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
		super.onFirstTick(aBaseMetaTileEntity);
		checkZoneAndNotify();
	}
	
	private void checkZoneAndNotify() {
		IGregTechTileEntity te = getBaseMetaTileEntity();
		
		if (te != null) {
			World w = te.getWorld();
			Chunk ch = w.getChunkFromBlockCoords(te.getXCoord(), te.getZCoord());
			
			int xChunk = ch.xPosition;
			int zChunk = ch.zPosition;
			
			receivers.clear();
			
			for (int x = -8; x <= 8; x++) {
				for (int z = -8; z <= 8; z++) {
					ch = w.getChunkFromChunkCoords(xChunk + x, zChunk + z);
					for (Object value : ch.chunkTileEntityMap.values()) {
						TileEntity tile = (TileEntity) value;
						
						if (tile instanceof IGregTechTileEntity) {
							IGregTechTileEntity gte = (IGregTechTileEntity) tile;
							
							if (gte.getMetaTileEntity() != null && gte.getMetaTileEntity() instanceof IReceiver) {
								IReceiver receiver = (IReceiver) gte.getMetaTileEntity();
								if (receiver.isValid()) {
									receivers.add(receiver);
								}
							}
						}
						
						if (tile instanceof IReceiver) {
							IReceiver receiver = (IReceiver) tile;
							if (receiver.isValid()) {
								receivers.add(receiver);
							}
						}
					}
				}
			}
		}
		notifyConnections();
	}
	
	@Override
	public void notifyConnections() {
		for (IReceiver receiver : receivers) {
			receiver.createConnect(this);
			receiver.updateConnectionStatus(isConnected);
		}
	}
	
	@NotNull
	@Override
	public Set<IReceiver> getConnections() {
		return receivers;
	}
	
	@Override
	public void disconnect(@NotNull IReceiver connection) {
		receivers.remove(connection);
		checkZoneAndNotify();
	}
	
	public boolean getConnectionStatus() {
		return isConnected;
	}
	
	@Override
	public boolean hasSeparate() {
		return false;
	}
}