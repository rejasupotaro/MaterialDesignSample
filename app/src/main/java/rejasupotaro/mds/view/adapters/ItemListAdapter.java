package rejasupotaro.mds.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import rejasupotaro.mds.view.holders.HeaderViewHolder;
import rejasupotaro.mds.view.holders.ItemViewHolder;

public class ItemListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;

    private List<Object> items;

    public ItemListAdapter() {
        this.items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            items.add(new Object());
        }
    }

    @Override
    public int getItemViewType(int position) {
//        if (position == 0) {
//            return VIEW_TYPE_HEADER;
//        } else {
            return VIEW_TYPE_ITEM;
//        }
    }

    @Override
    public int getItemCount() {
        return items.size();// + 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == VIEW_TYPE_HEADER) {
//            return HeaderViewHolder.create(parent);
//        } else {
            return ItemViewHolder.create(parent);
//        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).bind(items, position);
        }
    }
}
