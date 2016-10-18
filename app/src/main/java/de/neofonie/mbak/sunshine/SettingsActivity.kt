package de.neofonie.mbak.sunshine

import android.os.Bundle
import android.preference.ListPreference
import android.preference.Preference
import android.preference.PreferenceActivity
import android.preference.PreferenceManager

class SettingsActivity : PreferenceActivity(), Preference.OnPreferenceChangeListener {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    addPreferencesFromResource(R.xml.pref_general)
    bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_location_key)))
    bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_units_key)))
  }

  /**
   * Attaches a listener so the summary is always updated with the preference value.
   * Also fires the listener once, to initialize the summary (so it shows up before the value
   * is changed.)
   */
  private fun bindPreferenceSummaryToValue(preference: Preference) {
    // Set the listener to watch for value changes.
    preference.onPreferenceChangeListener = this

    // Trigger the listener immediately with the preference's
    // current value.
    onPreferenceChange(preference,
        PreferenceManager.getDefaultSharedPreferences(preference.context).getString(preference.key, ""))
  }

  override fun onPreferenceChange(preference: Preference, value: Any): Boolean {
    val stringValue = value.toString()

    if (preference is ListPreference) {
      // For list preferences, look up the correct display value in
      // the preference's 'entries' list (since they have separate labels/values).
      val prefIndex = preference.findIndexOfValue(stringValue)
      if (prefIndex >= 0) {
        preference.setSummary(preference.entries[prefIndex])
      }
    } else {
      // For other preferences, set the summary to the value's simple string representation.
      preference.summary = stringValue
    }
    return true
  }
}

