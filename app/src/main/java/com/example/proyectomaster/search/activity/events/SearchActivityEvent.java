package com.example.proyectomaster.search.activity.events;

import com.example.proyectomaster.model_place_api.Result;

import java.util.List;

public class SearchActivityEvent {

    private int type;

    public static final int GET_EVENT = 0;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
