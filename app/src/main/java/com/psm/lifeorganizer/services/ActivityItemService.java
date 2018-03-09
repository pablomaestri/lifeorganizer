package com.psm.lifeorganizer.services;

import com.psm.lifeorganizer.models.Activity;
import com.psm.lifeorganizer.models.ActivityItem;

import java.util.List;

/**
 * Created by pmaestri on 1/31/2018.
 */

public interface ActivityItemService {

    List<ActivityItem> getActivityItems(Activity activity, int page);
    ActivityItem getActivityItem(int activityId);
    void saveActivityItem(ActivityItem activityItem);
    void removeActivityItem(ActivityItem activityItem);

    void saveActivityItems(Activity activity);
    void removeActivityItems(List<ActivityItem> items);
}
