package com.example.schoolquizzer.ui_components;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolquizzer.R;
import com.example.schoolquizzer.adapters.QuestionsGridAdapter;

public class DialogQuestionsGrid extends DialogFragment {
    private RecyclerView recv_grid;
    private QuestionsGridAdapter adapter;
    private int noOfQuestions;

    // Supplied to Recycler View adapter
    private QuestionsGridAdapter.OnCardClick mCallBack;

    public DialogQuestionsGrid(int noOfQuestions, QuestionsGridAdapter.OnCardClick listener) {
        this.noOfQuestions = noOfQuestions;
        mCallBack = listener;
    }





    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_questions_grid, null);
        builder.setView(view);

        recv_grid = view.findViewById(R.id.recv_grid);
        recv_grid.setHasFixedSize(false);
        GridLayoutManager layoutManager = new GridLayoutManager(requireActivity(), 5);
        recv_grid.setLayoutManager(layoutManager);
        adapter = new QuestionsGridAdapter(noOfQuestions, mCallBack);
        recv_grid.setAdapter(adapter);

        return builder.create();
    }
}
