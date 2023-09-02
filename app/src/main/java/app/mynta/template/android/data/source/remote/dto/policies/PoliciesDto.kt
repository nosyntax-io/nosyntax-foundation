package app.mynta.template.android.data.source.remote.dto.policies

import com.google.gson.annotations.SerializedName

data class PoliciesDto(
    @SerializedName("policies")
    val policies: Policies) {

    data class Policies(
        @SerializedName("privacy")
        val privacy: Privacy,
        @SerializedName("terms")
        val terms: Terms,
        @SerializedName("response")
        val response: Response) {

        data class Privacy(
            @SerializedName("content")
            val content: String
        )

        data class Terms(
            @SerializedName("content")
            val content: String
        )

        data class Response(
            @SerializedName("code")
            val code: Int
        )
    }
}