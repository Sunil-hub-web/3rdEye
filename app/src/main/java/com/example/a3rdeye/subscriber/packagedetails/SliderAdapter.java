package com.example.a3rdeye.subscriber.packagedetails;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class SliderAdapter extends FragmentStatePagerAdapter {
    public SliderAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public SliderAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @Override
    public float getPageWidth(int position) {
        return 0.97f;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new SliverFragement();
            case 1:
                return new GoldFragement();
            case 2:
                return new PlatinumFragement();
            default:
                return new SliverFragement();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
