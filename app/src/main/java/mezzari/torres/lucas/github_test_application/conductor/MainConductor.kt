package mezzari.torres.lucas.github_test_application.conductor

import android.content.Intent
import android.net.Uri
import androidx.navigation.fragment.findNavController
import mezzari.torres.lucas.conductor.annotation.ConductorAnnotation
import mezzari.torres.lucas.conductor.source.generic.annotated.AnnotatedConductor
import mezzari.torres.lucas.conductor.source.generic.annotated.AnnotatedFlowCycle
import mezzari.torres.lucas.conductor.source.path.Path
import mezzari.torres.lucas.github_test_application.R
import mezzari.torres.lucas.github_test_application.dagger.DaggerHelper
import mezzari.torres.lucas.github_test_application.flow.fragments.repositories.RepositoriesFragment
import mezzari.torres.lucas.github_test_application.flow.fragments.repository.RepositoryFragment
import mezzari.torres.lucas.github_test_application.flow.fragments.search.SearchFragment
import mezzari.torres.lucas.github_test_application.flow.fragments.user.UserFragment
import mezzari.torres.lucas.github_test_application.model.Repository
import mezzari.torres.lucas.github_test_application.model.User
import mezzari.torres.lucas.github_test_application.persistence.sessions.ISessionManager
import javax.inject.Inject

/**
 * @author Lucas T. Mezzari
 * @since 12/08/2020
 */
class MainConductor: AnnotatedConductor(), IMainConductor {

    @Inject lateinit var sessionManager: ISessionManager

    private var user: User? = null
    private var repository: Repository? = null

    init {
        DaggerHelper.mainComponent.inject(this)
    }

    override fun start() {
        super.start()
        user = sessionManager.user
        repository = null
    }

    // ------------------------------------ SearchFragment

    @ConductorAnnotation(SearchFragment::class, AnnotatedFlowCycle.STEP_INITIATED)
    fun onSearchFragmentInitiated(searchFragment: SearchFragment) {
        searchFragment.user = null
        if (user != null) {
            val navController = searchFragment.findNavController()
            if (navController.currentDestination?.id != R.id.userFragment) {
                navController.navigate(R.id.action_searchFragment_to_userFragment)
            }
        }
    }

    @ConductorAnnotation(SearchFragment::class, AnnotatedFlowCycle.NEXT)
    fun onSearchFragmentNext(searchFragment: SearchFragment) {
        user = searchFragment.user ?: return
        val navController = searchFragment.findNavController()
        if (navController.currentDestination?.id != R.id.userFragment) {
            navController.navigate(R.id.action_searchFragment_to_userFragment)
        }
    }

    // ------------------------------------ UserFragment

    @ConductorAnnotation(UserFragment::class, AnnotatedFlowCycle.STEP_INITIATED)
    fun onUserFragmentInitiated(userFragment: UserFragment) {
        userFragment.user = user
    }

    @ConductorAnnotation(UserFragment::class, AnnotatedFlowCycle.NEXT)
    fun onUserFragmentNext(userFragment: UserFragment, path: Path) {
        when (path) {
            UserFragment.UserFragmentPaths.REPOSITORIES -> {
                val navController = userFragment.findNavController()
                if (navController.currentDestination?.id != R.id.repositoriesFragment) {
                    navController.navigate(R.id.action_userFragment_to_repositoriesFragment)
                }
            }

            UserFragment.UserFragmentPaths.SEARCH -> {
                val navController = userFragment.findNavController()
                if (navController.currentDestination?.id != R.id.searchFragment) {
                    user = null
                    repository = null
                    sessionManager.user = null
                    navController.navigateUp()
                }

            }

            UserFragment.UserFragmentPaths.PROFILE -> {
                //Open github profile on web
                val userUrl = user?.originUrl ?: return
                userFragment.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(userUrl)))
            }
        }
    }

    // ------------------------------------ RepositoriesFragment

    @ConductorAnnotation(RepositoriesFragment::class, AnnotatedFlowCycle.STEP_INITIATED)
    fun onRepositoriesFragmentInitiated(repositoriesFragment: RepositoriesFragment) {
        repositoriesFragment.user = user
    }

    @ConductorAnnotation(RepositoriesFragment::class, AnnotatedFlowCycle.NEXT)
    fun onRepositoriesFragmentNext(repositoriesFragment: RepositoriesFragment) {
        repository = repositoriesFragment.repository ?: return
        val navController = repositoriesFragment.findNavController()
        if (navController.currentDestination?.id != R.id.repositoryFragment) {
            navController.navigate(R.id.action_repositoriesFragment_to_repositoryFragment)
        }
    }

    // ------------------------------------ RepositoryFragment

    @ConductorAnnotation(RepositoryFragment::class, AnnotatedFlowCycle.STEP_INITIATED)
    fun onRepositoryFragmentInitiated(repositoryFragment: RepositoryFragment) {
        repositoryFragment.user = user
        repositoryFragment.repository = repository
    }

    @ConductorAnnotation(RepositoryFragment::class, AnnotatedFlowCycle.NEXT)
    fun onRepositoryFragmentNext(repositoryFragment: RepositoryFragment) {
        //Open github repository on web
        val repositoryUrl = repository?.originUrl ?: return
        repositoryFragment.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(repositoryUrl)))
    }

    override fun end() {
        super.end()
        user = null
        repository = null
    }
}