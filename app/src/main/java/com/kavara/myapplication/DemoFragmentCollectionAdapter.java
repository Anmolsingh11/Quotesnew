package com.kavara.myapplication;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class DemoFragmentCollectionAdapter extends FragmentStatePagerAdapter {


    public DemoFragmentCollectionAdapter(@NonNull FragmentManager fm)
    {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        DemoFragment demoFragement =  new DemoFragment();
        Bundle bundle = new Bundle();
        position = position +1;
        bundle.putString("message","Hello From Page:-"+position);
        demoFragement.setArguments(bundle);

        return demoFragement;
    }

    @Override
    public int getCount() {
        return 100;
    }
}
