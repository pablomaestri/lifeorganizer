package com.psm.lifeorganizer.models.Enum;

/**
 * Created by pmaestri on 1/30/2018.
 */

public enum QuantityType {

    UNIT("Unit", 1), Liters("Liters", 2), KILOGRAMS("Kilograms", 3), CUSTOM("Custom", 4);

    private String name;
    private Integer id;


    QuantityType(String name, Integer id) {
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
