package revolhope.splanes.com.data.feature.employees.repositoryimpl

import revolhope.splanes.com.data.feature.employees.cache.EmployeeCacheDataSource
import revolhope.splanes.com.data.feature.employees.mapper.EmployeeMapper
import revolhope.splanes.com.data.feature.employees.net.EmployeeNetworkDataSource
import revolhope.splanes.com.domain.feature.employees.model.OompaLoompaDetailsModel
import revolhope.splanes.com.domain.feature.employees.model.OompaLoompaModel
import revolhope.splanes.com.domain.feature.employees.repository.EmployeeRepository

class EmployeeRepositoryImpl(
    private val networkDataSource: EmployeeNetworkDataSource
) : EmployeeRepository {

    override suspend fun fetchEmployees(page: Int): Triple<Int, Int, List<OompaLoompaModel>> {
        if (!EmployeeCacheDataSource.isCached(page)) {
            val response = networkDataSource.fetchEmployees(page)
            response.results?.map(EmployeeMapper::fromEmployeeResponseToModel)?.let { list ->
                EmployeeCacheDataSource.setData(
                    currentPage = response.current ?: page,
                    maxPage = response.total ?: page,
                    data = list
                )
            }
        }
        return EmployeeCacheDataSource.fetch()
    }

    override suspend fun fetchEmployeeDetails(id: Int): OompaLoompaDetailsModel =
        networkDataSource.fetchEmployeeDetail(id)
            .let(EmployeeMapper::fromEmployeeDetailsResponseToModel)
}