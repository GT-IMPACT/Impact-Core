package com.impact.mods.gregtech.tileentities.multi.storage.hatch;

import appeng.api.AEApi;
import appeng.api.config.AccessRestriction;
import appeng.api.config.Actionable;
import appeng.api.networking.GridFlags;
import appeng.api.networking.IGrid;
import appeng.api.networking.IGridNode;
import appeng.api.networking.events.*;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.networking.security.IActionHost;
import appeng.api.networking.storage.IStorageGrid;
import appeng.api.storage.ICellContainer;
import appeng.api.storage.IMEInventory;
import appeng.api.storage.IMEInventoryHandler;
import appeng.api.storage.StorageChannel;
import appeng.api.storage.data.IAEFluidStack;
import appeng.api.storage.data.IItemList;
import appeng.api.util.AECableType;
import appeng.api.util.DimensionalCoord;
import appeng.me.helpers.AENetworkProxy;
import appeng.me.helpers.IGridProxyable;
import com.impact.util.fluid.MultiFluidHandler;
import com.impact.util.fluid.IMultiFluidWatcher;
import cpw.mods.fml.common.Optional;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.impact.mods.gregtech.enums.Texture.Icons.OVERLAY_MULTIHATCH;

/**
 * Доступ к общему хранилищу бака через Forge и ME-сеть AE2; собственного жидкостного буфера и предметных слотов нет.
 */
@Optional.InterfaceList(value = {
		@Optional.Interface(iface = "appeng.api.networking.security.IActionHost", modid = "appliedenergistics2", striprefs = true),
		@Optional.Interface(iface = "appeng.me.helpers.IGridProxyable", modid = "appliedenergistics2", striprefs = true),
		@Optional.Interface(iface = "appeng.api.storage.IMEInventory", modid = "appliedenergistics2", striprefs = true),
		@Optional.Interface(iface = "appeng.api.storage.IMEInventoryHandler", modid = "appliedenergistics2", striprefs = true),
		@Optional.Interface(iface = "appeng.api.storage.ICellContainer", modid = "appliedenergistics2", striprefs = true),
})
public class GTMTE_TankHatch extends GT_MetaTileEntity_Hatch implements IGridProxyable, IActionHost, ICellContainer,
		IMEInventory<IAEFluidStack>, IMEInventoryHandler<IAEFluidStack>, IMultiFluidWatcher {
	
	public MultiFluidHandler mfh;
	private Set<FluidStack> updatedFluids = new HashSet<>();
	
	private AENetworkProxy gridProxy = null;
	private int priority;
	
	public boolean lastLocked = false;
	
	/**
	 * Создаёт стек AE2 для зарегистрированной жидкости с количеством типа {@code long}.
	 *
	 * @param fluid жидкость для операции
	 * @param amount количество жидкости в литрах
	 * @return жидкостный стек AE2
	 */
	public static IAEFluidStack createAEFluidStack(Fluid fluid, long amount) {
		return createAEFluidStack(fluid.getID(), amount);
	}
	
	/**
	 * Преобразует жидкость Forge в представление AE2.
	 *
	 * @param fluid жидкость для операции
	 * @return жидкостный стек AE2
	 */
	public static IAEFluidStack createAEFluidStack(FluidStack fluid) {
		return AEApi.instance().storage().createFluidStack(fluid);
	}
	
	/**
	 * Создаёт стек AE2 для зарегистрированной жидкости с количеством типа {@code long}.
	 *
	 * @param fluidId зарегистрированный идентификатор жидкости Forge
	 * @param amount количество жидкости в литрах
	 * @return жидкостный стек AE2
	 */
	public static IAEFluidStack createAEFluidStack(int fluidId, long amount) {
		return createAEFluidStack(new FluidStack(FluidRegistry.getFluid(fluidId), 1)).setStackSize(amount);
	}
	
	/**
	 * Регистрирует I/O-хэтч без предметных слотов.
	 *
	 * @param aID идентификатор регистрации или интерфейса GregTech
	 * @param aName внутреннее имя метасущности
	 * @param aNameRegional локализованное имя для регистрации
	 * @param aTier уровень хэтча
	 */
	public GTMTE_TankHatch(int aID, String aName, String aNameRegional, int aTier) {
		super(aID, aName, aNameRegional, aTier, 0, new String[]{
				"All-in-one access for the High-Tech fluid tank"}
		);
	}
	
	/**
	 * Создаёт экземпляр I/O-хэтча с заданными описанием и текстурами, без предметных слотов.
	 *
	 * @param aName внутреннее имя метасущности
	 * @param aTier уровень хэтча
	 * @param aDescription описание хэтча
	 * @param aTextures набор текстур хэтча
	 */
	public GTMTE_TankHatch(String aName, int aTier, String aDescription, ITexture[][][] aTextures) {
		super(aName, aTier, 0, aDescription, aTextures);
	}
	
	/**
	 * Создаёт экземпляр I/O-хэтча с заданными описанием и текстурами, без предметных слотов.
	 *
	 * @param aName внутреннее имя метасущности
	 * @param aTier уровень хэтча
	 * @param aDescription описание хэтча
	 * @param aTextures набор текстур хэтча
	 */
	public GTMTE_TankHatch(String aName, int aTier, String[] aDescription, ITexture[][][] aTextures) {
		super(aName, aTier, 0, aDescription, aTextures);
	}
	
	/**
	 * Переподключает хэтч к общему хранилищу, переносит подписку наблюдателя и уведомляет ME-сеть.
	 *
	 * @param mfh общее хранилище бака или {@code null} для отключения
	 */
	public void setMultiFluidHandler(MultiFluidHandler mfh) {
		if (this.mfh != null) {
			this.mfh.removeFluidWatcher(this);
		}
		this.mfh = mfh;
		if (this.mfh != null) {
			this.lastLocked = mfh.locked;
			this.mfh.addFluidWatcher(this);
		}
		notifyAENetwork(true);
	}

	/**
	 * Сохраняет состояние базового хэтча, приоритет AE2 и последнюю известную блокировку бака.
	 *
	 * @param aNBT NBT-компаунд состояния хэтча
	 */
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setInteger("mAEPriority", this.priority);
		aNBT.setBoolean("lastLocked", lastLocked);
	}
	
	/**
	 * Восстанавливает состояние базового хэтча, приоритет AE2 и последнюю известную блокировку бака.
	 *
	 * @param aNBT NBT-компаунд состояния хэтча
	 */
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		this.priority = aNBT.getInteger("mAEPriority");
		lastLocked = aNBT.getBoolean("lastLocked");
	}
	
	/**
	 * Добавляет накладку I/O-хэтча к текстуре активного корпуса.
	 *
	 * @param aBaseTexture текстура корпуса хэтча
	 * @return слои текстуры активного хэтча
	 */
	@Override
	public ITexture[] getTexturesActive(ITexture aBaseTexture) {
		return new ITexture[]{aBaseTexture, TextureFactory.of(OVERLAY_MULTIHATCH)};
	}
	
	/**
	 * Добавляет накладку I/O-хэтча к текстуре неактивного корпуса.
	 *
	 * @param aBaseTexture текстура корпуса хэтча
	 * @return слои текстуры неактивного хэтча
	 */
	@Override
	public ITexture[] getTexturesInactive(ITexture aBaseTexture) {
		return new ITexture[]{aBaseTexture, TextureFactory.of(OVERLAY_MULTIHATCH)};
	}
	
	/**
	 * Отмечает хэтч как простую машину по контракту GregTech.
	 *
	 * @return {@code true}
	 */
	@Override
	public boolean isSimpleMachine() {
		return true;
	}
	
	/**
	 * Разрешает любую ориентацию лицевой стороны хэтча.
	 *
	 * @param aFacing направление лицевой стороны
	 * @return {@code true}: ограничений ориентации нет
	 */
	@Override
	public boolean isFacingValid(byte aFacing) {
		return true;
	}
	
	/**
	 * Разрешает взаимодействие с хэтчем любому игроку.
	 *
	 * @param aPlayer игрок, взаимодействующий с блоком
	 * @return {@code true}: проверка владельца здесь не выполняется
	 */
	@Override
	public boolean isAccessAllowed(EntityPlayer aPlayer) {
		return true;
	}
	
	/**
	 * Создаёт отдельный экземпляр метасущности с теми же регистрационными настройками.
	 *
	 * @param iGregTechTileEntity тайл-сущность нового блока; в этом методе не используется
	 * @return новый экземпляр для размещённого блока
	 */
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity iGregTechTileEntity) {
		return new GTMTE_TankHatch(mName, mTier, mDescriptionArray, mTextures);
	}
	
	/**
	 * Изменяет приоритет хранилища AE2 на 100: при приседании уменьшает, иначе увеличивает.
	 * Сообщает новый приоритет игроку.
	 *
	 * @param aSide сторона блока
	 * @param aWrenchingSide сторона применения инструмента; в этом методе не используется
	 * @param aPlayer игрок, взаимодействующий с блоком
	 * @param aX координата клика по оси X внутри блока
	 * @param aY координата клика по оси Y внутри блока
	 * @param aZ координата клика по оси Z внутри блока
	 * @return {@code true}: действие инструмента обработано
	 */
	@Override
	public boolean onSolderingToolRightClick(byte aSide, byte aWrenchingSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		if (aPlayer.isSneaking())
			this.priority -= 100;
		else
			this.priority += 100;
		GT_Utility.sendChatToPlayer(aPlayer, String.format("Priority set: %s", this.priority));
		return true;
	}
	
	/**
	 * Возвращает узел подключения хэтча к ME-сети через сетевой прокси.
	 *
	 * @param forgeDirection направление подключения
	 * @return узел AE2 или {@code null}, если прокси не предоставляет узел
	 */
	@Optional.Method(modid = "appliedenergistics2")
	public IGridNode getGridNode(ForgeDirection forgeDirection) {
		AENetworkProxy gp = getProxy();
		return gp != null ? gp.getNode() : null;
	}
	
	/**
	 * Задаёт тип подключения кабеля AE2 к хэтчу.
	 *
	 * @param forgeDirection направление подключения
	 * @return тип {@code SMART}
	 */
	@Override
	@Optional.Method(modid = "appliedenergistics2")
	public AECableType getCableConnectionType(ForgeDirection forgeDirection) {
		return AECableType.SMART;
	}
	
	/**
	 * Обрабатывает требование AE2 разрушить блок при нарушении безопасности; здесь действие не выполняется.
	 */
	@Optional.Method(modid = "appliedenergistics2")
	public void securityBreak() {}
	
	/**
	 * Лениво создаёт и подготавливает сетевой прокси AE2, требующий канал ME-сети.
	 *
	 * @return прокси подключения хэтча
	 */
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
	
	/**
	 * Возвращает мировые координаты хэтча и измерение для AE2.
	 *
	 * @return местоположение базовой тайл-сущности
	 */
	@Override
	@Optional.Method(modid = "appliedenergistics2")
	public DimensionalCoord getLocation() {
		IGregTechTileEntity gtm = this.getBaseMetaTileEntity();
		return new DimensionalCoord(gtm.getWorld(), gtm.getXCoord(), gtm.getYCoord(), gtm.getZCoord());
	}
	
	/**
	 * Добавляет жидкости доступного общего хранилища в переданный список AE2.
	 * Отсутствующий или заблокированный обработчик не добавляет содержимое.
	 *
	 * @param out список AE2 для добавления доступных жидкостей
	 * @return тот же список с добавленными жидкостями
	 */
	@Override
	@Optional.Method(modid = "appliedenergistics2")
	public IItemList<IAEFluidStack> getAvailableItems(IItemList<IAEFluidStack> out) {
		if (mfh != null) {
			if (mfh.getFluids().isEmpty()) return out;
			mfh.getFluids().forEach(fluidStack -> {
				if (fluidStack != null)
					out.add(createAEFluidStack(fluidStack));
			});
			return out;
		}
		return out;
	}
	
	/**
	 * Вставляет жидкость AE2 либо симулирует вставку, учитывая фильтр, ёмкость и уничтожение избытка.
	 * Одна операция ограничена Integer.MAX_VALUE литрами; непринимаемая часть возвращается без изменения входного стека.
	 *
	 * @param input предлагаемая жидкость AE2
	 * @param type режим AE2: симуляция или изменение хранилища
	 * @param src источник действия AE2; в этом методе не используется
	 * @return остаток жидкости или {@code null}, если весь объём принят
	 */
	@Override
	@Optional.Method(modid = "appliedenergistics2")
	public IAEFluidStack injectItems(IAEFluidStack input, Actionable type, BaseActionSource src) {
		if (input == null || input.getStackSize() <= 0) return input;
		FluidStack rInput = input.getFluidStack();
		rInput.amount = (int) Math.min(Integer.MAX_VALUE, input.getStackSize());
		int accepted = fill(null, rInput, type == Actionable.MODULATE);
		if (accepted <= 0) return input;
		if (accepted == input.getStackSize()) return null;
		return input.copy().setStackSize(input.getStackSize() - accepted);
	}
	
	/**
	 * Извлекает запрошенную жидкость через общий обработчик либо симулирует извлечение.
	 * Одна операция ограничена Integer.MAX_VALUE литрами.
	 *
	 * @param request запрошенная жидкость AE2
	 * @param mode режим AE2: симуляция или изменение хранилища
	 * @param src источник действия AE2; в этом методе не используется
	 * @return извлечённая жидкость AE2 или {@code null}, если извлечение невозможно
	 */
	@Override
	@Optional.Method(modid = "appliedenergistics2")
	public IAEFluidStack extractItems(IAEFluidStack request, Actionable mode, BaseActionSource src) {
		if (request == null || request.getStackSize() <= 0) return null;
		FluidStack fluid = request.getFluidStack();
		fluid.amount = (int) Math.min(Integer.MAX_VALUE, request.getStackSize());
		FluidStack ready = drain(null, fluid, mode == Actionable.MODULATE);
		return ready == null ? null : createAEFluidStack(ready);
	}
	
	/**
	 * Выбирает канал AE2, обслуживаемый хэтчем.
	 *
	 * @return канал {@code FLUIDS}
	 */
	@Override
	@Optional.Method(modid = "appliedenergistics2")
	public StorageChannel getChannel() {
		return StorageChannel.FLUIDS;
	}

	/**
	 * Уведомляет ME-сеть об изменении ячейки и содержимого.
	 * Если сетевой узел или сеть отсутствуют, завершает обработку без отправки событий.
	 *
	 * @param allFluids запрос полного обновления списка вместо только отмеченных изменений
	 */
	@Optional.Method(modid = "appliedenergistics2")
	private void notifyAENetwork(boolean allFluids) {
		IGridNode node = getGridNode(null);
		if (node == null) {
			return;
		}

		IGrid grid = node.getGrid();
		if (grid == null) {
			return;
		}

		grid.postEvent(new MENetworkCellArrayUpdate());
		IStorageGrid storageGrid = grid.getCache(IStorageGrid.class);
		if (storageGrid == null) {
			node.getGrid().postEvent(new MENetworkStorageEvent(null, StorageChannel.FLUIDS));
		} else if (mfh != null) {
			List<IAEFluidStack> aeFluids = new ArrayList<>(mfh.getFluids().size());
			if (allFluids) {
				mfh.getFluids().forEach(fluidStack -> {
					aeFluids.add(AEApi.instance().storage().createFluidStack(fluidStack));
				});
			}
			if (!mfh.locked) {
				updatedFluids.forEach(fluidStack -> {
					aeFluids.add(AEApi.instance().storage().createFluidStack(fluidStack));
				});
			}
			storageGrid.postAlterationOfStoredItems(StorageChannel.FLUIDS, aeFluids, new BaseActionSource());
			if (allFluids) {
				node.getGrid().postEvent(new MENetworkStorageEvent(storageGrid.getFluidInventory(), StorageChannel.FLUIDS));
			}
		} else {
			node.getGrid().postEvent(new MENetworkStorageEvent(storageGrid.getFluidInventory(), StorageChannel.FLUIDS));
		}
		node.getGrid().postEvent(new MENetworkCellArrayUpdate());
	}

	/**
	 * Запрашивает полное обновление хранилища при изменении доступных каналов ME-сети.
	 *
	 * @param channel канал хранения или событие изменения каналов AE2
	 */
	@MENetworkEventSubscribe
	@Optional.Method(modid = "appliedenergistics2")
	public void updateChannels(MENetworkChannelsChanged channel) {
		notifyAENetwork(true);
	}
	
	/**
	 * Запрашивает полное обновление хранилища при изменении питания ME-сети.
	 *
	 * @param event событие изменения питания ME-сети
	 */
	@MENetworkEventSubscribe
	@Optional.Method(modid = "appliedenergistics2")
	public void powerChange(MENetworkPowerStatusChange event) {
		notifyAENetwork(true);
	}

	/**
	 * Отмечает тип изменившейся жидкости для отправки уведомления AE2 на следующем тике.
	 * Количество не используется: уведомление служит сигналом пересчёта содержимого.
	 *
	 * @param fs жидкость, для которой выполняется операция
	 * @param newAmount новое количество жидкости в хранилище; ноль означает опустошение
	 */
	@Override
	public void onMultiFluidChange(FluidStack fs, int newAmount) {
        updatedFluids.add(new FluidStack(fs.getFluid(), 1));
	}

	/**
	 * Выполняет обработку первого тика базового хэтча и инициализирует подключение к ME-сети.
	 *
	 * @param aBaseMetaTileEntity базовая тайл-сущность блока
	 */
	@Override
	public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
		super.onFirstTick(aBaseMetaTileEntity);
		getProxy();
	}
	
	/**
	 * Отправляет накопленные изменения в ME-сеть, при снятии блокировки запрашивает полный пересчёт.
	 * После уведомления очищает очередь и выполняет обработку тика базового хэтча.
	 *
	 * @param aBaseMetaTileEntity базовая тайл-сущность блока
	 * @param aTick номер игрового тика
	 */
	@Override
	public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
		if (mfh != null) {
			boolean fullUpdate = !mfh.locked && (lastLocked != mfh.locked);
			lastLocked = mfh.locked;
			if (fullUpdate || !updatedFluids.isEmpty()) {
				notifyAENetwork(fullUpdate);
				updatedFluids.clear();
			}
		}
		super.onPostTick(aBaseMetaTileEntity, aTick);
	}
	
	/**
	 * Возвращает ёмкость одного жидкостного слота общего бака.
	 *
	 * @return ёмкость в литрах или ноль, если обработчик не подключён
	 */
	@Override
	public int getCapacity() {
		return mfh == null ? 0 : mfh.getCapacity();
	}

	/**
	 * Предоставляет Forge сведения о доступных жидкостных слотах общего бака.
	 *
	 * @param from сторона обращения; обработчик не ограничивает доступ по сторонам
	 * @return массив слотов; пустой при отсутствии обработчика, блокировке или отсутствии жидкостей
	 */
	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		FluidTankInfo[] tankInfo = new FluidTankInfo[0];
		List<FluidStack> fluids;
		if (mfh != null) {
			fluids   = mfh.getFluids();
			tankInfo = new FluidTankInfo[fluids.size()];
			for (int i = 0; i < tankInfo.length; i++) {
				tankInfo[i] = new FluidTankInfo(fluids.get(i), mfh.getCapacity());
			}
		}
		return tankInfo;
	}
	
	/**
	 * Не вводит дополнительный фильтр на уровне базового хэтча; фактическую вставку проверяет общий обработчик.
	 *
	 * @param aFluid жидкость, проверяемая по контракту GregTech
	 * @return {@code true}
	 */
	@Override
	public boolean isFluidInputAllowed(FluidStack aFluid) {
		return true;
	}
	
	/**
	 * Отключает опустошение предметных контейнеров в хэтче без предметных слотов.
	 *
	 * @return {@code false}
	 */
	@Override
	public boolean doesEmptyContainers() {
		return false;
	}
	
	/**
	 * Отключает предметный индикатор жидкости в хэтче без предметных слотов.
	 *
	 * @return {@code false}
	 */
	@Override
	public boolean displaysItemStack() {
		return false;
	}
	
	/**
	 * Отключает отображение размера предметного стека в хэтче.
	 *
	 * @return {@code false}
	 */
	@Override
	public boolean displaysStackSize() {
		return false;
	}
	
	/**
	 * Передаёт вставку или её симуляцию общему обработчику, не используя локальный буфер базового хэтча.
	 *
	 * @param resource предлагаемая или запрошенная жидкость с количеством
	 * @param doFill изменять хранилище; при {@code false} только рассчитать результат
	 * @return принятый объём в литрах; при включённом уничтожении избытка учитывает уничтоженный объём
	 */
	@Override
	public int fill(FluidStack resource, boolean doFill) {
		return mfh == null ? 0 : mfh.pushFluid(resource, doFill);
	}

	/**
	 * Передаёт вставку или её симуляцию общему обработчику, не используя локальный буфер базового хэтча.
	 *
	 * @param from сторона обращения; обработчик не ограничивает доступ по сторонам
	 * @param resource предлагаемая или запрошенная жидкость с количеством
	 * @param doFill изменять хранилище; при {@code false} только рассчитать результат
	 * @return принятый объём в литрах; при включённом уничтожении избытка учитывает уничтоженный объём
	 */
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return fill(resource, doFill);
	}

	/**
	 * Извлекает запрошенную жидкость либо симулирует извлечение через общее хранилище.
	 *
	 * @param from сторона обращения; обработчик не ограничивает доступ по сторонам
	 * @param resource предлагаемая или запрошенная жидкость с количеством
	 * @param doDrain изменять хранилище; при {@code false} только рассчитать результат
	 * @return извлечённый стек или {@code null}, если доступного объёма нет
	 */
	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if (mfh == null || resource == null || resource.amount <= 0) return null;
		int drained = mfh.pullFluid(resource, doDrain);
		if (drained <= 0) return null;
		FluidStack result = resource.copy();
		result.amount = drained;
		return result;
	}

	/**
	 * Извлекает жидкость из первого непустого доступного слота либо симулирует извлечение.
	 *
	 * @param maxDrain максимальное количество для извлечения в литрах
	 * @param doDrain изменять хранилище; при {@code false} только рассчитать результат
	 * @return извлечённый стек или {@code null}, если доступного объёма нет
	 */
	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		if (mfh == null || maxDrain <= 0) return null;
		for (FluidStack stored : mfh.getFluids()) {
			if (stored.amount > 0) {
				FluidStack request = stored.copy();
				request.amount = maxDrain;
				return drain(null, request, doDrain);
			}
		}
		return null;
	}

	/**
	 * Извлекает жидкость из первого непустого доступного слота либо симулирует извлечение.
	 * Сторона не ограничивает выбор жидкости; операция делегируется перегрузке без стороны.
	 *
	 * @param from сторона обращения; обработчик не ограничивает доступ по сторонам
	 * @param maxDrain максимальное количество для извлечения в литрах
	 * @param doDrain изменять хранилище; при {@code false} только рассчитать результат
	 * @return извлечённый стек или {@code null}, если доступного объёма нет
	 */
	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return drain(maxDrain, doDrain);
	}

	/**
	 * Проверяет возможность принять хотя бы один литр указанной жидкости с учётом фильтра и уничтожения избытка.
	 *
	 * @param from сторона обращения; обработчик не ограничивает доступ по сторонам
	 * @param fluid жидкость для операции
	 * @return {@code true}, если обработчик допускает вставку
	 */
	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		if (mfh != null && fluid != null) {
			return mfh.couldPush(new FluidStack(fluid, 1));
		}
		return false;
	}
	
	/**
	 * Проверяет возможность извлечь хотя бы один литр указанной жидкости без изменения хранилища.
	 *
	 * @param from сторона обращения; обработчик не ограничивает доступ по сторонам
	 * @param fluid жидкость для операции
	 * @return {@code true}, если жидкость доступна для извлечения
	 */
	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		if (mfh != null && fluid != null) {
			return mfh.pullFluid(new FluidStack(fluid, 1), false) > 0;
		}
		return false;
	}
	
	/**
	 * Запрещает извлечение предметов: у I/O-хэтча отсутствуют предметные слоты.
	 *
	 * @param aBaseMetaTileEntity базовая тайл-сущность блока
	 * @param aIndex индекс предметного слота; в этом хэтче предметных слотов нет
	 * @param aSide сторона блока
	 * @param aStack предмет для переноса; в этом хэтче предметные операции отключены
	 * @return {@code false}
	 */
	@Override
	public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide,
								  ItemStack aStack) {
		return false;
	}
	
	/**
	 * Запрещает вставку предметов: у I/O-хэтча отсутствуют предметные слоты.
	 *
	 * @param aBaseMetaTileEntity базовая тайл-сущность блока
	 * @param aIndex индекс предметного слота; в этом хэтче предметных слотов нет
	 * @param aSide сторона блока
	 * @param aStack предмет для переноса; в этом хэтче предметные операции отключены
	 * @return {@code false}
	 */
	@Override
	public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide,
								 ItemStack aStack) {
		return false;
	}
	
	/**
	 * Проверяет наличие подключённого и разблокированного общего обработчика.
	 *
	 * @return {@code true}, если доступ к заполнению бака открыт
	 */
	@Override
	public boolean canTankBeFilled() {
		return mfh != null && !mfh.locked;
	}
	
	/**
	 * Проверяет наличие подключённого и разблокированного общего обработчика.
	 *
	 * @return {@code true}, если доступ к извлечению из бака открыт
	 */
	@Override
	public boolean canTankBeEmptied() {
		return mfh != null && !mfh.locked;
	}
	
	/**
	 * Отключает заполнение предметных контейнеров в хэтче без предметных слотов.
	 *
	 * @return {@code false}
	 */
	@Override
	public boolean doesFillContainers() {
		return false;
	}
	
	/**
	 * Предоставляет AE2 узел хэтча для операций, требующих источника действия.
	 *
	 * @return сетевой узел или {@code null}, если прокси не предоставляет узел
	 */
	@Override
	@Optional.Method(modid = "appliedenergistics2")
	public IGridNode getActionableNode() {
		AENetworkProxy gp = getProxy();
		return gp != null ? gp.getNode() : null;
	}
	
	/**
	 * Предоставляет ME-сети права чтения и записи; фактическая доступность зависит от блокировки бака.
	 *
	 * @return права {@code READ_WRITE}
	 */
	@Override
	@Optional.Method(modid = "appliedenergistics2")
	public AccessRestriction getAccess() {
		return AccessRestriction.READ_WRITE;
	}
	
	/**
	 * Отмечает хранилище как приоритетное для рассматриваемой жидкости AE2.
	 *
	 * @param input предлагаемая жидкость AE2
	 * @return {@code true}: хэтч участвует в приоритетном поиске
	 */
	@Override
	@Optional.Method(modid = "appliedenergistics2")
	public boolean isPrioritized(IAEFluidStack input) {
		return true;
	}
	
	/**
	 * Симулирует заполнение и проверяет, может ли общее хранилище принять часть жидкости AE2.
	 *
	 * @param input предлагаемая жидкость AE2
	 * @return {@code true}, если принимаемый объём больше нуля
	 */
	@Override
	@Optional.Method(modid = "appliedenergistics2")
	public boolean canAccept(IAEFluidStack input) {
		FluidStack rInput = input.getFluidStack();
		return fill(null, rInput, false) > 0;
	}
	
	/**
	 * Предоставляет этот хэтч как ячейку хранения только для жидкостного канала AE2.
	 *
	 * @param channel канал хранения или событие изменения каналов AE2
	 * @return список с этим хэтчем для {@code FLUIDS}, иначе пустой список
	 */
	@Override
	@Optional.Method(modid = "appliedenergistics2")
	public List<IMEInventoryHandler> getCellArray(StorageChannel channel) {
		List<IMEInventoryHandler> list = new ArrayList<>();
		if (channel == StorageChannel.FLUIDS) {
			list.add(this);
		}
		return list;
	}
	
	/**
	 * Возвращает настроенный приоритет хэтча в ME-сети.
	 *
	 * @return числовой приоритет хранения AE2
	 */
	@Override
	@Optional.Method(modid = "appliedenergistics2")
	public int getPriority() {
		return this.priority;
	}
	
	/**
	 * Задаёт логический индекс ячейки AE2; он не является предметным слотом хэтча.
	 *
	 * @return ноль: хэтч представляет одну ячейку AE2
	 */
	@Override
	@Optional.Method(modid = "appliedenergistics2")
	public int getSlot() {
		return 0;
	}
	
	/**
	 * Разрешает использование хэтча на любом проходе поиска хранилища AE2.
	 *
	 * @param i номер прохода поиска хранилища AE2
	 * @return {@code true}
	 */
	@Override
	@Optional.Method(modid = "appliedenergistics2")
	public boolean validForPass(int i) {
		return true;
	}
	
	/**
	 * Принимает запрос AE2 на подсветку ячейки; визуальная подсветка здесь не реализована.
	 *
	 * @param slot логический индекс ячейки AE2; в этом методе не используется
	 */
	@Override
	public void blinkCell(int slot) { }
	
	/**
	 * Принимает уведомление AE2 о сохранении; отдельная запись не выполняется, жидкости сохраняет контроллер бака.
	 *
	 * @param cellInventory инвентарь ячейки AE2; сохранение выполняет контроллер
	 */
	@Override
	public void saveChanges(IMEInventory cellInventory) {
		//This is handled by host itself.
	}
}


