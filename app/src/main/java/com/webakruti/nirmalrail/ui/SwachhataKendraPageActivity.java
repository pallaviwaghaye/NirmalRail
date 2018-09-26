package com.webakruti.nirmalrail.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.webakruti.nirmalrail.R;
import com.webakruti.nirmalrail.fragments.AboutFragment;
import com.webakruti.nirmalrail.fragments.SwachhataKendraFragment;
import com.webakruti.nirmalrail.utils.SharedPreferenceManager;


public class SwachhataKendraPageActivity extends AppCompatActivity {


    private Toolbar toolbar;
    //private DrawerLayout drawerLayout;
    //private NavigationView navigationView;
    private FragmentManager fragManager;

    private LinearLayout linearLayoutMyRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swachhata_kendra_page);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        fragManager = getSupportFragmentManager();
        fragManager.beginTransaction().replace(R.id.swachhata_container, new SwachhataKendraFragment()).commit();

    }


}
