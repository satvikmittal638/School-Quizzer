package com.example.schoolquizzer.api;

import com.example.schoolquizzer.model.Question;
import com.example.schoolquizzer.model.Quiz;
import com.example.schoolquizzer.model.QuizAnalysis;
import com.example.schoolquizzer.model.QuizzesStudent;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface QuizService {
    @GET("/quiz/details/{quizId}")
    Call<Quiz> getQuizDetails(@Path("quizId") long quizId);

    @GET("/quiz/show/{roll}")
    Call<Map<String, List<Quiz>>> getQuizzes(@Path("roll") long roll);

    @GET("/quiz/{quizId}")
    Call<List<Question>> getQuestions(@Path("quizId") long quizId);

    @GET("/quiz/result/{quizId}/{roll}")
    Call<QuizzesStudent> getResult(@Path("quizId") long quizId, @Path("roll") long roll);

    @GET("/quiz/result/{quizId}")
    Call<QuizAnalysis> getQuizAnalysis(@Path("quizId") long quizId);
}
