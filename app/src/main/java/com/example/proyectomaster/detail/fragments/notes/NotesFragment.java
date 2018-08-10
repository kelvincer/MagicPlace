package com.example.proyectomaster.detail.fragments.notes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectomaster.R;

import butterknife.ButterKnife;

public class NotesFragment extends Fragment{

    private static final String TAG = NotesFragment.class.getSimpleName();
    public static Fragment getInstance() {

        Fragment fragment = new NotesFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
