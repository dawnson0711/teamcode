package com.example.groupasg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    TextInputEditText et_email, et_password;
    Button buttonReg;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView tv_backtologin;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent= new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //initializing variable
        mAuth = FirebaseAuth.getInstance();
        et_email = findViewById(R.id.email);
        et_password = findViewById(R.id.password);
        buttonReg = findViewById(R.id.bt_register);
        progressBar = findViewById(R.id.progressBar);
        tv_backtologin= findViewById(R.id.tv_backtologin);


        tv_backtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });
        //password visibility
        final TextInputEditText etPassword = findViewById(R.id.password);
        final ImageButton btnTogglePasswordVisibility = findViewById(R.id.btnTogglePasswordVisibility);

        btnTogglePasswordVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle password visibility
                if (etPassword.getTransformationMethod() == null) {
                    // Password is visible, hide it
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    btnTogglePasswordVisibility.setImageResource(R.drawable.baseline_remove_red_eye_24);
                } else {
                    // Password is hidden, show it
                    etPassword.setTransformationMethod(null);
                    btnTogglePasswordVisibility.setImageResource(R.drawable.baseline_visibility_off_24);
                }
            }
        });
        //Register Button

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = String.valueOf(et_email.getText());
                password = String.valueOf(et_password.getText());
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Register.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Register.this, "Please enter the password", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(Register.this, "Account created.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), Login.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // If sign-in fails, display a message to the user.
                                    Toast.makeText(Register.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Log the error for further investigation
                                Log.e("RegisterActivity", "Authentication failed", e);
                            }
                        });

            }
        });
    }
}
