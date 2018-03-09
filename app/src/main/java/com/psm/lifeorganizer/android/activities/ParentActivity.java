package com.psm.lifeorganizer.android.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import com.psm.lifeorganizer.LO1Application;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created by pablo on 18/4/2016.
 */
public class ParentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Fabric crashes = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(true)
                .build();
        Fabric.with(crashes);

        LO1Application application = (LO1Application) getApplication();
        application.getInjector().injectMembers(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
