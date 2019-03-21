package com.saoussen.snapanonyme.presentation.loader;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class PostSnapsLoader extends AsyncTaskLoader {


    public PostSnapsLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public Object loadInBackground() {
        return null;
    }

    @Override
    protected void onStartLoading() {

        forceLoad();
        super.onStartLoading();
    }
}
