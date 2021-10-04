package com.example.schoolquizzer.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolquizzer.R;
import com.example.schoolquizzer.api.ApiController;
import com.example.schoolquizzer.api.StudentService;
import com.example.schoolquizzer.model.Student;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    public static String SHARED_PREFS_DETAILS = "studentDetails", KEY_STUDENT = "student";
    private TextInputEditText et_roll, et_pwd;
    private StudentService studentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        studentService = ApiController.getInstance().getStudentService();

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
        studentService.loginStudent(roll, pwd).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(@NonNull Call<Student> call, @NonNull Response<Student> response) {

                Student student = response.body();
                if (student != null) { // if details are correct
//                    student.setPassword(pwd); // not returned by the API
                    saveStudentDetails(student); // saving all the student details(excluding the password)
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this, HomeScreen.class));
                    finish();
                }

            }

            @Override
            public void onFailure(@NonNull Call<Student> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * Saves the student's details in the shared preferences only after a successful login
     * By serializing it into a JSON
     *
     * @param validStudent Student object to be saved
     */
    private void saveStudentDetails(Student validStudent) {
        SharedPreferences sharedPrefs = getSharedPreferences(SHARED_PREFS_DETAILS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        String studentJson = new Gson().toJson(validStudent);
        editor.putString(KEY_STUDENT, studentJson); // saving all details JSON
        editor.apply(); // runs asynchronously
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_contact_call:
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + getString(R.string.administrator_number)));
                startActivity(callIntent);
                break;

            case R.id.item_contact_mail:
                sendEmailToAdministrator(getString(R.string.contactSubject));
                break;
            case R.id.item_report:
                sendEmailToAdministrator(getString(R.string.reportSubject));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // Menu Functions

    private void sendEmailToAdministrator(String subject) {
        String mailTo = "mailto:" + getString(R.string.administrator_mail) +
                "?&subject=" + Uri.encode(subject);
        Intent mailIntent = new Intent(Intent.ACTION_VIEW);
        mailIntent.setData(Uri.parse(mailTo));
        startActivity(mailIntent);
    }

    // Ends Here
}