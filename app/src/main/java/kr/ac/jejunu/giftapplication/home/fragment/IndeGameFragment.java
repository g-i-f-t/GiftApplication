package kr.ac.jejunu.giftapplication.home.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
        adapter.addPage(MobileIndeFragment.newInstance(8), getResources().getString(R.string.availableFunding));
        adapter.addPage(MobileIndeFragment.newInstance(89), getResources().getString(R.string.completeFunding));
        return adapter;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(IndeGameViewModel.class);
        // TODO: Use the ViewModel

        setTabLayout();
    }

}
