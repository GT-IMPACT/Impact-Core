package com.impact.mods.gregtech.tileentities.multi.storage;

import com.impact.mods.gregtech.tileentities.multi.storage.hatch.GTMTE_TankHatch;
import com.impact.util.fluid.IMultiFluidTank;
import com.impact.util.fluid.MultiFluidHandler;
import cpw.mods.fml.common.Optional;
import gregtech.api.enums.Casings;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.IHatchElement;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_EnhancedMultiBlockBase;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import space.impact.structures.alignment.IAlignmentLimits;
import space.impact.structures.alignment.constructable.ISurvivalConstructable;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.FluidTankBlock;
import static com.impact.loader.ItemRegistery.IGlassBlock;
import static net.minecraft.util.StatCollector.translateToLocalFormatted;
import static space.impact.structures.structure.StructureUtility.ofBlock;
import static space.impact.structures.structure.StructureUtility.ofBlockAnyMeta;
import static space.impact.structures.structure.StructureUtility.ofChain;
import static space.impact.structures.structure.StructureUtility.onElementPass;
import static space.impact.structures.structure.StructureUtility.withHint;

/**
 * Контроллер многоблочного бака для одной жидкости с общим хранилищем и
 * подключаемыми хэтчами.
 */
public class GTMTE_SingleTank
        extends GT_MetaTileEntity_EnhancedMultiBlockBase<GTMTE_SingleTank>
        implements ISurvivalConstructable, IMultiFluidTank {
    private static final ICasing CASING = Casings.CHEMICALLY_INERT;
    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 0).build();
    private static IStructureDefinition<GTMTE_SingleTank> STRUCTURE;
    private double fluidCapacityAcc;
    private int casingCount;

    @Override
    protected IAlignmentLimits getInitialAlignmentLimits() {
        return (direction, rotation, flip) -> direction != ForgeDirection.UNKNOWN
                && rotation.isNotRotated() && flip.isNotFlipped();
    }

    @Override
    public IStructureDefinition<GTMTE_SingleTank> getStructureDefinition() {
        if (STRUCTURE == null) {
            STRUCTURE = StructureDefinition.<GTMTE_SingleTank>builder()
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addShape("main", new String[][] {
                            { "AAA", "A~A", "AAA" },
                            { "GMG", "MTM", "GMG" },
                            { "GMG", "MTM", "GMG" },
                            { "GMG", "MTM", "GMG" },
                            { "GMG", "MTM", "GMG" },
                            { "GMG", "MTM", "GMG" },
                            { "EEE", "EEE", "EEE" }
                    })
                    .addElement('A', withHint(GTStructureUtility.buildHatchAdder(GTMTE_SingleTank.class)
                            .allow(HatchElement.Input, HatchElement.Output, TankHatch.INSTANCE)
                            .buildAndChain(SHELL, ofBlockAnyMeta(CASING.getBlock(), CASING.getMetadata()),
                                    t -> t.casingCount++),
                            HintColor.GREEN))
                    .addElement('E', onElementPass(t -> t.casingCount++,
                            ofBlockAnyMeta(CASING.getBlock(), CASING.getMetadata())))
                    .addElement('G', ofBlockAnyMeta(IGlassBlock))
                    .addElement('M', withHint(GTStructureUtility.buildHatchAdder(GTMTE_SingleTank.class)
                            .allow(HatchElement.Input, HatchElement.Output, TankHatch.INSTANCE)
                            .buildAndChain(StructureRequirements.builder(CASING, 0).build(),
                                    ofBlockAnyMeta(IGlassBlock), t -> {
                                    }),
                            HintColor.ORANGE))
                    .addElement('T', ofChain(
                            onElementPass(t -> t.fluidCapacityAcc += 16000000f, ofBlock(FluidTankBlock, 0)),
                            onElementPass(t -> t.fluidCapacityAcc += 32000000f, ofBlock(FluidTankBlock, 1)),
                            onElementPass(t -> t.fluidCapacityAcc += 64000000f, ofBlock(FluidTankBlock, 2)),
                            onElementPass(t -> t.fluidCapacityAcc += 128000000f, ofBlock(FluidTankBlock, 3)),
                            onElementPass(t -> t.fluidCapacityAcc += 256000000f, ofBlock(FluidTankBlock, 4)),
                            onElementPass(t -> t.fluidCapacityAcc += 400000000f, ofBlock(FluidTankBlock, 5)),
                            ofBlockAnyMeta(FluidTankBlock)))
                    .build();
        }
        return STRUCTURE;
    }

    private enum TankHatch implements IHatchElement {
        INSTANCE;

        @Override
        public boolean matches(IMetaTileEntity tile) {
            return tile instanceof GTMTE_TankHatch;
        }

        @Override
        public int count(GT_MetaTileEntity_MultiBlockBase machine) {
            return ((GTMTE_SingleTank) machine).sMultiHatches.size();
        }

        @Override
        public boolean add(GT_MetaTileEntity_MultiBlockBase machine, IGregTechTileEntity tile, int texture) {
            return ((GTMTE_SingleTank) machine).addMultiHatchToMachineList(tile, texture);
        }

        @Override
        public String getDescription() {
            return StatCollector.translateToLocal("impact.structure.hatch.tank");
        }
    }

    @Override
    public void construct(ItemStack trigger, boolean hintsOnly) {
        buildPiece("main", trigger, hintsOnly, 1, 1, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 1, 1, 0, budget, environment);
    }

    public final int MAX_DISTINCT_FLUIDS = 1;
    private final HashSet<GTMTE_TankHatch> sMultiHatches = new HashSet<>();
    private MultiFluidHandler mfh;
    private boolean doVoidExcess = false;

    @Override
    public MultiFluidHandler getMultiFluidHandler() {
        return mfh;
    }

    private void markTankDirty() {
        IGregTechTileEntity base = getBaseMetaTileEntity();
        if (base != null && base.getWorld() != null && base.isServerSide()) {
            markStorageDirty();
        }
    }

    private void detachHatches() {
        for (GTMTE_TankHatch hatch : sMultiHatches) {
            hatch.detachFromController(getBaseMetaTileEntity());
        }
        sMultiHatches.clear();
    }

    @Override
    public void onRemoval() {
        detachHatches();
        super.onRemoval();
    }

    @Override
    public void inValidate() {
        detachHatches();
        super.inValidate();
    }

    /**
     * Регистрирует контроллер бака, описание структуры и отключает обслуживание.
     *
     * @param aID           идентификатор регистрации или интерфейса GregTech
     * @param aNameRegional локализованное имя для регистрации
     */
    public GTMTE_SingleTank(int aID, String aNameRegional) {
        super(aID, "impact.multimachine.singlefluidtank", aNameRegional);

        enabledMaintenance = false;
    }

    /**
     * Создаёт экземпляр контроллера бака, регистрирует описание структуры и
     * отключает обслуживание.
     *
     * @param aName внутреннее имя метасущности
     */
    public GTMTE_SingleTank(String aName) {
        super(aName);

        enabledMaintenance = false;
    }

    /**
     * Создаёт отдельный экземпляр метасущности с теми же регистрационными
     * настройками.
     *
     * @param var1 предмет или тайл-сущность из контракта GregTech; в этом методе не
     *             используется
     * @return новый экземпляр для размещённого блока
     */
    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity var1) {

        return new GTMTE_SingleTank(super.mName);
    }

    /**
     * Возвращает описание бака; при удержании левого Shift показывает требования к
     * структуре.
     *
     * @return строки подсказки для выбранного режима
     */
    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder(mName)
                .addInfo("High-Tech fluid tank!").addMachineType("Fluid Tank")
                .addInfo("Right-Click the controller with a screwdriver will turn on excess voiding.")
                .addInfo("Fluid storage amount depends on the storage field blocks used.")
                .addInfo("If the integrity of Tank Storage is broken, the fluids are removed.")
                .addInfo("Place a fluid cell in the GUI to restrict.").addSeparator().addSeparator()
                .beginStructureBlock(3, 7, 3, false).addController("See the structure hologram")
                .addOtherStructurePart("Inner 1x5x1 tube", "Tank Storage Block")
                .addOtherStructurePart("Outer 3x1&7x3 Casing", "Chemical Casing")
                .addOtherStructurePart("Outer 3x5x3 glass shell", "I-Glass")
                .addOtherStructurePart("I/O Tank Hatch", "Instead of any casing or glass")
                .addInfo("I/O Tank Hatch for information and used EC2, OC systems")
                .addStructureHint("Casing or compatible hatch", HintColor.GREEN)
                .addStructureHint("Glass or input hatch/bus / output hatch/bus / tank hatch", HintColor.ORANGE)
                .toolTipFinisher();
    }

    /**
     * Выбирает текстуру корпуса и накладку контроллера с учётом стороны и
     * активности.
     *
     * @param aBaseMetaTileEntity базовая тайл-сущность блока
     * @param aSide               сторона блока
     * @param aFacing             направление лицевой стороны
     * @param aColorIndex         индекс окраски корпуса
     * @param aActive             признак активности машины
     * @param aRedstone           состояние красного камня; в этом методе не
     *                            используется
     * @return слои текстуры отображаемой стороны
     */
    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex,
            boolean aActive, boolean aRedstone) {
        return aSide == aFacing
                ? new ITexture[] { CASING.getTexture(),
                        TextureFactory.of(aActive ? Textures.BlockIcons.OVERLAY_FRONT_LARGE_CHEMICAL_REACTOR_ACTIVE
                                : Textures.BlockIcons.OVERLAY_FRONT_LARGE_CHEMICAL_REACTOR) }
                : new ITexture[] { CASING.getTexture() };
    }

    /**
     * Создаёт клиентский интерфейс контроллера многоблочного бака.
     *
     * @param aID                 идентификатор регистрации или интерфейса GregTech
     * @param aPlayerInventory    инвентарь игрока для интерфейса
     * @param aBaseMetaTileEntity базовая тайл-сущность блока
     * @return экран интерфейса бака
     */
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, this.getLocalName(),
                "MultiblockDisplay.png");
    }

    /**
     * Разрешает работу без обязательного предмета в слоте контроллера.
     *
     * @param var1 предмет или тайл-сущность из контракта GregTech; в этом методе не
     *             используется
     * @return {@code true}: проверка специального компонента не требуется
     */
    @Override
    public boolean isCorrectMachinePart(ItemStack var1) {
        return true;
    }

    /**
     * Настраивает рабочий цикл хранения, обновляет фильтр и блокировку доступа к
     * жидкостям.
     *
     * @param guiSlotItem предмет в слоте контроллера, задающий фильтр жидкостей
     * @return {@code true}: бак не требует рецепта переработки
     */
    @Override
    public boolean checkRecipe(ItemStack guiSlotItem) {

        this.mEfficiency = 10000;
        this.mEfficiencyIncrease = 10000;
        this.mEUt = 0;
        super.mMaxProgresstime = 100;

        if (mfh != null) {
            mfh.setFilterFluids(guiSlotItem);
            mfh.setLock(!super.getBaseMetaTileEntity().isActive());
        }

        return true;
    }

    /**
     * После базового тика обновляет блокировку по активности, принимает жидкости
     * входных хэтчей и заполняет выходные.
     *
     * @param aBaseMetaTileEntity базовая тайл-сущность блока
     * @param aTick               номер игрового тика
     */
    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
        if (!aBaseMetaTileEntity.isServerSide()) {
            return;
        }
        if (mfh != null) {
            mfh.setLock(!aBaseMetaTileEntity.isActive());
        }

        if (aBaseMetaTileEntity.isActive() && mfh != null) {
            // Suck in fluids
            final ArrayList<FluidStack> inputHatchFluids = super.getStoredFluids();
            if (!inputHatchFluids.isEmpty()) {
                for (FluidStack fluidStack : inputHatchFluids) {
                    final int pushed = mfh.pushFluid(fluidStack, true);
                    final FluidStack toDeplete = fluidStack.copy();
                    toDeplete.amount = pushed;
                    super.depleteInput(toDeplete);
                }
            }

            for (FluidStack storedFluid : new ArrayList<>(mfh.getFluids())) {
                outputFluid(storedFluid);
            }
        }

    }

    /**
     * Передаёт жидкость в допустимые выходные хэтчи, начиная с ограниченных. Из
     * бака списывается только фактически принятый объём; учитываются блокировка
     * жидкости и режим пара.
     *
     * @param storedFluid жидкость из общего хранилища
     */
    private void outputFluid(FluidStack storedFluid) {
        if (storedFluid == null || storedFluid.amount <= 0) {
            return;
        }
        // Prefer restrictive hatches, as the multiblock output helper does.
        for (int pass = 0; pass < 2; pass++) {
            for (GT_MetaTileEntity_Hatch_Output outputHatch : mOutputHatches) {
                if (!isValidMetaTileEntity(outputHatch) || (pass == 0 && outputHatch.mMode == 0)) {
                    continue;
                }
                if (GT_ModHandler.isSteam(storedFluid) ? !outputHatch.outputsSteam() : !outputHatch.outputsLiquids()) {
                    continue;
                }
                if (outputHatch.isFluidLocked() && outputHatch.getLockedFluidName() != null
                        && !outputHatch.getLockedFluidName().equals(storedFluid.getUnlocalizedName())) {
                    continue;
                }
                final FluidStack offered = storedFluid.copy();
                offered.amount = mfh.pullFluid(offered, false);
                if (offered.amount <= 0) {
                    return;
                }
                final int filled = outputHatch.fill(offered, true);
                if (filled > 0) {
                    offered.amount = filled;
                    mfh.pullFluid(offered, true);
                    outputHatch.onEmptyingContainerWhenEmpty();
                }
            }
        }
    }

    /**
     * Проверяет корпус, стекло, блоки хранения и хэтчи. При успешной проверке
     * обновляет ёмкость и связывает I/O-хэтчи с общим обработчиком без замены
     * содержимого.
     *
     * @param thisController тайл-сущность проверяемого контроллера
     * @param guiSlotItem    предмет слота контроллера; при проверке структуры не
     *                       используется
     * @return {@code true}, если структура соответствует требованиям
     */
    @Override
    public boolean checkMachine(IGregTechTileEntity thisController, ItemStack guiSlotItem) {
        HashSet<GTMTE_TankHatch> previousHatches = new HashSet<>(sMultiHatches);
        sMultiHatches.clear();
        fluidCapacityAcc = 0;
        casingCount = 0;
        mInputHatches.clear();
        mInputBusses.clear();
        mOutputHatches.clear();
        mOutputBusses.clear();
        mEnergyHatches.clear();
        mEnergyHatchesMulti.clear();
        boolean formationChecklist = checkPiece("main", 1, 1, 0) && SHELL.isSatisfied(this, casingCount);

        if (this.mEnergyHatches.size() > 0) {
            formationChecklist = false;
        }

        if (formationChecklist) {

            // Update MultiFluidHandler in case storage cells have been changed
            final int capacityPerFluid = (int) Math.round(fluidCapacityAcc);
            if (mfh == null) {
                mfh = new MultiFluidHandler(capacityPerFluid, MAX_DISTINCT_FLUIDS);
                mfh.setStorageChangeListener(this::markTankDirty);
                markTankDirty();
            } else {
                mfh.setCapacity(capacityPerFluid);
            }
            mfh.setVoidExcess(doVoidExcess);
            for (GTMTE_TankHatch mh : sMultiHatches) {
                mh.setMultiFluidHandler(mfh, getBaseMetaTileEntity());
            }
        }
        if (formationChecklist) {
            previousHatches.removeAll(sMultiHatches);
        } else {
            previousHatches.addAll(sMultiHatches);
            sMultiHatches.clear();
            if (mfh != null) {
                mfh.setLock(true);
            }
        }
        for (GTMTE_TankHatch hatch : previousHatches) {
            hatch.detachFromController(thisController);
        }
        return formationChecklist;
    }

    /**
     * Добавляет I/O-хэтч в список структуры и назначает ему текстуру корпуса.
     *
     * @param aTileEntity      кандидат на включение в список I/O-хэтчей
     * @param aBaseCasingIndex индекс текстуры корпуса для хэтча
     * @return {@code true}, если найден подходящий хэтч и он добавлен впервые
     */
    public boolean addMultiHatchToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) {
            return false;
        } else {
            final IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
            if (aMetaTileEntity == null) {
                return false;
            } else if (aMetaTileEntity instanceof GTMTE_TankHatch) {
                ((GTMTE_TankHatch) aMetaTileEntity).updateTexture(aBaseCasingIndex);
                return sMultiHatches.add((GTMTE_TankHatch) aMetaTileEntity);
            } else {
                return false;
            }
        }
    }

    /**
     * Переключает уничтожение избытка жидкости, передаёт настройку обработчику и
     * сообщает её игроку.
     *
     * @param aSide   сторона блока
     * @param aPlayer игрок, взаимодействующий с блоком
     * @param aX      координата клика по оси X внутри блока
     * @param aY      координата клика по оси Y внутри блока
     * @param aZ      координата клика по оси Z внутри блока
     */
    @Override
    public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        doVoidExcess = !doVoidExcess;
        markTankDirty();
        if (mfh != null) {
            mfh.setVoidExcess(doVoidExcess);
        }
        GT_Utility.sendChatToPlayer(aPlayer, doVoidExcess ? "Auto-voiding enabled" : "Auto-voiding disabled");
    }

    /**
     * Формирует диагностические строки о сохранённых жидкостях, ёмкости и режиме
     * уничтожения избытка.
     *
     * @return строки информации о состоянии бака
     */
    @Override
    public String[] getInfoData() {
        final ArrayList<String> ll = new ArrayList<>();

        if (mfh != null) {
            ll.add(EnumChatFormatting.YELLOW + "Stored Fluid:" + EnumChatFormatting.RESET);
            for (int i = 0; i < mfh.fluids.size(); i++) {
                ll.add(i + " - " + mfh.fluids.get(i).getLocalizedName() + ": " + mfh.fluids.get(i).amount + "L ("
                        + (Math.round(100.0f * mfh.fluids.get(i).amount / mfh.getCapacity())) + "%)");
            }
            ll.add(EnumChatFormatting.YELLOW + "Operational Data:" + EnumChatFormatting.RESET);
            ll.add("Auto-voiding: " + mfh.getVoidExcess());
            ll.add("Fluid Capacity: " + NumberFormat.getNumberInstance().format(mfh.getCapacity()) + "L");
        } else {
            ll.add("Incomplete multi-block.");
        }
        ll.add("---------------------------------------------");

        final String[] a = new String[ll.size()];
        return ll.toArray(a);
    }

    /**
     * Сохраняет настройки контроллера и полное содержимое обработчика в тег
     * {@code fluids}, затем состояние базовой машины.
     *
     * @param nbt NBT-компаунд состояния
     */
    @Override
    public void saveNBTData(NBTTagCompound nbt) {
        nbt = (nbt == null) ? new NBTTagCompound() : nbt;

        nbt.setBoolean("doVoidExcess", doVoidExcess);

        if (mfh != null) {
            nbt.setInteger("capacityPerFluid", mfh.getCapacity());
            nbt.setTag("fluids", mfh.saveNBTData(new NBTTagCompound()));
        } else {
            nbt.setInteger("capacityPerFluid", 1000);
            nbt.setTag("fluids", new NBTTagCompound());
        }
        super.saveNBTData(nbt);
    }

    /**
     * Восстанавливает настройки и содержимое бака, применяет режим уничтожения
     * избытка и переподключает известные I/O-хэтчи.
     *
     * @param nbt NBT-компаунд состояния
     */
    @Override
    public void loadNBTData(NBTTagCompound nbt) {
        nbt = (nbt == null) ? new NBTTagCompound() : nbt;

        doVoidExcess = nbt.getBoolean("doVoidExcess");

        mfh = new MultiFluidHandler(MAX_DISTINCT_FLUIDS);
        mfh.loadNBTData(nbt);
        mfh.setStorageChangeListener(this::markTankDirty);
        mfh.setVoidExcess(doVoidExcess);
        for (GTMTE_TankHatch mh : sMultiHatches) {
            mh.setMultiFluidHandler(mfh, getBaseMetaTileEntity());
        }
        super.loadNBTData(nbt);
    }

    /**
     * Разрешает получение диагностической информации о баке.
     *
     * @return {@code true}: бак предоставляет данные состояния
     */
    @Override
    public boolean isGivingInformation() {
        return true;
    }

    /**
     * Задаёт максимальную эффективность рабочего цикла хранения.
     *
     * @param var1 предмет или тайл-сущность из контракта GregTech; в этом методе не
     *             используется
     * @return 10000, что соответствует 100 процентам эффективности GregTech
     */
    @Override
    public int getMaxEfficiency(ItemStack var1) {
        return 10000;
    }

    /**
     * Задаёт загрязнение от рабочего цикла бака.
     *
     * @param var1 предмет или тайл-сущность из контракта GregTech; в этом методе не
     *             используется
     * @return ноль: хранение жидкостей не создаёт загрязнение
     */
    @Override
    public int getPollutionPerTick(ItemStack var1) {
        return 0;
    }

    /**
     * Задаёт износ компонента при работе бака.
     *
     * @param var1 предмет или тайл-сущность из контракта GregTech; в этом методе не
     *             используется
     * @return ноль: компоненты не изнашиваются
     */
    @Override
    public int getDamageToComponent(ItemStack var1) {
        return 0;
    }

    /**
     * Определяет реакцию бака на поломку компонента.
     *
     * @param var1 предмет или тайл-сущность из контракта GregTech; в этом методе не
     *             используется
     * @return {@code false}: поломка компонента не вызывает взрыв
     */
    @Override
    public boolean explodesOnComponentBreak(ItemStack var1) {
        return false;
    }

    @Override
    @Optional.Method(modid = "Waila")
    public void getWailaBody(ItemStack stack, List<String> tooltip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        super.getWailaBody(stack, tooltip, accessor, config);
        NBTTagCompound tag = accessor.getNBTData();
        int capacity = tag.getInteger("capacityPerFluid");
        NBTTagCompound fluids = tag.getCompoundTag("fluids");
        int fluidCount = tag.hasKey("tankFluidCount") ? tag.getInteger("tankFluidCount") : MAX_DISTINCT_FLUIDS;
        for (int i = 0; i < fluidCount; i++) {
            FluidStack fluid = FluidStack.loadFluidStackFromNBT(fluids.getCompoundTag(Integer.toString(i)));
            if (fluid != null) {
                String translationKey = fluid.amount == 0 ? "impact.waila.tank.reserved" : "impact.waila.tank.fluid";
                tooltip.add(
                        translateToLocalFormatted(translationKey, NumberFormat.getNumberInstance().format(fluid.amount),
                                NumberFormat.getNumberInstance().format(capacity), fluid.getLocalizedName()));
            }
        }
    }

    @Override
    @Optional.Method(modid = "Waila")
    public void getWailaNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag, World world, int x, int y,
            int z) {
        super.getWailaNBTData(player, tile, tag, world, x, y, z);
        NBTTagCompound fluids = new NBTTagCompound();
        List<FluidStack> displayFluids = MultiFluidHandler
                .getDisplayFluids(mfh == null ? Collections.emptyList() : mfh.fluids, mInventory[1]);
        tag.setInteger("capacityPerFluid", mfh == null ? 0 : mfh.getCapacity());
        tag.setInteger("tankFluidCount", displayFluids.size());
        int index = 0;
        for (FluidStack fluid : displayFluids) {
            fluids.setTag(Integer.toString(index++), fluid.writeToNBT(new NBTTagCompound()));
        }
        tag.setTag("fluids", fluids);
    }
}
