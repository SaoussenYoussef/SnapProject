package com.saoussen.snapanonyme.presentation.activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.saoussen.snapanonyme.R;
import com.saoussen.snapanonyme.presentation.Infrastructure.Network.AppUtils;
import com.saoussen.snapanonyme.presentation.adapter.SnapsAdapter;
import com.saoussen.snapanonyme.presentation.loader.SnapLoader;
import com.saoussen.snapanonyme.presentation.model.Snap;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.saoussen.snapanonyme.presentation.Infrastructure.Network.AppUtils.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;


public class SnapListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Snap>> {


    List<Snap> mSnpas = new ArrayList<>();
    RecyclerView mRecyclerView;
    SnapsAdapter mAdapter;
    RelativeLayout mSpinner;
    Spinner mScopeSpinner;
    private double mScope = 3000;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location mCurrentLocation;
    private LoaderManager.LoaderCallbacks<? extends Object> mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snap_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mContext = this; // on l'initialise pas dans sa methode car c'est une implémentation d'nterface
        mSpinner = findViewById(R.id.spinner);

        mScopeSpinner = findViewById(R.id.scope_spinner);

        mRecyclerView = findViewById(R.id.snap_list_recyclerview);
        mAdapter = new SnapsAdapter(mSnpas, this);
        mRecyclerView.setAdapter(mAdapter);
        // par défaut le layoutmanager charge de manière  vertical
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        //TODO load snaps unsing an Asyntaskloader

        initData();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });





    }

    private void initData() {
        mSpinner.setVisibility(View.VISIBLE);
        mScopeSpinner.setVisibility(View.VISIBLE);



        mScopeSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            if (!AppUtils.hasPermissions(this, permissions)) {

                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    AppUtils.requestPermissions(this, permissions, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                    // No explanation needed; request the permission
                }
            } else {
                //Permission already granted , get the current location and loadSnaps
                // cas où la permission a déjà été demandée
                retrieveSnaps();
            }
        }
    }

    public void onDropDown(View view) {

        Spinner spinner = (Spinner) findViewById(R.id.scope_spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.scope_array, android.R.layout.simple_spinner_item);

// Specify the layout to use when the list of choices appears

        int simple_spinner_dropdown_item = android.R.layout.simple_spinner_dropdown_item;

// Apply the adapter to the spinner

        spinner.setAdapter(adapter);


    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permission granted, we can loadSnaps
                    retrieveSnaps();
                } else {
                    //Permission not granted, go back to the home page
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);

                }
                return;
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void retrieveSnaps() {

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location result) {
                        // Got last known location. In some rare situations this can be null.
                        if (result != null) {
                            // Logic to handle location object
                            mCurrentLocation = result;
                            getSupportLoaderManager().restartLoader(0, null, mContext);
                        }
                    }
                });

    }


    @NonNull
    @Override
    public Loader<List<Snap>> onCreateLoader(int id, @Nullable Bundle args) {


        return new SnapLoader(this, mCurrentLocation, mScope);
    }



    /*
    @Override
    public void onLoadFinished(@NonNull Loader<BookList> loader, BookList books) {

        mBookList.clear();
        if (books.getTotalItems() != 0)
    *        mBookList.addAll(books.getItems());
        mAdapter.notifyDataSetChanged();



    }
     *
    * */




    @Override
    public void onLoadFinished(@NonNull Loader<List<Snap>> loader, List<Snap> snaps) {


        mSnpas.addAll(snaps);
        mAdapter.notifyDataSetChanged();
        mSpinner.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Snap>> loader) {

    }
}
