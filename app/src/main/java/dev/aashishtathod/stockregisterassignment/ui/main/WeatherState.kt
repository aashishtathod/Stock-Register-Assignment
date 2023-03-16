package dev.aashishtathod.stockregisterassignment.ui.main

import dev.aashishtathod.stockregisterassignment.data.dto.WeatherDTO

sealed class WeatherState {
	object Idle : WeatherState()
	object Loading : WeatherState()
	data class Error(val msg: String) : WeatherState()
	data class Sucess(val data: WeatherDTO) : WeatherState()
}