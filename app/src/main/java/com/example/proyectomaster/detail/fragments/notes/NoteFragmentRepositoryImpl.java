package com.example.proyectomaster.detail.fragments.notes;

import android.support.annotation.NonNull;

import com.example.proyectomaster.detail.fragments.notes.entities.Comment;
import com.example.proyectomaster.detail.fragments.notes.events.NoteFragEvent;
import com.example.proyectomaster.lib.EventBus;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class NoteFragmentRepositoryImpl implements NoteFragmentRepository {

    EventBus eventBus;

    public NoteFragmentRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void getNotes(String placeId) {

        final Query query = FirebaseFirestore.getInstance()
                .collection("comentarios")
                .document(placeId)
                .collection("messages")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .limit(20);

        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.getDocuments().size() != 0) {
                    FirestoreRecyclerOptions<Comment> options = new FirestoreRecyclerOptions
                            .Builder<Comment>().setQuery(query, Comment.class).build();
                    post(NoteFragEvent.GET_COMMENTS_SUCCESS, options);
                } else {
                    post(NoteFragEvent.NO_COMMENTS, "No hay comentarios");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                post(NoteFragEvent.ERROR, e.getMessage());
            }
        });
    }

    private void post(int type) {
        post(type, "");
    }

    private void post(int type, String message) {
        NoteFragEvent event = new NoteFragEvent();
        event.setType(type);
        event.setMessage(message);
        eventBus.post(event);
    }

    private void post(int type, FirestoreRecyclerOptions<Comment> options) {
        NoteFragEvent event = new NoteFragEvent();
        event.setOpciones(options);
        event.setType(type);
        eventBus.post(event);
    }
}
