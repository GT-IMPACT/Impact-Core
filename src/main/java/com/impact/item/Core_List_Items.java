package com.impact.item;

import gregtech.api.GregTech_API;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;

public enum Core_List_Items {

    //MetaItems0 dust
    BarnardaEStoneDust(0, 0),
    BarnardaFStoneDust(1, 0),
    CallistoIceDust(2, 0),
    CallistoStoneDust(3, 0),
    CentauriASurfaceDust(4, 0),
    CentauriAStoneDust(5, 0),
    CeresStoneDust(6, 0),
    DeimosStoneDust(7, 0),
    EnceladusIceDust(8, 0),
    EnceladusStoneDust(9, 0),
    EuropaIceDust(10, 0),
    EuropaStoneDust(11, 0),
    GanymedStoneDust(12, 0),
    IoStoneDust(13, 0),
    HaumeaStoneDust(14, 0),
    MakeMakeStoneDust(15, 0),
    MercuryStoneDust(16, 0),
    MirandaStoneDust(17, 0),
    VenusStoneDust(18, 0),
    PhobosStoneDust(19, 0),
    PlutoIceDust(20, 0),
    PlutoStoneDust(21, 0),
    ProteusStoneDust(22, 0),
    OberonStoneDust(23, 0),
    TCetiEStoneDust(24, 0),
    TitanStoneDust(25, 0),
    TritonStoneDust(26, 0),
    VegaBStoneDust(27, 0),
    TCetiESeaweedExtract(28, 0),
    DustSmallFertilizer(29, 0),
    DustTinyFertilizer(30, 0),

    //MetaItems1
    SchematicsTier1(0, 1),
    SchematicsTier2(1, 1),
    SchematicsTier3(2, 1),
    SchematicsTier4(3, 1),
    SchematicsTier5(4, 1),
    SchematicsTier6(5, 1),
    SchematicsTier7(6, 1),
    SchematicsTier8(7, 1),
    SchematicsAstroMiner(8, 1),
    SchematicsCargoRocket(9, 1),
    SchematicsMoonBuggy(10, 1),
    HeavyDutyPlateTier4(11, 1),
    HeavyDutyPlateTier5(12, 1),
    HeavyDutyPlateTier6(13, 1),
    HeavyDutyPlateTier7(14, 1),
    HeavyDutyPlateTier8(15, 1),
    HeavyDutyAlloyIngotT4(16, 1),
    HeavyDutyAlloyIngotT5(17, 1),
    HeavyDutyAlloyIngotT6(18, 1),
    HeavyDutyAlloyIngotT7(19, 1),
    HeavyDutyAlloyIngotT8(20, 1),
    HeavyDutyNoseConeTier4(21, 1),
    HeavyDutyNoseConeTier5(22, 1),
    HeavyDutyNoseConeTier6(23, 1),
    HeavyDutyNoseConeTier7(24, 1),
    HeavyDutyNoseConeTier8(25, 1),
    HeavyDutyRocketEngineTier4(26, 1),
    HeavyDutyRocketEngineTier5(27, 1),
    HeavyDutyRocketEngineTier6(28, 1),
    HeavyDutyRocketEngineTier7(29, 1),
    HeavyDutyRocketEngineTier8(30, 1),
    HeavyDutyRocketFinsTier4(31, 1),
    HeavyDutyRocketFinsTier5(32, 1),
    HeavyDutyRocketFinsTier6(33, 1),
    HeavyDutyRocketFinsTier7(34, 1),
    HeavyDutyRocketFinsTier8(35, 1),
    Tier4Booster(36, 1),
    Tier5Booster(37, 1),
    Tier6Booster(38, 1),
    Tier7Booster(39, 1),
    Tier8Booster(40, 1),
    QuantumPartBoots(41, 1),
    QuantumPartChestplate(42, 1),
    QuantumPartHelmetEmpty(43, 1),
    QuantumPartHelmet(44, 1),
    QuantumPartLeggings(45, 1),
    QuantumCrystal(46, 1),
    RefinedReinforcedGlassLense(47, 1),
    ChargedGlassLense(48, 1),
    HugeRefinedReinforcedGlassLense(49, 1),
    HugeChargedGlassLense(50, 1),
    SteelBars(51, 1),
    IridiumAlloyItemCasing(52, 1),
    PistonPlate(53, 1),
    Empty180SpCell(54, 1),
    Empty360SpCell(55, 1),
    Empty540SpCell(56, 1),
    Empty1080SpCell(57, 1),
    OakScheme(58, 1),
    DarkOakScheme(59, 1),
    JungleScheme(60, 1),
    AcaciaScheme(61, 1),
    SpruceScheme(62, 1),
    BirchScheme(63, 1),
    RubberScheme(64, 1),
    CokeOvenBrick(65,1),
    UnfiredSearedBrick(66,1),
    UnfiredCokeOvenBrick(67,1),
    UnfiredClayBrick(68,1),

    WoodenBrickForm1(0,2),

    ;

    static {
        BarnardaEStoneDust.setOreDictName("dustBarnardaE");
        BarnardaFStoneDust.setOreDictName("dustBarnardaF");
        CallistoIceDust.setOreDictName("dustIceCallisto");
        CallistoStoneDust.setOreDictName("dustCallisto");
        CentauriASurfaceDust.setOreDictName("dustSurfaceCentauriA");
        CentauriAStoneDust.setOreDictName("dustCentauriA");
        CeresStoneDust.setOreDictName("dustCeres");
        DeimosStoneDust.setOreDictName("dustDeimos");
        EnceladusIceDust.setOreDictName("dustIceEnceladus");
        EnceladusStoneDust.setOreDictName("dustEnceladus");
        EuropaIceDust.setOreDictName("dustIceEuropa");
        EuropaStoneDust.setOreDictName("dustEuropa");
        GanymedStoneDust.setOreDictName("dustGanymed");
        IoStoneDust.setOreDictName("dustIo");
        HaumeaStoneDust.setOreDictName("dustHaumea");
        MakeMakeStoneDust.setOreDictName("dustMakeMake");
        MercuryStoneDust.setOreDictName("dustMercuryP");
        MirandaStoneDust.setOreDictName("dustMiranda");
        VenusStoneDust.setOreDictName("dustVenus");
        PhobosStoneDust.setOreDictName("dustPhobos");
        PlutoIceDust.setOreDictName("dustIcePluto");
        PlutoStoneDust.setOreDictName("dustPluto");
        ProteusStoneDust.setOreDictName("dustProteus");
        OberonStoneDust.setOreDictName("dustOberon");
        TCetiEStoneDust.setOreDictName("dustTCetiE");
        TitanStoneDust.setOreDictName("dustTitan");
        TritonStoneDust.setOreDictName("dustTriton");
        VegaBStoneDust.setOreDictName("dustVegaB");
        DustSmallFertilizer.setOreDictName("dustSmallFertilizer");
        DustTinyFertilizer.setOreDictName("dustTinyFertilizer");
    }

    private final int metaID;
    private final int identifier;

    private Core_List_Items(int metaID, int identifier) {
        this.metaID = metaID;
        this.identifier = identifier;
    }

    public int getMetaID() {
        return metaID;
    }

    String OreDictName;

    private void registerOreDict() {
        OreDictionary.registerOre(getOreDictName(), getNonOreDictedItemStack(1));
    }

    public static void registerOreDictNames() {
        Arrays.stream(Core_List_Items.values()).filter(e -> e.getOreDictName() != null).forEach(Core_List_Items::registerOreDict);
    }

    public ItemStack getNonOreDictedItemStack(int amount) {
        return
                identifier == 0 ?
                        new ItemStack(Core_Items.getInstance(), amount, this.getMetaID()) :
                identifier == 2 ?
                        new ItemStack(WoodBrickFormTool.getInstance(), amount, this.getMetaID()) :
                        new ItemStack(Core_Items2.getInstance(), amount, this.getMetaID());
    }

    public ItemStack getOreDictedItemStack(int amount) {
        return this.getOreDictName() != null ? GregTech_API.getStackofAmountFromOreDict(this.getOreDictName(), amount) :
                identifier == 0 ?
                        new ItemStack(Core_Items.getInstance(), amount, this.getMetaID()) :
                        identifier == 2 ?
                                new ItemStack(WoodBrickFormTool.getInstance(), amount, this.getMetaID()) :
                                new ItemStack(Core_Items2.getInstance(), amount, this.getMetaID());
    }

    public String getOreDictName() {
        return OreDictName;
    }

    public void setOreDictName(String oreDictName) {
        OreDictName = oreDictName;
    }
}

