package com.example.client;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ImageView teacherimage;
    private ImageView childerenimage;
    private ImageView parentimage;
    private ImageView image;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        teacherimage = (ImageView) findViewById(R.id.teacherImage);
        childerenimage = (ImageView) findViewById(R.id.childrenImage);
        parentimage = (ImageView) findViewById(R.id.parentImage);
        image = (ImageView) findViewById(R.id.logo_image);
        text = (TextView) findViewById(R.id.logo_name);

       teacherimage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
             Intent intent = new Intent(MainActivity.this, LoginTeacher.class);
             startActivity(intent);

           }
       });




       childerenimage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this, LoginStudent.class);
               startActivity(intent);

           }
       });




       parentimage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this, LoginParent.class);
               startActivity(intent);
           }
       });
    }

}