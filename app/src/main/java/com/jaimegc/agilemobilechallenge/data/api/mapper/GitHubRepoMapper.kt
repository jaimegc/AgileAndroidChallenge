package com.jaimegc.agilemobilechallenge.data.api.mapper

import com.jaimegc.agilemobilechallenge.data.api.model.GitHubRepoDto
import com.jaimegc.agilemobilechallenge.data.api.model.OwnerRepoDto
import com.jaimegc.agilemobilechallenge.domain.model.GitHubRepo
import com.jaimegc.agilemobilechallenge.domain.model.OwnerRepo

class GitHubReposDtoToGitHubReposMapper {
    operator fun invoke(gitHubReposDto: List<GitHubRepoDto>): List<GitHubRepo> =
        gitHubReposDto.map { mapper(it) }

    private fun mapper(gitHubRepoDto: GitHubRepoDto): GitHubRepo =
        GitHubRepo(
            owner = mapper(gitHubRepoDto.owner),
            name = gitHubRepoDto.name,
            language = gitHubRepoDto.language ?: "???"
        )

    private fun mapper(ownerDto: OwnerRepoDto): OwnerRepo =
        OwnerRepo(
            avatarUrl = ownerDto.avatarUrl,
            username = ownerDto.login
        )
}