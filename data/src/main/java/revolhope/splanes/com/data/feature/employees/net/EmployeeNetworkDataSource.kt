package revolhope.splanes.com.data.feature.employees.net

import revolhope.splanes.com.data.feature.employees.response.EmployeesResponse
import revolhope.splanes.com.data.feature.employees.response.OompaLoompaDetailResponse

class EmployeeNetworkDataSource(private val employeeApi: EmployeeApi) {

    suspend fun fetchEmployees(page: Int): EmployeesResponse = employeeApi.fetchEmployees(page)

    suspend fun fetchEmployeeDetail(id: Int): OompaLoompaDetailResponse =
        employeeApi.fetchDetail(id)
}