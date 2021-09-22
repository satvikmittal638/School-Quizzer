package com.example.schoolquizzer.api;

import com.example.schoolquizzer.model.StudentResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface StudentService {
    @POST("/student/login")
    @FormUrlEncoded
    Call<Map<String, Boolean>> loginStudent(@Field("roll") long roll, @Field("password") String password);

    @POST("/student/upload")
    Call<Void> uploadAnswers(@Body List<StudentResponse> responses);
}
