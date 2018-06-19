package com.example.proyectomaster.detail.fragments.highlight;

public class HighlightInteractorImpl implements HighlightInteractor {


    private HighlightRepository highlightRepository;

    public HighlightInteractorImpl(HighlightRepository highlightRepository) {
        this.highlightRepository = highlightRepository;
    }

    @Override
    public void execute(String placeId) {
        highlightRepository.getPhotos(placeId);
    }

    @Override
    public void uploadPhoto(byte[] data, String placeId) {
        highlightRepository.uploadPhoto(data, placeId);
    }
}
