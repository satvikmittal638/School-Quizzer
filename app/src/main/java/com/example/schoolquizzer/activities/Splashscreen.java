package com.example.schoolquizzer.activities;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolquizzer.R;
import com.example.schoolquizzer.Utility;
import com.example.schoolquizzer.model.Student;

public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        // Auto login the user if the credentials are already saved on the device
        Student student = Utility.getStudentFromPrefs(this);
        if (student != null) {
            startActivity(new Intent(this, HomeScreen.class));
        }else{
            startActivity(new Intent(this, Login.class));
        }
        finish();
    }
}