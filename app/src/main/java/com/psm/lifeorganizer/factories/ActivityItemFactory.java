package com.psm.lifeorganizer.factories;

import com.psm.lifeorganizer.models.ActivityItem;

import java.util.Date;

/**
 * Created by pmaestri on 2/22/2018.
 */

public class ActivityItemFactory {

    public ActivityItem create(Integer id, String name, String description, String quantity,
                               Double priceUnit, boolean completed, Date createdDate) {

        ActivityItem item = new ActivityItem();

        item.setId(id);
        item.setName(name);
        item.setDescription(description);
        item.setQuantity(quantity);
        item.setPriceUnit(priceUnit);
        item.setCompleted(completed);
        Date today = new Date();
        if (createdDate==null) {
            item.setCreatedDate(today);
        }
        else {
            item.setCreatedDate(createdDate);
        }
        item.setModDate(today);

        return item;

    }

}
