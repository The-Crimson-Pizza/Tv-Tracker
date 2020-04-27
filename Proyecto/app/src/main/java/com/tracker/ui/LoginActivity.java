package com.tracker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.tracker.MainActivity;
import com.tracker.R;
import com.tracker.util.Util;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1001;

    private EditText etEmail;
    private EditText etPassword;
    private Button btLogin;
    private Button btRegister;

    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
//      FirebaseAuth.getInstance().signOut(); // LOGOUT

        etEmail = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        btLogin = findViewById(R.id.login);
        btRegister = findViewById(R.id.sign_up);
        SignInButton btGoogleSignIn = findViewById(R.id.google_sign_in_button);
        TextView tvForgetPassword = findViewById(R.id.forget);

        etEmail.addTextChangedListener(getTextWatcher());
        etPassword.addTextChangedListener(getTextWatcher());

        tvForgetPassword.setOnClickListener(v -> {
            if (Util.isNetworkAvailable(this) && !etEmail.getText().toString().isEmpty()) {
                recoverPassword(etEmail.getText().toString());
            } else {
                showToastMessage(getString(R.string.no_network));
            }
        });

        btLogin.setOnClickListener(view -> {
            if (Util.isNetworkAvailable(this) && !etEmail.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty()) {
                login(etEmail.getText().toString(), etPassword.getText().toString());
            } else {
                showToastMessage(getString(R.string.no_network));
            }
        });

        btRegister.setOnClickListener(view -> {
            if (Util.isNetworkAvailable(this) && !etEmail.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty()) {
                createUser(etEmail.getText().toString(), etPassword.getText().toString());
            } else {
                showToastMessage(getString(R.string.no_network));
            }
        });

        btGoogleSignIn.setOnClickListener(view -> {
            if (Util.isNetworkAvailable(this)) {
                configureGoogleClient();
//                signIn Google
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            } else {
                showToastMessage(getString(R.string.no_network));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            showToastMessage(getString(R.string.current_user) + currentUser.getEmail());
            launchMainActivity(currentUser);
        }
    }

    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                showToastMessage(getString(R.string.success_login));
                launchMainActivity(mAuth.getCurrentUser());
            } else {
                String localizedMessage = task.getException().getLocalizedMessage();
                showToastMessage(getString(R.string.login_fail) + localizedMessage);
            }
        });
    }

    private void createUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                showToastMessage(getString(R.string.success_register));
                launchMainActivity(mAuth.getCurrentUser());
            } else {
                String localizedMessage = task.getException().getLocalizedMessage();
                showToastMessage(getString(R.string.register_fail) + localizedMessage);
            }
        });
    }

    private void recoverPassword(String email) {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                showToastMessage(getString(R.string.reset_success));
            } else {
                String localizedMessage = task.getException().getLocalizedMessage();
                showToastMessage(getString(R.string.reset_fail) + localizedMessage);
            }
        });
    }

    private void configureGoogleClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        showToastMessage(getString(R.string.auth_success));
                        launchMainActivity(mAuth.getCurrentUser());
                    } else {
                        showToastMessage(getString(R.string.auth_fail) + task.getException());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                firebaseAuthWithGoogle(task.getResult(ApiException.class));
                showToastMessage(getString(R.string.google_success));
            } catch (ApiException e) {
                showToastMessage(getString(R.string.google_fail) + e);
            }
        }
    }

    private boolean validateFields(String username, String password) {
        if (!isUserNameValid(username)) {
//            editText.requestFocus();
            etEmail.setError(getString(R.string.invalid_email));
            return false;
        }
        if (!isPasswordValid(password)) {
            etPassword.setError(getString(R.string.invalid_password));
            return false;
        }
        return true;
    }

    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(username).matches();
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    @NotNull
    private TextWatcher getTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                btLogin.setEnabled(validateFields(etEmail.getText().toString(), etPassword.getText().toString()));
                btRegister.setEnabled(validateFields(etEmail.getText().toString(), etPassword.getText().toString()));
            }
        };
    }

    private void showToastMessage(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
    }

    private void launchMainActivity(FirebaseUser user) {
        if (user != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}
