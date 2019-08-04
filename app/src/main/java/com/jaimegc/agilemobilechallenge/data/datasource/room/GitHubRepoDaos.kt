package com.jaimegc.agilemobilechallenge.data.datasource.room

import androidx.room.*


@Dao
interface GitHubRepositoryDao {

    @Query("SELECT * FROM github_repository WHERE username_id=:username")
    suspend fun getRepositoriesByUser(username: String): List<GitHubRepositoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repo: GitHubRepositoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<GitHubRepositoryEntity>)

    @Query("DELETE FROM github_repository")
    suspend fun deleteAll()
}

@Dao
interface GitHubUserDao {

    @Query("SELECT * FROM github_user WHERE username=:name")
    suspend fun getUserByName(name: String): GitHubUserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: GitHubUserEntity)

    @Delete
    suspend fun delete(user: GitHubUserEntity)

    @Query("DELETE FROM github_user")
    suspend fun deleteAll()
}
