package de.neofonie.mbak.sunshine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

  public MainActivityFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // http://api.openweathermap.org/data/2.5/forecast/daily?
    // zip=94040,us&
    // appid=627ae5e49713570d91f2054169ecf061&
    // units=metric&
    // cnt=7&
    // mode=json

    View view = inflater.inflate(R.layout.fragment_main, container, false);

    final int maxList = 20;
    ArrayList<String> strings = new ArrayList<>(maxList);
    for (int i = 0; i < maxList; i++) {
      strings.add("Weather " + i);
    }

    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.li_forecast, R.id.li_forecast_tv, strings);

    ListView list = (ListView) view.findViewById(R.id.forecast_lv);
    list.setAdapter(adapter);

    return view;
  }
}
