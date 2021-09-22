package com.example.schoolquizzer.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolquizzer.R;
import com.example.schoolquizzer.api.ApiController;
import com.example.schoolquizzer.api.StudentService;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    public static String SHARED_PREFS_CREDENTIALS = "credentials", KEY_ROLL = "roll", KEY_PWD = "pwd";
    private TextInputEditText et_roll, et_pwd;
    private ApiController controller;
    private StudentService studentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        controller = ApiController.getInstance();
        studentService = controller.getStudentService();
        // Auto login the user if the credentials are already saved on the device
        SharedPreferences sharedPrefs = getSharedPreferences(SHARED_PREFS_CREDENTIALS, MODE_PRIVATE);
        if (sharedPrefs.contains(KEY_ROLL) && sharedPrefs.contains(KEY_PWD)) {
            loginUser(sharedPrefs.getLong(KEY_ROLL, 0), sharedPrefs.getString(KEY_PWD, null));
        }


        et_roll = findViewById(R.id.txtEdit_roll);
        et_pwd = findViewById(R.id.txtEdit_pwd);


    }

    public void login(View view) {
        long roll = Long.parseLong(et_roll.getText().toString());
        String pwd = et_pwd.getText().toString();
        loginUser(roll, pwd);
    }

    private void loginUser(long roll, String pwd) {
        // verifying credentials
        studentService.loginStudent(roll, pwd).enqueue(new Callback<Map<String, Boolean>>() {
            @Override
            public void onResponse(@NonNull Call<Map<String, Boolean>> call, @NonNull Response<Map<String, Boolean>> response) {
                Map<String, Boolean> body = response.body();
                if (body != null && body.get("success")) {
                    saveCredentials(roll, pwd);
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this, HomeScreen.class));
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), "Can't login", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<String, Boolean>> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveCredentials(long roll, String password) {
        SharedPreferences sharedPrefs = getSharedPreferences(SHARED_PREFS_CREDENTIALS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putLong(KEY_ROLL, roll);
        editor.putString(KEY_PWD, password);
        editor.apply(); // runs asynchronously
    }
}