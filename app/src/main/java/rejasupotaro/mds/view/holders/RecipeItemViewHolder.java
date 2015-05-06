package rejasupotaro.mds.view.holders;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rejasupotaro.mds.R;
import rejasupotaro.mds.activities.RecipeDetailActivity;
import rejasupotaro.mds.data.model.Recipe;

public class RecipeItemViewHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.recipe_image)
    ImageView recipeImageView;
    @InjectView(R.id.user_image)
    ImageView userImageView;
    @InjectView(R.id.user_name_text)
    TextView userNameTextView;
    @InjectView(R.id.title_text)
    TextView titleTextView;

    public RecipeItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
    }

    public static RecipeItemViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_recipe, parent, false);
        return new RecipeItemViewHolder(view);
    }

    public void bind(Recipe recipe) {
        recipeImageView.setImageResource(R.drawable.recipe);
        userImageView.setImageResource(R.drawable.user);
        userNameTextView.setText(recipe.user().name());
        titleTextView.setText(recipe.title());

        itemView.setOnClickListener(view -> {
            Activity activity = (Activity) itemView.getContext();
            RecipeDetailActivity.launch(activity, recipe, recipeImageView, "");
        });
    }
}

