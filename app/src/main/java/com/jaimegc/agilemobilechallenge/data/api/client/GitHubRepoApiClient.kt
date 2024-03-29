package com.jaimegc.agilemobilechallenge.data.api.client

import com.jaimegc.agilemobilechallenge.data.api.config.ServerApiClient
import com.jaimegc.agilemobilechallenge.data.api.config.ServerApiConfig
import com.jaimegc.agilemobilechallenge.data.api.model.GitHubRepoDto
import com.jaimegc.agilemobilechallenge.data.api.rest.GitHubRepoRest


class GitHubRepoApiClient(
    serverApiConfig: ServerApiConfig
) : ServerApiClient(serverApiConfig) {

    suspend fun getGitHubReposByUser(username: String): List<GitHubRepoDto>? =
        handleResponse(suspendApiCall(call = { getApi(GitHubRepoRest::class.java).getGitHubReposByUser(username).await() }))
}