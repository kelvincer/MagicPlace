package com.upv.magicplace.detail.fragments.highlight.ui;

import com.upv.magicplace.detail.entities.FavoritePhotoModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public interface HighlightView {

    void showMessage(String message);

    void setOptions(FirestoreRecyclerOptions<FavoritePhotoModel> options);

    void loadFavoritePhotos();
}
