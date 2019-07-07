package com.jaimegc.agilemobilechallenge.data.datasource

import arrow.core.Either
import com.jaimegc.agilemobilechallenge.common.TimeProvider
import com.jaimegc.agilemobilechallenge.common.extensions.apiException
import com.jaimegc.agilemobilechallenge.common.extensions.mapResponse
import com.jaimegc.agilemobilechallenge.common.extensions.results
import com.jaimegc.agilemobilechallenge.data.api.client.GitHubRepoApiClient
import com.jaimegc.agilemobilechallenge.data.api.mapper.GitHubReposDtoToGitHubReposMapper
import com.jaimegc.agilemobilechallenge.domain.model.DomainError
import com.jaimegc.agilemobilechallenge.domain.model.GitHubRepo
import java.util.concurrent.TimeUnit


class RemoteGitHubRepoDataSource(
    private val apiClient: GitHubRepoApiClient
) {

    suspend fun getGitHubReposByUser(name: String): Either<DomainError, List<GitHubRepo>> =
        try {
            mapResponse(apiClient.getGitHubReposByUser(name)) { GitHubReposDtoToGitHubReposMapper()(it.results()) }
        } catch (exception: Exception) {
            Either.left(exception.apiException())
        }
}

class LocalGitHubRepoDataSource(
    private val timeProvider: TimeProvider
) {

    companion object {
        private val CACHE_TIME = TimeUnit.MINUTES.toMillis(10)
    }

    private val cache = linkedMapOf<String, List<GitHubRepo>>()
    private var lastUpdate = 0L

    fun getGitHubReposByUser(name: String): Either<DomainError, List<GitHubRepo>> =
        cache[name]?.let { Either.right(it) } ?: Either.left(DomainError.NotIndexStringFoundDomainError(name))

    fun save(name: String, repos: List<GitHubRepo>) {
        lastUpdate = timeProvider.time()
        cache[name] = repos
    }

    fun isValid(name: String, forceRefresh: Boolean = false): Boolean =
        if (!forceRefresh) {
            isUpdated() && contains(name)
        } else {
            invalidateCache()
            false
        }

    private fun isUpdated(): Boolean =
        timeProvider.time() - lastUpdate < CACHE_TIME

    private fun contains(key: String): Boolean =
        cache.contains(key)

    private fun invalidateCache() {
        lastUpdate = 0
        cache.clear()
    }
}