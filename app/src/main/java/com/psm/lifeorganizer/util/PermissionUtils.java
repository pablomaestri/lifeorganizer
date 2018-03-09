package com.psm.lifeorganizer.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.View;

import com.psm.lifeorganizer.R;

/**
 * Created by pmaestri on 5/4/2016.
 */
public class PermissionUtils {

    private static int REQUEST_INTERNET = 0;
    public static int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private static int REQUEST_ACCESS_NETWORK_STATE = 3;
    public static int REQUEST_PHONE_CALL = 4;

    //DATA: http://developer.android.com/intl/es/training/permissions/requesting.html

    //TODO: it's an example, INTERNET is always accepted in android M
    public static boolean validateInternetPermission(final Activity context, final View view) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (context.checkSelfPermission(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.INTERNET)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(context,
                        new String[]{Manifest.permission.INTERNET}, REQUEST_INTERNET);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        return false;
    }

    public static boolean validateWriteExternalStoragePermissionForFragment(final Activity context, final View view, final Fragment fragment) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        else {
            if (fragment.shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                Snackbar.make(view, R.string.permission_storage, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            fragment.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
                        }
                    });

            } else {

                // No explanation needed, we can request the permission.
                fragment.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        return false;
    }

    public static boolean validateAccessNetworkState(final Activity context) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (context.checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.ACCESS_NETWORK_STATE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(context,
                        new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, REQUEST_ACCESS_NETWORK_STATE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        return false;
    }

    public static boolean validatePhoneCallPermission(final Activity context, final View view) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (context.checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        else {
            if (context.shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                Snackbar.make(view, R.string.permission_call, Snackbar.LENGTH_INDEFINITE)
                        .setAction(android.R.string.ok, new View.OnClickListener() {
                            @Override
                            @TargetApi(Build.VERSION_CODES.M)
                            public void onClick(View v) {
                                context.requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                            }
                        });

            } else {

                // No explanation needed, we can request the permission.
                context.requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        return false;
    }


}
