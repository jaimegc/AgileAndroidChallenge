package com.jaimegc.agilemobilechallenge.data.api.client

import com.jaimegc.agilemobilechallenge.data.api.config.ServerApiClient
import com.jaimegc.agilemobilechallenge.data.api.config.ServerApiConfig
import com.jaimegc.agilemobilechallenge.data.api.model.GitHubRepoDto
import com.jaimegc.agilemobilechallenge.data.api.rest.GitHubRepoRest


class GitHubRepoApiClient(
    serverApiConfig: ServerApiConfig
) : ServerApiClient(serverApiConfig) {

    suspend fun getGitHubReposByUser(username: String): List<GitHubRepoDto> =
        getApi(GitHubRepoRest::class.java).getGitHubReposByUser(username)
}