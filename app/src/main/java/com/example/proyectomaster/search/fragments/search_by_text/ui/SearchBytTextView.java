package com.example.proyectomaster.search.fragments.search_by_text.ui;

import com.example.proyectomaster.search.entities.Result;

import java.util.List;

public interface SearchBytTextView {

    void showMessage();
    void showErrorMessage(String message);
    void setData(List<Result> results);
}
