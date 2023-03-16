package dev.aashishtathod.stockregisterassignment.data.repository

import android.util.Log
import dev.aashishtathod.stockregisterassignment.data.dto.WeatherDTO
import dev.aashishtathod.stockregisterassignment.data.remote.WeatherService
import dev.aashishtathod.stockregisterassignment.utils.Either
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
	private val weatherService: WeatherService
) : WeatherRepository {
	
	override suspend fun getWeather(latitude: Double, longitude: Double): Flow<Either<WeatherDTO>> = flow {
		try {
			val result = weatherService.getCurrentWeather(latitude, longitude)
			emit(Either.Success(result))
		} catch (e: Exception) {
			Log.d("ddddd", e.message.toString())
			emit(Either.Error("Something went wrong"))
		}
	}
	
}