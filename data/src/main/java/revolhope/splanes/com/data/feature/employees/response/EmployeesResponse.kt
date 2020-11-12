package revolhope.splanes.com.data.feature.employees.response

import com.google.gson.annotations.SerializedName

data class EmployeesResponse(
    @SerializedName("current") val current: Int?,
    @SerializedName("total") val total: Int?,
    @SerializedName("results") val results: List<OompaLoompaResponse>?
)