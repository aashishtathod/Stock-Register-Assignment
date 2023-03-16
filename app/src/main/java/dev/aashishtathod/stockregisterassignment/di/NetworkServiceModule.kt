package dev.aashishtathod.stockregisterassignment.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import dev.aashishtathod.stockregisterassignment.data.remote.WeatherService
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
class NetworkServiceModule {
	
	@Provides
	fun provideWeatherService(retrofit: Retrofit): WeatherService =
		retrofit.create(WeatherService::class.java)
}