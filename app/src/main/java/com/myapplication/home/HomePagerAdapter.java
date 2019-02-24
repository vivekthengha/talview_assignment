package com.myapplication.home;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomePagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<String> titleList;

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
        titleList = new ArrayList<>();
    }

    void addFragment(Fragment fragment, String title){
        fragments.add(fragment);
        titleList.add(title);
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
