package com.example.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginStudent extends AppCompatActivity {
    private TextView signupFor_student;
    private TextInputEditText email_login_student;
    private TextInputEditText password_login_student;
    private TextInputEditText loginphn;
    private Button forgetPassword_student;

    private FirebaseAuth fAuth;
    private Button loign_btn;
    private Button ForgetPassword_student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_student);

        //initialized the instance variables
        signupFor_student=findViewById(R.id.slogan_name_student);
        email_login_student= findViewById(R.id.email_log_student);
        password_login_student=findViewById(R.id.password_log_student);
        forgetPassword_student = findViewById(R.id.forgot_student);
        loign_btn = findViewById(R.id.Login_text_sigin_student);
        //login_phnno_teacher=findViewById(R.id.phnno_log_student);
        //teacher_login = findViewById(R.id.login_teacher);
        loginphn = findViewById(R.id.phnno_studet);
        fAuth=FirebaseAuth.getInstance();
        signupFor_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeTosignupPageForstudent();
            }
        });

        loign_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_login=email_login_student.getText().toString().trim();
                String password_login=password_login_student.getText().toString().trim();
                fAuth.signInWithEmailAndPassword(email_login,password_login).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(
                                    LoginStudent.this,"Loggid in successfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),form_teacher.class));
                        }else{
                            Toast.makeText(LoginStudent.this,"Error !"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }

    //for going to signup activity
    private void takeTosignupPageForstudent(){
        startActivity(new Intent(this,Signup_student.class));
    }
}