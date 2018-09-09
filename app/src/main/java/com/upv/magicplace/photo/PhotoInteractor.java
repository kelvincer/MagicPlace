package com.upv.magicplace.photo;

import com.upv.magicplace.detail.entities.FavoritePhotoModel;

public interface PhotoInteractor {

    void deletePhoto(FavoritePhotoModel model, String placeId);
}
