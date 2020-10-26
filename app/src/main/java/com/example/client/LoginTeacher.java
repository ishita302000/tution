package com.example.client;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginTeacher extends AppCompatActivity {
     private TextView signup;
     private TextInputEditText email_login_teacher;
     private TextInputEditText password_login_teacher;
     private Button forgetPassword_teacher;
     private TextView login_phnno_teacher;
     private Button teacher_login;
      private FirebaseAuth fAuth;
    private FirebaseFirestore fstore;
      private  TextView loginphn;
      private Button ForgetPassword_teacher;
      private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_teacher);

        signup=findViewById(R.id.slogan_name_teacher);
        email_login_teacher= findViewById(R.id.email_log_teacher);
        password_login_teacher=findViewById(R.id.password_log_teacher);
        forgetPassword_teacher = findViewById(R.id.forgot_teacher);
        login_phnno_teacher=findViewById(R.id.phnno_log_teacher);
        teacher_login = findViewById(R.id.login_teacher);
        loginphn = findViewById(R.id.phnno_log_teacher);
        progressBar=findViewById(R.id.progressBar_teacher_login);
        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        teacher_login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String email_login=email_login_teacher.getText().toString().trim();
                String password_login=password_login_teacher.getText().toString().trim();
                if(TextUtils.isEmpty(email_login)) {
                    email_login_teacher.setError("username is required");
                    return;
                }
                if(TextUtils.isEmpty(password_login)) {
                    password_login_teacher.setError("password is required");
                    return;
                }
                if(password_login.length()<6)
                {
                    password_login_teacher.setError("the password must be more than 6 charaters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                teacher_login.setEnabled(false);
                fAuth.signInWithEmailAndPassword(email_login,password_login).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //Toast.makeText(LoginTeacher.this,"Loggid in successfully",Toast.LENGTH_SHORT).show();
                            DocumentReference documentReference = fstore.collection("users").document(fAuth.getCurrentUser().getUid());
                            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if(documentSnapshot.exists()){
                                        Toast.makeText(LoginTeacher.this,"Loggid in successfully",Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.VISIBLE);
                                        teacher_login.setEnabled(true);
                                        startActivity(new Intent(getApplicationContext(),form_teacher.class));
                                    }else{
                                        Toast.makeText(LoginTeacher.this,"invalid Id and password",Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        teacher_login.setEnabled(true);
                                    }
                                }
                            });

                            //startActivity(new Intent(getApplicationContext(),form_teacher.class));
                        }else{
                            Toast.makeText(LoginTeacher.this,"Error !"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            teacher_login.setEnabled(true);
                        }
                    }
                });
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(LoginTeacher.this , Signup_teacher.class);
                startActivity(intent);
                finish();
            }
        });
       loginphn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(LoginTeacher.this , phn_teacher.class);
               startActivity(i);
               finish();
           }
       });
        forgetPassword_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LoginTeacher.this , forgetpass_teacher.class);
                startActivity(in);
            }
        });


    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        logout();

    }
}