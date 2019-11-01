package com.eleganzit.cgp.fragments;


import android.app.ProgressDialog;

import android.content.Intent;
import android.os.Bundle;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.cgp.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUp3Fragment extends Fragment {

    public static EditText ed_mobile;
    public static TextView ed_email;
    private static final String TAG = "logggggggggggggz";
    private static final int RC_SIGN_IN = 1;
    public GoogleApiClient googleApiClient;
    String Token,devicetoken;
    GoogleSignInClient mGoogleSignInClient;

    public SignUp3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_sign_up3, container, false);

        SignInButton signInButton = v.findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);

        ed_email=v.findViewById(R.id.ed_email);
        ed_mobile=v.findViewById(R.id.ed_mobile);

        // Creating and Configuring Google Sign In object.
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), googleSignInOptions);


        // Creating and Configuring Google Api Client.
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity() , new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());

                if(account!=null){
                    signOut();
                }
                else
                {
                    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                    startActivityForResult(signInIntent,RC_SIGN_IN);
                }

            }
        });

        return v;
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                        startActivityForResult(signInIntent,RC_SIGN_IN);
                    }
                });
    }

    @Override
    public void onPause() {
        super.onPause();
        googleApiClient.stopAutoManage(getActivity());
        googleApiClient.disconnect();
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            //updateUI(account);
            Log.d("gmailllll","handleSignInResult");
            Log.d("gmailllll",""+account.getEmail());
            Log.d("gmailllll",""+account.getDisplayName());
            Log.d("gmailllll",""+account.getFamilyName());
            Log.d("gmailllll",""+account.getGivenName());
            Log.d("gmailllll",""+account.getId());
            Log.d("gmailllll",""+account.getIdToken());
            Log.d("gmailllll",""+account.getPhotoUrl());
            ed_email.setText(account.getEmail()+"");


        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("gmailllll", "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("tokenid","onActivityResult");
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.d("tokenid","requestCode == RC_SIGN_IN");
            Log.d("tokenid",""+data.getData());

            Log.d("tokenid",""+result.getStatus()+"  "+result.isSuccess());

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

            if (result.isSuccess()){
                //progressDialog.setMessage("Please wait");
                //progressDialog.show();
                GoogleSignInAccount googleSignInAccount = result.getSignInAccount();

                Log.d("tokenid",""+googleSignInAccount.getId()+" "+googleSignInAccount.getDisplayName()+" "+googleSignInAccount.getFamilyName()+" "+googleSignInAccount.getGivenName());
                final String str_email = googleSignInAccount.getEmail();
                //str_password=googleSignInAccount.getId();
                final String idtoken=googleSignInAccount.getId();


                final String fname=googleSignInAccount.getGivenName();
                final String lname=googleSignInAccount.getFamilyName();
                String profile_pic=googleSignInAccount.getPhotoUrl().toString();

                //tid=googleSignInAccount.getId();

                Log.d("dataaaaaa: "," "+str_email+" "+profile_pic+" ");
                //FirebaseMessaging.getInstance().subscribeToTopic("test");
                //FirebaseInstanceId.getInstance().getToken();
                Thread t=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String Token= FirebaseInstanceId.getInstance().getToken();
                        if (Token!=null)
                        {

                            Log.d("thisismytoken", Token);
                            devicetoken=Token;
                            StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().build();
                            StrictMode.setThreadPolicy(threadPolicy);
                            //getGoogleLogin(str_email,fname,lname,idtoken);

                            //
                            // Toast.makeText(LoginActivity.this, "google login", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Log.d("thisismytoken", "No token"+Token);

                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });t.start();
                //FirebaseUserAuth(googleSignInAccount);
            }
            else
            {
                Log.d("tokenid","else");
                Toast.makeText(getActivity(), "Please try again", Toast.LENGTH_SHORT).show();
            }
            //handleSignInResult(result);
        }
    }

}
