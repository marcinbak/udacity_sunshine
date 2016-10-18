package de.neofonie.mbak.sunshine

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

/**
 * Created by marcinbak on 14/10/2016.
 */
private val PREFS_NAME = "ASDF12DW5E34ad6fsi6s6a3f4dknskdfa"

object PreferencesManager {

  private lateinit var mPrefs: SharedPreferences
  private lateinit var mContext: Context

  fun init(context: Context) {
    mContext = context
    mPrefs = mContext.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE)
  }

  fun getLocation(): String = mPrefs.getString(mContext.getString(R.string.pref_location_key), mContext.getString(R.string.pre_location_default))

  fun getUnits(): String = mPrefs.getString(mContext.getString(R.string.pref_units_key), mContext.getString(R.string.pref_units_metric))

}