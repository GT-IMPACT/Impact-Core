package com.impact.util.string;

import static com.impact.util.string.Language.transMTB;

public enum Lang {
	impact("IMPACT", 1),
	multi_amperes_generator("Multi-Amperes generator"),
	parallel_point_basic("basic"),
	parallel_point("Parallel Point"),
	per_second("per second"),
	pollution("Pollution"),
	screw_type("Select type machine with Screwdriver click in Controller"),
	screw_type_different("with Screwdriver click in Controller"),
	select("Select"),
	separated_bus("Select Separated Buses mod with Shift + Screwdriver click in Controller"),
	parallel_hatch("Parallel Hatch"),
	begin_structure_block("Description of the structure in development"),
	structure("Structure"),
	blueprint("Follow the %s Hologram Projector to build the main structure"),
	at_least("at least"),
	hatch_energy("Energy Hatch"),
	hatch_dynamo("Dynamo Hatch"),
	hatch_muffler("Muffler Hatch"),
	hatch_maintenance("Maintenance Hatch"),
	hatch_io("I/O Hatch"),
	hatch_nuclear("Nuclear Rod Hatch"),
	hatch_bus_in("Input Bus"),
	hatch_in("Input Hatch"),
	hatch_bus_out("Output Bus"),
	hatch_out("Output Hatch"),
	max("max"),
	min("min"),
	to_structure("to structure"),
	to_more_info("to more info"),
	left_ctrl("LCTRL"),
	left_shift("LSHIFT"),
	hold("Hold"),
	any_case("Any casing"),
	get_eu_t("Outputs %s EU/t"),
	generator("Generator"),
	steam_producer("Steam Producer"),
	single_analog("One-block machine analog"),
	dimensions("Dimensions"),
	wxhxl("(WxHxL)"),
	holo_details("Impact Hologram Details"),
	efficiency_loss("Overclocking without loss of efficiency"),
	;
	
	public String eng;
	
	Lang(String eng) {
		this.eng = eng;
		transMTB(this.name(), eng, true);
	}
	
	Lang(String eng, int kek) {
		this.eng = eng;
	}
	
	public String get() {
		return transMTB(this.name(), eng, false);
	}
}