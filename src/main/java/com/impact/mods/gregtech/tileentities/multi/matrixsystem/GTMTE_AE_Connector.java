package com.impact.mods.gregtech.tileentities.multi.matrixsystem;

import appeng.api.networking.GridFlags;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.networking.security.IActionHost;
import appeng.api.networking.security.MachineSource;
import appeng.api.util.AECableType;
import appeng.me.GridAccessException;
import appeng.me.helpers.AENetworkProxy;
import appeng.me.helpers.IGridProxyable;
import com.impact.mods.gregtech.enums.Texture;
import cpw.mods.fml.common.Optional;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.render.TextureFactory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;

public class GTMTE_AE_Connector extends GT_MetaTileEntity_Hatch {

    private BaseActionSource requestSource = null;
    private AENetworkProxy gridProxy = null;

    public GTMTE_AE_Connector(int aID, String aName, String aNameRegional, ITexture... aTextures) {
        super(aID, aName, aNameRegional, 5, 1, "", aTextures);
    }

    public GTMTE_AE_Connector(String aName, int aTier, int aInvSlotCount, String[] aDescription, ITexture[][][] aTextures) {
        super(aName, aTier, aInvSlotCount, aDescription, aTextures);
    }
    
    
    @Override
    public boolean isFacingValid(byte aFacing) {
        return true;
    }
    
    @Override
    public boolean isSimpleMachine() {
        return true;
    }
    
    @Override
    public boolean isAccessAllowed(EntityPlayer aPlayer) {
        return true;
    }
    
    @Override
    public ITexture[] getTexturesActive(ITexture iTexture) {
        return new ITexture[]{iTexture, TextureFactory.of(Texture.Icons.OVERLAY_AE_CONNECTOR_ACTIVE)};
    }

    @Override
    public ITexture[] getTexturesInactive(ITexture iTexture) {
        return new ITexture[]{iTexture, TextureFactory.of(Texture.Icons.OVERLAY_AE_CONNECTOR_INACTIVE)};
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_AE_Connector(mName, mTier, mInventory.length, mDescriptionArray, mTextures);
    }

    @Override
    public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
        super.onPostTick(aBaseMetaTileEntity, aTick);
    }

    @Override
    public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
        super.onFirstTick(aBaseMetaTileEntity);
        getProxy();
    }

    @Override
    public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
        return false;
    }

    @Optional.Method(modid = "appliedenergistics2")
    private BaseActionSource getRequest() {
        if (requestSource == null)
            requestSource = new MachineSource((IActionHost)getBaseMetaTileEntity());
        return requestSource;
    }

    @Override
    @Optional.Method(modid = "appliedenergistics2")
    public AECableType getCableConnectionType(ForgeDirection forgeDirection) {
        super.getCableConnectionType(forgeDirection);
        return AECableType.SMART;
    }

    @Override
    @Optional.Method(modid = "appliedenergistics2")
    public AENetworkProxy getProxy() {
        if (gridProxy == null) {
            if (getBaseMetaTileEntity() instanceof IGridProxyable) {
                gridProxy = new AENetworkProxy((IGridProxyable)getBaseMetaTileEntity(), "proxy", getStackForm(1), true);
                gridProxy.onReady();
                gridProxy.setFlags(GridFlags.REQUIRE_CHANNEL);
            }
        }
        return this.gridProxy;
    }

    @Optional.Method(modid = "appliedenergistics2")
    public boolean isValid(Object verificationToken) {
        try {
            getBaseMetaTileEntity().setActive(getProxy().getGrid() == verificationToken);
            return (getProxy().getGrid() == verificationToken);
        } catch (GridAccessException e) {
            getBaseMetaTileEntity().setActive(false);
            return false;
        }
    }
}