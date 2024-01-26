package com.example.mabibliotheque.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mabibliotheque.MainActivity;
import com.example.mabibliotheque.R;
import com.google.android.material.card.MaterialCardView;

public class ResultActivity extends AppCompatActivity {

    MaterialCardView home;
    TextView correctte, fauxx, resultinfo, resultscore;
    ImageView resultImage;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        home = findViewById(R.id.returnHome);
        correctte = findViewById(R.id.correctScore);
        fauxx = findViewById(R.id.wrongScore);
        resultinfo = findViewById(R.id.resultInfo);
        resultscore = findViewById(R.id.resultScore);
        resultImage = findViewById(R.id.resultImage);
        mediaPlayer = MediaPlayer.create(this, R.raw.bravo);

        int correcte = getIntent().getIntExtra("correcte", 0);
        int faux = getIntent().getIntExtra("faux", 0);
        int score = correcte * 5;

        correctte.setText("" + correcte);
        fauxx.setText("" + faux);
        resultscore.setText("" + score);

        if (correcte >= 0 && correcte <= 2) {
            resultinfo.setText("Tu dois refaire le test");
            resultImage.setImageResource(R.drawable.ic_sad);

        } else if (correcte >= 3 && correcte <= 5) {
            resultinfo.setText("Tu dois essayer un peu plus");
            resultImage.setImageResource(R.drawable.ic_neutral);

        } else if (correcte >= 6 && correcte <= 8) {
            resultinfo.setText("Tu es bien");
            resultImage.setImageResource(R.drawable.ic_smile);
            playBravoSound();
        } else {
            resultinfo.setText("Tu es très bien félicitations");
            resultImage.setImageResource(R.drawable.ic_smile);
            playBravoSound();
        }

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResultActivity.this, quizapp.class));
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(ResultActivity.this, quizapp.class));
        finish();
    }

    private void playBravoSound() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        // Libérer les ressources de l'objet MediaPlayer
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        super.onDestroy();
    }
}
