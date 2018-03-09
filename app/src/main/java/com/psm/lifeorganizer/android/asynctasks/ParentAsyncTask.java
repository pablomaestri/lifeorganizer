package com.psm.lifeorganizer.android.asynctasks;

import android.app.Activity;
import android.os.AsyncTask;

import com.psm.lifeorganizer.R;
import com.psm.lifeorganizer.util.InternetUtil;
import com.psm.lifeorganizer.util.MessageUtil;

/**
 * Created by pmaestri on 3/15/2017.
 */

public class ParentAsyncTask<T,U,V> extends AsyncTask<T, U, V> {

    protected Activity activity;
    protected boolean hasInternet;

    protected boolean validateInternet() {
        hasInternet = InternetUtil.isOnline(activity);
        return hasInternet;
    }

    @Override
    protected V doInBackground(T... ts) {
        validateInternet();
        return null;
    }

    @Override
    protected void onPostExecute(V v) {
        if (!hasInternet) {
            MessageUtil.showToast(activity, R.string.message_error_internet);
        }
    }



}
