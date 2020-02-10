package com.gwppcore.gthandler.casings;

import com.gwppcore.gthandler.casings.GT_Block_CasingsParall;
import com.gwppcore.gthandler.casings.GT_Container_CasingsParall;

public class GT_Loader_CasingsParall implements Runnable {
    @Override
    public void run(){
        GT_Container_CasingsParall.sBlockCasingsParall = new GT_Block_CasingsParall();
    }
}
