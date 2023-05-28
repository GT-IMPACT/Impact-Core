package com.impact.common.item;

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
    CokeOvenBrickDust(31, 1),
    SearedBrickDust(32, 0),
    ClearGlassDust(33, 0),
    RawNeutroniumDust(34, 0),
    BarnardaCBiomass(35, 0),
    StargateCrystalDust(36, 0),
    ConcreteDust(37, 0),
    ChargedQuartzDust(38, 0),
    SawDust(39, 0),
    DioxideYttrium(40, 0),

    NULL41(41, 0, true), // пустой

    YSZdust(42, 0),
    GDCdust(43, 0),
    LSCFdust(44, 0),
    YSZCeramic(45, 0),
    GDCCeramic(46, 0),
    LSFCCeramic(47, 0),
    Zircon(48, 0),
    ZirconiumChloride(49, 0),

    PetcokeDust(50, 0),
    NULL51(51, 0, true),
    NULL52(52, 0, true),
    NULL53(53, 0, true),
    NULL54(54, 0, true),
    NULL55(55, 0, true),
    NULL56(56, 0, true),
    NULL57(57, 0, true),
    NULL58(58, 0, true),
    NULL59(59, 0, true),
    NULL60(60, 0, true),
    NULL61(61, 0, true),
    NULL62(62, 0, true),
    NULL63(63, 0, true),
    NULL64(64, 0, true),
    NULL65(65, 0, true),
    NULL66(66, 0, true),
    NULL67(67, 0, true),
    NULL68(68, 0, true),
    NULL69(69, 0, true),
    NULL70(70, 0, true),
    NULL71(71, 0, true),
    NULL72(72, 0, true),
    NULL73(73, 0, true),
    NULL74(74, 0, true),
    NULL75(75, 0, true),
    NULL76(76, 0, true),
    NULL77(77, 0, true),
    NULL78(78, 0, true),
    NULL79(79, 0, true),
    NULL80(80, 0, true),
    NULL81(81, 0, true),
    NULL82(82, 0, true),
    NULL83(83, 0, true),
    NULL84(84, 0, true),
    NULL85(85, 0, true),
    NULL86(86, 0, true),
    NULL87(87, 0, true),
    NULL88(88, 0, true),
    NULL89(89, 0, true),
    NULL90(90, 0, true),
    NULL91(91, 0, true),
    NULL92(92, 0, true),
    NULL93(93, 0, true),
    NULL94(94, 0, true),
    NULL95(95, 0, true),
    NULL96(96, 0, true),
    NULL97(97, 0, true),
    NULL98(98, 0, true),
    NULL99(99, 0, true),
    
    DropCrashedStone(100, 0),
    DropCrashedMetallic(101, 0),
    DropCrashedCoal(102, 0),
    
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
    CokeOvenBrick(65, 1),
    UnfiredSearedBrick(66, 1),
    UnfiredCokeOvenBrick(67, 1),
    UnfiredClayBrick(68, 1),
    CallistoIceColdIngot(69, 1),
    CallistoIceIngot(70, 1),
    LedoxColdIngot(71, 1),
    MysteriousCrystalColdIngot(72, 1),
    MysteriousCrystalIngot(73, 1),
    CallistoIcePlate(74, 1),
    LedoxColdPlate(75, 1),
    MysteriousCrystalColdPlate(76, 1),
    BlackPlutoniumCompressedPlate(77, 1),
    CallistoIceCompressedPlate(78, 1),
    MeitneriumCompressedPlate(79, 1),
    IceCompressedPlate(80, 1),
    IridiumAlloyCompressedPlate(81, 1),
    LedoxCompressedPlate(82, 1),
    MysteriousCrystalCompressedPlate(83, 1),
    NaquadahCompressedPlate(84, 1),
    QuantiumCompressedPlate(85, 1),
    MytrylCompressedPlate(86, 1),
    PalladiumCompressedPlate(87, 1),
    LeadNickelCompressedPlate(88, 1),
    LeadOriharukonCompressedPlate(89, 1),
    DeshDualCompressedPlate(90, 1),
    IceDualCompressedPlate(91, 1),
    IridiumAlloyDualCompressedPlate(92, 1),
    MeteoricIronDualCompressedPlate(93, 1),
    MysteriousCrystalDualCompressedPlate(94, 1),
    QuantiumDualCompressedPlate(95, 1),
    TitaniumDualCompressedPlate(96, 1),
    RawSDHCAlloy(97, 1),
    AdvancedCoolingCore(98, 1),
    MiningCrystal(99, 1),
    NanoCrystal(100, 1),
    AluminiumIronPlate(101, 1),
    TitaniumIronPlate(102, 1),
    TungstenIronPlate(103, 1),
    TungstenSteelIronPlate(104, 1),
    ChromeIronPlate(105, 1),
    IridiumIronPlate(106, 1),
    NaquadriaIronPlate(107, 1),
    NeutroniumIronPlate(108, 1),
    ReinforcedAluminiumIronPlate(109, 1),
    ReinforcedTitaniumIronPlate(110, 1),
    ReinforcedTungstenIronPlate(111, 1),
    ReinforcedTungstenSteelIronPlate(112, 1),
    ReinforcedChromeIronPlate(113, 1),
    ReinforcedIridiumIronPlate(114, 1),
    ReinforcedNaquadriaIronPlate(115, 1),
    ReinforcedNeutroniumIronPlate(116, 1),
    IrradiantReinforcedAluminiumPlate(117, 1),
    IrradiantReinforcedTitaniumPlate(118, 1),
    IrradiantReinforcedTungstenPlate(119, 1),
    IrradiantReinforcedTungstenSteelPlate(120, 1),
    IrradiantReinforcedChromePlate(121, 1),
    IrradiantReinforcedIridiumPlate(122, 1),
    IrradiantReinforcedNaquadriaPlate(123, 1),
    IrradiantReinforcedNeutroniumPlate(124, 1),
    SunnariumPiece(125, 1),
    Sunnarium(126, 1),
    SunnariumAlloy(127, 1),
    IrradiantUranium(128, 1),
    EnrichedSunnarium(129, 1),
    EnrichedSunnariumAlloy(130, 1),
    EnrichedNaquadriaSunnariumAlloy(131, 1),
    EnrichedNeutroniumSunnariumAlloy(132, 1),
    MediumFuelCanister(133, 1),
    LargeFuelCanister(134, 1),
    ExtraLargeFuelCanister(135, 1),
    CarbonPartHelmet(136, 1),
    CarbonPartHelmetNightVision(137, 1),
    CarbonPartChestplate(138, 1),
    CarbonPartLeggings(139, 1),
    CarbonPartBoots(140, 1),
    NeutronReflectorSmallParts(141, 1),
    NeutronReflectorParts(142, 1),
    TenKCell(143, 1),
    ThirtyKCell(144, 1),
    SixtyKCell(145, 1),
    IndustryFrame(146, 1),
    StargateShieldingFoil(147, 1),
    StargateFramePart(148, 1),
    StargateChevron(149, 1),
    BarnardaCScheme(150, 1),
    BarnardaCBall(151, 1),
    BarnardaCBiochaff(152, 1),
    ChargedQuartzRod(153, 1),
    MicrochipBoard1(154, 1),
    MicrochipBoard2(155, 1),
    MicrochipBoard3(156, 1),
    ALUBoard(157, 1),
    CUBoard(158, 1),
    YSZCeramicPlate(159, 1),
    GDCCeramicPlate(160, 1),
    LSFCCeramicPlate(161, 1),
    TachyonTube(162, 1),
    NaqChamberPart(163, 1),
    ConcreteTie(164, 1),
    ConcreteRailbed(165, 1),
    ExtruderShapeRail(166, 1),
    AdvancedMixIngot(167, 1),
    ElectricMixIngot(168, 1),
    ReinforcedMixIngot(169, 1),
    HSMixIngot(170, 1),
    LeadCompressedPlate(171, 1),
    PlatinumCompressedPlate(172, 1),
    OriharukonCompressedPlate(173, 1),
    AdamantiumCompressedPlate(174, 1),
    BronzeDualCompressedPlate(175, 1),
    AluminiumDualCompressedPlate(176, 1),
    CobblestoneRod(177, 1),
    SandstoneRod(178, 1),
    BlankGeneSampleRaw(179, 1),
    GeneticTemplateRaw(180, 1),
    InfinityCatalyst(181, 1),
    LargeScreen(182, 1),
    Naquadah(183, 1),
    NaquadahNuclearFuel(184, 1),
    NaquadahE(185, 1),
    NaquadahENuclearFuel(186, 1),
    ThoriumNuclearPart(187, 1),
    ThoriumNuclearFuel(188, 1),
    ThoriumMOXFuel(189, 1),
    OrbCasingI(190, 1),
    OrbCasingII(191, 1),
    RTRMFoil(192, 1),
    PGFoil(193, 1),
    NANDArray(194, 1),
    FlawedFluixCrystal(195, 1),
    FlawlessFluixCrystal(196, 1),
    BlankPattern(197, 1),
	LaserCore1(198, 1),
	LaserCore2(199, 1),
	LaserCore3(200, 1),
    PetCokeGem(201, 1),
    MudBall(202, 1),
    Processorx1(203, 1),
    Processorx4(204, 1),
    Processorx16(205, 1),
    Processorx64(206, 1),
    Processorx256(207, 1),
    Processorx1024(208, 1),
    Processorx4096(209, 1),
    

    WoodenBrickForm1(0, 2),

    FakeMVCircuit(0, 3),
    FakeEVCircuit(1, 3),
    FakeIVCircuit(2, 3),
    FakeLuVCircuit(3, 3),
    FakeZPMCircuit(4, 3),
    FakeUVCircuit(5, 3),
    FakeUHVCircuit(6, 3),
    FakeUEVCircuit(7, 3),

    MPBufferEmpty(0, 4),
    MPBufferFull(1, 4),

    LVRotor(0, 5),
    MVRotor(1, 5),
    HVRotor(2, 5),
    EVRotor(3, 5),
    IVRotor(4, 5),
    
    AdvFluidCover(0, 6),
    AdvEnergyCover(1, 6),
    
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
        CokeOvenBrickDust.setOreDictName("dustCokeOven");
        SearedBrickDust.setOreDictName("dustSearedBrick");
        CokeOvenBrick.setOreDictName("ingotCokeOvenBrick");
        BlackPlutoniumCompressedPlate.setOreDictName("compressedBlackPlutonium");
        MeitneriumCompressedPlate.setOreDictName("compressedDraconium");
        NaquadahCompressedPlate.setOreDictName("compressedNaquadah");
        QuantiumCompressedPlate.setOreDictName("compressedQuantium");
        MytrylCompressedPlate.setOreDictName("compressedMytryl");
        PalladiumCompressedPlate.setOreDictName("compressedPalladium");
        FakeMVCircuit.setOreDictName("circuitGood");
        FakeEVCircuit.setOreDictName("circuitData");
        FakeIVCircuit.setOreDictName("circuitElite");
        FakeLuVCircuit.setOreDictName("circuitMaster");
        FakeZPMCircuit.setOreDictName("circuitUltimate");
        FakeUVCircuit.setOreDictName("circuitSuperconductor");
        FakeUHVCircuit.setOreDictName("circuitInfinite");
        FakeUEVCircuit.setOreDictName("circuitBio");
        YSZCeramicPlate.setOreDictName("plateYSZ");
        GDCCeramicPlate.setOreDictName("plateGDC");
        LSFCCeramicPlate.setOreDictName("plateLSCF");
    }

    private final int metaID;
    private final int identifier;
    public final boolean hideNEI;
    String OreDictName;

    Core_List_Items(int metaID, int identifier) {
        this.metaID = metaID;
        this.identifier = identifier;
        this.hideNEI = false;
    }
    
    Core_List_Items(int metaID, int identifier, boolean hideNEI) {
        this.metaID = metaID;
        this.identifier = identifier;
        this.hideNEI = hideNEI;
    }

    public static void registerOreDictNames() {
        Arrays.stream(Core_List_Items.values()).filter(e -> e.getOreDictName() != null)
                .forEach(Core_List_Items::registerOreDict);
    }

    public ItemStack get(int amount) {
        return getNonOreDictedItemStack(amount);
    }

    public int getMetaID() {
        return metaID;
    }

    private void registerOreDict() {
        OreDictionary.registerOre(getOreDictName(), getNonOreDictedItemStack(1));
    }

    public ItemStack getNonOreDictedItemStack(int amount) {
        switch (identifier) {
            case 0:
                return new ItemStack(Core_Items.getInstance(), amount, this.getMetaID());
            case 1:
                return new ItemStack(Core_Items2.getInstance(), amount, this.getMetaID());
            case 2:
                return new ItemStack(WoodBrickFormTool.getInstance(), amount, this.getMetaID());
            case 3:
                return new ItemStack(FakeCircuits.getInstance(), amount, this.getMetaID());
            case 4:
                return new ItemStack(Core_Items3.getInstance(), amount, this.getMetaID());
            case 5:
                return new ItemStack(KineticRotors.getInstance(), amount, this.getMetaID());
            case 6:
                return new ItemStack(Covers.getInstance(), amount, this.getMetaID());
            default:
                return null;
        }
    }

    public String getOreDictName() {
        return OreDictName;
    }

    public void setOreDictName(String oreDictName) {
        OreDictName = oreDictName;
    }
}