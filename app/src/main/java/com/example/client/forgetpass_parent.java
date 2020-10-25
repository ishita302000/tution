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

public class forgetpass_parent extends AppCompatActivity {
    private Button btn_forget_parent;
    private TextView Email_forget_parent;
    private FirebaseAuth mAuth;
    private TextView backtologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpass_parent);

        btn_forget_parent = (Button)findViewById(R.id.buttonforget_parent);
        Email_forget_parent = (TextView)findViewById(R.id.parent_forget_email);
        backtologin = (TextView)findViewById(R.id.loginforget_p);
        mAuth = FirebaseAuth.getInstance();

        btn_forget_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_teacher = Email_forget_parent.getText().toString();
                if(TextUtils.isEmpty(email_teacher))
                {
                    Toast.makeText(forgetpass_parent.this ,"Please enter your correct email !" , Toast.LENGTH_SHORT).show();
                }
                else
                {

                    mAuth.sendPasswordResetEmail(email_teacher).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(forgetpass_parent.this , "Please visit your email to reset your password", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(forgetpass_parent.this , LoginTeacher.class));
                            }
                            else
                            {
                                String message = task.getException().getMessage();
                                Toast.makeText(forgetpass_parent.this , "Error"+message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        backtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(forgetpass_parent.this , LoginParent.class);
                startActivity(i);
                finish();
            }
        });
    }
}