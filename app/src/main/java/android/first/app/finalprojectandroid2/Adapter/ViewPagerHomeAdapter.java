package android.first.app.finalprojectandroid2.Adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class ViewPagerHomeAdapter extends FragmentPagerAdapter {
    List<Fragment> screenFragment;
    List<String> titleScreenFragment;

    public ViewPagerHomeAdapter(FragmentManager fm , List<Fragment>screenFragment, List<String>titleScreenFragment) {
        super(fm);
        this.screenFragment = screenFragment;
        this.titleScreenFragment = titleScreenFragment;
    }

    @Override
    public Fragment getItem(int i) {
        return screenFragment.get(i);
    }

    @Override
    public int getCount() {
        return screenFragment.size();
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleScreenFragment.get(position);
    }
}
