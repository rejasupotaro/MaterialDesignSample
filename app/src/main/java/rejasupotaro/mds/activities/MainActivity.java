package rejasupotaro.mds.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rejasupotaro.mds.R;
import rejasupotaro.mds.view.adapters.TabFragmentPagerAdapter;
import rejasupotaro.mds.view.components.SlidingTabLayout;
import rejasupotaro.mds.view.fragments.ItemListFragment;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.view_pager)
    ViewPager viewPager;

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
        ItemListFragment itemListFragment1 = new ItemListFragment();
        ItemListFragment itemListFragment2 = new ItemListFragment();
        TabFragmentPagerAdapter pagerAdapter = new TabFragmentPagerAdapter.Builder(getSupportFragmentManager())
                .add(new TabFragmentPagerAdapter.TabFragment("Channel1", itemListFragment1))
                .add(new TabFragmentPagerAdapter.TabFragment("Channel2", itemListFragment2))
                .build();
        viewPager.setAdapter(pagerAdapter);
        slidingTabLayout.setDistributeEvenly(false);
        slidingTabLayout.setViewPager(viewPager);
    }
}
