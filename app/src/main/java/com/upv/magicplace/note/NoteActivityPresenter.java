package com.upv.magicplace.note;

import com.upv.magicplace.note.events.NoteEvent;

public interface NoteActivityPresenter {

    void onCreate();

    void onDestroy();

    void uploadPhotoAndNote(byte[] data, String message, String placeId);

    void uploadOnlyNote(String message, String placeId);

    void onEventMainThread(NoteEvent event);
}
