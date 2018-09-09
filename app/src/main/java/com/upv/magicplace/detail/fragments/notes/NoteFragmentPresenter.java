package com.upv.magicplace.detail.fragments.notes;

import com.upv.magicplace.detail.fragments.notes.events.NoteFragEvent;

public interface NoteFragmentPresenter {

    void onCreate();

    void onDestroy();

    void getNotes(String placeId);

    void onEventMainThread(NoteFragEvent event);
}
