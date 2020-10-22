package com.example.client;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Qualification_teacher extends AppCompatActivity {
    
    private TextInputEditText board_t;
    private TextInputEditText graduation_t;
    private TextInputEditText year_of_graduation_t;
    private TextInputEditText postgraduation_t;
    private TextInputEditText subject_spacialization_t;
    private TextInputEditText Institution_t;
    private TextInputEditText degree_t;
    private TextInputEditText language_t;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fstore;
    private String userId_techer;
    Button btn_next_qua_t;
    DatabaseReference reff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qualification_teacher);

//        findViewById(R.id.Qualification_teacher).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i=new Intent(Qualification_teacher.this , upload_teacher.class);
//                startActivity(i);
//                finish();
//            }
//        });
        //board_t = findViewById(R.id.boardcbsc_teacher);
        board_t=findViewById(R.id.boardcbsc_teacher);
        graduation_t = findViewById(R.id.graduation_teacher);
        year_of_graduation_t = findViewById(R.id.your_graduation_year_teacher);
        postgraduation_t = findViewById(R.id.post_graduation_teacher);
        subject_spacialization_t = findViewById(R.id.subject_specialisation_teacher);
        Institution_t = findViewById(R.id.name_institution_teacher);
        degree_t = findViewById(R.id.chosee_degree_teacher);
        language_t = findViewById(R.id.lang_known_teacher);
        btn_next_qua_t = findViewById(R.id.Qualification_teacher_btn_next);

       // reff = FirebaseDatabase.getInstance().getReference().child("");

       fAuth = FirebaseAuth.getInstance();
       fstore = FirebaseFirestore.getInstance();
        btn_next_qua_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String board=board_t.getText().toString().trim();
                String graduation=graduation_t.getText().toString().trim();
                String yearofgraduation=year_of_graduation_t.getText().toString().trim();
                String postgraduation= postgraduation_t.getText().toString().trim();
                String specialization=subject_spacialization_t.getText().toString().trim();
                String institution=Institution_t.getText().toString().trim();
                String degree=degree_t.getText().toString().trim();
                String language=language_t.getText().toString().trim();
                if((TextUtils.isEmpty(board))) {
                    board_t.setError("board is required");
                    return;
                }
                if((TextUtils.isEmpty(graduation))) {
                    graduation_t.setError("graduaation is required");
                    return;
                }
                if((TextUtils.isEmpty(yearofgraduation))) {
                    year_of_graduation_t.setError("year of graduation is required");
                    return;
                }
                if((TextUtils.isEmpty(postgraduation))) {
                    postgraduation_t.setError("postgraduation  is required");
                    return;
                }
                if((TextUtils.isEmpty(specialization))) {
                    subject_spacialization_t.setError("specialization is required");
                    return;
                }
                if((TextUtils.isEmpty(institution))) {
                    Institution_t.setError("institution is required");
                    return;
                }
                if((TextUtils.isEmpty(degree))) {
                    degree_t.setError("degree is required");
                    return;
                }
                if((TextUtils.isEmpty(language))) {
                    language_t.setError("language is required");
                    return;
                }
                 userId_techer = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fstore.collection("user_qualification_teacher").document(userId_techer);
                Map<String , Object> user= new HashMap<>();
                user.put("Board of class 12th" ,board );
                user.put("Graduation subject " , graduation);
                user.put("Year of graduation" ,yearofgraduation );
                user.put("post graduation" ,postgraduation);
                user.put("subject specialization" , specialization);
                user.put("Institution name " , institution);
                user.put(" any other degree " , degree);
                user.put("Language known" , language);
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("info", "details added successfully" + userId_techer);
                        //. Log.i("info","on success:user  profile is created"+userId);
                        //Intent i = new Intent(Qualification_teacher.this , professional_teacher.class);
                        //startActivity(i);

                    }
                });
            }
        });
    }
}