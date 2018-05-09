package com.ttrm.ttconnection.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.ttrm.ttconnection.fragment.HomeFragment;
import com.ttrm.ttconnection.fragment.MineFragment;
import com.ttrm.ttconnection.fragment.ZsFragment;

/**
 * Created by Administrator on 2016/8/3.
 */
public class MainVpAdapter extends FragmentPagerAdapter {
    private HomeFragment main0Fragment = null;
    private ZsFragment main1Fragment = null;
    private MineFragment main2Fragment = null;

    public MainVpAdapter(FragmentManager fm) {
        super(fm);
        main0Fragment = new HomeFragment();
        main1Fragment = new ZsFragment();
        main2Fragment = new MineFragment();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = main0Fragment;
                break;
            case 1:
                fragment = main1Fragment;
                break;
            case 2:
                fragment = main2Fragment;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

}
