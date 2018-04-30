package com.example.proyectomaster.search.fragments.ui;

import com.example.proyectomaster.model_place_api.Result;

import java.util.List;

public interface SearchBytTextView {

    void showMessage();
    void setData(List<Result> results);
}
