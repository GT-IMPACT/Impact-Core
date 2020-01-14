package com.gwppcore.auxiliary;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import com.gwppcore.lib.Refstrings;
import com.gwppcore.gwppcore;

public class NEIGTgwppConfig implements IConfigureNEI
{
	@Override
    public void loadConfig()
    {
        // Hide metaID 0, as this is the generic item for trash bags
        gwppcore.Logger.info("Added NEI Config");
    }

    @Override
    public String getName()
    {
        return "GTgwpp-NEIConfig";
    }

    @Override
    public String getVersion()
    {
        return Refstrings.VERSION;
    }

}
