package com.impact.mods.gregtech.enums;

import java.util.Objects;

@lombok.AllArgsConstructor
public class VeinChunk {
	public int idDim;
	public int xChunk;
	public int zChunk;
	public int tierVein;
	public int sizeVein;
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (hashCode() == o.hashCode()) return true;
		VeinChunk veinChunk = (VeinChunk) o;
		return idDim == veinChunk.idDim && xChunk == veinChunk.xChunk && zChunk == veinChunk.zChunk && tierVein == veinChunk.tierVein;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(idDim, xChunk, zChunk, tierVein);
	}
}