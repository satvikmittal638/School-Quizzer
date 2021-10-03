package com.example.schoolquizzer;

import static android.content.Context.MODE_PRIVATE;
import static com.example.schoolquizzer.activities.Login.KEY_STUDENT;
import static com.example.schoolquizzer.activities.Login.SHARED_PREFS_DETAILS;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.schoolquizzer.activities.HomeScreen;
import com.example.schoolquizzer.activities.Login;
import com.example.schoolquizzer.api.ApiController;
import com.example.schoolquizzer.api.StudentService;
import com.example.schoolquizzer.model.Student;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Utility {
    /**
     * Deserializes JSON the stored in the shared preferences
     * @return Logged in Student object
     */
    public static Student getStudentFromPrefs(Context context) {
        SharedPreferences sharedPrefs = context.getSharedPreferences(SHARED_PREFS_DETAILS, MODE_PRIVATE);
        if (sharedPrefs.contains(KEY_STUDENT)) {
            return new Gson().fromJson(sharedPrefs.getString(KEY_STUDENT, ""), Student.class);
        }
        return null;
    }



}
