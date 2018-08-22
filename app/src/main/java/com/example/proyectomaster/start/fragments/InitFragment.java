package com.example.proyectomaster.start.fragments;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.proyectomaster.R;
import com.example.proyectomaster.search.activity.ui.SearchActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InitFragment extends Fragment {

    @BindView(R.id.lnl_init)
    RelativeLayout lnlInit;
    @BindView(R.id.search_query_section)
    CardView searchQuerySection;
    @BindView(R.id.txv_title)
    TextView txvTitle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_init, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViews();
    }

    private void setupViews() {
        searchQuerySection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(getActivity(),
                                    searchQuerySection,
                                    ViewCompat.getTransitionName(searchQuerySection));
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
            }
        });

        searchQuerySection.post(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                createCenteredReveal(searchQuerySection);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void createCenteredReveal(View view) {
        // Could optimize by reusing a temporary Rect instead of allocating a new one
        Rect bounds = new Rect();
        view.getDrawingRect(bounds);
        int centerX = bounds.centerX();
        int centerY = bounds.centerY();
        int finalRadius = Math.max(bounds.width(), bounds.height());
        Animator animator = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, 0f, finalRadius);
        animator.setDuration(1000);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //txvTitle.animate().y(80).setDuration(200);
                if (getActivity() != null) {
                    Animation animacion = AnimationUtils.loadAnimation(getActivity(), R.anim.animacion);
                    animacion.setFillAfter(true);
                    animacion.setDuration(150);
                    txvTitle.startAnimation(animacion);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
        txvTitle.bringToFront();
    }
}
