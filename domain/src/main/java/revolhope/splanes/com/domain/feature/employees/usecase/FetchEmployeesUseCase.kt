package revolhope.splanes.com.domain.feature.employees.usecase

import revolhope.splanes.com.domain.feature.employees.model.OompaLoompaModel
import revolhope.splanes.com.domain.feature.employees.repository.EmployeeRepository
import revolhope.splanes.com.domain.library.usecase.BaseUseCase

class FetchEmployeesUseCase(
    private val employeeRepository: EmployeeRepository
) : BaseUseCase<FetchEmployeesUseCase.Request, Triple<Int, Int, List<OompaLoompaModel>>>() {

    override suspend fun execute(req: Request): Triple<Int, Int, List<OompaLoompaModel>>? =
        employeeRepository.fetchEmployees(req.page)

    data class Request(val page: Int = 1)
}