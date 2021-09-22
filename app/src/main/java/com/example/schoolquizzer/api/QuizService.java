package com.example.schoolquizzer.api;

import com.example.schoolquizzer.model.Question;
import com.example.schoolquizzer.model.Quiz;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface QuizService {
    @GET("/quiz/show/{roll}/{type}")
    Call<List<Quiz>> getQuizzes(@Path("roll") long roll, @Path("type") String type);

    @GET("/quiz/{quizId}")
    Call<List<Question>> getQuestions(@Path("quizId") long quizId);
}
