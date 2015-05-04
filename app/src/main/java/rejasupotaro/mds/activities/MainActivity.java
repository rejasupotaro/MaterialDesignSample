package rejasupotaro.mds.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.github.ksoichiro.android.observablescrollview.Scrollable;
import com.github.ksoichiro.android.observablescrollview.TouchInterceptionFrameLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rejasupotaro.mds.R;
import rejasupotaro.mds.view.adapters.TabFragmentPagerAdapter;
import rejasupotaro.mds.view.components.SlidingTabLayout;
import rejasupotaro.mds.view.fragments.ItemListFragment;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.container)
    TouchInterceptionFrameLayout interceptionLayout;

    @InjectView(R.id.view_pager_wrapper)
    View viewPagerWrapper;

    @InjectView(R.id.view_pager)
    ViewPager viewPager;

    @InjectView(R.id.sliding_tab_layout)
    SlidingTabLayout slidingTabLayout;

    private TabFragmentPagerAdapter pagerAdapter;
    private int slop;
    private int itemListHeaderHeight;
    private int viewPagerTabHeight;
    private boolean isScrolled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setupViewPager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
        itemListHeaderHeight = getResources().getDimensionPixelSize(R.dimen.item_list_header_height);
        viewPagerTabHeight = getResources().getDimensionPixelSize(R.dimen.view_pager_tab_height);

        viewPagerWrapper.setPadding(0, itemListHeaderHeight, 0, 0);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) slidingTabLayout.getLayoutParams();
        params.topMargin = itemListHeaderHeight - viewPagerTabHeight;

        ItemListFragment itemListFragment1 = new ItemListFragment();
        ItemListFragment itemListFragment2 = new ItemListFragment();
        pagerAdapter = new TabFragmentPagerAdapter.Builder(getSupportFragmentManager())
                .add(new TabFragmentPagerAdapter.TabFragment("Channel1", itemListFragment1))
                .add(new TabFragmentPagerAdapter.TabFragment("Channel2", itemListFragment2))
                .build();
        viewPager.setAdapter(pagerAdapter);
        slidingTabLayout.setDistributeEvenly(false);
        slidingTabLayout.setViewPager(viewPager);

        ViewConfiguration viewConfiguration = ViewConfiguration.get(this);
        slop = viewConfiguration.getScaledTouchSlop();

        interceptionLayout.setScrollInterceptionListener(interceptionListener);
    }

    private TouchInterceptionFrameLayout.TouchInterceptionListener interceptionListener = new TouchInterceptionFrameLayout.TouchInterceptionListener() {
        @Override
        public boolean shouldInterceptTouchEvent(MotionEvent motionEvent, boolean isMoving, float diffX, float diffY) {
            if (!isScrolled && slop < Math.abs(diffX) && Math.abs(diffY) < Math.abs(diffX)) {
                return false;
            }

            Scrollable scrollable = getCurrentScrollable();
            if (scrollable == null) {
                isScrolled = false;
                return false;
            }

            int flexibleSpace = itemListHeaderHeight - viewPagerTabHeight;
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
        public void onDownMotionEvent(MotionEvent motionEvent) {

        }

        @Override
        public void onMoveMotionEvent(MotionEvent motionEvent, float diffX, float diffY) {
            int flexibleSpace = itemListHeaderHeight - viewPagerTabHeight;
            float translationY = ScrollUtils.getFloat(interceptionLayout.getTranslationY() + diffY, -flexibleSpace, 0);
            updateFlexibleSpace(translationY);
            if (translationY < 0) {
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) interceptionLayout.getLayoutParams();
                lp.height = (int) (-translationY + getScreenHeight());
                interceptionLayout.requestLayout();
            }
        }

        @Override
        public void onUpOrCancelMotionEvent(MotionEvent motionEvent) {

        }
    };

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

    private Fragment getCurrentFragment() {
        return pagerAdapter.getItemAt(viewPager.getCurrentItem());
    }

    private void updateFlexibleSpace(float translationY) {
        interceptionLayout.setTranslationY(translationY);
    }

    protected int getScreenHeight() {
        return findViewById(android.R.id.content).getHeight();
    }
}
