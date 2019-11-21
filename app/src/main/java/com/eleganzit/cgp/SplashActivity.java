package com.eleganzit.cgp;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.cgp.utils.UserLoggedInSession;

import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
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

    }

    @Override
    protected void onResume() {
        super.onResume();

        forceUpdate();

    }

    public void forceUpdate(){
        PackageManager packageManager = this.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo =  packageManager.getPackageInfo(getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String currentVersion = packageInfo.versionName;
        new ForceUpdateAsync(currentVersion,SplashActivity.this).execute();
    }

    public class ForceUpdateAsync extends AsyncTask<String, String, JSONObject> {

        private String latestVersion;
        private String currentVersion;
        private Context context;

        public ForceUpdateAsync(String currentVersion, Context context){
            this.currentVersion = currentVersion;
            this.context = context;
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            try {
                latestVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + getPackageName()+ "&hl=en")
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select("div.hAyfc:nth-child(4) > span:nth-child(2) > div:nth-child(1) > span:nth-child(1)")
                        .first()
                        .ownText();

                Log.d("tysfdds","doInBackground");

            } catch (IOException e) {
                e.printStackTrace();
                Log.d("tysfdds",""+e.getMessage());

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
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
                },2500);

            }
            return new JSONObject();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if(latestVersion!=null){
                if(!currentVersion.equalsIgnoreCase(latestVersion)){
                    // Toast.makeText(context,"update is available.",Toast.LENGTH_LONG).show();
                    Log.d("vvvvvvvvv","1  crnt v : "+currentVersion+"  latest v : "+latestVersion);
                        if(!((Activity)context).isFinishing()){
                            showForceUpdateDialog();
                            Log.d("vvvvvvvvv","2  crnt v : "+currentVersion+"  latest v : "+latestVersion);
                        }
                        else
                        {

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
                            },2500);

                        }

                }
                else
                {

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
                    },2500);

                }
            }
            super.onPostExecute(jsonObject);
        }

        public void showForceUpdateDialog(){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

            alertDialogBuilder.setTitle("Update");
            alertDialogBuilder.setMessage("New Update "+ " " + latestVersion + " is Available");
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.getPackageName())));
                    dialog.cancel();
                }
            });
            alertDialogBuilder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alertDialogBuilder.show();
        }
    }

}
