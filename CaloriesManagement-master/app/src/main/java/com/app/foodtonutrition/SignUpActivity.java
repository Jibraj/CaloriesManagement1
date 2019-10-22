package com.app.foodtonutrition;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.foodtonutrition.room.model.User;
import com.app.foodtonutrition.room.repository.Repository;
import com.app.foodtonutrition.R;

public class SignUpActivity extends AppCompatActivity {

    private EditText etnm;
    private EditText eta;
    private EditText etWeight;
    private EditText etEmail;
    private EditText etpsw;
    private EditText etcpsw;
    private Spinner spnGender;
    private ProgressBar pdLoading;
    private Repository repository;
    
    private int age = 0, weight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        etnm = findViewById(R.id.etname);
        eta = findViewById(R.id.etAge);
        etWeight = findViewById(R.id.etWeight);
        spnGender = findViewById(R.id.spinnerGender);
        etEmail = findViewById(R.id.etEmail);
        etpsw = findViewById(R.id.etPassword);
        etcpsw = findViewById(R.id.etCPassword);
        pdLoading = findViewById(R.id.pdLoading);
        repository = new Repository(SignUpActivity.this);
        Button btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdLoading.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pdLoading.setVisibility(View.GONE);
                        if (!TextUtils.isEmpty(eta.getText().toString()))
                            age = Integer.parseInt(eta.getText().toString());
                        if (!TextUtils.isEmpty(etWeight.getText().toString()))
                            weight = Integer.parseInt(etWeight.getText().toString());
                        if (validation()) {
                            UserCheck userCheck = new UserCheck();
                            userCheck.execute();
                        }
                    }
                }, 2000);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private boolean validation() {
        if (TextUtils.isEmpty(etnm.getText().toString())) {
            etnm.setError("Name is required");
            return false;
        } else if (TextUtils.isEmpty(etEmail.getText().toString())) {
            etEmail.setError("Email is required");
            return false;
        } else if (Patterns.EMAIL_ADDRESS.matcher(etnm.getText().toString()).matches()) {
            etEmail.setError("Enter valid email");
            return false;
        } else if (TextUtils.isEmpty(etpsw.getText().toString())) {
            etpsw.setError("Password cannot be empty");
            return false;
        } else if (!etpsw.getText().toString().equals(etcpsw.getText().toString())) {
            etpsw.setError("Password doesnot match");
            return false;
        } else
            return true;
    }

    @SuppressLint("StaticFieldLeak")
    private class UserCheck extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... urls) {
            return repository.checkUserExistance(etEmail.getText().toString().trim());
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (result > 0) {
                Toast.makeText(SignUpActivity.this, "Email address already used.", Toast.LENGTH_SHORT).show();
            } else {
                final User user = new User(
                        etnm.getText().toString().trim(),
                        spnGender.getSelectedItem().toString(),
                        age,
                        weight,
                        etEmail.getText().toString().trim(),
                        etpsw.getText().toString().trim()
                );
                repository.insertUser(user);
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                finish();
                Toast.makeText(SignUpActivity.this, "Sign Up Success", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

