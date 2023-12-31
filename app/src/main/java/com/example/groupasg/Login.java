package com.example.groupasg;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    TextInputEditText et_email, et_password;
    Button buttonSignUp, buttonLogin;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView tvForget;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initializing variable
        mAuth = FirebaseAuth.getInstance();
        et_email = findViewById(R.id.email);
        et_password = findViewById(R.id.password);
        buttonSignUp = findViewById(R.id.bt_signup1);
        progressBar = findViewById(R.id.progressBar);
        buttonLogin = findViewById(R.id.bt_login);
        tvForget = findViewById(R.id.tv_forget);

        //forget password text
        tvForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), forgetpassword.class);
                startActivity(intent);
                finish();
            }
        });
        //button signup
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
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


        //Login Button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = String.valueOf(et_email.getText());
                password = String.valueOf(et_password.getText());
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Login.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Login.this, "Please enter the password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Login Successfull",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),
                                            MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}