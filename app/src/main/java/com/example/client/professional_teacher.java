package com.example.client;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class professional_teacher extends AppCompatActivity {
    private TextInputEditText total_work_experience_t;
    private TextInputEditText work_experience_any_other_t;
    private TextInputEditText your_strength_t;
    private TextInputEditText classes_you_can_teach_t;
    private TextInputEditText ehich_board_t;
    private TextInputEditText teaching_area_t;
    private TextInputEditText subjects_you_can_teach_t;
    private TextInputEditText teaching_languages_t;
    private TextInputEditText timings_t;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fstore;
    private String userId_techer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional_teacher);

        total_work_experience_t=findViewById(R.id.work_experince_teaching_teacher);
        work_experience_any_other_t=findViewById(R.id.any_other_teaching_teacher);
        your_strength_t=findViewById(R.id.your_strengths_teacher);
        classes_you_can_teach_t=findViewById(R.id.classes_teach_teacher);
        ehich_board_t=findViewById(R.id.board_teacher);
        teaching_area_t=findViewById(R.id.chosee_teacher);
        subjects_you_can_teach_t=findViewById(R.id.can_teach_teacher);
        teaching_languages_t=findViewById(R.id.lang_teacher);
        timings_t=findViewById(R.id.timing_teacher);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        findViewById(R.id.pprofessional_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String total_work=total_work_experience_t.getText().toString().trim();
               String work_experience=work_experience_any_other_t.getText().toString().trim();
               String your_strength=your_strength_t.getText().toString().trim();
               String classes=classes_you_can_teach_t.getText().toString().trim();
               String board=ehich_board_t.getText().toString().trim();
               String teaching_area=teaching_area_t.getText().toString().trim();
               String subjects=subjects_you_can_teach_t.getText().toString().trim();
               String language=teaching_languages_t.getText().toString().trim();
               String timing=timings_t.getText().toString().trim();
                if((TextUtils.isEmpty(total_work))) {
                    total_work_experience_t.setError("total work experience is required");
                    return;
                }
                if((TextUtils.isEmpty(work_experience))) {
                    work_experience_any_other_t.setError("work experience is required");
                    return;
                }
                if((TextUtils.isEmpty(your_strength))) {
                    your_strength_t.setError("strength experience is required");
                    return;
                }
                if((TextUtils.isEmpty(classes))) {
                    classes_you_can_teach_t.setError("class you can teach is required");
                    return;
                }
                if((TextUtils.isEmpty(board))) {
                    ehich_board_t.setError("board is required");
                    return;
                }
                if((TextUtils.isEmpty(teaching_area))) {
                    teaching_area_t.setError("teaching area is required");
                    return;
                }
                if((TextUtils.isEmpty(subjects))) {
                    subjects_you_can_teach_t.setError("subjects is required");
                    return;
                }
                if((TextUtils.isEmpty(language))) {
                    teaching_languages_t.setError("language is required");
                    return;
                }
                if((TextUtils.isEmpty(timing))) {
                    timings_t.setError("timing is required");
                    return;
                }
                userId_techer = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fstore.collection("usersdetails_proffessional").document(userId_techer);
                Map<String, Object> user = new HashMap<>();
                user.put("total work experience in teaching",total_work );
                user.put("work experience in any other", work_experience);
                user.put("your strengths", your_strength);
                user.put("classes you can teach",classes );
                user.put("which board will you prefer to teach", board);
                user.put("your preferable teaching area", teaching_area);
                user.put("subjects you can teach",subjects );
                user.put("preferable teaching language",language );
                user.put("timings", timing);
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("info", "details added successfully" + userId_techer);
                        //. Log.i("info","on success:user  profile is created"+userId);
                        Intent i=new Intent(professional_teacher.this , Qualification_teacher.class);
                        startActivity(i);
                        finish();

                    }

                });

            }
        });
    }
}