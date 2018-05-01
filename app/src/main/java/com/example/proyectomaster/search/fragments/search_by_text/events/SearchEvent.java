package com.example.proyectomaster.search.fragments.search_by_text.events;

import com.example.proyectomaster.model_place_api.Result;

import java.util.List;

public class SearchEvent {

    private int type;
    private List<Result> data;

    public static final int GET_EVENT = 0;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Result> getData() {
        return data;
    }

    public void setData(List<Result> data) {
        this.data = data;
    }

}
