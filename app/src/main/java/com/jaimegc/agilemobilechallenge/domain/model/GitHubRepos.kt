package com.jaimegc.agilemobilechallenge.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitHubRepo(
    val owner: OwnerRepo,
    val name: String,
    val language: String
) : Parcelable

@Parcelize
data class OwnerRepo(
    val avatarUrl: String?,
    val username: String
) : Parcelable