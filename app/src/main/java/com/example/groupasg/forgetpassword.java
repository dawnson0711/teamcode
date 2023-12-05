package com.example.groupasg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class forgetpassword extends AppCompatActivity {

    //variable
    Button btnReset,btnBack;
    TextInputEditText et_email;
    FirebaseAuth mAuth;
    String strEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        btnBack =findViewById(R.id.bt_backtoLogin);
        btnReset=findViewById(R.id.bt_reset);
        et_email=findViewById(R.id.email);

        mAuth = FirebaseAuth.getInstance();

        //Reset button Listener
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strEmail = et_email.getText().toString().trim();
                if (!TextUtils.isEmpty(strEmail)){
                    ResetPassword();
                }else {
                    et_email.setError("Email field can't be empty");
                }
            }

            private void ResetPassword() {
                btnReset.setVisibility(View.INVISIBLE);

                mAuth.sendPasswordResetEmail(strEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(forgetpassword.this,
                                        "Reset password link has been sent to your email",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent= new Intent(forgetpassword.this,Login.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(forgetpassword.this,
                                        "Error : " +e.getMessage(),Toast.LENGTH_SHORT).show();
                                btnReset.setVisibility(View.VISIBLE);

                            }
                        });
            }
        });

    }
}
