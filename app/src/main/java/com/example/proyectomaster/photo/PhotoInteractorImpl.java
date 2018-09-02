package com.example.proyectomaster.photo;

import com.example.proyectomaster.detail.entities.FavoritePhotoModel;

public class PhotoInteractorImpl implements PhotoInteractor {

    PhotoRepository photoRepository;

    public PhotoInteractorImpl(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @Override
    public void deletePhoto(FavoritePhotoModel model, String placeId) {
        photoRepository.deleteFile(model, placeId);
    }
}
