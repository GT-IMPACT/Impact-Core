package com.impact.mods.gregtech.tileentities.multi.units;

import com.impact.impact;
import com.impact.mods.gregtech.blocks.ImpactCasings;
import com.impact.mods.gregtech.blocks.ImpactHatchElement;
import com.impact.mods.gregtech.enums.Texture;
import com.impact.mods.gregtech.gui.filler.Container_Construction_Laser;
import com.impact.mods.gregtech.gui.filler.GUI_Construction_Laser;
import com.impact.mods.gregtech.items.tools.ConstructionLaser;
import com.impact.mods.gregtech.tileentities.multi.implement.GTMTE_Impact_BlockBase;
import com.impact.util.vector.Box;
import com.impact.util.vector.Structure;
import com.impact.util.vector.Vector3ic;
import gregtech.api.enums.HatchElement;
import gregtech.api.interfaces.ICasing;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GTStructureUtility;
import gregtech.api.util.MultiblockTooltipBuilder;
import gregtech.api.util.StructureRequirements;
import gregtech.common.blocks.GT_TileEntity_Ores;
import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import space.impact.structures.alignment.IAlignmentLimits;
import space.impact.structures.hint.HintColor;
import space.impact.structures.structure.AutoPlaceEnvironment;
import space.impact.structures.structure.IStructureDefinition;
import space.impact.structures.structure.IStructureElement;
import space.impact.structures.structure.ISurvivalBuildEnvironment;
import space.impact.structures.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.decorateBlock;
import static space.impact.structures.structure.StructureUtility.withHint;

public class GTMTE_Filler extends GTMTE_Impact_BlockBase<GTMTE_Filler> {

    public int xMax, yMax, zMax;
    public int xMin, yMin, zMin;
    public int curX, curY, curZ;
    public boolean saveTiles = false;
    public boolean isDone = false;
    public boolean killMobs = false;
    public Box box;

    private static final ICasing CASING = ImpactCasings.SPACE_ELEVATOR_CASING;

    private static final StructureRequirements SHELL = StructureRequirements.builder(CASING, 0)
            .atLeast(ImpactHatchElement.RegularEnergy).build();
    private static IStructureDefinition<GTMTE_Filler> definition;

    public GTMTE_Filler(int aID, String aNameRegional) {
        super(aID, "impact.multis.filler", aNameRegional);
    }

    public GTMTE_Filler(String aName) {
        super(aName);
    }

    @Override
    public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new Container_Construction_Laser(aPlayerInventory, aBaseMetaTileEntity);
    }

    @Override
    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GUI_Construction_Laser(aPlayerInventory, aBaseMetaTileEntity, getLocalName());
    }

    @Override
    public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing,
            final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
        return aSide == aFacing
                ? new ITexture[] { CASING.getTexture(), TextureFactory.of(
                        aActive ? Texture.Icons.OVERLAY_SPACE_ELEVATOR_ACTIVE : Texture.Icons.OVERLAY_SPACE_ELEVATOR) }
                : new ITexture[] { CASING.getTexture() };
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new GTMTE_Filler(this.mName);
    }

    protected IAlignmentLimits getInitialAlignmentLimits() {
        return (direction, rotation, flip) -> rotation.isNotRotated() && flip.isNotFlipped();
    }

    @Override
    protected MultiblockTooltipBuilder createTooltip() {
        return new MultiblockTooltipBuilder(mName)
                .addRequirements(SHELL, "Either side of the controller")
                .addStructureHint("Casing or compatible hatch", HintColor.GREEN)
                .toolTipFinisher();
    }

    @Override
    public IStructureDefinition<GTMTE_Filler> getStructureDefinition() {
        if (definition == null) {
            definition = StructureDefinition.<GTMTE_Filler>builder()
                    .addChannel(GTStructureUtility.HATCH_CHANNEL, 1, 1)
                    .addShape("main", new String[][] { { "C C", "C~C", "C C" } })
                    .addElement('C', withHint(GTStructureUtility.buildHatchAdder(GTMTE_Filler.class)
                            .allow(HatchElement.Energy, ImpactHatchElement.LaserInput, HatchElement.Output)
                            .buildAndChain(SHELL, optionalCasing(), machine -> {
                            }), HintColor.GREEN))
                    .build();
        }
        return definition;
    }

    private static IStructureElement<GTMTE_Filler> optionalCasing() {
        IStructureElement<GTMTE_Filler> casing = CASING.asElement();
        return new IStructureElement<GTMTE_Filler>() {
            @Override
            public boolean check(GTMTE_Filler machine, World world, int x, int y, int z) {
                // The original filler only required energy; all six surrounding positions were
                // optional.
                return true;
            }

            @Override
            public boolean couldBeValid(GTMTE_Filler machine, World world, int x, int y, int z, ItemStack trigger) {
                return !world.isAirBlock(x, y, z);
            }

            @Override
            public boolean spawnHint(GTMTE_Filler machine, World world, int x, int y, int z, ItemStack trigger) {
                return casing.spawnHint(machine, world, x, y, z, trigger);
            }

            @Override
            public boolean placeBlock(GTMTE_Filler machine, World world, int x, int y, int z, ItemStack trigger) {
                return casing.placeBlock(machine, world, x, y, z, trigger);
            }

            @Override
            public BlocksToPlace getBlocksToPlace(GTMTE_Filler machine, World world, int x, int y, int z,
                    ItemStack trigger, AutoPlaceEnvironment environment) {
                return casing.getBlocksToPlace(machine, world, x, y, z, trigger, environment);
            }

            @Override
            public PlaceResult survivalPlaceBlock(GTMTE_Filler machine, World world, int x, int y, int z,
                    ItemStack trigger, AutoPlaceEnvironment environment) {
                return casing.survivalPlaceBlock(machine, world, x, y, z, trigger, environment);
            }
        };
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setInteger("curX", curX);
        aNBT.setInteger("curY", curY);
        aNBT.setInteger("curZ", curZ);

        aNBT.setInteger("xMin", xMin);
        aNBT.setInteger("yMin", yMin);
        aNBT.setInteger("zMin", zMin);

        aNBT.setInteger("xMax", xMax);
        aNBT.setInteger("yMax", yMax);
        aNBT.setInteger("zMax", zMax);

        aNBT.setBoolean("saveTiles", saveTiles);
        aNBT.setBoolean("isDone", isDone);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        curX = aNBT.getInteger("curX");
        curY = aNBT.getInteger("curY");
        curZ = aNBT.getInteger("curZ");

        xMin = aNBT.getInteger("xMin");
        yMin = aNBT.getInteger("yMin");
        zMin = aNBT.getInteger("zMin");

        xMax = aNBT.getInteger("xMax");
        yMax = aNBT.getInteger("yMax");
        zMax = aNBT.getInteger("zMax");

        isDone = aNBT.getBoolean("isDone");
        saveTiles = aNBT.getBoolean("saveTiles");
    }

    @Override
    public boolean machineStructure(IGregTechTileEntity te) {
        mWrench = mScrewdriver = mSoftHammer = mHardHammer = mSolderingTool = mCrowbar = true;
        return checkPiece(1, 1, 0) && SHELL.isSatisfied(this, 0);
    }

    private ArrayList<ItemStack> getBlockDrops(int posX, int posY, int posZ) {
        ArrayList<ItemStack> is = new ArrayList<>();
        TileEntity te = getBaseMetaTileEntity().getTileEntity(posX, posY, posZ);
        if (te instanceof GT_TileEntity_Ores) {
            return is;
        }
        Block block = getBaseMetaTileEntity().getBlock(posX, posY, posZ);
        if (block != null) {
            final int blockMeta = getBaseMetaTileEntity().getMetaID(posX, posY, posZ);
            is.addAll(block.getDrops(getBaseMetaTileEntity().getWorld(), posX, posY, posZ, blockMeta, 0));
        }
        return is;
    }

    public void mineBlock(int x, int y, int z) {
        IGregTechTileEntity iAm = getBaseMetaTileEntity();
        ArrayList<ItemStack> drops = getBlockDrops(x, y, z);
        Block currentBlock = iAm.getBlock(x, y, z);
        if (currentBlock == Blocks.bedrock || currentBlock == Blocks.air || isMe(x, y, z))
            return;
        if (!saveTiles) {
            iAm.getWorld().setBlockToAir(x, y, z);
            for (ItemStack drop : drops) {
                addOutput(drop);
            }
        } else {
            TileEntity te = iAm.getTileEntity(x, y, z);
            if (te == null || te instanceof GT_TileEntity_Ores) {
                iAm.getWorld().setBlockToAir(x, y, z);
            }
        }
    }

    public void addBound(World w) {
        int age = 20 * 5;
        impact.proxy.hint_particle(w, xMin, yMin, zMin, decorateBlock[2], 2, age); // x0 y0 z0 green
        impact.proxy.hint_particle(w, xMax, yMax, zMax, decorateBlock[2], 3, age); // x1 y1 z1 blue
        impact.proxy.hint_particle(w, xMax, yMin, zMin, decorateBlock[2], 1, age); // x1 y0 z0
        impact.proxy.hint_particle(w, xMin, yMax, zMin, decorateBlock[2], 1, age); // x0 y1 z0
        impact.proxy.hint_particle(w, xMin, yMin, zMax, decorateBlock[2], 1, age); // x0 y0 z1
        impact.proxy.hint_particle(w, xMax, yMin, zMax, decorateBlock[2], 1, age); // x1 y0 z1
        impact.proxy.hint_particle(w, xMin, yMax, zMax, decorateBlock[2], 1, age); // x0 y1 z1
        impact.proxy.hint_particle(w, xMax, yMax, zMin, decorateBlock[2], 1, age); // x1 y1 z0
    }

    @Override
    public void onPostTick(IGregTechTileEntity iAm, long aTick) {
        super.onPostTick(iAm, aTick);
        if (iAm.isServerSide()) {
            int x = iAm.getXCoord();
            int z = iAm.getZCoord();
            World w = iAm.getWorld();

            if (aTick % 20 == 0 && isDone) {
                if (mInventory != null) {
                    ItemStack is = mInventory[0];
                    if (is != null && is.getItem() instanceof ConstructionLaser) {
                        ConstructionLaser laser = (ConstructionLaser) is.getItem();
                        if (box != null && box.equals(laser.getBox(is))) {
                            return;
                        }
                        box = laser.getBox(is);
                        setBoxCoords(box);
                    }
                }
            }
            if (aTick == 1 || aTick % (20 * 5) == 0) {
                addBound(w);
            }
            if (iAm.isActive() && !isDone && aTick % 20 == 0) {
                if (curY < yMin) {
                    isDone = true;
                    return;
                }
                if (curY == 0 && curX == 0 && curZ == 0) {
                    isDone = true;
                    return;
                }
                for (curX = xMax; curX >= xMin; --curX) {
                    for (curZ = zMax; curZ >= zMin; --curZ) {
                        mineBlock(curX, curY, curZ);
                    }
                }
                Vector3ic vec = Structure.goBuild(iAm, 0, 0, -1);
                impact.proxy.hint_particle(w, vec.x() + x, curY, vec.z() + z, decorateBlock[2], 0, 18);
                curY--;
            }
        }
    }

    @Override
    public void onFirstTick(IGregTechTileEntity iAm) {
        super.onFirstTick(iAm);
        if (iAm.isServerSide()) {
            saveTiles = true;
            isDone = true;
            if (xMax != 0 && yMax != 0 && zMax != 0) {
                box = new Box(xMin, yMin, zMin, xMax, yMax, zMax);
            }
            TileEntity te = iAm.getTileEntityAtSide(iAm.getBackFacing());
            // if (Loader.isModLoaded("BuildCraft|Core") && te instanceof IAreaProvider) {
            // IAreaProvider mark = (IAreaProvider) te;
            // box = new Box(mark.xMin(), mark.yMin(), mark.zMin(), mark.xMax(),
            // mark.yMax(), mark.zMax());
            // mark.removeFromWorld();
            // setBoxCoords(box);
            // }
        }
    }

    private void setBoxCoords(Box box) {
        if (box == null) {
            isDone = true;
            return;
        }
        xMax = box.xMax;
        yMax = box.yMax;
        zMax = box.zMax;
        xMin = box.xMin;
        yMin = box.yMin;
        zMin = box.zMin;
        curX = xMax;
        curY = yMax;
        curZ = zMax;
        isDone = false;
    }

    @SuppressWarnings("unchecked")
    public void killMobs() {
        if (box != null) {
            killMobs = true;
            getBaseMetaTileEntity().getWorld().getEntitiesWithinAABB(EntityLivingBase.class, box.getBoundingBox())
                    .forEach(o -> ((Entity) o).setDead());
        }
        killMobs = false;
    }

    @Override
    public boolean checkRecipe(ItemStack itemStack) {
        if (isDone)
            return false;
        this.mMaxProgresstime = 20;
        this.mEUt = 0;
        this.mEfficiencyIncrease = 10000;
        this.mEfficiency = 10000;
        return true;
    }

    public boolean isMe(int x, int y, int z) {
        IGregTechTileEntity te = getBaseMetaTileEntity();
        return x == te.getXCoord() && y == te.getYCoord() && z == te.getZCoord();
    }

    @Override
    public void construct(ItemStack trigger, boolean hintsOnly) {
        buildPiece(trigger, hintsOnly, 1, 1, 0);
    }

    @Override
    public int survivalConstruct(ItemStack trigger, int budget, ISurvivalBuildEnvironment environment) {
        return survivalBuildPiece("main", trigger, 1, 1, 0, budget, environment);
    }

    @Override
    public boolean hasSeparate() {
        return false;
    }
}
