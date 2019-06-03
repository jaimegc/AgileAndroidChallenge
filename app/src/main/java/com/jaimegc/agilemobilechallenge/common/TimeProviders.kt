package com.jaimegc.agilemobilechallenge.common


interface TimeProvider {
    fun time(): Long
}

class RealTimeProvider : TimeProvider {
    override fun time(): Long = System.currentTimeMillis()
}