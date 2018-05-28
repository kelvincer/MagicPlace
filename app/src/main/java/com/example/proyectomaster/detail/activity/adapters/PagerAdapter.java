package com.example.proyectomaster.detail.activity.adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.proyectomaster.detail.entities.Result;
import com.example.proyectomaster.detail.fragments.ImportantFragment;
import com.example.proyectomaster.detail.fragments.PhotosFragment;


public class PagerAdapter extends FragmentStatePagerAdapter {

    private int numOfTabs;
    private Result result;

    public PagerAdapter(FragmentManager fm, int numOfTabs, Result result) {
        super(fm);
        this.numOfTabs = numOfTabs;
        this.result = result;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return ImportantFragment.getInstance(result);
            case 1:
                return PhotosFragment.getInstance(result);
        }

        return null;
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    public void updateViewPager(Result result) {
        this.result = result;
        notifyDataSetChanged();
    }
}
