package com.example.proyectomaster.note;

public class NoteActivityInteractorImpl implements NoteActivityInteractor {

    private NoteActivityRepository noteActivityRepository;

    public NoteActivityInteractorImpl(NoteActivityRepository noteActivityRepository) {
        this.noteActivityRepository = noteActivityRepository;
    }

    @Override
    public void uploadPhotoAndNote(byte[] data, String message, String placeId) {
        noteActivityRepository.uploadPhotoAndNote(data, message, placeId);
    }

    @Override
    public void uploadOnlyNote(String message, String placeId) {
        noteActivityRepository.uploadOnlyNote(message, placeId);
    }
}
