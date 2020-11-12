package revolhope.splanes.com.data.feature.employees.response

import com.google.gson.annotations.SerializedName

data class OompaLoompaResponse(
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("last_name") val lastName: String?,
    @SerializedName("favorite") val favorite: FavoriteResponse?,
    @SerializedName("gender") val gender: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("profession") val profession: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("age") val age: Int?,
    @SerializedName("country") val country: String?,
    @SerializedName("height") val height: Int?,
    @SerializedName("id") val id: Int?
)