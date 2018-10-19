package com.webakruti.nirmalrail.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.webakruti.nirmalrail.R;
import com.webakruti.nirmalrail.adapter.MyRequestStatusAdapter;
import com.webakruti.nirmalrail.fragments.AdminColonyStatusFragment;
import com.webakruti.nirmalrail.fragments.AdminStationStatusFragment;
import com.webakruti.nirmalrail.utils.SharedPreferenceManager;
import com.webakruti.nirmalrail.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class AdminHomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    public static final String NEW = "new";
    public static final String IN_PROGRESS = "inprocess";
    public static final String COMPLETED = "complete";
    public static final String INVALID = "invalid";


    private ImageView imageViewAdminLogout;


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
    private LinearLayout linearLayoutNewStatus;
    private LinearLayout linearLayoutInprogressStatus;
    private LinearLayout linearLayoutCompletedStatus;
    private LinearLayout linearLayoutInvalidStatus;
    private TextView textViewInvalid;
    private TextView textViewCompleted;
    private TextView textViewInProgress;
    private TextView textViewNew;

    String selectedStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        imageViewAdminLogout = (ImageView) findViewById(R.id.imageViewAdminLogout);


        linearLayoutNewStatus = (LinearLayout) findViewById(R.id.linearLayoutNewStatus);
        linearLayoutNewStatus.setOnClickListener(this);

        linearLayoutInprogressStatus = (LinearLayout) findViewById(R.id.linearLayoutInprogressStatus);
        linearLayoutInprogressStatus.setOnClickListener(this);

        linearLayoutCompletedStatus = (LinearLayout) findViewById(R.id.linearLayoutCompletedStatus);
        linearLayoutCompletedStatus.setOnClickListener(this);

        linearLayoutInvalidStatus = (LinearLayout) findViewById(R.id.linearLayoutInvalidStatus);
        linearLayoutInvalidStatus.setOnClickListener(this);


        textViewInvalid = (TextView) findViewById(R.id.textViewInvalid);
        textViewCompleted = (TextView) findViewById(R.id.textViewCompleted);
        textViewInProgress = (TextView) findViewById(R.id.textViewInProgress);
        textViewNew = (TextView) findViewById(R.id.textViewNew);


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


        // call by default New
        // no need to pass new first time, its assignning in fragments
        this.selectedStatus = NEW;
        textViewNew.setTextSize(Utils.pixelToDp(AdminHomeActivity.this, 42));
        textViewNew.setTypeface(null, Typeface.BOLD);

        textViewInProgress.setTextSize(Utils.pixelToDp(AdminHomeActivity.this, 36));
        textViewInProgress.setTypeface(null, Typeface.NORMAL);

        textViewCompleted.setTextSize(Utils.pixelToDp(AdminHomeActivity.this, 36));
        textViewCompleted.setTypeface(null, Typeface.NORMAL);

        textViewInvalid.setTextSize(Utils.pixelToDp(AdminHomeActivity.this, 36));
        textViewInvalid.setTypeface(null, Typeface.NORMAL);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linearLayoutNewStatus:
                callGetDataAPI(NEW);
                break;
            case R.id.linearLayoutInprogressStatus:
                callGetDataAPI(IN_PROGRESS);

                break;
            case R.id.linearLayoutCompletedStatus:
                callGetDataAPI(COMPLETED);

                break;
            case R.id.linearLayoutInvalidStatus:
                callGetDataAPI(INVALID);

                break;
        }

    }

    private void callGetDataAPI(String statusType) {
        this.selectedStatus = statusType;

        switch (statusType) {

            case NEW:
                textViewNew.setTextSize(Utils.pixelToDp(AdminHomeActivity.this, 42));
                textViewNew.setTypeface(null, Typeface.BOLD);

                textViewInProgress.setTextSize(Utils.pixelToDp(AdminHomeActivity.this, 36));
                textViewInProgress.setTypeface(null, Typeface.NORMAL);

                textViewCompleted.setTextSize(Utils.pixelToDp(AdminHomeActivity.this, 36));
                textViewCompleted.setTypeface(null, Typeface.NORMAL);

                textViewInvalid.setTextSize(Utils.pixelToDp(AdminHomeActivity.this, 36));
                textViewInvalid.setTypeface(null, Typeface.NORMAL);

                viewPager.setCurrentItem(0);
                onPageSelected(0);
                break;

            case IN_PROGRESS:

                textViewNew.setTextSize(Utils.pixelToDp(AdminHomeActivity.this, 36));
                textViewNew.setTypeface(null, Typeface.NORMAL);

                textViewInProgress.setTextSize(Utils.pixelToDp(AdminHomeActivity.this, 42));
                textViewInProgress.setTypeface(null, Typeface.BOLD);

                textViewCompleted.setTextSize(Utils.pixelToDp(AdminHomeActivity.this, 36));
                textViewCompleted.setTypeface(null, Typeface.NORMAL);

                textViewInvalid.setTextSize(Utils.pixelToDp(AdminHomeActivity.this, 36));
                textViewInvalid.setTypeface(null, Typeface.NORMAL);

                viewPager.setCurrentItem(0);
                onPageSelected(0);
                break;

            case COMPLETED:

                textViewNew.setTextSize(Utils.pixelToDp(AdminHomeActivity.this, 36));
                textViewNew.setTypeface(null, Typeface.NORMAL);

                textViewInProgress.setTextSize(Utils.pixelToDp(AdminHomeActivity.this, 36));
                textViewInProgress.setTypeface(null, Typeface.NORMAL);

                textViewCompleted.setTextSize(Utils.pixelToDp(AdminHomeActivity.this, 42));
                textViewCompleted.setTypeface(null, Typeface.BOLD);

                textViewInvalid.setTextSize(Utils.pixelToDp(AdminHomeActivity.this, 36));
                textViewInvalid.setTypeface(null, Typeface.NORMAL);

                viewPager.setCurrentItem(0);
                onPageSelected(0);
                break;

            case INVALID:

                textViewNew.setTextSize(Utils.pixelToDp(AdminHomeActivity.this, 36));
                textViewNew.setTypeface(null, Typeface.NORMAL);

                textViewInProgress.setTextSize(Utils.pixelToDp(AdminHomeActivity.this, 36));
                textViewInProgress.setTypeface(null, Typeface.NORMAL);

                textViewCompleted.setTextSize(Utils.pixelToDp(AdminHomeActivity.this, 36));
                textViewCompleted.setTypeface(null, Typeface.NORMAL);

                textViewInvalid.setTextSize(Utils.pixelToDp(AdminHomeActivity.this, 42));
                textViewInvalid.setTypeface(null, Typeface.BOLD);

                viewPager.setCurrentItem(0);
                onPageSelected(0);

                break;

        }
    }


    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.adminViewpager);
        setupViewPager(viewPager, NEW);


    }

    private void setupViewPager(ViewPager viewPager, String typeStatus) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        AdminStationStatusFragment adminStationFragment = new AdminStationStatusFragment();

        AdminColonyStatusFragment adminColonyFragment = new AdminColonyStatusFragment();

        adapter.addFragment(adminStationFragment, "STATION");
        adapter.addFragment(adminColonyFragment, "COLONY");

        viewPager.setAdapter(adapter);


        tabLayout = (TabLayout) findViewById(R.id.adminTabs);
        tabLayout.setupWithViewPager(viewPager);
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

        if (position == 0) {
            // Station
            ((AdminStationStatusFragment) adapter.getItem(position)).onRefresh(selectedStatus);

        } else if (position == 1) {
            // colony
            ((AdminColonyStatusFragment) adapter.getItem(position)).onRefresh(selectedStatus);

        }


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
