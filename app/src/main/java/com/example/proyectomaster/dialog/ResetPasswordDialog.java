package com.example.proyectomaster.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.proyectomaster.R;
import com.example.proyectomaster.account.InitSessionActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResetPasswordDialog extends Dialog implements View.OnClickListener {

    @BindView(R.id.tie_email)
    TextInputEditText tieEmail;
    @BindView(R.id.til_email)
    TextInputLayout tilEmail;
    @BindView(R.id.btn_send_email)
    Button btnSendEmail;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.ctl_main)
    ConstraintLayout ctlMain;

    Activity activity;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public ResetPasswordDialog(@NonNull Context context, Activity activity) {
        super(context);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_reset);
        ButterKnife.bind(this);

        setupViews();
    }

    private void setupViews() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthLcl = (int) (displayMetrics.widthPixels * 0.9f);

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) ctlMain.getLayoutParams();
        params.width = widthLcl;

        btnCancel.setOnClickListener(this);
        btnSendEmail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_send_email:
                resetPassword();
                break;
        }
    }

    public void resetPassword() {
        String correo = tieEmail.getText().toString();
        tilEmail.setError("");
        if (correo.isEmpty()) {
            tilEmail.setError("Introduce un correo");
        } else if (!correo.matches(".+@.+[.].+")) {
            tilEmail.setError("Correo no válido");
        } else {
            auth.sendPasswordResetEmail(correo)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                message("Verifica tu correo para cambiar contraseña.");
                            } else {
                                message("Error al mandar correo para cambiar contraseña");
                            }
                        }
                    });
        }
    }

    private void message(String s) {
        dismiss();
        ((InitSessionActivity) activity).showMessage(s);
    }
}
