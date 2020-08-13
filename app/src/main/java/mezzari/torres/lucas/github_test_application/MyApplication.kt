package mezzari.torres.lucas.github_test_application

import android.app.Application
import mezzari.torres.lucas.github_test_application.dagger.DaggerHelper
import mezzari.torres.lucas.github_test_application.persistence.preferences.PreferencesManager
import mezzari.torres.lucas.network.source.Network
import mezzari.torres.lucas.network.source.module.client.LogModule
import mezzari.torres.lucas.network.source.module.retrofit.GsonConverterModule
import java.util.*

/**
 * @author Lucas T. Mezzari
 * @since 12/08/2020
 */
class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Network.initialize(
            retrofitLevelModules = Collections.singletonList(GsonConverterModule()),
            okHttpClientLevelModule = listOf(LogModule())
        )

        PreferencesManager.initialize(this)

        DaggerHelper.initialize()
    }
}