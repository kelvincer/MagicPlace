package com.example.proyectomaster.start.activities.list;

import com.example.proyectomaster.start.activities.list.entitites.FavouritePlaceModel;

public interface ListFavouriteRepository {

    void getFavouritesByCategory(String category);

    void deleteFavouritePlace(FavouritePlaceModel placeId);
}
