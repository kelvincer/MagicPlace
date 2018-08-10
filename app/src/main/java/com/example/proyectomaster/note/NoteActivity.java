package com.example.proyectomaster.note;

import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.proyectomaster.CommonHelper;
import com.example.proyectomaster.Helper;
import com.example.proyectomaster.R;
import com.example.proyectomaster.note.adapter.ImageAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteActivity extends AppCompatActivity {

    private static final String TAG = NoteActivity.class.getSimpleName();
    private boolean isVisibleKeyboard;
    @BindView(R.id.bottom_sheet)
    LinearLayout bottomSheet;
    BottomSheetBehavior mBottomSheetBehavior;
    @BindView(R.id.gridview)
    GridView gridview;
    @BindView(R.id.editText)
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        ButterKnife.bind(this);

        setupViews();
    }

    private void setupViews() {

        Bundle bundle = getIntent().getExtras();
        getSupportActionBar().setTitle(bundle.getString(CommonHelper.PLACE_NAME));
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        ImageAdapter booksAdapter = new ImageAdapter(this);
        gridview.setAdapter(booksAdapter);

        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePhotoLayout();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.id_take_photo:
                //Toast.makeText(this, "Take photo", Toast.LENGTH_SHORT).show();
                openTakePhoto();
                break;
            case R.id.id_publish:
                //Toast.makeText(this, "Publish", Toast.LENGTH_SHORT).show();
                break;
            default:
                throw new IllegalArgumentException("Invalid menu id");
        }

        return super.onOptionsItemSelected(item);
    }

    private void openTakePhoto() {

        // Falta echarle un vistazp
        Helper.hideKeyboard(this, getCurrentFocus().getWindowToken());
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        }, 100);
        //Falta echarle un vistazo

        /*Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.photos_bottom_up);
        RelativeLayout hiddenPanel = findViewById(R.id.hidden_panel);
        hiddenPanel.startAnimation(bottomUp);
        hiddenPanel.setVisibility(View.VISIBLE);*/

       /* Log.d(TAG, "height " + screenHeight);
        RelativeLayout hiddenPanel = findViewById(R.id.hidden_panel);
        hiddenPanel.setVisibility(View.VISIBLE);

        ObjectAnimator transAnimation = ObjectAnimator.ofInt(hiddenPanel, "translationY", screenHeight, screenHeight / 2);
        transAnimation.setDuration(15000);//
        transAnimation.setInterpolator(new LinearInterpolator());
        //findViewById(R.id.hidden_panel).setVisibility(View.VISIBLE);
        transAnimation.start();*/
    }

    private void closePhotoLayout() {

        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        /*Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.photos_up_bottom);
        RelativeLayout hiddenPanel = findViewById(R.id.hidden_panel);
        hiddenPanel.startAnimation(bottomUp);
        hiddenPanel.setVisibility(View.VISIBLE);*/
        /*int screenHeight = getResources().getDisplayMetrics().heightPixels;

        Log.d(TAG, "height " + screenHeight);
        RelativeLayout hiddenPanel = findViewById(R.id.hidden_panel);
        hiddenPanel.setVisibility(View.VISIBLE);

        ObjectAnimator transAnimation = ObjectAnimator.ofInt(hiddenPanel, "translationY", screenHeight / 2, screenHeight);
        transAnimation.setDuration(2000);//set duration
        transAnimation.start();*/
    }
}
