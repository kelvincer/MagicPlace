package com.example.proyectomaster.detail.fragments.notes;

public class NoteFragmentInteractorImpl implements NoteFragmentInteractor {

    NoteFragmentRepository repository;

    public NoteFragmentInteractorImpl(NoteFragmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(String placeId) {
        repository.getNotes(placeId);
    }
}
