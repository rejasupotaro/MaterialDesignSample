package rejasupotaro.mds.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rejasupotaro.mds.R;
import rejasupotaro.mds.view.adapters.ItemListAdapter;

public class ItemListFragment extends Fragment {

    @InjectView(R.id.item_list)
    RecyclerView itemListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, null);
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
        itemListView.setLayoutManager(layoutManager);
        itemListView.setHasFixedSize(true);
        itemListView.setAdapter(new ItemListAdapter());
    }
}
