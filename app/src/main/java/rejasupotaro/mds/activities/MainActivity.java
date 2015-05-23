package rejasupotaro.mds.activities;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.etsy.android.grid.StaggeredGridView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rejasupotaro.mds.R;
import rejasupotaro.mds.data.ChannelService;
import rejasupotaro.mds.data.model.Channel;
import rejasupotaro.mds.view.adapters.RecipeListAdapter;
import rejasupotaro.mds.view.components.SearchView;
import rx.Subscription;
import rx.android.app.AppObservable;
import rx.subscriptions.Subscriptions;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.channel_recipe_list)
    StaggeredGridView recipeListView;

    private RecipeListAdapter recipeListAdapter;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
    }

    private void setupViews() {
        channelsSubscription = AppObservable.bindActivity(this, new ChannelService().getList())
                .subscribe(this::setupViews);
    }

    private void setupViews(List<Channel> channels) {
        LayoutInflater layoutInflater = getLayoutInflater();

        View header = layoutInflater.inflate(R.layout.list_header_channel_recipe, null);
        recipeListView.addHeaderView(header);

        recipeListAdapter = new RecipeListAdapter(this, channels.get(0).recipes());
        recipeListView.setAdapter(recipeListAdapter);
    }
}
