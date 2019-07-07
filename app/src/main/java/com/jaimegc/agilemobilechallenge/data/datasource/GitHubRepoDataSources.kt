package com.jaimegc.agilemobilechallenge.data.datasource

import arrow.core.Either
import com.jaimegc.agilemobilechallenge.common.TimeProvider
import com.jaimegc.agilemobilechallenge.common.extensions.apiException
import com.jaimegc.agilemobilechallenge.common.extensions.mapResponse
import com.jaimegc.agilemobilechallenge.common.extensions.results
import com.jaimegc.agilemobilechallenge.data.api.client.GitHubRepoApiClient
import com.jaimegc.agilemobilechallenge.data.api.mapper.GitHubReposDtoToGitHubReposMapper
import com.jaimegc.agilemobilechallenge.data.datasource.room.GitHubRepositoryDao
import com.jaimegc.agilemobilechallenge.data.datasource.room.GitHubRepositoryEntity
import com.jaimegc.agilemobilechallenge.data.datasource.room.GitHubUserDao
import com.jaimegc.agilemobilechallenge.data.datasource.room.GitHubUserEntity
import com.jaimegc.agilemobilechallenge.domain.model.DomainError
import com.jaimegc.agilemobilechallenge.domain.model.GitHubRepo
import com.jaimegc.agilemobilechallenge.domain.model.OwnerRepo
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
    private val daoRepository: GitHubRepositoryDao,
    private val daoUser: GitHubUserDao,
    private val timeProvider: TimeProvider
) {

    companion object {
        private val CACHE_TIME = TimeUnit.MINUTES.toMillis(10)
    }

    private var lastUpdate = 0L

    suspend fun getGitHubReposByUser(name: String): Either<DomainError, List<GitHubRepo>> =
        daoUser.getUserByName(name)?.let { user ->
            val repos = daoRepository.getRepositoriesByUser(name)
            Either.right(repos.map { it.toGitHubRepo(user) })
        } ?: Either.left(DomainError.NotIndexStringFoundDomainError(name))

    suspend fun save(name: String, repos: List<GitHubRepo>) {
        lastUpdate = timeProvider.time()
        daoUser.insert(repos[0].toUserEntity(name))
        daoRepository.insertAll(repos.map { it.toReposEntity() })
    }

    suspend fun isValid(name: String, forceRefresh: Boolean = false): Boolean =
        if (!forceRefresh) {
            isUpdated() && contains(name)
        } else {
            invalidateCache()
            false
        }

    private fun isUpdated(): Boolean =
        timeProvider.time() - lastUpdate < CACHE_TIME

    private suspend fun contains(username: String): Boolean =
        daoUser.getUserByName(username) != null

    private suspend fun invalidateCache() {
        lastUpdate = 0
        daoUser.deleteAll()
        daoRepository.deleteAll()
    }

    private fun GitHubRepo.toUserEntity(username: String): GitHubUserEntity = GitHubUserEntity(username, owner.avatarUrl)
    private fun GitHubRepo.toReposEntity(): GitHubRepositoryEntity = GitHubRepositoryEntity(name, language, owner.username)
    private fun GitHubRepositoryEntity.toGitHubRepo(user: GitHubUserEntity): GitHubRepo =
        GitHubRepo(OwnerRepo(user.username, user.avatarUrl), name, language)
}