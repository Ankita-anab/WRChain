package com.example.wrchain.View.Authentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.wrchain.R;
import com.example.wrchain.View.AdminDashboard;
import com.example.wrchain.View.MainActivity;
import com.example.wrchain.View.SavedSharedPreference;
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

public class GoogleSignInActivity extends AppCompatActivity {
    private static final int RC_SIG_IN = 123;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    private FirebaseAuth mAuth;
    FirebaseUser Muser;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_sign_in);
        //configure google SignIn
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Google SignIn....");
        progressDialog.show();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("95558346836-al7h2hsshjoq4qdsbbtrdn4mcfnlcsav.apps.googleusercontent.com")
                .requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        //Firebase Auth Instance
        mAuth = FirebaseAuth.getInstance();
        Muser=mAuth.getCurrentUser();

        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIG_IN);



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIG_IN)
        {

            Task<GoogleSignInAccount> signInAccountTask=GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                firebaseAuthWithGoogle(googleSignInAccount.getIdToken());
            }
            catch (Exception e)
            {

                Toast.makeText(this, "error1", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                finish();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            progressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                        }
                        else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(GoogleSignInActivity.this, "error2", Toast.LENGTH_SHORT).show();
                            finish();

                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        Intent intent=new Intent(GoogleSignInActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
