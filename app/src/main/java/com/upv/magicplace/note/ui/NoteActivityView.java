package com.upv.magicplace.note.ui;

public interface NoteActivityView {

    void showMessage(String message);

    void showProgressBar();

    void hideProgressBar();

    void hideKeyboard();

    void disableActivity();
}
