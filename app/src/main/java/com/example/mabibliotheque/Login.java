package com.example.mabibliotheque;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mabibliotheque.databinding.ActivityLoginBinding;
import com.example.mabibliotheque.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private FirebaseAuth auth;
    private TextInputLayout passwordInputLayout ;
    private TextInputLayout EmailInputLayout;
    private Button loginButton;
    private TextView signupRedirectText;
    private TextView motpasseoblier;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        EmailInputLayout = findViewById(R.id.EmailInputLayout);
        loginButton = findViewById(R.id.loginButton);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        motpasseoblier = findViewById(R.id.motpasseoblier);
        auth = FirebaseAuth.getInstance();
        passwordInputLayout.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = EmailInputLayout.getEditText().getText().toString();
                String pass = passwordInputLayout.getEditText().getText().toString();
                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    if (!pass.isEmpty()) {
                        auth.signInWithEmailAndPassword(email, pass)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Toast.makeText(Login.this, "Connexion_réussie", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Login.this, MainActivity.class));
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Login.this, "La connexion a échoué", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                       passwordInputLayout.setError("Les champs vides ne sont pas autorisés");
                    }
                } else if (email.isEmpty()) {
                    EmailInputLayout.setError("Les champs vides ne sont pas autorisés");
                } else {
                    EmailInputLayout.setError("Veuillez saisir l'e-mail correct");
                }
            }
        });


        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Signup.class));
            }
        });
        motpasseoblier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                View dialogView = getLayoutInflater().inflate(R.layout.activity_motpasseoblier, null);
                TextInputLayout  emailBox = dialogView.findViewById(R.id.emailBox);
                builder.setView(dialogView);
                dialogView.setBackgroundColor(Color.WHITE);
                AlertDialog dialog = builder.create();
                dialogView.findViewById(R.id.btnreset).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String userEmail = emailBox.getEditText().getText().toString();
                        if (TextUtils.isEmpty(userEmail) && !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
                            Toast.makeText(Login.this, "Entrez votre identifiant de messagerie enregistré", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        auth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(Login.this, "Vérifiez votre courrier électronique", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(Login.this, "Impossible d'envoyer, échec", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

                if (dialog.getWindow() != null){
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                dialog.show();
            }
        });
    }
}