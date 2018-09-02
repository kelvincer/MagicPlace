package com.example.proyectomaster.photo;

import com.example.proyectomaster.detail.entities.FavoritePhotoModel;

public interface PhotoRepository {

    void deleteFile(FavoritePhotoModel model, String placeId);
}
