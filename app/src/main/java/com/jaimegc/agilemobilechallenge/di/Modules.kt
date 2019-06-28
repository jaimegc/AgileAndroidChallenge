package com.jaimegc.agilemobilechallenge.di

import android.content.Context
import com.jaimegc.agilemobilechallenge.R
import com.jaimegc.agilemobilechallenge.common.RealTimeProvider
import com.jaimegc.agilemobilechallenge.common.TimeProvider
import com.jaimegc.agilemobilechallenge.data.api.client.GitHubRepoApiClient
import com.jaimegc.agilemobilechallenge.data.api.config.ServerApiConfig
import com.jaimegc.agilemobilechallenge.data.api.config.ServerApiGitHubConfigBuilder
import com.jaimegc.agilemobilechallenge.data.datasource.*
import com.jaimegc.agilemobilechallenge.data.repository.GitHubRepoRepository
import com.jaimegc.agilemobilechallenge.domain.usecase.GetGitHubReposByUser
import org.kodein.di.Kodein
import org.kodein.di.generic.*

class KodeinModules(context: Context) {

    val repositories = Kodein.Module("repositories") {
        bind<GitHubRepoRepository>() with singleton {
            GitHubRepoRepository(instance(), instance())
        }
    }

    val dataSources = Kodein.Module("dataSources") {
        bind<LocalGitHubRepoDataSource>() with singleton {
            LocalGitHubRepoDataSource(instance())
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


