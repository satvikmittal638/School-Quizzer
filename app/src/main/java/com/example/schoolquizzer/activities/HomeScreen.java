package com.example.schoolquizzer.activities;

import static com.example.schoolquizzer.activities.Login.SHARED_PREFS_DETAILS;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolquizzer.R;
import com.example.schoolquizzer.Utility;
import com.example.schoolquizzer.adapters.QuizzesRecyclerAdapter;
import com.example.schoolquizzer.api.ApiController;
import com.example.schoolquizzer.model.Quiz;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

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
        roll = Utility.getStudentFromPrefs(this).getRollNo();

        tabLayoutQuizzes = findViewById(R.id.tabLay_quizzes);
        recv_quizzes = findViewById(R.id.recv_quizzes);
        recv_quizzes.setLayoutManager(new LinearLayoutManager(this));
        recv_quizzes.setHasFixedSize(false);
        quizzesAdapter = new QuizzesRecyclerAdapter(this);
        recv_quizzes.setAdapter(quizzesAdapter);

        tabLayoutQuizzes.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // TODO show quizzes accordingly in the same recycler view
                switch (tab.getPosition()) {
                    case 0:
                        quizType = "missed";
                        break;
                    case 1:
                        quizType = "attempted";
                        break;
                    case 2:
                        quizType = "upcoming";
                        break;
                }

                // Fetching the quizzes
                ApiController.getInstance().getQuizService()
                        .getQuizzes(roll, quizType)
                        .enqueue(new Callback<List<Quiz>>() {
                            @Override
                            public void onResponse(@NonNull Call<List<Quiz>> call, @NonNull Response<List<Quiz>> response) {
                                if (response.body() != null) {
                                    quizzesAdapter.setQuizzes(response.body());
//                                    Log.d("quizzerBaba", "onResponse: " + response.body().get(0).getSubject());
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<List<Quiz>> call, @NonNull Throwable t) {
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.d("quizzerBaba", "onFailure: " + t);
                            }
                        });

            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
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
        switch (itemSelected){
            case R.id.item_logout:
                SharedPreferences sharedPrefs = getSharedPreferences(SHARED_PREFS_DETAILS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.clear(); // cleaning all the student data stored
                editor.apply();
                startActivity(new Intent(this, Login.class));
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}