package revolhope.splanes.com.presentation.feature.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import revolhope.splanes.com.domain.feature.employees.model.OompaLoompaDetailsModel
import revolhope.splanes.com.domain.feature.employees.model.OompaLoompaModel
import revolhope.splanes.com.domain.feature.employees.usecase.FetchEmployeeDetailsUseCase
import revolhope.splanes.com.presentation.common.base.BaseViewModel

class DetailViewModel(
    private val fetchEmployeeDetailsUseCase: FetchEmployeeDetailsUseCase
) : BaseViewModel() {

    val details: LiveData<OompaLoompaDetailsModel> get() = _details
    private var _details = MutableLiveData<OompaLoompaDetailsModel>()

    fun fetchDetails(model: OompaLoompaModel?) {
        model?.let { req ->
            launchAsync(
                action = {
                    fetchEmployeeDetailsUseCase.invoke(FetchEmployeeDetailsUseCase.Request(req.id))
                },
                onSuccess = { _details.value = it }
            )
        } ?: postError()
    }

    private fun postError() { _onErrorState.value = true }
}