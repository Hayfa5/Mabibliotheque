package com.example.mabibliotheque;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mabibliotheque.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity {

    private FirebaseAuth auth;
    private TextInputLayout passwordInputLayout ;
    private TextInputLayout EmailInputLayout;
    private Button singupButton;
    private TextView signupRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        EmailInputLayout = findViewById(R.id.EmailInputLayout);
        singupButton = findViewById(R.id.singupButton);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        passwordInputLayout.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        auth = FirebaseAuth.getInstance();
        singupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = EmailInputLayout.getEditText().getText().toString().trim();
                String pass = passwordInputLayout.getEditText().getText().toString().trim();
                if (user.isEmpty()){
                    EmailInputLayout.setError("L'email ne peut pas être vide");
                }
                if (pass.isEmpty()){
                    passwordInputLayout.setError("Le mot de passe ne peut pas être vide");
                } else{
                    auth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Signup.this, "Inscription réussie", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Signup.this, Login.class));
                            } else {
                                Toast.makeText(Signup.this, "Échec de l'inscription" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
       signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signup.this, Login.class));
            }
        });
    }
}