package com.jaimegc.agilemobilechallenge.data.api.rest

import com.jaimegc.agilemobilechallenge.data.api.model.GitHubRepoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubRepoRest {

    @GET("/users/{repository}/repos")
    suspend fun getGitHubReposByUser(@Path("repository") username: String): Response<List<GitHubRepoDto>>
}