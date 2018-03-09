package com.psm.lifeorganizer.services;

import com.psm.lifeorganizer.models.Activity;

import java.util.List;

/**
 * Created by pmaestri on 1/31/2018.
 */

public interface ActivityService {

    List<Activity> getActivities(boolean completed, int page);
    Activity getActivity(int activityId);
    void saveActivity(Activity activity);
    void removeActivity(Activity activity);
}
