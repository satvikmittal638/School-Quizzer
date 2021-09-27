package com.example.schoolquizzer.activities;

import static com.example.schoolquizzer.adapters.QuizzesRecyclerAdapter.KEY_QUIZ_DURATION;
import static com.example.schoolquizzer.adapters.QuizzesRecyclerAdapter.KEY_QUIZ_ID;
import static com.example.schoolquizzer.adapters.QuizzesRecyclerAdapter.KEY_QUIZ_SUBJECT;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import com.example.schoolquizzer.R;
import com.example.schoolquizzer.Utility;
import com.example.schoolquizzer.adapters.QuestionsGridAdapter;
import com.example.schoolquizzer.adapters.QuizzesRecyclerAdapter;
import com.example.schoolquizzer.api.ApiController;
import com.example.schoolquizzer.api.QuizService;
import com.example.schoolquizzer.api.StudentService;
import com.example.schoolquizzer.model.Question;
import com.example.schoolquizzer.model.Student;
import com.example.schoolquizzer.model.StudentResponse;
import com.example.schoolquizzer.ui_components.DialogQuestionsGrid;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Exam extends AppCompatActivity implements QuestionsGridAdapter.OnCardClick {
    private int questionNo = 1;
    private TextView tv_question;
    private RadioGroup optionsGroup;
    private RadioButton btn_optA, btn_optB, btn_optC, btn_optD;

    private Student currentStudent;
    private Bundle quizDetails;
    private List<Question> questionList;
    // Question Id is used as a key here
    private List<StudentResponse> studentResponseList; // to store the answers given by the user


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        currentStudent = Utility.getStudentFromPrefs(this);
        questionList = new ArrayList<>();
        quizDetails = getIntent().getBundleExtra(QuizzesRecyclerAdapter.class.getSimpleName());

        QuizService quizService = ApiController.getInstance().getQuizService();
        quizService.getQuestions(quizDetails.getLong(KEY_QUIZ_ID))
                .enqueue(new Callback<List<Question>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Question>> call, @NonNull Response<List<Question>> response) {
                        if (response.body() != null) {
                            questionList.clear();
                            questionList.addAll(response.body());

                            studentResponseList = new ArrayList<>(questionList.size());
                            makeEmptyAnswerList();
                            updateQuestionUI(); // setting the first question by default

                            getSupportActionBar().setTitle(quizDetails.getString(KEY_QUIZ_SUBJECT));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Question>> call, @NonNull Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        tv_question = findViewById(R.id.tv_question);
        optionsGroup = findViewById(R.id.optionsGroup);
        // TODO find a more optimized way
        btn_optA = findViewById(R.id.btn_optA);
        btn_optB = findViewById(R.id.btn_optB);
        btn_optC = findViewById(R.id.btn_optC);
        btn_optD = findViewById(R.id.btn_optD);

        // Getting the answers


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_exam, menu);

        // Managing the timer
        MenuItem timer = menu.findItem(R.id.timer);
        TextView timerTxtView = (TextView) MenuItemCompat.getActionView(timer);
        timerTxtView.setTextSize(20);
        timerTxtView.setPadding(20, 3, 20, 0);
        startTimer(timerTxtView, (long) quizDetails.getInt(KEY_QUIZ_DURATION) * 60 * 1000);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_show_questions:
                DialogQuestionsGrid dialogQuestionsGrid = new DialogQuestionsGrid(questionList.size(), this);// give the listener attached to this class
                dialogQuestionsGrid.show(getSupportFragmentManager(), "Skip to any question");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startTimer(TextView onTxtView, long durationInMillis) {
        // tick after every 1 sec
        CountDownTimer timer = new CountDownTimer(durationInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long timeInSec = Math.round(millisUntilFinished / 1000d);
                long min = (timeInSec - (timeInSec % 60)) / 60; // getting the exact minutes and taking the extra seconds out
                long sec = timeInSec - min * 60; // subtracting the exact sec from total time(in sec) and getting the remaining seconds
                String timeToShow = String.format(Locale.ENGLISH, "%d : %d", min, sec);
                onTxtView.setText(timeToShow);
            }

            @Override
            public void onFinish() {
                onTxtView.setText(R.string.hint_time_over);
                submitAnswers(); // auto submitting answers upon time completion
            }
        };
        timer.start();

    }

    // Supplied to the Dialog Fragment
    // Received the question Selected from the recycler adapter
    @Override
    public void onClick(int questionSelected) {
        questionNo = questionSelected;
        updateQuestionUI();
    }

    public void saveAndNext(View view) {
        saveSelectedOption();

        if (questionNo < questionList.size()) // if the q is before the last q
        {
            questionNo++;
            updateQuestionUI();
        } else {
            Toast.makeText(getApplicationContext(), "This is the last question", Toast.LENGTH_SHORT).show();
        }
    }

    public void previous(View view) {
        if (questionNo > 1) // if the q is after the 1st q
        {
            questionNo--;
            updateQuestionUI();
        } else
            Toast.makeText(getApplicationContext(), "This is the first question", Toast.LENGTH_SHORT).show();
    }

    public void submit(View view) {
        saveSelectedOption();
        submitAnswers();
    }

    /**
     * Updates the content of the textview and the radio group for traversing through multiple questions
     */
    private void updateQuestionUI() {
        Question question = questionList.get(questionNo - 1); // as index starts from 0
        tv_question.setText(question.getQuestion());
        btn_optA.setText(question.getOption1());
        btn_optB.setText(question.getOption2());
        btn_optC.setText(question.getOption3());
        btn_optD.setText(question.getOption4());


        // Setting the options of the previously answered questions
        char optSelected = studentResponseList.get(questionNo - 1).getOptionSelected();
        if (optSelected == 'N') { // N by default
            optionsGroup.clearCheck(); // for answering a fresh question
        } else {
            int selectedId = R.id.btn_optA;
            switch (optSelected) {
                case 'A':
                    selectedId = R.id.btn_optA;
                    break;
                case 'B':
                    selectedId = R.id.btn_optB;
                    break;
                case 'C':
                    selectedId = R.id.btn_optC;
                    break;
                case 'D':
                    selectedId = R.id.btn_optD;
                    break;
            }
            optionsGroup.check(selectedId);
        }
    }


    /**
     * Creates a empty list which then just needs to be updated for every answer
     */
    private void makeEmptyAnswerList() {
        // No of q = no of answers
        for (int i = 0; i < questionList.size(); i++) {
            StudentResponse response = new StudentResponse(
                    currentStudent.getSchoolClass(),
                    questionList.get(i).getId(),
                    quizDetails.getLong(KEY_QUIZ_ID),
                    currentStudent.getRollNo()
                    , 'N');
            studentResponseList.add(response);
        }
    }

    /**
     * Saves the currently selected option the response list otherwise does nothing
     */
    private void saveSelectedOption() {
        char optionSelected = 'N'; // nothing selected by default

        switch (optionsGroup.getCheckedRadioButtonId()) {
            case R.id.btn_optA:
                optionSelected = 'A';
                break;
            case R.id.btn_optB:
                optionSelected = 'B';
                break;
            case R.id.btn_optC:
                optionSelected = 'C';
                break;
            case R.id.btn_optD:
                optionSelected = 'D';
                break;
        }
        if (optionSelected != 'N') {
            StudentResponse response = studentResponseList.get(questionNo - 1);
            response.setOptionSelected(optionSelected);
        }
    }

    private void submitAnswers() {
        StudentService service = ApiController.getInstance().getStudentService();
        service.uploadAnswers(studentResponseList).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Test submitted successfully", Toast.LENGTH_SHORT).show();
                    onBackPressed(); // throws back to the home screen
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
