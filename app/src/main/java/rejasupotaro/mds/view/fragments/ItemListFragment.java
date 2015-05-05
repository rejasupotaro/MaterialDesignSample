package rejasupotaro.mds.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rejasupotaro.mds.R;
import rejasupotaro.mds.view.adapters.ItemListAdapter;

public class ItemListFragment extends Fragment {

    @InjectView(R.id.item_list)
    ObservableRecyclerView itemListView;

    private ObservableScrollViewCallbacks callback;

    public void setOnScrollCallback(ObservableScrollViewCallbacks callback) {
        this.callback = callback;
    }

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
        itemListView.setScrollViewCallbacks(new ObservableScrollViewCallbacks() {
            @Override
            public void onScrollChanged(int i, boolean b, boolean b1) {
                if (callback != null) {
                    callback.onScrollChanged(i, b, b1);
                }
            }

            @Override
            public void onDownMotionEvent() {
                if (callback != null) {
                    callback.onDownMotionEvent();
                }
            }

            @Override
            public void onUpOrCancelMotionEvent(ScrollState scrollState) {
                if (callback != null) {
                    callback.onUpOrCancelMotionEvent(scrollState);
                }
            }
        });
    }

    public void adjustScroll(int y) {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) itemListView.getLayoutManager());
        int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
        if (y == 0 && firstVisiblePosition >= 1) {
            return;
        }
        itemListView.scrollVerticallyTo(y);
    }
}
