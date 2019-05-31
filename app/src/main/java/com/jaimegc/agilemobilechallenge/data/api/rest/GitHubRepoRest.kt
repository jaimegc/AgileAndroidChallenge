package com.jaimegc.agilemobilechallenge.data.api.rest

import com.jaimegc.agilemobilechallenge.data.api.model.GitHubRepoDto
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubRepoRest {

    @GET("/users/{name}/repos")
    fun getGitHubReposByUser(@Path("name") username: String): Deferred<Response<List<GitHubRepoDto>>>
}