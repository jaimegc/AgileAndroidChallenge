package com.jaimegc.agilemobilechallenge.domain.usecase

import arrow.core.Either
import com.jaimegc.agilemobilechallenge.data.repository.GitHubRepoRepository
import com.jaimegc.agilemobilechallenge.domain.model.DomainError
import com.jaimegc.agilemobilechallenge.domain.model.GitHubRepo

class GetGitHubReposByUser(
    private val repository: GitHubRepoRepository
) {

    suspend operator fun invoke(name: String): Either<DomainError, List<GitHubRepo>> =
        repository.getGitHubReposByUser(name)

    fun invalidateCache() =
        repository.invalidateCache()
}