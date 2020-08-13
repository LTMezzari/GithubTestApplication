package mezzari.torres.lucas.github_test_application.dagger.modules

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import mezzari.torres.lucas.conductor.source.generic.provider.ConductorProvider
import mezzari.torres.lucas.github_test_application.conductor.MainConductor
import mezzari.torres.lucas.github_test_application.model.dispatcher.AppDispatcher
import mezzari.torres.lucas.github_test_application.model.dispatcher.IAppDispatcher
import mezzari.torres.lucas.github_test_application.persistence.preferences.IPreferencesManager
import mezzari.torres.lucas.github_test_application.persistence.preferences.PreferencesManager
import mezzari.torres.lucas.github_test_application.persistence.sessions.ISessionManager
import mezzari.torres.lucas.github_test_application.persistence.sessions.SessionManager
import mezzari.torres.lucas.network.source.Network

/**
 * @author Lucas T. Mezzari
 * @since 12/08/2020
 */
@Module
class MainModule {
    @Provides
    fun provideNetwork(): Network {
        return Network
    }

    @Provides
    fun providePreferencesManager(): IPreferencesManager {
        return PreferencesManager
    }

    @Provides
    fun provideSessionManager(preferencesManger: IPreferencesManager): ISessionManager {
        return SessionManager(preferencesManger)
    }

    @Provides
    fun provideMainConductor(): MainConductor {
        return ConductorProvider[MainConductor::class]
    }

    @Provides
    fun provideAppDispatcher(): IAppDispatcher {
        return AppDispatcher(
            main = Dispatchers.Main,
            io = Dispatchers.IO
        )
    }
}