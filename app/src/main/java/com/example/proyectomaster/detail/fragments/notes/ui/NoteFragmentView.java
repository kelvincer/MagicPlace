package com.example.proyectomaster.detail.fragments.notes.ui;

import com.example.proyectomaster.detail.fragments.notes.entities.Comment;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public interface NoteFragmentView {

    void showMessage(String message);

    void displayNotes(FirestoreRecyclerOptions<Comment> options);
}
