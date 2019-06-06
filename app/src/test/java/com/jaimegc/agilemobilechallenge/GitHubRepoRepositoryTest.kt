package com.jaimegc.agilemobilechallenge

import arrow.core.Either
import com.jaimegc.agilemobilechallenge.data.datasource.GitHubRepoDataSource
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
        val LIST_REPOS = listOf(repo1, repo2, repo3)
    }

    private val dataSourceMemory: GitHubRepoDataSource = mock()
    private val dataSourceNetwork: GitHubRepoDataSource = mock()
    private lateinit var gitHubRepoRepository: GitHubRepoRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        gitHubRepoRepository =
            GitHubRepoRepository(listOf(dataSourceMemory, dataSourceNetwork))
    }

    @Test
    fun `should return get all data from the first datasource if info is updated and contains the key`() = runBlockingTest {
        givenDataSourceWithData(dataSourceMemory)

        gitHubRepoRepository.getGitHubReposByUser(USERNAME)

        verify(dataSourceMemory, times(1)).getGitHubReposByUser(USERNAME)
        verify(dataSourceNetwork, never()).getGitHubReposByUser(USERNAME)
    }

    @Test
    fun `should return get all data from the second datasource if info is not updated`() = runBlockingTest {
        givenDataSourceWithOldData(dataSourceMemory)
        givenDataSourceWithData(dataSourceNetwork)

        gitHubRepoRepository.getGitHubReposByUser(USERNAME)

        verify(dataSourceMemory, never()).getGitHubReposByUser(USERNAME)
        verify(dataSourceNetwork, times(1)).getGitHubReposByUser(USERNAME)
    }

    @Test
    fun `should call populate with new data for each datasource after obtain all data`() = runBlockingTest {
        givenDataSourceWithOldData(dataSourceMemory)
        givenDataSourceWithData(dataSourceNetwork)

        gitHubRepoRepository.getGitHubReposByUser(USERNAME)

        verify(dataSourceMemory, times(1)).populate(USERNAME, LIST_REPOS)
        verify(dataSourceNetwork, times(1)).populate(USERNAME, LIST_REPOS)
    }

    @Test
    fun `should get all data from the second datasource if first does not contain the key`() = runBlockingTest {
        givenDataSourceWithoutTheKey(dataSourceMemory)
        givenDataSourceWithData(dataSourceNetwork)

        gitHubRepoRepository.getGitHubReposByUser(USERNAME)

        verify(dataSourceMemory, never()).getGitHubReposByUser(USERNAME)
        verify(dataSourceNetwork, times(1)).getGitHubReposByUser(USERNAME)
    }

    private fun givenDataSourceWithData(dataSource: GitHubRepoDataSource) = runBlockingTest {
        whenever(dataSource.isUpdated()).thenReturn(true)
        whenever(dataSource.contains(USERNAME)).thenReturn(true)
        whenever(dataSource.getGitHubReposByUser(USERNAME)).thenReturn(Either.right(LIST_REPOS))
    }

    private fun givenDataSourceWithOldData(dataSource: GitHubRepoDataSource) {
        whenever(dataSource.isUpdated()).thenReturn(false)
    }

    private fun givenDataSourceWithoutTheKey(dataSource: GitHubRepoDataSource) {
        whenever(dataSource.isUpdated()).thenReturn(true)
        whenever(dataSource.contains(USERNAME)).thenReturn(false)
    }
}