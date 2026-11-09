package com.impact.mods.gregtech.tileentities.multi.ores;

import gregtech.api.logic.ProcessingLogic;
import gregtech.api.logic.ProcessingInputs;
import gregtech.api.logic.WorldProcessingLogic;
import gregtech.api.recipe.check.CheckRecipeResult;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import com.impact.addon.vw.VirtualWorldScan;
import com.impact.mods.gregtech.blocks.HatchCounts;
import com.impact.util.ImpactMultiblockTooltipBuilder;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.enums.Texture;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.mods.gregtech.tileentities.multi.ores.hatches.GTMTE_EnrichmentUnit;
import com.impact.mods.gregtech.tileentities.multi.ores.hatches.GTMTE_OreHatch;
import cpw.mods.fml.common.Optional;
import gregtech.api.enums.Casings;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.IHatchElement;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.XSTR;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fluids.FluidStack;
import space.gtimpact.virtual_world.api.core.WorldPos;
import space.gtimpact.virtual_world.api.resources.ores.OreVein;
import space.gtimpact.virtual_world.api.services.scanning.ores.OreVeinScanResult;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static gregtech.api.util.GTStructureUtility.ofFrame;
import static net.minecraft.util.StatCollector.translateToLocalFormatted;
import static space.impact.structures.structure.StructureUtility.lazy;

public class GTMTE_AdvancedMiner extends GTMTE_Impact_BlockBase<GTMTE_AdvancedMiner> {

    private final List<GTMTE_OreHatch> hatch = new ArrayList<>();
    private final List<GTMTE_EnrichmentUnit> enrich = new ArrayList<>();
    public int cycleIncrease = 0, sizeVeinPreStart = 0, maxVeinSize = 0, drillLevel = 0;
    public List<Chunk> chunks = new ArrayList<>();
    public OreVein oreVein;
    public int layer = 1;
    public double cashedDecrement = 0d;

    private static final ICasing CASING = Casings.HSLA;
    private int casingCount;

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 16).build();

    private GT_Recipe cashedMaceratorRecipe;
    private GT_Recipe cashedFlotationRecipe;

    private static IStructureDefinition<GTMTE_AdvancedMiner> definition;

    public GTMTE_AdvancedMiner(int aID, String aNameRegional) {
        super(aID, "impact.multis.miner.advanced", aNameRegional);
    }

    public GTMTE_AdvancedMiner(String aName) {
        super(aName);
    }

    @Override
    public boolean hasIndicator() {
        return true;
    }

    @Override
    public void onFirstTick(IGregTechTileEntity te) {
        super.onFirstTick(te);
        initOreProperty(te);
    }

    public void increaseLayer(IGregTechTileEntity te) {
        if (te.isServerSide()) {
            if (cycleIncrease <= 0)
                return;

            WorldPos pos = new WorldPos(te.getXCoord(), te.getZCoord());

            kotlin.Pair<Integer, Integer> pair = VirtualWorldScan
                    .mineVeinAndGetRemaining(te.getWorld().provider.dimensionId, pos, layer, cycleIncrease);

            if (pair != null) {
                sizeVeinPreStart = pair.getFirst();
                maxVeinSize = pair.getSecond();
            }
        }
    }

    public void initOreProperty(IGregTechTileEntity te) {
        if (te.isServerSide()) {
            chunks.clear();
            cycleIncrease = 0;

            oreVein = null;
            sizeVeinPreStart = 0;

            OreVeinScanResult result = VirtualWorldScan
                    .scanCurrentOreVein(te.getWorld().provider.dimensionId, te.getXCoord(), te.getZCoord(), layer);

            if (result != null) {
                oreVein = result.getOre();
                if (result.getRemainingAmount() != null) {
                    sizeVeinPreStart = result.getRemainingAmount();
                    maxVeinSize = VirtualWorldScan.getMaxOresInVein(result);
                } else {
                    sizeVeinPreStart = 0;
                }
            }
        }
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex,
            boolean aActive, boolean aRedstone) {
        return aSide == aFacing
                ? new ITexture[] { CASING.getTexture(),
                        TextureFactory.of(aActive ? Texture.Icons.OVERLAY_BASIC_MINER_ACTIVE
                                : Texture.Icons.OVERLAY_BASIC_MINER) }
                : new ITexture[] { CASING.getTexture() };
    }

    @Override
    public IStructureDefinition<GTMTE_AdvancedMiner> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_AdvancedMiner>builder()
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addShape("main", new String[][] {
                            { "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", " B   B ", " B   B ", " B   B " },
                            { "       ", "       ", "       ", "       ", "       ", "       ", "       ", "  B B  ", "  B B  ", " BB BB ", " BA~AB ", "BBBBBBB", "B     B", "B     B" },
                            { "       ", "       ", "       ", "   B   ", "   B   ", "   B   ", "  AAA  ", " BBBBB ", " B   B ", " B   B ", " AAAAA ", " B   B ", "       ", "       " },
                            { "   B   ", "   B   ", "   B   ", "  BAB  ", "  BAB  ", "  BAB  ", "  AAA  ", "  B B  ", "       ", "       ", " AACAA ", " B   B ", "       ", "       " },
                            { "       ", "       ", "       ", "   B   ", "   B   ", "   B   ", "  AAA  ", " BBBBB ", " B   B ", " B   B ", " AAAAA ", " B   B ", "       ", "       " },
                            { "       ", "       ", "       ", "       ", "       ", "       ", "       ", "  B B  ", "  B B  ", " BB BB ", " BAAAB ", "BBBBBBB", "B     B", "B     B" },
                            { "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", "       ", " B   B ", " B   B ", " B   B " }
                    })
                    .addElement('B', lazy(t -> ofFrame(Materials.HSLA)))
                    .addElement('C',
                            GTStructureUtility.buildHatchAdder(GTMTE_AdvancedMiner.class).hint(CASING, HintColor.GREEN)
                                    .build(StructureRequirements.builder(CASING, 0).atLeast(SpecialHatch.Ore).build()))
                    .addElement('A', GTStructureUtility.buildHatchAdder(GTMTE_AdvancedMiner.class)
                            .allow(HatchElement.Input, HatchElement.Output, HatchElement.Energy,
                                    HatchElement.Maintenance,
                                    ImpactHatchElement.Dynamo, ImpactHatchElement.Muffler,
                                    ImpactHatchElement.LaserInput,
                                    ImpactHatchElement.LaserOutput, SpecialHatch.Enrichment)
                            .autoPlace(ImpactHatchElement.PowerOutput, ImpactHatchElement.PowerInput, HatchElement.Input, HatchElement.Output, HatchElement.Maintenance, ImpactHatchElement.Muffler, SpecialHatch.Enrichment)
                            .buildAndChain(SHELL, machine -> machine.casingCount++).withHint(HintColor.BLUE))
                    .build();
        }
        return definition;
    }

    public boolean machineStructure(IGregTechTileEntity te) {
        casingCount = 0;
        te.setFrontFacing((byte) 1);
        Chunk chunk = te.getWorld().getChunkFromBlockCoords(te.getXCoord(), te.getZCoord());
        int size = 0;
        for (Object o : chunk.chunkTileEntityMap.values()) {
            if (o instanceof TileEntity) {
                TileEntity tile = (TileEntity) o;
                if (tile instanceof IGregTechTileEntity) {
                    IMetaTileEntity meta = ((IGregTechTileEntity) tile).getMetaTileEntity();
                    if (meta instanceof GTMTE_Mining_Coal) {
                        size++;
                    }
                }
            }
        }
        if (size > 1)
            return false;
        hatch.clear();
        enrich.clear();
        boolean check = checkPiece(3, 10, 1)
                && mMaintenanceHatches.size() <= 1
                && mOutputBusses.size() <= 1
                && HatchCounts.inputHatches(this) <= 1
                && HatchCounts.energyHatches(this) <= 2
                && SHELL.isSatisfied(this, casingCount);
        if (hatch.size() != 1)
            check = false;
        if (enrich.size() != 1)
            check = false;
        return check;
    }

    @Override
    public void onPostTick(IGregTechTileEntity te, long aTick) {
        super.onPostTick(te, aTick);
        if (te.isClientSide()) {
            if (te.isActive()) {
                te.getWorld().spawnParticle("largesmoke",
                        te.getOffsetX((byte) 0, 1) + XSTR.XSTR_INSTANCE.nextFloat(),
                        te.getOffsetY((byte) 0, 2),
                        te.getOffsetZ((byte) 0, 1) + XSTR.XSTR_INSTANCE.nextFloat(),
                        0.1D, 0.6D, 0.1D);
            }
        } else {
            if (te.isActive()) {
                if (aTick % 20 == 5 && !hatch.isEmpty() && hatch.get(0) != null) {
                    if (!hatch.get(0).ready && hatch.get(0).drillCoefficient == 0) {
                        stopMachine();
                    }
                }
            }
        }
    }

    private int byDrillHead(ItemStack stack) {
        drillLevel = hatch.get(0).drillCoefficient;
        return stack.stackSize + drillLevel;
    }

    @Override
    protected ProcessingLogic createProcessingLogic() {
        return new WorldProcessingLogic(this::prepareMining);
    }

    private WorldProcessingLogic.Operation prepareMining(ProcessingLogic.Request request) {
        if (hatch.isEmpty() || hatch.get(0) == null || !hatch.get(0).ready || oreVein == null) {
            return WorldProcessingLogic.Operation.failure(CheckRecipeResult.MACHINE_CONDITION);
        }
        if (sizeVeinPreStart - cycleIncrease <= 0) {
            initOreProperty(getBaseMetaTileEntity());
            return WorldProcessingLogic.Operation.failure(CheckRecipeResult.NO_RECIPE);
        }
        ProcessingInputs inputs = request.inputs.get(0).copy();
        if (!inputs.consumeFluid(new FluidStack(ItemList.sDrillingFluid, 50))
                || oreVein.getSpecial() != null && !inputs.consumeFluid(oreVein.getSpecial())) {
            return WorldProcessingLogic.Operation.failure(CheckRecipeResult.NO_RECIPE);
        }
        int tier = Math.max(1, getTierEnergyHatch());
        long startEU = Math.multiplyExact(6L, 1L << (tier * 2));
        List<ItemStack> output = new ArrayList<>();
        List<ItemStack> outputEnrich = new ArrayList<>();
        IGregTechTileEntity te = getBaseMetaTileEntity();
        int chancePrimary = 5000;
        long euTotal = 0;
        long voltage = Math.multiplyExact(request.voltage, 4L);
        long power = Math.multiplyExact(request.voltage, request.amperage);
        List<OreVein.OreVeinOut> is = oreVein.getOres();
        for (OreVein.OreVeinOut component : is) {
            if (te.getRandomNumber(10000) < component.getChance() * 100) {
                ItemStack oreStack = component.getStack().copy();
                ItemStack drillHeadDrop = new ItemStack(oreStack.getItem(), byDrillHead(oreStack),
                        oreStack.getItemDamage());
                outputEnrich.add(drillHeadDrop);
                GT_Recipe tRecipe = GT_Recipe.GT_Recipe_Map.sMaceratorRecipes
                        .findRecipe(getBaseMetaTileEntity(), cashedMaceratorRecipe, false, voltage, null,
                                new ItemStack[] { oreStack });
                if (tRecipe != null) {
                    cashedMaceratorRecipe = tRecipe;
                    for (int i = 0; i < tRecipe.mOutputs.length; i++) {
                        ItemStack recipeOutput = tRecipe.mOutputs[i].copy();
                        if (i == 0 && te.getRandomNumber(10000) < chancePrimary) {
                            output.add(recipeOutput);
                        }
                    }
                }
            }
        }

        Map<GTMTE_EnrichmentUnit, ProcessingInputs> enrichmentInputs = new LinkedHashMap<>();
        for (GTMTE_EnrichmentUnit unit : enrich) {
            FluidStack original = unit.getFluid();
            ProcessingInputs enrichment = new ProcessingInputs(Collections.emptyList(),
                    Collections.singletonList(original), () -> unit.getFluid() == original);
            enrichmentInputs.put(unit, enrichment);
            for (ItemStack stack : outputEnrich) {
                int size = stack.stackSize;
                for (int index = 0; index < size && stack.stackSize > 0; index++) {
                    if (enrichment.fluids.length == 0) {
                        break;
                    }
                    GT_Recipe recipe = GT_Recipe.GT_Recipe_Map.sFlotationUnitRecipes.findRecipe(
                            te, cashedFlotationRecipe, false, false, voltage, enrichment.fluids,
                            new ItemStack[] { stack });
                    if (recipe == null) {
                        break;
                    }
                    long nextEU = Math.addExact(startEU, Math.addExact(euTotal, recipe.mEUt));
                    if (power <= nextEU) {
                        break;
                    }
                    if (recipe.isRecipeInputEqual(true, enrichment.fluids, stack)) {
                        cashedFlotationRecipe = recipe;
                        euTotal = Math.addExact(euTotal, recipe.mEUt);
                        output.addAll(Arrays.asList(ProcessingInputs.copyItems(recipe.mOutputs)));
                    }
                }
                if (stack.stackSize > 0) {
                    output.add(stack);
                }
            }
        }
        if (output.isEmpty()) {
            output.addAll(outputEnrich);
        }
        OreVein plannedVein = oreVein;
        double decrement = Math.pow(0.9D, drillLevel) + cashedDecrement;
        int nextIncrease = (int) Math.floor(decrement);
        return new WorldProcessingLogic.Operation(Math.addExact(startEU, euTotal),
                Math.max(2, 400 / (1 << (tier - 1))), output.toArray(new ItemStack[0]), new FluidStack[0], () -> {
                    if (oreVein != plannedVein || !hatch.get(0).ready || !inputs.isUnchanged()) {
                        return false;
                    }
                    for (ProcessingInputs enrichment : enrichmentInputs.values()) {
                        if (!enrichment.isUnchanged()) {
                            return false;
                        }
                    }
                    inputs.commit();
                    for (Map.Entry<GTMTE_EnrichmentUnit, ProcessingInputs> entry : enrichmentInputs.entrySet()) {
                        entry.getValue().commit();
                        entry.getKey().updateSlots();
                    }
                    cycleIncrease = nextIncrease;
                    cashedDecrement = decrement - nextIncrease;
                    hatch.get(0).cycleDrill(true);
                    if (cycleIncrease >= 1) {
                        sizeVeinPreStart -= cycleIncrease;
                        increaseLayer(getBaseMetaTileEntity());
                    }
                    return true;
                });
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new ImpactMultiblockTooltipBuilder(mName)
                .useCompactStructure()
                .addMachineType("Advanced Electric Miner")
                .addInfo("Mining area: 4x4 chunks (full vein).")
                .addInfo("Consumes 50 L of Drilling Fluid per cycle; some veins require an additional fluid.")
                .addInfo("Requires a drill head in the Miner Drill Hatch.")
                .beginStructureBlock(7, 14, 7, false)
                .addController("front center of the lower platform")
                .addStructureSection("Components")
                .addStructurePartInfo(SHELL.getCasing(), "%s+; fill unused casing positions", SHELL.getMinimumCasings())
                .addOtherStructurePart(GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.HSLA, 1), "exactly 95; support frame")
                .addStructureSection("Hatches and buses")
                .addHatchInfo("Maintenance Hatch", "optional, up to 1; casing positions")
                .addHatchInfo("Energy Hatches", "optional, up to 2 total; casing positions")
                .addHatchInfo("Input Hatch", "optional, up to 1; casing positions")
                .addHatchInfo("Output Bus", "optional, up to 1; casing positions")
                .addHatchInfo("Miner Drill Hatch", "exactly 1; center of the lower platform; facing down")
                .addHatchInfo("Miner Enrichment Unit", "exactly 1; casing positions")
                .addStructureHint("Miner Drill Hatch", HintColor.GREEN)
                .addStructureHint("Casings or hatches/buses", HintColor.BLUE)
                .addHatchChannelInfo()
                .toolTipFinisher();
    }

    @Override
    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_AdvancedMiner(mName);
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("cycleIncrease", cycleIncrease);
        aNBT.setInteger("sizeVeinPreStart", sizeVeinPreStart);
        aNBT.setInteger("layer", layer);
        aNBT.setInteger("drillLevel", drillLevel);
        aNBT.setDouble("cashedDecrement", cashedDecrement);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        cycleIncrease = aNBT.getInteger("cycleIncrease");
        sizeVeinPreStart = aNBT.getInteger("sizeVeinPreStart");
        layer = aNBT.getInteger("layer");
        drillLevel = aNBT.getInteger("drillLevel");
        cashedDecrement = aNBT.getDouble("cashedDecrement");
    }

    @Override
    public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
        super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
        IGregTechTileEntity te = getBaseMetaTileEntity();
        if (te.isServerSide()) {
            if (!aPlayer.isSneaking()) {
                increaseLayer(te);
                layer++;
                if (layer >= 2) {
                    layer = 0;
                }
                initOreProperty(getBaseMetaTileEntity());
                GT_Utility.sendChatToPlayer(aPlayer, "Layer set: " + (layer));
            }
        }
    }

    @Override
    public void construct(ItemStack itemStack, boolean b) {
        buildPiece(itemStack, b, 3, 10, 1);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 3, 10, 1, budget, environment);
    }

    @Override
    public int getSupportedProcessingModes() {
        return ProcessingLogic.PROTECT_ITEMS | ProcessingLogic.PROTECT_FLUIDS;
    }

    @Override
    @Optional.Method(modid = "Waila")
    public void getWailaBody(ItemStack stack, List<String> tooltip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        super.getWailaBody(stack, tooltip, accessor, config);
        NBTTagCompound tag = accessor.getNBTData();
        if (tag.getBoolean("incompleteStructure")) {
            return;
        }
        String size = GT_Utility.formatNumbers(tag.getInteger("adv_miner.vein"));
        tooltip.add(translateToLocalFormatted("impact.waila.miner.vein_size", size));
        tooltip.add(translateToLocalFormatted("impact.waila.miner.layer", tag.getInteger("adv_miner.layer")));
    }

    @Override
    @Optional.Method(modid = "Waila")
    public void getWailaNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag,
            World world, int x, int y, int z) {
        super.getWailaNBTData(player, tile, tag, world, x, y, z);
        tag.setInteger("adv_miner.vein", (int) Math.round(100 * ((double) sizeVeinPreStart / (double) maxVeinSize)));
        tag.setInteger("adv_miner.layer", layer);
    }

    private enum SpecialHatch implements IHatchElement {
        Ore {
            @Override
            public boolean matches(IMetaTileEntity tile) {
                return tile instanceof GTMTE_OreHatch;
            }

            @Override
            public int count(GT_MetaTileEntity_MultiBlockBase machine) {
                return machine instanceof GTMTE_AdvancedMiner ? ((GTMTE_AdvancedMiner) machine).hatch.size() : 0;
            }

            @Override
            public boolean add(GT_MetaTileEntity_MultiBlockBase machine, IGregTechTileEntity tile, int texture) {
                if (!(machine instanceof GTMTE_AdvancedMiner) || !matches(tile.getMetaTileEntity())) {
                    return false;
                }
                GTMTE_OreHatch hatch = (GTMTE_OreHatch) tile.getMetaTileEntity();
                hatch.updateTexture(texture);
                return ((GTMTE_AdvancedMiner) machine).hatch.add(hatch);
            }

            @Override
            public String getDescription() {
                return StatCollector.translateToLocal("impact.structure.hatch.ore");
            }
        },
        Enrichment {
            @Override
            public boolean matches(IMetaTileEntity tile) {
                return tile instanceof GTMTE_EnrichmentUnit;
            }

            @Override
            public int count(GT_MetaTileEntity_MultiBlockBase machine) {
                return machine instanceof GTMTE_AdvancedMiner ? ((GTMTE_AdvancedMiner) machine).enrich.size() : 0;
            }

            @Override
            public boolean add(GT_MetaTileEntity_MultiBlockBase machine, IGregTechTileEntity tile, int texture) {
                if (!(machine instanceof GTMTE_AdvancedMiner) || !matches(tile.getMetaTileEntity())) {
                    return false;
                }
                GTMTE_EnrichmentUnit hatch = (GTMTE_EnrichmentUnit) tile.getMetaTileEntity();
                hatch.updateTexture(texture);
                return ((GTMTE_AdvancedMiner) machine).enrich.add(hatch);
            }

            @Override
            public String getDescription() {
                return StatCollector.translateToLocal("impact.structure.hatch.enrichment");
            }
        };
    }
}
