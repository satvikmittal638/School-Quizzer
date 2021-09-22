package com.example.schoolquizzer.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolquizzer.R;
import com.example.schoolquizzer.activities.Exam;
import com.example.schoolquizzer.model.Quiz;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class QuizzesRecyclerAdapter extends RecyclerView.Adapter<QuizzesRecyclerAdapter.Vholder> {
    private final Context context;
    private List<Quiz> quizzes;
    public static final String KEY_QUIZ_ID="quizId",KEY_QUIZ_DURATION="quizDuration", KEY_QUIZ_SUBJECT = "quizSubject";

    public QuizzesRecyclerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    public QuizzesRecyclerAdapter.Vholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_quiz, parent, false);
        return new Vholder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull QuizzesRecyclerAdapter.Vholder holder, int position) {
        Quiz quiz = quizzes.get(position);

        holder.tv_subject.setText(quiz.getSubject());
        holder.tv_date.setText(quiz.getDateTimeFrom().format(DateTimeFormatter.ISO_LOCAL_DATE));
        holder.tv_from.setText(quiz.getDateTimeFrom().format(DateTimeFormatter.ISO_LOCAL_TIME));
        holder.tv_to.setText(quiz.getDateTimeTo().format(DateTimeFormatter.ISO_LOCAL_TIME));
        holder.btn_start.setOnClickListener(v -> {
            Toast.makeText(context, "Quiz started", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, Exam.class);
            Bundle requiredQuizDetails = new Bundle();
            requiredQuizDetails.putLong(KEY_QUIZ_ID, quiz.getId());
            requiredQuizDetails.putInt(KEY_QUIZ_DURATION, quiz.getDurationInMin());
            requiredQuizDetails.putString(KEY_QUIZ_SUBJECT, quiz.getSubject());
            intent.putExtra(getClass().getSimpleName(), requiredQuizDetails);
            context.startActivity(intent);
        });

       // TODO improve time condition
        LocalDateTime now = LocalDateTime.now();
        // if the current time lies between the start and end time, then let user start the quiz
        if (!(now.isAfter(quiz.getDateTimeFrom()) && now.isBefore(quiz.getDateTimeTo()))) {
            // if the time has passed, you can't attempt the quiz
            holder.btn_start.setClickable(false);
        }

    }

    @Override
    public int getItemCount() {
        if (quizzes != null) {
            return quizzes.size();
        }
        return 0;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
        notifyDataSetChanged();
    }


    static class Vholder extends RecyclerView.ViewHolder {
//        CardView card_quiz;
        TextView tv_subject, tv_date, tv_from, tv_to;
        Button btn_start;

        public Vholder(@NonNull View itemView) {
            super(itemView);
//            card_quiz = itemView.findViewById(R.id.card_quiz);
            tv_subject = itemView.findViewById(R.id.tv_subject);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_from = itemView.findViewById(R.id.tv_from);
            tv_to = itemView.findViewById(R.id.tv_to);
            btn_start = itemView.findViewById(R.id.btn_start);
        }
    }
}
