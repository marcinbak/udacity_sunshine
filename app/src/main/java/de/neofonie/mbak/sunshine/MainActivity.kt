package de.neofonie.mbak.sunshine

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity() {

  private val LOG_TAG = "MainActivity"

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.main, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.action_settings -> {
        startActivity(Intent(this, SettingsActivity::class.java))
        return true
      }
      R.id.action_map -> {
        openPreferredLocationInMap()
      }
    }
    return super.onOptionsItemSelected(item)
  }

  private fun openPreferredLocationInMap() {
    val location = PreferencesManager.getLocation()
    val geoLocation = Uri.parse("geo:0,0?").buildUpon()
        .appendQueryParameter("q", location)
        .build()

    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = geoLocation

    if (intent.resolveActivity(packageManager) != null) {
      startActivity(intent)
    } else {
      Log.d(LOG_TAG, "Couldn't call $location, no receiving apps installed!");
    }
  }

}
