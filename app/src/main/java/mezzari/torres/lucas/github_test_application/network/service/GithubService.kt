package mezzari.torres.lucas.github_test_application.network.service

import mezzari.torres.lucas.github_test_application.model.Content
import mezzari.torres.lucas.github_test_application.model.Repository
import mezzari.torres.lucas.github_test_application.model.User
import mezzari.torres.lucas.github_test_application.network.IGithubAPI
import retrofit2.Response
import retrofit2.awaitResponse
import java.lang.Exception
import javax.inject.Inject

/**
 * @author Lucas T. Mezzari
 * @since 12/08/2020
 */
class GithubService @Inject constructor(private val api: IGithubAPI):
    IGithubService {
    override suspend fun getUser(userId: String): User {
        return unwrapResponse(api.getUser(userId).awaitResponse())
    }

    override suspend fun getUserRepositories(userId: String): List<Repository> {
        return unwrapResponse(api.getUserRepositories(userId).awaitResponse())
    }

    override suspend fun getRepositoryReadme(userId: String, repoId: String): Content {
        return unwrapResponse(api.getRepositoryReadme(userId, repoId).awaitResponse())
    }

    private fun <T> unwrapResponse(response: Response<T>): T {
        val code = response.code()
        val result = response.body()

        if (result != null && code in 200..399) {
            return result
        } else {
            throw Exception(response.message())
        }
    }
}