package com.impact.api.security

interface ISecurity {
    companion object {
        fun generateSecurity(key: String, value: Int): String {
            return "$key:$value"
        }

        fun getFrequencyFromSecurity(security: String, key: String): Int {
            return try {
                val keySecurity = security.substringBefore(":")
                val freq = security.substringAfter(":")

                if (keySecurity == key) freq.toInt() else -1
            } catch (e: Exception) {
                -1
            }
        }
    }

    fun updateSecurity(key: String)
}