package com.example.client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class phn_parent extends AppCompatActivity {
    private FirebaseAuth fAuth1;
    private FirebaseFirestore fstore1;
    private FirebaseFirestore fStore;
    private String userId;
    private String verificationid;
    PhoneAuthProvider.ForceResendingToken token;
    private Boolean verificationInprogress=false;
    private FirebaseUser fUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phn_parent);
        Button btn_phn_login=findViewById(R.id.login_phn_parent);
        final EditText mPhone_no=findViewById(R.id.phn__login_parent);
        final EditText mOTP_no=findViewById(R.id.loginotp_parent);
        fAuth1=FirebaseAuth.getInstance();
        fstore1=FirebaseFirestore.getInstance();
        fUser=fAuth1.getCurrentUser();

        btn_phn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!verificationInprogress) {
                    final String phone = mPhone_no.getText().toString().trim();
                    //final String otp = mOTP_no.getText().toString().trim();
                    String phonrnumber = "+91" + phone;
                    requestOTP(phonrnumber);
                }else{
                    String userOTP=mOTP_no.getText().toString().trim();
                    if(!userOTP.isEmpty() && userOTP.length()==6){
                        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationid,userOTP);
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
                Toast.makeText(phn_parent.this,"Cannot create account"+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void verifyAuth(PhoneAuthCredential credential) {
        fAuth1.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(phn_parent.this,"Login successful",Toast.LENGTH_SHORT).show();
                    DocumentReference documentReference = fstore1.collection("usersParent").document(fAuth1.getCurrentUser().getUid());
                    if(documentReference==null){
                        fUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(phn_parent.this,"noDeleted",Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(phn_parent.this,"noDeleted not",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if(documentSnapshot.exists()){
                                    startActivity(new Intent(phn_parent.this,form_teacher.class));
                                    Toast.makeText(phn_parent.this,"Authentication is successful123",Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(phn_parent.this,"userDoesnotExist",Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                    }
                }else{
                    Toast.makeText(phn_parent.this,"Authentication failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}