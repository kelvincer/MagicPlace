package com.upv.magicplace.start.entities;

import java.io.Serializable;

public class CategoryModel implements Serializable{

    String type_name;

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }
}
