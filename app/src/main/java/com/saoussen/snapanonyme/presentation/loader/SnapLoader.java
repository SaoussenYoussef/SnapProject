package com.saoussen.snapanonyme.presentation.loader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.DropBoxManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Base64;

import com.saoussen.snapanonyme.R;
import com.saoussen.snapanonyme.presentation.Infrastructure.NetworkUtils;
import com.saoussen.snapanonyme.presentation.model.Picture;
import com.saoussen.snapanonyme.presentation.model.Snap;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class SnapLoader extends AsyncTaskLoader<List<Snap>> {

    Context mContext;

    public SnapLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        onForceLoad();
        super.onStartLoading();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public List<Snap> loadInBackground() {


        // TODO request snaps from network

        List<Snap> snaps = new ArrayList<>();

       /* Snap snap = new Snap();

        Picture picture = new Picture();


        for (int i = 0; i <= 10; i++) {
            snap.setDistance(i+1);


            // condition ternaire i % 2 == 0 ?,

            Drawable drawable =
                    this.getContext().getResources().getDrawable(R.drawable.img_swimming, null);


            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            String pictureContent = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
            picture.setContent(pictureContent);
            snap.setPicture(picture);
            snaps.add(snap);


        }*/


      return NetworkUtils.getSnaps();

    }
}
