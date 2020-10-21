package com.example.client;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class persnal_details_student extends AppCompatActivity {
    private TextInputEditText age_s;
    private  TextInputEditText gender_s;
    private  TextInputEditText birth_s;
    private  TextInputEditText schoolname_s;
    private  TextInputEditText schoolboard_s;
    private  TextInputEditText class_s;
    private  TextInputEditText percentage_s;
    private TextInputEditText address_s;
    private  TextInputEditText blood_s;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fstore;
    private  String  userId_techer;
    private Button btn_student_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persnal_details_student);
        age_s = findViewById(R.id.age_student);
        gender_s = findViewById(R.id.Gender_student);
        birth_s = findViewById(R.id.birth_student);
        schoolname_s = findViewById(R.id.schoolname_student);
        schoolboard_s = findViewById(R.id.Board_student);
        class_s = findViewById(R.id.class_student);
        percentage_s = findViewById(R.id.percentage_student);
        address_s = findViewById(R.id.address_student);
        blood_s=findViewById(R.id.Blood_student);
        fAuth = FirebaseAuth.getInstance();
        fstore= FirebaseFirestore.getInstance();

        btn_student_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userId_techer = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fstore.collection("users_persnal_details_student").document(userId_techer);
                Map<String, Object> user = new HashMap<>();
                user.put("age", age_s.getText().toString().trim());
                user.put("gender", gender_s.getText().toString().trim());
                user.put("birth", birth_s.getText().toString().trim());
                user.put("school name", schoolname_s.getText().toString().trim());
                user.put("school board", schoolboard_s.getText().toString().trim());
                user.put("class", class_s.getText().toString().trim());
                user.put("percentage ", percentage_s.getText().toString().trim());
                user.put("address", address_s.getText().toString().trim());
                user.put("blood group", blood_s.getText().toString().trim());
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("info", "details added successfully" + userId_techer);
                        //. Log.i("info","on success:user  profile is created"+userId);
                        //   Intent i = new Intent(uploadphoto_student.this , professional_teacher.class);
                        // startActivity(i);
                        Toast.makeText(persnal_details_student.this , "ThankYou for filling the form " , Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }
}