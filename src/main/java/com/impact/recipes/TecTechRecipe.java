package com.impact.recipes;

import com.impact.common.item.Core_Items2;
import com.github.technus.tectech.recipe.TT_recipeAdder;
import com.github.technus.tectech.thing.CustomItemList;
import com.impact.mods.GregTech.GT_ItemList;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class TecTechRecipe implements Runnable {

    final Core_Items2 CoreItems2 = Core_Items2.getInstance();

    @Override
    public void run() {
        
//####################################################################################################################################################################################################################################################################################################################################################################################################

        //Laser Dynamo IV-UEV 256/t
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Hull_MAX.get(1), GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Diamond, 1), ItemList.Emitter_UHV.get(1), ItemList.Electric_Pump_UHV.get(1), GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Europium, 2), GT_Utility.getIntegratedCircuit(1)}, null, CustomItemList.eM_dynamoTunnel1_UHV.get(1), 1000, 2000000);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Hull_UEV.get(1), GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Diamond, 1), ItemList.Emitter_UEV.get(1), ItemList.Electric_Pump_UEV.get(1), GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Diamericiumtitanium, 2), GT_Utility.getIntegratedCircuit(1)}, null, CustomItemList.eM_dynamoTunnel1_UEV.get(1), 1000, 8000000);

        //Laser Dynamo IV-UEV 1024/t
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Hull_MAX.get(1), GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Diamond, 2), ItemList.Emitter_UHV.get(2), ItemList.Electric_Pump_UHV.get(2), GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.Europium, 4), GT_Utility.getIntegratedCircuit(2)}, null, CustomItemList.eM_dynamoTunnel2_UHV.get(1), 2000, 2000000);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Hull_UEV.get(1), GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Diamond, 2), ItemList.Emitter_UEV.get(2), ItemList.Electric_Pump_UEV.get(2), GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.Diamericiumtitanium, 4), GT_Utility.getIntegratedCircuit(2)}, null, CustomItemList.eM_dynamoTunnel2_UEV.get(1), 2000, 8000000);

        //Laser Dynamo IV-UEV 4096/t
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Hull_MAX.get(1), GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Diamond, 4), ItemList.Emitter_UHV.get(4), ItemList.Electric_Pump_UHV.get(4), GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Europium, 4), GT_Utility.getIntegratedCircuit(3)}, null, CustomItemList.eM_dynamoTunnel3_UHV.get(1), 4000, 2000000);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Hull_UEV.get(1), GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Diamond, 4), ItemList.Emitter_UEV.get(4), ItemList.Electric_Pump_UEV.get(4), GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Diamericiumtitanium, 4), GT_Utility.getIntegratedCircuit(3)}, null, CustomItemList.eM_dynamoTunnel3_UEV.get(1), 4000, 8000000);

        //Laser Dynamo IV-UEV 16384/t
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Hull_MAX.get(1), GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Diamond, 8), ItemList.Emitter_UHV.get(8), ItemList.Electric_Pump_UHV.get(8), GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Europium, 8), GT_Utility.getIntegratedCircuit(4)}, null, CustomItemList.eM_dynamoTunnel4_UHV.get(1), 8000, 2000000);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Hull_UEV.get(1), GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Diamond, 8), ItemList.Emitter_UEV.get(8), ItemList.Electric_Pump_UEV.get(8), GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Diamericiumtitanium, 8), GT_Utility.getIntegratedCircuit(4)}, null, CustomItemList.eM_dynamoTunnel4_UEV.get(1), 8000, 8000000);

        //Laser Dynamo IV-UEV 65536/t
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Hull_MAX.get(1), GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Diamond, 16), ItemList.Emitter_UHV.get(16), ItemList.Electric_Pump_UHV.get(16), GT_OreDictUnificator.get(OrePrefixes.wireGt08, Materials.Europium, 8), GT_Utility.getIntegratedCircuit(5)}, null, CustomItemList.eM_dynamoTunnel5_UHV.get(1), 16000, 2000000);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Hull_UEV.get(1), GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Diamond, 16), ItemList.Emitter_UEV.get(16), ItemList.Electric_Pump_UEV.get(16), GT_OreDictUnificator.get(OrePrefixes.wireGt08, Materials.Diamericiumtitanium, 8), GT_Utility.getIntegratedCircuit(5)}, null, CustomItemList.eM_dynamoTunnel5_UEV.get(1), 16000, 8000000);

        //Laser Dynamo IV-UEV 262144/t
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Hull_MAX.get(1), GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Diamond, 32), ItemList.Emitter_UHV.get(32), ItemList.Electric_Pump_UHV.get(32), GT_OreDictUnificator.get(OrePrefixes.wireGt08, Materials.Europium, 16), GT_Utility.getIntegratedCircuit(6)}, null, CustomItemList.eM_dynamoTunnel6_UHV.get(1), 32000, 2000000);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Hull_UEV.get(1), GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Diamond, 32), ItemList.Emitter_UEV.get(32), ItemList.Electric_Pump_UEV.get(32), GT_OreDictUnificator.get(OrePrefixes.wireGt08, Materials.Diamericiumtitanium, 16), GT_Utility.getIntegratedCircuit(6)}, null, CustomItemList.eM_dynamoTunnel6_UEV.get(1), 32000, 8000000);

        //Laser Dynamo IV-UEV 1048576/t
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Hull_MAX.get(1), GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Diamond, 64), ItemList.Emitter_UHV.get(64), ItemList.Electric_Pump_UHV.get(64), GT_OreDictUnificator.get(OrePrefixes.wireGt16, Materials.Europium, 16), GT_Utility.getIntegratedCircuit(7)}, null, CustomItemList.eM_dynamoTunnel7_UHV.get(1), 64000, 2000000);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Hull_UEV.get(1), GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Diamond, 64), ItemList.Emitter_UEV.get(64), ItemList.Electric_Pump_UEV.get(64), GT_OreDictUnificator.get(OrePrefixes.wireGt16, Materials.Diamericiumtitanium, 16), GT_Utility.getIntegratedCircuit(7)}, null, CustomItemList.eM_dynamoTunnel7_UEV.get(1), 64000, 8000000);

        //Laser Target IV-UEV 256/t
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Hull_MAX.get(1), GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Diamond, 1), ItemList.Sensor_UHV.get(1), ItemList.Electric_Pump_UHV.get(1), GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Europium, 2), GT_Utility.getIntegratedCircuit(1)}, null, CustomItemList.eM_energyTunnel1_UHV.get(1), 1000, 2000000);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Hull_UEV.get(1), GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Diamond, 1), ItemList.Sensor_UEV.get(1), ItemList.Electric_Pump_UEV.get(1), GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Diamericiumtitanium, 2), GT_Utility.getIntegratedCircuit(1)}, null, CustomItemList.eM_energyTunnel1_UEV.get(1), 1000, 8000000);

        //Laser Target IV-UEV 1024/t
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Hull_MAX.get(1), GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Diamond, 2), ItemList.Sensor_UHV.get(2), ItemList.Electric_Pump_UHV.get(2), GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.Europium, 4), GT_Utility.getIntegratedCircuit(2)}, null, CustomItemList.eM_energyTunnel2_UHV.get(1), 2000, 2000000);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Hull_UEV.get(1), GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Diamond, 2), ItemList.Sensor_UEV.get(2), ItemList.Electric_Pump_UEV.get(2), GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.Diamericiumtitanium, 4), GT_Utility.getIntegratedCircuit(2)}, null, CustomItemList.eM_energyTunnel2_UEV.get(1), 2000, 8000000);

        //Laser Target IV-UEV 4096/t
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Hull_MAX.get(1), GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Diamond, 4), ItemList.Sensor_UHV.get(4), ItemList.Electric_Pump_UHV.get(4), GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Europium, 4), GT_Utility.getIntegratedCircuit(3)}, null, CustomItemList.eM_energyTunnel3_UHV.get(1), 4000, 2000000);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Hull_UEV.get(1), GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Diamond, 4), ItemList.Sensor_UEV.get(4), ItemList.Electric_Pump_UEV.get(4), GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Diamericiumtitanium, 4), GT_Utility.getIntegratedCircuit(3)}, null, CustomItemList.eM_energyTunnel3_UEV.get(1), 4000, 8000000);

        //Laser Target IV-UEV 16384/t
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Hull_MAX.get(1), GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Diamond, 8), ItemList.Sensor_UHV.get(8), ItemList.Electric_Pump_UHV.get(8), GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Europium, 8), GT_Utility.getIntegratedCircuit(4)}, null, CustomItemList.eM_energyTunnel4_UHV.get(1), 8000, 2000000);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Hull_UEV.get(1), GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Diamond, 8), ItemList.Sensor_UEV.get(8), ItemList.Electric_Pump_UEV.get(8), GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Diamericiumtitanium, 8), GT_Utility.getIntegratedCircuit(4)}, null, CustomItemList.eM_energyTunnel4_UEV.get(1), 8000, 8000000);

        //Laser Target IV-UEV 65536/t
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Hull_MAX.get(1), GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Diamond, 16), ItemList.Sensor_UHV.get(16), ItemList.Electric_Pump_UHV.get(16), GT_OreDictUnificator.get(OrePrefixes.wireGt08, Materials.Europium, 8), GT_Utility.getIntegratedCircuit(5)}, null, CustomItemList.eM_energyTunnel5_UHV.get(1), 16000, 2000000);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Hull_UEV.get(1), GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Diamond, 16), ItemList.Sensor_UEV.get(16), ItemList.Electric_Pump_UEV.get(16), GT_OreDictUnificator.get(OrePrefixes.wireGt08, Materials.Diamericiumtitanium, 8), GT_Utility.getIntegratedCircuit(5)}, null, CustomItemList.eM_energyTunnel5_UEV.get(1), 16000, 8000000);

        //Laser Target IV-UEV 262144/t
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Hull_MAX.get(1), GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Diamond, 32), ItemList.Sensor_UHV.get(32), ItemList.Electric_Pump_UHV.get(32), GT_OreDictUnificator.get(OrePrefixes.wireGt08, Materials.Europium, 16), GT_Utility.getIntegratedCircuit(6)}, null, CustomItemList.eM_energyTunnel6_UHV.get(1), 32000, 2000000);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Hull_UEV.get(1), GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Diamond, 32), ItemList.Sensor_UEV.get(32), ItemList.Electric_Pump_UEV.get(32), GT_OreDictUnificator.get(OrePrefixes.wireGt08, Materials.Diamericiumtitanium, 16), GT_Utility.getIntegratedCircuit(6)}, null, CustomItemList.eM_energyTunnel6_UEV.get(1), 32000, 8000000);

        //Laser Target IV-UEV 1048576/t
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Hull_MAX.get(1), GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Diamond, 64), ItemList.Sensor_UHV.get(64), ItemList.Electric_Pump_UHV.get(64), GT_OreDictUnificator.get(OrePrefixes.wireGt16, Materials.Europium, 16), GT_Utility.getIntegratedCircuit(7)}, null, CustomItemList.eM_energyTunnel7_UHV.get(1), 64000, 2000000);
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{ItemList.Hull_UEV.get(1), GT_OreDictUnificator.get(OrePrefixes.lens, Materials.Diamond, 64), ItemList.Sensor_UEV.get(64), ItemList.Electric_Pump_UEV.get(64), GT_OreDictUnificator.get(OrePrefixes.wireGt16, Materials.Diamericiumtitanium, 16), GT_Utility.getIntegratedCircuit(7)}, null, CustomItemList.eM_energyTunnel7_UEV.get(1), 64000, 8000000);

//####################################################################################################################################################################################################################################################################################################################################################################################################

        //region multi blocks

        //Active Transformer
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{
                ItemList.WetTransformer_ZPM_LuV.get(1),
                GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Energy, 1),
                GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorLuV, 16),
                ItemList.Circuit_Chip_UHPIC.get(2),
        }, Materials.TungstenSteel.getMolten(576), CustomItemList.Machine_Multi_Transformer.get(1), 400, 30720);

        //Essentia Quantizer
        TT_recipeAdder.addResearchableAssemblylineRecipe(CustomItemList.Machine_Multi_MatterToEM.get(1),
                15000, 32, 500000, 8, new ItemStack[]{
                        CustomItemList.Machine_Multi_MatterToEM.get(1),
                        GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Neutronium, 4),
                        ItemList.Emitter_UV.get(2),
                        ItemList.Circuit_Wetwaresupercomputer.get(1),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt02, Materials.Diamericiumtitanium, 2),
                }, new FluidStack[]{
                        Materials.UUMatter.getFluid(2000),
                        Materials.Silver.getMolten(2592),
                        new FluidStack(FluidRegistry.getFluid("ic2coolant"), 4000),
                        Materials.Osmium.getMolten(1296),
                }, CustomItemList.Machine_Multi_EssentiaToEM.get(1), 24000, 500000);

        //Essentia DeQuantizer
        TT_recipeAdder.addResearchableAssemblylineRecipe(CustomItemList.Machine_Multi_EMToMatter.get(1),
                15000, 32, 500000, 8, new ItemStack[]{
                        CustomItemList.Machine_Multi_EMToMatter.get(1),
                        GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Neutronium, 4),
                        ItemList.Sensor_UV.get(2),
                        ItemList.Circuit_Wetwaresupercomputer.get(1),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt02, Materials.Diamericiumtitanium, 2),
                }, new FluidStack[]{
                        Materials.UUMatter.getFluid(2000),
                        Materials.Silver.getMolten(2592),
                        new FluidStack(FluidRegistry.getFluid("ic2coolant"), 4000),
                        Materials.Osmium.getMolten(1296),
                }, CustomItemList.Machine_Multi_EMToEssentia.get(1), 24000, 500000);

        //EM Scanner
        TT_recipeAdder.addResearchableAssemblylineRecipe(CustomItemList.Machine_Multi_Research.get(1),
                150000, 128, 500000, 16, new ItemStack[]{
                        CustomItemList.Machine_Multi_EMjunction.get(1),
                        CustomItemList.eM_Computer_Bus.get(4),
                        ItemList.Field_Generator_UV.get(4),
                        ItemList.Sensor_UV.get(4),
                        GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Nano, 4),
                        GT_OreDictUnificator.get(OrePrefixes.lens, Materials.MysteriousCrystal, 4),
                        GT_OreDictUnificator.get(OrePrefixes.cableGt02, Materials.Diamericiumtitanium, 4),
                }, new FluidStack[]{
                        Materials.UUMatter.getFluid(2000),
                        Materials.Neutronium.getMolten(2592),
                        new FluidStack(FluidRegistry.getFluid("ic2coolant"), 4000),
                        Materials.Osmiridium.getMolten(1296),
                }, CustomItemList.Machine_Multi_Scanner.get(1), 24000, 500000);

//####################################################################################################################################################################################################################################################################################################################################################################################################

        //region endgame items

        //Motor UHV-UEV
        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Electric_Motor_UV.get(1L),
                24000, 32, 100000, 4, new ItemStack[]{
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.EuropiumoxideMagnetic, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Neutronium, 8L),
                        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Quantium, 8L),
                        GT_OreDictUnificator.get(OrePrefixes.round, Materials.Quantium, 32L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Americium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Americium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Americium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Americium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Americium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Americium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Americium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Americium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Europium, 2L)
                }, new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(2592),
                        Materials.Lubricant.getFluid(4000),
                        Materials.Naquadria.getMolten(2592)},
                ItemList.Electric_Motor_UHV.get(1L), 1000, 200000);

        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Electric_Motor_UHV.get(1L),
                48000, 64, 2000000, 8, new ItemStack[]{
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.EuropiumoxideMagnetic, 8L),
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.CosmicNeutronium, 16L),
                        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.CosmicNeutronium, 8L),
                        GT_OreDictUnificator.get(OrePrefixes.round, Materials.CosmicNeutronium, 32L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Neutronium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Neutronium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Neutronium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Neutronium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Neutronium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Neutronium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Neutronium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Neutronium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Diamericiumtitanium, 2L)
                }, new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(5184),
                        Materials.Lubricant.getFluid(8000),
                        Materials.Quantium.getMolten(2592)},
                ItemList.Electric_Motor_UEV.get(1L), 2000, 800000);

        //Pumps UHV-UEV
        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Electric_Pump_UV.get(1L),
                24000, 32, 100000, 4, new ItemStack[]{
                        ItemList.Electric_Motor_UHV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.Infuscolium, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Neutronium, 16L),
                        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Silicone, 32L),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Neutronium, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Europium, 2L)
                }, new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(2592),
                        Materials.Lubricant.getFluid(4000),
                        Materials.Naquadria.getMolten(2592)},
                ItemList.Electric_Pump_UHV.get(1), 1000, 200000);

        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Electric_Pump_UHV.get(1L),
                48000, 64, 200000, 8, new ItemStack[]{
                        ItemList.Electric_Motor_UEV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.pipeLarge, Materials.Neutronium, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.CosmicNeutronium, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.screw, Materials.CosmicNeutronium, 16L),
                        GT_OreDictUnificator.get(OrePrefixes.ring, (Materials.Silicone), 64L),
                        GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.CosmicNeutronium, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Diamericiumtitanium, 2L)
                }, new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(5184),
                        Materials.Lubricant.getFluid(8000),
                        Materials.Quantium.getMolten(2592)},
                ItemList.Electric_Pump_UEV.get(1), 2000, 800000);

        //Conveyor Belt UHV-UEV
        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Conveyor_Module_UV.get(1L),
                24000, 32, 100000, 4, new ItemStack[]{
                        ItemList.Electric_Motor_UHV.get(2L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Neutronium, 8L),
                        GT_OreDictUnificator.get(OrePrefixes.round, Materials.Neutronium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Europium, 2L)
                }, new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(2592),
                        Materials.Lubricant.getFluid(4000),
                        Materials.Silicone.getMolten(5760),
                        Materials.Naquadria.getMolten(2592)},
                ItemList.Conveyor_Module_UHV.get(1), 1000, 200000);

        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Conveyor_Module_UHV.get(1L),
                48000, 64, 200000, 8, new ItemStack[]{
                        ItemList.Electric_Motor_UEV.get(2L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.CosmicNeutronium, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.CosmicNeutronium, 8L),
                        GT_OreDictUnificator.get(OrePrefixes.round, Materials.CosmicNeutronium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Diamericiumtitanium, 2L)
                }, new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(5184),
                        Materials.Lubricant.getFluid(8000),
                        Materials.Silicone.getMolten(11520),
                        Materials.Quantium.getMolten(2592)},
                ItemList.Conveyor_Module_UEV.get(1), 2000, 800000);

        //Piston UHV-UEV
        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Electric_Piston_UV.get(1L),
                24000, 32, 100000, 4, new ItemStack[]{
                        ItemList.Electric_Motor_UHV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 6L),
                        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Neutronium, 8L),
                        GT_OreDictUnificator.get(OrePrefixes.round, Materials.Neutronium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Neutronium, 8L),
                        GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Neutronium, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Neutronium, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Europium, 4L)
                }, new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(2592),
                        Materials.Lubricant.getFluid(4000),
                        Materials.Naquadria.getMolten(2592)},
                ItemList.Electric_Piston_UHV.get(1), 1000, 200000);

        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Electric_Piston_UHV.get(1L),
                48000, 64, 200000, 8, new ItemStack[]{
                        ItemList.Electric_Motor_UEV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.CosmicNeutronium, 6L),
                        GT_OreDictUnificator.get(OrePrefixes.ring, Materials.CosmicNeutronium, 8L),
                        GT_OreDictUnificator.get(OrePrefixes.round, Materials.CosmicNeutronium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.stick, Materials.CosmicNeutronium, 8L),
                        GT_OreDictUnificator.get(OrePrefixes.gear, Materials.CosmicNeutronium, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.CosmicNeutronium, 4L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Diamericiumtitanium, 4L)
                }, new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(5184),
                        Materials.Lubricant.getFluid(8000),
                        Materials.Quantium.getMolten(2592)},
                ItemList.Electric_Piston_UEV.get(1), 2000, 800000);

        //Robot Arm UHV-UEV
        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Robot_Arm_UV.get(1L),
                24000, 32, 100000, 4, new Object[]{
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Neutronium, 8L),
                        GT_OreDictUnificator.get(OrePrefixes.gear, Materials.Neutronium, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Neutronium, 6L),
                        ItemList.Electric_Motor_UHV.get(2L),
                        ItemList.Electric_Piston_UHV.get(1L),
                        new Object[]{OrePrefixes.circuit.get(Materials.Infinite), 2L},
                        new Object[]{OrePrefixes.circuit.get(Materials.Superconductor), 4L},
                        new Object[]{OrePrefixes.circuit.get(Materials.Ultimate), 8L},
                        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Europium, 6L)
                }, new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(4608),
                        Materials.Lubricant.getFluid(4000),
                        Materials.Naquadria.getMolten(2592)},
                ItemList.Robot_Arm_UHV.get(1L), 1000, 200000);

        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Robot_Arm_UHV.get(1L),
                48000, 64, 200000, 8, new Object[]{
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.CosmicNeutronium, 8L),
                        GT_OreDictUnificator.get(OrePrefixes.gear, Materials.CosmicNeutronium, 2L),
                        GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.CosmicNeutronium, 6L),
                        ItemList.Electric_Motor_UEV.get(2L),
                        ItemList.Electric_Piston_UEV.get(1L),
                        new Object[]{OrePrefixes.circuit.get(Materials.Bio), 2L},
                        new Object[]{OrePrefixes.circuit.get(Materials.Infinite), 4L},
                        new Object[]{OrePrefixes.circuit.get(Materials.Superconductor), 8L},
                        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Diamericiumtitanium, 6L)
                }, new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(9216),
                        Materials.Lubricant.getFluid(8000),
                        Materials.Quantium.getMolten(2592)},
                ItemList.Robot_Arm_UEV.get(1L), 2000, 800000);

        //Emitter UHV-UEV
        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Emitter_UV.get(1L),
                24000, 32, 100000, 4, new Object[]{
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Neutronium, 1L),
                        ItemList.Electric_Motor_UHV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Neutronium, 8L),
                        GT_OreDictUnificator.get(OrePrefixes.gemExquisite, Materials.MysteriousCrystal, 4L),
                        new Object[]{OrePrefixes.circuit.get(Materials.Infinite), 4L},
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.ElectrumFlux, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.ElectrumFlux, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.ElectrumFlux, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.ElectrumFlux, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Europium, 7L)
                }, new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(4608),
                        Materials.Naquadria.getMolten(2592)},
                ItemList.Emitter_UHV.get(1L), 1000, 200000);

        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Emitter_UHV.get(1L),
                48000, 64, 200000, 8, new Object[]{
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.CosmicNeutronium, 1L),
                        ItemList.Electric_Motor_UEV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Phoenixite, 12L),
                        GT_OreDictUnificator.get(OrePrefixes.gemExquisite, Materials.EnrichedMysteriousCrystal, 8L),
                        new Object[]{OrePrefixes.circuit.get(Materials.Bio), 4L},
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Infuscolium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Infuscolium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Infuscolium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Infuscolium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Diamericiumtitanium, 7L)
                }, new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(9216),
                        Materials.Quantium.getMolten(2592)},
                ItemList.Emitter_UEV.get(1L), 2000, 800000);

        //Sensor UHV-UEV
        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Sensor_UV.get(1L),
                24000, 32, 100000, 4, new Object[]{
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Neutronium, 1L),
                        ItemList.Electric_Motor_UHV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Neutronium, 8L),
                        GT_OreDictUnificator.get(OrePrefixes.gemExquisite, Materials.MysteriousCrystal, 4L),
                        new Object[]{OrePrefixes.circuit.get(Materials.Infinite), 4L},
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.ElectrumFlux, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.ElectrumFlux, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.ElectrumFlux, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.ElectrumFlux, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Europium, 7L)
                }, new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(4608),
                        Materials.Naquadria.getMolten(2592)},
                ItemList.Sensor_UHV.get(1L), 1000, 200000);

        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Sensor_UHV.get(1L),
                48000, 64, 200000, 8, new Object[]{
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.CosmicNeutronium, 1L),
                        ItemList.Electric_Motor_UEV.get(1),
                        GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Phoenixite, 12L),
                        GT_OreDictUnificator.get(OrePrefixes.gemExquisite, Materials.EnrichedMysteriousCrystal, 8L),
                        new Object[]{OrePrefixes.circuit.get(Materials.Bio), 4L},
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Infuscolium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Infuscolium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Infuscolium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Infuscolium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Draconium, 7L)
                }, new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(9216),
                        Materials.Quantium.getMolten(2592)},
                ItemList.Sensor_UEV.get(1L), 2000, 800000);

        //Fieldgen UHV and UEV
        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Field_Generator_UV.get(1),
                48000, 64, 200000, 8, new Object[]{
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Neutronium, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 6L),
                        new Object[]{OrePrefixes.circuit.get(Materials.Bio), 4L},
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Tritanium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Tritanium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Tritanium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Tritanium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Tritanium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Tritanium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Tritanium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Tritanium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Europium, 8L)
                }, new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(4608),
                        Materials.Naquadria.getMolten(2592)},
                ItemList.Field_Generator_UHV.get(1L), 2000, 200000);

        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Field_Generator_UHV.get(1L),
                96000, 128, 400000, 16, new Object[]{
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.CosmicNeutronium, 1L),
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.CosmicNeutronium, 6L),
                        new Object[]{OrePrefixes.circuit.get(Materials.Bio), 8L},
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Neutronium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Neutronium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Neutronium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Neutronium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Neutronium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Neutronium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Neutronium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Neutronium, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Diamericiumtitanium, 8L)
                }, new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(9216),
                        Materials.Quantium.getMolten(2592)
                },
                ItemList.Field_Generator_UEV.get(1L), 4000, 800000);

        //Circuits UHV - UXV

        //UHV Circuit
        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Circuit_Wetwaresupercomputer.get(1L),
                24000, 64, 50000, 4, new ItemStack[]{
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Tritanium, 2),
                        ItemList.Circuit_Wetwaresupercomputer.get(2L),
                        ItemList.ZPM_Coil.get(16L),
                        ItemList.Circuit_Parts_CapacitorASMD.get(32L),
                        ItemList.Circuit_Parts_ResistorASMD.get(32L),
                        ItemList.Circuit_Parts_TransistorASMD.get(32L),
                        ItemList.Circuit_Parts_DiodeASMD.get(32L),
                        ItemList.Circuit_Chip_Ram.get(48L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorZPM, 64L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, (Materials.Silicone), 64L),
                }, new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(2880L),
                        new FluidStack(FluidRegistry.getFluid("ic2coolant"), 10000),
                        Materials.Radon.getGas(2500L),
                },
                ItemList.Circuit_Wetwaremainframe.get(1L), 2000, 300000);

        //Bio Chips
        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Circuit_Biowarecomputer.get(1L),
                48000, 128, 500000, 8, new ItemStack[]{
                        ItemList.Circuit_Board_Bio_Ultra.get(2L),
                        ItemList.Circuit_Biowarecomputer.get(2L),
                        ItemList.Circuit_Parts_TransistorASMD.get(16L),
                        ItemList.Circuit_Parts_ResistorASMD.get(16L),
                        ItemList.Circuit_Parts_CapacitorASMD.get(16L),
                        ItemList.Circuit_Parts_DiodeASMD.get(48L),
                        ItemList.Circuit_Chip_NOR.get(32L),
                        ItemList.Circuit_Chip_Ram.get(64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.NiobiumTitanium, 32L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Silicone, 16L),
                }, new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(1440L),
                        Materials.BioMediumSterilized.getFluid(1440L),
                        new FluidStack(FluidRegistry.getFluid("ic2coolant"), 10000)
                },
                ItemList.Circuit_Biowaresupercomputer.get(1L), 4000, 500000);

        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Circuit_Biowaresupercomputer.get(1L),
                96000, 256, 1000000, 16, new ItemStack[]{
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Tritanium, 4L),
                        ItemList.Circuit_Biowaresupercomputer.get(2L),
                        ItemList.UV_Coil.get(16L),
                        ItemList.Circuit_Parts_CapacitorASMD.get(64L),
                        ItemList.Circuit_Parts_ResistorASMD.get(64L),
                        ItemList.Circuit_Parts_TransistorASMD.get(64L),
                        ItemList.Circuit_Parts_DiodeASMD.get(64L),
                        ItemList.Circuit_Chip_Ram.get(64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Superconductor, 64),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Silicone, 64),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Polybenzimidazole, 64)
                }, new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(2880L),
                        Materials.BioMediumSterilized.getFluid(2880L),
                        new FluidStack(FluidRegistry.getFluid("ic2coolant"), 20000)
                },
                ItemList.Circuit_Biomainframe.get(1L), 6000, 2000000);

        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Circuit_Biomainframe.get(1L),
                192000, 512, 2000000, 32, new ItemStack[]{
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Tritanium, 8),
                        ItemList.Circuit_Biomainframe.get(2L),
                        ItemList.Circuit_Parts_CapacitorASMD.get(64L),
                        ItemList.Circuit_Parts_ResistorASMD.get(64L),
                        ItemList.Circuit_Parts_TransistorASMD.get(64L),
                        ItemList.Circuit_Parts_DiodeASMD.get(64L),
                        ItemList.Circuit_Chip_Ram.get(64L),
                        ItemList.Circuit_Chip_NPIC.get(64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Diamericiumtitanium, 64),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.Superconductor, 64),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Silicone, 64),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Polybenzimidazole, 64)
                }, new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(3760L),
                        Materials.Naquadria.getMolten(4032L),
                        new FluidStack(FluidRegistry.getFluid("ic2coolant"), 20000)
                },
                ItemList.Circuit_Nano.get(1L), 8000, 8000000);


        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Circuit_Chip_Pico.get(1L),
                384000, 1024, 4000000, 64, new ItemStack[]{
                        ItemList.Circuit_Board_Bio_Ultra.get(1L),
                        ItemList.Circuit_Chip_Pico.get(4L),
                        ItemList.Circuit_Nano.get(2L),
                        ItemList.Circuit_Parts_TransistorASMD.get(64L),
                        ItemList.Circuit_Parts_ResistorASMD.get(64L),
                        ItemList.Circuit_Parts_CapacitorASMD.get(64L),
                        ItemList.Circuit_Parts_DiodeASMD.get(64L),
                        ItemList.Circuit_Chip_PPIC.get(64L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.NiobiumTitanium, 16),
                        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Osmium, 32),
                        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Neutronium, 16),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Lanthanum, 64)
                }, new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(3760L),
                        Materials.UUMatter.getFluid(8000L),
                        Materials.Osmium.getMolten(1152L)
                },
                ItemList.Circuit_Piko.get(1L), 10000, 8000000);

        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Circuit_Piko.get(1L),
                720000, 2048, 8000000, 128, new ItemStack[]{
                        GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Neutronium, 16),
                        ItemList.Circuit_Piko.get(8L),
                        ItemList.Circuit_Parts_CapacitorASMD.get(64L),
                        ItemList.Circuit_Parts_DiodeASMD.get(64L),
                        ItemList.Circuit_Parts_TransistorASMD.get(64L),
                        ItemList.Circuit_Parts_ResistorASMD.get(64L),
                        ItemList.Circuit_Chip_QPIC.get(64L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.NiobiumTitanium, 64),
                        GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.Indium, 64),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Neutronium, 8),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Lanthanum, 64)
                }, new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(3760L),
                        Materials.UUMatter.getFluid(24000L),
                        Materials.Osmium.getMolten(2304L)
                },
                ItemList.Circuit_Quantum.get(1L), 20000, 32000000);

        //Batteries

        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Energy_Cluster.get(1L), 12000, 16, 100000, 3, new Object[]{
                GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Tritanium, 64L),
                new Object[]{OrePrefixes.circuit.get(Materials.Infinite), 4L},
                ItemList.Circuit_HighEnergyFlow.get(4L),
                ItemList.Energy_Cluster.get(8L),
                ItemList.Field_Generator_UV.get(2),
                ItemList.Circuit_Wafer_HPIC.get(64),
                ItemList.Circuit_Wafer_HPIC.get(64),
                ItemList.Circuit_Parts_DiodeASMD.get(64),
                GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Superconductor, 32),
        }, new FluidStack[]{
                Materials.SolderingAlloy.getMolten(2880),
                new FluidStack(FluidRegistry.getFluid("ic2coolant"), 16000)
        }, ItemList.ZPM2.get(1), 3000, 400000);

        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.ZPM2.get(1L), 24000, 64, 200000, 6, new Object[]{
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Neutronium, 32L),
                GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.Neutronium, 32L),
                new Object[]{OrePrefixes.circuit.get(Materials.Bio), 4L},
                ItemList.Circuit_HighEnergyFlow.get(8L),
                ItemList.ZPM2.get(8),
                ItemList.Field_Generator_UHV.get(4),
                ItemList.Circuit_Wafer_UHPIC.get(64),
                ItemList.Circuit_Wafer_UHPIC.get(64),
                ItemList.Circuit_Wafer_SoC2.get(32),
                ItemList.Circuit_Parts_DiodeASMD.get(64),
                GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.SuperconductorUEV, 64),
        }, new FluidStack[]{
                Materials.SolderingAlloy.getMolten(3760),
                Materials.Naquadria.getMolten(9000),
                new FluidStack(FluidRegistry.getFluid("ic2coolant"), 32000)
        }, ItemList.ZPM3.get(1), 4000, 1600000);

        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.MysteriousCrystal.get(1L), 12000, 16, 500000, 3, new Object[]{
                        ItemList.Circuit_Board_Bio_Ultra.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.foil, Materials.Neutronium, 32L),
                        new Object[]{OrePrefixes.circuit.get(Materials.Infinite), 4L},
                        ItemList.Circuit_HighEnergyFlow.get(2L),
                        ItemList.Circuit_Parts_MECrystal_Chip_Elite.get(36L),
                        ItemList.Circuit_Parts_MECrystal_Chip_Elite.get(36L),
                        ItemList.Circuit_Chip_PPIC.get(64L),
                        ItemList.Circuit_Parts_DiodeASMD.get(64L),
                        ItemList.Circuit_Parts_CapacitorASMD.get(64L),
                        ItemList.Circuit_Parts_ResistorASMD.get(64L),
                        ItemList.Circuit_Parts_TransistorASMD.get(64L),
                        GT_OreDictUnificator.get(OrePrefixes.wireFine.get(Materials.Neutronium), 64L)},
                new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(1440),
                        new FluidStack(FluidRegistry.getFluid("ic2coolant"), 8000)},
                ItemList.MysteriousCrystalOrb.get(1), 1000, 2000000);

        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.MysteriousCrystalOrb.get(1L), 24000, 16, 1000000, 6, new Object[]{
                        GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Phoenixite, 16L),
                        new Object[]{OrePrefixes.circuit.get(Materials.Bio), 4L},
                        ItemList.Circuit_HighEnergyFlow.get(4L),
                        ItemList.MysteriousCrystalOrb.get(8L),
                        ItemList.Field_Generator_UEV.get(2),
                        ItemList.Circuit_Wafer_SoC3.get(64),
                        ItemList.Circuit_Wafer_SoC3.get(64),
                        ItemList.Circuit_Parts_DiodeASMD.get(64),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.SuperconductorUEV, 16),},
                new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(2880),
                        new FluidStack(FluidRegistry.getFluid("ic2coolant"), 16000)},
                ItemList.MysteriousCrystalModule.get(1), 2000, 8000000);

        //Blocks and Multiblocks
        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Casing_Core_Chamber.get(1L), 48000, 128, 1000000, 6, new Object[]{
                ItemList.Teleporter.get(1L),
                ItemList.Casing_Dyson_Ring.get(16L),
                ItemList.Casing_Fusion_Coil.get(16L),
                ItemList.Field_Generator_UV.get(4L),
                ItemList.Sensor_UHV.get(4L),
                ItemList.Emitter_UHV.get(4L),
                new Object[]{OrePrefixes.circuit.get(Materials.Infinite), 4L},
                GT_OreDictUnificator.get(OrePrefixes.lens, Materials.EnrichedMysteriousCrystal, 32),
                GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Superconductor, 16)
        }, new FluidStack[]{
                Materials.UUMatter.getFluid(2000),
                Materials.Neutronium.getMolten(2592),
                new FluidStack(FluidRegistry.getFluid("ic2coolant"), 4000),
                Materials.Osmiridium.getMolten(1296)
        },
                ItemList.Machine_MultiblockTesseract.get(1L), 8000, 2000000);

        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Casing_Fusion_Coil3.get(1L), 12000, 32, 500000, 6, new Object[]{
                ItemList.Casing_Fusion_Coil3.get(1L),
                ItemList.Circuit_Nano.get(1L),
                ItemList.Circuit_Nano.get(1L),
                ItemList.Circuit_Nano.get(1L),
                ItemList.Circuit_Nano.get(1L),
                GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Neutronium, 4L),
                ItemList.Field_Generator_UHV.get(2L),
                ItemList.Circuit_Wafer_QPIC.get(64L),
                ItemList.Circuit_Wafer_QPIC.get(64L),
                GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.SuperconductorUEV, 32),
        }, new FluidStack[]{
                Materials.SolderingAlloy.getMolten(5760),
                Materials.Phoenixite.getMolten(2304L)
        },
                ItemList.FusionComputer_UEV.get(1L), 1600, 1000000);

        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Cover_SolarPanel_UV.get(1L),
                96000, 128, 500000, 16, new ItemStack[]{
                        ItemList.Cover_SolarPanel_UV.get(2L),
                        ItemList.Circuit_Nano.get(8L),
                        GT_OreDictUnificator.get(OrePrefixes.plateQuintuple, Materials.Sunnarium, 8),
                        ItemList.Circuit_Silicon_Wafer10.get(4L),
                        ItemList.Circuit_Silicon_Wafer6.get(4L),
                        ItemList.Circuit_Chip_RPico.get(4L),
                        GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 12),
                        GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Neutronium, 3),
                        GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.PerroxPolymer, 2),
                        GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Silicon, 8),
                        GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.ElectrumFlux, 8),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt08, Materials.Superconductor, 16),
                        ItemList.Circuit_Chip_QPIC.get(8L)
                },
                new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(1890)
                },
                ItemList.Cover_SolarPanel_UHV.get(1), 840, 8000000);

        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Cover_SolarPanel_UHV.get(1L),
                192000, 512, 2000000, 32, new ItemStack[]{
                        ItemList.Cover_SolarPanel_UHV.get(1L),
                        ItemList.Circuit_Piko.get(8L),
                        GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Sunnarium, 8),
                        ItemList.Circuit_Chip_Pico.get(8L),
                        GT_OreDictUnificator.get(OrePrefixes.plateAlloy, Materials.Carbon, 16),
                        GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Phoenixite, 4),
                        GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.PerroxPolymer, 4),
                        GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.Silicon, 8),
                        GT_OreDictUnificator.get(OrePrefixes.plateQuadruple, Materials.ElectrumFlux, 8),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt12, Materials.SuperconductorUEV, 18),
                        ItemList.Circuit_Chip_FPIC.get(12L)
                },
                new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(3780)
                },
                ItemList.Cover_SolarPanel_UEV.get(1), 960, 32000000);

        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Machine_DigitalTransformer_UV.get(1L),
                12000, 16, 100000, 2, new Object[]{
                        GT_ModHandler.getModItem("gregtech", "gt.blockmachines", 1L, 12159),
                        ItemList.Cover_Screen.get(16L),
                        GT_OreDictUnificator.get(OrePrefixes.spring, Materials.Osmium, 2L),
                        ItemList.UHV_Coil.get(1L),
                        ItemList.Circuit_HighEnergyFlow.get(1L, new Object() {
                        }),
                        new Object[]{OrePrefixes.circuit.get(Materials.Infinite), 2},
                },
                new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(11520)
                },
                ItemList.Machine_DigitalTransformer_UHV.get(1L), 1000, 2000000);

        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Machine_DigitalTransformer_UHV.get(1L),
                24000, 32, 100000, 4, new Object[]{
                        GT_ModHandler.getModItem("gregtech", "gt.blockmachines", 1L, 12160),
                        ItemList.Cover_Screen.get(32L),
                        GT_OreDictUnificator.get(OrePrefixes.spring, Materials.Osmium, 2L),
                        ItemList.UEV_Coil.get(1L),
                        ItemList.Circuit_HighEnergyFlow.get(2L, new Object() {
                        }),
                        new Object[]{OrePrefixes.circuit.get(Materials.Bio), 2},
                },
                new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(23040)
                },
                ItemList.Machine_DigitalTransformer_UEV.get(1L), 2000, 8000000);

        //UHV - UIV Energy Hatches
        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Hatch_Energy_UV.get(1L),
                24000, 64, 500000, 4, new Object[]{
                        ItemList.Hull_MAX.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.Superconductor, 2L),
                        ItemList.Circuit_Chip_QPIC.get(2L),
                        ItemList.Circuit_HighEnergyFlow.get(1L),
                        new Object[]{OrePrefixes.circuit.get(Materials.Infinite), 2},
                        ItemList.UHV_Coil.get(2L),
                        new ItemStack[]{ItemList.Reactor_Coolant_He_6.get(1), ItemList.Reactor_Coolant_NaK_6.get(1), ItemList.Reactor_Coolant_Le_2.get(1)},
                        new ItemStack[]{ItemList.Reactor_Coolant_He_6.get(1), ItemList.Reactor_Coolant_NaK_6.get(1), ItemList.Reactor_Coolant_Le_2.get(1)},
                        new ItemStack[]{ItemList.Reactor_Coolant_He_6.get(1), ItemList.Reactor_Coolant_NaK_6.get(1), ItemList.Reactor_Coolant_Le_2.get(1)},
                        ItemList.Electric_Pump_UHV.get(1L)},
                new FluidStack[]{
                        new FluidStack(FluidRegistry.getFluid("ic2coolant"), 16000),
                        Materials.SolderingAlloy.getMolten(5760)
                },
                ItemList.Hatch_Energy_MAX.get(1), 1000, 2000000);

        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Hatch_Energy_MAX.get(1L),
                48000, 128, 1000000, 4, new Object[]{
                        ItemList.Hull_UEV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt04, Materials.SuperconductorUEV, 6L),
                        ItemList.Circuit_Chip_QPIC.get(4L),
                        ItemList.Circuit_HighEnergyFlow.get(2L),
                        new Object[]{OrePrefixes.circuit.get(Materials.Bio), 2},
                        ItemList.UEV_Coil.get(2L),
                        ItemList.Reactor_Coolant_Le_2.get(1L),
                        ItemList.Reactor_Coolant_Le_2.get(1L),
                        ItemList.Reactor_Coolant_Le_2.get(1L),
                        ItemList.Electric_Pump_UEV.get(1L)},
                new FluidStack[]{
                        new FluidStack(FluidRegistry.getFluid("ic2coolant"), 32000),
                        Materials.SolderingAlloy.getMolten(11520)
                },
                ItemList.Hatch_Energy_UEV.get(1), 1200, 8000000);

        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Hatch_Energy_UEV.get(1L),
                96000, 256, 2000000, 4, new Object[]{
                        ItemList.Hull_UIV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.wireGt08, Materials.Neutronium, 8L),
                        ItemList.Circuit_Chip_QPIC.get(8L),
                        ItemList.Circuit_HighEnergyFlow.get(4L),
                        new Object[]{OrePrefixes.circuit.get(Materials.Bio), 2},
                        ItemList.UIV_Coil.get(2L),
                        ItemList.Reactor_Coolant_Le_2.get(1L),
                        ItemList.Reactor_Coolant_Le_2.get(1L),
                        ItemList.Reactor_Coolant_Le_2.get(1L),
                        ItemList.Reactor_Coolant_Le_2.get(1L),
                        ItemList.Electric_Pump_UEV.get(1L)},
                new FluidStack[]{
                        new FluidStack(FluidRegistry.getFluid("ic2coolant"), 64000),
                        Materials.SolderingAlloy.getMolten(23040)
                },
                ItemList.Hatch_Energy_UIV.get(1), 1400, 32000000);

        //UHV - UIV Dynamo Hatches
        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Hatch_Dynamo_UV.get(1L),
                24000, 64, 500000, 4, new Object[]{
                        ItemList.Hull_MAX.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.spring, Materials.Europium, 4L),
                        ItemList.Circuit_Chip_QPIC.get(2L),
                        ItemList.Circuit_HighEnergyFlow.get(1L),
                        new Object[]{OrePrefixes.circuit.get(Materials.Infinite), 2},
                        ItemList.UHV_Coil.get(2L),
                        new ItemStack[]{ItemList.Reactor_Coolant_He_6.get(1), ItemList.Reactor_Coolant_NaK_6.get(1), ItemList.Reactor_Coolant_Le_2.get(1)},
                        new ItemStack[]{ItemList.Reactor_Coolant_He_6.get(1), ItemList.Reactor_Coolant_NaK_6.get(1), ItemList.Reactor_Coolant_Le_2.get(1)},
                        new ItemStack[]{ItemList.Reactor_Coolant_He_6.get(1), ItemList.Reactor_Coolant_NaK_6.get(1), ItemList.Reactor_Coolant_Le_2.get(1)},
                        ItemList.Electric_Pump_UHV.get(1L)},
                new FluidStack[]{
                        new FluidStack(FluidRegistry.getFluid("ic2coolant"), 16000),
                        Materials.SolderingAlloy.getMolten(5760)
                },
                ItemList.Hatch_Dynamo_MAX.get(1), 1000, 2000000);

        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Hatch_Dynamo_MAX.get(1L),
                48000, 128, 1000000, 4, new Object[]{
                        ItemList.Hull_UEV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.spring, Materials.Diamericiumtitanium, 6L),
                        ItemList.Circuit_Chip_QPIC.get(4L),
                        ItemList.Circuit_HighEnergyFlow.get(2L),
                        new Object[]{OrePrefixes.circuit.get(Materials.Bio), 2},
                        ItemList.UEV_Coil.get(2L),
                        ItemList.Reactor_Coolant_Le_2.get(1L),
                        ItemList.Reactor_Coolant_Le_2.get(1L),
                        ItemList.Reactor_Coolant_Le_2.get(1L),
                        ItemList.Electric_Pump_UEV.get(1L)},
                new FluidStack[]{
                        new FluidStack(FluidRegistry.getFluid("ic2coolant"), 32000),
                        Materials.SolderingAlloy.getMolten(11520)
                },
                ItemList.Hatch_Dynamo_UEV.get(1), 1200, 8000000);

        TT_recipeAdder.addResearchableAssemblylineRecipe(ItemList.Hatch_Dynamo_UEV.get(1L),
                96000, 256, 2000000, 4, new Object[]{
                        ItemList.Hull_UIV.get(1L),
                        GT_OreDictUnificator.get(OrePrefixes.spring, Materials.Neutronium, 8L),
                        ItemList.Circuit_Chip_QPIC.get(8L),
                        ItemList.Circuit_HighEnergyFlow.get(4L),
                        new Object[]{OrePrefixes.circuit.get(Materials.Bio), 2},
                        ItemList.UIV_Coil.get(2L),
                        ItemList.Reactor_Coolant_Le_2.get(1L),
                        ItemList.Reactor_Coolant_Le_2.get(1L),
                        ItemList.Reactor_Coolant_Le_2.get(1L),
                        ItemList.Reactor_Coolant_Le_2.get(1L),
                        ItemList.Electric_Pump_UEV.get(1L)},
                new FluidStack[]{
                        new FluidStack(FluidRegistry.getFluid("ic2coolant"), 64000),
                        Materials.SolderingAlloy.getMolten(23040)
                },
                ItemList.Hatch_Dynamo_UIV.get(1), 1400, 32000000);

        //Stargate Stuff
        //if (Loader.isModLoaded("SGCraft")) {
            TT_recipeAdder.addResearchableAssemblylineRecipe(GT_OreDictUnificator.get(OrePrefixes.foil, Materials.CosmicNeutronium, 1L),
                    96000, 256, 2000000, 8, new ItemStack[]{
                            ItemList.Sensor_UV.get(16L),
                            GT_OreDictUnificator.get(OrePrefixes.block, Materials.Adamantium, 16L),
                            GT_OreDictUnificator.get(OrePrefixes.block, Materials.Osmiridium, 16L),
                            GT_OreDictUnificator.get(OrePrefixes.block, Materials.NaquadahAlloy, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.block, Materials.NaquadahAlloy, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.block, Materials.NaquadahAlloy, 64L)
                    },
                    new FluidStack[]{
                            Materials.Neutronium.getMolten(9216L),
                            Materials.Tritanium.getMolten(9216L),
                            Materials.Tetranaquadahdiindiumhexaplatiumosminid.getMolten(9216L),
                            Materials.Silver.getPlasma(9216L)
                    },
                    CoreItems2.getRecipe(147, 1), 1200, 2000000);

            TT_recipeAdder.addResearchableAssemblylineRecipe(CoreItems2.getRecipe(147, 1),
                    96000, 256, 2000000, 8, new Object[]{
                            ItemList.Electric_Piston_UV.get(4L),
                            ItemList.Electric_Motor_UV.get(16L),
                            GT_OreDictUnificator.get(OrePrefixes.block, Materials.Adamantium, 16L),
                            GT_OreDictUnificator.get(OrePrefixes.block, Materials.NaquadahAlloy, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.DraconiumAwakened, 8L),
                            GT_OreDictUnificator.get(OrePrefixes.plateDense, Materials.DraconiumAwakened, 8L),
                            GT_OreDictUnificator.get(OrePrefixes.gemExquisite, Materials.Ruby, 16L),
                            GT_OreDictUnificator.get(OrePrefixes.gemExquisite, Materials.GarnetRed, 16L),
                            new Object[]{OrePrefixes.circuit.get(Materials.Nano), 1}
                    },
                    new FluidStack[]{
                            Materials.Neutronium.getMolten(9216L),
                            Materials.Tritanium.getMolten(9216L),
                            Materials.Tetranaquadahdiindiumhexaplatiumosminid.getMolten(9216L),
                            Materials.Silver.getPlasma(9216L)
                    },
                    CoreItems2.getRecipe(149, 1), 1200, 2000000);

            TT_recipeAdder.addResearchableAssemblylineRecipe(GT_OreDictUnificator.get(OrePrefixes.frameGt, Materials.Neutronium, 1L),
                    96000, 256, 2000000, 8, new ItemStack[]{
                            GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Adamantium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.NaquadahAlloy, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.CosmicNeutronium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Neutronium, 64L),
                            GT_OreDictUnificator.get(OrePrefixes.stickLong, Materials.Osmiridium, 64L)
                    },
                    new FluidStack[]{
                            Materials.Neutronium.getMolten(9216L),
                            Materials.Tritanium.getMolten(9216L),
                            Materials.Concrete.getMolten(9216L)
                    },
                    CoreItems2.getRecipe(148, 1), 1200, 2000000);

        TT_recipeAdder.addResearchableAssemblylineRecipe(GT_ModHandler.getModItem("SGCraft", "sgIrisUpgrade", 1L),
                192000, 512, 2000000, 16, new Object[]{
                        GT_ModHandler.getModItem("SGCraft", "stargateController", 1L),
                        GT_ModHandler.getModItem("SGCraft", "stargateBase", 1L),
                        ItemList.Field_Generator_UEV.get(4L),
                        ItemList.Sensor_UEV.get(16L),
                        ItemList.Emitter_UEV.get(16L),
                        GT_ModHandler.getModItem("SGCraft", "stargateRing", 7L, 1),
                        GT_ModHandler.getModItem("SGCraft", "stargateRing", 15L),
                        GT_ModHandler.getModItem("SGCraft", "ic2PowerUnit", 3L),
                        new Object[]{OrePrefixes.circuit.get(Materials.Quantum), 1}
                },
                new FluidStack[]{
                        Materials.InfinityCatalyst.getMolten(2304L),
                        Materials.PerroxPolymer.getMolten(9216L),
                        Materials.Phoenixite.getMolten(9216L),
                        Materials.Americium.getPlasma(9216L)
                },
                GT_ModHandler.getModItem("ExtraUtilities", "dark_portal", 1L), 6000, 32000000);
        //}

        //Hyper Engine
        TT_recipeAdder.addResearchableAssemblylineRecipe(GT_OreDictUnificator.get(OrePrefixes.pipeHuge, Materials.Naquadah, 1),
                24000, 64, 500000, 4, new Object[]{
                        GT_ItemList.Naquadah_Liquid_Enriched.get(1L),
                        ItemList.Casing_Fusion_Coil.get(8L),
                        ItemList.Electric_Pump_UV.get(12L),
                        ItemList.Field_Generator_UV.get(16L),
                        ItemList.Sensor_UV.get(16L),
                        new Object[]{OrePrefixes.circuit.get(Materials.Superconductor), 32},
                        GT_OreDictUnificator.get(OrePrefixes.wireGt08, Materials.SuperconductorUV, 32),
                        GT_OreDictUnificator.get(OrePrefixes.lens, Materials.MysteriousCrystal, 64)
                },
                new FluidStack[]{
                        Materials.SolderingAlloy.getMolten(4608),
                        Materials.Lubricant.getFluid(4000),
                        new FluidStack(FluidRegistry.getFluid("ic2coolant"), 4000),
                        Materials.Quantium.getMolten(4608)
                },
                GT_ItemList.Naquadah_multi.get(1L), 320 * 20, 500000);

    }
}
