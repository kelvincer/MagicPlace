package com.example.proyectomaster.search.fragments.search_by_loc.ui;

import com.example.proyectomaster.search.entities.Result;

import java.util.List;

public interface LocationSearchView {

    void showMessage();
    void showErrorMessage(String error);
    void setData(List<Result> results);
}
