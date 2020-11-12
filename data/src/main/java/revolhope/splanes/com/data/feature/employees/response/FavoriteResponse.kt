package revolhope.splanes.com.data.feature.employees.response

import com.google.gson.annotations.SerializedName

data class FavoriteResponse(
    @SerializedName("color") val color: String?,
    @SerializedName("food") val food: String?,
    @SerializedName("random_string") val random: String?,
    @SerializedName("song") val song: String?
)