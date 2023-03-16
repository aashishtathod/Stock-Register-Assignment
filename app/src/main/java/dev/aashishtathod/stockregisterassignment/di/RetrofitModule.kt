package dev.aashishtathod.stockregisterassignment.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {
	
	
	@Provides
	@Singleton
	fun provideOkHttpClient(
		logging: HttpLoggingInterceptor,
	): OkHttpClient {
		return OkHttpClient.Builder()
			.addNetworkInterceptor(logging)
			.build()
	}
	
	
	@Provides
	@Singleton
	fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
		val logging = HttpLoggingInterceptor()
		logging.level = HttpLoggingInterceptor.Level.BODY
		return logging
	}
	
	@Provides
	@Singleton
	fun provideGson(): Gson {
		return GsonBuilder()
			.setLenient()
			.serializeNulls() // To allow sending null values
			.create()
	}
	
	
	
	@Provides
	@Singleton
	fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
		.client(okHttpClient)
		.baseUrl("https://api.openweathermap.org/data/2.5/")
		.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
		.addConverterFactory(GsonConverterFactory.create())
		.build()
}
