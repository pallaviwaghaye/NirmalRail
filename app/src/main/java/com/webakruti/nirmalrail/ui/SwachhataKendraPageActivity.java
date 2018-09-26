package com.webakruti.nirmalrail.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.webakruti.nirmalrail.R;
import com.webakruti.nirmalrail.fragments.AboutFragment;
import com.webakruti.nirmalrail.fragments.ColonyFragment;
import com.webakruti.nirmalrail.fragments.RailwayFragment;
import com.webakruti.nirmalrail.fragments.SwachhataKendraFragment;
import com.webakruti.nirmalrail.utils.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;


public class SwachhataKendraPageActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {


    private Toolbar toolbar;
    //private DrawerLayout drawerLayout;
    //private NavigationView navigationView;
    private FragmentManager fragManager;

    private ImageView imageViewBack;

    private LinearLayout linearLayoutMyRequest;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swachhata_kendra_page);

        imageViewBack = (ImageView) findViewById(R.id.imageViewBack);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        initViews();
        viewPager.setOnPageChangeListener(this);

    }


    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        RailwayFragment allFragment = new RailwayFragment();
        ColonyFragment liveFragment = new ColonyFragment();

        adapter.addFragment(allFragment, "STATION");
        adapter.addFragment(liveFragment, "COLONY");

        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


}
