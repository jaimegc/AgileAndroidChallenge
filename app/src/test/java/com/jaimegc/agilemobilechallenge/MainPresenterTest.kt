package com.jaimegc.agilemobilechallenge

import androidx.lifecycle.MutableLiveData
import arrow.core.Either
import com.jaimegc.agilemobilechallenge.domain.model.DomainError
import com.jaimegc.agilemobilechallenge.domain.model.GitHubRepo
import com.jaimegc.agilemobilechallenge.domain.model.OwnerRepo
import com.jaimegc.agilemobilechallenge.domain.usecase.GetGitHubReposByUser
import com.jaimegc.agilemobilechallenge.ui.presenter.MainPresenter
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MainPresenterTest {

    companion object {
        const val USERNAME = "username"
        private val user = OwnerRepo(USERNAME, "https://www.image.com")
        private val repo1 = GitHubRepo(user, "Best project", "Kotlin")
        private val repo2 = GitHubRepo(user, "The incredible", "Java")
        private val repo3 = GitHubRepo(user, "Nice", "Javascript")
        private val LIST_REPOS = listOf(repo1, repo2, repo3)
    }

    private val view: MainPresenter.View = mock()
    private val getGitHubReposByUser: GetGitHubReposByUser = mock()
    private lateinit var presenter: MainPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)

        presenter =
            MainPresenter(
                view,
                getGitHubReposByUser
            )
    }

    @After
    fun tearDown() {
        presenter.destroy()
        Dispatchers.resetMain()
    }

    @Test
    fun `should return all data from use case if the list contains values`() = runBlockingTest {
        givenUseCaseWithData()

        presenter.getGitHubReposByUser(USERNAME)

        verify(view).showLoading()
        verify(getGitHubReposByUser).invoke(USERNAME)
        verify(view).hideLoading()
        verify(view).goDetail(LIST_REPOS)
    }

    @Test
    fun `should return data not found from use case if the list does not contain values`() = runBlockingTest {
        givenUseCaseWithEmptyData()

        presenter.getGitHubReposByUser(USERNAME)

        verify(view).showLoading()
        verify(getGitHubReposByUser).invoke(USERNAME)
        verify(view).hideLoading()
        verify(view).showReposNotFound()
    }

    @Test
    fun `should return user not found from use case if the user does not exist`() = runBlockingTest {
        givenUseCaseWithUserNotFoundError()

        presenter.getGitHubReposByUser(USERNAME)

        verify(view).showLoading()
        verify(getGitHubReposByUser).invoke(USERNAME)
        verify(view).hideLoading()
        verify(view).showUserNotFound()
    }

    @Test
    fun `should return user unknown from use case if the user exists but there is some problem`() = runBlockingTest {
        givenUseCaseWithUserUnknownError()

        presenter.getGitHubReposByUser(USERNAME)

        verify(view).showLoading()
        verify(getGitHubReposByUser).invoke(USERNAME)
        verify(view).hideLoading()
        verify(view).showUserUnknown()
    }

    private fun givenUseCaseWithData() = runBlockingTest {
        whenever(getGitHubReposByUser.invoke(USERNAME))
            .thenReturn(MutableLiveData<Either<DomainError, List<GitHubRepo>>>(Either.right(LIST_REPOS)))
    }

    private fun givenUseCaseWithEmptyData() = runBlockingTest {
        whenever(getGitHubReposByUser.invoke(USERNAME))
            .thenReturn(MutableLiveData<Either<DomainError, List<GitHubRepo>>>(Either.right(emptyList())))
    }

    private fun givenUseCaseWithUserNotFoundError() = runBlockingTest {
        whenever(getGitHubReposByUser.invoke(USERNAME))
            .thenReturn(MutableLiveData<Either<DomainError, List<GitHubRepo>>>(Either.left(DomainError.UserNotFoundDomainError)))
    }

    private fun givenUseCaseWithUserUnknownError() = runBlockingTest {
        whenever(getGitHubReposByUser.invoke(USERNAME))
            .thenReturn(MutableLiveData<Either<DomainError, List<GitHubRepo>>>(Either.left(DomainError.UserUnknownDomainError)))
    }
}