package com.example.proyectomaster.note;

public interface NoteActivityInteractor {

    void uploadPhotoAndNote(byte[] data, String message, String placeId);

    void uploadOnlyNote(String message, String placeId);

}
