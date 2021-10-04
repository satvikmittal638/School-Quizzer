package com.example.schoolquizzer.activities;

import static com.example.schoolquizzer.adapters.QuizzesRecyclerAdapter.KEY_QUIZ_ID;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolquizzer.R;
import com.example.schoolquizzer.Utility;
import com.example.schoolquizzer.adapters.LeaderboardAdapter;
import com.example.schoolquizzer.api.ApiController;
import com.example.schoolquizzer.api.QuizService;
import com.example.schoolquizzer.model.Quiz;
import com.example.schoolquizzer.model.QuizAnalysis;
import com.example.schoolquizzer.model.QuizzesStudent;
import com.example.schoolquizzer.model.Student;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Result extends AppCompatActivity {
    private long quizId;
    private Student student;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        quizId = getIntent().getLongExtra(KEY_QUIZ_ID, 1);
        student = Utility.getStudentFromPrefs(this);
        QuizService quizService = ApiController.getInstance().getQuizService();

        // Getting the quiz details
        quizService.getQuizDetails(quizId)
                .enqueue(new Callback<Quiz>() {
                    @Override
                    public void onResponse(@NonNull Call<Quiz> call, @NonNull Response<Quiz> response) {
                        Quiz quiz = response.body();
                        if (quiz != null) {
                            String title = String.format(Locale.ENGLISH, "%s (%d)", quiz.getSubject(), quiz.getMaxMarks());
                            if (getSupportActionBar() != null)
                                getSupportActionBar().setTitle(title);

                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Quiz> call, @NonNull Throwable t) {
                        // TODO notify user of any failures
                    }
                });


        // Getting the student's result in the form of QuizStudent relation
        // TODO prepare the student result with question-wise analysis
        quizService.getResult(quizId, student.getRollNo())
                .enqueue(new Callback<QuizzesStudent>() {
                    @Override
                    public void onResponse(@NonNull Call<QuizzesStudent> call, @NonNull Response<QuizzesStudent> response) {

                    }

                    @Override
                    public void onFailure(@NonNull Call<QuizzesStudent> call, @NonNull Throwable t) {

                    }
                });


        // Managing and displaying the quiz analysis
        quizService.getQuizAnalysis(quizId)
                .enqueue(new Callback<QuizAnalysis>() {
                    @Override
                    public void onResponse(@NonNull Call<QuizAnalysis> call, @NonNull Response<QuizAnalysis> response) {
                        QuizAnalysis analysis = response.body();
                        if (analysis != null) {
                            plotBarGraph(analysis.getMarksFrequencyTable());
                            manageLeaderBoard(analysis.getLeaderBoard());
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<QuizAnalysis> call, @NonNull Throwable t) {
                        // TODO notify user
                    }
                });


    }

    /**
     * Populates the recyclerview to look like a leaderboard
     *
     * @param leaderBoard List of top students in the leaderboard
     */
    private void manageLeaderBoard(List<Student> leaderBoard) {
        // Managing the leaderboard
        RecyclerView recv_leaderboard = findViewById(R.id.recv_leaderboard);
        recv_leaderboard.setHasFixedSize(false);
        recv_leaderboard.setLayoutManager(new LinearLayoutManager(Result.this));
        LeaderboardAdapter leaderboardAdapter = new LeaderboardAdapter(Result.this, leaderBoard);
        recv_leaderboard.setAdapter(leaderboardAdapter);
    }

    /**
     * Plots the bar graph on the bar chart for the given frequency distribution
     */
    private void plotBarGraph(long[][] frequencyTable) {
        BarChart barMarksFreq = findViewById(R.id.barchart_marks_frequency);

        List<BarEntry> info = new ArrayList<>();
        for (long[] record : frequencyTable)
            info.add(new BarEntry(record[0], record[1])); // 0 is marks and 1 is frequency


        BarDataSet barDataSet = new BarDataSet(info, getString(R.string.label_num_students));
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(20f);
        barDataSet.setBarBorderWidth(1f);

        BarData barData = new BarData(barDataSet);
        barMarksFreq.setData(barData);
        barData.setValueTextSize(20f);

        // adjusting additional properties
        barMarksFreq.getDescription().setText("");
        barMarksFreq.setFitBars(true);
        barMarksFreq.animateY(500);
        barMarksFreq.getXAxis().setGranularity(0.5f);

    }

}