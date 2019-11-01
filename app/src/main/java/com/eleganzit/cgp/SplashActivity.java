package com.eleganzit.cgp;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.eleganzit.cgp.utils.UserLoggedInSession;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SplashActivity extends AppCompatActivity {

    UserLoggedInSession userLoggedInSession;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        userLoggedInSession=new UserLoggedInSession(this);

        logo=findViewById(R.id.logo);
        Animation expandIn = AnimationUtils.loadAnimation(this, R.anim.pop_anim);
        logo.startAnimation(expandIn);

        Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                if (userLoggedInSession.isLoggedIn())
                {
                    startActivity(new Intent(SplashActivity.this,HomeActivity.class)
                            .putExtra("from","splash")
                    );
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    finish();
                }
                else {
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    finish();

                }

            }
        },3000);

    }
}
