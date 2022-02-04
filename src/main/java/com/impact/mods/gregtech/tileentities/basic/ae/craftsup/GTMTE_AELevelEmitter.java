package com.impact.mods.gregtech.tileentities.basic.ae.craftsup;

import appeng.api.config.Actionable;
import appeng.api.networking.GridFlags;
import appeng.api.networking.IGridNode;
import appeng.api.networking.crafting.ICraftingLink;
import appeng.api.networking.crafting.ICraftingRequester;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.networking.security.IActionHost;
import appeng.api.networking.storage.IBaseMonitor;
import appeng.api.storage.IMEMonitorHandlerReceiver;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.util.DimensionalCoord;
import appeng.me.GridAccessException;
import appeng.me.helpers.AENetworkProxy;
import appeng.me.helpers.IGridProxyable;
import appeng.util.item.AEItemStack;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.impact.client.gui.GUIHandler;
import com.impact.mods.gregtech.tileentities.basic.implement.GTMTE_AbstractLogic;
import com.impact.network.IPacketInteger;
import com.impact.network.special.SupplierAEPacket;
import com.impact.util.Utilits;
import cpw.mods.fml.common.Optional;
import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.gui.GT_GUIContainerMetaTile_Machine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static gregtech.api.enums.GT_Values.RES_PATH_GUI;

public class GTMTE_AELevelEmitter extends GTMTE_AbstractLogic
		implements IGridProxyable, IActionHost, IMEMonitorHandlerReceiver<IAEItemStack>, IPacketInteger, ICraftingRequester {
	
	private static final int IN_SLOTS = 27, OUT_SLOTS = 0, TIER = 4, AMPERAGE = 1;
	private static final String GUI_NAME = "";
	private final BaseActionSource mySrc;
	private final Map<IAEItemStack, Suppliers.BoundProperty> boundProperties;
	private final List<Suppliers.Request> pendingRequests;
	private AENetworkProxy gridProxy;
	private CraftingHandler handler;
	private int update;
	
	public GTMTE_AELevelEmitter(int aID, String aNameRegional) {
		super(aID, "impact.ae2.level_emitter", aNameRegional, TIER, AMPERAGE, IN_SLOTS, OUT_SLOTS, GUI_NAME);
		this.mySrc           = null;
		this.gridProxy       = null;
		this.boundProperties = Maps.newLinkedHashMap();
		this.pendingRequests = Lists.newLinkedList();
		this.handler         = null;
		this.update          = 0;
	}
	
	protected GTMTE_AELevelEmitter(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
		super(aName, aTier, aDescription, aTextures, AMPERAGE, IN_SLOTS, OUT_SLOTS, GUI_NAME);
		this.gridProxy       = null;
		this.boundProperties = Maps.newLinkedHashMap();
		this.handler         = null;
		this.pendingRequests = Lists.newLinkedList();
		this.update          = 0;
		this.mySrc           = new BaseActionSource();
	}
	
	@Override
	public String[] getDescription() {
		return new String[] {
				"KEK"
		};
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_AELevelEmitter(mName, mTier, mDescription, mTextures);
	}
	
	@Override
	public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
		super.onFirstTick(aBaseMetaTileEntity);
		handler = new CraftingHandler(this, 10);
		getProxy();
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
		
		if (aBaseMetaTileEntity.isClientSide()) {
			return;
		}
		if (aTick % 20L == 0L) {
			try {
				this.updateCraftingStatus();
			} catch (GridAccessException e) {
				e.printStackTrace();
			}
		}
		if (aTick % 300L == 0L) {
			this.checkAllProperties();
		}
		if (--this.update == 0) {
			try {
				this.getProxy().getStorage().getItemInventory().addListener(this, this.getProxy().getGrid());
			} catch (GridAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void updateCraftingStatus() throws GridAccessException {
		final Iterator<Suppliers.Request> iterator = this.pendingRequests.iterator();
		while (iterator.hasNext()) {
			final Suppliers.Request request = iterator.next();
			if (this.handler.handleCrafting(request.channel, request.amount, request.stack,
					this.getBaseMetaTileEntity().getWorld(), this.getProxy().getGrid(), this.getProxy().getCrafting(), this.mySrc
			)) {
				iterator.remove();
				if (!this.boundProperties.containsKey(request.stack)) {
					continue;
				}
				this.checkProperty(this.boundProperties.get(request.stack));
			}
		}
	}
	
	private void checkAllProperties() {
		for (final Map.Entry<IAEItemStack, Suppliers.BoundProperty> entry : this.boundProperties.entrySet()) {
			this.checkProperty(entry.getValue());
		}
	}
	
	private void addCraft(final IAEItemStack stack) throws GridAccessException {
		if (!this.boundProperties.containsKey(stack)) {
			return;
		}
		final Suppliers.BoundProperty params = this.boundProperties.get(stack);
		this.handler.handleCrafting(params.channel, params.orderCount, params.stack.copy(),
				this.getBaseMetaTileEntity().getWorld(), this.getProxy().getGrid(), this.getProxy().getCrafting(), this.mySrc
		);
		this.pendingRequests.add(new Suppliers.Request(stack, params.channel, params.orderCount));
	}
	
	@Override
	public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new CONTAINER(aPlayerInventory, aBaseMetaTileEntity);
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GUI(aPlayerInventory, aBaseMetaTileEntity, "RECIPE SUP 3000");
	}

	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		NBTTagCompound map = aNBT.getCompoundTag("map");
		if (map != null) {
			int size = map.getInteger("size");
			
			for (int index = 0; index < size; ++index) {
				IAEItemStack stack = AEItemStack.loadItemStackFromNBT(map.getCompoundTag("s" + index));
				int channel = map.getInteger("c" + index);
				int makeCount = map.getInteger("mc" + index);
				int orderCount = map.getInteger("oc" + index);
				this.boundProperties.put(stack, new Suppliers.BoundProperty(stack, makeCount, orderCount, channel));
			}
		}
		NBTTagCompound handler = aNBT.getCompoundTag("handler");
		if (handler != null) {
			this.handler.readFromNBT(handler);
		}
	}

	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		NBTTagCompound map = new NBTTagCompound();
		map.setInteger("size", this.boundProperties.size());
		int index = 0;
		
		for (Iterator<Map.Entry<IAEItemStack, Suppliers.BoundProperty>> var4 = this.boundProperties.entrySet().iterator(); var4.hasNext(); ++index) {
			Map.Entry<IAEItemStack, Suppliers.BoundProperty> entry = var4.next();
			NBTTagCompound stack = new NBTTagCompound();
			entry.getKey().writeToNBT(stack);
			map.setTag("s" + index, stack);
			map.setInteger("c" + index, entry.getValue().channel);
			map.setInteger("mc" + index, entry.getValue().makeOrderAmount);
			map.setInteger("oc" + index, entry.getValue().orderCount);
		}
		
		aNBT.setTag("map", map);
		NBTTagCompound handler = new NBTTagCompound();
		this.handler.writeToNBT(handler);
		aNBT.setTag("handler", handler);
	}
	
	@Override
	public void update(int... integer) {
		EntityPlayer player = this.getBaseMetaTileEntity().getWorld().getPlayerEntityByName(this.getBaseMetaTileEntity().getOwnerName());
		try {
			int slotId = integer[0] <= IN_SLOTS ? integer[0] : 0;
			int minBound = integer[1];
			int requestCraftAmount = integer[2];
			ItemStack item = this.getStackInSlot(slotId);
			Suppliers.BoundProperty params = new Suppliers.BoundProperty(AEItemStack.create(item), minBound, requestCraftAmount, slotId);
			this.boundProperties.put(params.stack, params);
			if (player == null) {
				player = this.getBaseMetaTileEntity().getWorld().getClosestPlayer(this.getBaseMetaTileEntity().getXCoord(),
						this.getBaseMetaTileEntity().getYCoord(), this.getBaseMetaTileEntity().getZCoord(), 199.0D
				);
				if (player != null) {
					GT_Utility.sendChatToPlayer(player, "added recipe for: " + item.getDisplayName() +
							" in slot " + slotId + " with min and craft values " + minBound + "," + requestCraftAmount);
				}
			}
			
		} catch (Exception e) {
			if (player != null) {
				GT_Utility.sendChatToPlayer(player, "invalid input");
			}
			e.printStackTrace();
		}
	}
	
	public String[] getInfoData() {
		List<String> info = new ArrayList<>();
		try {
			AtomicInteger i = new AtomicInteger(1);
			boundProperties.forEach((stack, property) -> {
				info.add("#" + i.get() + " - " + stack.getItemStack().getDisplayName() + " (bound: " + property.makeOrderAmount + ", make: " + property.orderCount + ")");
				i.getAndIncrement();
			});
		} catch (Exception e) {}
		
		info.add("pending requests: " + this.pendingRequests.size());
		if (this.getProxy() != null) {
			try {
				if (this.getProxy().getStorage().getItemInventory().getStorageList().size() > 0) {
					info.add("is connected");
				}
			} catch (GridAccessException var3) {
				var3.printStackTrace();
			}
		}
		return info.toArray(new String[0]);
	}
	
	public boolean isGivingInformation() {
		return true;
	}
	
	
	//AE2
	
	@Override
	@Optional.Method(modid = "appliedenergistics2")
	public AENetworkProxy getProxy() {
		if (gridProxy == null) {
			gridProxy = new AENetworkProxy(this, "proxy", getStackForm(1), true);
			gridProxy.onReady();
			gridProxy.setFlags(GridFlags.REQUIRE_CHANNEL);
		}
		return this.gridProxy;
	}
	
	@Override
	@Optional.Method(modid = "appliedenergistics2")
	public DimensionalCoord getLocation() {
		IGregTechTileEntity gtm = this.getBaseMetaTileEntity();
		return new DimensionalCoord(gtm.getWorld(), gtm.getXCoord(), gtm.getYCoord(), gtm.getZCoord());
	}
	
	@Optional.Method(modid = "appliedenergistics2")
	public IGridNode getGridNode(ForgeDirection forgeDirection) {
		AENetworkProxy gp = getProxy();
		return gp != null ? gp.getNode() : null;
	}
	
	@Override
	@Optional.Method(modid = "appliedenergistics2")
	public void securityBreak() {}
	
	@Override
	@Optional.Method(modid = "appliedenergistics2")
	public IGridNode getActionableNode() {
		AENetworkProxy gp = getProxy();
		return gp != null ? gp.getNode() : null;
	}
	
	@Override
	public boolean isValid(Object token) {
		try {
			return this.getProxy().getGrid() == token;
		} catch (GridAccessException e) {
			return false;
		}
	}
	
	@Override
	public void postChange(IBaseMonitor<IAEItemStack> iBaseMonitor, Iterable<IAEItemStack> iterable, BaseActionSource baseActionSource) {
		for (final IAEItemStack stack : iterable) {
			if (stack.getStackSize() < 0L && this.boundProperties.containsKey(stack)) {
				final Suppliers.BoundProperty property = this.boundProperties.get(stack);
				this.checkProperty(property);
			}
		}
	}
	
	private void checkProperty(final Suppliers.BoundProperty params) {
		try {
			final IAEItemStack meStack = this.getProxy().getStorage().getItemInventory().getStorageList().findPrecise(params.stack);
			if (meStack == null || meStack.getStackSize() < params.makeOrderAmount) {
				this.addCraft(meStack);
			}
		} catch (GridAccessException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void gridChanged() {
		super.gridChanged();
		this.update = 20;
	}
	
	@Override
	public void onListUpdate() {
	
	}
	
	public ImmutableSet<ICraftingLink> getRequestedJobs() {
		return this.handler.getRequestedJobs();
	}
	
	@Override
	public IAEItemStack injectCraftedItems(ICraftingLink iCraftingLink, IAEItemStack iaeItemStack, Actionable actionable) {
		return iaeItemStack;
	}
	
	@Override
	public void jobStateChange(ICraftingLink link) {
		int x = this.handler.jobStateChange(link);
		if (x >= 0) {
			this.pendingRequests.removeIf((req) -> req.channel == x);
		}
	}
	
	class GUI extends GT_GUIContainerMetaTile_Machine {
		
		public String mName;
		
		public GUI(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, String name) {
			super(new CONTAINER(aInventoryPlayer, aTileEntity), RES_PATH_GUI + "OneStackRegulateChest.png");
			mName = name;
		}
		
		@Override
		protected void drawGuiContainerForegroundLayer(int par1, int par2) {
			fontRendererObj.drawString(mName, 8, 6, 4210752);
			fontRendererObj.drawString("Inventory", 8, 73, 4210752);
		}
		
		@Override
		protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
			super.drawGuiContainerBackgroundLayer(par1, par2, par3);
			int x = (width - xSize) / 2;
			int y = (height - ySize) / 2;
			drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		}
	}
	
	class CONTAINER extends GT_ContainerMetaTile_Machine {
		
		public CONTAINER(InventoryPlayer inventoryPlayer, IGregTechTileEntity te) {
			super(inventoryPlayer, te);
		}
		
		@Override
		public void addSlots(InventoryPlayer aInventoryPlayer) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 9; j++) {
					addSlotToContainer(new SLOT(mTileEntity, j + i * 9, 8 + j * 18, 17 + i * 18, true));
				}
			}
		}
		
		@Override
		public int getSlotCount() {
			return 27;
		}
		
		@Override
		public int getShiftClickSlotCount() {
			return 27;
		}
		
		@Override
		public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold, EntityPlayer aPlayer) {
			GTMTE_AELevelEmitter te = (GTMTE_AELevelEmitter) mTileEntity.getMetaTileEntity();
			if (aSlotIndex > te.mInventory.length) {
				return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
			}
			try {
				ItemStack stackInSlot = te.getStackInSlot(aSlotIndex);
				if (aMouseclick == 1) {
					if (stackInSlot != null) {
						te.boundProperties.remove(AEItemStack.create(stackInSlot));
						te.mInventory[aSlotIndex] = null;
						return null;
					}
				}
				if (aMouseclick == 0) {
					if (stackInSlot != null) {
						Utilits.openGui(aPlayer, GUIHandler.GUI_AE, mTileEntity);
						if (mTileEntity.isServerSide()) {
							int bound, make;
							Suppliers.BoundProperty pr = te.boundProperties.get(AEItemStack.create(stackInSlot));
							if (pr != null) {
								bound = pr.makeOrderAmount;
								make = pr.orderCount;
							} else {
								bound = 1;
								make = 1;
							}
							new SupplierAEPacket(aSlotIndex, stackInSlot, bound, make).sendToClients();
						}
					}
				}
			} catch (Exception e) {
				return null;
			}
			return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
		}
		
		class SLOT extends Slot {
			public final int mSlotIndex;
			public boolean mCanInsertItem;
			public boolean mCanStackItem;
			public int mMaxStacksize = 127;
			
			public SLOT(IInventory par1iInventory, int par2, int par3, int par4, boolean aCanInsertItem) {
				super(par1iInventory, par2, par3, par4);
				this.mCanInsertItem = aCanInsertItem;
				this.mSlotIndex     = par2;
			}
			
			public boolean isItemValid(ItemStack par1ItemStack) {
				return this.mCanInsertItem;
			}
			
			public int getSlotStackLimit() {
				return 0;
			}
			
			public boolean getHasStack() {
				return false;
			}
			
			public ItemStack decrStackSize(int par1) {
				return null;
			}
			
			public void putStack(ItemStack stack) {
				super.putStack(stack);
			}
			
			public boolean canTakeStack(EntityPlayer par1EntityPlayer) {
				return false;
			}
		}
	}
}