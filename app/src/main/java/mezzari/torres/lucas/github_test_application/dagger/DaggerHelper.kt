package mezzari.torres.lucas.github_test_application.dagger

import mezzari.torres.lucas.github_test_application.dagger.components.DaggerMainComponent
import mezzari.torres.lucas.github_test_application.dagger.components.MainComponent

/**
 * @author Lucas T. Mezzari
 * @since 12/08/2020
 */
object DaggerHelper {

    lateinit var mainComponent: MainComponent
        private set

    fun initialize() {
        mainComponent = DaggerMainComponent.builder().build()
    }
}