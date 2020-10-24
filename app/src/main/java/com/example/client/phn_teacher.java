package com.example.client;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

public class phn_teacher extends AppCompatActivity {
    private FirebaseAuth fAuth1;
    private FirebaseFirestore fstore1;
    private FirebaseFirestore fStore;
    private String userId;
    private TextView reLogin_otp_not_generated;
    private String verificationid;
     PhoneAuthProvider.ForceResendingToken token;
    private Boolean verificationInprogress=false;
    private FirebaseUser fUser;
    private ProgressBar progressBar;
   private  Button btn_phn_login;
   private  EditText mOTP_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phn_teacher);
         btn_phn_login=findViewById(R.id.login_phn_teacher);
        final EditText mPhone_no=findViewById(R.id.phn__login_teacher);
         mOTP_no=findViewById(R.id.loginotp_teacher);
        progressBar=findViewById(R.id.progressBar_teacher_pn);
        reLogin_otp_not_generated=findViewById(R.id.reEnterpn);
        fAuth1=FirebaseAuth.getInstance();
        fstore1=FirebaseFirestore.getInstance();
        fUser=fAuth1.getCurrentUser();
        reLogin_otp_not_generated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(phn_teacher.this,form_teacher.class));
            }
        });

        btn_phn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!verificationInprogress) {
                    final String phone = mPhone_no.getText().toString().trim();
                    if((TextUtils.isEmpty(phone))) {
                        mPhone_no.setError("phone is required");
                        return;
                    }
                    if(phone.length()<10) {
                        mPhone_no.setError("the no must be of 10 character");
                        return;
                    }
                    progressBar.setVisibility(View.VISIBLE);
                    reLogin_otp_not_generated.setVisibility(View.VISIBLE);
                    btn_phn_login.setEnabled(false);
                    //final String otp = mOTP_no.getText().toString().trim();
                    String phonrnumber = "+91" + phone;
                    requestOTP(phonrnumber);
                }else{
                    String userOTP=mOTP_no.getText().toString().trim();
                    if(!userOTP.isEmpty() && userOTP.length()==6){
                        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationid,userOTP);
                        btn_phn_login.setEnabled(false);
                        progressBar.setVisibility(View.VISIBLE);
                        verifyAuth(credential);
                        // verificationInprogress=false;
                    }else{
                        mOTP_no.setError("valid otp is required");
                    }
                }
            }
        });
    }
    public void requestOTP(String phonNo){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phonNo, 60L, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationid=s;
                //setContentView(R.layout.otp_layout);
                token=forceResendingToken;
                verificationInprogress=true;
                btn_phn_login.setEnabled(true);
                progressBar.setVisibility(View.GONE);
                mOTP_no.setVisibility(View.VISIBLE);
                reLogin_otp_not_generated.setVisibility(View.GONE);
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(phn_teacher.this,"Cannot create account"+e.getMessage(),Toast.LENGTH_SHORT).show();
                Toast.makeText(phn_teacher.this,"please try after sometime."+e.getMessage(),Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                startActivity(new Intent(phn_teacher.this,MainActivity.class));
            }
        });

    }
    private void verifyAuth(PhoneAuthCredential credential) {
        fAuth1.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(phn_teacher.this,"Login successful",Toast.LENGTH_SHORT).show();
                    DocumentReference documentReference = fstore1.collection("users").document(fAuth1.getCurrentUser().getUid());
                    if(documentReference==null){
                        fUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(phn_teacher.this,"noDeleted",Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(phn_teacher.this,"noDeleted not",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        fUser=fAuth1.getCurrentUser();
                        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if(documentSnapshot.exists()){
                                    progressBar.setVisibility(View.GONE);
                                    startActivity(new Intent(phn_teacher.this,form_teacher.class));
                                    Toast.makeText(phn_teacher.this,"Authentication is successful123",Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(phn_teacher.this,"userDoesnotExist",Toast.LENGTH_SHORT).show();
                                    fUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(phn_teacher.this,"noDeleted",Toast.LENGTH_SHORT).show();
                                                FirebaseAuth.getInstance().signOut();
                                            }
                                            else{
                                                Toast.makeText(phn_teacher.this,"noDeleted not",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });


                                }
                            }
                        });
                    }
                }else{
                    reLogin_otp_not_generated.setVisibility(View.GONE);
                    Toast.makeText(phn_teacher.this,"wrong OTP entered",Toast.LENGTH_SHORT).show();
                    Toast.makeText(phn_teacher.this,"relogin with phone no",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(phn_teacher.this,phn_teacher.class));
                }
            }
        });
    }
}