package com.impact.mods.gregtech.tileentities.basic;

import com.impact.util.ItemStack_NoNBT;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_Container_BasicTank;
import gregtech.api.gui.GT_GUIContainerMetaTile_Machine;
import gregtech.api.gui.GT_Slot_Holo;
import gregtech.api.gui.GT_Slot_Output;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicMachine;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Utility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.awt.*;
import java.util.*;
import java.util.List;

import static com.impact.core.Refstrings.MODID;
import static com.impact.mods.gregtech.enums.Texture.Icons.READER_OFFLINE;
import static com.impact.mods.gregtech.enums.Texture.Icons.READER_ONLINE;
import static gregtech.api.enums.GT_Values.V;


public class GTMTE_DataReader extends GT_MetaTileEntity_BasicMachine {
	
	private static final HashMap<ItemStack_NoNBT, ArrayList<IDataRender>> RENDER_REGISTRY = new HashMap<>();
	
	public GTMTE_DataReader(int aID, String aNameRegional) {
		super(aID, "impact.basic.datareader", aNameRegional, 5, 1, "", 1, 1, "dataReader.png", "");
		run();
	}
	
	public GTMTE_DataReader(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
		super(aName, aTier, 1, aDescription, aTextures, 1, 1, "dataReader.png", "");
	}
	
	public static void addDataRender(ItemStack_NoNBT stack, IDataRender render) {
		ArrayList<IDataRender> renders = RENDER_REGISTRY.computeIfAbsent(stack, k -> new ArrayList<>());
		if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
			render.loadResources();
		}
		renders.add(render);
	}
	
	public static List<IDataRender> getRenders(ItemStack_NoNBT stack) {
		ArrayList<IDataRender> iDataRenders = RENDER_REGISTRY.get(stack);
		return iDataRenders == null ? Collections.emptyList() : iDataRenders;
	}
	
	public static void run() {
		addDataRender(new ItemStack_NoNBT(ItemList.Tool_DataStick.get(1)), new IDataRender() {
			@SideOnly(Side.CLIENT)
			private ResourceLocation bg;
			@SideOnly(Side.CLIENT)
			private HashMap<GT_Slot_Holo, ItemStack> slots;
			private HashMap<GT_Slot_Holo, ItemStack[]> slots2;
			
			@Override
			@SideOnly(Side.CLIENT)
			public void loadResources() {
				bg = new ResourceLocation(MODID + ":textures/gui/assLineRender.png");
			}
			
			@Override
			public void initRender(ItemStack itemStack) {
				slots  = new HashMap<>();
				slots2 = new HashMap<>();
				
				slots.put(new GT_Slot_Holo(null, 0, 143, 55, false, false, 1), ItemList.Tool_DataStick.getWithName(1, "Research data"));
				ItemStack output = ItemStack.loadItemStackFromNBT(itemStack.stackTagCompound.getCompoundTag("output"));
				if (output != null) {
					slots.put(new GT_Slot_Holo(null, 0, 143, 19, false, false, 64), output);
				}
				
				for (int i = 0; i < 16; i++) {
					ArrayList<ItemStack> array = new ArrayList<>();
					ItemStack input = ItemStack.loadItemStackFromNBT(itemStack.stackTagCompound.getCompoundTag(Integer.toString(i)));
					if (input != null) {
						array.add(input);
					}
					for (int k = 0; k < itemStack.stackTagCompound.getInteger("a" + i); k++) {
						input = ItemStack.loadItemStackFromNBT(itemStack.stackTagCompound.getCompoundTag("a" + i + ":" + k));
						if (input != null) {
							array.add(input);
						}
					}
					if (array.size() > 0) {
						slots2.put(
								new GT_Slot_Holo(null, 0, 17 + (i & 0x3) * 18, 19 + (i >> 2) * 18, false, false, 64),
								array.toArray(new ItemStack[0])
						);
					}
				}
				for (int i = 0; i < 4; i++) {
					FluidStack fs = FluidStack.loadFluidStackFromNBT(itemStack.stackTagCompound.getCompoundTag("f" + i));
					if (fs != null) {
						slots.put(
								new GT_Slot_Holo(null, 0, 107, 19 + i * 18, false, false, 1),
								GT_Utility.getFluidDisplayStack(fs, true)
						);
					}
				}
			}
			
			@Override
			public void renderTooltips(ItemStack itemStack, int mouseX, int mouseY, GT_GUIContainer_DataReader gui) {
				for (Map.Entry<GT_Slot_Holo, ItemStack> entry : slots.entrySet()) {
					gui.renderTooltipSimple(mouseX, mouseY, entry.getKey(), entry.getValue());
				}
				int time = (int) (System.currentTimeMillis() / 2000);
				for (Map.Entry<GT_Slot_Holo, ItemStack[]> entry : slots2.entrySet()) {
					gui.renderTooltipSimple(mouseX, mouseY, entry.getKey(), entry.getValue()[time % entry.getValue().length]);
				}
			}
			
			@Override
			@SideOnly(Side.CLIENT)
			public void renderForeground(ItemStack itemStack, int mouseX, int mouseY, GT_GUIContainer_DataReader gui, FontRenderer font) {
				int time = itemStack.stackTagCompound.getInteger("time");
				int EUt = itemStack.stackTagCompound.getInteger("eu");
				int xStart = 8;
				int color = new Color(255, 255, 255).hashCode();
				font.drawString("Assembly Line Recipe", xStart, 8, color);
				font.drawString(GT_Utility.trans("152", "Total: ") + GT_Utility.formatNumbers((long) time * EUt) + " EU", xStart, 93, color);
				font.drawString(GT_Utility.trans("153", "Usage: ") + GT_Utility.formatNumbers(EUt) + " EU/t", xStart, 103, color);
				font.drawString(GT_Utility.trans("154", "Voltage: ") + GT_Utility.formatNumbers(EUt) + " EU", xStart, 113, color);
				font.drawString(GT_Utility.trans("155", "Amperage: ") + 1, xStart, 123, color);
				font.drawString(GT_Utility.trans("158", "Time: ") + String.format("%.2f " + GT_Utility.trans("161", " secs"), 0.05F * time), xStart, 133, color);
				
				for (Map.Entry<GT_Slot_Holo, ItemStack> entry : slots.entrySet()) {
					gui.renderItemSimple(entry.getKey(), entry.getValue());
				}
				time = (int) (System.currentTimeMillis() / 2000);
				for (Map.Entry<GT_Slot_Holo, ItemStack[]> entry : slots2.entrySet()) {
					gui.renderItemSimple(entry.getKey(), entry.getValue()[time % entry.getValue().length]);
				}
			}
			
			@Override
			@SideOnly(Side.CLIENT)
			public void renderBackgroundOverlay(ItemStack itemStack, int mouseX, int mouseY, int X, int Y, GT_GUIContainer_DataReader gui) {
				//176/83
				gui.mc.getTextureManager().bindTexture(bg);
				gui.drawTexturedModalRect(X, Y, 0, 0, 176, 151);
			}
			
			@Override
			public boolean canRender(ItemStack itemStack, byte tier) {
				NBTTagCompound nbtTagCompound = itemStack.stackTagCompound;
				return nbtTagCompound != null && nbtTagCompound.hasKey("output");
			}
			
			@Override
			public int getReadingEUt() {
				return (int) V[4];
			}
			
			@Override
			public int getReadingTime() {
				return 128;
			}
		});
	}
	
	@Override
	public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_DataReader(mName, mTier, mDescription, mTextures);
	}
	
	@Override
	public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
		if (aBaseMetaTileEntity.getWorld() == null) {
			if (aSide == aFacing) {
				return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1],
						aActive ? TextureFactory.of(READER_ONLINE) : TextureFactory.of(READER_OFFLINE)};
			}
			return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1]};
		}
		if (aSide == mMainFacing) {
			return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1],
					aActive ? TextureFactory.of(READER_ONLINE) : TextureFactory.of(READER_OFFLINE)};
		} else if (aSide == aFacing) {
			return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1], TextureFactory.of(Textures.BlockIcons.OVERLAY_PIPE_OUT)};
		}
		return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1]};
	}
	
	@Override
	public ITexture[][][] getTextureSet(ITexture[] aTextures) {
		return null;
	}
	
	@Override
	public int checkRecipe() {
		if (getOutputAt(0) != null) {
			return DID_NOT_FIND_RECIPE;
		}
		ItemStack input = getInputAt(0);
		for (IDataRender render : getRenders(new ItemStack_NoNBT(input))) {
			if (render.canRender(input, mTier)) {
				mOutputItems[0] = input.copy();
				input.stackSize -= 1;
				calculateOverclockedNess(render.getReadingEUt(), render.getReadingTime());
				if (mMaxProgresstime == Integer.MAX_VALUE - 1 && mEUt == Integer.MAX_VALUE - 1)
					return FOUND_RECIPE_BUT_DID_NOT_MEET_REQUIREMENTS;
				return FOUND_AND_SUCCESSFULLY_USED_RECIPE;
			}
		}
		return DID_NOT_FIND_RECIPE;
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
		super.onPostTick(aBaseMetaTileEntity, aTick);
		aBaseMetaTileEntity.setActive(getOutputAt(0) != null || mMaxProgresstime > 0);
	}
	
	@Override
	public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GT_Container_DataReader(aPlayerInventory, aBaseMetaTileEntity);
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GT_GUIContainer_DataReader(aPlayerInventory, aBaseMetaTileEntity, getLocalName(), mGUIName,
				GT_Utility.isStringValid(mNEIName) ? mNEIName : getRecipeList() != null ? getRecipeList().mUnlocalizedName : "");
	}
	
	@Override
	public boolean isSimpleMachine() {
		return false;
	}
	
	@Override
	public boolean isAccessAllowed(EntityPlayer aPlayer) {
		return true;
	}
	
	@Override
	public String[] getDescription() {
		return new String[]{"Reads Data Sticks and Orbs", "Power it up and", "Put the data storage in",};
	}
	
	@Override
	public boolean isElectric() {
		return true;
	}
	
	@Override
	public boolean isEnetInput() {
		return true;
	}
	
	@Override
	public boolean isInputFacing(byte aSide) {
		return aSide != getBaseMetaTileEntity().getFrontFacing();
	}
	
	@Override
	public boolean isOutputFacing(byte aSide) {
		return aSide != getBaseMetaTileEntity().getFrontFacing();
	}
	
	@Override
	public long maxEUInput() {
		return V[mTier];
	}
	
	@Override
	public long maxEUStore() {
		return maxEUInput() * 16L;
	}
	
	@Override
	public long getMinimumStoredEU() {
		return maxEUInput() * 4L;
	}
	
	public interface IDataRender {
		@SideOnly(Side.CLIENT)
		void loadResources();
		
		@SideOnly(Side.CLIENT)
		void initRender(ItemStack itemStack);
		
		@SideOnly(Side.CLIENT)
		void renderTooltips(ItemStack itemStack, int mouseX, int mouseY, GT_GUIContainer_DataReader gui);
		
		@SideOnly(Side.CLIENT)
		void renderForeground(ItemStack itemStack, int mouseX, int mouseY, GT_GUIContainer_DataReader gui, FontRenderer font);
		
		@SideOnly(Side.CLIENT)
		void renderBackgroundOverlay(ItemStack itemStack, int mouseX, int mouseY, int X, int Y, GT_GUIContainer_DataReader gui);
		
		boolean canRender(ItemStack itemStack, byte tier);
		
		int getReadingEUt();
		
		int getReadingTime();
	}
	
	public static class GT_Container_DataReader extends GT_Container_BasicTank {
		public boolean mStuttering = false;
		
		public GT_Container_DataReader(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
			super(aInventoryPlayer, aTileEntity);
		}
		
		@Override
		public void addSlots(InventoryPlayer aInventoryPlayer) {
			int tStartIndex = ((GT_MetaTileEntity_BasicMachine) this.mTileEntity.getMetaTileEntity()).getInputSlot();
			this.addSlotToContainer(new Slot(this.mTileEntity, tStartIndex, 53, 153));
			tStartIndex = ((GT_MetaTileEntity_BasicMachine) this.mTileEntity.getMetaTileEntity()).getOutputSlot();
			this.addSlotToContainer(new GT_Slot_Output(this.mTileEntity, tStartIndex, 107, 153));
			this.addSlotToContainer(new Slot(this.mTileEntity, 1, 17, 153));
		}
		
		@Override
		protected void bindPlayerInventory(InventoryPlayer aInventoryPlayer) {
			int i;
			for (i = 0; i < 3; ++i) {
				for (int j = 0; j < 9; ++j) {
					this.addSlotToContainer(new Slot(aInventoryPlayer, j + i * 9 + 9, 8 + j * 18, 174 + i * 18));
				}
			}
			for (i = 0; i < 9; ++i) {
				this.addSlotToContainer(new Slot(aInventoryPlayer, i, 8 + i * 18, 232));
			}
		}
		
		@Override
		public void detectAndSendChanges() {
			super.detectAndSendChanges();
			if (!this.mTileEntity.isClientSide() && this.mTileEntity.getMetaTileEntity() != null) {
				this.mStuttering = ((GT_MetaTileEntity_BasicMachine) this.mTileEntity.getMetaTileEntity()).mStuttering;
				for (Object crafter : this.crafters) {
					ICrafting var1 = (ICrafting) crafter;
					var1.sendProgressBarUpdate(this, 102, this.mStuttering ? 1 : 0);
				}
			}
		}
		
		@Override
		public void addCraftingToCrafters(ICrafting par1ICrafting) {
			super.addCraftingToCrafters(par1ICrafting);
		}
		
		@Override
		@SideOnly(Side.CLIENT)
		public void updateProgressBar(int par1, int par2) {
			super.updateProgressBar(par1, par2);
			if (par1 == 102) {
				this.mStuttering = par2 != 0;
			}
			
		}
		
		@Override
		public int getSlotStartIndex() {
			return 0;
		}
		
		@Override
		public int getShiftClickStartIndex() {
			return 0;
		}
		
		@Override
		public int getSlotCount() {
			return 3;
		}
		
		@Override
		public int getShiftClickSlotCount() {
			return 1;
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static class GT_GUIContainer_DataReader extends GT_GUIContainerMetaTile_Machine {
		public final String mName;
		public final String mNEI;
		public final byte mProgressBarDirection;
		public final byte mProgressBarAmount;
		private ItemStack stack = null;
		
		public GT_GUIContainer_DataReader(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, String aName, String aTextureFile, String aNEI) {
			this(aInventoryPlayer, aTileEntity, aName, aTextureFile, aNEI, (byte) 0, (byte) 1);
		}
		
		public GT_GUIContainer_DataReader(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, String aName, String aTextureFile, String aNEI, byte aProgressBarDirection, byte aProgressBarAmount) {
			super(new GT_Container_DataReader(aInventoryPlayer, aTileEntity), "gregtech:textures/gui/basicmachines/" + aTextureFile);
			this.mProgressBarDirection = aProgressBarDirection;
			this.mProgressBarAmount    = (byte) Math.max(1, aProgressBarAmount);
			this.mName                 = aName;
			this.mNEI                  = aNEI;
			ySize                      = 256;
		}
		
		@Override
		public void drawScreen(int mouseX, int mouseY, float par3) {
			super.drawScreen(mouseX, mouseY, par3);
			if (mContainer != null) {
				if (mContainer.mTileEntity != null && mContainer.mTileEntity.getMetaTileEntity() instanceof GTMTE_DataReader) {
					GTMTE_DataReader reader = (GTMTE_DataReader) mContainer.mTileEntity.getMetaTileEntity();
					renderDataTooltips(mouseX, mouseY, reader.mTier);
				}
			}
		}
		
		protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
			if (mContainer != null) {
				if (mContainer.mTileEntity != null && mContainer.mTileEntity.getMetaTileEntity() instanceof GTMTE_DataReader) {
					GTMTE_DataReader reader = (GTMTE_DataReader) mContainer.mTileEntity.getMetaTileEntity();
					if (renderDataFG(mouseX, mouseY, reader.mTier)) {
						return;
					}
				}
			}
			fontRendererObj.drawString(mName, 7, 8, 0xfafaff);
		}
		
		protected void drawGuiContainerBackgroundLayer(float par1, int mouseX, int mouseY) {
			super.drawGuiContainerBackgroundLayer(par1, mouseX, mouseY);
			int x = (this.width - this.xSize) / 2;
			int y = (this.height - this.ySize) / 2;
			this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
			if (this.mContainer != null) {
				if (((GT_Container_DataReader) this.mContainer).mStuttering) {
					this.drawTexturedModalRect(x + 127, y + 152, 176, 54, 18, 18);
				}
				
				if (this.mContainer.mMaxProgressTime > 0) {
					int tSize = this.mProgressBarDirection < 2 ? 20 : 18;
					int tProgress = Math.max(1, Math.min(tSize * this.mProgressBarAmount,
							(this.mContainer.mProgressTime > 0 ? 1 : 0) + this.mContainer.mProgressTime * tSize * this.mProgressBarAmount / this.mContainer.mMaxProgressTime)) % (tSize + 1);
					switch (this.mProgressBarDirection) {
						case 0:
							this.drawTexturedModalRect(x + 78, y + 152, 176, 0, tProgress, 18);
							break;
						case 1:
							this.drawTexturedModalRect(x + 78 + 20 - tProgress, y + 152, 196 - tProgress, 0, tProgress, 18);
							break;
						case 2:
							this.drawTexturedModalRect(x + 78, y + 152, 176, 0, 20, tProgress);
							break;
						case 3:
							this.drawTexturedModalRect(x + 78, y + 152 + 18 - tProgress, 176, 18 - tProgress, 20, tProgress);
							break;
						case 4:
							tProgress = 20 - tProgress;
							this.drawTexturedModalRect(x + 78, y + 152, 176, 0, tProgress, 18);
							break;
						case 5:
							tProgress = 20 - tProgress;
							this.drawTexturedModalRect(x + 78 + 20 - tProgress, y + 152, 196 - tProgress, 0, tProgress, 18);
							break;
						case 6:
							tProgress = 18 - tProgress;
							this.drawTexturedModalRect(x + 78, y + 152, 176, 0, 20, tProgress);
							break;
						case 7:
							tProgress = 18 - tProgress;
							this.drawTexturedModalRect(x + 78, y + 152 + 18 - tProgress, 176, 18 - tProgress, 20, tProgress);
					}
				}
			}
			if (mContainer != null) {
				if (mContainer.mTileEntity != null && mContainer.mTileEntity.getMetaTileEntity() instanceof GTMTE_DataReader) {
					GTMTE_DataReader reader = (GTMTE_DataReader) mContainer.mTileEntity.getMetaTileEntity();
					renderDataBG(reader.getStackInSlot(reader.getOutputSlot()), mouseX, mouseY, x, y, reader.mTier);
				}
			}
		}
		
		private void renderDataBG(ItemStack thing, int mouseX, int mouseY, int x, int y, byte mTier) {
			if (thing != null) {
				for (GTMTE_DataReader.IDataRender render :
						GTMTE_DataReader.getRenders(new ItemStack_NoNBT(thing))) {
					if (render.canRender(thing, mTier)) {
						if (!GT_Utility.areStacksEqual(stack, thing, false)) {
							render.initRender(thing);
						}
						render.renderBackgroundOverlay(thing, mouseX, mouseY, x, y, this);
						break;
					}
				}
			}
			stack = thing;
		}
		
		private boolean renderDataFG(int mouseX, int mouseY, byte mTier) {
			if (stack == null) {
				return false;
			}
			for (GTMTE_DataReader.IDataRender render :
					GTMTE_DataReader.getRenders(new ItemStack_NoNBT(stack))) {
				if (render.canRender(stack, mTier)) {
					render.renderForeground(stack, mouseX, mouseY, this, fontRendererObj);
					return true;
				}
			}
			return false;
		}
		
		private boolean renderDataTooltips(int mouseX, int mouseY, byte mTier) {
			if (stack == null) {
				return false;
			}
			for (GTMTE_DataReader.IDataRender render :
					GTMTE_DataReader.getRenders(new ItemStack_NoNBT(stack))) {
				if (render.canRender(stack, mTier)) {
					render.renderTooltips(stack, mouseX, mouseY, this);
					return true;
				}
			}
			return false;
		}
		
		public void renderItemSimple(GT_Slot_Holo slot, ItemStack itemStack) {
			int x = slot.xDisplayPosition;
			int y = slot.yDisplayPosition;
			this.zLevel       = 100.0F;
			itemRender.zLevel = 100.0F;
			
			if (itemStack == null) {
				IIcon iicon = slot.getBackgroundIconIndex();
				
				if (iicon != null) {
					GL11.glDisable(GL11.GL_LIGHTING);
					GL11.glEnable(GL11.GL_BLEND); // Forge: Blending needs to be enabled for this.
					this.mc.getTextureManager().bindTexture(TextureMap.locationItemsTexture);
					this.drawTexturedModelRectFromIcon(x, y, iicon, 16, 16);
					GL11.glDisable(GL11.GL_BLEND); // Forge: And clean that up
					GL11.glEnable(GL11.GL_LIGHTING);
				}
			}
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), itemStack, x, y);
			itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), itemStack, x, y);
			
			itemRender.zLevel = 0.0F;
			this.zLevel       = 0.0F;
		}
		
		public void renderTooltipSimple(int mouseX, int mouseY, GT_Slot_Holo slot, ItemStack itemStack) {
			int x = slot.xDisplayPosition + (width - xSize) / 2;
			int y = slot.yDisplayPosition + (height - ySize) / 2;
			if (mouseX >= x && mouseY >= y && mouseX <= x + 16 && mouseY <= y + 16) {
				List strings = itemStack.getTooltip(Minecraft.getMinecraft().thePlayer, false);
				if (strings.size() > 0) {
					strings.set(0, itemStack.getRarity().rarityColor + (String) strings.get(0));
				}
				hoveringText(strings, mouseX, mouseY, fontRendererObj);
			}
		}
		
		private void hoveringText(List strings, int x, int y, FontRenderer font) {
			if (!strings.isEmpty()) {
				GL11.glDisable(GL12.GL_RESCALE_NORMAL);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				int k = 0;
				
				for (Object aP_146283_1_ : strings) {
					String s = (String) aP_146283_1_;
					int l = font.getStringWidth(s);
					
					if (l > k) {
						k = l;
					}
				}
				
				int x2 = x + 12;
				int y2 = y - 12;
				int i1 = 8;
				
				if (strings.size() > 1) {
					i1 += 2 + (strings.size() - 1) * 10;
				}
				
				if (x2 + k > this.width) {
					x2 -= 28 + k;
				}
				
				if (y2 + i1 + 6 > this.height) {
					y2 = this.height - i1 - 6;
				}
				
				int j1 = 0xf0001040;//bg
				this.drawGradientRect(x2 - 3, y2 - 4, x2 + k + 3, y2 - 3, j1, j1);
				this.drawGradientRect(x2 - 3, y2 + i1 + 3, x2 + k + 3, y2 + i1 + 4, j1, j1);
				this.drawGradientRect(x2 - 3, y2 - 3, x2 + k + 3, y2 + i1 + 3, j1, j1);
				this.drawGradientRect(x2 - 4, y2 - 3, x2 - 3, y2 + i1 + 3, j1, j1);
				this.drawGradientRect(x2 + k + 3, y2 - 3, x2 + k + 4, y2 + i1 + 3, j1, j1);
				int k1 = 0x500040ff;//border bright
				int l1 = (k1 & 0xfefefe) >> 1 | k1 & 0xff000000;//border dark???
				this.drawGradientRect(x2 - 3, y2 - 3 + 1, x2 - 3 + 1, y2 + i1 + 3 - 1, k1, l1);
				this.drawGradientRect(x2 + k + 2, y2 - 3 + 1, x2 + k + 3, y2 + i1 + 3 - 1, k1, l1);
				this.drawGradientRect(x2 - 3, y2 - 3, x2 + k + 3, y2 - 3 + 1, k1, k1);
				this.drawGradientRect(x2 - 3, y2 + i1 + 2, x2 + k + 3, y2 + i1 + 3, l1, l1);
				for (int i2 = 0; i2 < strings.size(); ++i2) {
					String s1 = (String) strings.get(i2);
					font.drawStringWithShadow(s1, x2, y2, -1);
					
					if (i2 == 0) {
						y2 += 2;
					}
					
					y2 += 10;
				}
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			}
		}
	}
}
