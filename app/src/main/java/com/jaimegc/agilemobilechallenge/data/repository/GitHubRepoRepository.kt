package com.jaimegc.agilemobilechallenge.data.repository

import arrow.core.Either
import com.jaimegc.agilemobilechallenge.data.datasource.*
import com.jaimegc.agilemobilechallenge.domain.model.DomainError
import com.jaimegc.agilemobilechallenge.domain.model.GitHubRepo

class GitHubRepoRepository(
    private val local: LocalGitHubRepoDataSource,
    private val remote: RemoteGitHubRepoDataSource
) {
    suspend fun getGitHubReposByUser(name: String, forceRefresh: Boolean = false): Either<DomainError, List<GitHubRepo>> =
        if (local.isValid(name, forceRefresh)) {
            local.getGitHubReposByUser(name)
        } else {
            remote.getGitHubReposByUser(name).also { it.map { list -> local.save(name, list) } }
        }
}