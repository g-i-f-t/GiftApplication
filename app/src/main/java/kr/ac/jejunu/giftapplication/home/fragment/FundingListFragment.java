package kr.ac.jejunu.giftapplication.home.fragment;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;

import kr.ac.jejunu.giftapplication.DisableSwipeViewPager;
import kr.ac.jejunu.giftapplication.R;
import kr.ac.jejunu.giftapplication.home.adapter.FundingViewPagerAdapter;
import kr.ac.jejunu.giftapplication.home.viewmodel.GameListViewModel;

public class FundingListFragment extends Fragment {
    private ViewPager fundingViewPager;
    private TabLayout fundingTableLayout;
    private GameListViewModel mViewModel;
    View view;

    public static FundingListFragment newInstance() {
        return new FundingListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.game_list_fragment, container, false);
        fundingTableLayout = view.findViewById(R.id.funding_tab);
        fundingViewPager = (DisableSwipeViewPager) view.findViewById(R.id.funding_pager);

        return view;
    }

    private void setDrawerSettings() {
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setTabLayout() {
        FundingViewPagerAdapter adapter = setFundingViewPagerAdapter();
        fundingViewPager.setAdapter(adapter);
        fundingViewPager.setPageMargin((int) getResources().getDimension(R.dimen.view_pager_gap));
        fundingViewPager.setOffscreenPageLimit(2);
        fundingTableLayout.setupWithViewPager(fundingViewPager);

    }

    private FundingViewPagerAdapter setFundingViewPagerAdapter() {

        FundingViewPagerAdapter adapter = new FundingViewPagerAdapter(getChildFragmentManager());
        adapter.addPage(AvailableFundingFragment.newInstance(), getResources().getString(R.string.availableFunding));
        adapter.addPage(CompleteFundingFragment.newInstance(), getResources().getString(R.string.completeFunding));
        return adapter;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(GameListViewModel.class);
        setTabLayout();
        setDrawerSettings();
    }
}
