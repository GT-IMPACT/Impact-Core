package com.impact.mods.gregtech.tileentities.multi.implement;

import com.impact.util.multis.OverclockCalculate;
import com.impact.util.multis.WorldProperties;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.impact.util.recipe.RecipeHelper.calcTimeParallel;
import static com.impact.util.recipe.RecipeHelper.resizeItemStackSizeChance;
import static gregtech.api.enums.GT_Values.V;

/**
 * [WIP] Билдер для проверки рецепта Мульти Блоков
 *
 * TODO need test
 */
public class MultiBlockRecipe {
	
	public GT_MetaTileEntity_MultiBlockBase multiBlock = null;
	public FluidStack[] fluidsIn = null;
	public ItemStack[] itemsIn = null;
	public long voltageIn = 0;
	public int tierFromVoltage = 0, parallel = 1;
	public GT_Recipe tRecipe = null;
	public boolean preFoundRecipe = false, finallyFoundRecipe = false;
	
	/**
	 * Примеры
	 */
	public void examples() {
		MultiBlockRecipe parallel = new MultiBlockRecipe.Builder(null)
				.checkParallel()
				.sortItems(null)
				.sortFluids(null)
				.checkVoltage(100)
				.checkRecipeMap(null, false, false)
				.checkCleanRoom()
				.checkLowGravity()
				.checkOutsParallel(true, false, true)
				.setEfficiency(10000, 1000)
				.checkEU(((GT_MetaTileEntity_MultiParallelBlockBase<?>) multiBlock).mCheckParallelCurrent)
				.build();
		
		MultiBlockRecipe vanila = new MultiBlockRecipe.Builder(null)
				.sortFluidGT(null)
				.sortItemsGT(null)
				.checkVoltage(100)
				.checkRecipeMap(null, false, false)
				.checkCleanRoom()
				.setEfficiency(10000, 1000)
				.checkEUVanila(4, 2)
				.checkOuts(true, false)
				.build();
	}
	
	public static class Builder {
		
		private final MultiBlockRecipe recipe;
		
		/**
		 * Инициализация Билдера
		 * @param multiBlock Instance МультиБлока
		 */
		public Builder(GT_MetaTileEntity_MultiBlockBase multiBlock) {
			recipe            = new MultiBlockRecipe();
			recipe.multiBlock = multiBlock;
		}
		
		/**
		 * Окончание билдера
		 * @return если рецепт найден вернет - рецепт, если нет - null
		 */
		public MultiBlockRecipe build() {
			if (recipe.finallyFoundRecipe) {
				return recipe;
			}
			return null;
		}
		
		/**
		 * 	Проверка подключения параллельности
		 */
		public Builder checkParallel() {
			GT_MetaTileEntity_MultiParallelBlockBase<?> base = (GT_MetaTileEntity_MultiParallelBlockBase<?>) recipe.multiBlock;
			if (base.sParallHatchesIn.size() > 0 && base.getRecipeCheckParallel()) {
				recipe.finallyFoundRecipe = false;
			}
			return this;
		}
		
		/**
		 * Сортировка жидкостей дефолтная из GT5
		 * @param fluids список жидкостей {@link GT_MetaTileEntity_MultiBlockBase#getStoredFluids()}
		 */
		public Builder sortFluidGT(List<FluidStack> fluids) {
			int tFluidList_sS = fluids.size();
			for (int i = 0; i < tFluidList_sS - 1; i++) {
				for (int j = i + 1; j < tFluidList_sS; j++) {
					if (GT_Utility.areFluidsEqual(fluids.get(i), fluids.get(j))) {
						if (fluids.get(i).amount >= fluids.get(j).amount) {
							fluids.remove(j--);
							tFluidList_sS = fluids.size();
						} else {
							fluids.remove(i--);
							tFluidList_sS = fluids.size();
							break;
						}
					}
				}
			}
			recipe.fluidsIn = fluids.toArray(new FluidStack[0]);
			return this;
		}
		
		/**
		 * Сортировка предметов дефолтная из GT5
		 * @param items список предметов {@link GT_MetaTileEntity_MultiBlockBase#getStoredInputs()}
		 */
		public Builder sortItemsGT(List<ItemStack> items) {
			int tInputList_sS = items.size();
			for (int i = 0; i < tInputList_sS - 1; i++) {
				for (int j = i + 1; j < tInputList_sS; j++) {
					if (GT_Utility.areStacksEqual(items.get(i), items.get(j))) {
						if ((items.get(i)).stackSize >= (items.get(j)).stackSize) {
							items.remove(j--);
							tInputList_sS = items.size();
						} else {
							items.remove(i--);
							tInputList_sS = items.size();
							break;
						}
					}
				}
			}
			items.add(recipe.multiBlock.mInventory[1]);
			recipe.itemsIn = items.toArray(new ItemStack[0]);
			return this;
		}
		
		/**
		 * Мнимая сортировка, каст из списка в массив
		 * @param items список предметов {@link GT_MetaTileEntity_MultiBlockBase#getStoredInputs()}
		 */
		public Builder sortItems(List<ItemStack> items) {
			recipe.itemsIn = items.toArray(new ItemStack[0]);
			return this;
		}
		
		/**
		 * Мнимая сортировка, каст из списка в массив
		 * @param fluids список жидкостей {@link GT_MetaTileEntity_MultiBlockBase#getStoredFluids()}
		 */
		public Builder sortFluids(List<FluidStack> fluids) {
			recipe.fluidsIn = fluids.toArray(new FluidStack[0]);
			return this;
		}
		
		/**
		 * Проверка входного вольтажа + проверка входных хэтчей на пустоту
		 * @param voltage входной вольтаж {@link GT_MetaTileEntity_MultiBlockBase#getMaxInputVoltage()}
		 */
		public Builder checkVoltage(long voltage) {
			if (recipe.itemsIn.length > 0 || recipe.fluidsIn.length > 0) {
				recipe.finallyFoundRecipe = true;
			}
			if (recipe.finallyFoundRecipe) {
				recipe.voltageIn       = voltage;
				recipe.tierFromVoltage = Math.max(1, GT_Utility.getTier(voltage));
			}
			return this;
		}
		
		/**
		 * Поиск рецепта по мапе рецептов
		 * @param rMap мапа рецептов {@link GT_MetaTileEntity_MultiBlockBase#getRecipeMap()}
		 * @param aNotUnificated условие для унификации (обычно false)
		 * @param aDontCheckStackSizes проверка количество предметов для проверяемого слота, если false то не проверяет и выполняет рецепт из всех слотов
		 */
		public Builder checkRecipeMap(GT_Recipe.GT_Recipe_Map rMap, boolean aNotUnificated, boolean aDontCheckStackSizes) {
			if (recipe.finallyFoundRecipe) {
				recipe.tRecipe = rMap.findRecipe(
						recipe.multiBlock.getBaseMetaTileEntity(),
						aNotUnificated,
						aDontCheckStackSizes,
						V[recipe.tierFromVoltage],
						recipe.fluidsIn,
						recipe.itemsIn
				);
			}
			return this;
		}
		
		/**
		 * Проверять условия для чистой комнаты
		 */
		public Builder checkCleanRoom() {
			if (recipe.finallyFoundRecipe) {
				recipe.finallyFoundRecipe = WorldProperties.needSpace(recipe.tRecipe, recipe.multiBlock);
			}
			return this;
		}
		
		/**
		 * Проверять условия для низкой гравитации
		 */
		public Builder checkLowGravity() {
			if (recipe.finallyFoundRecipe) {
				recipe.finallyFoundRecipe = WorldProperties.needCleanroom(recipe.tRecipe, recipe.multiBlock);
			}
			return this;
		}
		
		/**
		 * Установить значения эффективности
		 * @param max максимальная эффективность (обычно {@code (10000 - (getIdealStatus() - getRepairStatus()) * 1000})
		 * @param increase коэффициент повышения эффективности
		 */
		public Builder setEfficiency(int max, int increase) {
			if (recipe.finallyFoundRecipe) {
				recipe.multiBlock.mEfficiency         = max;
				recipe.multiBlock.mEfficiencyIncrease = increase;
			}
			return this;
		}
		
		/**
		 * Параметры оверклока, установка макс. времени рецепта и потребление энергии
		 * @param coefEU коэффициент потребления (обычно: 4)
		 * @param coefTime коэффициент времени (обычно: 2)
		 */
		public Builder checkEUVanila(int coefEU, int coefTime) {
			if (recipe.finallyFoundRecipe) {
				int EUt = recipe.tRecipe.mEUt;
				int maxProgressTime = recipe.tRecipe.mDuration;
				while (EUt <= V[recipe.tierFromVoltage - 1] && maxProgressTime > 2) {
					EUt *= coefEU;
					maxProgressTime /= coefTime;
				}
				if (maxProgressTime < 2) {
					maxProgressTime = 2;
					EUt             = recipe.tRecipe.mEUt * recipe.tRecipe.mDuration / 2;
				}
				
				recipe.multiBlock.mEUt             = -EUt;
				recipe.multiBlock.mMaxProgresstime = maxProgressTime;
			}
			return this;
		}
		
		/**
		 * Параметры оверклока, установка макс. времени рецепта и потребление энергии
		 * @param coef коэффициент параллельности (обычно: 1, для параллельности: {@link GT_MetaTileEntity_MultiParallelBlockBase#mCheckParallelCurrent}
		 */
		public Builder checkEU(int coef) {
			if (recipe.finallyFoundRecipe) {
				GT_MetaTileEntity_MultiParallelBlockBase<?> base = (GT_MetaTileEntity_MultiParallelBlockBase<?>) recipe.multiBlock;
				long actualEUT = (long) (recipe.tRecipe.mEUt) * coef;
				if (actualEUT > Integer.MAX_VALUE) {
					byte divider = 0;
					while (actualEUT > Integer.MAX_VALUE) {
						actualEUT = actualEUT / 2;
						divider++;
					}
					OverclockCalculate.calculateOverclockedNessMulti((int) (actualEUT / (divider * 2)),
							recipe.tRecipe.mDuration * (divider * 2), 1, recipe.voltageIn, base
					);
				} else {
					OverclockCalculate.calculateOverclockedNessMulti((int) actualEUT,
							recipe.tRecipe.mDuration, 1, recipe.voltageIn, base
					);
				}
				
				if (base.mMaxProgresstime == Integer.MAX_VALUE - 1 && base.mEUt == Integer.MAX_VALUE - 1) {
					recipe.finallyFoundRecipe = false;
				}
				base.mMaxProgresstime = calcTimeParallel(base);
				base.mEUt             = base.mEUt > 0 ? (-base.mEUt) : base.mEUt;
			}
			return this;
		}
		
		/**
		 * Установка выходных предметов по рецепту
		 */
		public Builder outItemsDefault() {
			if (recipe.finallyFoundRecipe) {
				recipe.multiBlock.mOutputItems = recipe.tRecipe.mOutputs;
			}
			return this;
		}
		
		/**
		 * Установка выходных жидкостей по рецепту
		 */
		public Builder outFluidsDefault() {
			if (recipe.finallyFoundRecipe) {
				recipe.multiBlock.mOutputFluids = recipe.tRecipe.mFluidOutputs;
			}
			return this;
		}
		
		/**
		 * Установка выходных предметов и жидкостей исходя из работы машины
		 * @param aDecreaseStackSizeBySuccess -
		 * @param aDontCheckStackSizes -
		 */
		public Builder checkOuts(boolean aDecreaseStackSizeBySuccess, boolean aDontCheckStackSizes) {
			if (recipe.finallyFoundRecipe) {
				List<ItemStack> outputItems = new ArrayList<>();
				List<FluidStack> outputFluids = new ArrayList<>();
				int processed = 0;
				recipe.preFoundRecipe = false;
				boolean isNoEmptyHatches = recipe.itemsIn.length > 0 || recipe.fluidsIn.length > 0;
				while (isNoEmptyHatches && processed < 1) {
					if ((recipe.tRecipe.mEUt * (processed + 1L)) < recipe.voltageIn) {
						if (recipe.tRecipe.isRecipeInputEqual(
								aDecreaseStackSizeBySuccess, aDontCheckStackSizes, recipe.fluidsIn, recipe.itemsIn)) {
							
							recipe.preFoundRecipe = true;
							
							outputItems.addAll(Arrays.asList(recipe.tRecipe.mOutputs));
							outputFluids.addAll(Arrays.asList(recipe.tRecipe.mFluidOutputs));
						}
						++processed;
					} else {
						break;
					}
				}
				if (recipe.preFoundRecipe) {
					recipe.multiBlock.mOutputItems  = outputItems.toArray(new ItemStack[0]);
					recipe.multiBlock.mOutputFluids = outputFluids.toArray(new FluidStack[0]);
				}
			}
			return this;
		}
		
		/**
		 * Установка выходных предметов и жидкостей исходя из работы параллельной машины
		 * @param aDecreaseStackSizeBySuccess -
		 * @param aDontCheckStackSizes -
		 * @param chanceEnabled -
		 */
		public Builder checkOutsParallel(boolean aDecreaseStackSizeBySuccess, boolean aDontCheckStackSizes, boolean chanceEnabled) {
			if (recipe.finallyFoundRecipe) {
				ItemStack[] tOut = new ItemStack[recipe.tRecipe.mOutputs.length];
				List<FluidStack> outputFluids = new ArrayList<>();
				GT_MetaTileEntity_MultiParallelBlockBase<?> base = (GT_MetaTileEntity_MultiParallelBlockBase<?>) recipe.multiBlock;
				base.mCheckParallelCurrent = 0;
				recipe.preFoundRecipe      = false;
				while (((recipe.itemsIn.length | recipe.fluidsIn.length) > 0) && base.mCheckParallelCurrent < base.mParallel) {
					if ((recipe.tRecipe.mEUt * (base.mCheckParallelCurrent + 1L)) < recipe.voltageIn) {
						if (recipe.tRecipe.isRecipeInputEqual(
								aDecreaseStackSizeBySuccess, aDontCheckStackSizes, recipe.fluidsIn, recipe.itemsIn)) {
							
							recipe.preFoundRecipe = true;
							outputFluids.addAll(Arrays.asList(recipe.tRecipe.mFluidOutputs));
							
							for (int h = 0; h < recipe.tRecipe.mOutputs.length; h++) {
								if (recipe.tRecipe.getOutput(h) != null) {
									tOut[h]           = recipe.tRecipe.getOutput(h).copy();
									tOut[h].stackSize = 0;
								}
							}
						}
						++base.mCheckParallelCurrent;
					} else {
						break;
					}
				}
				
				if (recipe.preFoundRecipe) {
					recipe.multiBlock.mOutputItems  = resizeItemStackSizeChance(tOut, recipe.tRecipe, base, chanceEnabled);
					recipe.multiBlock.mOutputFluids = outputFluids.toArray(new FluidStack[0]);
				}
			}
			return this;
		}
	}
}