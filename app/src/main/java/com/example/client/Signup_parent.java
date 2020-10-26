package com.example.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Signup_parent extends AppCompatActivity {

    TextInputEditText parent_regName, parent_regEmail, parent_Phone, parent_regpassword,parent_fullname;
    Button parent_regbtn, parent_regtoLoginBtn;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fstore;
    private String userId_techer;
    private EditText enterotp;
    private FirebaseAuth fAuth3;
    private FirebaseAuth fAuth2;
    private String userId_techer1;
    private String userId_techer3;
    private FirebaseUser fUser;
    private FirebaseUser fUser3;
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

    //  Boolean verificationInprogress=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_parent);
        parent_fullname=findViewById(R.id.signn_fullname_parent);
       // parent_regName = findViewById(R.id.signn_fullname_parent);
        //teacher_regUsserName = findViewById(R.id.signn_username_teacher);
        parent_regEmail = findViewById(R.id.Sign_email_parent);
        parent_Phone = findViewById(R.id.sign_phn_parent);
        parent_regpassword = findViewById(R.id.sign_password_parent);
        parent_regbtn = findViewById(R.id.signup_parent);
        parent_regtoLoginBtn = findViewById(R.id.login_text_sigin_parent);
        enterotp = findViewById(R.id.sign_otp_parent);
        fAuth = FirebaseAuth.getInstance();
        fAuth2=FirebaseAuth.getInstance();
        fAuth3=FirebaseAuth.getInstance();
        fUser=fAuth2.getCurrentUser();
        fUser3=fAuth3.getCurrentUser();
        fstore = FirebaseFirestore.getInstance();
        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), LoginTeacher.class));
            finish();
        }
        parent_regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (u == 0) {

                    final String parent_name = parent_fullname.getEditableText().toString();
                    // final String  teacher_username = teacher_regUsserName.getEditableText().toString();
                    final String parent_email = parent_regEmail.getEditableText().toString();
                    final String parent_phoneNo = parent_Phone.getEditableText().toString();
                    final String parent_password = parent_regpassword.getEditableText().toString();
                    name_t=parent_name;
                    email_t= parent_email;
                    phpne_t=parent_phoneNo;
                    if((TextUtils.isEmpty(parent_name))) {
                        parent_fullname.setError("name is required");
                        return;
                    }
                    if((TextUtils.isEmpty(parent_password))) {
                        parent_regpassword.setError("password is required");
                        return;
                    }
                    if((TextUtils.isEmpty(parent_phoneNo))) {
                        parent_Phone.setError("phone is required");
                        return;
                    }
                    if(parent_phoneNo.length()<10) {
                        parent_Phone.setError("the no must be of 10 character");
                        return;
                    }
                    if((TextUtils.isEmpty(parent_email))) {
                        parent_regEmail.setError("email is required");
                        return;
                    }
                    if(parent_password.length()<6)
                    {
                        parent_regpassword.setError("the password must be more than 6 charaters");
                        return;
                    }
                    parent_regbtn.setEnabled(false);
                    UserHelperClass_parent helperClass_parent = new UserHelperClass_parent(parent_name, parent_email, parent_phoneNo, parent_password);
                    fAuth.createUserWithEmailAndPassword(parent_email, parent_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //Toast.makeText(Signup_parent.this, "user created", Toast.LENGTH_SHORT).show();
                                //putting other data like name ,email etc into the fire base collection name users
                                userId_techer = fAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = fstore.collection("usersParent").document(userId_techer);
                                Map<String, Object> user = new HashMap<>();
                                user.put("name", parent_name);
                                user.put("E-mail", parent_email);
                                user.put("PhoneNo", parent_phoneNo);
                                u=1;
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        //Log.i("info", "on success:user  profile is created" + userId_techer);
                                        //. Log.i("info","on success:user  profile is created"+userId);
                                        FirebaseAuth.getInstance().signOut();

                                        String phonrnumber = "+91" + parent_phoneNo;
                                        requestOTP(phonrnumber);
                                    }

                                });
                                //startActivity(new Intent(getApplicationContext(),LoginTeacher.class));
                                //finish();
                            } else {
                                Toast.makeText(Signup_parent.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                // mProgressBar2.setVisibility(View.GONE);u=
                                //startActivity(new Intent(Signup_parent.this,MainActivity.class));
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
                        parent_Phone.setError("valid otp is required");
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
                parent_regbtn.setEnabled(true);
                parent_fullname .setVisibility(View.GONE);
                parent_regEmail.setVisibility(View.GONE);
                parent_Phone.setVisibility(View.GONE);
                parent_regpassword.setVisibility(View.GONE);
                parent_regbtn.setEnabled(true);
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
                Toast.makeText(Signup_parent.this, "Cannot create account" + e.getMessage(), Toast.LENGTH_SHORT).show();


                fAuth2.signInWithEmailAndPassword(parent_regEmail .getEditableText().toString(),parent_regpassword.getEditableText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //Toast.makeText(Signup_parent.this,"Loggid in successfully",Toast.LENGTH_SHORT).show();

                            userId_techer1 = fAuth2.getCurrentUser().getUid();
                            FirebaseFirestore.getInstance().collection("usersParent").document(userId_techer1).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Signup_parent.this,"Signup again after sometimes.",Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Signup_parent.this,"Error",Toast.LENGTH_SHORT).show();
                                }
                            });
                            fUser=fAuth2.getCurrentUser();
                            fUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Signup_parent.this,"noDeleted",Toast.LENGTH_SHORT).show();
                                        FirebaseAuth.getInstance().signOut();
                                    }
                                    else{
                                        Toast.makeText(Signup_parent.this,"noDeleted not",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(Signup_parent.this,"Error !"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            //progressBar.setVisibility(View.GONE);
                            //teacher_login.setEnabled(false);
                        }
                    }
                });


            }
        });

    }

    private void verifyAuth(PhoneAuthCredential credential) {
        fAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Signup_parent.this, "registration is successful", Toast.LENGTH_SHORT).show();
                    userId1 = fAuth1.getCurrentUser().getUid();
                    DocumentReference documentReference1 = fstore1.collection("usersParent").document(userId1);
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
                    Toast.makeText(Signup_parent.this, "registration failed", Toast.LENGTH_SHORT).show();
                    parent_regbtn.setEnabled(true);

                    fAuth3.signInWithEmailAndPassword(parent_regEmail .getEditableText().toString(),parent_regpassword.getEditableText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                //Toast.makeText(Signup_parent.this,"Loggid in successfully",Toast.LENGTH_SHORT).show();

                                userId_techer3 = fAuth3.getCurrentUser().getUid();
                                FirebaseFirestore.getInstance().collection("users").document(userId_techer3).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        //Toast.makeText(Signup_parent.this,"Deleted Successfuly",Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Signup_parent.this,"Error",Toast.LENGTH_SHORT).show();
                                    }
                                });
                                fUser3=fAuth3.getCurrentUser();
                                fUser3.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            //Toast.makeText(Signup_parent.this,"noDeleted",Toast.LENGTH_SHORT).show();
                                            FirebaseAuth.getInstance().signOut();
                                        }
                                        else{
                                           // Toast.makeText(Signup_parent.this,"noDeleted not",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                // startActivity(new Intent(getApplicationContext(),LoginTeacher.class));
                            }else{
                                Toast.makeText(Signup_parent.this,"Error !"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                //progressBar.setVisibility(View.GONE);
                                //teacher_login.setEnabled(false);
                            }
                        }
                    });

                }
            }
        });

    }

    public void abc() {
        startActivity(new Intent(this,LoginParent.class));
    }
    public void logout(){
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(Signup_parent.this,"authentication failed", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        logout();

    }
}
