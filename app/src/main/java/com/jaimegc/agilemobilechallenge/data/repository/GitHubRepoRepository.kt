package com.jaimegc.agilemobilechallenge.data.repository

import androidx.lifecycle.LiveData
import arrow.core.Either
import com.jaimegc.agilemobilechallenge.data.datasource.*
import com.jaimegc.agilemobilechallenge.domain.model.DomainError
import com.jaimegc.agilemobilechallenge.domain.model.GitHubRepo

class GitHubRepoRepository(
    private val local: LocalGitHubRepoDataSource,
    private val remote: RemoteGitHubRepoDataSource
) {

    suspend fun getGitHubReposByUser(name: String, forceRefresh: Boolean = false): LiveData<Either<DomainError, List<GitHubRepo>>> =
        if (local.isValid(name, forceRefresh)) {
            local.getGitHubReposByUser(name)
        } else {
            remote.getGitHubReposByUser(name).also {
                it.value?.let { either -> either.map { list -> if (list.isNotEmpty()) local.save(name, list) } } }
        }
}