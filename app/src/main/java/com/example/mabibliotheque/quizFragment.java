package com.example.mabibliotheque;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mabibliotheque.quiz.quizapp;

public class quizFragment extends Fragment {
    private View quizjouer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_quiz, container, false);

        quizjouer = rootView.findViewById(R.id.imagehistoire);

        quizjouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = v.getContext();
                Intent intent = new Intent(context, quizapp.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
