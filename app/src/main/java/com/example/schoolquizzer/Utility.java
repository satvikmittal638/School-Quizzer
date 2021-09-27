package com.example.schoolquizzer;

import static android.content.Context.MODE_PRIVATE;
import static com.example.schoolquizzer.activities.Login.KEY_STUDENT;
import static com.example.schoolquizzer.activities.Login.SHARED_PREFS_DETAILS;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.schoolquizzer.model.Student;
import com.google.gson.Gson;

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
