package com.upv.magicplace.detail.activity;

public interface FirebasePhotoRepository {
    void uploadPhoto(byte[] data, String id);
}
