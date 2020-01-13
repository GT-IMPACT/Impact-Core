package com.gwppcore.config;

import eu.usrv.yamcore.config.ConfigManager;
import java.io.File;

    public class CoreModConfig extends ConfigManager{


        public CoreModConfig( File pConfigBaseDirectory, String pModCollectionDirectory, String pModID )
        {
            super( pConfigBaseDirectory, pModCollectionDirectory, pModID );

        }

        public boolean ModAdminErrorLogs_Enabled;
        public boolean ModItemInHandInfo_Enabled;

        @Override
        protected void PreInit()
        {
            ModAdminErrorLogs_Enabled = true;
            ModItemInHandInfo_Enabled = false;
        }

        @Override
        protected void Init()
        {
            ModItemInHandInfo_Enabled = _mainConfig.getBoolean( "ItemInHandInfo", "Modules", ModItemInHandInfo_Enabled, "Set to true to enable ItemInHandInfo module. If enabled, type /iih to display the item's name-info" );
            ModAdminErrorLogs_Enabled = _mainConfig.getBoolean( "AdminErrorLog", "Modules", ModAdminErrorLogs_Enabled, "If set to true, every op/admin will receive all errors occoured during the startup phase as ingame message on join" );
        }

        @Override
        protected void PostInit()
        {

        }
    }

