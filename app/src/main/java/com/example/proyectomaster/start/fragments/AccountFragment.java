package com.example.proyectomaster.start.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.proyectomaster.CommonHelper;
import com.example.proyectomaster.R;
import com.example.proyectomaster.account.InitSessionActivity;
import com.example.proyectomaster.account.RegisterActivity;
import com.example.proyectomaster.start.InitActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AccountFragment extends Fragment implements View.OnClickListener {

    private static String TAG = AccountFragment.class.getSimpleName();
    private final int REGISTER_REQUEST_CODE = 100;
    private final int INIT_SESSION_REQUEST_CODE = 101;

    Unbinder unbinder;
    @BindView(R.id.btn_create_account)
    Button btnCreateAccount;
    @BindView(R.id.btn_google_init_session)
    Button btnInitSession;
    Activity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity)
            activity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_create_account:
                gotoRegisterActivity();
                break;
            case R.id.btn_google_init_session:
                gotoInitSessionActivity();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REGISTER_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    ((InitActivity) activity).loadFragment(new LoggedFragment());
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    if (data != null) {
                        String message = data.getStringExtra(CommonHelper.MESSAGE);
                        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
                    }
                }
                break;
            case INIT_SESSION_REQUEST_CODE:
                if (resultCode == Activity.RESULT_CANCELED) {
                    if (data != null) {
                        String message = data.getStringExtra(CommonHelper.MESSAGE);
                        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
                    }
                } else if (resultCode == Activity.RESULT_OK) {
                    ((InitActivity) activity).loadFragment(new LoggedFragment());
                }
                break;
            default:
                throw new IllegalArgumentException("Illegal request code");
        }
    }

    private void setupViews() {
        btnCreateAccount.setOnClickListener(this);
        btnInitSession.setOnClickListener(this);
    }

    private void gotoInitSessionActivity() {
        startActivityForResult(new Intent(getContext(), InitSessionActivity.class), INIT_SESSION_REQUEST_CODE);
    }

    private void gotoRegisterActivity() {
        startActivityForResult(new Intent(getContext(), RegisterActivity.class), REGISTER_REQUEST_CODE);
    }
}
