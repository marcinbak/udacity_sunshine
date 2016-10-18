package de.neofonie.mbak.sunshine

import android.content.Intent
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.ShareActionProvider
import android.view.*
import android.widget.TextView
import de.neofonie.mbak.utils.bindView

/**
 * Created by marcinbak on 16/10/2016.
 */
class DetailFragment : Fragment() {

  private val FORECAST_SHARE_HASHTAG = " #SunshineApp"


  private val textView: TextView by bindView<TextView>(R.id.detail_text)
  private val text: String? by lazy { activity?.intent?.getStringExtra(Intent.EXTRA_TEXT) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
    val rootView = inflater.inflate(R.layout.fragment_detail, container, false)
    return rootView
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    textView.text = text
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    // Inflate the menu; this adds items to the action bar if it is present.
    inflater.inflate(R.menu.detailfragment, menu)

    val item = menu.findItem(R.id.action_share)

    // Fetch and store ShareActionProvider
    val shareActionProvider = MenuItemCompat.getActionProvider(item) as ShareActionProvider?

    shareActionProvider?.setShareIntent(createShareIntent())
  }

  private fun createShareIntent() = Intent(Intent.ACTION_SEND).apply {
    addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
    type = "text/plain"
    putExtra(Intent.EXTRA_TEXT, "$text $FORECAST_SHARE_HASHTAG")
  }

}