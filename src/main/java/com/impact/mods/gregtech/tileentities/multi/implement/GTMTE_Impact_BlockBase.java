package com.impact.mods.gregtech.tileentities.multi.implement;

import net.minecraft.util.StatCollector;

import com.google.common.io.ByteArrayDataInput;
import com.impact.addon.gt.api.multis.IMachineRecipe;
import com.impact.addon.gt.api.multis.ISwitchRecipeMap;
import com.impact.addon.gt.api.other.IIndicatorProvider;
import gregtech.api.logic.ProcessingLogic;
import gregtech.api.recipe.check.CheckRecipeResult;
import com.impact.mods.gregtech.tileentities.hatches.lasers.GTMTE_LaserEnergy_In;
import com.impact.mods.gregtech.tileentities.hatches.lasers.GTMTE_LaserEnergy_Out;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import cpw.mods.fml.common.Optional;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GTMTE_Multi_Hatch_Input;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Input;
import gregtech.api.metatileentity.implementations.*;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.api.util.MultiblockTooltipBuilder;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import space.impact.packet_network.network.packets.IStreamPacketReceiver;
import space.impact.structures.alignment.constructable.ISurvivalConstructable;
import space.impact.structures.structure.IStructureDefinition;

import static gregtech.api.enums.GT_Values.V;
import static net.minecraft.util.StatCollector.translateToLocalFormatted;

public abstract class GTMTE_Impact_BlockBase<MULTIS extends GTMTE_Impact_BlockBase<MULTIS>>
        extends GT_MetaTileEntity_ProcessingMultiBlockBase<MULTIS>
        implements ISurvivalConstructable, ISwitchRecipeMap,
        IStreamPacketReceiver, IMachineRecipe, IIndicatorProvider {

    public final HashSet<GTMTE_LaserEnergy_In> mLaserIn = new HashSet<>();
    public final HashSet<GTMTE_LaserEnergy_Out> mLaserOut = new HashSet<>();
    private GT_Recipe.GT_Recipe_Map legacyRecipeMapToRestore;

    public GTMTE_Impact_BlockBase(final int aID, final String aName, final String aNameRegional) {
        super(aID, aName, aNameRegional, 2);
    }

    public GTMTE_Impact_BlockBase(final int aID, final String aName, final String aNameRegional, int slots) {
        super(aID, aName, aNameRegional, slots);
    }

    public GTMTE_Impact_BlockBase(final String aName, int slots) {
        super(aName, slots);
    }

    public GTMTE_Impact_BlockBase(final String aName) {
        super(aName, 2);
    }

    public static boolean isValidMetaTileEntity(MetaTileEntity aMetaTileEntity) {
        return aMetaTileEntity.getBaseMetaTileEntity() != null
                && aMetaTileEntity.getBaseMetaTileEntity().getMetaTileEntity() == aMetaTileEntity
                && !aMetaTileEntity.getBaseMetaTileEntity().isDead();
    }

    public ItemStack get() {
        return getStackForm(1L);
    }

    @NotNull
    @Override
    public String getLabel() {
        return "gt.blockmachines." + this.mName + ".name";
    }

    @NotNull
    @Override
    public ItemStack getStack() {
        return get();
    }

    @NotNull
    @Override
    public List<String> playersRecipients() {
        IGregTechTileEntity te = getBaseMetaTileEntity();
        if (te == null)
            return Collections.emptyList();
        return Collections.singletonList(te.getOwnerName());
    }

    @Override
    public boolean hasIndicator() {
        return false;
    }

    @Override
    public void onScrewdriverRightClick(byte side, EntityPlayer player, float x, float y, float z) {
        if (!player.isSneaking() && hasSwitchMap()) {
            if (cycleRecipeMap(false)) {
                GT_Utility.sendChatToPlayer(player, getMapName());
            }
        }
    }

    public boolean inputStack(IGregTechTileEntity te, int slotIndex, int side, ItemStack stack) {
        return false;
    }

    public boolean outputStack(IGregTechTileEntity te, int slotIndex, int side, ItemStack stack) {
        return false;
    }

    @Override
    public boolean allowPullStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return outputStack(aBaseMetaTileEntity, aIndex, aSide, aStack);
    }

    @Override
    public boolean allowPutStack(IGregTechTileEntity aBaseMetaTileEntity, int aIndex, byte aSide, ItemStack aStack) {
        return inputStack(aBaseMetaTileEntity, aIndex, aSide, aStack);
    }


    public int getMaxEfficiency(ItemStack aStack) {
        return 10000;
    }

    public boolean isCorrectMachinePart(ItemStack aStack) {
        return true;
    }

    public int getDamageToComponent(ItemStack aStack) {
        return 0;
    }

    public boolean explodesOnComponentBreak(ItemStack aStack) {
        return false;
    }

    public Vector3ic rotateOffsetVector(Vector3ic forgeDirection, int x, int y, int z) {
        final Vector3i offset = new Vector3i();

        // В любом направлении по оси Z
        if (forgeDirection.x() == 0 && forgeDirection.z() == -1) {
            offset.x = x;
            offset.y = y;
            offset.z = z;
        }
        if (forgeDirection.x() == 0 && forgeDirection.z() == 1) {
            offset.x = -x;
            offset.y = y;
            offset.z = -z;
        }
        // В любом направлении по оси X
        if (forgeDirection.x() == -1 && forgeDirection.z() == 0) {
            offset.x = z;
            offset.y = y;
            offset.z = -x;
        }
        if (forgeDirection.x() == 1 && forgeDirection.z() == 0) {
            offset.x = -z;
            offset.y = y;
            offset.z = x;
        }
        // в любом направлении по оси Y
        if (forgeDirection.y() == -1) {
            offset.x = x;
            offset.y = z;
            offset.z = y;
        }
        return offset;
    }

    @Override
    public boolean addEnergyOutput(long aEU) {
        if (aEU <= 0) {
            return true;
        }
        if (mDynamoHatches.size() > 0 || mDynamoHatchesMulti.size() > 0 || mLaserOut.size() > 0) {
            return addEnergyOutputMultipleDynamos(aEU, true);
        }
        return super.addEnergyOutput(aEU);
    }

    @Override
    protected List<GT_MetaTileEntity_Hatch> getProcessingEnergyHatches() {
        List<GT_MetaTileEntity_Hatch> hatches = super.getProcessingEnergyHatches();
        hatches.addAll(mLaserIn);
        return hatches;
    }

    @Override
    protected List<GT_MetaTileEntity_Hatch> getProcessingDynamos() {
        List<GT_MetaTileEntity_Hatch> hatches = super.getProcessingDynamos();
        hatches.addAll(mLaserOut);
        return hatches;
    }

    @Override
    public long getMaxInputVoltage() {
        long voltage = 0;
        for (GT_MetaTileEntity_Hatch hatch : getProcessingEnergyHatches()) {
            if (isValidMetaTileEntity(hatch)) {
                voltage = Math.addExact(voltage, hatch.getBaseMetaTileEntity().getInputVoltage());
            }
        }
        return voltage;
    }

    public long getMaxOutputVoltage() {
        long rVoltage = 0;
        for (GT_MetaTileEntity_Hatch_Dynamo tHatch : mDynamoHatches) {
            if (isValidMetaTileEntity(tHatch)) {
                rVoltage += tHatch.getBaseMetaTileEntity().getOutputVoltage() * tHatch.mAmpers;
            }
        }
        for (GTMTE_LaserEnergy_Out tHatch : mLaserOut) {
            if (isValidMetaTileEntity(tHatch)) {
                rVoltage += tHatch.getBaseMetaTileEntity().getOutputVoltage() * tHatch.Amp;
            }
        }
        for (GT_MetaTileEntity_Hatch_DynamoMulti tHatch : mDynamoHatchesMulti) {
            if (isValidMetaTileEntity(tHatch)) {
                rVoltage += tHatch.getBaseMetaTileEntity().getOutputVoltage() * tHatch.Amp;
            }
        }
        return rVoltage;
    }

    public int getTierEnergyHatch() {
        int aTier = 0;
        for (GT_MetaTileEntity_Hatch_Energy tEHatch : mEnergyHatches) {
            if (isValidMetaTileEntity(tEHatch)) {
                if (aTier < tEHatch.mTier)
                    aTier = tEHatch.mTier;
            }
        }
        for (GTMTE_LaserEnergy_In tEHatch : mLaserIn) {
            if (isValidMetaTileEntity(tEHatch)) {
                if (aTier < tEHatch.mTier)
                    aTier = tEHatch.mTier;
            }
        }
        for (GT_MetaTileEntity_Hatch_EnergyMulti tEHatch : mEnergyHatchesMulti) {
            if (isValidMetaTileEntity(tEHatch)) {
                if (aTier < tEHatch.mTier)
                    aTier = tEHatch.mTier;
            }
        }
        return aTier;
    }

    @Override
    public void loadNBTData(NBTTagCompound tag) {
        super.loadNBTData(tag);
        legacyRecipeMapToRestore = null;
        if (!tag.hasKey("processing")) {
            setProcessingModes(tag.getBoolean("isSeparated")
                    ? ProcessingLogic.SEPARATE_INPUTS : 0);
            List<GT_Recipe.GT_Recipe_Map> maps = getRecipesMap();
            int index = tag.getByte("mRecipeMode");
            if (!maps.isEmpty()) {
                GT_Recipe.GT_Recipe_Map map = maps.get(index >= 0 && index < maps.size() ? index : 0);
                if (getBaseMetaTileEntity().getWorld() == null) {
                    legacyRecipeMapToRestore = map;
                } else {
                    setActiveRecipeMap(map);
                }
            }
        }
    }

    @Override
    public void saveNBTData(NBTTagCompound tag) {
        super.saveNBTData(tag);
        if (legacyRecipeMapToRestore != null) {
            tag.getCompoundTag("processing").setString("recipeMap", legacyRecipeMapToRestore.mUnlocalizedName);
        }
    }

    @Override
    public void onFirstTick(IGregTechTileEntity base) {
        super.onFirstTick(base);
        if (legacyRecipeMapToRestore != null) {
            setActiveRecipeMap(legacyRecipeMapToRestore);
            legacyRecipeMapToRestore = null;
        }
    }

    public int getPollutionPerTick(ItemStack aStack) {
        return 0;
    }

    public abstract boolean machineStructure(IGregTechTileEntity thisController);

    @Override
    public boolean checkMachine(IGregTechTileEntity thisController, ItemStack guiSlotItem) {
        clearHatches();
        boolean check = machineStructure(thisController);
        return check;
    }

    public void clearHatches() {
        mLaserIn.clear();
        mLaserOut.clear();
    }

    @Override
    public void explodeMultiblock() {
        for (MetaTileEntity tTileEntity : mLaserOut)
            tTileEntity.getBaseMetaTileEntity().doExplosion(V[8]);
        for (MetaTileEntity tTileEntity : mLaserIn)
            tTileEntity.getBaseMetaTileEntity().doExplosion(V[8]);
        super.explodeMultiblock();
    }

    @Override
    public boolean addToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {

        if (aTileEntity == null)
            return false;
        IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
        if (aMetaTileEntity == null)
            return false;
        if (aMetaTileEntity instanceof GT_MetaTileEntity_Hatch) {
            ((GT_MetaTileEntity_Hatch) aMetaTileEntity).updateTexture(aBaseCasingIndex);
        }
        if (aMetaTileEntity instanceof GTMTE_LaserEnergy_In)
            return mLaserIn.add((GTMTE_LaserEnergy_In) aMetaTileEntity);
        if (aMetaTileEntity instanceof GTMTE_LaserEnergy_Out)
            return mLaserOut.add((GTMTE_LaserEnergy_Out) aMetaTileEntity);

        return super.addToMachineList(aTileEntity, aBaseCasingIndex);
    }

    @Override
    public boolean addEnergyInputToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) {
            return false;
        }
        IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
        if (aMetaTileEntity == null)
            return false;
        if (aMetaTileEntity instanceof GTMTE_LaserEnergy_In) {
            ((GTMTE_LaserEnergy_In) aMetaTileEntity).updateTexture(aBaseCasingIndex);
            return mLaserIn.add((GTMTE_LaserEnergy_In) aMetaTileEntity);
        }
        return super.addEnergyInputToMachineList(aTileEntity, aBaseCasingIndex);
    }

    @Override
    public boolean addDynamoToMachineList(IGregTechTileEntity aTileEntity, int aBaseCasingIndex) {
        if (aTileEntity == null) {
            return false;
        }
        IMetaTileEntity aMetaTileEntity = aTileEntity.getMetaTileEntity();
        if (aMetaTileEntity == null)
            return false;
        if (aMetaTileEntity instanceof GTMTE_LaserEnergy_Out) {
            ((GTMTE_LaserEnergy_Out) aMetaTileEntity).updateTexture(aBaseCasingIndex);
            return mLaserOut.add((GTMTE_LaserEnergy_Out) aMetaTileEntity);
        }
        return super.addDynamoToMachineList(aTileEntity, aBaseCasingIndex);
    }

    protected abstract MultiblockTooltipBuilder createTooltip();

    public abstract IStructureDefinition<MULTIS> getStructureDefinition();

    @Override
    public abstract IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity);

    protected final boolean buildPiece(ItemStack trigger, boolean hintOnly, int x, int y, int z) {
        return buildPiece("main", trigger, hintOnly, x, y, z);
    }

    protected final boolean checkPiece(int x, int y, int z) {
        return checkPiece("main", x, y, z);
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new ProcessingLogic()
                .setRecipeMapSupplier(this::getRecipeMap)
                .setOutputChances(usesRecipeOutputChances())
                .setRecipeValidator(this::validateRecipe);
    }

    protected boolean usesRecipeOutputChances() {
        return false;
    }

    protected CheckRecipeResult validateRecipe(GT_Recipe recipe) {
        return CheckRecipeResult.SUCCESS;
    }

    @Override
    public int getCircuitSlot() {
        return -1;
    }

    @Override
    protected List<GT_MetaTileEntity_Hatch> getProcessingInputBuses() {
        List<GT_MetaTileEntity_Hatch> hatches = super.getProcessingInputBuses();
        for (GT_MetaTileEntity_Hatch_Input hatch : mInputHatches) {
            if (!(hatch instanceof GTMTE_Multi_Hatch_Input)) {
                hatches.add(hatch);
            }
        }
        hatches.sort(Comparator.comparingInt(hatch -> hatch.idHatch));
        return hatches;
    }

    @Override
    protected GT_Recipe.GT_Recipe_Map getDefaultRecipeMap() {
        List<GT_Recipe.GT_Recipe_Map> maps = getRecipesMap();
        return maps.isEmpty() ? null : maps.get(Math.min(1, maps.size() - 1));
    }

    @Override
    public List<GT_Recipe.GT_Recipe_Map> getRecipeMaps() {
        return getRecipesMap();
    }

    @NotNull
    @Override
    public List<GT_Recipe.GT_Recipe_Map> getRecipesMap() {
        return Collections.emptyList();
    }

    @Override
    public void onChangeRecipeMap(@NotNull GT_Recipe.GT_Recipe_Map map, @NotNull EntityPlayer player) {
        if (setActiveRecipeMap(map)) {
            GT_Utility.sendChatToPlayer(player, getMapName());
        }
    }

    @Override
    public void receive(@NotNull ByteArrayDataInput data) {
        // Old clients can still send this display-only packet. The active map is server-owned.
        data.readUTF();
    }

    @Override
    public boolean hasSwitchMap() {
        return false;
    }

    @NotNull
    @Override
    public String getMapName() {
        GT_Recipe.GT_Recipe_Map map = getRecipeMap();
        return map == null ? "" : GT_LanguageManager.getTranslation(map.mUnlocalizedName);
    }

    @Override
    public int getEUt() {
        return mEUt;
    }

    @Override
    public void setEUt(int value) {
        this.mEUt = value;
    }

    @Override
    public int getMaxProgressTime() {
        return mMaxProgresstime;
    }

    @Override
    public void setMaxProgressTime(int value) {
        this.mMaxProgresstime = value;
    }

    @Override
    @Optional.Method(modid = "Waila")
    public void getWailaBody(ItemStack stack, List<String> tooltip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        super.getWailaBody(stack, tooltip, accessor, config);
        NBTTagCompound tag = accessor.getNBTData();
        String map = tag.getString("recipe_map_switch");
        if (!map.isEmpty()) {
            tooltip.add(translateToLocalFormatted("impact.waila.recipe_map",
                    EnumChatFormatting.YELLOW + GT_LanguageManager.getTranslation(map) + EnumChatFormatting.RESET));
        }

    }

    @Override
    @Optional.Method(modid = "Waila")
    public void getWailaNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag,
            World world, int x, int y, int z) {
        super.getWailaNBTData(player, tile, tag, world, x, y, z);
        GT_Recipe.GT_Recipe_Map map = getRecipeMap();
        tag.setString("recipe_map_switch", map == null ? "" : map.mUnlocalizedName);

    }

}
