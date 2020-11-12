package revolhope.splanes.com.data.feature.employees.cache

import revolhope.splanes.com.domain.feature.employees.model.OompaLoompaModel

object EmployeeCacheDataSource {

    private var employees: MutableList<OompaLoompaModel>? = null
    private var currentPage: Int = 0
    private var maxPage: Int = 1

    fun isCached(page: Int): Boolean = currentPage >= page

    fun setData(
        currentPage: Int,
        maxPage: Int,
        data: List<OompaLoompaModel>
    ) {
        if (this.employees == null) this.employees = mutableListOf()
        this.currentPage = currentPage
        this.maxPage = maxPage
        this.employees?.addAll(data)
    }

    fun fetch(): Triple<Int, Int, List<OompaLoompaModel>> =
        Triple(
            first = this.currentPage,
            second = this.maxPage,
            third = this.employees ?: emptyList()
        )

    fun clear() {
        this.currentPage = 0
        this.maxPage = 1
        this.employees = null
    }
}