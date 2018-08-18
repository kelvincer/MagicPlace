package com.example.proyectomaster.detail.fragments.notes;

import com.example.proyectomaster.detail.fragments.notes.events.NoteFragEvent;

public interface NoteFragmentPresenter {

    void onCreate();

    void onDestroy();

    void getNotes(String placeId);

    void onEventMainThread(NoteFragEvent event);
}
