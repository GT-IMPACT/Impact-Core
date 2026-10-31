package com.impact.mods.gregtech.tileentities.multi.generators.nuclear;

import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.gui.nuclear.GT_Container_NuclearReactor;
import com.impact.mods.gregtech.gui.nuclear.GUI_NuclearReactor;
import com.impact.mods.gregtech.tileentities.multi.generators.nuclear.hatch.GTMTE_Reactor_Rod_Hatch;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import cpw.mods.fml.common.Optional;
import gregtech.api.enums.Casings;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.Materials;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.IHatchElement;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.items.GT_RadioactiveCellIC_Item;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_BasicHull;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Utility;
import gregtech.api.util.HatchElementBuilder;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureElement;

import static com.impact.mods.gregtech.enums.Texture.Icons.REACTOR_OVERLAY;
import static com.impact.mods.gregtech.enums.Texture.Icons.REACTOR_OVERLAY_ACTIVE;
import static com.impact.util.Utilits.getFluidStack;
import static net.minecraft.util.StatCollector.translateToLocal;
import static net.minecraft.util.StatCollector.translateToLocalFormatted;
import static space.impact.structures.structure.StructureUtility.withHint;

public abstract class GTMTE_NuclearReactorBase<T> extends GTMTE_Impact_BlockBase<GTMTE_NuclearReactorBase<T>> {

    private static final int SPEED_DECAY = 5;
    private final int[] AMOUNT_NUCLEAR_HATCHES = { 1, 9, 25 };
    private final int[] AMOUNT_INPUT_HATCHES = { 1, 3, 6 };
    private final int[] AMOUNT_OUTPUT_HATCHES = { 6, 12, 24 };
    private final int[] AMOUNT_CASE = { 36, 100, 121 };
    public ArrayList<GTMTE_Reactor_Rod_Hatch> mRodHatches = new ArrayList<>();
    public ArrayList<GT_MetaTileEntity_BasicHull> mMachineHull = new ArrayList<>();
    public boolean mFirstStart = false;
    public float mCurrentTemp = 1;
    public double mCurrentOutput = 1;
    public double mCurrentInput = 1;
    public boolean isMoxFuel = false;
    public boolean isFastDecay = false;
    public boolean stopTemp = false;
    protected static final ICasing CASING = Casings.RADIATION_PROOF;
    protected static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 0).build();
    protected static final StructureRequirements RODS = StructureRequirements.builder(CASING, 0)
            .atLeast(ReactorHatch.Rod).build();

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
            return new ITexture[] { CASING.getTexture(),
                    TextureFactory.of(aActive ? REACTOR_OVERLAY_ACTIVE : REACTOR_OVERLAY) };
        }
        return new ITexture[] { CASING.getTexture() };
    }

    abstract int tierReactor();

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder(mName)
                .addInfo("Radiation!")
                .addMachineType("Steam Producer")
                .addInfo("Default Mode (default rods): consumes water produces steam")
                .addInfo("Default Mode (MOX rods): consumes water produces superheated steam, all rods need MOX")
                .addInfo("Fact Decay Mode (default or MOX rods): consumes coolant produces hot coolant, rods decays speed x5")
                .addInfo("Select reactor mode with a screwdriver")
                .addSeparator()
                .beginStructureBlock(0, 0, 0, false)
                .addController("See the structure hologram")
                .addOtherStructurePart("Nuclear Rod Hatch", "Any top middle casing; maximum " + AMOUNT_NUCLEAR_HATCHES[tierReactor() - 1])
                .addOtherStructurePart("Input Hatch", "Any casing; maximum " + AMOUNT_INPUT_HATCHES[tierReactor() - 1])
                .addOtherStructurePart("Output Hatch", "Any casing; maximum " + AMOUNT_OUTPUT_HATCHES[tierReactor() - 1])
                .addRequirements(SHELL, "Radiation shielding")
                .addOtherStructurePart("Steel Pipe Casing", "pipes!")
                .addOtherStructurePart("Machine Hull", "what? yes, its for AE2 provider (max x1)")
                .addStructureHint("Casing or compatible hatch", HintColor.BLUE)
                .addStructureHint("Casing, compatible hatch or machine hull", HintColor.CYAN)
                .addStructureHint(tierReactor() == 1 ? "Reactor rod" : "Casing or reactor rod", tierReactor() == 1 ? HintColor.RED : HintColor.GREEN)
                .toolTipFinisher();
    }

    public boolean addMachineHull(IGregTechTileEntity aTileEntity) {
        if (aTileEntity == null)
            return false;
        IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
        if (aMetaTileEntity == null)
            return false;
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
                if (mCurrentTemp <= 0)
                    mCurrentTemp = 0;
            }

            if (levelRods > 0 && aBaseMetaTileEntity.isActive() && !stopTemp && aTick % countTime == 0) {
                mCurrentTemp += coefficientFuelRods;
                if (mCurrentTemp > maxTemperature())
                    mCurrentTemp = maxTemperature();
            }

            int countMox = (int) arrayMoxRod.stream().filter(b -> b).count();
            isMoxFuel = countMox != 0 && countMox >= checkReallyRod;

            double totalBase = ((double) mCurrentTemp / (double) maxTemperature()) * coefficientReactor() / 4D;

            double total = (totalBase * coefficientFuelRods) / 400D;

            if (total <= 0)
                total = (totalBase / 400D);

            total /= 8D;

            // calculator
            // https://drive.google.com/file/d/1oIsN8srvb0jAJpYMgWyLldbQIrNeTVLi/view?usp=sharing

            mCurrentInput = (isFastDecay ? total / 4D : total) * 8;
            mCurrentOutput = (isFastDecay ? total / 4D : total * 160D) * 8;

            double tScale = (double) mCurrentTemp / (double) maxTemperature();
            tScale = tScale <= 0 ? 0 : tScale;
            int temperature = (int) Math.min(((100 * tScale)), 100);

            if (aTick % 2 == 0 && temperature > 0) {
                if (!depleteInput(getInputFluid())) {
                    for (GTMTE_Reactor_Rod_Hatch rod_hatch : mRodHatches) {
                        rod_hatch.getBaseMetaTileEntity().doExplosion(Long.MAX_VALUE);
                    }
                }
            }

            if (aTick % 2 == 0 && temperature > 0) {
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
            if (aPlayer.capabilities.isCreativeMode)
                mCurrentTemp = maxTemperature();
        } else {
            if (mCurrentTemp <= 10
                    && depleteInput(!isFastDecay ? getFluidStack("ic2coolant", 1) : Materials.Water.getFluid(1))) {
                isFastDecay = !isFastDecay;
                GT_Utility.sendChatToPlayer(aPlayer, "Fast Decay Mode " + (isFastDecay ? "Enabled" : "Disabled"));
            } else {
                GT_Utility.sendChatToPlayer(aPlayer,
                        EnumChatFormatting.RED
                                + (mCurrentTemp > 10 ? "Reactor active! " : isFastDecay ? "No Water! " : "No Coolant! ")
                                +
                                EnumChatFormatting.RESET + (!isFastDecay ? "Fast Decay Mode" : "Default Mode")
                                + " cannot be activated");
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
        return isFastDecay ? getFluidStack("ic2coolant", in) : Materials.Water.getFluid(in);
    }

    public FluidStack getOutputFluid() {
        int out = (int) Math.ceil(mCurrentOutput);
        return isFastDecay ? getFluidStack("ic2hotcoolant", out)
                : isMoxFuel ? getFluidStack("ic2superheatedsteam", out) : Materials.Water.getGas(out);
    }

    public abstract boolean checkMachineFunction(IGregTechTileEntity thisController);

    protected IStructureElement<GTMTE_NuclearReactorBase<T>> shellElement(boolean acceptsHull) {
        HatchElementBuilder<GTMTE_NuclearReactorBase<T>> builder = new HatchElementBuilder<GTMTE_NuclearReactorBase<T>>()
                .allow(HatchElement.Input, HatchElement.Output, HatchElement.Maintenance, HatchElement.Energy,
                        ImpactHatchElement.Dynamo, ImpactHatchElement.Muffler,
                        ImpactHatchElement.LaserInput, ImpactHatchElement.LaserOutput);
        if (acceptsHull) {
            builder.allow(ReactorHatch.Hull);
        }
        return withHint(builder.buildAndChain(SHELL), acceptsHull ? HintColor.CYAN : HintColor.BLUE);
    }

    protected enum ReactorHatch implements IHatchElement {
        Rod {
            @Override
            public boolean matches(IMetaTileEntity tile) {
                return tile instanceof GTMTE_Reactor_Rod_Hatch;
            }

            @Override
            public int count(GT_MetaTileEntity_MultiBlockBase machine) {
                return ((GTMTE_NuclearReactorBase<?>) machine).mRodHatches.size();
            }

            @Override
            public boolean add(GT_MetaTileEntity_MultiBlockBase machine, IGregTechTileEntity tile, int texture) {
                GTMTE_Reactor_Rod_Hatch hatch = (GTMTE_Reactor_Rod_Hatch) tile.getMetaTileEntity();
                hatch.updateTexture(texture);
                return ((GTMTE_NuclearReactorBase<?>) machine).mRodHatches.add(hatch);
            }

            @Override
            public String getDescription() {
                return translateToLocal("impact.structure.hatch.reactor_rod");
            }
        },
        Hull {
            @Override
            public boolean matches(IMetaTileEntity tile) {
                return tile instanceof GT_MetaTileEntity_BasicHull;
            }

            @Override
            public int count(GT_MetaTileEntity_MultiBlockBase machine) {
                return ((GTMTE_NuclearReactorBase<?>) machine).mMachineHull.size();
            }

            @Override
            public boolean add(GT_MetaTileEntity_MultiBlockBase machine, IGregTechTileEntity tile, int texture) {
                return ((GTMTE_NuclearReactorBase<?>) machine).addMachineHull(tile);
            }

            @Override
            public String getDescription() {
                return translateToLocal("impact.structure.hatch.machine_hull");
            }
        }
    }

    private int rodPosition(GTMTE_Reactor_Rod_Hatch rod) {
        IGregTechTileEntity base = getBaseMetaTileEntity();
        IGregTechTileEntity hatch = rod.getBaseMetaTileEntity();
        int[] local = new int[3];
        getExtendedFacing().getOffsetABC(new int[] {
                hatch.getXCoord() - base.getXCoord(), hatch.getYCoord() - base.getYCoord(),
                hatch.getZCoord() - base.getZCoord()
        }, local);
        return local[0] * 16 + local[2];
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity thisController) {
        mRodHatches.clear();
        mMachineHull.clear();
        boolean formed = checkMachineFunction(thisController);
        // Keep the existing left-to-right, front-to-back rod IDs independently of
        // traversal order.
        mRodHatches.sort(Comparator.comparingInt(this::rodPosition));
        for (int index = 0; index < mRodHatches.size(); index++) {
            mRodHatches.get(index).setID(index);
        }
        return formed;
    }

    @Override
    public String[] getInfoData() {

        double tScale = (double) mCurrentTemp / (double) maxTemperature();
        tScale = tScale <= 0 ? 0 : tScale;
        float temperature = (float) Math.min(((100 * tScale)), 100);

        return new String[] {
                "Temperature: " + EnumChatFormatting.RED + temperature + " / 100.0 %",
                "Input: " + getInputFluid().getLocalizedName() + " " + EnumChatFormatting.RED
                        + NumberFormat.getNumberInstance().format(mCurrentInput / 2.0) + EnumChatFormatting.RESET
                        + " L/t",
                "Output: " + getOutputFluid().getLocalizedName() + " " + EnumChatFormatting.GREEN
                        + NumberFormat.getNumberInstance().format(mCurrentOutput / 2.0)
                        + EnumChatFormatting.RESET + " L/t"
        };
    }

    @Override
    @Optional.Method(modid = "Waila")
    public void getWailaBody(ItemStack stack, List<String> tooltip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        super.getWailaBody(stack, tooltip, accessor, config);
        NBTTagCompound tag = accessor.getNBTData();
        int temp = tag.getInteger("reactorTemp");
        EnumChatFormatting[] color = {
                EnumChatFormatting.BLUE,
                EnumChatFormatting.AQUA,
                EnumChatFormatting.YELLOW,
                EnumChatFormatting.RED,
        };
        String colorTemp = "" + (temp > 75 ? color[3] : temp > 50 ? color[2] : temp > 25 ? color[1] : color[0]) + temp;
        String str = translateToLocalFormatted("impact.waila.reactor.temp", colorTemp + EnumChatFormatting.RESET);
        tooltip.add(str);
        if (tag.getBoolean("wMox"))
            tooltip.add(color[3] + translateToLocal("impact.waila.reactor.mox"));
        if (tag.getBoolean("wFastDecay"))
            tooltip.add(color[2] + translateToLocal("impact.waila.reactor.fastdecay"));
    }

    @Override
    @Optional.Method(modid = "Waila")
    public void getWailaNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag,
            World world, int x, int y, int z) {
        super.getWailaNBTData(player, tile, tag, world, x, y, z);
        double tScale = (double) mCurrentTemp / (double) maxTemperature();
        tScale = tScale <= 0 ? 0 : tScale;
        int temperature = Math.min(((int) (100 * tScale)), 100);
        tag.setInteger("reactorTemp", temperature);
        tag.setBoolean("wMox", isMoxFuel);
        tag.setBoolean("wFastDecay", isFastDecay);
    }
}
