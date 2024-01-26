package com.example.mabibliotheque.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.example.mabibliotheque.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class basicquiz extends AppCompatActivity {

    TextView quiztext, A, B, C, D;
    List<QuesitionsItem> quesitionsItems;
    int currentQuestions = 0;
    int correcte = 0, faux = 0;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        quiztext = findViewById(R.id.quizText);
        A = findViewById(R.id.A);
        B = findViewById(R.id.B);
        C = findViewById(R.id.C);
        D = findViewById(R.id.D);

        loadAllQuestions();
        Collections.shuffle(quesitionsItems);
        setQuestionScreen(currentQuestions);

        A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quesitionsItems.get(currentQuestions).getAnswer1().equals(quesitionsItems.get(currentQuestions).getCorrect())) {
                    correcte++;
                    A.setBackgroundResource(R.color.green);
                    A.setTextColor(getResources().getColor(R.color.white));
                } else {
                    faux++;
                    A.setBackgroundResource(R.color.red);
                    A.setTextColor(getResources().getColor(R.color.white));
                }

                if (currentQuestions < quesitionsItems.size()-1) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            currentQuestions++;
                            setQuestionScreen(currentQuestions);
                            A.setBackgroundResource(R.color.white);
                            A.setTextColor(getResources().getColor(R.color.text_secondery_color));
                        }
                    }, 500);
                } else {
                    Intent intent = new Intent(basicquiz.this, ResultActivity.class);
                    intent.putExtra("correcte", correcte);
                    intent.putExtra("faux", faux);
                    startActivity(intent);
                    finish();
                }
            }
        });

        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quesitionsItems.get(currentQuestions).getAnswer2().equals(quesitionsItems.get(currentQuestions).getCorrect())) {
                    correcte++;
                    B.setBackgroundResource(R.color.green);
                    B.setTextColor(getResources().getColor(R.color.white));
                } else {
                    faux++;
                    B.setBackgroundResource(R.color.red);
                    B.setTextColor(getResources().getColor(R.color.white));
                }

                if (currentQuestions < quesitionsItems.size()-1) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            currentQuestions++;
                            setQuestionScreen(currentQuestions);
                            B.setBackgroundResource(R.color.white);
                            B.setTextColor(getResources().getColor(R.color.text_secondery_color));
                        }
                    }, 500);
                } else {
                    Intent intent = new Intent(basicquiz.this, ResultActivity.class);
                    intent.putExtra("correcte", correcte);
                    intent.putExtra("faux", faux);
                    startActivity(intent);
                    finish();
                }
            }
        });

        C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quesitionsItems.get(currentQuestions).getAnswer3().equals(quesitionsItems.get(currentQuestions).getCorrect())) {
                    correcte++;
                    C.setBackgroundResource(R.color.green);
                    C.setTextColor(getResources().getColor(R.color.white));
                } else {
                    faux++;
                    C.setBackgroundResource(R.color.red);
                    C.setTextColor(getResources().getColor(R.color.white));
                }

                if (currentQuestions < quesitionsItems.size()-1) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            currentQuestions++;
                            setQuestionScreen(currentQuestions);
                            C.setBackgroundResource(R.color.white);
                            C.setTextColor(getResources().getColor(R.color.text_secondery_color));
                        }
                    }, 500);
                } else {
                    Intent intent = new Intent(basicquiz.this, ResultActivity.class);
                    intent.putExtra("correcte", correcte);
                    intent.putExtra("faux", faux);
                    startActivity(intent);
                    finish();
                }
            }
        });

        D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quesitionsItems.get(currentQuestions).getAnswer4().equals(quesitionsItems.get(currentQuestions).getCorrect())) {
                    correcte++;
                    D.setBackgroundResource(R.color.green);
                    D.setTextColor(getResources().getColor(R.color.white));
                } else {
                    faux++;
                    D.setBackgroundResource(R.color.red);
                    D.setTextColor(getResources().getColor(R.color.white));
                }

                if (currentQuestions < quesitionsItems.size()-1) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            currentQuestions++;
                            setQuestionScreen(currentQuestions);
                            D.setBackgroundResource(R.color.white);
                            D.setTextColor(getResources().getColor(R.color.text_secondery_color));
                        }
                    }, 500);
                } else {
                    Intent intent = new Intent(basicquiz.this, ResultActivity.class);
                    intent.putExtra("correcte", correcte);
                    intent.putExtra("faux", faux);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private void setQuestionScreen(int currentQuestions) {
        quiztext.setText(quesitionsItems.get(currentQuestions).getQuestions());
        A.setText(quesitionsItems.get(currentQuestions).getAnswer1());
        B.setText(quesitionsItems.get(currentQuestions).getAnswer2());
        C.setText(quesitionsItems.get(currentQuestions).getAnswer3());
        D.setText(quesitionsItems.get(currentQuestions).getAnswer4());
    }

    private void loadAllQuestions() {
        quesitionsItems = new ArrayList<>();
        String jsonquiz = loadJsonFromAsset("easyquestions.json");
        try {
            JSONObject jsonObject = new JSONObject(jsonquiz);
            JSONArray questions = jsonObject.getJSONArray("easyquestions");
            for (int i = 0; i < questions.length(); i++) {
                JSONObject question = questions.getJSONObject(i);
            // creation de table (table d'objet de type quesitionsItems  )
            // chaque foix on ajout objet a partir de fichier json
                String questionsString = question.getString("question");
                String correctA = question.getString("1");
                String correctB = question.getString("2");
                String correctC = question.getString("3");
                String correctD = question.getString("4");
                String correctF = question.getString("correcte");

                quesitionsItems.add(new QuesitionsItem(questionsString, correctA, correctB, correctC, correctD, correctF));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String loadJsonFromAsset(String s) {
        String json = "";
        try {
            InputStream inputStream = getAssets().open(s);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public void onBackPressed() {
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(basicquiz.this);
        materialAlertDialogBuilder.setTitle(R.string.app_name);
        materialAlertDialogBuilder.setMessage("Etes-vous sûr de vouloir quitter le quiz?");
        materialAlertDialogBuilder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        materialAlertDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(basicquiz.this, quizapp.class));
                finish();
            }
        });
        materialAlertDialogBuilder.show();
    }

}