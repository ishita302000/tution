package com.example.client;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;

public class form_teacher extends AppCompatActivity {
   private  String currentPhotoPath ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_teacher);

         findViewById(R.id.button_photo_teacher).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
               String filename = "photo_teacher";
               File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                 try
                 {
                     File imagefile = File.createTempFile(filename,".jpg" , storageDirectory);
                     Uri imageUri =  FileProvider.getUriForFile(form_teacher.this , "com.example.client.fileprovider" , imagefile);
                     currentPhotoPath = imagefile.getAbsolutePath();
                     Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                     intent.putExtra(MediaStore.EXTRA_OUTPUT , imageUri);
                     startActivityForResult(intent , 1);

                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
         });



         findViewById(R.id.button_next_teacher).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent i=new Intent(form_teacher.this , persnal_details_teacher.class);
                 startActivity(i);
                 finish();
             }
         });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==1 && resultCode == RESULT_OK)
        {
            Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);

            ImageView  imageView = findViewById(R.id.imageView);
            imageView.setImageBitmap(bitmap);
        }
    }
}