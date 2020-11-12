package revolhope.splanes.com.data.feature.employees.net

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import revolhope.splanes.com.data.feature.employees.response.OompaLoompaDetailResponse
import revolhope.splanes.com.data.feature.employees.response.EmployeesResponse

interface EmployeeApi {

    @GET("/napptilus/oompa-loompas")
    suspend fun fetchEmployees(@Query("page") page: Int): EmployeesResponse

    @GET("/napptilus/oompa-loompas/{id}")
    suspend fun fetchDetail(@Path("id") id: Int): OompaLoompaDetailResponse
}