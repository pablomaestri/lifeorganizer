package com.psm.lifeorganizer.models.Enum;

/**
 * Created by pmaestri on 1/31/2018.
 */

public enum ActivityType {

    ACTION("Action", 1), ELEMENT("Element", 2);

    private String name;
    private Integer id;


    ActivityType(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }
}
