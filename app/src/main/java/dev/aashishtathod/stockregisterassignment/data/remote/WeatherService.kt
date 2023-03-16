package dev.aashishtathod.stockregisterassignment.data.remote

import dev.aashishtathod.stockregisterassignment.data.dto.WeatherDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
	
	@GET("weather")
	suspend fun getCurrentWeather(
		@Query("lat") latitude: Double,
		@Query("lon") longitude: Double,
		@Query("appid") appid: String = "37abe143726a4e823e0aed5bdc0852b6",
	): WeatherDTO
}