package rejasupotaro.mds.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<TabFragment> tabFragments = new ArrayList<>();

    public TabFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public void add(TabFragment tabFragment) {
        tabFragments.add(tabFragment);
    }

    @Override
    public Fragment getItem(int position) {
        return tabFragments.get(position).fragment;
    }

    @Override
    public int getCount() {
        return tabFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabFragments.get(position).title;
    }

    public static class TabFragment {

        private String title;

        private Fragment fragment;

        public TabFragment(String title, Fragment fragment) {
            this.title = title;
            this.fragment = fragment;
        }
    }

    public static class Builder {

        private TabFragmentPagerAdapter pagerAdapter;

        public Builder(FragmentManager fragmentManager) {
            this.pagerAdapter = new TabFragmentPagerAdapter(fragmentManager);
        }

        public Builder add(TabFragment tabFragment) {
            pagerAdapter.add(tabFragment);
            return this;
        }

        public TabFragmentPagerAdapter build() {
            return pagerAdapter;
        }
    }
}

