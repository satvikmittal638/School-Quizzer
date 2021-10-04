package com.example.schoolquizzer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolquizzer.R;
import com.example.schoolquizzer.model.Student;

import java.util.List;
import java.util.Locale;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.viewHolder> {
    private final Context context;
    private final List<Student> students;

    public LeaderboardAdapter(Context context, List<Student> students) {
        this.context = context;
        this.students = students;
    }

    @NonNull
    @Override
    public LeaderboardAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_leaderboard_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardAdapter.viewHolder holder, int position) {
        Student student = students.get(position);
        String classAndSection = String.format(Locale.ENGLISH, "%s-%s", student.getSchoolClass(), student.getSchoolSection());


        holder.tv_position.setText(String.valueOf(position + 1)); // index starts from 0
        holder.tv_name.setText(student.getName());
        holder.tv_classAndSection.setText(classAndSection);
    }

    @Override
    public int getItemCount() {
        if (students != null)
            return students.size();
        return 0;
    }

    static class viewHolder extends RecyclerView.ViewHolder {
        TextView tv_position, tv_name, tv_classAndSection;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tv_position = itemView.findViewById(R.id.tv_position);
            tv_name = itemView.findViewById(R.id.tv_student_name);
            tv_classAndSection = itemView.findViewById(R.id.tv_student_classAndSection);
        }
    }
}
