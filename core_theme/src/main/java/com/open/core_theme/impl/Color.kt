package com.open.core_theme.impl

internal class Color(
    val a: Int = 0,
    val r: Int = 0,
    val g: Int = 0,
    val b: Int = 0
) {

    fun isLightColor(): Boolean {
        return r > 0x7f || g > 0x7f || b > 0x7f
    }


    companion object {
        @JvmStatic
        fun parseColor(color: Int): Color {
            val a = color shr 24 and 0xff
            val r = color shr 16 and 0xff
            val g = color shr 8 and 0xff
            val b = color and 0xff
            return Color(a, r, g, b)
        }
    }
}