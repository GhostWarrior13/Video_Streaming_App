package com.example.movify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class Forgot_Password_Activity extends AppCompatActivity {

    Button btn_Reset;
    EditText et_Email;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        btn_Reset = findViewById(R.id.btn_Reset);
        et_Email = findViewById(R.id.et_Email);
        fAuth = FirebaseAuth.getInstance();

        btn_Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetPassword();
            }
        });
    }

    private void ResetPassword(){
        String email = et_Email.getText().toString().trim();

        if (email.isEmpty()){
            Toast.makeText(Forgot_Password_Activity.this,"Enter your email", Toast.LENGTH_SHORT).show();
            et_Email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            et_Email.setError("Please provide valid email");
            et_Email.requestFocus();
            return;
        }
        fAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                Toast.makeText(Forgot_Password_Activity.this,"Check your email", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(Forgot_Password_Activity.this,"Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}