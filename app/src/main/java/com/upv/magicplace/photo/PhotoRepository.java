package com.upv.magicplace.photo;

import com.upv.magicplace.detail.entities.FavoritePhotoModel;

public interface PhotoRepository {

    void deleteFile(FavoritePhotoModel model, String placeId);
}
