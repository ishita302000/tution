package com.example.client;

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

public class personal_details_parent extends AppCompatActivity {

    private TextInputEditText age_p;
    private TextInputEditText gender_p;
    private TextInputEditText occupation_p;
    private TextInputEditText graduation_p;
    private TextInputEditText birth_p;
    private TextInputEditText post_graduation__p;
    private TextInputEditText address_p;
    private TextInputEditText allergy_p;
    Button btn_next;
    DatabaseReference reff;
    Button btn_save;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fstore;
    private String userId_techer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details_parent);


        age_p = (TextInputEditText)findViewById(R.id.age_teacher_p);
        gender_p = (TextInputEditText)findViewById(R.id.Gender_teacher_p);
occupation_p=(TextInputEditText)findViewById(R.id.occupation_parent);
graduation_p=(TextInputEditText)findViewById(R.id.graduation_parent);
        birth_p = (TextInputEditText)findViewById(R.id.Birth_teacher_p);
post_graduation__p=(TextInputEditText)findViewById(R.id.post_graduationparent);
allergy_p=(TextInputEditText)findViewById(R.id.allergy_parent) ;
        address_p = (TextInputEditText)findViewById(R.id.address_teacher_p);
        reff = FirebaseDatabase.getInstance().getReference().child("users");
        btn_save = (Button)findViewById(R.id.persnal_teacher_button_p);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userId_techer = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fstore.collection("parent_personal_details").document(userId_techer);
                Map<String, Object> user = new HashMap<>();
                user.put("age", age_p.getText().toString().trim());
                user.put("gender", gender_p.getText().toString().trim());
                user.put("occupation", occupation_p.getText().toString().trim()) ;
                user.put("graduation", graduation_p.getText().toString().trim()) ;
                user.put("post graduation", post_graduation__p.getText().toString().trim()) ;
                user.put("allergy", allergy_p.getText().toString().trim()) ;
                user.put("date of birth", birth_p.getText().toString().trim());
                user.put("address", address_p.getText().toString().trim());
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("info", "details added successfully" + userId_techer);
                        //. Log.i("info","on success:user  profile is created"+userId);
                        //Intent i = new Intent(personal_details_parent.this , professional_parent.class);
                        //startActivity(i);

                    }

                });



            }
        });

    }
}