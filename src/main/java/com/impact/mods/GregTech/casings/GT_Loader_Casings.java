package com.impact.mods.GregTech.casings;

import com.impact.mods.GregTech.casings.Page3_0_15.GT_Block_Case1;
import com.impact.mods.GregTech.casings.Page3_16_31.GT_Block_Case2;
import com.impact.mods.GregTech.casings.Page8_64_79.GT_Block_Page8_3;
//import com.impact.mods.GregTech.casings.glass1.GT_Block_Glass1;

public class GT_Loader_Casings implements Runnable {
    @Override
    public void run(){
        CORE_API.sCaseCore1 = new GT_Block_Case1();
        CORE_API.sCaseCore2 = new GT_Block_Case2();
        CORE_API.sCasePage8_3 = new GT_Block_Page8_3();
//        CORE_API.sGlassCore1 = new GT_Block_Glass1();

    }
}
