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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class Registration_Activity extends AppCompatActivity {

    private EditText Username,Email, Password;
    private TextView Already_A_Member;
    private Button Register_BTN, btn_Resend_Verify;
    private String name, email, password;
    private TextView msg_confirm;
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

        FirebaseUser user = mAuth.getCurrentUser();

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
                public void onComplete(@NotNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser user = mAuth.getCurrentUser();
                        assert user != null;
                        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Registration_Activity.this, "Email Verification Sent", Toast.LENGTH_SHORT).show();
                                DirectToLoginPage();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText(Registration_Activity.this, "Email could not be sent", Toast.LENGTH_SHORT).show();
                            }
                        });

                        if (user.isEmailVerified()){
                        Message_Success.setVisibility(View.VISIBLE);

                        Toast.makeText(Registration_Activity.this, "User Registered", Toast.LENGTH_SHORT).show();
                        DirectToLoginPage();
                    }else{
                            Toast.makeText(Registration_Activity.this, "Email not verified", Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });

        }



    }
}




