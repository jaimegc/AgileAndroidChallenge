package com.jaimegc.agilemobilechallenge.di

import android.content.Context
import com.jaimegc.agilemobilechallenge.R
import com.jaimegc.agilemobilechallenge.common.RealTimeProvider
import com.jaimegc.agilemobilechallenge.common.TimeProvider
import com.jaimegc.agilemobilechallenge.data.api.client.GitHubRepoApiClient
import com.jaimegc.agilemobilechallenge.data.api.config.ServerApiConfig
import com.jaimegc.agilemobilechallenge.data.api.config.ServerApiGitHubConfigBuilder
import com.jaimegc.agilemobilechallenge.data.datasource.*
import com.jaimegc.agilemobilechallenge.data.datasource.room.GitHubRepoDatabase
import com.jaimegc.agilemobilechallenge.data.datasource.room.GitHubRepositoryDao
import com.jaimegc.agilemobilechallenge.data.datasource.room.GitHubUserDao
import com.jaimegc.agilemobilechallenge.data.repository.GitHubRepoRepository
import com.jaimegc.agilemobilechallenge.domain.usecase.GetGitHubReposByUser
import org.kodein.di.Kodein
import org.kodein.di.generic.*

class KodeinModules(context: Context) {

    val database = Kodein.Module("database") {
        bind<GitHubRepoDatabase>() with singleton {
            GitHubRepoDatabase.build(context)
        }
    }

    val roomDaos = Kodein.Module("roomDaos") {
        bind<GitHubRepositoryDao>() with provider {
            val database: GitHubRepoDatabase = instance()
            database.gitHubReposDao()
        }

        bind<GitHubUserDao>() with provider {
            val database: GitHubRepoDatabase = instance()
            database.gitHubUsersDao()
        }
    }

    val repositories = Kodein.Module("repositories") {
        bind<GitHubRepoRepository>() with provider {
            GitHubRepoRepository(instance(), instance())
        }
    }

    val dataSources = Kodein.Module("dataSources") {
        bind<LocalGitHubRepoDataSource>() with provider {
            LocalGitHubRepoDataSource(instance(), instance(), instance())
        }
        bind<RemoteGitHubRepoDataSource>() with provider {
            RemoteGitHubRepoDataSource(instance())
        }
    }

    val apiClients = Kodein.Module("apiClients") {
        bind<GitHubRepoApiClient>() with provider { GitHubRepoApiClient(instance()) }
    }

    val others = Kodein.Module("others") {
        bind<TimeProvider>() with instance(RealTimeProvider())

        bind<ServerApiConfig>() with instance(
            ServerApiGitHubConfigBuilder(context.getString(R.string.base_api_url)).build())
    }

    val mainActivityModule = Kodein.Module("mainActivity") {
        bind<GetGitHubReposByUser>() with provider { GetGitHubReposByUser(instance()) }
    }
}


