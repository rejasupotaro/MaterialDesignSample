package rejasupotaro.mds.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rejasupotaro.mds.R;
import rejasupotaro.mds.data.models.Recipe;

public class UserRecipeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Recipe> recipes;

    public UserRecipeListAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ItemViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder) holder).bind(recipes.get(position));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.user_image)
        ImageView userImageView;
        @InjectView(R.id.user_name_text)
        TextView userNameTextView;
        @InjectView(R.id.recipe_image)
        ImageView recipeImageView;
        @InjectView(R.id.recipe_title_text)
        TextView recipeTitleTextView;
        @InjectView(R.id.recipe_description_text)
        TextView recipeDescriptionTextView;

        public static ItemViewHolder create(ViewGroup parent) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user_recipe, parent, false);
            return new ItemViewHolder(itemView);
        }

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        public void bind(Recipe recipe) {
            userImageView.setImageDrawable(userImageView.getResources().getDrawable(R.drawable.user));
            userNameTextView.setText(recipe.user().name());
            Picasso.with(recipeImageView.getContext())
                    .load(recipe.imageUrl())
                    .into(recipeImageView);
            recipeTitleTextView.setText(recipe.title());
            recipeDescriptionTextView.setText(recipe.description());
        }
    }
}

