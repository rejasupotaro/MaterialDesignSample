package rejasupotaro.mds.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import rejasupotaro.mds.data.model.Recipe;
import rejasupotaro.mds.view.holders.RecipeItemViewHolder;

public class RecipeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Recipe> recipes;

    public RecipeListAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return RecipeItemViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((RecipeItemViewHolder) holder).bind(recipes.get(position));
    }
}
