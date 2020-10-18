package com.example.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginStudent extends AppCompatActivity {
    private TextView signupFor_student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_student);

        //initialized the instance variables
        signupFor_student=findViewById(R.id.slogan_name_student);
        signupFor_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeTosignupPageForstudent();
            }
        });
    }

    //for going to signup activity
    private void takeTosignupPageForstudent(){
        startActivity(new Intent(this,Signup_student.class));
    }
}