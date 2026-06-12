package com.impact.client.render.special

enum class RenderSpecialType {
    Unknown,
    BlockHighlight,
    SphereHighlight,
    PathHighlight,
    ;

    companion object {
        fun valueOf(ordinal: Int): RenderSpecialType {
            return RenderSpecialType.entries.getOrNull(ordinal) ?: Unknown
        }
    }
}
