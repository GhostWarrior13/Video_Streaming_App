package com.example.movify;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Login_Activity extends AppCompatActivity {

    TextView Not_a_Member;
    private EditText Email, Password;
    private String email, password;
    Button Login_BTN, btn_Resend_Verify;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        Not_a_Member = findViewById(R.id.Not_a_Member);
        Login_BTN = findViewById(R.id.Login_BTN);
        email = password = "";
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        btn_Resend_Verify = findViewById(R.id.btn_Resend_Verify);
        FirebaseUser user = mAuth.getCurrentUser();

        email = Email.getText().toString();
        password = Password.getText().toString();

        
        btn_Resend_Verify.setVisibility(View.INVISIBLE);



        Not_a_Member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        Login_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        btn_Resend_Verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert user != null;
                user.sendEmailVerification();
            }
        });
    }


        public void register () {

            Intent intent = new Intent(this, Registration_Activity.class);
            startActivity(intent);
            finish();
        }

        public void loginUser(){
            FirebaseUser user = mAuth.getCurrentUser();

            email = Email.getText().toString();
            password = Password.getText().toString();

            if (TextUtils.isEmpty(email)){
                Email.setError("Email cannot be empty");
                Email.requestFocus();

            }else if (TextUtils.isEmpty(password)){
                Password.setError("Email cannot be empty");
                Password.requestFocus();

            }
            else {
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            if (task.isSuccessful() && Objects.requireNonNull(user).isEmailVerified()){
                                Toast.makeText(Login_Activity.this,"Logged in", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Login_Activity.this, MainActivity.class));
                            }else {
                                Toast.makeText(Login_Activity.this, "Login Error, make sure you are verified" ,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        }
