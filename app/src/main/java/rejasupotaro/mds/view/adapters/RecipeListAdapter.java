package rejasupotaro.mds.view.adapters;

import android.app.Activity;
import android.content.Context;
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
import rejasupotaro.mds.activities.RecipeDetailActivity;
import rejasupotaro.mds.data.model.Recipe;

public class RecipeListAdapter extends BindableAdapter<Recipe> {
    public RecipeListAdapter(Context context, List<Recipe> recipes) {
        super(context, recipes);
    }

    static class ViewHolder {
        @InjectView(R.id.recipe_image)
        ImageView recipeImageView;
        @InjectView(R.id.user_image)
        ImageView userImageView;
        @InjectView(R.id.user_name_text)
        TextView userNameTextView;
        @InjectView(R.id.title_text)
        TextView titleTextView;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    @Override
    public View newView(LayoutInflater inflater, int position, ViewGroup container) {
        View view = inflater.inflate(R.layout.list_item_recipe, null, false);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(Recipe recipe, int position, View view) {
        final ViewHolder holder = (ViewHolder) view.getTag();

        Picasso.with(view.getContext())
                .load(recipe.imageUrl())
                .into(holder.recipeImageView);
        holder.userImageView.setImageResource(R.drawable.user);
        holder.userNameTextView.setText(recipe.user().name());
        holder.titleTextView.setText(recipe.title());

        view.setOnClickListener(v -> {
            Activity activity = (Activity) v.getContext();
            RecipeDetailActivity.launch(activity, recipe, holder.recipeImageView, "");
        });
    }
}
