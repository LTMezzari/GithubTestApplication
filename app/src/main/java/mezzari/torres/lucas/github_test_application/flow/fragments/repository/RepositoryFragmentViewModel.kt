package mezzari.torres.lucas.github_test_application.flow.fragments.repository

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import mezzari.torres.lucas.github_test_application.archive.decodeBase64
import mezzari.torres.lucas.github_test_application.archive.format
import mezzari.torres.lucas.github_test_application.archive.toDate
import mezzari.torres.lucas.github_test_application.dagger.DaggerHelper
import mezzari.torres.lucas.github_test_application.model.Content
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
class RepositoryFragmentViewModel: ViewModel() {

    @Inject lateinit var service: IGithubService
    @Inject lateinit var dispatcher: IAppDispatcher

    init {
        DaggerHelper.mainComponent.inject(this)
    }

    var user: User? = null

    private val _repository: MutableLiveData<Repository> = MutableLiveData()
    var repository: Repository? set(value) {
        _repository.value = value
    } get() = _repository.value

    val fullName: LiveData<String> = Transformations.map(_repository) {
        it.fullName
    }

    val description: LiveData<String> = Transformations.map(_repository) {
        it.description
    }

    val createdDate: LiveData<String> = Transformations.map(_repository) {
        it.createdAt?.toDate()?.format("dd/MM/yyyy")
    }

    val updatedDate: LiveData<String> = Transformations.map(_repository) {
        it.updatedAt?.toDate()?.format("dd/MM/yyyy")
    }

    val stars: LiveData<String> = Transformations.map(_repository) {
        it.stars.toString()
    }

    val language: LiveData<String> = Transformations.map(_repository) {
        it.language
    }

    private val content: MutableLiveData<Content> = MutableLiveData()
    val overview: LiveData<String> = Transformations.map(content) {
        it.base64Content?.decodeBase64()
    }

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val error: MutableLiveData<String> = MutableLiveData()

    fun getOverview() {
        val userId = user?.username ?: return
        val repositoryId = repository?.name ?: return
        viewModelScope.launch(dispatcher.io) {
            try {
                _isLoading.postValue(true)
                content.postValue(service.getRepositoryReadme(userId, repositoryId))
            } catch (e: Exception) {
                error.postValue(e.message)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
}