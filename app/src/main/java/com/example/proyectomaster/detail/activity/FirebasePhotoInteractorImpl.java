package com.example.proyectomaster.detail.activity;

public class FirebasePhotoInteractorImpl implements FirebasePhotoInteractor {

    FirebasePhotoRepository firebasePhotoRepository;

    public FirebasePhotoInteractorImpl(FirebasePhotoRepository firebasePhotoRepository) {
        this.firebasePhotoRepository = firebasePhotoRepository;

    }

    @Override
    public void uploadPhoto(byte[] data, String id) {
        firebasePhotoRepository.uploadPhoto(data, id);
    }
}
