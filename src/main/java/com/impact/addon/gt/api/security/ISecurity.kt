package com.impact.addon.gt.api.security

interface ISecurity {
    fun updateSecurity(key: String?)
    fun getSecurity(): String?
}