package com.example.proyectomaster.detail.fragments.highlight.ui;

import com.example.proyectomaster.detail.entities.StoragePhoto;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public interface HighlightView {

    void showMessage(String message);

    void setOptions(FirestoreRecyclerOptions<StoragePhoto> options);

    void loadFavoritePhotos();
}
