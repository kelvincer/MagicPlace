package com.example.proyectomaster;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.example.proyectomaster.search.activity.ui.SearchActivity;

public class InitActivity extends AppCompatActivity {

    private FloatingSearchView mSearchView;
    CardView searchArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        searchArea = findViewById(R.id.search_query_section);

        searchArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(InitActivity.this, SearchActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(InitActivity.this,
                                searchArea,
                                ViewCompat.getTransitionName(searchArea));
                startActivity(intent, options.toBundle());
            }
        });


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //Here you can get the size!

        Log.d("HEIGHT 1", "" + searchArea.getHeight());
    }
}
