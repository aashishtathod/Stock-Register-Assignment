package dev.aashishtathod.stockregisterassignment.data.repository

import dev.aashishtathod.stockregisterassignment.data.dto.WeatherDTO
import dev.aashishtathod.stockregisterassignment.utils.Either
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
	
	suspend fun getWeather(latitude:Double, longitude:Double) : Flow<Either<WeatherDTO>>
}