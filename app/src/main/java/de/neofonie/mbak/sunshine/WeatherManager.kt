package de.neofonie.mbak.sunshine

import android.content.Context
import android.text.format.Time
import android.util.Log
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by marcinbak on 14/10/2016.
 */
object WeatherManager {

  private val weatherApi = NetworkApis.weatherApi

  private lateinit var mContext: Context

  fun init(context: Context) {
    mContext = context.applicationContext
  }

  fun getDailyForecast(zipCode: String, units: String, count: Int): Single<List<String>> {
    var dayTime = Time()
    dayTime.setToNow()
    val julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff)

    // now we work exclusively in UTC
    dayTime = Time()

    return weatherApi.getForecast("$zipCode,us", BuildConfig.WEATHER_API_KEY, "metric", count, "json")
        .map { response -> response.list.mapIndexed { index, day -> dayToString(index, day, dayTime, julianStartDay, units) } }
  }

  private val LOG_TAG: String = "WeatherManager";

  private fun dayToString(index: Int, day: DayForecast, dayTime: Time, julianStartDay: Int, units: String): String {
    val dateTime: Long
    // Cheating to convert this to UTC time, which is what we want anyhow
    dateTime = dayTime.setJulianDay(julianStartDay + index)

    val date = getReadableDateString(dateTime)
    val main = day.weather[0].main
    var min = day.temp.min
    var max = day.temp.max

    when (units) {
      mContext.getString(R.string.pref_units_imperial) -> {
        max = (max * 1.8) + 32.0
        min = (min * 1.8) + 32.0
      }
      mContext.getString(R.string.pref_units_metric) -> {
      }
      else -> {
        Log.d(LOG_TAG, "Unit type not found: " + units)
      }
    }

    min = Math.round(min).toDouble()
    max = Math.round(max).toDouble()

    return "$date - $main, $max/$min"
  }

  private fun getReadableDateString(time: Long): String {
    // Because the API returns a unix timestamp (measured in seconds),
    // it must be converted to milliseconds in order to be converted to valid date.
    val shortenedDateFormat = SimpleDateFormat("EEE, MMM dd", Locale.getDefault())
    return shortenedDateFormat.format(time)
  }
}


val UsDateFormat = SimpleDateFormat("EEE, MM/yy", Locale.getDefault())

interface WeatherApi {

  @Headers("Accept: application/json")
  @GET("/data/2.5/forecast/daily")
  fun getForecast(@Query("zip") zipCode: String,
                  @Query("appid") apiCode: String,
                  @Query("units") units: String,
                  @Query("cnt") count: Int,
                  @Query("mode") mode: String): Single<DailyWeatherResponse>

}

data class DailyWeatherResponse(val city: City,
                                val cod: String,
                                val message: Double,
                                val cnt: Int,
                                val list: List<DayForecast>)

data class City(val id: Long,
                val name: String,
                val coord: Coord,
                val country: String,
                val population: Long)

data class DayForecast(val dt: Long,
                       val temp: Temp,
                       val pressure: Double,
                       val humidity: Int,
                       val weather: List<Weather>,
                       val speed: Double,
                       val deg: Int,
                       val clouds: Int)

data class Weather(val id: Long,
                   val main: String,
                   val description: String,
                   val icon: String)

data class Temp(val day: Double,
                val min: Double,
                val max: Double,
                val night: Double,
                val eve: Double,
                val morn: Double)

data class Coord(val lon: Double,
                 val lat: Double)

object NetworkApis {

  private val adapter: Retrofit = initAdapter()

  val weatherApi: WeatherApi by lazy { createWeather() }

  private fun initAdapter() = Retrofit.Builder()
      .baseUrl("http://api.openweathermap.org")
      .addConverterFactory(MoshiConverterFactory.create())
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .build()

  private fun createWeather() = adapter.create(WeatherApi::class.java)

}