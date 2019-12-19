package com.study.android.mytry.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PageAdapter(FragmentManager fm, int NumOfTabs){
        super(fm);
        this.mNumOfTabs=NumOfTabs;
    }

    @Override
    public Fragment getItem(int position){

        switch (position){
            case 0:
                return new com.study.android.mytry.main.Fragment_Search();
            case 1:
                return new com.study.android.mytry.main.Fragment_Creation();
            case 2:
                return new com.study.android.mytry.main.Fragment_Certification();
            case 3:
                return new com.study.android.mytry.main.Fragment_Feed();
            case 4:
                return new com.study.android.mytry.main.Fragment_Mypage();
            default:
                return null;
        }
    }

    public int getCount(){
        return mNumOfTabs;
    }

}
