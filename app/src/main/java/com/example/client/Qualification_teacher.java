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
        year_of_graduation_t = findViewById(R.id.your_graduation_teacher);
        postgraduation_t = findViewById(R.id.post_graduationteacher);
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
                 userId_techer = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fstore.collection("user_qualification_teacher").document(userId_techer);
                Map<String , Object> user= new HashMap<>();
                user.put("Board of class 12th" , board_t.getText().toString().trim());
                user.put("Graduation subject " , graduation_t.getText().toString().trim());
                user.put("Year of graduation" , year_of_graduation_t.getText().toString().trim());
                user.put("post graduation" , postgraduation_t.getText().toString().trim());
                user.put("subject specialization" , subject_spacialization_t.getText().toString().trim());
                user.put("Institution name " , Institution_t.getText().toString().trim());
                user.put(" any other degree " , degree_t.getText().toString().trim());
                user.put("Language known" , language_t.getText().toString().trim());
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("info", "details added successfully" + userId_techer);
                        //. Log.i("info","on success:user  profile is created"+userId);
                        Intent i = new Intent(Qualification_teacher.this , professional_teacher.class);
                        startActivity(i);

                    }
                });
            }
        });
    }
}