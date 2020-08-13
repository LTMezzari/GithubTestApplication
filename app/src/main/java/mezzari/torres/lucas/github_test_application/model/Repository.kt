package mezzari.torres.lucas.github_test_application.model

import com.google.gson.annotations.SerializedName

/**
 * @author Lucas T. Mezzari
 * @since 12/08/2020
 */
class Repository {
    @SerializedName("id")
    var id: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("full_name")
    var fullName: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("language")
    var language: String? = null

    @SerializedName("stargazers_count")
    var stars: Int = 0

    @SerializedName("created_at")
    var createdAt: String? = null

    @SerializedName("updated_at")
    var updatedAt: String? = null

    @SerializedName("html_url")
    var originUrl: String? = null
}