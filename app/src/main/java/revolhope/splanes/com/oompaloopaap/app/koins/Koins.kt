package revolhope.splanes.com.oompaloopaap.app.koins

import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import revolhope.splanes.com.data.feature.employees.net.EmployeeApi
import revolhope.splanes.com.data.feature.employees.net.EmployeeNetworkDataSource
import revolhope.splanes.com.data.feature.employees.repositoryimpl.EmployeeRepositoryImpl
import revolhope.splanes.com.data.library.api.ApiClientFactory
import revolhope.splanes.com.data.library.api.BaseUrl
import revolhope.splanes.com.domain.feature.employees.repository.EmployeeRepository
import revolhope.splanes.com.domain.feature.employees.usecase.FetchEmployeeDetailsUseCase
import revolhope.splanes.com.domain.feature.employees.usecase.FetchEmployeesUseCase
import revolhope.splanes.com.presentation.feature.dashboard.DashboardViewModel
import revolhope.splanes.com.presentation.feature.detail.DetailViewModel


/* Repositories */
val repositoryModule = module(override = true) {
    single<EmployeeRepository> { EmployeeRepositoryImpl(get()) }
}

/* DataSources */
val dataSourceModule = module(override = true) {
    single { EmployeeNetworkDataSource(get()) }
    single {
        ApiClientFactory.create(
            baseUrl = BaseUrl.OOMPA_LOOMPA,
            clazz = EmployeeApi::class.java
        )
    }
}

/* UseCases */
val useCaseModule = module(override = true) {
    factory { FetchEmployeesUseCase(get()) }
    factory { FetchEmployeeDetailsUseCase(get()) }
}

val viewModelModule = module(override = true) {
    viewModel { DashboardViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}