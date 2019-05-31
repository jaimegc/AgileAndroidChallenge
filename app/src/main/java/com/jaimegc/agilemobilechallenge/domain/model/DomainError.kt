package com.jaimegc.agilemobilechallenge.domain.model

sealed class DomainError {
    object NotInternetDomainError : DomainError()
    object GenericDomainError : DomainError()
    data class UnknownDomainError(val errorMessage: String = "Unknown Error") : DomainError()
    data class NotIndexStringFoundDomainError(val key: String) : DomainError()
}
