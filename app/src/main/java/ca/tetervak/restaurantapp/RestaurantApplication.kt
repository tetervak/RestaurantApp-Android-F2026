package ca.tetervak.restaurantapp

import android.app.Application
import ca.tetervak.restaurantapp.di.appModules
import ca.tetervak.restaurantapp.work.RestaurantWorkScheduler
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin

class RestaurantApplication : Application() {

    private val workScheduler: RestaurantWorkScheduler by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@RestaurantApplication)
            workManagerFactory() // Registers Koin WorkerFactory with Koin container
            modules(appModules)
        }

        // Schedule periodic refresh task (every 15 minutes)
        workScheduler.schedulePeriodicRefresh()

        // Schedule one-time refresh task on application start
        workScheduler.scheduleOneTimeRefresh()
    }
}
