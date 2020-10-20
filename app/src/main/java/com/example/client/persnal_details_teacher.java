package com.example.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    users user;
    Button btn_save;


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
//
        name_t = (TextInputEditText)findViewById(R.id.name_teacher);
        age_t = (TextInputEditText)findViewById(R.id.age_teacher);
        gender_t = (TextInputEditText)findViewById(R.id.Gender_teacher);
        martial_t = (TextInputEditText)findViewById(R.id.martial_teacher);
        email_t = (TextInputEditText)findViewById(R.id.Email_teacher);
        birth_t = (TextInputEditText)findViewById(R.id.Birth_teacher);
        contact_t = (TextInputEditText)findViewById(R.id.contact_teacher);
        address_t = (TextInputEditText)findViewById(R.id.address_teacher);
        reff = FirebaseDatabase.getInstance().getReference().child("users");
        btn_save = (Button)findViewById(R.id.persnal_teacher_button);
        user = new users();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   int aget = Integer.parseInt(age_t.getText().toString().trim());
                   Long  birtht =Long.parseLong(age_t.getText().toString().trim());
                   Long contactt = Long.parseLong(contact_t.getText().toString().trim());

                   user.setTe_name(name_t.getText().toString().trim());
                   user.setTe_age(aget);
                   user.setTe_gender(gender_t.getText().toString().trim());
                   user.setTe_martial(martial_t.getText().toString().trim());
                   user.setTe_email(email_t.getText().toString().trim());
                   user.setTe_birth(birtht);
                   user.setTe_contact(contactt);
                   user.setTe_address(address_t.getText().toString().trim());
                   reff.push().setValue(user);
                Toast.makeText(persnal_details_teacher.this , "Data inserted successfully " , Toast.LENGTH_LONG).show();

                Intent i = new Intent(persnal_details_teacher.this , Qualification_teacher.class);
                startActivity(i);

            }
        });

    }
}