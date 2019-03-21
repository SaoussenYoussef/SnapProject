package com.saoussen.snapanonyme.presentation.loader;

import android.content.Context;
import android.location.Location;
import android.os.Build;

import com.saoussen.snapanonyme.presentation.Infrastructure.Network.AppUtils;
import com.saoussen.snapanonyme.presentation.Infrastructure.NetworkUtils;
import com.saoussen.snapanonyme.presentation.model.SimpleLocation;
import com.saoussen.snapanonyme.presentation.model.Snap;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.loader.content.AsyncTaskLoader;


public class GetSnapsLoader extends AsyncTaskLoader<List<Snap>> {


    Location mCurrentLocation;
    Double mScope;


    public GetSnapsLoader(@NonNull Context context, Location mCurrentLocation, double mScope) {
        super(context);


        this.mCurrentLocation = mCurrentLocation;
        this.mScope = mScope;
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

        List<Snap> snaps = NetworkUtils.getSnaps(mCurrentLocation, mScope);

        for (int i = 0; i < snaps.size(); i++) {
            Snap snap = snaps.get(i);

            SimpleLocation currentSimpleLocation = new SimpleLocation(mCurrentLocation.getLongitude(),
                    mCurrentLocation.getLatitude());
            SimpleLocation snapPostedAtSimpleLocation = snap.getPostedAt();

            double distanceBetweenAsDouble = AppUtils.distanceBetweenAsMeters(currentSimpleLocation, snapPostedAtSimpleLocation, 0, 0);

            snap.setDistance((int) distanceBetweenAsDouble);

        }

        return snaps;





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


    }
}
