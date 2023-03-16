package dev.aashishtathod.stockregisterassignment.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aashishtathod.stockregisterassignment.data.repository.WeatherRepository
import dev.aashishtathod.stockregisterassignment.utils.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
	private val weatherRepository: WeatherRepository
) : ViewModel() {
	
	private val _state = MutableStateFlow<WeatherState>(WeatherState.Idle)
	
	val state: StateFlow<WeatherState> = _state.asStateFlow()
	
	fun getWeatherData(latitude: Double, longitude: Double) {
		viewModelScope.launch {
			
			withContext(Dispatchers.IO) {
				_state.value = WeatherState.Loading
				
				weatherRepository.getWeather(latitude,longitude).collect{
					_state.value = when(it){
							is Either.Success -> {
								WeatherState.Sucess(it.data)
							}
							is Either.Error -> {
								WeatherState.Error(it.message)
							}
						}
				}
				
			}
		}
	}
}