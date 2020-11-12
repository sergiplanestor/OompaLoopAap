package revolhope.splanes.com.domain.feature.employees.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteModel(
    val color: String,
    val food: String,
    val song: String
) : Parcelable {
    companion object {
        val empty
            get() =
                FavoriteModel(
                    color = "",
                    food = "",
                    song = ""
                )
    }
}
