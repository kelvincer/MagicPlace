package com.upv.magicplace.photo;

import com.upv.magicplace.detail.entities.FavoritePhotoModel;

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
