package mezzari.torres.lucas.github_test_application.network.service

import mezzari.torres.lucas.github_test_application.model.Content
import mezzari.torres.lucas.github_test_application.model.Repository
import mezzari.torres.lucas.github_test_application.model.User

/**
 * @author Lucas T. Mezzari
 * @since 12/08/2020
 */
interface IGithubService {
    suspend fun getUser(userId: String): User

    suspend fun getUserRepositories(userId: String): List<Repository>

    suspend fun getRepositoryReadme(userId: String, repoId: String): Content
}