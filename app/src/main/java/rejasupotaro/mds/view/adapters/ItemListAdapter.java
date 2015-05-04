package rejasupotaro.mds.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import rejasupotaro.mds.view.holders.ItemViewHolder;

public class ItemListAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private List<Object> items;

    public ItemListAdapter() {
        this.items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            items.add(new Object());
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ItemViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.bind(items, position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
