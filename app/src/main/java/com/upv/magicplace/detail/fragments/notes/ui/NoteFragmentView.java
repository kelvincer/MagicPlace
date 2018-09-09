package com.upv.magicplace.detail.fragments.notes.ui;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.upv.magicplace.detail.fragments.notes.entities.Comment;

public interface NoteFragmentView {

    void showMessage(String message);

    void displayNotes(FirestoreRecyclerOptions<Comment> options);
}
