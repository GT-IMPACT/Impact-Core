package com.gwppcore.GTHandler;

import com.gwppcore.Item.ItemList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;


    public class GT_CustomLoader {

        private static final GT_Loader_Items ItemLoader = new GT_Loader_Items();
        private static final GT_MachineRecipeLoader MachineRecipeLoader = new GT_MachineRecipeLoader();

        public static void run()
        {
            ItemLoader.run();
            MachineRecipeLoader.run();
        }

    }
