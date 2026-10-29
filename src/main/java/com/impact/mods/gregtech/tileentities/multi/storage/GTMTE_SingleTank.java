package com.impact.mods.gregtech.tileentities.multi.storage;

import static net.minecraft.util.StatCollector.translateToLocalFormatted;

import cpw.mods.fml.common.Optional;
import java.util.List;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.impact.mods.gregtech.tileentities.multi.storage.hatch.GTMTE_TankHatch;
import com.impact.util.fluid.MultiFluidHandler;
import com.impact.util.fluid.IMultiFluidTank;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Utility;
import gregtech.api.util.GT_ModHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.input.Keyboard;
import space.impact.api.multiblocks.alignment.constructable.IMultiBlockInfoContainer;
import space.impact.api.multiblocks.alignment.enumerable.ExtendedFacing;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;

import static com.impact.loader.ItemRegistery.FluidTankBlock;
import static com.impact.loader.ItemRegistery.IGlassBlock;
import static gregtech.api.GregTech_API.sBlockCasings8;
import static space.impact.api.multiblocks.alignment.constructable.IMultiBlockInfoContainer.registerTileClass;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlock;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlockAnyMeta;

/**
 * Контроллер многоблочного бака для одной жидкости с общим хранилищем и подключаемыми хэтчами.
 */
public class GTMTE_SingleTank extends GT_MetaTileEntity_MultiBlockBase implements IMultiFluidTank {
	
	public final int MAX_DISTINCT_FLUIDS = 1;
	private final HashSet<GTMTE_TankHatch> sMultiHatches = new HashSet<>();
	private final Block glassIC2 = IGlassBlock;
	private final Block CASING = sBlockCasings8;
	private final Block CASING_TANK = FluidTankBlock;
	private MultiFluidHandler mfh;
	int CASING_TEXTURE_ID = 176;
	private boolean doVoidExcess = false;

	@Override
	public MultiFluidHandler getMultiFluidHandler() {
		return mfh;
	}

	private void markTankDirty() {
		IGregTechTileEntity base = getBaseMetaTileEntity();
		if (base != null && base.getWorld() != null && base.isServerSide()) markStorageDirty();
	}

	private void detachHatches() {
		for (GTMTE_TankHatch hatch : sMultiHatches) hatch.detachFromController(getBaseMetaTileEntity());
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
	 * @param aID идентификатор регистрации или интерфейса GregTech
	 * @param aNameRegional локализованное имя для регистрации
	 */
	public GTMTE_SingleTank(int aID, String aNameRegional) {
		super(aID, "impact.multimachine.singlefluidtank", aNameRegional);
		run();
		enabledMaintenance = false;
	}
	
	/**
	 * Создаёт экземпляр контроллера бака, регистрирует описание структуры и отключает обслуживание.
	 *
	 * @param aName внутреннее имя метасущности
	 */
	public GTMTE_SingleTank(String aName) {
		super(aName);
		run();
		enabledMaintenance = false;
	}
	
	/**
	 * Создаёт отдельный экземпляр метасущности с теми же регистрационными настройками.
	 *
	 * @param var1 предмет или тайл-сущность из контракта GregTech; в этом методе не используется
	 * @return новый экземпляр для размещённого блока
	 */
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity var1) {
		run();
		return new GTMTE_SingleTank(super.mName);
	}
	
	/**
	 * Возвращает описание бака; при удержании левого Shift показывает требования к структуре.
	 *
	 * @return строки подсказки для выбранного режима
	 */
	@Override
	public String[] getDescription() {
		final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("single_tank");
		b.addInfo("info.0", "High-Tech fluid tank!")
				.addTypeMachine("name", "Fluid Tank")
				.addInfo("info.1", "Right-Click the controller with a screwdriver will turn on excess voiding.")
				.addInfo("info.2", "Fluid storage amount depends on the storage field blocks used.")
				.addInfo("info.3", "If the integrity of Tank Storage is broken, the fluids are removed.")
				.addInfo("info.4", "Place a fluid cell in the GUI to restrict.")
				.addSeparator()
				.addSeparator()
				.beginStructureBlock(3, 7, 3)
				.addController()
				.addOtherStructurePart("other.0", "Inner 1x5x1 tube", "other.1", "Tank Storage Block")
				.addOtherStructurePart("other.2", "Outer 3x1&7x3 Casing", "other.3", "Chemical Casing")
				.addOtherStructurePart("other.4", "Outer 3x5x3 glass shell", "other.5", "I-Glass")
				.addOtherStructurePart("other.6", "I/O Tank Hatch", "other.7", "Instead of any casing or glass")
				.addInfo("info.5", "I/O Tank Hatch for information and used EC2, OC systems")
				.signAndFinalize();
		if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			return b.getInformation();
		} else {
			return b.getStructureInformation();
		}
	}
	
	/**
	 * Выбирает текстуру корпуса и накладку контроллера с учётом стороны и активности.
	 *
	 * @param aBaseMetaTileEntity базовая тайл-сущность блока
	 * @param aSide сторона блока
	 * @param aFacing направление лицевой стороны
	 * @param aColorIndex индекс окраски корпуса
	 * @param aActive признак активности машины
	 * @param aRedstone состояние красного камня; в этом методе не используется
	 * @return слои текстуры отображаемой стороны
	 */
	@Override
	public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing,
								 byte aColorIndex,
								 boolean aActive, boolean aRedstone) {
		return aSide == aFacing
				? new ITexture[]{Textures.BlockIcons.casingTexturePages[1][48],
				TextureFactory.of(aActive
						? Textures.BlockIcons.OVERLAY_FRONT_LARGE_CHEMICAL_REACTOR_ACTIVE
						: Textures.BlockIcons.OVERLAY_FRONT_LARGE_CHEMICAL_REACTOR)}
				: new ITexture[]{Textures.BlockIcons.casingTexturePages[1][48]};
	}
	
	/**
	 * Создаёт клиентский интерфейс контроллера многоблочного бака.
	 *
	 * @param aID идентификатор регистрации или интерфейса GregTech
	 * @param aPlayerInventory инвентарь игрока для интерфейса
	 * @param aBaseMetaTileEntity базовая тайл-сущность блока
	 * @return экран интерфейса бака
	 */
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory,
							   IGregTechTileEntity aBaseMetaTileEntity) {
		return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity,
				this.getLocalName(),
				"MultiblockDisplay.png"
		);
	}
	
	/**
	 * Разрешает работу без обязательного предмета в слоте контроллера.
	 *
	 * @param var1 предмет или тайл-сущность из контракта GregTech; в этом методе не используется
	 * @return {@code true}: проверка специального компонента не требуется
	 */
	@Override
	public boolean isCorrectMachinePart(ItemStack var1) {
		return true;
	}
	
	/**
	 * Настраивает рабочий цикл хранения, обновляет фильтр и блокировку доступа к жидкостям.
	 *
	 * @param guiSlotItem предмет в слоте контроллера, задающий фильтр жидкостей
	 * @return {@code true}: бак не требует рецепта переработки
	 */
	@Override
	public boolean checkRecipe(ItemStack guiSlotItem) {
		
		this.mEfficiency         = 10000;
		this.mEfficiencyIncrease = 10000;
		this.mEUt                = 0;
		super.mMaxProgresstime   = 100;
	
		if (mfh != null) {
			mfh.setFilterFluids(guiSlotItem);
			mfh.setLock(!super.getBaseMetaTileEntity().isActive());
		}
		
		return true;
	}
	
	/**
	 * После базового тика обновляет блокировку по активности, принимает жидкости входных хэтчей и заполняет выходные.
	 *
	 * @param aBaseMetaTileEntity базовая тайл-сущность блока
	 * @param aTick номер игрового тика
	 */
	@Override
	public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
		super.onPostTick(aBaseMetaTileEntity, aTick);
		if (!aBaseMetaTileEntity.isServerSide()) return;
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
	 * Передаёт жидкость в допустимые выходные хэтчи, начиная с ограниченных.
	 * Из бака списывается только фактически принятый объём; учитываются блокировка жидкости и режим пара.
	 *
	 * @param storedFluid жидкость из общего хранилища
	 */
	private void outputFluid(FluidStack storedFluid) {
		if (storedFluid == null || storedFluid.amount <= 0) return;
		// Prefer restrictive hatches, as the multiblock output helper does.
		for (int pass = 0; pass < 2; pass++) {
			for (GT_MetaTileEntity_Hatch_Output outputHatch : mOutputHatches) {
				if (!isValidMetaTileEntity(outputHatch) || (pass == 0 && outputHatch.mMode == 0)) continue;
				if (GT_ModHandler.isSteam(storedFluid) ? !outputHatch.outputsSteam() : !outputHatch.outputsLiquids()) continue;
				if (outputHatch.isFluidLocked() && outputHatch.getLockedFluidName() != null
						&& !outputHatch.getLockedFluidName().equals(storedFluid.getUnlocalizedName())) continue;
				final FluidStack offered = storedFluid.copy();
				offered.amount = mfh.pullFluid(offered, false);
				if (offered.amount <= 0) return;
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
	 * Преобразует локальное смещение структуры в смещение мира по направлению контроллера.
	 *
	 * @param forgeDirection вектор направления задней стороны контроллера
	 * @param x локальное смещение по оси X
	 * @param y локальное смещение по оси Y
	 * @param z локальное смещение по оси Z
	 * @return повёрнутый вектор смещения
	 */
	public Vector3ic rotateOffsetVector(Vector3ic forgeDirection, int x, int y, int z) {
		final Vector3i offset = new Vector3i();
		
		// either direction on z-axis
		if (forgeDirection.x() == 0 && forgeDirection.z() == -1) {
			offset.x = x;
			offset.y = y;
			offset.z = z;
		}
		if (forgeDirection.x() == 0 && forgeDirection.z() == 1) {
			offset.x = -x;
			offset.y = y;
			offset.z = -z;
		}
		// either direction on x-axis
		if (forgeDirection.x() == -1 && forgeDirection.z() == 0) {
			offset.x = z;
			offset.y = y;
			offset.z = -x;
		}
		if (forgeDirection.x() == 1 && forgeDirection.z() == 0) {
			offset.x = -z;
			offset.y = y;
			offset.z = x;
		}
		// either direction on y-axis
		if (forgeDirection.y() == -1) {
			offset.x = x;
			offset.y = z;
			offset.z = y;
		}
		
		return offset;
	}
	
	/**
	 * Проверяет корпус, стекло, блоки хранения и хэтчи.
	 * При успешной проверке обновляет ёмкость и связывает I/O-хэтчи с общим обработчиком без замены содержимого.
	 *
	 * @param thisController тайл-сущность проверяемого контроллера
	 * @param guiSlotItem предмет слота контроллера; при проверке структуры не используется
	 * @return {@code true}, если структура соответствует требованиям
	 */
	@Override
	public boolean checkMachine(IGregTechTileEntity thisController, ItemStack guiSlotItem) {
		HashSet<GTMTE_TankHatch> previousHatches = new HashSet<>(sMultiHatches);
		sMultiHatches.clear();
		// Figure out the vector for the direction the back face of the controller is facing
		final Vector3ic forgeDirection = new Vector3i(
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ
		);
		int minCasingAmount = 20;
		boolean formationChecklist = true; // If this is still true at the end, machine is good to go :)
		double fluidCapacityAcc = 0;
		
		// Front segment
		for (int X = -1; X <= 1; X++) {
			for (int Y = -1; Y <= 1; Y++) {
				if (X == 0 && Y == 0) {
					continue; // Skip controller
				}
				
				// Get next TE
				final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, 0);
				final IGregTechTileEntity currentTE =
						thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				
				// Fluid hatches should touch the storage field.
				// Maintenance/Energy hatch can go anywhere
				if (X >= -1 && X <= 1 && Y >= -1 && Y <= 1) {
					if (!super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !addMultiHatchToMachineList(currentTE, CASING_TEXTURE_ID)) {
						
						// If it's not a hatch, is it the right casing for this machine? Check block and block meta.
						// Also check for multi hatch
						if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING) {
							// Seems to be valid casing. Decrement counter.
							minCasingAmount--;
						} else {
							formationChecklist = false;
						}
					}
				} else {
					if (!super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)) {
						
						// If it's not a hatch, is it the right casing for this machine? Check block and block meta.
						if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING) {
							// Seems to be valid casing. Decrement counter.
							minCasingAmount--;
						} else {
							formationChecklist = false;
						}
					}
				}
			}
		}
		
		// Middle seven long segment
		for (int X = -1; X <= 1; X++) {
			for (int Y = -1; Y <= 1; Y++) {
				for (int Z = -1; Z >= -5; Z--) {
					final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);
					if (X == 0 && Y == 0) {
						final int meta = thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z());
						
						if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING_TANK) {
							switch (meta) {
								case 0:
									fluidCapacityAcc += 16000000f; //80kk L
									break;
								case 1:
									fluidCapacityAcc += 32000000f; //160kk L
									break;
								case 2:
									fluidCapacityAcc += 64000000f; //320kk L
									break;
								case 3:
									fluidCapacityAcc += 128000000f; //640kk L
									break;
								case 4:
									fluidCapacityAcc += 256000000f; //1.28kkk L
									break;
								case 5:
									fluidCapacityAcc += 400000000f; //2kkk L
									break;
							}
						} else {
							
							formationChecklist = false;
						}
						continue;
					}
					
					// Get next TE
					final IGregTechTileEntity currentTE =
							thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
					
					// Corner allows only glass
					if (X == -1 && Y == -1 || X == 1 && Y == 1 || X == -1 && Y == 1 || X == 1 && Y == -1) {
						if (!(thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == glassIC2)) {
							formationChecklist = false;
						}
					} else {
						// Tries to add TE as either of those kinds of hatches.
						// The number is the texture index number for the texture that needs to be painted over the hatch texture (TAE for GT++)
						if (!super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
								&& !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)
								&& !addMultiHatchToMachineList(currentTE, CASING_TEXTURE_ID)) {
							
							// If it's not a hatch, is it the right casing for this machine? Check block and block meta.
							// Also check for multi hatch
							if (!(thisController.getBlockOffset(offset.x(), offset.y(), offset.z())
									== glassIC2)) {
								formationChecklist = false;
							}
						}
					}
				}
			}
		}
		
		// Back segment
		for (int X = -1; X <= 1; X++) {
			for (int Y = -1; Y <= 1; Y++) {
				// Get next TE
				final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, -6);
				final IGregTechTileEntity currentTE =
						thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				
				// Fluid hatches should touch the storage field.
				// Maintenance/Energy hatch can go anywhere
				if (X == -1 && X == 1 && Y == -1 && Y == 1) {
					if (!super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !addMultiHatchToMachineList(currentTE, CASING_TEXTURE_ID)) {
						
						// If it's not a hatch, is it the right casing for this machine? Check block and block meta.
						if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING) {
							// Seems to be valid casing. Decrement counter.
							minCasingAmount--;
						} else {
							formationChecklist = false;
						}
					}
				} else {
					if (!super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)) {
						
						// If it's not a hatch, is it the right casing for this machine? Check block and block meta.
						if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING) {
							// Seems to be valid casing. Decrement counter.
							minCasingAmount--;
						} else {
							formationChecklist = false;
						}
					}
				}
			}
		}
		
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
			if (mfh != null) mfh.setLock(true);
		}
		for (GTMTE_TankHatch hatch : previousHatches) hatch.detachFromController(thisController);
		return formationChecklist;
	}
	
	/**
	 * Регистрирует описание структуры и обработчики строительства или визуальных подсказок для этого типа бака.
	 */
	public void run() {
		registerTileClass(GTMTE_SingleTank.class.getCanonicalName(), new IMultiBlockInfoContainer<GTMTE_SingleTank>() {
			//region Structure
			private final IStructureDefinition<GTMTE_SingleTank> definition =
					StructureDefinition.<GTMTE_SingleTank>builder()
							.addShape("main", new String[][]{
									{"AAA", "A~A", "AAA"},
									{"CCC", "CBC", "CCC"},
									{"CCC", "CBC", "CCC"},
									{"CCC", "CBC", "CCC"},
									{"CCC", "CBC", "CCC"},
									{"CCC", "CBC", "CCC"},
									{"AAA", "AAA", "AAA"}
							})
							.addElement('A', ofBlock(sBlockCasings8, 0))
							.addElement('B', ofBlockAnyMeta(FluidTankBlock))
							.addElement('C', ofBlockAnyMeta(IGlassBlock))
							.build();
			private final String[] desc = new String[]{
					EnumChatFormatting.RED + "Impact Details:",
					"- Chemical Casing",
					"- Tank Storage Block (Tier 1-6)",
					"- I-Glass",
					"- Hatches (any Chemical Casing)",
					"- I/O Tank Hatch (any Chemical Casing or I-Glass (not angle))",
			};
			
			//endregion
			/**
			 * Размещает структуру бака либо показывает её подсказки через зарегистрированное описание.
			 *
			 * @param stackSize предмет, переданный системой подсказок структуры
			 * @param hintsOnly показывать только подсказки вместо размещения блоков
			 * @param tileEntity контроллер, для которого строится структура
			 * @param aSide ориентация структуры относительно контроллера
			 */
			@Override
			public void construct(ItemStack stackSize, boolean hintsOnly, GTMTE_SingleTank tileEntity, ExtendedFacing aSide) {
				IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
				definition.buildOrHints(tileEntity, stackSize, "main", base.getWorld(), aSide,
						base.getXCoord(), base.getYCoord(), base.getZCoord(),
						1, 1, 0, hintsOnly
				);
			}
			
			/**
			 * Возвращает описание материалов и допустимых хэтчей для системы строительства структуры.
			 *
			 * @param stackSize предмет, переданный системой подсказок структуры
			 * @return строки подсказки о составе структуры
			 */
			@Override
			public String[] getDescription(ItemStack stackSize) {
				return desc;
			}
		});
	}
	
	/**
	 * Добавляет I/O-хэтч в список структуры и назначает ему текстуру корпуса.
	 *
	 * @param aTileEntity кандидат на включение в список I/O-хэтчей
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
	 * Переключает уничтожение избытка жидкости, передаёт настройку обработчику и сообщает её игроку.
	 *
	 * @param aSide сторона блока
	 * @param aPlayer игрок, взаимодействующий с блоком
	 * @param aX координата клика по оси X внутри блока
	 * @param aY координата клика по оси Y внутри блока
	 * @param aZ координата клика по оси Z внутри блока
	 */
	@Override
	public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY,
										float aZ) {
		doVoidExcess = !doVoidExcess;
		markTankDirty();
		if (mfh != null) {
			mfh.setVoidExcess(doVoidExcess);
		}
		GT_Utility
			.sendChatToPlayer(aPlayer, doVoidExcess ? "Auto-voiding enabled" : "Auto-voiding disabled");
	}
	
	/**
	 * Формирует диагностические строки о сохранённых жидкостях, ёмкости и режиме уничтожения избытка.
	 *
	 * @return строки информации о состоянии бака
	 */
	@Override
	public String[] getInfoData() {
		final ArrayList<String> ll = new ArrayList<>();
		
		if (mfh != null) {
			ll.add(EnumChatFormatting.YELLOW + "Stored Fluid:" + EnumChatFormatting.RESET);
			for (int i = 0; i < mfh.fluids.size(); i++) {
				ll.add(
						i + " - " + mfh.fluids.get(i).getLocalizedName() + ": " + mfh.fluids.get(i).amount
								+ "L (" + (Math.round(100.0f * mfh.fluids.get(i).amount / mfh.getCapacity()))
								+ "%)");
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
	 * Сохраняет настройки контроллера и полное содержимое обработчика в тег {@code fluids}, затем состояние базовой машины.
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
	 * Восстанавливает настройки и содержимое бака, применяет режим уничтожения избытка и переподключает известные I/O-хэтчи.
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
	 * @param var1 предмет или тайл-сущность из контракта GregTech; в этом методе не используется
	 * @return 10000, что соответствует 100 процентам эффективности GregTech
	 */
	@Override
	public int getMaxEfficiency(ItemStack var1) {
		return 10000;
	}
	
	/**
	 * Задаёт загрязнение от рабочего цикла бака.
	 *
	 * @param var1 предмет или тайл-сущность из контракта GregTech; в этом методе не используется
	 * @return ноль: хранение жидкостей не создаёт загрязнение
	 */
	@Override
	public int getPollutionPerTick(ItemStack var1) {
		return 0;
	}
	
	/**
	 * Задаёт износ компонента при работе бака.
	 *
	 * @param var1 предмет или тайл-сущность из контракта GregTech; в этом методе не используется
	 * @return ноль: компоненты не изнашиваются
	 */
	@Override
	public int getDamageToComponent(ItemStack var1) {
		return 0;
	}
	
	/**
	 * Определяет реакцию бака на поломку компонента.
	 *
	 * @param var1 предмет или тайл-сущность из контракта GregTech; в этом методе не используется
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
        for (int i = 0; i < MAX_DISTINCT_FLUIDS; i++) {
            FluidStack fluid = FluidStack.loadFluidStackFromNBT(fluids.getCompoundTag(Integer.toString(i)));
            if (fluid != null) {
                tooltip.add(translateToLocalFormatted("impact.waila.tank.fluid",
                        NumberFormat.getNumberInstance().format(fluid.amount),
                        NumberFormat.getNumberInstance().format(capacity), fluid.getLocalizedName()));
            }
        }
    }

    @Override
    @Optional.Method(modid = "Waila")
    public void getWailaNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag,
                                World world, int x, int y, int z) {
        super.getWailaNBTData(player, tile, tag, world, x, y, z);
        NBTTagCompound fluids = new NBTTagCompound();
        if (mfh != null) {
            tag.setInteger("capacityPerFluid", mfh.getCapacity());
            int index = 0;
            for (FluidStack fluid : mfh.fluids) {
                fluids.setTag(Integer.toString(index++), fluid.writeToNBT(new NBTTagCompound()));
            }
        }
        tag.setTag("fluids", fluids);
    }
}
