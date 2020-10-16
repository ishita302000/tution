package com.example.client;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgetpass_teacher extends AppCompatActivity {

    private Button btn_forget;
    private TextView Email_forget_teacher;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpass_teacher);
        btn_forget = (Button)findViewById(R.id.buttonforget_teacher);
        Email_forget_teacher = (TextView)findViewById(R.id.teacher_forget_email);
        mAuth = FirebaseAuth.getInstance();

       btn_forget.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String email_teacher = Email_forget_teacher.getText().toString();
               if(TextUtils.isEmpty(email_teacher))
               {
                   Toast.makeText(forgetpass_teacher.this ,"Please enter your correct email !" , Toast.LENGTH_SHORT).show();
               }
               else
               {

                   mAuth.sendPasswordResetEmail(email_teacher).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful())
                           {
                               Toast.makeText(forgetpass_teacher.this , "Please visit your email to reset your password", Toast.LENGTH_SHORT).show();
                               startActivity(new Intent(forgetpass_teacher.this , LoginTeacher.class));
                           }
                           else
                           {
                               String message = task.getException().getMessage();
                               Toast.makeText(forgetpass_teacher.this , "Error"+message, Toast.LENGTH_SHORT).show();
                           }
                       }
                   });
               }
           }
       });
    }
}