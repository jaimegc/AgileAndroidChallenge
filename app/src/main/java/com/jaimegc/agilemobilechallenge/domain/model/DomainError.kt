package com.jaimegc.agilemobilechallenge.domain.model

sealed class DomainError {
    object NotInternetDomainError : DomainError()
    object GenericDomainError : DomainError()
    object UserNotFoundDomainError : DomainError()
    object UserUnknownDomainError : DomainError()
    object UserNotCachedDomainError : DomainError()
    data class UnknownDomainError(val errorMessage: String = "Unknown Error") : DomainError()
    data class NotIndexStringFoundDomainError(val key: String) : DomainError()
}
