package com.upv.magicplace.start.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.crashlytics.android.Crashlytics;
import com.upv.magicplace.R;
import com.upv.magicplace.start.fragments.AccountFragment;
import com.upv.magicplace.start.fragments.favourites.ui.FavouritesFragment;
import com.upv.magicplace.start.fragments.InitFragment;
import com.upv.magicplace.start.fragments.LoggedFragment;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InitActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        ButterKnife.bind(this);

        loadFragment(new InitFragment());
        bottomNavigation.setOnNavigationItemSelectedListener(this);
    }

    public boolean loadFragment(Fragment fragment) {

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commitAllowingStateLoss();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.action_search:
                fragment = new InitFragment();
                break;
            case R.id.action_favorites:
                fragment = new FavouritesFragment();
                break;
            case R.id.action_account:
                if (FirebaseAuth.getInstance().getCurrentUser() == null)
                    fragment = new AccountFragment();
                else
                    fragment = new LoggedFragment();
                break;
        }

        return loadFragment(fragment);
    }
}
