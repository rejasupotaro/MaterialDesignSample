package rejasupotaro.mds.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.etsy.android.grid.StaggeredGridView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rejasupotaro.mds.R;
import rejasupotaro.mds.data.ChannelService;
import rejasupotaro.mds.data.model.Channel;
import rejasupotaro.mds.view.adapters.RecipeListAdapter;
import rx.Subscription;
import rx.android.app.AppObservable;
import rx.subscriptions.Subscriptions;

public class MainActivity extends BaseActivity implements AbsListView.OnScrollListener, AbsListView.OnItemClickListener {

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
        recipeListView.setOnScrollListener(this);
        recipeListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
    }
}
