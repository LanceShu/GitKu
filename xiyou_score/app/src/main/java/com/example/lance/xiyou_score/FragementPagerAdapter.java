package com.example.lance.xiyou_score;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Lance on 2017/1/24.
 */
public class FragementPagerAdapter extends FragmentStatePagerAdapter{

    private List<Fragment> fragmentList;
    private List<String> titlelist;

    public FragementPagerAdapter(FragmentManager fm,List<Fragment> fragmentList,List<String> titlelist) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titlelist = titlelist;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titlelist.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
