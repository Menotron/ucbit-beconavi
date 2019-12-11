package com.ucbits.ble_uart;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpClient {

    private static String WEATHER_BASE_URL = "http://api.openweathermap.org/data/2.5/weather?";
    private static String WIKI_BASE_URL = "https://en.wikipedia.org/w/api.php?action=opensearch&search=";
    private static String WIKI_SUFFIX = "&limit=1&namespace=0&format=xml";
    private static String APPID = "3a017cc0a29c6f8dfe513363d51a1909";

    public String getHttp(URL url){
        HttpURLConnection con = null ;
        InputStream is = null;
        try {
            con = (HttpURLConnection) (url).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            // Let's read the response
            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ( (line = br.readLine()) != null )
                buffer.append(line + "rn");

            is.close();
            con.disconnect();
            return buffer.toString();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }
        return null;
    }

    String getWeatherData(String location) {
        URL openweatherurl = null;
        try {
            openweatherurl = new URL(WEATHER_BASE_URL + location + "&APPID=" + APPID);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return getHttp(openweatherurl);
    }

    String getWikirData(String search) {
        URL wikiurl = null;
        try {
            wikiurl = new URL(WIKI_BASE_URL + search + WIKI_SUFFIX);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return getHttp(wikiurl);
    }


}


