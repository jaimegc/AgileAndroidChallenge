package com.jaimegc.agilemobilechallenge.domain.model

data class GitHubRepo(
    val owner: OwnerRepo,
    val name: String,
    val language: String
)

data class OwnerRepo(
    val avatarUrl: String
)