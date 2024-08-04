package com.example.techgirls.RegistrationClasses;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.techgirls.HelpClasses.ShowPages;
import com.example.techgirls.Pages.RegisterPage;
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

public class GoogleSignInHelper {
    private GoogleSignInClient googleSignInClient;
    private final FirebaseAuth firebaseAuth;
    private Activity activity;
    private GoogleSignInListener listener;
    private final int RC_SIGN_IN=40;
    private final DatabaseManager databaseManager=new DatabaseManager();

    public interface GoogleSignInListener {
        void onGoogleSignInSuccess(FirebaseUser user);
        void onGoogleSignInFailure(String errorMessage);
    }

    public GoogleSignInHelper(Activity activity, GoogleSignInListener listener) {
        this.activity = activity;
        this.listener = listener;
        this.firebaseAuth = FirebaseAuth.getInstance();
        initGoogleSignIn();
    }

    private void initGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(activity, gso);
    }

    public void signIn() {
        googleSignInClient.signOut().addOnCompleteListener(task -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            activity.startActivityForResult(signInIntent, RC_SIGN_IN);
        });
    }

    public void handleSignInResult(Intent data) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            signInWithGoogleIdToken(account.getIdToken());
        } catch (ApiException e) {
            listener.onGoogleSignInFailure("Google sign-in failed");
        }
    }
    public void signInWithGoogleIdToken(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user != null) {
                                databaseManager.checkEmailExistence(activity, user,
                                        // Действия, если пользователь существует в базе
                                        () -> {
                                            firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@androidx.annotation.NonNull Task<AuthResult> task) {
                                                    databaseManager.authenticateUser(activity,user.getEmail());
                                                }
                                            });
                                        },
                                        // Действия, если пользователь не существует в базе
                                        () -> {
                                            RegisterPage r=new RegisterPage();
                                            r.showDialogForAdditionalInfo(activity,user);
                                        }
                                );
                            } else {
                                listener.onGoogleSignInFailure("Firebase authentication failed");
                            }
                        } else {
                            listener.onGoogleSignInFailure("Authentication Failed.");
                        }
                    }
                });
    }
}
