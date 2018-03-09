package com.psm.lifeorganizer.factories;

import com.psm.lifeorganizer.models.Activity;
import com.psm.lifeorganizer.models.ActivityItem;
import com.psm.lifeorganizer.models.Enum.ActivityStyle;
import com.psm.lifeorganizer.models.Enum.ActivityType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pmaestri on 2/15/2018.
 */

public class ActivityFactory {

    public Activity createEmpty() {
        Activity activity = new Activity();
        activity.setItems(new ArrayList<ActivityItem>());
        return activity;
    }

    public Activity create(Integer id, String name, String description, Date activityDate,
                           List<ActivityItem> items, boolean completed, ActivityStyle activityStyle,
                           ActivityType activityType, String color, Date createdDate) {

        Activity activity = new Activity();
        activity.setId(id);
        activity.setName(name);
        activity.setDescription(description);
        activity.setActivityDate(activityDate);
        activity.setItems(items);
        activity.setCompleted(completed);
        activity.setActivityStyle(activityStyle);
        activity.setActivityType(activityType);
        activity.setColor(color);
        if (createdDate==null) {
            createdDate = new Date();
        }

        activity.setCreatedDate(createdDate);
        activity.setModDate(new Date());

        return activity;
    }

}
