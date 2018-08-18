package com.example.proyectomaster.note;

import com.example.proyectomaster.note.events.NoteEvent;

public interface NoteActivityPresenter {

    void onCreate();

    void onDestroy();

    void uploadPhotoAndNote(byte[] data, String message, String placeId);

    void uploadOnlyNote(String message, String placeId);

    void onEventMainThread(NoteEvent event);
}
