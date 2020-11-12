package revolhope.splanes.com.domain.feature.employees.usecase

import revolhope.splanes.com.domain.feature.employees.model.OompaLoompaDetailsModel
import revolhope.splanes.com.domain.feature.employees.repository.EmployeeRepository
import revolhope.splanes.com.domain.library.usecase.BaseUseCase

class FetchEmployeeDetailsUseCase(
    private val employeeRepository: EmployeeRepository
) : BaseUseCase<FetchEmployeeDetailsUseCase.Request, OompaLoompaDetailsModel>() {

    override suspend fun execute(req: Request): OompaLoompaDetailsModel? =
        employeeRepository.fetchEmployeeDetails(req.id)

    data class Request(val id: Int)
}