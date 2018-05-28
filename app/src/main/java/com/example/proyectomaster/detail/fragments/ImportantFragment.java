package com.example.proyectomaster.detail.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.proyectomaster.CommonHelper;
import com.example.proyectomaster.R;
import com.example.proyectomaster.detail.entities.Result;
import com.willy.ratingbar.ScaleRatingBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ImportantFragment extends Fragment {

    private static final String TAG = ImportantFragment.class.getSimpleName();
    Unbinder unbinder;
    Result result;
    @BindView(R.id.txv_label_count)
    TextView txvLabelCount;
    @BindView(R.id.simpleRatingBar)
    ScaleRatingBar simpleRatingBar;
   /* @BindView(R.id.txv_rating)
    TextView txvRating;*/

    public static Fragment getInstance(Result result) {
        Fragment fragment = new ImportantFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CommonHelper.RESULT, result);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        result = (Result) bundle.getSerializable(CommonHelper.RESULT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_important, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*Log.d(TAG, "ID: " + result.getId());
        Log.d(TAG, "rating: " + result.getRating());
        Log.d(TAG, "name: " + result.getName());*/
        if (result != null && result.getId() != null)
            setupViews();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        result = null;
    }

    private void setupViews() {
        if (result.getRating() != null) {

            Log.d(TAG, "float: " + result.getRating().floatValue());
            //txvRating.setText(result.getRating().toString());
            /*rtbRating.setVisibility(View.VISIBLE);
            rtbRating.setEnabled(true);
            rtbRating.animate();
            //rtbRating.setProgress(3);
            rtbRating.setRating(2.0f*//*result.getRating().floatValue()*//*);*/
            simpleRatingBar.setRating(result.getRating().floatValue());
            txvLabelCount.setText(String.format("Calificado %s de %s", result.getRating(), "5"));
        }
    }
}
