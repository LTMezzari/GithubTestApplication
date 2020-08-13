package mezzari.torres.lucas.github_test_application.flow.fragments.user

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import mezzari.torres.lucas.github_test_application.archive.decodeBase64
import mezzari.torres.lucas.github_test_application.archive.format
import mezzari.torres.lucas.github_test_application.archive.toDate
import mezzari.torres.lucas.github_test_application.dagger.DaggerHelper
import mezzari.torres.lucas.github_test_application.model.Content
import mezzari.torres.lucas.github_test_application.model.User
import mezzari.torres.lucas.github_test_application.model.dispatcher.IAppDispatcher
import mezzari.torres.lucas.github_test_application.network.service.IGithubService
import javax.inject.Inject

/**
 * @author Lucas T. Mezzari
 * @since 12/08/2020
 */
class UserFragmentViewModel: ViewModel() {

    @Inject lateinit var service: IGithubService
    @Inject lateinit var dispatcher: IAppDispatcher

    private val _user: MutableLiveData<User> = MutableLiveData()
    var user: User? set(value) {
        _user.value = value
    } get() = _user.value

    init {
        DaggerHelper.mainComponent.inject(this)
    }

    val name: LiveData<String> = Transformations.map(_user) {
        it.name
    }

    val username: LiveData<String> = Transformations.map(_user) {
        it.username
    }

    val company: LiveData<String> = Transformations.map(_user) {
        it.company
    }

    val location: LiveData<String> = Transformations.map(_user) {
        it.location
    }

    val bio: LiveData<String> = Transformations.map(_user) {
        it.bio
    }

    val enteredAt: LiveData<String> = Transformations.map(_user) {
        it.enteredAt?.toDate()?.format("dd/MM/yyyy")
    }

    val following: LiveData<String> = Transformations.map(_user) {
        it.following.toString()
    }

    val followers: LiveData<String> = Transformations.map(_user) {
        it.followers.toString()
    }

    val profileImage: LiveData<String> = Transformations.map(_user) {
        it.profileImageSrc
    }

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val error: MutableLiveData<String> = MutableLiveData()

    private val content: MutableLiveData<Content> = MutableLiveData()
    val overview: LiveData<String> = Transformations.map(content) {
        it?.base64Content?.decodeBase64()
    }

    fun loadOverview() {
        val userId = user?.username ?: return
        viewModelScope.launch(dispatcher.io) {
            try {
                _isLoading.postValue(true)
                content.postValue(service.getRepositoryReadme(userId, userId))
            } catch (e: Exception) {
                error.postValue(e.message)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}