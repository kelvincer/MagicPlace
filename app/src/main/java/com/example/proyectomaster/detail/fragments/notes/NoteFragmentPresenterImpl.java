package com.example.proyectomaster.detail.fragments.notes;

import com.example.proyectomaster.detail.fragments.notes.events.NoteFragEvent;
import com.example.proyectomaster.detail.fragments.notes.ui.NoteFragmentView;
import com.example.proyectomaster.lib.EventBus;

import org.greenrobot.eventbus.Subscribe;

public class NoteFragmentPresenterImpl implements NoteFragmentPresenter {

    EventBus eventBus;
    NoteFragmentView noteFragmentView;
    NoteFragmentInteractor noteFragmentInteractor;

    public NoteFragmentPresenterImpl(EventBus eventBus, NoteFragmentView noteFragmentView, NoteFragmentInteractor noteFragmentInteractor) {
        this.eventBus = eventBus;
        this.noteFragmentView = noteFragmentView;
        this.noteFragmentInteractor = noteFragmentInteractor;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
    }

    @Override
    public void getNotes(String placeId) {
        noteFragmentInteractor.execute(placeId);
    }

    @Override
    @Subscribe
    public void onEventMainThread(NoteFragEvent event) {

        switch (event.getType()) {
            case NoteFragEvent.GET_COMMENTS_SUCCESS:
                noteFragmentView.displayNotes(event.getOpciones());
                break;
            case NoteFragEvent.ERROR:
                noteFragmentView.showMessage(event.getMessage());
                break;
            case NoteFragEvent.NO_COMMENTS:
                noteFragmentView.showMessage(event.getMessage());
                break;
        }
    }
}
