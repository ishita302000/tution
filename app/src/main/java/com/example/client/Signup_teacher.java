package com.example.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Signup_teacher extends AppCompatActivity {

    TextInputEditText teacher_regName , teacher_regUsserName , teacher_regEmail , teacher_Phone , teacher_regpassword ;
    Button   teacher_regbtn , teacher_regtoLoginBtn;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fstore;
    private  String userId_techer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_teacher);

        teacher_regName = findViewById(R.id.signn_fullname_teacher);
        //teacher_regUsserName = findViewById(R.id.signn_username_teacher);
        teacher_regEmail = findViewById(R.id.sign_email_teacher);
        teacher_Phone = findViewById(R.id.sign_phn_teacher);
        teacher_regpassword = findViewById(R.id.sign_password_teacher);
        teacher_regbtn = findViewById(R.id.signup_teacher);
        teacher_regtoLoginBtn = findViewById(R.id.Login_text_sigin_teacher);
        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),LoginTeacher.class));
            finish();
        }
        teacher_regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



             final String   teacher_name = teacher_regName.getEditableText().toString();
            // final String  teacher_username = teacher_regUsserName.getEditableText().toString();
             final String teacher_email = teacher_regEmail.getEditableText().toString();
             final String teacher_phoneNo = teacher_Phone.getEditableText().toString();
             final String  teacher_password = teacher_regpassword.getEditableText().toString();

             UserHelperClass_teacher helperClass_teacher = new UserHelperClass_teacher(  teacher_name , teacher_email , teacher_phoneNo , teacher_password);
                fAuth.createUserWithEmailAndPassword(teacher_email,teacher_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Signup_teacher.this,"user created",Toast.LENGTH_SHORT).show();
                            //putting other data like name ,email etc into the fire base collection name users
                            userId_techer=fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference=fstore.collection("users").document(userId_techer);
                            Map<String,Object> user=new HashMap<>();
                            user.put("name",teacher_name);
                            user.put("E-mail",teacher_email);
                            user.put("PhoneNo",teacher_phoneNo);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.i("info","on success:user  profile is created"+userId_techer);
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),LoginTeacher.class));
                            finish();
                        }else{
                            Toast.makeText(Signup_teacher.this,"Error !"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                           // mProgressBar2.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }

}