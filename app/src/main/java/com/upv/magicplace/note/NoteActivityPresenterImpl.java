package com.upv.magicplace.note;

import com.upv.magicplace.lib.EventBus;
import com.upv.magicplace.note.events.NoteEvent;
import com.upv.magicplace.note.ui.NoteActivityView;

import org.greenrobot.eventbus.Subscribe;

public class NoteActivityPresenterImpl implements NoteActivityPresenter {

    private EventBus eventBus;
    private NoteActivityView noteActivityView;
    private NoteActivityInteractor noteActivityInteractor;

    public NoteActivityPresenterImpl(EventBus eventBus, NoteActivityView noteActivityView, NoteActivityInteractor noteActivityInteractor) {
        this.eventBus = eventBus;
        this.noteActivityView = noteActivityView;
        this.noteActivityInteractor = noteActivityInteractor;
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
    public void uploadPhotoAndNote(byte[] data, String message, String placeId) {
        noteActivityView.hideKeyboard();
        noteActivityView.disableActivity();
        noteActivityView.showProgressBar();
        noteActivityInteractor.uploadPhotoAndNote(data, message, placeId);
    }

    @Override
    public void uploadOnlyNote(String message, String placeId) {
        noteActivityView.hideKeyboard();
        noteActivityView.disableActivity();
        noteActivityView.showProgressBar();
        noteActivityInteractor.uploadOnlyNote(message, placeId);
    }

    @Override
    @Subscribe
    public void onEventMainThread(NoteEvent event) {

        noteActivityView.hideProgressBar();
        switch (event.getType()) {

            case NoteEvent.ON_SUCCESS:
                noteActivityView.showMessage("Publicado con éxito");
                break;
            case NoteEvent.ON_ERROR:
                noteActivityView.showMessage(event.getMessage());
                break;
            default:
                throw new IllegalArgumentException("Illegal event id");
        }
    }
}
