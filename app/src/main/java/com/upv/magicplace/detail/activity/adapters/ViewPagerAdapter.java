package com.upv.magicplace.detail.activity.adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.upv.magicplace.detail.entities.Result;
import com.upv.magicplace.detail.fragments.highlight.ui.HighlightsFragment;
import com.upv.magicplace.detail.fragments.notes.ui.NotesFragment;
import com.upv.magicplace.detail.fragments.path.PathFragment;
import com.upv.magicplace.detail.fragments.photos.PhotosFragment;
import com.upv.magicplace.lib.ImageLoader;

import java.util.HashMap;
import java.util.Map;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private int numOfTabs;
    private Result result;
    private ImageLoader imageLoader;
    Map<Integer, Fragment> mPageReferenceMap = new HashMap<>();

    public ViewPagerAdapter(FragmentManager fm, int numOfTabs, Result result, ImageLoader imageLoader) {
        super(fm);
        this.numOfTabs = numOfTabs;
        this.result = result;
        this.imageLoader = imageLoader;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = HighlightsFragment.getInstance(result);
                break;
            case 1:
                fragment = PhotosFragment.getInstance(result);
                break;
            case 2:
                fragment = NotesFragment.getInstance(result);
                break;
            case 3:
                fragment = PathFragment.getInstance(result);
                break;
            default:
                throw new IllegalArgumentException("Illegal position");
        }
        mPageReferenceMap.put(position, fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        mPageReferenceMap.remove(position);
    }

    public Map<Integer, Fragment> getmPageReferenceMap() {
        return mPageReferenceMap;
    }
}
