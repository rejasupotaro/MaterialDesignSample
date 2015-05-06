package rejasupotaro.mds.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rejasupotaro.mds.R;
import rejasupotaro.mds.data.model.Channel;
import rejasupotaro.mds.view.adapters.RecipeListAdapter;

public class ChannelFragment extends Fragment {

    private static final String EXTRA_CHANNEL = "_channel";

    @InjectView(R.id.item_list)
    ObservableRecyclerView recipeListView;

    private Channel channel;

    public static ChannelFragment newInstance(Channel channel) {
        ChannelFragment channelFragment = new ChannelFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_CHANNEL, channel.toJson());
        channelFragment.setArguments(args);
        return channelFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.channel = Channel.fromJson(getArguments().getString(EXTRA_CHANNEL), Channel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channel, null);
        ButterKnife.inject(this, view);
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recipeListView.setLayoutManager(layoutManager);
        recipeListView.setHasFixedSize(true);
        recipeListView.setAdapter(new RecipeListAdapter(channel.recipes()));
    }
}
