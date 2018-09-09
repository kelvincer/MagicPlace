package com.upv.magicplace.search.activity.ui;

import com.upv.magicplace.search.entities.Result;

import java.util.List;

public interface SearchActivityView {

    void updatePlaces(List<Result> data);

    void showProgressBar();

    void hideProgressBar();

    void showMessage(String message);

    void hideInfoText();

    void hideKeyboard();

    void clearData();
}
