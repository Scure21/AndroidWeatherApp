package com.stephaniecure.stephapp;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText zipCode;
    private Button getWeatherbutton;
    private Button forecastButton;
    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather/";
    private static String IMG_URL = "http://openweathermap.org/img/w/";
    private static String BASE_FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast?q=22902&mode=json&units=metrics&appid=f8fdae74c29544baebdb927d392c5538";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //android:id="@blah"
        zipCode  =  (EditText)findViewById(R.id.enterZipCode);
        zipCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        getWeatherbutton = (Button)findViewById(R.id.getWeather);
        getWeatherbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, zipCode.getText(), Toast.LENGTH_SHORT).show();
                // get info from weather API

                Retrofit retrofit = new Retrofit.Builder()
                        // pass custom params: Http client, parser, etc.
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Log.d("HELLOOOO~~~~~~~~~~~", BASE_URL);
                WeatherApi api = retrofit.create(WeatherApi.class);

                api.getWeatherForCity(zipCode.getText().toString(), "f8fdae74c29544baebdb927d392c5538").enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                        if (response.isSuccessful()) {

                            String weather = response.body().weather.main;
                            Toast.makeText(MainActivity.this, weather, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
                        // handle IO error
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
}
