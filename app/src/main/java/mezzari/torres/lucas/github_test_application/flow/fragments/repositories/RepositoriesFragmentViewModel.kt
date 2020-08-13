package mezzari.torres.lucas.github_test_application.flow.fragments.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mezzari.torres.lucas.github_test_application.dagger.DaggerHelper
import mezzari.torres.lucas.github_test_application.model.Repository
import mezzari.torres.lucas.github_test_application.model.User
import mezzari.torres.lucas.github_test_application.model.dispatcher.IAppDispatcher
import mezzari.torres.lucas.github_test_application.network.service.IGithubService
import java.lang.Exception
import javax.inject.Inject

/**
 * @author Lucas T. Mezzari
 * @since 13/08/2020
 */
class RepositoriesFragmentViewModel: ViewModel() {

    @Inject lateinit var service: IGithubService
    @Inject lateinit var dispatcher: IAppDispatcher

    init {
        DaggerHelper.mainComponent.inject(this)
    }

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _repositories: MutableLiveData<List<Repository>> = MutableLiveData()
    val repositories: LiveData<List<Repository>> get() = _repositories

    private val error: MutableLiveData<String> = MutableLiveData()

    var user: User? = null

    fun getRepositories() {
        val userId = user?.username ?: return
        viewModelScope.launch(dispatcher.io) {
            try {
                _isLoading.postValue(true)
                _repositories.postValue(service.getUserRepositories(userId))
            } catch (e: Exception) {
                error.postValue(e.message)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}