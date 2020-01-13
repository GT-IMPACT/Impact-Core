package com.gwppcore.gthandler;


public class GT_CustomLoader {

        private static final GT_Loader_Items ItemLoader = new GT_Loader_Items();
        private static final GT_MachineRecipeLoader MachineRecipeLoader = new GT_MachineRecipeLoader();

        public static void run()
        {
            ItemLoader.run();
            MachineRecipeLoader.run();
        }

    }
