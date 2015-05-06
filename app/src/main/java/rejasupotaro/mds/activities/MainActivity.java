package rejasupotaro.mds.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.github.ksoichiro.android.observablescrollview.CacheFragmentStatePagerAdapter;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.github.ksoichiro.android.observablescrollview.Scrollable;
import com.github.ksoichiro.android.observablescrollview.TouchInterceptionFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rejasupotaro.mds.R;
import rejasupotaro.mds.utils.DisplayUtils;
import rejasupotaro.mds.view.components.SlidingTabLayout;
import rejasupotaro.mds.view.fragments.ItemListFragment;

public class MainActivity extends BaseActivity implements ObservableScrollViewCallbacks {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.container)
    TouchInterceptionFrameLayout interceptionLayout;

    @InjectView(R.id.list_header)
    View listHeader;

    @InjectView(R.id.view_pager_wrapper)
    View viewPagerWrapper;

    @InjectView(R.id.view_pager)
    ViewPager viewPager;

    @InjectView(R.id.sliding_tab_layout)
    SlidingTabLayout slidingTabLayout;

    @InjectView(R.id.edit_text)
    EditText editText;

    private TabFragmentPagerAdapter pagerAdapter;
    private int slop;
    private int flexibleSpaceHeight;
    private int tabHeight;
    private boolean isScrolled;

    private TouchInterceptionFrameLayout.TouchInterceptionListener mInterceptionListener = new TouchInterceptionFrameLayout.TouchInterceptionListener() {
        @Override
        public boolean shouldInterceptTouchEvent(MotionEvent ev, boolean moving, float diffX, float diffY) {
            if (!isScrolled && slop < Math.abs(diffX) && Math.abs(diffY) < Math.abs(diffX)) {
                return false;
            }

            Scrollable scrollable = getCurrentScrollable();
            if (scrollable == null) {
                isScrolled = false;
                return false;
            }

            int flexibleSpace = flexibleSpaceHeight - (tabHeight * 2);
            int translationY = (int) interceptionLayout.getTranslationY();
            boolean scrollingUp = 0 < diffY;
            boolean scrollingDown = diffY < 0;
            if (scrollingUp) {
                if (translationY < 0) {
                    isScrolled = true;
                    return true;
                }
            } else if (scrollingDown) {
                if (-flexibleSpace < translationY) {
                    isScrolled = true;
                    return true;
                }
            }
            isScrolled = false;
            return false;
        }

        @Override
        public void onDownMotionEvent(MotionEvent ev) {
        }

        @Override
        public void onMoveMotionEvent(MotionEvent ev, float diffX, float diffY) {
            int flexibleSpace = flexibleSpaceHeight - tabHeight;
            float translationY = ScrollUtils.getFloat(interceptionLayout.getTranslationY() + diffY, -flexibleSpace, 0);
            updateFlexibleSpace(translationY);
            if (translationY < 0) {
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) interceptionLayout.getLayoutParams();
                lp.height = (int) (-translationY + findViewById(android.R.id.content).getHeight());
                interceptionLayout.requestLayout();
            }
        }

        @Override
        public void onUpOrCancelMotionEvent(MotionEvent ev) {
            isScrolled = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setupActionBar();
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

    private void setupActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
    }

    private void setupViewPager() {
        flexibleSpaceHeight = getResources().getDimensionPixelSize(R.dimen.item_list_header_height);
        tabHeight = getResources().getDimensionPixelSize(R.dimen.view_pager_tab_height);

        pagerAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
        pagerAdapter.add(new Tab("Channel 1", new ItemListFragment()));
        pagerAdapter.add(new Tab("Channel 2", new ItemListFragment()));
        viewPager.setAdapter(pagerAdapter);

        viewPagerWrapper.setPadding(0, flexibleSpaceHeight, 0, 0);

        slidingTabLayout.setDistributeEvenly(false);
        slidingTabLayout.setViewPager(viewPager);

        ((FrameLayout.LayoutParams) slidingTabLayout.getLayoutParams()).topMargin = flexibleSpaceHeight - tabHeight;

        ViewConfiguration viewConfiguration = ViewConfiguration.get(this);
        slop = viewConfiguration.getScaledTouchSlop();
        interceptionLayout.setScrollInterceptionListener(mInterceptionListener);
        ScrollUtils.addOnGlobalLayoutListener(interceptionLayout, new Runnable() {
            @Override
            public void run() {
                updateFlexibleSpace();
            }
        });
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }

    private Scrollable getCurrentScrollable() {
        Fragment fragment = getCurrentFragment();
        if (fragment == null) {
            return null;
        }
        View view = fragment.getView();
        if (view == null) {
            return null;
        }
        return (Scrollable) view.findViewById(R.id.item_list);
    }

    private void updateFlexibleSpace() {
        updateFlexibleSpace(interceptionLayout.getTranslationY());
    }

    private void updateFlexibleSpace(float translationY) {
        interceptionLayout.setTranslationY(translationY);

        editText.setTranslationY(translationY);
        if ((DisplayUtils.dpToPx(this, 102) + translationY) < 0) {
            editText.setTranslationY(0 - (DisplayUtils.dpToPx(this, 102)));
        } else {
            editText.setTranslationY(translationY);
        }

        float ratio = Math.max(0, DisplayUtils.dpToPx(this, 102) + translationY) / DisplayUtils.dpToPx(this, 110) / 0.3f;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) editText.getLayoutParams();
        layoutParams.width = (int) (710 * Math.max(Math.min(ratio, 1), 0.9));
    }

    private Fragment getCurrentFragment() {
        return pagerAdapter.getItemAt(viewPager.getCurrentItem());
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
