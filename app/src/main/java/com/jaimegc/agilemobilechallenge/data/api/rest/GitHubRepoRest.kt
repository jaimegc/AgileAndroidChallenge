package com.jaimegc.agilemobilechallenge.data.api.rest

import com.jaimegc.agilemobilechallenge.data.api.model.GitHubRepoDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubRepoRest {

    @GET("/users/{username}/repos")
    fun getReposByUser(@Path("username") username: String): Call<List<GitHubRepoDto>>
}