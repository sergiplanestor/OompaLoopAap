package revolhope.splanes.com.oompaloopaap.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import revolhope.splanes.com.oompaloopaap.app.koins.dataSourceModule
import revolhope.splanes.com.oompaloopaap.app.koins.repositoryModule
import revolhope.splanes.com.oompaloopaap.app.koins.useCaseModule
import revolhope.splanes.com.oompaloopaap.app.koins.viewModelModule

class OompaLoompAap : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@OompaLoompAap)
            modules(listOf(
                    repositoryModule,
                    dataSourceModule,
                    useCaseModule,
                    viewModelModule)
            )
        }
    }
}