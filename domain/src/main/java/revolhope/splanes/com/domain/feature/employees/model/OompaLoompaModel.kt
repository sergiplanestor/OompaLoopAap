package revolhope.splanes.com.domain.feature.employees.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OompaLoompaModel(
    val firstName: String,
    val lastName: String,
    val favorite: FavoriteModel,
    val gender: Gender,
    val image: String,
    val profession: String,
    val email: String,
    val age: Int,
    val country: String,
    val height: Int,
    val id: Int
) : Parcelable