package rejasupotaro.mds.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rejasupotaro.mds.R;
import rejasupotaro.mds.data.model.Channel;
import rejasupotaro.mds.view.adapters.RecipeListAdapter;

public class ChannelFragment extends ScrollTabHolderFragment implements AbsListView.OnScrollListener {

    private static final String EXTRA_CHANNEL = "channel";
    private static final String EXTRA_TAB_POSITION = "tab_position";

    @InjectView(R.id.recipe_list)
    ListView recipeListView;

    private Channel channel;
    private int tabPosition;

    public static ChannelFragment newInstance(Channel channel, int tabPosition) {
        ChannelFragment channelFragment = new ChannelFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_CHANNEL, channel.toJson());
        args.putInt(EXTRA_TAB_POSITION, tabPosition);
        channelFragment.setArguments(args);
        return channelFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.channel = Channel.fromJson(getArguments().getString(EXTRA_CHANNEL), Channel.class);
        this.tabPosition = getArguments().getInt(EXTRA_TAB_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channel, null);
        ButterKnife.inject(this, view);
        View headerView = inflater.inflate(R.layout.list_header_channel_recipe, recipeListView, false);
        recipeListView.addHeaderView(headerView);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViews();
    }

    @Override
    public void onDestroy() {
        ButterKnife.reset(this);
        super.onDestroy();
    }

    private void setupViews() {
        recipeListView.setOnScrollListener(this);
        recipeListView.setAdapter(new RecipeListAdapter(getActivity(), channel.recipes()));
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        // do nothing
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (mScrollTabHolder != null) {
            mScrollTabHolder.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount, tabPosition);
        }
    }

    @Override
    public void adjustScroll(int scrollHeight) {
        if (scrollHeight == 0 && recipeListView.getFirstVisiblePosition() >= 1) {
            return;
        }
        recipeListView.setSelectionFromTop(1, scrollHeight);
    }
}
