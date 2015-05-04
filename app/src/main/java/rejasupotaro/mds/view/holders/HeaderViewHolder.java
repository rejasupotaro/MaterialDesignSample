package rejasupotaro.mds.view.holders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import rejasupotaro.mds.R;

public class HeaderViewHolder extends RecyclerView.ViewHolder {

    public HeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
    }

    public static HeaderViewHolder create(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_header, parent, false);
        return new HeaderViewHolder(itemView);
    }

    public void bind() {
    }
}

