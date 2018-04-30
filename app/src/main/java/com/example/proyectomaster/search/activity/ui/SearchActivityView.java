package com.example.proyectomaster.search.activity.ui;

import com.example.proyectomaster.model_place_api.Result;

import java.util.List;

public interface SearchActivityView {

    void setPlaces(List<Result> data);

    void showProgressBar();
}
