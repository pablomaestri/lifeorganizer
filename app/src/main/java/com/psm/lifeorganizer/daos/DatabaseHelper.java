package com.psm.lifeorganizer.daos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.psm.lifeorganizer.models.Activity;
import com.psm.lifeorganizer.models.ActivityItem;

import java.sql.SQLException;

/**
 * Created by pmaestri on 4/20/2016.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "beer372mk.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 2;

    // the DAO object we use to access the SimpleData table
    private Dao<Activity, Integer> activityDao = null;
    private RuntimeExceptionDao<Activity, Integer> activityRuntimeDao = null;
    private Dao<ActivityItem, Integer> activityItemDao = null;
    private RuntimeExceptionDao<ActivityItem, Integer> activityItemRuntimeDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Activity.class);
            TableUtils.createTable(connectionSource, ActivityItem.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Activity.class, true);
            TableUtils.dropTable(connectionSource, ActivityItem.class, true);
            TableUtils.createTable(connectionSource, Activity.class);
            TableUtils.createTable(connectionSource, ActivityItem.class);

            // after we drop the old databases, we create the new ones
            //onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the Database Access Object (DAO) for our SimpleData class. It will create it or just give the cached
     * value.
     */
    public Dao<Activity, Integer> getActivityDao() throws SQLException {
        if (activityDao == null) {
            activityDao = getDao(Activity.class);
        }
        return activityDao;
    }

    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our SimpleData class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    public RuntimeExceptionDao<Activity, Integer> getRuntimeActivityDao() {
        if (activityRuntimeDao == null) {
            activityRuntimeDao = getRuntimeExceptionDao(Activity.class);
        }
        return activityRuntimeDao;
    }

    public Dao<ActivityItem, Integer> getActivityItemDao() throws SQLException {
        if (activityItemDao == null) {
            activityItemDao = getDao(ActivityItem.class);
        }
        return activityItemDao;
    }

    public RuntimeExceptionDao<ActivityItem, Integer> getRuntimeActivityItemDao() {
        if (activityItemRuntimeDao == null) {
            activityItemRuntimeDao = getRuntimeExceptionDao(ActivityItem.class);
        }
        return activityItemRuntimeDao;
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        activityDao = null;
        activityRuntimeDao = null;
        activityItemDao = null;
        activityItemRuntimeDao = null;
    }
}