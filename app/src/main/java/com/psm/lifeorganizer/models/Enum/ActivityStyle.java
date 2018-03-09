package com.psm.lifeorganizer.models.Enum;

/**
 * Created by pmaestri on 1/30/2018.
 */

public enum ActivityStyle {

    NORMAL("Normal", 1), TEMPLATE("Template", 2);

    private String name;
    private Integer id;


    ActivityStyle(String name, Integer id) {
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
