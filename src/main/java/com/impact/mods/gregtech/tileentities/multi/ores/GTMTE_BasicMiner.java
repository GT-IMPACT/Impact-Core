package com.impact.mods.gregtech.tileentities.multi.ores;

import com.impact.addon.vw.VirtualWorldScan;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.enums.Texture;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.mods.gregtech.tileentities.multi.ores.hatches.GTMTE_OreHatch;
import cpw.mods.fml.common.Optional;
import gregtech.api.enums.Casings;
import gregtech.api.enums.HatchElement;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
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
import space.gtimpact.virtual_world.api.VirtualAPI;
import space.gtimpact.virtual_world.api.core.WorldPos;
import space.gtimpact.virtual_world.api.resources.ores.OreVein;
import space.gtimpact.virtual_world.api.services.mining.ores.OreMiningResult;
import space.gtimpact.virtual_world.api.services.scanning.ores.OreChunkResult;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static gregtech.api.util.GTStructureUtility.ofFrame;
import static net.minecraft.util.StatCollector.translateToLocalFormatted;
import static space.impact.structures.structure.StructureUtility.*;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_BasicMiner extends GTMTE_Impact_BlockBase<GTMTE_BasicMiner> {

    private static final StructureRequirements HATCHES_A = StructureRequirements.builder(Casings.SOLID_STEEL, 0)
            .build();

    private static IStructureDefinition<GTMTE_BasicMiner> definition;
    private final List<GTMTE_OreHatch> hatch = new ArrayList<>();
    public int cycleIncrease = 0, sizeVeinPreStart = 0, drillLevel = 0;
    public OreVein oreVein;
    public int layer = 1;
    public double cashedDecrement = 0d;

    public GTMTE_BasicMiner(int aID, String aNameRegional) {
        super(aID, "impact.multis.miner.basic", aNameRegional);
    }

    public GTMTE_BasicMiner(String aName) {
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

    public void increaseLayer(IGregTechTileEntity te, int reduce) {
        if (te.isServerSide()) {

            WorldPos pos = new WorldPos(te.getXCoord(), te.getZCoord());
            OreMiningResult result = VirtualAPI.INSTANCE.getMining()
                    .mineOreAtBlock(te.getWorld().provider.dimensionId, pos, layer, cycleIncrease);

            oreVein = result != null ? result.getOre() : null;
            sizeVeinPreStart = result != null ? (100 * result.getRemainingAmount() / oreVein.getRangeSize().getLast())
                    : 0;
        }
    }

    public void initOreProperty(IGregTechTileEntity te) {
        if (te.isServerSide()) {

            oreVein = null;
            sizeVeinPreStart = 0;

            OreChunkResult result = VirtualWorldScan
                    .scanCurrentOreChunk(te.getWorld().provider.dimensionId, te.getXCoord(), te.getZCoord(), layer);

            if (result != null) {
                oreVein = result.getOre();
                sizeVeinPreStart = 100 * result.getRemainingAmount() / oreVein.getRangeSize().getLast();
            }
        }
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex,
            boolean aActive, boolean aRedstone) {
        return aSide == aFacing
                ? new ITexture[] { Casings.SOLID_STEEL.getTexture(),
                        TextureFactory.of(aActive ? Texture.Icons.OVERLAY_BASIC_MINER_ACTIVE
                                : Texture.Icons.OVERLAY_BASIC_MINER) }
                : new ITexture[] { Casings.SOLID_STEEL.getTexture() };
    }

    @Override
    public IStructureDefinition<GTMTE_BasicMiner> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_BasicMiner>builder()
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addShape("main", new String[][] {
                            { "     ", "     ", "     ", "     ", "     ", "     ", "     ", "     ", "     ", " G G ", " G G ", " G G " },
                            { "     ", "     ", "     ", "  G  ", "  G  ", "  G  ", " GAG ", " G G ", " G G ", "GA~AG", "G   G", "G   G" },
                            { "  G  ", "  G  ", "  G  ", " GAG ", " GAG ", " GAG ", " AAA ", "     ", "     ", " ACA ", "     ", "     " },
                            { "     ", "     ", "     ", "  G  ", "  G  ", "  G  ", " GAG ", " G G ", " G G ", "GAAAG", "G   G", "G   G" },
                            { "     ", "     ", "     ", "     ", "     ", "     ", "     ", "     ", "     ", " G G ", " G G ", " G G " },
                    })
                    .addElement('G', lazy(t -> ofFrame(Materials.Steel)))
                    .addElement('C',
                            GTStructureUtility.buildHatchAdder(GTMTE_BasicMiner.class).hint(HintColor.RED)
                                    .build(StructureRequirements.builder(Casings.SOLID_STEEL, 0)
                                            .atLeast(SpecialHatch.Ore).build()))
                    .addElement('A', withHint(GTStructureUtility.buildHatchAdder(GTMTE_BasicMiner.class)
                            .allow(HatchElement.Input, HatchElement.Output, HatchElement.Maintenance,
                                    HatchElement.Energy, ImpactHatchElement.Muffler, ImpactHatchElement.Dynamo,
                                    ImpactHatchElement.LaserInput, ImpactHatchElement.LaserOutput)
                            .buildAndChain(HATCHES_A), HintColor.GREEN))
                    .build();
        }
        return definition;
    }

    @Override
    public void onRemoval() {
        super.onRemoval();
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
                    if (meta instanceof GTMTE_BasicMiner) {
                        size++;
                    }
                }
            }
        }
        if (size > 1)
            return false;
        hatch.clear();
        boolean check = checkPiece(2, 9, 1);// TODO: 02.12.2021
        if (hatch.size() != 1)
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
        if (sizeVeinPreStart <= 0)
            return false;
        if (oreVein == null)
            return false;
        boolean check = hatch.get(0).ready;
        if ((sizeVeinPreStart - cycleIncrease) <= 0) {
            initOreProperty(getBaseMetaTileEntity());
            return false;
        }
        if (check) {
            List<ItemStack> output = new ArrayList<>();
            IGregTechTileEntity te = getBaseMetaTileEntity();
            int chancePrimary = 500;
            int tier = Math.max(1, getTierEnergyHatch());
            int startEU = 3 * (2 << (tier << 1));
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
                    output.add(drillHeadDrop);
                    GT_Recipe tRecipe = GT_Recipe.GT_Recipe_Map.sMaceratorRecipes.findRecipe(getBaseMetaTileEntity(),
                            false, voltage, null, oreStack);
                    if (tRecipe != null) {
                        for (int i = 0; i < tRecipe.mOutputs.length; i++) {
                            ItemStack recipeOutput = tRecipe.mOutputs[i].copy();
                            if (i == 0 && te.getRandomNumber(10000) < chancePrimary) {
                                output.add(recipeOutput);
                            }
                        }
                    }
                }
            }
            this.mEfficiency = getCurrentEfficiency(null);
            this.mEfficiencyIncrease = 10000;
            this.mEUt = -startEU;
            this.mMaxProgresstime = 400 / (1 << (tier - 1));
            this.mMaxProgresstime = Math.max(2, this.mMaxProgresstime);
            this.mOutputItems = output.toArray(new ItemStack[0]);
        }
        GTMTE_OreHatch oreHatch = hatch.get(0);
        if (oreHatch != null) {
            oreHatch.cycleDrill(check);
        }

        double utilization = 1.0D;
        for (int i = 0; i < drillLevel; i++) {
            utilization *= 0.9D;
        }

        double aa = utilization + cashedDecrement;
        cycleIncrease = (int) Math.floor(aa);
        cashedDecrement = aa - cycleIncrease;

        if (cycleIncrease >= 1) {
            sizeVeinPreStart -= cycleIncrease;
            increaseLayer(getBaseMetaTileEntity(), 1);
        }
        return check;
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder(mName)
                .addInfo("Factorio Miner??")
                .addMachineType("Miner")
                .addInfo("Mining takes place in current Chunk")
                .addInfo("There is only ONE miner in one Chunk")
                .addInfo("Use a screwdriver to change the Ore Layer (0-1)")
                .addSeparator()
                .addController("See the structure hologram")
                .beginStructureBlock(5, 12, 5, false)
                .addOtherStructurePart("Maintenance Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Output Bus", "Any casing; maximum 1")
                .addOtherStructurePart("Input Hatch", "Any casing; maximum 1")
                .addOtherStructurePart("Energy Hatch", "Any casing; maximum 2")
                .addCasingInfoMin("Solid Steel Machine Casing", 11, false)
                .addCasingInfoMin("Steel Frame Box", 51, false)
                .addStructureHint("Miner Drill Hatch", HintColor.RED)
                .addStructureHint("Casing or compatible hatch", HintColor.GREEN)
                .toolTipFinisher();
    }

    @Override
    public MetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_BasicMiner(mName);
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
                increaseLayer(te, 1);
                layer++;
                if (layer >= 2) {
                    layer = 0;
                }
                initOreProperty(te);
                GT_Utility.sendChatToPlayer(aPlayer, "Layer set: " + (layer));
            }
        }
    }

    @Override
    public void construct(ItemStack itemStack, boolean b) { // TODO: 02.12.2021
        buildPiece(itemStack, b, 2, 9, 1);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget,
            ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 2, 9, 1, budget, environment);
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
        String size = GT_Utility.formatNumbers(tag.getInteger("basic_miner.vein"));
        tooltip.add(translateToLocalFormatted("impact.waila.miner.chunk_size", size));
        tooltip.add(translateToLocalFormatted("impact.waila.miner.layer", tag.getInteger("basic_miner.layer")));
    }

    @Override
    @Optional.Method(modid = "Waila")
    public void getWailaNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag,
            World world, int x, int y, int z) {
        super.getWailaNBTData(player, tile, tag, world, x, y, z);
        tag.setInteger("basic_miner.vein", sizeVeinPreStart);
        tag.setInteger("basic_miner.layer", layer);
    }

    private enum SpecialHatch implements IHatchElement {
        Ore {
            @Override
            public boolean matches(IMetaTileEntity tile) {
                return tile instanceof GTMTE_OreHatch;
            }

            @Override
            public int count(GT_MetaTileEntity_MultiBlockBase machine) {
                return machine instanceof GTMTE_BasicMiner ? ((GTMTE_BasicMiner) machine).hatch.size() : 0;
            }

            @Override
            public boolean add(GT_MetaTileEntity_MultiBlockBase machine, IGregTechTileEntity tile, int texture) {
                if (!(machine instanceof GTMTE_BasicMiner) || !matches(tile.getMetaTileEntity())) {
                    return false;
                }
                GTMTE_OreHatch hatch = (GTMTE_OreHatch) tile.getMetaTileEntity();
                hatch.updateTexture(texture);
                return ((GTMTE_BasicMiner) machine).hatch.add(hatch);
            }

            @Override
            public String getDescription() {
                return StatCollector.translateToLocal("impact.structure.hatch.ore");
            }
        };
    }
}
