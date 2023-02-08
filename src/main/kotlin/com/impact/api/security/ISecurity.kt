package com.impact.api.security

interface ISecurity {
    fun updateSecurity(key: String?)
    fun getSecurity(): String?
}