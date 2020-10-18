package com.example.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginParent extends AppCompatActivity {
     private TextView signupFor_parent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_parent);
        //initialized the instance variables
        signupFor_parent=findViewById(R.id.slogan_name_parent);
        signupFor_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeTosignupPageForteachers();
            }
        });
    }
    //for going to signup activity
    private void takeTosignupPageForteachers(){
        startActivity(new Intent(this,Signup_parent.class));
    }

}