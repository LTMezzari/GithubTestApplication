package mezzari.torres.lucas.github_test_application.model

import com.google.gson.annotations.SerializedName

/**
 * @author Lucas T. Mezzari
 * @since 12/08/2020
 */
class Content {

    @SerializedName("name")
    var name: String? = null

    @SerializedName("content")
    var base64Content: String? = null

    @SerializedName("html_url")
    var originUrl: String? = null
}