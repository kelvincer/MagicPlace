package com.example.proyectomaster.detail.activity.ui;

import com.example.proyectomaster.detail.entities.Result;

public interface DetailActivityView {

    void showProgressaBar();

    void hideProgressBar();

    void showMessage(String message);

    void setResult(Result result);
}
