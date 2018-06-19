package com.example.proyectomaster.search.fragments.search_by_text;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.proyectomaster.CommonHelper;
import com.example.proyectomaster.R;
import com.example.proyectomaster.search.activity.ui.SearchActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TextSearchFragment extends Fragment {

    @BindView(R.id.filter_btn)
    Button filterButton;
    @BindView(R.id.searchTextView)
    EditText searchTextView;
    @BindView(R.id.action_up_btn)
    ImageButton actionUpBtn;
    @BindView(R.id.action_empty_btn)
    ImageButton actionEmptyBtn;
    @BindView(R.id.action_search_btn)
    ImageButton actionSearchBtn;
    Unbinder unbinder;

    public static Fragment newInstance() {
        TextSearchFragment fragment = new TextSearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.search_text, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupSearchView();
    }

    private void setupSearchView() {

        searchTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().isEmpty()) {
                    actionEmptyBtn.setVisibility(View.INVISIBLE);
                    actionSearchBtn.setVisibility(View.VISIBLE);
                } else {
                    actionEmptyBtn.setVisibility(View.VISIBLE);
                    actionSearchBtn.setVisibility(View.INVISIBLE);
                }
            }
        });
        searchTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    newSearch(searchTextView.getText().toString());
                    ((SearchActivity) getActivity()).hideKeyboard();
                    return true;
                }
                return false;
            }
        });
        searchTextView.setText("lima");
        actionEmptyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTextView.setText("");
            }
        });
        actionUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        searchTextView.setSelection(searchTextView.getText().length());
    }

    private void newSearch(String currentQuery) {
        CommonHelper.NEXT_PAGE_TOKEN = null;
        clearData();
        showProgressBar();
        CommonHelper.QUERY = currentQuery;
        hideInfoText();
        ((SearchActivity) getActivity()).getResults(currentQuery);
    }

    private void clearData() {
        ((SearchActivity) getActivity()).clearData();
    }

    private void showProgressBar() {
        ((SearchActivity) getActivity()).showProgressBar();
    }

    private void hideInfoText() {
        ((SearchActivity) getActivity()).hideInfoText();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.filter_btn)
    public void onViewClicked() {
        ((SearchActivity) getActivity()).hideKeyboard();
        ((SearchActivity) getActivity()).openDrawer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}