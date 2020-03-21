package com.impact.auxiliary;

import codechicken.nei.api.IConfigureNEI;
import com.impact.lib.Refstrings;
import com.impact.impact;

public class NEIGTgwppConfig implements IConfigureNEI
{
	@Override
    public void loadConfig()
    {
        // Hide metaID 0, as this is the generic item for trash bags
        impact.Logger.info("Added NEI Config");
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
