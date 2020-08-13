package mezzari.torres.lucas.github_test_application.dagger.components

import dagger.Component
import mezzari.torres.lucas.github_test_application.conductor.MainConductor
import mezzari.torres.lucas.github_test_application.dagger.modules.MainModule
import mezzari.torres.lucas.github_test_application.dagger.modules.NetworkModule
import mezzari.torres.lucas.github_test_application.flow.MainActivity
import mezzari.torres.lucas.github_test_application.flow.fragments.repositories.RepositoriesFragment
import mezzari.torres.lucas.github_test_application.flow.fragments.repositories.RepositoriesFragmentViewModel
import mezzari.torres.lucas.github_test_application.flow.fragments.repository.RepositoryFragment
import mezzari.torres.lucas.github_test_application.flow.fragments.repository.RepositoryFragmentViewModel
import mezzari.torres.lucas.github_test_application.flow.fragments.search.SearchFragment
import mezzari.torres.lucas.github_test_application.flow.fragments.search.SearchFragmentViewModel
import mezzari.torres.lucas.github_test_application.flow.fragments.user.UserFragment
import mezzari.torres.lucas.github_test_application.flow.fragments.user.UserFragmentViewModel
import javax.inject.Singleton

/**
 * @author Lucas T. Mezzari
 * @since 12/08/2020
 */
@Singleton
@Component( modules = [MainModule::class, NetworkModule::class])
interface MainComponent {

    fun inject(conductor: MainConductor)

    //---------------------------- MainActivity

    fun inject(activity: MainActivity)

    //---------------------------- SearchFragment

    fun inject(fragment: SearchFragment)

    fun inject(viewModel: SearchFragmentViewModel)

    //---------------------------- UserFragment

    fun inject(fragment: UserFragment)

    fun inject(viewModel: UserFragmentViewModel)

    //---------------------------- RepositoriesFragment

    fun inject(fragment: RepositoriesFragment)

    fun inject(viewModel: RepositoriesFragmentViewModel)

    //---------------------------- RepositoryFragment

    fun inject(fragment: RepositoryFragment)

    fun inject(viewModel: RepositoryFragmentViewModel)
}