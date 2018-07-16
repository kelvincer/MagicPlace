package com.example.proyectomaster;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.proyectomaster.search.activity.ui.SearchActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InitActivity extends AppCompatActivity {

    @BindView(R.id.lnl_init)
    RelativeLayout lnlInit;
    @BindView(R.id.search_query_section)
    CardView searchQuerySection;
    @BindView(R.id.txv_title)
    TextView txvTitle;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        ButterKnife.bind(this);

        searchQuerySection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InitActivity.this, SearchActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(InitActivity.this,
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
                Animation animacion = AnimationUtils.loadAnimation(InitActivity.this,
                        R.anim.animacion);
                animacion.setFillAfter(true);
                animacion.setDuration(150);
                txvTitle.startAnimation(animacion);
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
