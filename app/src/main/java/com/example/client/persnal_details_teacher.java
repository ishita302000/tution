package com.example.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class persnal_details_teacher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persnal_details_teacher);

        findViewById(R.id.persnal_teacher_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(persnal_details_teacher.this , Qualification_teacher.class);
                startActivity(i);
                finish();
            }
        });
    }
}