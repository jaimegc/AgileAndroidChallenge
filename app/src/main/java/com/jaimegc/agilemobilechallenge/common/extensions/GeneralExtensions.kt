package com.jaimegc.agilemobilechallenge.common.extensions


fun <A> List<A>?.results(): List<A> =
    this ?: emptyList()