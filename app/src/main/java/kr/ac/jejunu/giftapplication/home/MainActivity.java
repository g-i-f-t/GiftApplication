/**
 * Author. Aerain
 * SSLAB
 * Jeju Nation University.
 */

package kr.ac.jejunu.giftapplication.home;

import android.os.Bundle;

import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import kr.ac.jejunu.giftapplication.GiftApplication;
import kr.ac.jejunu.giftapplication.R;
import kr.ac.jejunu.giftapplication.home.adapter.FundingViewPagerAdapter;
import kr.ac.jejunu.giftapplication.home.fragment.AvailableFundingFragment;
import kr.ac.jejunu.giftapplication.home.fragment.CompleteFundingFragment;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ViewPager fundingViewPager;
    private TabLayout fundingTableLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setDrawerSettings();
        setTabLayout();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        fundingTableLayout = findViewById(R.id.funding_tab);
        fundingViewPager = findViewById(R.id.funding_pager);
    }

    private void setTabLayout() {
        FundingViewPagerAdapter adapter = setFundingViewPagerAdapter();
        fundingViewPager.setAdapter(adapter);
        fundingViewPager.setPageMargin((int) getResources().getDimension(R.dimen.view_pager_gap));
        fundingViewPager.setOffscreenPageLimit(2);
        fundingTableLayout.setupWithViewPager(fundingViewPager);
    }

    private FundingViewPagerAdapter setFundingViewPagerAdapter() {
        FundingViewPagerAdapter adapter = new FundingViewPagerAdapter(getSupportFragmentManager());
        adapter.addPage(AvailableFundingFragment.newInstance(), getResources().getString(R.string.availableFunding));
        adapter.addPage(CompleteFundingFragment.newInstance(), getResources().getString(R.string.completeFunding));
        return adapter;
    }

    private void setDrawerSettings() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerLayout = navigationView.getHeaderView(0);
        setUserInfo(headerLayout);
    }

    private void setUserInfo(View headerLayout) {
        String userInfo = ((GiftApplication) getApplication()).userInfo();
        TextView userId = headerLayout.findViewById(R.id.user_id);
        userId.setText(userInfo);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
