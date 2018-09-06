package com.example.proyectomaster.detail.activity.adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.example.proyectomaster.detail.entities.Result;
import com.example.proyectomaster.detail.fragments.highlight.ui.HighlightsFragment;
import com.example.proyectomaster.detail.fragments.notes.ui.NotesFragment;
import com.example.proyectomaster.detail.fragments.path.PathFragment;
import com.example.proyectomaster.detail.fragments.photos.PhotosFragment;
import com.example.proyectomaster.lib.ImageLoader;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private int numOfTabs;
    private Result result;
    private ImageLoader imageLoader;

    public ViewPagerAdapter(FragmentManager fm, int numOfTabs, Result result, ImageLoader imageLoader) {
        super(fm);
        this.numOfTabs = numOfTabs;
        this.result = result;
        this.imageLoader = imageLoader;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return HighlightsFragment.getInstance(result);
            case 1:
                return PhotosFragment.getInstance(result);
            case 2:
                return NotesFragment.getInstance(result);
            case 3:
                return PathFragment.getInstance(result);
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
}
