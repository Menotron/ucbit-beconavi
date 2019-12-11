package com.ucbits.ble_uart;

import com.ucbits.ble_uart.pojo.WeatherData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {

        public static WeatherData getWeather(String data) throws JSONException {
            JSONObject jObj = new JSONObject(data);
            WeatherData weather = new WeatherData();

            JSONObject coordObj = getObject("coord", jObj);
            weather.setLatitude(getFloat("lat", coordObj));
            weather.setLongitude(getFloat("lon", coordObj));

            String base = getString("base", jObj);
            weather.setBase(base);
            //Float visibility = getFloat("visilibitty", jObj);
            //weather.setVisibility(visibility);
            JSONObject mainObj = getObject("main", jObj);
            weather.MainObject.setTemp(getFloat("temp",mainObj));
            weather.MainObject.setPressure(getInt("pressure",mainObj));
            weather.MainObject.setHumidity(getInt("humidity",mainObj));
            weather.MainObject.setTemp_min(getFloat("temp_min",mainObj));
            weather.MainObject.setTemp_max(getFloat("temp_max",mainObj));

            JSONObject sysObj = getObject("sys", jObj);
            weather.setCountry(getString("country", sysObj));
            weather.setCity(getString("name", jObj));

            // We get weather info (This is an array)
            JSONArray jArr = jObj.getJSONArray("weather");

            // We use only the first value
            JSONObject JSONWeather = jArr.getJSONObject(0);
            weather.weather.setId(getInt("id", JSONWeather));
            weather.weather.setDescription(getString("description", JSONWeather));
            weather.weather.setMain(getString("main", JSONWeather));


            // Wind
            JSONObject wObj = getObject("wind", jObj);
            weather.WindObject.setSpeed(getFloat("speed", wObj));
            weather.WindObject.setDeg(getFloat("deg", wObj));

            // Clouds
            JSONObject cObj = getObject("clouds", jObj);
            weather.CloudsObject.setAll(getInt("all", cObj));

            return weather;
        }


        private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
            JSONObject subObj = jObj.getJSONObject(tagName);
            return subObj;
        }

        private static String getString(String tagName, JSONObject jObj) throws JSONException {
            return jObj.getString(tagName);
        }

        private static float  getFloat(String tagName, JSONObject jObj) throws JSONException {
            return (float) jObj.getDouble(tagName);
        }

        private static int  getInt(String tagName, JSONObject jObj) throws JSONException {
            return jObj.getInt(tagName);
        }

}
