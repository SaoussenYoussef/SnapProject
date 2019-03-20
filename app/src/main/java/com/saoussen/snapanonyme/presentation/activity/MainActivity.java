package com.saoussen.snapanonyme.presentation.activity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import com.saoussen.snapanonyme.R;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {


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
    }


}
