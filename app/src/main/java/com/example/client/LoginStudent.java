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

public class LoginStudent extends AppCompatActivity {
    private TextView signupFor_student;
    private TextInputEditText email_login_student;
    private TextInputEditText password_login_student;
   //private TextInputEditText loginphn;
    private Button forgetPassword_student;
    private ProgressBar progressBar;
    private FirebaseAuth fAuth;
    private Button loign_btn;
    private FirebaseFirestore fstore;
    private Button ForgetPassword_student;
    private Button login_phn;
    //private ProgressBar progressBar;
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
        progressBar=findViewById(R.id.progressBar_student_login);
        fstore=FirebaseFirestore.getInstance();
        //login_phnno_teacher=findViewById(R.id.phnno_log_student);
        //teacher_login = findViewById(R.id.login_teacher);
        login_phn = findViewById(R.id.phnno_studet);
        fAuth=FirebaseAuth.getInstance();
        signupFor_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                takeTosignupPageForstudent();
            }
        });
        login_phn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginStudent.this , phn_student.class);
                startActivity(i);
                finish();
            }
        });
        forgetPassword_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
/*ForgetPassword_student.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i = new Intent(LoginStudent.this , phn_teacher.class);
        startActivity(i);
        finish();
    }
});*/
        loign_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_login=email_login_student.getText().toString().trim();
                String password_login=password_login_student.getText().toString().trim();
                if(TextUtils.isEmpty(email_login)) {
                    email_login_student.setError("username is required");
                    return;
                }
                if(TextUtils.isEmpty(password_login)) {
                    password_login_student.setError("password is required");
                    return;
                }
                if(password_login.length()<6)
                {
                    password_login_student.setError("the password must be more than 6 charaters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                loign_btn.setEnabled(false);
                fAuth.signInWithEmailAndPassword(email_login,password_login).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                           // Toast.makeText(
                                 //   LoginStudent.this,"Loggid in successfully",Toast.LENGTH_SHORT).show();
                            DocumentReference documentReference = fstore.collection("users").document(fAuth.getCurrentUser().getUid());
                            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if(documentSnapshot.exists()){
                                        Toast.makeText(LoginStudent.this,"Logged in successfully",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(),form_teacher.class));
                                    }else{
                                        Toast.makeText(LoginStudent.this,"invalid Id and password",Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        loign_btn.setEnabled(true);
                                    }
                                }
                            });

                            //startActivity(new Intent(getApplicationContext(),form_teacher.class));
                        }else{
                            Toast.makeText(LoginStudent.this,"Error !"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            loign_btn.setEnabled(true);
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