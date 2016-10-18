package de.neofonie.mbak.sunshine

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

class DetailActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_detail)
    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction().add(R.id.container, DetailFragment()).commit()
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    // Inflate the menu; this adds items to the action bar if it is present.
    menuInflater.inflate(R.menu.detail, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.action_settings -> {
        startActivity(Intent(this, SettingsActivity::class.java))
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }

}
