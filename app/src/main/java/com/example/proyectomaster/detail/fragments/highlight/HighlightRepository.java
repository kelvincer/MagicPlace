package com.example.proyectomaster.detail.fragments.highlight;

public interface HighlightRepository {

    void getPhotos(String id);

    void uploadPhoto(byte[] data, String id);
}
