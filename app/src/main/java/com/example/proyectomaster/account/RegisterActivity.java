package com.example.proyectomaster.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.proyectomaster.CommonHelper;
import com.example.proyectomaster.Helper;
import com.example.proyectomaster.R;
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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.tie_email)
    TextInputEditText tieEmail;
    @BindView(R.id.tie_password)
    TextInputEditText tiePassword;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.til_email)
    TextInputLayout tilEmail;
    @BindView(R.id.til_password)
    TextInputLayout tilPassword;
    @BindView(R.id.btn_google_init_session)
    Button btnGoogleInitSession;
    @BindView(R.id.pgn_register)
    ProgressBar pgnRegister;

    private String email = "";
    private String password = "";

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private static final int RC_GOOGLE_SIGN_IN = 123;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setupViews();
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
                    errorMessage("Error de autentificación con Google");
                }
            }
        }
    }

    private void setupViews() {
        getSupportActionBar().setTitle("Registrarse");
        btnRegister.setOnClickListener(this);
        btnGoogleInitSession.setOnClickListener(this);

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                registerUser();
                break;
            case R.id.btn_google_init_session:
                authenticateGoogle();
                break;
            default:
                throw new IllegalArgumentException("Illegal view id");
        }
    }

    private void registerUser() {
        Helper.hideKeyboard(this, tilEmail.getWindowToken());
        if (checkValues()) {
            pgnRegister.setVisibility(View.VISIBLE);
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            pgnRegister.setVisibility(View.INVISIBLE);
                            if (task.isSuccessful()) {
                                successMessage("SUCCESSFUL");
                            } else {
                                errorMessage(task.getException().getLocalizedMessage());
                            }
                        }
                    });
        }
    }

    private void errorMessage(String message) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(CommonHelper.MESSAGE, message);
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }

    private boolean checkValues() {
        email = tieEmail.getText().toString();
        password = tiePassword.getText().toString();
        tilEmail.setError(null);
        tilPassword.setError(null);
        if (email.isEmpty()) {
            tilEmail.setError("Introduce un correo");
        } else if (!email.matches(".+@.+[.].+")) {
            tilEmail.setError("Correo no válido");
        } else if (password.isEmpty()) {
            tilPassword.setError("Introduce una contraseña");
        } else if (password.length() < 6) {
            tilPassword.setError("Ha de contener al menos 6 caracteres");
        } else {
            return true;
        }
        return false;
    }

    private void successMessage(String mensaje) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(CommonHelper.MESSAGE, mensaje);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection Failed", Toast.LENGTH_SHORT).show();
    }

    public void authenticateGoogle() {
        Intent i = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(i, RC_GOOGLE_SIGN_IN);
    }

    private void googleAuth(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(
                acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            errorMessage(task.getException().getLocalizedMessage());
                        } else {
                            successMessage("Autenticación correcta");
                        }
                    }
                });
    }
}
