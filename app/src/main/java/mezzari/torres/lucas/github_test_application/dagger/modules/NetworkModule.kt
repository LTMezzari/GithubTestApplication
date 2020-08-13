package mezzari.torres.lucas.github_test_application.dagger.modules

import dagger.Module
import dagger.Provides
import mezzari.torres.lucas.github_test_application.network.IGithubAPI
import mezzari.torres.lucas.github_test_application.network.service.GithubService
import mezzari.torres.lucas.github_test_application.network.service.IGithubService
import mezzari.torres.lucas.network.source.Network

/**
 * @author Lucas T. Mezzari
 * @since 12/08/2020
 */
@Module
class NetworkModule {
    @Provides
    fun provideGithubAPI(): IGithubAPI {
        return Network.build(IGithubAPI::class)
    }

    @Provides
    fun provideGithubService(api: IGithubAPI): IGithubService {
        return GithubService(api)
    }
}