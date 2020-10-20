package com.example.client;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class persnal_details_teacher extends AppCompatActivity {

    private TextInputEditText name_t;
    private TextInputEditText age_t;
    private TextInputEditText gender_t;
    private TextInputEditText martial_t;
    private TextInputEditText email_t;
    private TextInputEditText birth_t;
    private TextInputEditText contact_t;
    private TextInputEditText address_t;
    Button btn_next;
    DatabaseReference reff;
    Button btn_save;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fstore;
    private String userId_techer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persnal_details_teacher);

//        findViewById(R.id.persnal_teacher_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i=new Intent(persnal_details_teacher.this , Qualification_teacher.class);
//                startActivity(i);
//                finish();
//            }
//        });

        age_t = (TextInputEditText)findViewById(R.id.age_teacher);
        gender_t = (TextInputEditText)findViewById(R.id.Gender_teacher);
        martial_t = (TextInputEditText)findViewById(R.id.martial_teacher);
       // email_t = (TextInputEditText)findViewById(R.id.Email_teacher);
        birth_t = (TextInputEditText)findViewById(R.id.Birth_teacher);
        //contact_t = (TextInputEditText)findViewById(R.id.contact_teacher);
        address_t = (TextInputEditText)findViewById(R.id.address_teacher);
        reff = FirebaseDatabase.getInstance().getReference().child("users");
        btn_save = (Button)findViewById(R.id.persnal_teacher_button);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  /* int aget = Integer.parseInt(age_t.getText().toString().trim());
                   Long  birtht =Long.parseLong(age_t.getText().toString().trim());
                   Long contactt = Long.parseLong(contact_t.getText().toString().trim());*/
                userId_techer = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fstore.collection("users_persnal_details_teacher").document(userId_techer);
                Map<String, Object> user = new HashMap<>();
                user.put("age", age_t.getText().toString().trim());
                user.put("gender", gender_t.getText().toString().trim());
                user.put("martial status", martial_t.getText().toString().trim());
                user.put("date of birth", birth_t.getText().toString().trim());
                user.put("address", address_t.getText().toString().trim());
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("info", "details added successfully" + userId_techer);
                        //. Log.i("info","on success:user  profile is created"+userId);
                        Intent i = new Intent(persnal_details_teacher.this , professional_teacher.class);
                        startActivity(i);

                    }

                });
                /*user.put("board of class 12", " ");
                user.put("graduation", " ");
                user.put("year of geaduation", " ");
                user.put("post graduation", " ");
                user.put("subjectspecialization", " ");
                user.put("name of institution", " ");
                user.put("any other degree", " ");
                user.put("language known", " ");
                user.put("interested field", " ");
                user.put("total work experience in teaching", " ");
                user.put("work experience in any other", " ");
                user.put("your strengths", " ");
                user.put("classes you can teach", " ");
                user.put("which board will you prefer to teach", " ");
                user.put("your preferable teaching area", " ");
                user.put("subjects you can teach", " ");
                user.put("preferable teaching language", " ");
                user.put("timings", " ");*/
                  /* user.setTe_name(name_t.getText().toString().trim());
                   user.setTe_age(aget);
                   user.setTe_gender(gender_t.getText().toString().trim());
                   user.setTe_martial(martial_t.getText().toString().trim());
                   user.setTe_email(email_t.getText().toString().trim());
                   user.setTe_birth(birtht);
                   user.setTe_contact(contactt);
                   user.setTe_address(address_t.getText().toString().trim());
                   reff.push().setValue(user);
                Toast.makeText(persnal_details_teacher.this , "Data inserted successfully " , Toast.LENGTH_LONG).show();*/


            }
        });

    }
}