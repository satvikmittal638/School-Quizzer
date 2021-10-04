package com.example.schoolquizzer.activities;

import static com.example.schoolquizzer.activities.Login.SHARED_PREFS_DETAILS;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolquizzer.R;
import com.example.schoolquizzer.Utility;
import com.example.schoolquizzer.adapters.QuizzesRecyclerAdapter;
import com.example.schoolquizzer.api.ApiController;
import com.example.schoolquizzer.model.Quiz;
import com.example.schoolquizzer.model.Student;
import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeScreen extends AppCompatActivity {
    private TabLayout tabLayoutQuizzes;
    private RecyclerView recv_quizzes;
    private QuizzesRecyclerAdapter quizzesAdapter;

    private String quizType = "upcoming"; //  to be sent to the API for getting the quizzes
    private long roll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Student student = Utility.getStudentFromPrefs(this);
        if (student != null)
            roll = student.getRollNo();


        tabLayoutQuizzes = findViewById(R.id.tabLay_quizzes);
        recv_quizzes = findViewById(R.id.recv_quizzes);
        recv_quizzes.setLayoutManager(new LinearLayoutManager(this));
        recv_quizzes.setHasFixedSize(false);


        // Fetching the quizzes and handling tab clicks
        ApiController.getInstance().getQuizService()
                .getQuizzes(roll)
                .enqueue(new Callback<Map<String, List<Quiz>>>() {
                    @Override
                    public void onResponse(@NonNull Call<Map<String, List<Quiz>>> call, @NonNull Response<Map<String, List<Quiz>>> response) {

                        if (response.body() != null) {
                            // TODO Stop progress bar

                            tabLayoutQuizzes.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                @Override
                                public void onTabSelected(TabLayout.Tab tab) {
                                    // Incorporating all quizzes in one recyclerview only
                                    switch (tab.getPosition()) {
                                        case 0:
                                            quizType = "missed";
                                            break;
                                        case 1:
                                            quizType = "attempted";
                                            break;
                                        case 2:
                                            quizType = "live";
                                            break;
                                        case 3:
                                            quizType = "upcoming";
                                            break;
                                    }
                                    quizzesAdapter = new QuizzesRecyclerAdapter(HomeScreen.this, quizType);
                                    recv_quizzes.setAdapter(quizzesAdapter);
                                    quizzesAdapter.setQuizzes(response.body().get(quizType));
                                }

                                @Override
                                public void onTabUnselected(TabLayout.Tab tab) {
                                    // Do nothing
                                }

                                @Override
                                public void onTabReselected(TabLayout.Tab tab) {
                                    // Do nothing

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Map<String, List<Quiz>>> call, @NonNull Throwable t) {

                    }
                });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_homescreen, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemSelected = item.getItemId();
        switch (itemSelected) {
            case R.id.item_logout:
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                dialogBuilder.setMessage(R.string.warn_logout);
                dialogBuilder.setTitle(R.string.warnHead_logout);
                dialogBuilder.setPositiveButton(R.string.yes, (dialog, which) -> {
                    SharedPreferences sharedPrefs = getSharedPreferences(SHARED_PREFS_DETAILS, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    editor.clear(); // cleaning all the student data stored
                    editor.apply();
                    startActivity(new Intent(this, Login.class));
                    finish();
                });
                dialogBuilder.setNegativeButton(R.string.no, null); // do nothing

                dialogBuilder.create().show();


        }
        return super.onOptionsItemSelected(item);
    }
}