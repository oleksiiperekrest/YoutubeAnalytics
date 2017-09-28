package com.gmail.fomichov.m.youtubeanalytics.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gmail.fomichov.m.youtubeanalytics.R;
import com.gmail.fomichov.m.youtubeanalytics.fragments.CompareGlobalInformation;
import com.gmail.fomichov.m.youtubeanalytics.fragments.CompareMediaResonance;
import com.gmail.fomichov.m.youtubeanalytics.fragments.GlobalInformation;
import com.gmail.fomichov.m.youtubeanalytics.fragments.MediaResonance;
import com.gmail.fomichov.m.youtubeanalytics.fragments.SortChannelsData;
import com.gmail.fomichov.m.youtubeanalytics.fragments.SortMediaResonance;

public class MyPagerAdapter extends FragmentPagerAdapter {
    private Context context;

    public MyPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return GlobalInformation.newInstance();
            case 1:
                return CompareGlobalInformation.newInstance();
            case 2:
                return SortChannelsData.newInstance();
            case 3:
                return MediaResonance.newInstance();
            case 4:
                return CompareMediaResonance.newInstance();
            case 5:
                return SortMediaResonance.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.titleGlobalInformation);
            case 1:
                return context.getString(R.string.titleCompareGlobalInformation);
            case 2:
                return context.getString(R.string.titleSortChannelsData);
            case 3:
                return context.getString(R.string.titleMediaResonance);
            case 4:
                return context.getString(R.string.titleCompareMediaResonance);
            case 5:
                return context.getString(R.string.titleSortMediaResonance);
        }
        return null;
    }
}
