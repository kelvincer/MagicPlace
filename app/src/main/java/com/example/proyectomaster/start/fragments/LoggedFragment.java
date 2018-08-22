package com.example.proyectomaster.start.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyectomaster.R;
import com.example.proyectomaster.start.InitActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LoggedFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = LoggedFragment.class.getSimpleName();
    Unbinder unbinder;
    @BindView(R.id.txv_name)
    TextView txvName;
    @BindView(R.id.txv_email)
    TextView txvEmail;
    @BindView(R.id.btn_close_session)
    Button btnCloseSession;
    Activity activity;
    @BindView(R.id.txv_character_name)
    TextView txvCharacterName;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.logged_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
    }

    private void setupViews() {
        btnCloseSession.setOnClickListener(this);
        char initChar = '0';
        String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        if (name == null || name.isEmpty()) {
            txvName.setVisibility(View.GONE);
        } else {
            txvName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            initChar = name.charAt(0);
        }

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        txvEmail.setText(email);

        if (initChar == '0') {
            initChar = email.charAt(0);
        }

        txvCharacterName.setText(String.valueOf(Character.toUpperCase(initChar)));

        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    ((InitActivity) activity).loadFragment(new AccountFragment());
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close_session:
                closeSession();
                break;
            default:
                throw new IllegalArgumentException("Illegal view id");
        }
    }

    private void closeSession() {
        FirebaseAuth.getInstance().signOut();
    }
}
