package com.psm.lifeorganizer.inject;

import android.content.Context;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import com.psm.lifeorganizer.LO1Application;
import com.psm.lifeorganizer.daos.DatabaseHelper;
import com.psm.lifeorganizer.services.ActivityItemService;
import com.psm.lifeorganizer.services.ActivityService;
import com.psm.lifeorganizer.services.impl.ActivityItemServiceImpl;
import com.psm.lifeorganizer.services.impl.ActivityServiceImpl;


public class LO1Module extends AbstractModule {

	private final LO1Application application;

    public LO1Module(LO1Application application) {
        this.application = application;
    }


    @Override
    protected void configure() {

    	//Services
    	bind(ActivityService.class).to(ActivityServiceImpl.class).in(Singleton.class);
        bind(ActivityItemService.class).to(ActivityItemServiceImpl.class).in(Singleton.class);

        //Helpers
        bind(DatabaseHelper.class).toInstance(new DatabaseHelper(getContext()));

    }

    @Provides
    @Singleton
    protected Context getContext() {
        return application;
    }


}
