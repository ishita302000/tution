package com.example.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginParent extends AppCompatActivity {
     private TextView signupFor_parent;
    private TextInputEditText email_login_parent;
    private TextInputEditText password_login_parent;
    private Button forgetPassword_parent;
    private TextView login_phnno_parent;
    private Button parent_login;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fstore;
    private  TextView loginphn;
    private Button ForgetPassword_parent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_parent);
        //initialized the instance variables
        signupFor_parent=findViewById(R.id.slogan_name_parent);
        email_login_parent= findViewById(R.id.email_log_parent);
        password_login_parent=findViewById(R.id.password_log_parent);
        forgetPassword_parent = findViewById(R.id.forgot_parent);
        login_phnno_parent=findViewById(R.id.newuser_parent);
        parent_login = findViewById(R.id.signin_parent);
        //loginphn = findViewById(R.id.phnno_log_teacher);
        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        signupFor_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeTosignupPageForteachers();
            }
        });

        parent_login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String email_login=email_login_parent.getText().toString().trim();
                String password_login=password_login_parent.getText().toString().trim();
                if(TextUtils.isEmpty(email_login)) {
                    email_login_parent.setError("username is required");
                    return;
                }
                if(TextUtils.isEmpty(password_login)) {
                    password_login_parent.setError("password is required");
                    return;
                }
                if(password_login.length()<6)
                {
                    password_login_parent.setError("the password must be more than 6 charaters");
                    return;
                }
                fAuth.signInWithEmailAndPassword(email_login,password_login).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            DocumentReference documentReference = fstore.collection("usersParent").document(fAuth.getCurrentUser().getUid());
                            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if(documentSnapshot.exists()){
                                        Toast.makeText(LoginParent.this,"Loggid in successfully",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(),form_parent.class));
                                    }else{
                                        Toast.makeText(LoginParent.this,"invalid Id and password",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            //Toast.makeText(LoginParent.this,"Loggid in successfully",Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(getApplicationContext(),form_teacher.class));
                        }else{
                            Toast.makeText(LoginParent.this,"Error !"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

        login_phnno_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginParent.this , phn_parent.class);
                startActivity(i);
                finish();
            }
        });
    }
    //for going to signup activity
    private void takeTosignupPageForteachers(){
        startActivity(new Intent(this,Signup_parent.class));
    }
    public void logout(){
        FirebaseAuth.getInstance().signOut();
        finish();
    }

    @Override
    public void onBackPressed() {
        logout();
        super.onBackPressed();


    }
}