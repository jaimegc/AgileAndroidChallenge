package com.jaimegc.agilemobilechallenge.data.datasource.room

import androidx.room.*
import androidx.room.ForeignKey.CASCADE


@Entity(tableName = "github_user")
data class GitHubUserEntity(
    @PrimaryKey
    val username: String,
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String?
)

@Entity(
    tableName = "github_repository",
    foreignKeys = [ForeignKey(
    entity = GitHubUserEntity::class,
    parentColumns = arrayOf("username"),
    childColumns = arrayOf("username_id"),
    onDelete = CASCADE)])
class GitHubRepositoryEntity(
    @PrimaryKey
    val name: String,
    val language: String,
    @ColumnInfo(name = "username_id")
    val usernameId: String
)