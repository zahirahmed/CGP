package com.eleganzit.cgp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;
import com.eleganzit.cgp.adapters.MenuAdapter;
import com.eleganzit.cgp.fragments.BalesListFragment;
import com.eleganzit.cgp.fragments.EditProfileFragment;
import com.eleganzit.cgp.fragments.PurchaseFormFragment;
import com.eleganzit.cgp.fragments.PurchaseListFragment;
import com.eleganzit.cgp.fragments.SalesBalesFormFragment;
import com.eleganzit.cgp.fragments.SeedListFragment;
import com.eleganzit.cgp.fragments.StockFragment;
import com.eleganzit.cgp.models.SlideMenuItem;
import com.eleganzit.cgp.utils.UserLoggedInSession;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    public static TextView txt_title;
    UserLoggedInSession userLoggedInSession;
    public static ImageView share,filter;
    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prefs = getSharedPreferences("com.mycompany.myAppName", MODE_PRIVATE);
        userLoggedInSession=new UserLoggedInSession(this);
        drawer = findViewById(R.id.drawer_layout);
        txt_title = findViewById(R.id.txt_title);
        share = findViewById(R.id.share);
        filter = findViewById(R.id.filter);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_drawer, getTheme());
        toggle.setHomeAsUpIndicator(drawable);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerVisible(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if (prefs.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            prefs.edit().putBoolean("firstrun", false).commit();
            PurchaseFormFragment purchaseFormFragment=new PurchaseFormFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container,purchaseFormFragment).commit();

        }
        else
        {
            PurchaseListFragment purchaseFormFragment=new PurchaseListFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container,purchaseFormFragment).commit();

        }

        final String[] mMenuTitles = { "Purchase", "Sale Bales", "Sale Seed", "Stock", "Settings", "Check Kapas Rate"};


        ListView mDrawerList = findViewById(R.id.left_drawer);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerList.setBackgroundColor(Color.parseColor("#ffffff"));
        drawer.setBackgroundColor(Color.parseColor("#ffffff"));
        ArrayList<SlideMenuItem> drawerItemList = new ArrayList<SlideMenuItem>();
        for( int i = 0; i < mMenuTitles.length; i++ ) {
            SlideMenuItem item = new SlideMenuItem();
            item.setTitle(mMenuTitles[i]);
            drawerItemList.add(item);
        }
        MenuAdapter mMenuAdapter = new MenuAdapter( HomeActivity.this, R.layout.drawer_list_item, drawerItemList);
        mDrawerList.setAdapter(mMenuAdapter);

    }

    public class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            try {
                drawer.closeDrawers();
                SlideMenuItem item =  (SlideMenuItem) parent.getItemAtPosition(position);
                switch (item.getTitle()) {

                    case "Purchase": {

                        PurchaseListFragment purchaseListFragment=new PurchaseListFragment();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .addToBackStack("HomeActivity")
                                .replace(R.id.container,purchaseListFragment)
                                .commit();
                    }
                    break;

                    case "Sale Bales": {
                        BalesListFragment balesListFragment=new BalesListFragment();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .addToBackStack("HomeActivity")
                                .replace(R.id.container,balesListFragment)
                                .commit();
                    }
                    break;

                    case "Sale Seed": {

                        SeedListFragment seedListFragment=new SeedListFragment();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .addToBackStack("HomeActivity")
                                .replace(R.id.container,seedListFragment)
                                .commit();

                    }
                    break;
                    case "Stock": {

                        StockFragment stockFragment=new StockFragment();

                        getSupportFragmentManager()
                                .beginTransaction()
                                .addToBackStack("HomeActivity")
                                .replace(R.id.container,stockFragment)
                                .commit();
                    }
                    break;
                    case "Settings": {

                        EditProfileFragment editProfileFragment=new EditProfileFragment();

                        getSupportFragmentManager()
                                .beginTransaction()
                                .addToBackStack("HomeActivity")
                                .replace(R.id.container,editProfileFragment)
                                .commit();
                    }
                    break;
                    case "Check Kapas Rate": {

                        Intent intent = getPackageManager().getLaunchIntentForPackage("com.eleganzit.cblt");
                        if (intent != null) {
                            // We found the activity now start the activity
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            // Bring user to the market or let them choose an app?
                            intent = new Intent(Intent.ACTION_VIEW);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setData(Uri.parse("market://details?id=" + "com.eleganzit.cblt"));
                            startActivity(intent);
                        }
                    }
                    break;
                    default: {
                    }
                    break;
                }
            } catch (Exception e) {
            }

        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
