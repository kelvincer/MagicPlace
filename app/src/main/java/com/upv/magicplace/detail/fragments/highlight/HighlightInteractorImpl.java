package com.upv.magicplace.detail.fragments.highlight;

public class HighlightInteractorImpl implements HighlightInteractor {


    private HighlightRepository highlightRepository;

    public HighlightInteractorImpl(HighlightRepository highlightRepository) {
        this.highlightRepository = highlightRepository;
    }

    @Override
    public void executeGetPhotos(String placeId) {
        highlightRepository.getPhotos(placeId);
    }

}
