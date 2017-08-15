package com.stephaniecure.stephapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText zipCode;
    private FusedLocationProviderClient mFusedLocationClient;
    private Button getWeatherbutton;
    private Button forecastButton;
    private TextView currentTemp;
    private TextView minTemp;
    private TextView maxTemp;
    private TextView cloudsText;
    private TextView cityText;
    private static String BASE_URL = "http://api.openweathermap.org";
    private static String IMG_URL = "http://openweathermap.org/img/w/";
    private static String BASE_FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast?q=22902&mode=json&units=metrics&appid=f8fdae74c29544baebdb927d392c5538";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //android:id="@blah"
        zipCode  =  (EditText)findViewById(R.id.enterZipCode);
        cloudsText = (TextView) findViewById(R.id.clouds);
        currentTemp = (TextView) findViewById(R.id.temperature);
        cityText = (TextView) findViewById(R.id.city);
        minTemp = (TextView) findViewById(R.id.min_temp);
        maxTemp = (TextView) findViewById(R.id.max_temp);
        getWeatherbutton = (Button)findViewById(R.id.getWeather);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getWeatherbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, zipCode.getText(), Toast.LENGTH_SHORT).show();
                // get info from weather API

                final Retrofit retrofit = new Retrofit.Builder()
                        // pass custom params: Http client, parser, etc.
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                WeatherApi api = retrofit.create(WeatherApi.class);

                api.getWeatherForCity(zipCode.getText().toString(), "f8fdae74c29544baebdb927d392c5538").enqueue(new Callback<WeatherResponse>() {

                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                        if (response.isSuccessful()) {

                            String clouds = response.body().weather.get(0).main;
                            Double temperature = response.body().main.temp;
                            Double minTempe = response.body().main.temp_min;
                            Double maxTempe = response.body().main.temp_max;
                            String city = response.body().name;
                            cityText.setText(city);
                            cloudsText.setText(clouds);
                            currentTemp.setText(temperature.toString());
                            minTemp.setText(minTempe.toString());
                            maxTemp.setText(maxTempe.toString());

                            Toast.makeText(MainActivity.this, zipCode.getText(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
                        // handle IO error
                        Toast.makeText(MainActivity.this,"Oops! an error occurred!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
       });

        forecastButton = (Button)findViewById(R.id.getForecast);

        forecastButton.setEnabled(true);
        forecastButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ForecastActivity.class);
                i.putExtra(ForecastActivity.EXTRA_CITY, zipCode.getText().toString());
                startActivity(i);
            }
        });
    }

    // ask the user for permission if they don't have location services activated
    private void askForPermission(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);
            }
        } else {
            Toast.makeText(this, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }
    }
//
//    // Listen for location changes
//    // Acquire a reference to the system Location Manager
//    LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//
//    // Define a listener that responds to location updates
//    LocationListener locationListener = new LocationListener() {
//        public void onLocationChanged(Location location) {
//            // Called when a new location is found by the network location provider.
//            // make API request to fetch the curren location weather
//            Log.d("LOCATION: ", location.toString());
//        }
//
//        public void onStatusChanged(String provider, int status, Bundle extras) {}
//
//        public void onProviderEnabled(String provider) {}
//
//        public void onProviderDisabled(String provider) {}
//    };

// Register the listener with the Location Manager to receive location updates
//locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
}
