package com.gwppcore.gthandler.casings;

import com.gwppcore.gthandler.casings.GT_Block_CasingsSC;
import com.gwppcore.gthandler.casings.GT_Container_CasingsSC;

public class GT_Loader_CasingsSC implements Runnable {
    @Override
    public void run(){
        GT_Container_CasingsSC.sBlockCasingsSC = new GT_Block_CasingsSC();
    }
}
