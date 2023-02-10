package com.impact.mods.gregtech.tileentities.multi.generators.nuclear;

import com.impact.mods.gregtech.gui.nuclear.GT_Container_NuclearReactor;
import com.impact.mods.gregtech.gui.nuclear.GUI_NuclearReactor;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.hatch.GTMTE_Reactor_Rod_Hatch;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.string.MultiBlockTooltipBuilder;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.items.GT_RadioactiveCellIC_Item;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicHull;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.input.Keyboard;

import java.text.NumberFormat;
import java.util.ArrayList;

import static com.impact.mods.gregtech.enums.Texture.Icons.REACTOR_OVERLAY;
import static com.impact.mods.gregtech.enums.Texture.Icons.REACTOR_OVERLAY_ACTIVE;
import static com.impact.util.Utilits.getFluidStack;

public abstract class GTMTE_NuclearReactorBase<T> extends GTMTE_Impact_BlockBase<GTMTE_NuclearReactorBase<T>> {
	
	private static final int SPEED_DECAY = 5;
	private final int[] AMOUNT_NUCLEAR_HATCHES = {1, 9, 25};
	private final int[] AMOUNT_INPUT_HATCHES = {1, 3, 6};
	private final int[] AMOUNT_OUTPUT_HATCHES = {6, 12, 24};
	private final int[] AMOUNT_CASE = {36, 100, 121};
	public ArrayList<GTMTE_Reactor_Rod_Hatch> mRodHatches = new ArrayList<>();
	public ArrayList<GT_MetaTileEntity_BasicHull> mMachineHull = new ArrayList<>();
	public boolean mFirstStart = false;
	public float mCurrentTemp = 1;
	public double mCurrentOutput = 1;
	public double mCurrentInput = 1;
	public boolean isMoxFuel = false;
	public boolean isFastDecay = false;
	public boolean stopTemp = false;
	ITexture INDEX_CASE = Textures.BlockIcons.CASING_BLOCKS[12 + 32];
	
	public GTMTE_NuclearReactorBase(int aID, String aName, String aNameRegional) {
		super(aID, aName, aNameRegional);
	}
	
	public GTMTE_NuclearReactorBase(String aName) {
		super(aName);
	}
	
	@Override
	public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing,
								 byte aColorIndex, boolean aActive, boolean aRedstone) {
		if (aSide == aFacing) {
			return new ITexture[]{INDEX_CASE,
					TextureFactory.of(aActive ? REACTOR_OVERLAY_ACTIVE : REACTOR_OVERLAY)};
		}
		return new ITexture[]{INDEX_CASE};
	}
	
	abstract int tierReactor();
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("nur" + tierReactor());
		b
				.addInfo("info.0", "Radiation!")
				.addTypeSteam()
				.addInfo("info.1", "Default Mode (default rods): consumes water produces steam")
				.addInfo("info.2", "Default Mode (MOX rods): consumes water produces superheated steam, all rods need MOX")
				.addInfo("info.3", "Fact Decay Mode (default or MOX rods): consumes coolant produces hot coolant, rods decays speed x5")
				.addScrew("screw", "reactor mode")
				.addSeparator()
				.beginStructureBlock(0, 0, 0)
				.addController()
				.addNuclearRod("nc.hatch", "Any top middle casing", AMOUNT_NUCLEAR_HATCHES[tierReactor() - 1])
				.addInputHatch(AMOUNT_INPUT_HATCHES[tierReactor() - 1])
				.addOutputHatch(AMOUNT_OUTPUT_HATCHES[tierReactor() - 1])
				.addCasingInfo("case", "Radiation Proof Casing", AMOUNT_CASE[tierReactor() - 1])
				.addOtherStructurePart("other.0", "Steel Pipe Casing", "other.1", "pipes!")
				.addOtherStructurePart("other.2", "Machine Hull", "other.3", "what? yes, its for AE2 provider (max x1)")
				.signAndFinalize();
		return b;
	}
	
	public boolean addMachineHull(IGregTechTileEntity aTileEntity) {
		if (aTileEntity == null) return false;
		IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
		if (aMetaTileEntity == null) return false;
		if (aMetaTileEntity instanceof GT_MetaTileEntity_BasicHull) {
			return mMachineHull.add((GT_MetaTileEntity_BasicHull) aMetaTileEntity);
		}
		return false;
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setFloat("mCurrentTemp", mCurrentTemp);
		aNBT.setDouble("mCurrentOutput", mCurrentOutput);
		aNBT.setDouble("mCurrentInput", mCurrentInput);
		aNBT.setBoolean("mFirstStart", mFirstStart);
		aNBT.setBoolean("isFastDecay", isFastDecay);
		aNBT.setBoolean("isMoxFuel", isMoxFuel);
		aNBT.setBoolean("stopTemp", stopTemp);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		this.mCurrentTemp = aNBT.getFloat("mCurrentTemp");
		this.mCurrentOutput = aNBT.getDouble("mCurrentOutput");
		this.mCurrentInput = aNBT.getDouble("mCurrentInput");
		this.mFirstStart = aNBT.getBoolean("mFirstStart");
		this.isFastDecay = aNBT.getBoolean("isFastDecay");
		this.isMoxFuel = aNBT.getBoolean("isMoxFuel");
		this.stopTemp = aNBT.getBoolean("stopTemp");
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory,
							   IGregTechTileEntity aBaseMetaTileEntity) {
		return new GUI_NuclearReactor(aPlayerInventory, aBaseMetaTileEntity, getLocalName(),
				"NuclearReactorGUI.png");
	}
	
	@Override
	public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory,
							   IGregTechTileEntity aBaseMetaTileEntity) {
		return new GT_Container_NuclearReactor(aPlayerInventory, aBaseMetaTileEntity);
	}
	
	@Override
	public boolean checkRecipe(ItemStack aStack) {
		if (mFirstStart) {
			this.mMaxProgresstime = 20;
			this.mEUt = 0;
			this.mEfficiencyIncrease = 1000;
			return true;
		}
		return false;
	}
	
	public abstract int maxTemperature();
	
	public abstract int coefficientReactor();
	
	@Override
	public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
		super.onPostTick(aBaseMetaTileEntity, aTick);
		
		if (aBaseMetaTileEntity.isServerSide()) {
			int countTime = 20; // 1 sec
			int checkReallyRod = 0;
			float coefficientFuelRods = 0;
			int levelRods = 0;
			
			ArrayList<Boolean> arrayMoxRod = new ArrayList<>();
			
			for (GTMTE_Reactor_Rod_Hatch rod_hatch : mRodHatches) {
				if (rod_hatch.mInventory[0] != null &&
						rod_hatch.mInventory[0].getItem() instanceof GT_RadioactiveCellIC_Item) {
					if (((GT_RadioactiveCellIC_Item) rod_hatch.mInventory[0].getItem()).sHeat > 0) {
						rod_hatch.setStartReactor(mEfficiency >= 10000 && aBaseMetaTileEntity.isActive());
						checkReallyRod++;
						coefficientFuelRods += rod_hatch.mCoefficientFuelRod;
						arrayMoxRod.add(rod_hatch.mIsMox);
						rod_hatch.setFastDecay(isFastDecay ? SPEED_DECAY : 1);
						levelRods += (rod_hatch.mDownRod > 0) ? 1 : 0;
					}
				}
			}
			
			if ((levelRods <= 0 || !aBaseMetaTileEntity.isActive()) && aTick % countTime == 0) {
				mCurrentTemp -= 100;
				if (mCurrentTemp <= 0) mCurrentTemp = 0;
			}
			
			if (levelRods > 0 && aBaseMetaTileEntity.isActive() && !stopTemp && aTick % countTime == 0) {
				mCurrentTemp += coefficientFuelRods;
				if (mCurrentTemp > maxTemperature()) mCurrentTemp = maxTemperature();
			}
			
			int countMox = (int) arrayMoxRod.stream().filter(b -> b).count();
			isMoxFuel = countMox != 0 && countMox >= checkReallyRod;
			
			double totalBase = ((double) mCurrentTemp / (double) maxTemperature()) * coefficientReactor() / 4D;
			
			double total = (totalBase * coefficientFuelRods) / 400D;
			
			if (total <= 0) total = (totalBase / 400D);
			
			total /= 8D;
			
			//calculator https://drive.google.com/file/d/1oIsN8srvb0jAJpYMgWyLldbQIrNeTVLi/view?usp=sharing
			
			mCurrentInput = (isFastDecay ? total / 4D : total) * 8;
			mCurrentOutput = (isFastDecay ? total / 4D : total * 160D) * 8;
			
			double tScale = (double) mCurrentTemp / (double) maxTemperature();
			tScale = tScale <= 0 ? 0 : tScale;
			int temperature = (int) Math.min(((100 * tScale)), 100);
			
			if (aTick % 8 == 0 && temperature > 0) {
				if (!depleteInput(getInputFluid())) {
					for (GTMTE_Reactor_Rod_Hatch rod_hatch : mRodHatches) {
						rod_hatch.getBaseMetaTileEntity().doExplosion(Long.MAX_VALUE);
					}
				}
			}
			
			if (aTick % 8 == 0 && temperature > 0) {
				addOutput(getOutputFluid());
			}
			
			if (temperature <= 0) {
				mCurrentInput = 0;
				mCurrentOutput = 0;
			}
		}
	}
	
	@Override
	public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
		if (aPlayer.isSneaking()) {
			if (aPlayer.capabilities.isCreativeMode) mCurrentTemp = maxTemperature();
		} else {
			if (mCurrentTemp <= 10 && depleteInput(!isFastDecay ? getFluidStack("ic2coolant", 1) : Materials.Water.getFluid(1))) {
				isFastDecay = !isFastDecay;
				GT_Utility.sendChatToPlayer(aPlayer, "Fast Decay Mode " + (isFastDecay ? "Enabled" : "Disabled"));
			} else {
				GT_Utility.sendChatToPlayer(aPlayer,
						EnumChatFormatting.RED + (mCurrentTemp > 10 ? "Reactor active! " : isFastDecay ? "No Water! " : "No Coolant! ") +
						EnumChatFormatting.RESET + (!isFastDecay ? "Fast Decay Mode" : "Default Mode") + " cannot be activated");
			}
		}
	}
	
	@Override
	public boolean hasSeparate() {
		return false;
	}
	
	public void setFastDecayMode(boolean isFastDecay) {
		this.isFastDecay = isFastDecay;
	}
	
	public int[] getRodPosition() {
		int[] rodsStatus = new int[mRodHatches.size()];
		for (int i = 0; i < mRodHatches.size(); i++) {
			rodsStatus[i] = mRodHatches.get(i).mDownRod;
		}
		return rodsStatus;
	}
	
	public void setRodPosition(int rodPosition) {
		for (GTMTE_Reactor_Rod_Hatch rod : mRodHatches) {
			rod.setRodPosition(rodPosition);
		}
	}
	
	public void setSelectRodPosition(int i, int rodPosition) {
		if (mRodHatches.size() > i) {
			mRodHatches.get(i).setRodPosition(rodPosition);
		}
	}
	
	public FluidStack getInputFluid() {
		int in = (int) Math.ceil(mCurrentInput);
		return isFastDecay ? getFluidStack("ic2coolant", in) :
				Materials.Water.getFluid(in);
	}
	
	public FluidStack getOutputFluid() {
		int out = (int) Math.ceil(mCurrentOutput);
		return isFastDecay ? getFluidStack("ic2hotcoolant", out) : isMoxFuel ?
				getFluidStack("ic2superheatedsteam", out) : Materials.Water.getGas(out);
	}
	
	public abstract boolean checkMachineFunction(IGregTechTileEntity thisController);
	
	public boolean checkRodHatches(IGregTechTileEntity aTileEntity, int aBaseCasingIndex, int ID) {
		if (aTileEntity == null) {
			return false;
		}
		IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
		if (aMetaTileEntity == null) {
			return false;
		}
		if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch) {
			((GT_MetaTileEntity_Hatch) aMetaTileEntity).updateTexture(aBaseCasingIndex);
		}
		if (aMetaTileEntity instanceof GTMTE_Reactor_Rod_Hatch) {
			((GTMTE_Reactor_Rod_Hatch) aMetaTileEntity).setID(ID);
			return mRodHatches.add((GTMTE_Reactor_Rod_Hatch) aMetaTileEntity);
		}
		return false;
	}
	
	@Override
	public boolean machineStructure(IGregTechTileEntity thisController) {
		mRodHatches.clear();
		mMachineHull.clear();
		return this.checkMachineFunction(thisController);
	}
	
	@Override
	public String[] getInfoData() {
		
		double tScale = (double) mCurrentTemp / (double) maxTemperature();
		tScale = tScale <= 0 ? 0 : tScale;
		float temperature = (float) Math.min(((100 * tScale)), 100);
		
		return new String[]{
				"Temperature: " + EnumChatFormatting.RED + temperature + " / 100.0 %",
				"Input: " + getInputFluid().getLocalizedName() + " " + EnumChatFormatting.RED
						+ NumberFormat.getNumberInstance().format(mCurrentInput / 8.0) + EnumChatFormatting.RESET + " L/t",
				"Output: " + getOutputFluid().getLocalizedName() + " " + EnumChatFormatting.GREEN +  NumberFormat.getNumberInstance().format(mCurrentOutput / 8.0)
						+ EnumChatFormatting.RESET + " L/t"
		};
	}
}