package com.dexlabs.extraloyaljuice;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import CustomerList.customerList;
import Feedback.feedback_main;
import GeneralCode.AdvanceActivity;
import GeneralCode.CommonFunctions;
import GeneralCode.CommonVariables;
import Review.ReviewList;
import Settings.KeyVariables;
import Settings.SettingsFragment;

public class MainActivity extends AdvanceActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private View header;
    private FloatingActionButton fab;

    @Override
    protected void AfterViewCreated() {
        loadParentFragment(new feedback_main(), R.id.main_container, true);
    }

    @Override
    protected void loadSettings(SharedPreferences sharedPref) {
        // setting outlet names
        String outletName = sharedPref.getString(KeyVariables.KEY_COMPANY_NAME, getResources().getString(R.string.settings_def_name));
        String outletEmail = sharedPref.getString(KeyVariables.KEY_COMPANY_EMAIL, CommonFunctions.clipString(outletName, new String[]{" "}) + "@email.com");
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(outletName);
        TextView navHeaderName = header.findViewById(R.id.textview_navbar_name);
        TextView navHeaderEmail = header.findViewById(R.id.textview_navbar_email);
        navHeaderName.setText(outletName);
        navHeaderEmail.setText(outletEmail);

        //setting lock

    }

    @Override
    public void hide() {
        fab.hide();
    }

    @Override
    public void show() {
        super.show();
        fab.show();
        loadSettings(PreferenceManager.getDefaultSharedPreferences(this));
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void ViewInitialization() {
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        header = navigationView.getHeaderView(0);
        init();
    }

    private void init() {
        CommonVariables.setCurrentDate();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else advanceBackPress();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Runnable r;
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            r = new Runnable() {
                @Override
                public void run() {
                    loadParentFragment(new SettingsFragment(), R.id.main_container, false);
                }
            };
            checkLock(r);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Runnable r;
        if (id == R.id.nav_item_feedback) {
            loadParentFragment(new feedback_main(), R.id.main_container, true);
        }
        if (id == R.id.nav_item_customer) {
            r = new Runnable() {
                @Override
                public void run() {
                    loadParentFragment(new customerList(), R.id.main_container, true);
                }
            };
            checkLock(r);
        }
        if (id == R.id.nav_item_review) {
            r = new Runnable() {
                @Override
                public void run() {
                    loadParentFragment(new ReviewList(), R.id.main_container, true);
                }
            };
            checkLock(r);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
