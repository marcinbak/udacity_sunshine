package de.neofonie.mbak.sunshine

import android.app.Application

/**
 * Created by marcinbak on 14/10/2016.
 */
class SunshineApp : Application() {

  override fun onCreate() {
    super.onCreate()
    WeatherManager.init(this)
    PreferencesManager.init(this)
  }

}