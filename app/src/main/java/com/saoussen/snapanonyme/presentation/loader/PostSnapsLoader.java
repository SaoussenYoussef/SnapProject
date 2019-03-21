package com.saoussen.snapanonyme.presentation.loader;

import android.content.Context;
import android.location.Location;

import com.saoussen.snapanonyme.presentation.Infrastructure.NetworkUtils;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class PostSnapsLoader extends AsyncTaskLoader {


    private Context context;
    private Location location;
    private File file;


    public PostSnapsLoader(@NonNull Context context,Location location, File file) {
        super(context);
        this.context = context;
        this.location = location;
        this.file = file;
    }

    @Nullable
    @Override
    public Object loadInBackground() {
        NetworkUtils  networkUtils = new NetworkUtils();
        networkUtils.postSnap(location, file);
        return null;
    }

    @Override
    protected void onStartLoading() {

        forceLoad();
        super.onStartLoading();
    }
}
