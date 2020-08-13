package mezzari.torres.lucas.github_test_application.flow.fragments.search

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import mezzari.torres.lucas.github_test_application.dagger.DaggerHelper
import mezzari.torres.lucas.github_test_application.model.User
import mezzari.torres.lucas.github_test_application.model.dispatcher.IAppDispatcher
import mezzari.torres.lucas.github_test_application.network.service.IGithubService
import mezzari.torres.lucas.github_test_application.persistence.preferences.IPreferencesManager
import mezzari.torres.lucas.github_test_application.persistence.preferences.PreferencesManager
import mezzari.torres.lucas.github_test_application.persistence.sessions.ISessionManager
import java.lang.Exception
import javax.inject.Inject

/**
 * @author Lucas T. Mezzari
 * @since 12/08/2020
 */
class SearchFragmentViewModel: ViewModel() {

    @Inject lateinit var service: IGithubService
    @Inject lateinit var dispatcher: IAppDispatcher

    @Inject lateinit var preferencesManager: IPreferencesManager
    @Inject lateinit var sessionManager: ISessionManager

    init {
        DaggerHelper.mainComponent.inject(this)
    }

    val search: MutableLiveData<String> = MutableLiveData()

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val error: MutableLiveData<String> = MutableLiveData()
    val hasError: LiveData<Boolean> = Transformations.map(error) {
        it != null && !it.isNullOrEmpty() && !it.isNullOrBlank()
    }

    val user: MutableLiveData<User> = MutableLiveData()

    private val _isValid: MediatorLiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(search) {
            value = isLoading.value != true &&
                    it != null &&
                    !it.isNullOrBlank() &&
                    !it.isNullOrEmpty()
        }

        addSource(isLoading) {
            value = it != true &&
                    search.value != null &&
                    !search.value.isNullOrBlank() &&
                    !search.value.isNullOrEmpty()
        }
    }
    val isValid: LiveData<Boolean> get() = _isValid

    fun getUser() {
        val userId = search.value ?: return
        viewModelScope.launch(dispatcher.io) {
            try {
                error.postValue(null)
                _isLoading.postValue(true)
                val response = service.getUser(userId)
                PreferencesManager.user = response
                user.postValue(response)
            } catch (e: Exception) {
                error.postValue(e.message)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}