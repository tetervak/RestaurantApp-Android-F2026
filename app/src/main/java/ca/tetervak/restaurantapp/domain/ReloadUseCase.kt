package ca.tetervak.restaurantapp.domain

import ca.tetervak.restaurantapp.work.RestaurantWorkScheduler

class ReloadUseCase(
    private val workScheduler: RestaurantWorkScheduler
) {
    suspend operator fun invoke() = workScheduler.scheduleOneTimeRefresh()
}
