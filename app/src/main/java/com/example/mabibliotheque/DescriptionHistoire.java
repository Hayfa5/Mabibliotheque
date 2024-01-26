package com.example.mabibliotheque;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Locale;

public class DescriptionHistoire extends AppCompatActivity {

    Button mSpeakBtn, mStopBtn;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;
    TextToSpeech mTTS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_histoire);
        mSpeakBtn = findViewById(R.id.speakBtn);
        mStopBtn = findViewById(R.id.stopBtn);
        TextView textViewNom = findViewById(R.id.Nomhistoire);
        TextView textViewdescription = findViewById(R.id.Descriptionhistoire);
        ImageView imageview = findViewById(R.id.imagehistoire);
        Button telechargerButton = findViewById(R.id.telecharger);
        Intent intent = getIntent();
        String Nom = intent.getStringExtra("Nom");
        String description = intent.getStringExtra("description");
        String URLIMAGe = intent.getStringExtra("URLIMAGe");
        String URL = intent.getStringExtra("URL");
        textViewNom.setText(Nom);
        textViewdescription.setText(description);
        Glide.with(DescriptionHistoire.this)
                .load(URLIMAGe)
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(imageview);

        mTTS = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR){
                    mTTS.setLanguage(Locale.FRANCE);
                }
                else {
                    Toast.makeText(DescriptionHistoire.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mSpeakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get text from edit text
                String toSpeak = textViewdescription.getText().toString().trim();
                if (toSpeak.equals("")){
                    Toast.makeText(DescriptionHistoire.this, "Please enter text...", Toast.LENGTH_SHORT).show();
                }
                else {
                    mTTS.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
        mStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTTS.isSpeaking()){
                    mTTS.stop();
                }
                else {
                    Toast.makeText(DescriptionHistoire.this, "Not speaking", Toast.LENGTH_SHORT).show();
                }
            }
        });
           telechargerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startDownloading(URL);
            }
        });
    }
    public void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (DescriptionHistoire.this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
            }
        }
    }

    @Override
    protected void onPause() {
        if (mTTS != null || mTTS.isSpeaking()){
            //if it is speaking then stop
            mTTS.stop();
            //mTTS.shutdown();
        }
        super.onPause();
    }


    private void downloadPdf(Context context, String pdfUrl) {
        if (pdfUrl != null) {
            Uri uri = Uri.parse(pdfUrl);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    requestStoragePermission();
                    return;
                }
            }

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(pdfUrl));
            request.setTitle("Downloading PDF");
            request.setDescription("Please wait while the PDF is being downloaded...");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "your_pdf_filename.pdf");

            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            downloadManager.enqueue(request);
        } else {
            Toast.makeText(context, "URL is null", Toast.LENGTH_SHORT).show();
        }
    }

    public void startDownloading(String pdfUrl) {
        if (pdfUrl != null) {
            if (ContextCompat.checkSelfPermission(DescriptionHistoire.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Uri uri = Uri.parse(pdfUrl);
                downloadPdf(DescriptionHistoire.this,pdfUrl);
            } else {
                ActivityCompat.requestPermissions((Activity) DescriptionHistoire.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
            }
        } else {
            Toast.makeText(DescriptionHistoire.this, "L'URL du PDF est nulle.", Toast.LENGTH_SHORT).show();
        }
    }


        }



