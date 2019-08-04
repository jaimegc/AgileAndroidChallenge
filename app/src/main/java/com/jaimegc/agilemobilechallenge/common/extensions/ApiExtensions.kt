package com.jaimegc.agilemobilechallenge.common.extensions

import arrow.core.Either
import com.jaimegc.agilemobilechallenge.data.api.config.ServerApiClient
import com.jaimegc.agilemobilechallenge.data.api.error.*
import com.jaimegc.agilemobilechallenge.domain.model.DomainError
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

fun Exception.apiException(): DomainError =
    when {
        // For Retrofit > 2.6.0
        this is HttpException -> toDomainError()
        this is UnknownHostException -> DomainError.NotInternetDomainError
        // For Retrofit < 2.6.0
        this is ServerConnectionApiException -> DomainError.NotInternetDomainError
        this is Server500ApiException -> DomainError.GenericDomainError
        this is Server404ApiException -> DomainError.UserNotFoundDomainError
        this is Server403ApiException -> DomainError.UserUnknownDomainError
        this.cause is ConnectException -> DomainError.NotInternetDomainError
        // For all
        else -> DomainError.GenericDomainError
    }

fun HttpException.toDomainError(): DomainError =
    when (this.code()) {
        ServerApiClient.RESPONSE_ERROR_500 -> throw Server500ApiException
        ServerApiClient.RESPONSE_ERROR_404 -> throw Server404ApiException
        ServerApiClient.RESPONSE_ERROR_403 -> DomainError.UserUnknownDomainError
        else -> DomainError.GenericDomainError
    }

fun <T, R> mapResponse(response: T?, mapper: (T?) -> R): Either<DomainError, R> =
    try {
        Either.right(mapper(response))
    } catch (exception: Exception) {
        Either.left(DomainError.UnknownDomainError())
    }