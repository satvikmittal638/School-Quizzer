package com.example.schoolquizzer.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiController {
    public static String baseUrl = "http://192.168.29.186:9090";
    private static ApiController controller;
    private static Retrofit retrofit;

    private ApiController() {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized ApiController getInstance() {
        if (controller == null) {
            controller = new ApiController();
        }
        return controller;
    }

    public StudentService getStudentService() {
        return retrofit.create(StudentService.class);
    }

    public QuizService getQuizService() {
        return retrofit.create(QuizService.class);
    }
}



