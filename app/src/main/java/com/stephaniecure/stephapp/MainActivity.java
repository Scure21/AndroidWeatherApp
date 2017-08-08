package com.stephaniecure.stephapp;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText zipCode;
    private Button getWeatherbutton;
    private Button forecastButton;
    private Animation animation;

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

        ValueAnimator animator = ValueAnimator.ofFloat(1,0);
        animator.setDuration(1000);
        animator.setRepeatCount(1);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                float value = animation.getAnimatedValue();
//                animation.setAlpha(value);
            }
        });
        animator.start();
    }
}
