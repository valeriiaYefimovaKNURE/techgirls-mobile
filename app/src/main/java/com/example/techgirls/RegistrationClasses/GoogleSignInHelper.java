package com.example.techgirls.RegistrationClasses;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

import com.example.techgirls.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.checkerframework.checker.nullness.qual.NonNull;

public class GoogleSignInHelper {
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;
    private Activity activity;
    private GoogleSignInListener listener;
    private int RC_SIGN_IN;

    public interface GoogleSignInListener {
        void onGoogleSignInSuccess(FirebaseUser user);
        void onGoogleSignInFailure(String errorMessage);
    }

    public GoogleSignInHelper(Activity activity, GoogleSignInListener listener, int RC_SIGN_IN) {
        this.activity = activity;
        this.listener = listener;
        this.RC_SIGN_IN = RC_SIGN_IN;
        this.firebaseAuth = FirebaseAuth.getInstance();
        initGoogleSignIn();
    }

    private void initGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(activity, gso);
        signIn();
    }

    public void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void handleSignInResult(Intent data) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            firebaseAuthWithGoogle(account.getIdToken());
        } catch (ApiException e) {
            listener.onGoogleSignInFailure("Google sign-in failed");
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    listener.onGoogleSignInSuccess(user);
                } else {
                    listener.onGoogleSignInFailure("Authentication Failed.");
                }
            }
        });
    }
}
