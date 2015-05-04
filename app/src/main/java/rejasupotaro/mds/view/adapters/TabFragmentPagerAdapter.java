package rejasupotaro.mds.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.github.ksoichiro.android.observablescrollview.CacheFragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabFragmentPagerAdapter extends CacheFragmentStatePagerAdapter {

    private List<TabFragment> pages = new ArrayList<>();

    public TabFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public void add(TabFragment tabFragment) {
        pages.add(tabFragment);
    }

    public Fragment getItemAt(int position) {
        return pages.get(position).fragment;
    }

    @Override
    protected Fragment createItem(int position) {
        return pages.get(position).fragment;
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pages.get(position).title;
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

