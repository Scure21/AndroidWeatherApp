package com.stephaniecure.stephapp;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class ForecastActivity extends AppCompatActivity {

    public static final String EXTRA_FORECAST = "extra_forecast";
    public static final String EXTRA_CITY = "extra_city";
    public ListView list;

    public static Intent createStartIntent(Context context, String cityName) {
        return new Intent(context, ForecastActivity.class)
                .putExtra(EXTRA_CITY, cityName);

    }

    // list View


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
    }
}
