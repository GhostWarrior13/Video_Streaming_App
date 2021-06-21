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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Registration_Activity extends AppCompatActivity {

    private EditText Username,Email, Password,Password_Confirm;
    private TextView Already_A_Member;
    private Button Register_BTN;
    private String name, email, password;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();

        TextView Message_Success = findViewById(R.id.Message_Success);
        Already_A_Member = findViewById(R.id.Already_A_Member);
        Username = findViewById(R.id.Username);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        Register_BTN = findViewById(R.id.Register_BTN);
        name = email = password = "";

        name = Username.getText().toString().trim();
        email = Email.getText().toString();
        password = Password.getText().toString();

        Message_Success.setVisibility(View.INVISIBLE);

        Already_A_Member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DirectToLoginPage();
            }
        });

        Register_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();

            }
        });
    }

    public void DirectToLoginPage(){
        Intent intent = new Intent(this, Login_Activity.class);
        startActivity(intent);
        finish();
    }

    private void createUser(){

        TextView Message_Success = findViewById(R.id.Message_Success);

        email = Email.getText().toString();
        password = Password.getText().toString();

        if (TextUtils.isEmpty(email)){
            Email.setError("Email cannot be empty");
            Email.requestFocus();

        }else if (TextUtils.isEmpty(password)){
            Password.setError("Password cannot be empty");
            Password.requestFocus();

        }else {
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Message_Success.setVisibility(View.VISIBLE);
                        Toast.makeText(Registration_Activity.this, "User Registered", Toast.LENGTH_SHORT).show();
                        DirectToLoginPage();
                    }else{
                        Toast.makeText(Registration_Activity.this, "Registration Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


        }

    }




