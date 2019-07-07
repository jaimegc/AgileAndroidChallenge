package com.jaimegc.agilemobilechallenge.data.datasource.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [GitHubRepositoryEntity::class, GitHubUserEntity::class], version = GitHubRepoDatabase.version)
abstract class GitHubRepoDatabase : RoomDatabase() {
    abstract fun gitHubReposDao(): GitHubRepositoryDao
    abstract fun gitHubUsersDao(): GitHubUserDao

    companion object {
        const val version = 1
        private const val DATABASE_NAME = "github-db"
        fun build(context: Context): GitHubRepoDatabase =
            Room.databaseBuilder(context.applicationContext, GitHubRepoDatabase::class.java, DATABASE_NAME).build()
    }
}