package com.example.proyectomaster.detail.activity;

import com.example.proyectomaster.detail.entities.Result;

public interface SaveFavouriteRepository {

    void saveFavourite(Result resulte);

    void checkIfFavourite(Result result);
}
