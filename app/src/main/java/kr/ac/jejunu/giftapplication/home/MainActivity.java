/**
 * Author. Aerain
 * SSLAB
 * Jeju Nation University.
 */

package kr.ac.jejunu.giftapplication.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.snackbar.SnackbarContentLayout;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import kr.ac.jejunu.giftapplication.GiftApplication;
import kr.ac.jejunu.giftapplication.R;
import kr.ac.jejunu.giftapplication.home.adapter.FundingViewPagerAdapter;
import kr.ac.jejunu.giftapplication.home.fragment.AvailableFundingFragment;
import kr.ac.jejunu.giftapplication.home.fragment.CompleteFundingFragment;
import kr.ac.jejunu.giftapplication.home.fragment.FundingListFragment;
import kr.ac.jejunu.giftapplication.home.fragment.IndeGameFragment;
import kr.ac.jejunu.giftapplication.home.fragment.NewsFeedFragment;
import kr.ac.jejunu.giftapplication.introduction.IntroductionActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setDrawerSettings();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        if(getIntent().getBooleanExtra("onPurchased", false)) {
            openFragment(FundingListFragment.newInstance());
        } else {
            openFragment(NewsFeedFragment.newInstance());
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.home_tab_newsfeed:
                    openFragment(NewsFeedFragment.newInstance());
                    break;
                case R.id.home_tab_gamelist:
                    openFragment(FundingListFragment.newInstance());
                    break;
                case R.id.home_tab_indegame:
                    openFragment(IndeGameFragment.newInstance());
                default:
                    break;
            }
            return true;
        });

    }

    private void openFragment(final Fragment fragment)   {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_navigation_view, fragment);
//        transaction.addToBackStack(null);
        transaction.commit();

    }

    private void setDrawerSettings() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerLayout = navigationView.getHeaderView(0);
        headerLayout.setOnClickListener(v -> {
            Snackbar.make(v, "로그인 버튼 누름.", Snackbar.LENGTH_SHORT).show();
            // TODO 로그인 인텐트 구현.
        });
        setUserInfo(headerLayout);
    }

    private void setUserInfo(View headerLayout) {
        String userInfo = ((GiftApplication) getApplication()).userInfo().getName();
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
        switch(menuItem.getItemId()) {
            case R.id.drawer_investment_display:
                break;
            case R.id.drawer_my_investment_list:
                break;
            case R.id.drawer_announce:
                break;
            case R.id.drawer_use_guide:
                Intent intent = new Intent(this, IntroductionActivity.class);
                startActivity(intent);
                finish();
                break;

            default:
                break;
        }
        return false;
    }
}
