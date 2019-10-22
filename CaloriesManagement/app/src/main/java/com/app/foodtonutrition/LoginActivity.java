package com.app.foodtonutrition;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.foodtonutrition.room.model.MeasurementModel;
import com.app.foodtonutrition.room.repository.Repository;
import com.app.foodtonutrition.R;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private ProgressBar pdLoading;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        pdLoading = findViewById(R.id.pdLoading);
        repository = new Repository(LoginActivity.this);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdLoading.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pdLoading.setVisibility(View.GONE);
                        if (validation()) {
                            repository.checkAuthentication(etEmail.getText().toString().trim(), etPassword.getText().toString().trim())
                                    .observe(LoginActivity.this, new Observer<Integer>() {
                                        @Override
                                        public void onChanged(@Nullable Integer integer) {
                                            if (integer == null || integer == 0) {
                                                Toast.makeText(LoginActivity.this, "Email or password does not matched.", Toast.LENGTH_LONG).show();
                                            } else {
                                                insertFoodMeasurement();
                                                updateSharedPreference();
                                                startActivity(new Intent(LoginActivity.this, MainPageActivity.class));
                                                finishAffinity();
                                            }
                                        }
                                    });
                        }
                    }
                }, 2000);
            }
        });
    }

    private boolean validation() {
        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            etEmail.setError("email cannot be empty");
            return false;
        } else if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError("Password missing");
            return false;
        } else
            return true;
    }

    private void insertFoodMeasurement() {
        ArrayList<MeasurementModel> measurementModels = new ArrayList<>();
        measurementModels.add(new MeasurementModel(1, "Noodles,Egg,cooked", (8.0/ 169), (42.0 /169), (1.0 /169), (1.9 /169), (2.0 / 169), (0.5 /169)));
        measurementModels.add(new MeasurementModel(2, "Pasta,fresh-refrigerated,spinach,cooked", (9.0 /169), (41.0 /169), (1.0 /169), (2.2 / 169), (3.0 / 169), (0.6 / 64)));
        measurementModels.add(new MeasurementModel(3, " Ramen noodles, chicken ﬂavour, dry", (9/85), (54.0 / 85), (1.0 / 85), (2.0/85), (13.0 / 85), (6.0 /85)));
        measurementModels.add(new MeasurementModel(4, "Spaghetti", (7.0/148), (42.0 / 148), (1.0 / 148), (2.5 / 148), (1.0 / 148), (0.1 / 148)));
        measurementModels.add(new MeasurementModel(5, "Mufﬁn fruit", (6.0 / 113), (54.0 / 113), (22.0 / 113), (2.9 / 113), (7.0 / 113), (1.6 / 113)));
        measurementModels.add(new MeasurementModel(6, "chocolate chips", (1.0 / 20), (13 / 20), (1.0 / 64), (4.7 / 64), (2.0 / 64), (0.3 / 64)));
        measurementModels.add(new MeasurementModel(7, "mushroom raw", (6.0 / 64), (35.0 / 64), (1.0 / 64), (4.7 / 64), (2.0 / 64), (0.3 / 64)));
        measurementModels.add(new MeasurementModel(8, "Onions, green (scallion), raw", (6.0 / 64), (35.0 / 64), (1.0 / 64), (4.7 / 64), (2.0 / 64), (0.3 / 64)));
        measurementModels.add(new MeasurementModel(9, "Potatoes, hashed brown, plain, frozen, heated", (6.0 / 64), (35.0 / 64), (1.0 / 64), (4.7 / 64), (2.0 / 64), (0.3 / 64)));
        measurementModels.add(new MeasurementModel(10, " Avocado ", (6.0 / 64), (35.0 / 64), (1.0 / 64), (4.7 / 64), (2.0 / 64), (0.3 / 64)));
        measurementModels.add(new MeasurementModel(11, " Egg, fried ", (6.0 / 64), (35.0 / 64), (1.0 / 64), (4.7 / 64), (2.0 / 64), (0.3 / 64)));
        measurementModels.add(new MeasurementModel(12, "Egg, boiled", (6.0 / 64), (35.0 / 64), (1.0 / 64), (4.7 / 64), (2.0 / 64), (0.3 / 64)));
        measurementModels.add(new MeasurementModel(13, "salmon smoked", (6.0 / 64), (35.0 / 64), (1.0 / 64), (4.7 / 64), (2.0 / 64), (0.3 / 64)));
        measurementModels.add(new MeasurementModel(14, "Beef Blade steak, lean + fat, braised", (6.0 / 64), (35.0 / 64), (1.0 / 64), (4.7 / 64), (2.0 / 64), (0.3 / 64)));
        measurementModels.add(new MeasurementModel(15, "pork pan fried ", (6.0 / 64), (35.0 / 64), (1.0 / 64), (4.7 / 64), (2.0 / 64), (0.3 / 64)));
        measurementModels.add(new MeasurementModel(16, "lamb(American, fresh, ground, cooked) ", (6.0 / 64), (35.0 / 64), (1.0 / 64), (4.7 / 64), (2.0 / 64), (0.3 / 64)));
        measurementModels.add(new MeasurementModel(17, "Chicken, broiler, breast, meat, roasted", (6.0 / 64), (35.0 / 64), (1.0 / 64), (4.7 / 64), (2.0 / 64), (0.3 / 64)));
        measurementModels.add(new MeasurementModel(18, "Duck, wild, cooked", (6.0 / 64), (35.0 / 64), (1.0 / 64), (4.7 / 64), (2.0 / 64), (0.3 / 64)));
        measurementModels.add(new MeasurementModel(19, "Turkey, ground, cooked", (6.0 / 64), (35.0 / 64), (1.0 / 64), (4.7 / 64), (2.0 / 64), (0.3 / 64)));
        measurementModels.add(new MeasurementModel(20, " Goat, roasted", (6.0 / 64), (35.0 / 64), (1.0 / 64), (4.7 / 64), (2.0 / 64), (0.3 / 64)));
        measurementModels.add(new MeasurementModel(21, "Back bacon, pork, grilled", (6.0 / 64), (35.0 / 64), (1.0 / 64), (4.7 / 64), (2.0 / 64), (0.3 / 64)));
        measurementModels.add(new MeasurementModel(22, "Pizza with cheese, meat and vegetables ", (6.0 / 64), (35.0 / 64), (1.0 / 64), (4.7 / 64), (2.0 / 64), (0.3 / 64)));measurementModels.add(new MeasurementModel(1, "Bread, pita, whole wheat (17cm diam)", (6.0 / 64), (35.0 / 64), (1.0 / 64), (4.7 / 64), (2.0 / 64), (0.3 / 64)));
        measurementModels.add(new MeasurementModel(23, " Beef stew", (6.0 / 64), (35.0 / 64), (1.0 / 64), (4.7 / 64), (2.0 / 64), (0.3 / 64)));
        measurementModels.add(new MeasurementModel(24, "Chicken noodle", (6.0 / 64), (35.0 / 64), (1.0 / 64), (4.7 / 64), (2.0 / 64), (0.3 / 64)));
        measurementModels.add(new MeasurementModel(25, "Bread, pita, whole wheat (17cm diam)", (6.0 / 64), (35.0 / 64), (1.0 / 64), (4.7 / 64), (2.0 / 64), (0.3 / 64)));
        measurementModels.add(new MeasurementModel(26, "Bread, raisin", (3.0 / 35), (28.0 / 35), (2.0 / 35), (1.5 / 35), (2.0 / 35), (0.4 / 35)));
        measurementModels.add(new MeasurementModel(27, "Bread, rye", (3.0 / 35), (17.0 / 35), (1.0 / 35), (2.0 / 35), (1.0 / 35), (0.2 / 35)));
        measurementModels.add(new MeasurementModel(28, "English muffin, white, toasted", (4.0 / 52), (26.0 / 52), (2.0 / 52), (1.5 / 52), (1.0 / 52), (0.1 / 52)));
        measurementModels.add(new MeasurementModel(29, "Coffee, latte", (5.0 / 52), (25.0 / 52), (1.0 / 52), (2.6 / 52), (1.0 / 52), (0.2 / 52)));
        measurementModels.add(new MeasurementModel(30, "Fry bread", (2.0 / 37), (18.0 / 37), (1.0 / 37), (1.0 / 37), (5.0 / 37), (1.7 / 37)));
        measurementModels.add(new MeasurementModel(32, "Roll, crusty (kaiser)", (6.0 / 57), (30.0 / 57), (1.0 / 57), (1.3 / 57), (2.0 / 57), (0.3 / 57)));
        measurementModels.add(new MeasurementModel(33, "Roll, dinner, white", (2.0 / 28), (14.0 / 28), (2.0 / 28), (0.9 / 28), (2.0 / 28), (0.5 / 28)));
        repository.insertFoodMeasurement(measurementModels);
    }

    private void updateSharedPreference() {
        SharedPreferences preferences = getSharedPreferences("Preference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("IS_LOGGED_IN", true);
        editor.putString("LOGGED_EMAIL", etEmail.getText().toString().trim());
        editor.apply();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LoginActivity.this, LandingActivity.class));
        finishAffinity();
    }
}
