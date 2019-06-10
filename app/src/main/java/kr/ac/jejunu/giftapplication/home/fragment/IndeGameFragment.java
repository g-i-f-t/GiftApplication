package kr.ac.jejunu.giftapplication.home.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import kr.ac.jejunu.giftapplication.DisableSwipeViewPager;
import kr.ac.jejunu.giftapplication.R;
import kr.ac.jejunu.giftapplication.home.adapter.FundingViewPagerAdapter;
import kr.ac.jejunu.giftapplication.home.viewmodel.IndeGameViewModel;

public class IndeGameFragment extends Fragment {


    private ViewPager indegameViewPager;
    private TabLayout indeGameTableLayout;
    private IndeGameViewModel mViewModel;

    public static IndeGameFragment newInstance() {
        return new IndeGameFragment();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inde_game_fragment, container, false);
        //탭 생성
        indeGameTableLayout = view.findViewById(R.id.indegame_tab);
        indegameViewPager = (DisableSwipeViewPager) view.findViewById(R.id.indegame_pager);

        return view;
    }

    private void setTabLayout() {
        FundingViewPagerAdapter adapter = setFundingViewPagerAdapter();
        indegameViewPager.setAdapter(adapter);
        indegameViewPager.setPageMargin((int) getResources().getDimension(R.dimen.view_pager_gap));
        indegameViewPager.setOffscreenPageLimit(2);
        indeGameTableLayout.setupWithViewPager(indegameViewPager);
    }
    private FundingViewPagerAdapter setFundingViewPagerAdapter() {
        FundingViewPagerAdapter adapter = new FundingViewPagerAdapter(getChildFragmentManager());
        adapter.addPage(MobileIndeFragment.newInstance(8), getResources().getString(R.string.mobile_tab));
        adapter.addPage(MobileIndeFragment.newInstance(89), getResources().getString(R.string.pc_tab));
        return adapter;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(IndeGameViewModel.class);
        // TODO: Use the ViewModel
        setDrawerSettings();
        setTabLayout();
    }

    private void setDrawerSettings() {
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.home_tab_indegame));
        DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

}
