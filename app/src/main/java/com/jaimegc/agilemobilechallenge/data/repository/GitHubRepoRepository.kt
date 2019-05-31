package com.jaimegc.agilemobilechallenge.data.repository

import arrow.core.Either
import com.jaimegc.agilemobilechallenge.data.datasource.GitHubRepoDataSource
import com.jaimegc.agilemobilechallenge.domain.model.DomainError
import com.jaimegc.agilemobilechallenge.domain.model.GitHubRepo

class GitHubRepoRepository(
    private val dataSources: List<GitHubRepoDataSource>
) {
    suspend fun getGitHubReposByUser(name: String): Either<DomainError, List<GitHubRepo>> =
        dataSources.first { it.isUpdated() }.getGitHubReposByUser(name).map {
            it.also { _ ->
                dataSources.forEach { ds -> ds.populate(name, it) }
            }
        }

    fun invalidateCache() =
        dataSources.forEach { it.invalidateCache() }
}