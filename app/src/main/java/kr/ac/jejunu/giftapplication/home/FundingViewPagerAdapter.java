package kr.ac.jejunu.giftapplication.home;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class FundingViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    public FundingViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public void addPage(Fragment fragment, String title) {
        fragmentList.add(fragment);
        titleList.add(title);
    }
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
    @Override
    public int getCount() {
        return fragmentList.size();
    }
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
