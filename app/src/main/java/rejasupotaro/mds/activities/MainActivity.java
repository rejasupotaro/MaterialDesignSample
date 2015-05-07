package rejasupotaro.mds.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rejasupotaro.mds.R;
import rejasupotaro.mds.data.ChannelService;
import rejasupotaro.mds.data.model.Channel;
import rejasupotaro.mds.utils.DisplayUtils;
import com.astuetz.PagerSlidingTabStrip;
import rejasupotaro.mds.view.fragments.ChannelFragment;
import rejasupotaro.mds.view.fragments.ScrollTabHolderFragment;
import rejasupotaro.mds.view.listeners.ScrollTabHolder;
import rx.Subscription;
import rx.android.app.AppObservable;
import rx.subscriptions.Subscriptions;

public class MainActivity extends BaseActivity {

    private static AccelerateInterpolator INTERPOLATOR = new AccelerateInterpolator();

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.list_header)
    View listHeader;
    @InjectView(R.id.view_pager)
    ViewPager viewPager;
    @InjectView(R.id.sliding_tabs)
    PagerSlidingTabStrip slidingTabs;
    @InjectView(R.id.query_edit_text)
    EditText queryEditText;

    private TabFragmentPagerAdapter pagerAdapter;
    private int headerHeight;
    private int tabHeight;
    private int minHeaderHeight;

    private Subscription channelsSubscription = Subscriptions.empty();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setupActionBar();
        setupViews();
    }

    @Override
    protected void onDestroy() {
        channelsSubscription.unsubscribe();
        super.onDestroy();
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

    private void setupActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
    }

    private void setupViews() {
        channelsSubscription = AppObservable.bindActivity(this, new ChannelService().getList())
                .subscribe(this::setupViewPager);
    }

    private void setupViewPager(List<Channel> channels) {
        headerHeight = getResources().getDimensionPixelSize(R.dimen.item_list_header_height);
        tabHeight = getResources().getDimensionPixelSize(R.dimen.view_pager_tab_height);
        int actionBarHeight = 0;
        TypedValue typedValue = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, typedValue, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(typedValue.data, getResources().getDisplayMetrics());
        }
        minHeaderHeight = tabHeight + actionBarHeight;

        pagerAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
        for (int i = 0; i < channels.size(); i++) {
            Channel channel = channels.get(i);
            pagerAdapter.add(new Tab(channel.name(), ChannelFragment.newInstance(channel, i)));
        }
        pagerAdapter.setTabHolderScrollingContent(new ScrollTabHolder() {
            @Override
            public void adjustScroll(int scrollHeight) {
                // do nothing
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition) {
                if (viewPager.getCurrentItem() == pagePosition) {
                    int scrollY = getScrollY(view);
                    listHeader.setTranslationY(Math.max(-scrollY, -minHeaderHeight));
                    updateHeader();
                }
            }
        });
        viewPager.setAdapter(pagerAdapter);

        slidingTabs.setIndicatorColor(Color.WHITE);
        slidingTabs.setViewPager(viewPager);
        slidingTabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // do nothing
            }

            @Override
            public void onPageSelected(int position) {
                SparseArray<ScrollTabHolder> scrollTabHolders = pagerAdapter.getScrollTabHolders();
                ScrollTabHolder currentHolder = scrollTabHolders.valueAt(position);
                currentHolder.adjustScroll((int) (listHeader.getHeight() + listHeader.getTranslationY()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // do nothing
            }
        });
    }

    public int getScrollY(AbsListView view) {
        View c = view.getChildAt(0);
        if (c == null) {
            return 0;
        }

        int firstVisiblePosition = view.getFirstVisiblePosition();
        int top = c.getTop();

        int headerHeight = 0;
        if (firstVisiblePosition >= 1) {
            headerHeight = this.headerHeight;
        }

        return -top + firstVisiblePosition * c.getHeight() + headerHeight;
    }

    private void updateHeader() {
        int translationY = (int) listHeader.getTranslationY();
        float ratio = -translationY / (float) minHeaderHeight;

        if ((DisplayUtils.dpToPx(this, 102) + translationY) < 0) {
            queryEditText.setTranslationY(0 - (DisplayUtils.dpToPx(this, 102)));
        } else {
            queryEditText.setTranslationY(translationY);
        }

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) queryEditText.getLayoutParams();
        int width = (int) (710 - (66 * Math.max(INTERPOLATOR.getInterpolation(ratio), 0)));
        layoutParams.width = width;
        queryEditText.requestLayout();
    }

    public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<Tab> pages = new ArrayList<>();
        private SparseArray<ScrollTabHolder> scrollTabHolders;
        private ScrollTabHolder scrollTabHolder;

        public TabFragmentPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            scrollTabHolders = new SparseArray<>();
        }

        public SparseArray<ScrollTabHolder> getScrollTabHolders() {
            return scrollTabHolders;
        }

        public void setTabHolderScrollingContent(ScrollTabHolder scrollTabHolder) {
            this.scrollTabHolder = scrollTabHolder;
        }

        public void add(Tab tab) {
            pages.add(tab);
        }

        @Override
        public Fragment getItem(int position) {
            ScrollTabHolderFragment fragment = pages.get(position).fragment;
            scrollTabHolders.put(position, fragment);
            if (scrollTabHolder != null) {
                fragment.setScrollTabHolder(scrollTabHolder);
            }
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

        private ChannelFragment fragment;

        public Tab(String title, ChannelFragment fragment) {
            this.title = title;
            this.fragment = fragment;
        }
    }
}
