package revolhope.splanes.com.domain.feature.employees.repository

import revolhope.splanes.com.domain.feature.employees.model.OompaLoompaDetailsModel
import revolhope.splanes.com.domain.feature.employees.model.OompaLoompaModel

interface EmployeeRepository {

    suspend fun fetchEmployees(page: Int): Triple<Int, Int, List<OompaLoompaModel>>

    suspend fun fetchEmployeeDetails(id: Int): OompaLoompaDetailsModel
}