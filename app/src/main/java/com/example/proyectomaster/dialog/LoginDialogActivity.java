package com.example.proyectomaster.dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.example.proyectomaster.CommonHelper;
import com.example.proyectomaster.R;
import com.example.proyectomaster.account.RegisterActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginDialogActivity extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private static final int RC_GOOGLE_SIGN_IN = 123;
    @BindView(R.id.container)
    ConstraintLayout container;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_login);
        ButterKnife.bind(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                GoogleSignInResult result = Auth.GoogleSignInApi
                        .getSignInResultFromIntent(data);
                if (result.isSuccess()) {
                    googleAuth(result.getSignInAccount());
                } else {
                    messageOnError("Error de autentificación con Google");
                }
            }
        }
    }

    private void googleAuth(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(
                acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            messageOnError(task.getException().getLocalizedMessage());
                        } else {
                            verificaSiUsuarioValidado();
                        }
                    }
                });
    }

    private void messageOnError(String mensaje) {
        Snackbar.make(container, mensaje, Snackbar.LENGTH_LONG).show();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    public void loginWithGoogle(View v) {
        Intent i = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(i, RC_GOOGLE_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        messageOnError("Error de autentificación con Google");
    }

    private void verificaSiUsuarioValidado() {
        //messageOnError("CORRECT");
        setResult(Activity.RESULT_OK);
        finish();
    }

    public void registerWithEmail(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra(CommonHelper.FROM_ACTIVITY, "LoginDialogActivity");
        startActivity(intent);
        finish();
    }
}
