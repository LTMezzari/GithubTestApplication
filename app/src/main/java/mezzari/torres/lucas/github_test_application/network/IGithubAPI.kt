package mezzari.torres.lucas.github_test_application.network

import mezzari.torres.lucas.github_test_application.model.Content
import mezzari.torres.lucas.github_test_application.model.Repository
import mezzari.torres.lucas.github_test_application.model.User
import mezzari.torres.lucas.network.annotation.Route
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author Lucas T. Mezzari
 * @since 12/08/2020
 */
@Route("https://api.github.com/")
interface IGithubAPI {
    @GET("users/{userId}")
    fun getUser(
        @Path("userId") userId: String
    ): Call<User>

    @GET("users/{userId}/repos")
    fun getUserRepositories(
        @Path("userId") userId: String
    ): Call<List<Repository>>

    @GET("repos/{userId}/{repoId}/contents/README.md")
    fun getRepositoryReadme(
        @Path("userId") userId: String,
        @Path("repoId") repoId: String
    ): Call<Content>
}