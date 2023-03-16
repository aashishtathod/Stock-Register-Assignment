package dev.aashishtathod.stockregisterassignment.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import dev.aashishtathod.stockregisterassignment.data.remote.WeatherService
import dev.aashishtathod.stockregisterassignment.data.repository.WeatherRepository
import dev.aashishtathod.stockregisterassignment.data.repository.WeatherRepositoryImpl

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
	
	@Provides
	fun provideWeatherRepository(
		service:WeatherService
	) : WeatherRepository = WeatherRepositoryImpl(service)
	
}