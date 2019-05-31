package com.jaimegc.agilemobilechallenge.common.extensions

import arrow.core.Either
import com.jaimegc.agilemobilechallenge.data.api.error.Server4xxApiException
import com.jaimegc.agilemobilechallenge.data.api.error.Server500ApiException
import com.jaimegc.agilemobilechallenge.data.api.error.ServerConnectionApiException
import com.jaimegc.agilemobilechallenge.domain.model.DomainError
import java.net.ConnectException

fun Exception.apiException(): DomainError =
    when {
        this is ServerConnectionApiException -> DomainError.NotInternetDomainError
        this is Server500ApiException -> DomainError.GenericDomainError
        this is Server4xxApiException -> DomainError.GenericDomainError
        this.cause is ConnectException -> DomainError.NotInternetDomainError
        else -> DomainError.GenericDomainError
    }

fun <T, R> mapResponse(response: T?, mapper: (T?) -> R): Either<DomainError, R> =
    try {
        Either.right(mapper(response))
    } catch (exception: Exception) {
        Either.left(DomainError.UnknownDomainError())
    }

fun <A> List<A>?.results(): List<A> =
    this ?: emptyList()