package com.impact.mods.gregtech.tileentities.multi.ores;

import com.impact.addon.vw.VirtualWorldScan;
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
import static space.impact.structures.structure.StructureUtility.*;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_AdvancedMiner extends GTMTE_Impact_BlockBase<GTMTE_AdvancedMiner> {

    private final List<GTMTE_OreHatch> hatch = new ArrayList<>();
    private final List<GTMTE_EnrichmentUnit> enrich = new ArrayList<>();
    public int cycleIncrease = 0, sizeVeinPreStart = 0, maxVeinSize = 0, drillLevel = 0;
    public List<Chunk> chunks = new ArrayList<>();
    public OreVein oreVein;
    public int layer = 1;
    public double cashedDecrement = 0d;

    private static final ICasing CASING = Casings.HSLA;

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
                            GTStructureUtility.buildHatchAdder(GTMTE_AdvancedMiner.class).hint(HintColor.RED)
                                    .build(StructureRequirements.builder(CASING, 0).atLeast(SpecialHatch.Ore).build()))
                    .addElement('A', withHint(GTStructureUtility.buildHatchAdder(GTMTE_AdvancedMiner.class)
                            .allow(HatchElement.Input, HatchElement.Output, HatchElement.Energy,
                                    HatchElement.Maintenance,
                                    ImpactHatchElement.Dynamo, ImpactHatchElement.Muffler,
                                    ImpactHatchElement.LaserInput,
                                    ImpactHatchElement.LaserOutput, SpecialHatch.Enrichment)
                            .buildAndChain(StructureRequirements.builder(CASING, 0).build()), HintColor.GREEN))
                    .build();
        }
        return definition;
    }

    public boolean machineStructure(IGregTechTileEntity te) {
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
        boolean check = checkPiece(3, 10, 1);
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
    public boolean checkRecipe(ItemStack aStack) {
        if (hatch.get(0) == null)
            return false;
        if (oreVein == null)
            return false;
        boolean check = hatch.get(0).ready;
        if ((sizeVeinPreStart - cycleIncrease) <= 0) {
            increaseLayer(getBaseMetaTileEntity());
            initOreProperty(getBaseMetaTileEntity());
            return false;
        }
        if (check) {
            int tier = Math.max(1, getTierEnergyHatch());
            int startEU = 3 * (2 << (tier << 1));
            List<ItemStack> output = new ArrayList<>();
            List<ItemStack> outputEnrich = new ArrayList<>();
            IGregTechTileEntity te = getBaseMetaTileEntity();
            int chancePrimary = 5000;
            int euTotal = 0;
            long voltage = getMaxInputVoltage();
            if (!depleteInput(new FluidStack(ItemList.sDrillingFluid, 50))) {
                return false;
            }
            if (oreVein.getSpecial() != null) {
                if (!depleteInput(oreVein.getSpecial())) {
                    return false;
                }
            }

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

            if (!enrich.isEmpty()) {
                for (GTMTE_EnrichmentUnit e : enrich) {
                    for (ItemStack stack : outputEnrich) {
                        int size = stack.stackSize;
                        for (int i = 0; i < size; i++) {
                            if (e.getFluid() == null)
                                break;
                            FluidStack[] eFluid = new FluidStack[] { e.getFluid() };
                            GT_Recipe tRecipe = GT_Recipe.GT_Recipe_Map.sFlotationUnitRecipes
                                    .findRecipe(this.getBaseMetaTileEntity(), cashedFlotationRecipe, false, false,
                                            voltage, eFluid, new ItemStack[] { stack });
                            if (tRecipe != null && tRecipe.isRecipeInputEqual(true, eFluid, stack)) {
                                cashedFlotationRecipe = tRecipe;
                                if (voltage <= euTotal + startEU + tRecipe.mEUt)
                                    break;
                                euTotal += tRecipe.mEUt;
                                output.addAll(Arrays.asList(tRecipe.mOutputs));
                                e.updateSlots();
                            }
                        }
                        if (stack.stackSize > 0) {
                            output.add(stack);
                        }
                    }
                }
            }

            if (output.isEmpty()) {
                output.addAll(outputEnrich);
            }
            this.mEfficiency = getCurrentEfficiency(null);
            this.mEfficiencyIncrease = 10000;
            this.mEUt = -(startEU + euTotal);
            this.mMaxProgresstime = 400 / (1 << (tier - 1));
            this.mMaxProgresstime = Math.max(2, this.mMaxProgresstime);
            this.mOutputItems = output.toArray(new ItemStack[0]);
        }
        hatch.get(0).cycleDrill(check);

        double utilization = 1.0D;
        for (int i = 0; i < drillLevel; i++) {
            utilization *= 0.9D;
        }

        double aa = utilization + cashedDecrement;
        cycleIncrease = (int) Math.floor(aa);
        cashedDecrement = aa - cycleIncrease;

        if (cycleIncrease >= 1) {
            sizeVeinPreStart -= cycleIncrease;
            increaseLayer(getBaseMetaTileEntity());
        }
        return check;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder(mName)
                .addInfo("Factorio Miner??")
                .addMachineType("Miner")
                .addInfo("Mining takes place in 16 (4x4 zone) Chunks (Full Vein)")
                .addInfo("There is only ONE miner in one Chunk")
                .addInfo("Use a screwdriver to change the Ore Layer (0-1)")
                .addSeparator()
                .addController("See the structure hologram")
                .beginStructureBlock(7, 13, 7, false)
                .addOtherStructurePart("Maintenance Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Output Bus", "Any casing; maximum 1")
                .addOtherStructurePart("Input Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Energy Hatch", "Any casing; maximum 2")
                .addCasingInfoMin("HSLA Machine Casing", 25, false)
                .addOtherStructurePart("HSLA Steel Frame Box", "Any Frame Box")
                .addOtherStructurePart("Miner Enrichment Unit", "Any casing")
                .addStructureHint("Miner Drill Hatch", HintColor.RED)
                .addStructureHint("Casing or compatible hatch", HintColor.GREEN)
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
    public boolean hasSeparate() {
        return false;
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
