package rejasupotaro.mds.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rejasupotaro.mds.R;
import rejasupotaro.mds.data.models.Recipe;
import rejasupotaro.mds.view.adapters.UserRecipeListAdapter;

public class UserRecipeListFragment extends Fragment {

    @InjectView(R.id.scroll)
    ObservableRecyclerView userRecipeListView;

    private UserRecipeListAdapter userRecipeListAdapter;

    public static Fragment newInstance() {
        UserRecipeListFragment fragment = new UserRecipeListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_recipe_list, container, false);
        ButterKnife.inject(this, view);
        setupViews();
        return view;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.reset(this);
        super.onDestroyView();
    }

    private void setupViews() {
        userRecipeListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        userRecipeListView.setHasFixedSize(false);
        userRecipeListView.setTouchInterceptionViewGroup((ViewGroup) getActivity().findViewById(R.id.container));
        userRecipeListAdapter = new UserRecipeListAdapter(Recipe.dummies());
        if (getActivity() instanceof ObservableScrollViewCallbacks) {
            userRecipeListView.setScrollViewCallbacks((ObservableScrollViewCallbacks) getActivity());
        }
        userRecipeListView.setAdapter(userRecipeListAdapter);
    }
}
