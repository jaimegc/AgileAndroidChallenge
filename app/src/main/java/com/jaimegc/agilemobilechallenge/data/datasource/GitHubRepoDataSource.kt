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


/**
 *  Interfaces DS
 */
interface GitHubRepoDataSource {
    suspend fun getGitHubReposByUser(name: String): Either<DomainError, List<GitHubRepo>>
    fun populate(name: String, repos: List<GitHubRepo>) {}
    fun isUpdated(): Boolean = true
    fun contains(key: String): Boolean = true
    fun invalidateCache() {}
}

/**
 *  Network DS
 */
class NetworkGitHubRepoDataSource(
    private val apiClient: GitHubRepoApiClient
) : GitHubRepoDataSource {

    override suspend fun getGitHubReposByUser(name: String): Either<DomainError, List<GitHubRepo>> =
        try {
            mapResponse(apiClient.getGitHubReposByUser(name)) { GitHubReposDtoToGitHubReposMapper()(it.results()) }
        } catch (exception: Exception) {
            Either.left(exception.apiException())
        }
}

/**
 *  Memory DS
 */
class MemoryGitHubRepoDataSource(
    private val timeProvider: TimeProvider
) : GitHubRepoDataSource {

    companion object {
        private val CACHE_TIME = TimeUnit.MINUTES.toMillis(10)
    }

    private val cache = linkedMapOf<String, List<GitHubRepo>>()
    private var lastUpdate = 0L

    override suspend fun getGitHubReposByUser(name: String): Either<DomainError, List<GitHubRepo>> =
        cache[name]?.let { Either.right(it) } ?: Either.left(DomainError.NotIndexStringFoundDomainError(name))

    override fun isUpdated(): Boolean =
        timeProvider.time() - lastUpdate < CACHE_TIME

    override fun contains(key: String): Boolean =
        cache.contains(key)

    override fun invalidateCache() {
        lastUpdate = 0
        cache.clear()
    }

    override fun populate(name: String, repos: List<GitHubRepo>) {
        lastUpdate = timeProvider.time()
        cache[name] = repos
    }
}