package com.webakruti.nirmalrail.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.webakruti.nirmalrail.R;
import com.webakruti.nirmalrail.adapter.MyRequestStatusAdapter;
import com.webakruti.nirmalrail.fragments.MyRequestColonyFragment;
import com.webakruti.nirmalrail.fragments.MyStationsRequestFragment;
import com.webakruti.nirmalrail.utils.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class AdminHomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    private ImageView imageViewAdminLogout;
    private Button buttonNewStatus;
    private Button buttonInprogressStatus;
    private Button buttonCompletedStatus;
    private Button buttonInvalidStatus;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeContainer;
    private ProgressDialog progressDialogForAPI;
    private MyRequestStatusAdapter myRequestStatusAdapter;
    boolean isCallFromPullDown = false;
    private TextView textViewNoData;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;
    private int typeOfComplaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        imageViewAdminLogout = (ImageView) findViewById(R.id.imageViewAdminLogout);
        imageViewAdminLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AdminHomeActivity.this);
                // Setting Dialog Title
                alertDialog.setTitle("Logout");
                // Setting Dialog Message
                alertDialog.setMessage("Are you sure you want to logout?");
                // Setting Icon to Dialog
                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferenceManager.clearPreferences();
                        Intent intent = new Intent(AdminHomeActivity.this, AdminLoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });
                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                // Showing Alert Message
                alertDialog.show();
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        initViews();
        viewPager.setOnPageChangeListener(this);

        // code to show respective tabs
        typeOfComplaint = getIntent().getIntExtra("TYPE_COMPLAINTS", -1);
        if (typeOfComplaint == 0) {
            viewPager.setCurrentItem(0);
        } else if (typeOfComplaint == 1) {
            viewPager.setCurrentItem(1);
        }
    }

    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.adminViewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.adminTabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        MyStationsRequestFragment allFragment = new MyStationsRequestFragment();
        MyRequestColonyFragment liveFragment = new MyRequestColonyFragment();

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
