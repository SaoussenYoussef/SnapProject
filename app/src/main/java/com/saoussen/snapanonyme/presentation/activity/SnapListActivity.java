package com.saoussen.snapanonyme.presentation.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.saoussen.snapanonyme.R;
import com.saoussen.snapanonyme.presentation.adapter.SnapsAdapter;
import com.saoussen.snapanonyme.presentation.loader.SnapLoader;
import com.saoussen.snapanonyme.presentation.model.Snap;

import java.util.ArrayList;
import java.util.List;


public class SnapListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Snap>> {


    List<Snap> mSnpas = new ArrayList<>();
    RecyclerView mRecyclerView;
    SnapsAdapter mAdapter;
    RelativeLayout mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snap_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mSpinner = findViewById(R.id.spinner);
        mRecyclerView = findViewById(R.id.snap_list_recyclerview);
        mAdapter = new SnapsAdapter(mSnpas, this);
        mRecyclerView.setAdapter(mAdapter);
        // par défaut le layoutmanager charge de manière  vertical
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


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

        getSupportLoaderManager().initLoader(0, null, this);
    }

    @NonNull
    @Override
    public Loader<List<Snap>> onCreateLoader(int id, @Nullable Bundle args) {


        return new SnapLoader(this);
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
