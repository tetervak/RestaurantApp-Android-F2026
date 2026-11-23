package ca.tetervak.restaurantapp.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ca.tetervak.restaurantapp.data.repository.RestaurantRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RefreshWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams), KoinComponent {

    private val repository: RestaurantRepository by inject()

    override suspend fun doWork(): Result {
        return try {
            Log.d(TAG, "Starting WorkManager background data refresh...")
            repository.refreshRestaurants()
            Log.d(TAG, "Data successfully refreshed via WorkManager.")
            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Error refreshing data via WorkManager", e)
            Result.retry()
        }
    }

    companion object {
        private const val TAG = "RefreshWorker"
        const val PERIODIC_WORK_NAME = "periodic_refresh_work"
        const val ONE_TIME_WORK_NAME = "one_time_refresh_work"
    }
}
