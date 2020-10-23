package com.example.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Signup_student extends AppCompatActivity {
    TextInputEditText student_regName, student_regEmail, student_phone, student_regpassword;
    Button student_regbtn, student_regtoLoginBtn;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fstore;
    private String userId_techer;
    private EditText enterotp;
    String verificationid;
    Boolean verificationInprogress = false;
    int u = 0;
    String userId1;
    String name_t;
    String email_t;
    String phpne_t;
    PhoneAuthProvider.ForceResendingToken token;
    FirebaseAuth fAuth1=FirebaseAuth.getInstance();
    FirebaseFirestore fstore1=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_student);

        student_regName = findViewById(R.id.signn_fullname_student);
        //teacher_regUsserName = findViewById(R.id.signn_username_teacher);
        student_regEmail = findViewById(R.id.sign_email_student);
        student_phone = findViewById(R.id.signn_phn_student);
        student_regpassword = findViewById(R.id.signn_password_student);
        student_regbtn = findViewById(R.id.sign_student_login);
        student_regtoLoginBtn = findViewById(R.id.Login_text_sigin_student);
        enterotp = findViewById(R.id.sign_otp_student);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), LoginTeacher.class));
            finish();
        }

        student_regtoLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (u == 0) {

                    final String teacher_name = student_regName.getEditableText().toString();
                    // final String  teacher_username = teacher_regUsserName.getEditableText().toString();
                    final String teacher_email = student_regEmail.getEditableText().toString();
                    final String teacher_phoneNo = student_phone.getEditableText().toString();
                    final String teacher_password = student_regpassword.getEditableText().toString();
                    name_t=teacher_name;
                    email_t= teacher_email;
                    phpne_t=teacher_phoneNo;

                    UserHelperClass_teacher helperClass_teacher = new UserHelperClass_teacher(teacher_name, teacher_email, teacher_phoneNo, teacher_password);
                    fAuth.createUserWithEmailAndPassword(teacher_email, teacher_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Signup_student.this, "user created", Toast.LENGTH_SHORT).show();
                                //putting other data like name ,email etc into the fire base collection name users
                                userId_techer = fAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = fstore.collection("usersStudent").document(userId_techer);
                                Map<String, Object> user = new HashMap<>();
                                user.put("name", teacher_name);
                                user.put("E-mail", teacher_email);
                                user.put("PhoneNo", teacher_phoneNo);
                                u=1;
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.i("info", "on success:user  profile is created" + userId_techer);
                                        //. Log.i("info","on success:user  profile is created"+userId);
                                        FirebaseAuth.getInstance().signOut();

                                        String phonrnumber = "+91" + teacher_phoneNo;
                                        requestOTP(phonrnumber);
                                    }

                                });
                                //startActivity(new Intent(getApplicationContext(),LoginTeacher.class));
                                //finish();
                            } else {
                                Toast.makeText(Signup_student.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                // mProgressBar2.setVisibility(View.GONE);u=
                                u = 0;
                            }
                        }


                    });
                } else {
                    String userOTP = enterotp.getText().toString().trim();
                    if (!userOTP.isEmpty() && userOTP.length() == 6) {
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid, userOTP);
                        verifyAuth(credential);
                        u = 0;
                        abc();
                        // verificationInprogress=false;
                    } else {
                        student_phone.setError("valid otp is required");
                    }
                }

            }
        });

    }

    public void requestOTP(String phonNo) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phonNo, 60L, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationid = s;
                //setContentView(R.layout.otp_layout);
                token = forceResendingToken;
                verificationInprogress = true;
                enterotp.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(Signup_student.this, "Cannot create account" + e.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });

    }

    private void verifyAuth(PhoneAuthCredential credential) {
        fAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Signup_student.this, "Authentication is successful", Toast.LENGTH_SHORT).show();
                    userId1 = fAuth1.getCurrentUser().getUid();
                    DocumentReference documentReference1 = fstore1.collection("usersStudent").document(userId1);
                    Map<String, Object> user1 = new HashMap<>();
                    user1.put("name", name_t);
                    user1.put("E-mail", email_t);
                    user1.put("PhoneNo", phpne_t);
                    documentReference1.set(user1).addOnSuccessListener(new OnSuccessListener<Void>(){

                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.i("info","on success:user  profile is created"+userId1);
                        }
                    });
                } else {
                    Toast.makeText(Signup_student.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void abc() {
        startActivity(new Intent(this,LoginStudent.class));
    }
}