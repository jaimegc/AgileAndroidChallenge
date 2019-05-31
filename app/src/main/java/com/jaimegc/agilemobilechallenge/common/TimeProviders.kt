package com.jaimegc.agilemobilechallenge.common


class RealTimeExecutionTypeProvider : TimeProvider {
    override fun time(): Long = System.currentTimeMillis()
}

interface TimeProvider {
    fun time(): Long
}

class RealTimeProvider : TimeProvider {
    override fun time(): Long = System.currentTimeMillis()
}