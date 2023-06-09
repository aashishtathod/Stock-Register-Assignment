package dev.aashishtathod.stockregisterassignment.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import dev.aashishtathod.stockregisterassignment.databinding.ActivityWeatherBinding

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {
	
	private lateinit var binding: ActivityWeatherBinding
	private lateinit var fusedLocationClient: FusedLocationProviderClient
	private val viewModel: WeatherViewModel by viewModels()
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityWeatherBinding.inflate(layoutInflater)
		setContentView(binding.root)
		
		fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
		
		observeState()
	}
	
	override fun onResume() {
		super.onResume()
		
		if (checkLocationPermission()) {
			fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
				if (location != null) viewModel.getWeatherData(
					location.latitude,
					location.longitude
				)
				else showErrorDialog("Can not get location")
			}
		} else requestLocationPermission()
		
	}
	
	private fun observeState() {
		lifecycleScope.launchWhenStarted {
			viewModel.state.collect{state ->
				when ( state ) {
					is WeatherState.Loading -> binding.pbMain.visibility = View.VISIBLE
					
					is WeatherState.Error -> {
						binding.pbMain.visibility = View.GONE
						Toast.makeText(
							this@WeatherActivity,
							state.msg,
							Toast.LENGTH_LONG
						).show()
					}
					
					is WeatherState.Idle -> binding.pbMain.visibility = View.GONE
					
					is WeatherState.Sucess -> {
						binding.pbMain.visibility = View.GONE
						state.data.apply {
							binding.tvCurrentWeather.text = weather[0].main
							binding.tvHumidity.text =  main.humidity.toString()
							binding.tvPressure.text =  main.pressure.toString()
							binding.tvTemperature.text = main.temp.toString()
							binding.tvVisibility.text = visibility.toString()
						}
					}
					
				}
			}
			
			
		}
	}
	
	override fun onRequestPermissionsResult(
		requestCode: Int,
		permissions: Array<out String>,
		grantResults: IntArray
	) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
		
		if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty()) {
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED && checkLocationPermission()) {
				
				fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
					if (location != null) viewModel.getWeatherData(
						location.latitude,
						location.longitude
					)
					else showErrorDialog("Can not get location")
				}
				
			} else showErrorDialog("Permissions are required to continue")
			
		}
	}
	
	private fun showErrorDialog(message: String) {
		AlertDialog.Builder(this)
			.setMessage(message)
			.setNeutralButton("Close App") { _, _ ->
				finish()
			}.create().show()
	}
	
	private fun checkLocationPermission(): Boolean {
		return ActivityCompat.checkSelfPermission(
			this, Manifest.permission.ACCESS_COARSE_LOCATION
		) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
			this, Manifest.permission.ACCESS_FINE_LOCATION
		) == PackageManager.PERMISSION_GRANTED
	}
	
	private fun requestLocationPermission() {
		ActivityCompat.requestPermissions(
			this,
			arrayOf(
				Manifest.permission.ACCESS_COARSE_LOCATION,
				Manifest.permission.ACCESS_FINE_LOCATION
			),
			LOCATION_PERMISSION_REQUEST_CODE
		)
	}
	
	companion object {
		const val LOCATION_PERMISSION_REQUEST_CODE = 100
	}
	
}
