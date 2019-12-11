package com.ucbits.ble_uart;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import com.mapbox.mapboxsdk.Mapbox;

import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.ucbits.ble_uart.pojo.WeatherData;
import com.ucbits.bleloc.BasicBeaconIndoorLocationProvider;

import org.json.JSONException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.indoorlocation.gps.GPSIndoorLocationProvider;
import io.mapwize.mapwizeformapbox.MapOptions;
import io.mapwize.mapwizeformapbox.MapwizePlugin;


public class MapActivity extends AppCompatActivity {

    private MapView mapView;
    private MapwizePlugin mpPlugin;
    private BasicBeaconIndoorLocationProvider beaconLocationProvider;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 0;
    private String API_KEY = "4011f2d84db736fa6807139d79324eb1";
    HttpClient rest_client = new HttpClient();

    private static final int NOTIFICATION_ID = 1;
    private static final String NOTIFICATION_CHANNEL_ID = "my_notification_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1Ijoic3VqZXNobTMiLCJhIjoiY2szYnY0N2l3MGY5djNvcDgwNWZubTY3ZCJ9.7rNt6qWSdKCzNb4pmom4dg");
        setContentView(R.layout.activity_map);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        mapView = findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mpPlugin = new MapwizePlugin(mapView, mapboxMap, new MapOptions());
                //mapwizePlugin.addMarker(new LatLngFloor(1,1,1.0), new IconFactory());
                setupLocationProvider();
            }
        });
    }

    public void sendNotification(View view) {
        String notifTitle = "";
        String notifText = "";
        String notifUrl = "";
        double latitude = 53.343027;
        double longitude = -6.250670;

        String weather_response = rest_client.getWeatherData("lat="+latitude+"&lon="+longitude);
        WeatherData weather = new WeatherData();
        try {
            weather = JsonParser.getWeather(weather_response);
            Log.d("REST",weather.getCountry());
        }catch (JSONException e) {
            e.printStackTrace();
        }
        String weatherDesc = weather.weather.getDescription();
        String wiki_response = rest_client.getWikirData("trinitycollegedublin").replaceAll("\n+","");
        Log.d("REST", wiki_response);

        Pattern urlPattern = Pattern.compile("<Url.*>(.*)</Url>");
        Pattern titlePattern = Pattern.compile("<Text.*>(.*)</Text>");
        Pattern descPattern = Pattern.compile("<Description.*>(.*)</Description>");

        Matcher url = urlPattern.matcher(wiki_response);   // get a matcher object
        Matcher title = titlePattern.matcher(wiki_response);
        Matcher desc = descPattern.matcher(wiki_response);

        while(url.find()) {
            notifUrl = url.group(1);
            Log.d("REST",notifUrl);
        }
        while(title.find()) {
            notifTitle =title.group(1);
            Log.d("REST",notifTitle);
        }
        while(desc.find()) {
            notifText = desc.group(1);
            Log.d("REST",notifText);
        }


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);

            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        //Create the intent that’ll fire when the user taps the notification//

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(notifUrl));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setVibrate(new long[]{0, 100, 100, 100, 100, 100})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Temperature Outside is " + (weather.MainObject.getTemp() - 273.15F) + "°C and weather is " + weatherDesc + "\nLocation Info: " +  notifText))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("You are at " + notifTitle)
                .setAutoCancel(true);

        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }


    private void setupLocationProvider() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_ACCESS_FINE_LOCATION);
        }
        else {
            beaconLocationProvider = new BasicBeaconIndoorLocationProvider(this, API_KEY, new GPSIndoorLocationProvider(this));
            mpPlugin.setLocationProvider(beaconLocationProvider);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setupLocationProvider();
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
