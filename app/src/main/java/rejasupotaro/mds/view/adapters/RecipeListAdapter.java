package rejasupotaro.mds.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import rejasupotaro.mds.data.model.Channel;
import rejasupotaro.mds.view.holders.RecipeItemViewHolder;

public class RecipeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Channel channel;

    public RecipeListAdapter() {
        this.channel = Channel.dummy();
    }

    @Override
    public int getItemCount() {
        return channel.recipes().size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return RecipeItemViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((RecipeItemViewHolder) holder).bind(channel.recipes().get(position));
    }
}
