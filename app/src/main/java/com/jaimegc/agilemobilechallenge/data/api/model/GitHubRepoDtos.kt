package com.jaimegc.agilemobilechallenge.data.api.model

import com.google.gson.annotations.SerializedName

data class GitHubRepoDto(
    @SerializedName("owner") val owner: OwnerRepoDto,
    @SerializedName("name") val name: String,
    @SerializedName("language") val language: String
)

data class OwnerRepoDto(
    @SerializedName("avatar_url") val avatarUrl: String
)