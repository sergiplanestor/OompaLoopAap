package revolhope.splanes.com.presentation.feature.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.domain.feature.employees.model.OompaLoompaModel
import revolhope.splanes.com.domain.feature.employees.usecase.FetchEmployeesUseCase
import revolhope.splanes.com.presentation.common.base.BaseViewModel
import revolhope.splanes.com.presentation.feature.dashboard.filter.FilterModel

class DashboardViewModel(
    private val fetchEmployeesUseCase: FetchEmployeesUseCase
) : BaseViewModel() {

    private var pageControl = 0 to 1
    private var currentFilter: FilterModel? = null

    private var _employees = MutableLiveData<List<OompaLoompaModel>>()
    val employees: LiveData<List<OompaLoompaModel>> get() = _employees

    private var _isListFiltered = MutableLiveData<Boolean>()
    val isListFiltered: LiveData<Boolean> get() = _isListFiltered

    private var _emptyState = MutableLiveData<Boolean>()
    val emptyState: LiveData<Boolean> get() = _emptyState

    fun fetchEmployees() {
        if (pageControl.first + 1 <= pageControl.second) {
            launchAsync(
                action = {
                    fetchEmployeesUseCase.invoke(
                        FetchEmployeesUseCase.Request(page = pageControl.first + 1)
                    )
                },
                onSuccess = { res ->
                    pageControl = res.first to res.second
                    _employees.value = if (currentFilter != null) {
                        _isListFiltered.value = true
                        filter(res.third)
                    } else {
                        _isListFiltered.value = false
                        res.third
                    }.also {
                        _emptyState.value = it.isEmpty()
                    }
                }
            )
        }
    }

    fun filterList(filter: FilterModel) {
        this.currentFilter =
            if (filter.genderFilter.isNotEmpty() || !filter.queryProfession.isNullOrBlank()) {
                filter
            } else {
                null
            }
        this.pageControl = pageControl.first - 1 to pageControl.second
        this.fetchEmployees()
    }

    fun getCurrentFilter(): FilterModel? = currentFilter

    private fun filter(list: List<OompaLoompaModel>): List<OompaLoompaModel> {
        return list.filter {
            var matched = true
            if (!currentFilter?.genderFilter.isNullOrEmpty()) {
                matched = currentFilter!!.genderFilter.contains(it.gender)
            }
            if (matched && !currentFilter?.queryProfession.isNullOrBlank()) {
                matched = it.profession.startsWith(
                    prefix = currentFilter!!.queryProfession!!,
                    ignoreCase = true
                )
            }
            matched
        }
    }
}