/*
 * (c) Neofonie Mobile GmbH (2016)
 *
 * This computer program is the sole property of Neofonie Mobile GmbH (http://mobile.neofonie.de)
 * and is protected under the German Copyright Act (paragraph 69a UrhG).
 *
 * All rights are reserved. Making copies, duplicating, modifying, using or distributing
 * this computer program in any form, without prior written consent of Neofonie Mobile GmbH, is prohibited.
 * Violation of copyright is punishable under the German Copyright Act (paragraph 106 UrhG).
 *
 * Removing this copyright statement is also a violation.
 */
package de.neofonie.mbak.sunshine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by marcinbak on 14/10/2016.
 */
public class JsonParser {

  public static double getMaxTemperatureForDay(String weatherJsonStr, int dayIndex)
      throws JSONException {
    JSONObject object = new JSONObject(weatherJsonStr);
    JSONArray array = object.getJSONArray("list");
    JSONObject day = array.getJSONObject(dayIndex);
    JSONObject temp = day.getJSONObject("temp");

    return temp.getDouble("max");
  }
}
