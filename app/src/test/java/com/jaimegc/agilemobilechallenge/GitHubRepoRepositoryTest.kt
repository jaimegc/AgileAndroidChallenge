package com.jaimegc.agilemobilechallenge

import arrow.core.Either
import com.jaimegc.agilemobilechallenge.data.datasource.LocalGitHubRepoDataSource
import com.jaimegc.agilemobilechallenge.data.datasource.RemoteGitHubRepoDataSource
import com.jaimegc.agilemobilechallenge.data.repository.GitHubRepoRepository
import com.jaimegc.agilemobilechallenge.domain.model.GitHubRepo
import com.jaimegc.agilemobilechallenge.domain.model.OwnerRepo
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class GitHubRepoRepositoryTest {

    companion object {
        const val USERNAME = "username"

        private val user = OwnerRepo("https://www.image.com", MainPresenterTest.USERNAME)
        private val repo1 = GitHubRepo(user, "Best project", "Kotlin")
        private val repo2 = GitHubRepo(user, "The incredible", "Java")
        private val repo3 = GitHubRepo(user, "Nice", "Javascript")
        private val LIST_REPOS = listOf(repo1, repo2, repo3)
    }

    private val dataSourceLocal: LocalGitHubRepoDataSource = mock()
    private val dataSourceRemote: RemoteGitHubRepoDataSource = mock()
    private lateinit var gitHubRepoRepository: GitHubRepoRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        gitHubRepoRepository =
            GitHubRepoRepository(dataSourceLocal, dataSourceRemote)
    }

    @Test
    fun `should return get all data from the local datasource if info is updated and contains the key`() = runBlockingTest {
        givenDataSourceLocalWithData(dataSourceLocal)

        gitHubRepoRepository.getGitHubReposByUser(USERNAME)

        verify(dataSourceLocal, times(1)).getGitHubReposByUser(USERNAME)
        verify(dataSourceRemote, never()).getGitHubReposByUser(USERNAME)
    }

    @Test
    fun `should return get all data from the remote datasource if info is not updated`() = runBlockingTest {
        givenDataSourceLocalWithOldData(dataSourceLocal)
        givenDataSourceRemoteWithData(dataSourceRemote)

        gitHubRepoRepository.getGitHubReposByUser(USERNAME)

        verify(dataSourceLocal, never()).getGitHubReposByUser(USERNAME)
        verify(dataSourceRemote, times(1)).getGitHubReposByUser(USERNAME)
    }

    @Test
    fun `should call populate with new data for each datasource after obtain all data`() = runBlockingTest {
        givenDataSourceLocalWithOldData(dataSourceLocal)
        givenDataSourceRemoteWithData(dataSourceRemote)

        gitHubRepoRepository.getGitHubReposByUser(USERNAME)

        verify(dataSourceLocal, times(1)).save(USERNAME, LIST_REPOS)
    }

    private fun givenDataSourceLocalWithData(dataSourceLocal: LocalGitHubRepoDataSource) = runBlockingTest {
        whenever(dataSourceLocal.isValid(USERNAME)).thenReturn(true)
        whenever(dataSourceLocal.getGitHubReposByUser(USERNAME)).thenReturn(Either.right(LIST_REPOS))
    }

    private fun givenDataSourceRemoteWithData(dataSourceRemote: RemoteGitHubRepoDataSource) = runBlockingTest {
        whenever(dataSourceRemote.getGitHubReposByUser(USERNAME)).thenReturn(Either.right(LIST_REPOS))
    }

    private fun givenDataSourceLocalWithOldData(dataSourceLocal: LocalGitHubRepoDataSource) {
        whenever(dataSourceLocal.isValid(USERNAME)).thenReturn(false)
    }
}