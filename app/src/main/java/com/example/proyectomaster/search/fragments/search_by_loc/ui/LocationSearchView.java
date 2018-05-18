package com.example.proyectomaster.search.fragments.search_by_loc.ui;

import com.example.proyectomaster.model_place_api.Result;

import java.util.List;

public interface LocationSearchView {

    void showMessage();
    void showErrorMessage(String error);
    void setData(List<Result> results);
}
