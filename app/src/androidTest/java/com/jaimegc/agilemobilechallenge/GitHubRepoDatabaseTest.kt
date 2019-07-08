package com.jaimegc.agilemobilechallenge

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jaimegc.agilemobilechallenge.data.datasource.room.GitHubRepoDatabase
import com.jaimegc.agilemobilechallenge.data.datasource.room.GitHubRepositoryEntity
import com.jaimegc.agilemobilechallenge.data.datasource.room.GitHubUserEntity
import com.jaimegc.agilemobilechallenge.domain.model.GitHubRepo
import com.jaimegc.agilemobilechallenge.domain.model.OwnerRepo
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals

@RunWith(AndroidJUnit4::class)
class GitHubRepoDatabaseTest {

    companion object {
        private const val USER_NAME = "user"
        private const val REPO1_NAME = "repo1"
        private const val REPO2_NAME = "repo2"
        private const val REPO3_NAME = "repo3"
        private const val LANGUAGE = "Kotlin"
        private const val AVATAR_URL = "Kotlin"

        private val githubRepo1 = GitHubRepo(OwnerRepo(USER_NAME, AVATAR_URL), REPO1_NAME, LANGUAGE)
        private val githubRepo2 = GitHubRepo(OwnerRepo(USER_NAME, AVATAR_URL), REPO2_NAME, LANGUAGE)
        private val githubRepo3 = GitHubRepo(OwnerRepo(USER_NAME, AVATAR_URL), REPO3_NAME, LANGUAGE)

        private val LIST_GITHUB_REPOS = listOf(githubRepo1, githubRepo2, githubRepo3)
    }

    private lateinit var database: GitHubRepoDatabase

    @Before
    fun setUp() {
        val app = ApplicationProvider.getApplicationContext<AgileMobileChallengeApplication>()
        database = Room.inMemoryDatabaseBuilder(app, GitHubRepoDatabase::class.java).allowMainThreadQueries().build()
        runBlocking {
            deleteAllUsers()
            deleteAllRepos()
        }
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun shouldInsertAnUser() {
        runBlocking {
            insertAnUser()
            val user = getUserByName()
            assertEquals(USER_NAME, user?.username)
            assertEquals(AVATAR_URL, user?.avatarUrl)
        }
    }

    @Test
    fun shouldInsertAnUserAndGetRepos() {
        runBlocking {
            insertAnUser()
            insertGitHubRepos()
            assertEquals(3, getRepositoriesByUser().size)
        }
    }

    @Test
    fun shouldDeleteAllUsersAndRepos() {
        runBlocking {
            insertAnUser()
            insertGitHubRepos()
            val user = getUserByName()
            assertEquals(USER_NAME, user?.username)
            assertEquals(AVATAR_URL, user?.avatarUrl)
            assertEquals(3, getRepositoriesByUser().size)
            deleteAllUsers()
            deleteAllRepos()
            assertEquals(null, getUserByName())
            assertEquals(0, getRepositoriesByUser().size)
        }
    }

    private suspend fun insertAnUser() =
        database.gitHubUsersDao().insert(GitHubUserEntity(LIST_GITHUB_REPOS[0].owner.username, LIST_GITHUB_REPOS[0].owner.avatarUrl))

    private suspend fun insertGitHubRepos() =
        database.gitHubReposDao().insertAll(LIST_GITHUB_REPOS.map { GitHubRepositoryEntity(it.name, it.language, it.owner.username) })

    private suspend fun getUserByName(): GitHubUserEntity? =
        database.gitHubUsersDao().getUserByName(USER_NAME)

    private suspend fun getRepositoriesByUser(): List<GitHubRepo> {
        var repositories = listOf<GitHubRepo>()
        val user = database.gitHubUsersDao().getUserByName(USER_NAME)
        user?.let {
            repositories = database.gitHubReposDao().getRepositoriesByUser(USER_NAME).map {
                GitHubRepo(OwnerRepo(user.username, user.avatarUrl), it.name, it.language)
            }
        }

        return repositories
    }

    private suspend fun deleteAllUsers() =
        database.gitHubUsersDao().deleteAll()

    private suspend fun deleteAllRepos() =
        database.gitHubReposDao().deleteAll()
}