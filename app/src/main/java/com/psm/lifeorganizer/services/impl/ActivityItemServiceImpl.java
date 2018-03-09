package com.psm.lifeorganizer.services.impl;

import com.crashlytics.android.Crashlytics;
import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import com.psm.lifeorganizer.daos.DatabaseHelper;
import com.psm.lifeorganizer.models.Activity;
import com.psm.lifeorganizer.models.ActivityItem;
import com.psm.lifeorganizer.services.ActivityItemService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pmaestri on 2/26/2018.
 */

public class ActivityItemServiceImpl implements ActivityItemService {

    private DatabaseHelper databaseHelper;

    @Inject
    public void setDatabaseHelper(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    @Override
    public List<ActivityItem> getActivityItems(Activity activity, int page) {
        List<ActivityItem> activityItems = null;
        try {
            Dao<ActivityItem, Integer> activityItemDao = databaseHelper.getActivityItemDao();

            activityItems = activityItemDao.queryForEq("activity_id", activity.getId());

        } catch (SQLException e) {
            Crashlytics.logException(e);
            activityItems = new ArrayList<>();
        }
        catch (Exception e) {
            System.out.print("asas");
        }

        return activityItems;
    }

    @Override
    public ActivityItem getActivityItem(int activityItemId) {
        ActivityItem activityItem = null;
        try {
            Dao<ActivityItem, Integer> activityItemDao = databaseHelper.getActivityItemDao();
            activityItem = activityItemDao.queryForId(activityItemId);

        } catch (SQLException e) {
            Crashlytics.logException(e);
        }
        catch (Exception e) {
            Crashlytics.logException(e);
        }

        return activityItem;
    }

    @Override
    public void saveActivityItem(ActivityItem activityItem) {
        try {
            Dao<ActivityItem, Integer> activityItemDao = databaseHelper.getActivityItemDao();
            activityItemDao.createOrUpdate(activityItem);
        } catch (SQLException e) {
            Crashlytics.logException(e);
        }
        catch (Exception e) {
            Crashlytics.logException(e);
        }
    }

    @Override
    public void removeActivityItem(ActivityItem activityItem) {
        try {
            Dao<ActivityItem, Integer> activityItemDao = databaseHelper.getActivityItemDao();
            activityItemDao.delete(activityItem);
        } catch (SQLException e) {
            Crashlytics.logException(e);
        }
        catch (Exception e) {
            Crashlytics.logException(e);
        }
    }

    @Override
    public void saveActivityItems(Activity activity) {
        try {
            Dao<ActivityItem, Integer> activityItemDao = databaseHelper.getActivityItemDao();
            List<ActivityItem> originalItems = getActivityItems(activity, 1);
            for (ActivityItem item:activity.getItems()) {
                activityItemDao.createOrUpdate(item);
            }
            originalItems.removeAll(activity.getItems());
            for (ActivityItem item:originalItems) {
                removeActivityItem(item);
            }
        } catch (SQLException e) {
            Crashlytics.logException(e);
        }
        catch (Exception e) {
            Crashlytics.logException(e);
        }

    }

    @Override
    public void removeActivityItems(List<ActivityItem> items) {
        try {
            Dao<ActivityItem, Integer> activityItemDao = databaseHelper.getActivityItemDao();
            activityItemDao.delete(items);
        } catch (SQLException e) {
            Crashlytics.logException(e);
        }
        catch (Exception e) {
            Crashlytics.logException(e);
        }
    }
}
