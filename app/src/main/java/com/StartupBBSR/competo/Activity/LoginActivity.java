package com.StartupBBSR.competo.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.StartupBBSR.competo.Listeners.addOnTextChangeListener;
import com.StartupBBSR.competo.R;
import com.StartupBBSR.competo.databinding.ActivityLoginBinding;
import com.StartupBBSR.competo.databinding.AlertlayoutBinding;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding activityLoginBinding;
    private int flag = 0;

    //    Firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseDB;


    private int temp_flag = 0;
    private String btn_clicked = "";

    //    Google
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 123;

    //    Facebook
    private CallbackManager mCallbackManager;
    private static String TAG = "fbdebug";

    private String m_mail = "";
    DocumentSnapshot document;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(activityLoginBinding.getRoot());

//       Hide the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDB = FirebaseFirestore.getInstance();

//        For fb
        mCallbackManager = CallbackManager.Factory.create();

        activityLoginBinding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLogin();
            }
        });

        activityLoginBinding.signupTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSignUp();
            }
        });

        activityLoginBinding.forgotPassTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotPassword();
            }
        });

//        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("email", "public_profile"));


//        Google Sign in
        activityLoginBinding.btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_clicked = "google";
                checkRole();
            }
        });

//        Facebook Sign in
        activityLoginBinding.btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_clicked = "fb";
                checkRole();
            }
        });

        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "onSuccess: ");
                Log.d("fblog", "onSuccess: " + loginResult);
                handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel: ");

            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "onError: ");
                Log.d("fblog", "onError: " + error);
            }
        });

//        TextChangedListeners
        textChangedListener(activityLoginBinding.emailET, activityLoginBinding.emailTIL);
        textChangedListener(activityLoginBinding.passwordET, activityLoginBinding.passwordTIL);
    }

    private void checkRole() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Select Role");
        builder.setMessage("Are you a part of any organization?");

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        temp_flag = 1;
                        Log.d("alertdialogrole", "Role: Organization");
                        if (btn_clicked.equals("google")) {
                            googleSignIn();
                            googleSignInIntent();
                        } else if (btn_clicked.equals("fb")) {
                            Log.d(TAG, "onClick: Yes");
                            LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("email", "public_profile"));
//                            fbLogIn();
                        }

                        dialogInterface.dismiss();
                    }
                }
        );

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        temp_flag = 0;
                        Log.d("alertdialogrole", "Role: User ");

                        if (btn_clicked.equals("google")) {
                            googleSignIn();
                            googleSignInIntent();
                        } else if (btn_clicked.equals("fb")) {
                            Log.d(TAG, "onClick: No");
                            LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("email", "public_profile"));
//                            fbLogIn();
                        }
                        dialogInterface.dismiss();
                    }
                }
        );

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void googleSignIn() {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
    }

    private void googleSignInIntent() {
        Intent googleSignInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(googleSignInIntent, RC_SIGN_IN);
    }

    private void handleFacebookToken(AccessToken accessToken) {
        Log.d(TAG, "handleFacebookToken: ");
        Log.d("fblog", "handleFacebookToken: " + accessToken);

        AuthCredential authCredential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("fblog", "SignInWithCredential: successful");
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    loginWithCredential(user);
                } else {
                    Toast.makeText(getApplicationContext(), "SignInWithCredential: failed\n" + task.getResult(), Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        Result from intent from googleSignIn
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
//                Google Sign In successful
                GoogleSignInAccount account = task.getResult(ApiException.class);

                Log.d("googlesignin: ", "firebaseAuthWithGoogle: " + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Log.d("googlesignin: ", "Google Sign In failed\n" + e.getMessage());
                ;
            }
        }

        if (mCallbackManager.onActivityResult(requestCode, resultCode, data)) {
            return;
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            Sign in success
                            Log.d("googlesignin", "signInWithCredential: success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            loginWithCredential(user);
                        } else {
                            Toast.makeText(getApplicationContext(), "SignInWithCredential: failed", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }

    private void loginWithCredential(FirebaseUser user) {
        String TAG = "cred";

        if (temp_flag == 1) {
            Log.d(TAG, "loginWithCredential: Role: Organizer");
        } else {
            Log.d(TAG, "loginWithCredential: Role: User");
        }

//        To add user to database if not done
        DocumentReference documentReference = firebaseDB.collection("Users")
                .document(firebaseAuth.getCurrentUser().getUid());

        Map<String, Object> userInfo = new HashMap<>();

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    document = task.getResult();
                    userInfo.put("Name", user.getDisplayName());
                    userInfo.put("Email", user.getEmail());
                    userInfo.put("Bio", document.getString("Bio"));
                    userInfo.put("Photo", document.getString("Photo"));
                    userInfo.put("linkedIn", document.getString("linkedIn"));
                    userInfo.put("Phone", document.getString("Phone"));

                } else {
                    userInfo.put("Name", user.getDisplayName());
                    userInfo.put("Email", user.getEmail());
                }
            }
        });


//        Now we check the role selected
        if (temp_flag == 0)
            userInfo.put("isUser", "1");
        else
            userInfo.put("isOrganizer", "1");

        documentReference.update(userInfo);

        if (temp_flag == 0) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        } else {
            startActivity(new Intent(getApplicationContext(), OrganizerActivity.class));
            finish();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    private void onLogin() {
//        Login
//        Check for blank fields
        flag = 0;
        checkEmptyField(activityLoginBinding.emailET, activityLoginBinding.emailTIL);
        checkEmptyField(activityLoginBinding.passwordET, activityLoginBinding.passwordTIL);

        if (flag == 2) {
//            Now we can attempt to login the user
            activityLoginBinding.loginProgressLayout.setVisibility(View.VISIBLE);

            firebaseAuth.signInWithEmailAndPassword(
                    activityLoginBinding.emailET.getText().toString(),
                    activityLoginBinding.passwordET.getText().toString()
            ).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(LoginActivity.this, "LogIn Successful", Toast.LENGTH_SHORT).show();
                    checkUserRoleAndSignIn(firebaseAuth.getCurrentUser().getUid());
                    activityLoginBinding.loginProgressLayout.setVisibility(View.GONE);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(LoginActivity.this, "LogIn Failed:\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    activityLoginBinding.loginProgressLayout.setVisibility(View.GONE);
                }
            });
        }
    }

    private void onSignUp() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    private void forgotPassword() {
        AlertlayoutBinding alertlayoutBinding = AlertlayoutBinding.inflate(getLayoutInflater());

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Forgot Password");
        builder.setMessage("Enter email to continue");
        builder.setView(alertlayoutBinding.getRoot());
        EditText input = alertlayoutBinding.input;

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

//                Get email address from user
                m_mail = input.getText().toString();


//                Send password reset link
                if (!m_mail.isEmpty()) {
                    firebaseAuth.sendPasswordResetEmail(m_mail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Reset link sent to:\n" + m_mail, Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();

    }

    private void checkUserRoleAndSignIn(String uid) {
//        To retrieve data from the database to check role of the user (organizer or user)
        DocumentReference documentReference = firebaseDB
                .collection("Users")
                .document(uid);

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
//            Now this document snapshot will contain the data of the document referenced above
            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                Now to identify the user role
                if (documentSnapshot.getString("isOrganizer") != null) {
//                    organizer role
                    startActivity(new Intent(getApplicationContext(), OrganizerActivity.class));
                    finish();
                } else if (documentSnapshot.getString("isUser") != null) {
//                    User role
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        });
    }

    private void textChangedListener(TextInputEditText ET, TextInputLayout TIL) {
        ET.addTextChangedListener(new addOnTextChangeListener(this, ET, TIL));
    }

    private void checkEmptyField(EditText et, TextInputLayout til) {
        if (et.getText().toString().isEmpty()) {
            til.setError("Field cannot be blank");
            til.setErrorEnabled(true);
            flag--;
        } else {
            flag++;
        }

    }

}