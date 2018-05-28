package com.example.proyectomaster.detail.activity.events;

import com.example.proyectomaster.detail.entities.Result;

import java.util.List;

public class DetailActivityEvent {

    private int type;
    private Result data;

    private String message;

    public static final int GET_DETAIL = 0;
    public static final int GET_DETAIL_ERROR = 1;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Result getData() {
        return data;
    }

    public void setData(Result data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
