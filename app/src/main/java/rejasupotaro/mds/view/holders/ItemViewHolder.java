package rejasupotaro.mds.view.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rejasupotaro.mds.R;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.title)
    TextView textView;

    public ItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
    }

    public static ItemViewHolder create(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    public void bind(List<Object> items, int position) {
        textView.setText("position: " + position);
    }
}

