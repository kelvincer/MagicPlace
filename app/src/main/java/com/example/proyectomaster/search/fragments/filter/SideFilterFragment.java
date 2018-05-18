package com.example.proyectomaster.search.fragments.filter;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.proyectomaster.CommonHelper;
import com.example.proyectomaster.R;
import com.example.proyectomaster.search.activity.ui.SearchActivity;
import com.example.proyectomaster.search.fragments.search_by_loc.ui.LocalizationSearchFragment;
import com.example.proyectomaster.search.fragments.search_by_text.ui.SearchByTextFragment;

import belka.us.androidtoggleswitch.widgets.ToggleSwitch;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class SideFilterFragment extends Fragment {

    @BindView(R.id.tls_source)
    ToggleSwitch tlsSource;
    @BindView(R.id.tls_form)
    ToggleSwitch tlsForm;
    Unbinder unbinder;

    public SideFilterFragment() {
        // Required empty public constructor
    }

    public static SideFilterFragment newInstance() {
        SideFilterFragment fragment = new SideFilterFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_side_filter, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupToggleListeners();
    }

    private void setupToggleListeners() {

        tlsSource.setOnToggleSwitchChangeListener(new ToggleSwitch.OnToggleSwitchChangeListener() {
            @Override
            public void onToggleSwitchChangeListener(int position, boolean isChecked) {
                if (position == 0) {
                    CommonHelper.SOURCE = 1;
                } else if (position == 1) {
                    CommonHelper.SOURCE = 2;
                } else {
                    Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tlsForm.setOnToggleSwitchChangeListener(new ToggleSwitch.OnToggleSwitchChangeListener() {

            @Override
            public void onToggleSwitchChangeListener(int position, boolean isChecked) {
                if (position == 0) {
                    changeToolbar(SearchByTextFragment.newInstance());
                    clearRecycleview();
                    CommonHelper.SEARCH_MODE = 1;
                } else if (position == 1) {
                    changeToolbar(LocalizationSearchFragment.newInstance());
                    clearRecycleview();
                    CommonHelper.SEARCH_MODE = 2;
                } else {
                    Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void changeToolbar(Fragment f) {
        getFragmentManager().beginTransaction()
                .replace(R.id.search_head_container, f)
                .commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void clearRecycleview() {
        ((SearchActivity) getActivity()).clearData();
    }
}
