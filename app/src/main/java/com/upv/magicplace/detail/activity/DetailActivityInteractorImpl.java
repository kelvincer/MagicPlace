package com.upv.magicplace.detail.activity;

public class DetailActivityInteractorImpl implements DetailActivityInteractor {

    DetailActivityRepository detailActivityRepository;

    public DetailActivityInteractorImpl(DetailActivityRepository detailActivityRepository) {
        this.detailActivityRepository = detailActivityRepository;
    }

    @Override
    public void execute(String placeId) {
        detailActivityRepository.fetchDetail(placeId);
    }
}
