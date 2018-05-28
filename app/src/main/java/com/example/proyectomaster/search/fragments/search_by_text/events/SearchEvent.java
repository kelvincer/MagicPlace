package com.example.proyectomaster.search.fragments.search_by_text.events;

import com.example.proyectomaster.search.entities.Result;

import java.util.List;

public class SearchEvent {

    private int type;
    private List<Result> data;
    private String message;

    public static final int GET_EVENT = 0;
    public static final int ERROR = 1;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
