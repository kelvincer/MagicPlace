package com.example.proyectomaster.search.activity.ui;

import com.example.proyectomaster.search.entities.Result;

import java.util.List;

public interface SearchActivityView {

    void setPlaces(List<Result> data);

    void showProgressBar();

    void hideProgressBar();
}
