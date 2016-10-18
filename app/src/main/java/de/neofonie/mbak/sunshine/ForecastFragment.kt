package de.neofonie.mbak.sunshine

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import de.neofonie.mbak.utils.bindView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * A placeholder fragment containing a simple view.
 */
class ForecastFragment : Fragment() {

  private var mSubscription = Disposables.empty()
  private val listView: ListView by bindView(R.id.forecast_lv)
  private lateinit var mAdapter: ArrayAdapter<String>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {

    return inflater!!.inflate(R.layout.fragment_main, container, false)
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    mAdapter = ArrayAdapter(activity, R.layout.li_forecast, R.id.li_forecast_tv, ArrayList<String>())
    listView.adapter = mAdapter
    listView.onItemClickListener = AdapterView.OnItemClickListener{ parent, view, position, id -> clickDay(position)}
  }

  private fun clickDay(position: Int) {
    val forecast = mAdapter.getItem(position)
    val intent = Intent(context, DetailActivity::class.java)
    intent.putExtra(Intent.EXTRA_TEXT, forecast)
    startActivity(intent)
  }

  override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
    inflater.inflate(R.menu.forecastfragment, menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.action_refresh -> getWeather()
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onStart() {
    super.onStart()
    getWeather()
  }

  override fun onStop() {
    mSubscription.dispose()
    super.onStop()
  }

  private fun getWeather() {
    mSubscription.dispose()
    mSubscription = WeatherManager
        .getDailyForecast(PreferencesManager.getLocation(), PreferencesManager.getUnits(), 7)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { v -> updateUi(v) }
  }

  private fun updateUi(strings: List<String>) {
    mAdapter.clear()
    mAdapter.addAll(strings)
  }


}