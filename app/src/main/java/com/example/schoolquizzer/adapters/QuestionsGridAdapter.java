package com.example.schoolquizzer.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolquizzer.R;

public class QuestionsGridAdapter extends RecyclerView.Adapter<QuestionsGridAdapter.viewHolder> {
    private int noOfQuestions;
    private OnCardClick mCallBack;
    public QuestionsGridAdapter(int noOfQuestions, OnCardClick listener) {
        this.noOfQuestions = noOfQuestions;
        this.mCallBack = listener;
    }

    @NonNull
    @Override
    public QuestionsGridAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_grid_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionsGridAdapter.viewHolder holder, int position) {
        // populating the cards with question numbers
        String questionNo = "Q."+(position+1);
            holder.tv_qno.setText(questionNo);
        holder.card_qno.setOnClickListener(v -> {
            mCallBack.onClick(position+1); // giving data to the dialog fragment
        });
    }

    @Override
    public int getItemCount() {
        return noOfQuestions;
    }

    static class viewHolder extends RecyclerView.ViewHolder {
        TextView tv_qno;
        CardView card_qno;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tv_qno = itemView.findViewById(R.id.tv_qno);
            card_qno = itemView.findViewById(R.id.card_qno);
        }
    }

    public interface OnCardClick{
        void onClick(int questionSelected);
    }
}
