package com.psm.lifeorganizer;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.psm.lifeorganizer.inject.LO1Module;


public class LO1Application extends MultiDexApplication {

	private Injector injector;

    @Override
    public void onCreate() {
        super.onCreate();
        injector = Guice.createInjector(new LO1Module(this));

    }

    public Injector getInjector() {
        return injector;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        MultiDex.install(newBase);
        super.attachBaseContext(newBase);
    }

}
