package revolhope.splanes.com.presentation.feature.dashboard.filter

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import revolhope.splanes.com.domain.feature.employees.model.Gender

@Parcelize
data class FilterModel(
    val genderFilter: List<Gender>,
    val queryProfession: String?
) : Parcelable