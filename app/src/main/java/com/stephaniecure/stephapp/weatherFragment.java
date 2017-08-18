package com.stephaniecure.stephapp;

import android.media.Image;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by stephaniecure on 8/17/17.
 */

public class weatherFragment extends Fragment {

    private EditText zipCode;
    private Button getWeatherbutton;
    private Button forecastButton;
    private TextView currentTemp;
    private TextView minTemp;
    private TextView maxTemp;
    private TextView cloudsText;
    private TextView cityText;
    private ImageView image;
    private static String BASE_URL = "http://api.openweathermap.org";
    private static String BASE_FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast?q=22902&mode=json&units=metrics&appid=f8fdae74c29544baebdb927d392c5538";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.activity_main, container, false);

        zipCode = (EditText) view.findViewById(R.id.enterZipCode);
        cloudsText = (TextView) view.findViewById(R.id.clouds);
        currentTemp = (TextView) view.findViewById(R.id.temperature);
        cityText = (TextView) view.findViewById(R.id.city);
        minTemp = (TextView) view.findViewById(R.id.min_temp);
        maxTemp = (TextView) view.findViewById(R.id.max_temp);
        image = (ImageView) view.findViewById(R.id.main_image);
        getWeatherbutton = (Button) view.findViewById(R.id.getWeather);

        image.animate()
                .rotationY(360)
                .setDuration(3000)
                .start();

        getWeatherbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getActivity(), zipCode.getText(), Toast.LENGTH_SHORT).show();
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

                            Toast.makeText(getActivity(), zipCode.getText(), Toast.LENGTH_SHORT).show();

                            image.animate()
                                    .rotationX(360)
                                    .setDuration(3000)
                                    .start();
                        }
                    }


                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
                        // handle IO error
                        Toast.makeText(getActivity(),"Oops! an error occurred!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        forecastButton = view.findViewById(R.id.getForecast);

        forecastButton.setEnabled(true);
        forecastButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ForecastActivity.class);
                i.putExtra(ForecastActivity.EXTRA_CITY, zipCode.getText().toString());
                startActivity(i);
            }
        });

        // return view
        return view;
    }
}
