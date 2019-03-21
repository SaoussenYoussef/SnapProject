package com.saoussen.snapanonyme.presentation.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import com.saoussen.snapanonyme.R;
import com.saoussen.snapanonyme.presentation.Infrastructure.Network.AppUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import static com.saoussen.snapanonyme.presentation.Infrastructure.Network.AppUtils.IMAGE_PATH_EXTRA;
import static com.saoussen.snapanonyme.presentation.Infrastructure.Network.AppUtils.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;


public class MainActivity extends AppCompatActivity {


    private static final int PERMISSION_REQUEST_ACCESS_EXTERNAL_STORAGE = 123 ;
    String mcurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.activity_main);

        //imageView = findViewById(R.id.main_background);
        // charger l'image avec GLide
        // Glide.with(this).load(R.drawable.astronomy_environment_evening).into(imageView);


    }

    public void onExploreSnaps(View view) {

        Intent intent = new Intent(this, SnapListActivity.class);
        startActivity(intent);

    }

    public void onPublishSnap(View view) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (!AppUtils.hasPermissions(this, permissions)) {

                    // TODO

                    AppUtils.requestPermissions(this, permissions, PERMISSION_REQUEST_ACCESS_EXTERNAL_STORAGE);
                    // No explanation needed; request the permission

            } else {
                //Permission already granted
                // cas où la permission a déjà été demandée
               dispatchTakePictureIntent();
            }
        }



    }


    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_ACCESS_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permission granted, we can loadSnaps
                    dispatchTakePictureIntent();
                } else {
                    //Permission not granted, go back to the home page
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);

                }
                return;
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {


            Intent intent = new Intent(this, PublisherActivity.class);
            intent.putExtra(IMAGE_PATH_EXTRA, mcurrentPhotoPath);
            startActivity(intent);
        }
    }


    // Creér le fichier qui contiendra notre photo

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mcurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.saoussen.snapanonyme.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }


}
