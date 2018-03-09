package com.psm.lifeorganizer.services.impl;

import com.crashlytics.android.Crashlytics;
import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import com.psm.lifeorganizer.daos.DatabaseHelper;
import com.psm.lifeorganizer.models.Activity;
import com.psm.lifeorganizer.models.ActivityItem;
import com.psm.lifeorganizer.models.Enum.ActivityStyle;
import com.psm.lifeorganizer.models.Enum.ActivityType;
import com.psm.lifeorganizer.services.ActivityItemService;
import com.psm.lifeorganizer.services.ActivityService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pmaestri on 1/31/2018.
 */

public class ActivityServiceImpl implements ActivityService {

    private DatabaseHelper databaseHelper;
    private ActivityItemService activityItemService;

    @Inject
    public void setDatabaseHelper(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    @Inject
    public void setActivityItemService(ActivityItemService activityItemService) {
        this.activityItemService = activityItemService;
    }

    @Override
    public List<Activity> getActivities(boolean completed, int page) {

        List<Activity> activities = null;
        try {
            Dao<Activity, Integer> activityDao = databaseHelper.getActivityDao();

            activities = activityDao.queryForEq("completed", completed);
            for (Activity activity: activities) {
                List<ActivityItem> items = activityItemService.getActivityItems(activity, 1);
                activity.setItems(items);
            }

        } catch (SQLException e) {
            Crashlytics.logException(e);
            activities = new ArrayList<>();
        }



        return activities;
    }

    @Override
    public Activity getActivity(int activityId) {

        Activity activity = null;
        try {
            Dao<Activity, Integer> activityDao = databaseHelper.getActivityDao();
            activity = activityDao.queryForId(activityId);
            List<ActivityItem> items = activityItemService.getActivityItems(activity, 1);
            activity.setItems(items);

        } catch (SQLException e) {
            Crashlytics.logException(e);
        }

        return activity;
    }

    @Override
    public void saveActivity(Activity activity) {

        try {
            Dao<Activity, Integer> activityDao = databaseHelper.getActivityDao();
            activityDao.createOrUpdate(activity);
            activityItemService.saveActivityItems(activity);

        } catch (SQLException e) {
            Crashlytics.logException(e);
        }

    }

    @Override
    public void removeActivity(Activity activity) {
        try {
            Dao<Activity, Integer> activityDao = databaseHelper.getActivityDao();
            activityItemService.removeActivityItems(activity.getItems());
            activityDao.delete(activity);
        } catch (SQLException e) {
            Crashlytics.logException(e);
        }
    }
}
