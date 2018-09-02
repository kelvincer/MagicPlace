package com.example.proyectomaster.detail.fragments.highlight.ui;

import com.example.proyectomaster.detail.entities.FavoritePhotoModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public interface HighlightView {

    void showMessage(String message);

    void setOptions(FirestoreRecyclerOptions<FavoritePhotoModel> options);

    void loadFavoritePhotos();
}
