package rejasupotaro.mds.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.ksoichiro.android.observablescrollview.CacheFragmentStatePagerAdapter;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rejasupotaro.mds.R;
import rejasupotaro.mds.view.components.SlidingTabLayout;
import rejasupotaro.mds.view.fragments.ItemListFragment;

public class MainActivity extends BaseActivity implements ObservableScrollViewCallbacks {

    @InjectView(R.id.view_pager)
    ViewPager viewPager;

    @InjectView(R.id.tab_container)
    View tabContainer;

    @InjectView(R.id.sliding_tab_layout)
    SlidingTabLayout slidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setupViewPager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager() {
        TabFragmentPagerAdapter pagerAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
        pagerAdapter.add(new Tab("Channel1", new ItemListFragment()));
        pagerAdapter.add(new Tab("Channel2", new ItemListFragment()));
        viewPager.setAdapter(pagerAdapter);

        slidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                int scrollY = (int) (tabContainer.getTranslationY());
                pagerAdapter.getItemAt(position).adjustScroll(scrollY);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        slidingTabLayout.setDistributeEvenly(false);
        slidingTabLayout.setViewPager(viewPager);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean isFirstScroll, boolean isDragging) {
        int height = getResources().getDimensionPixelSize(R.dimen.item_list_header_height);
        int size = getResources().getDimensionPixelSize(R.dimen.view_pager_tab_height);
        tabContainer.setTranslationY(Math.min(Math.max(-scrollY, -height + size), size));
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

    public class TabFragmentPagerAdapter extends CacheFragmentStatePagerAdapter {

        private List<Tab> pages = new ArrayList<>();

        public TabFragmentPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public void add(Tab tab) {
            pages.add(tab);
        }

        public ItemListFragment getItemAt(int position) {
            return pages.get(position).fragment;
        }

        @Override
        protected Fragment createItem(int position) {
            ItemListFragment fragment = pages.get(position).fragment;
            fragment.setOnScrollCallback(MainActivity.this);
            return fragment;
        }

        @Override
        public int getCount() {
            return pages.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return pages.get(position).title;
        }
    }

    public class Tab {

        private String title;

        private ItemListFragment fragment;

        public Tab(String title, ItemListFragment fragment) {
            this.title = title;
            this.fragment = fragment;
        }
    }
}
