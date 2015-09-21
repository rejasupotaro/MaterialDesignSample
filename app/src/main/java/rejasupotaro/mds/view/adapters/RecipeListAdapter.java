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

import butterknife.Bind;
import butterknife.ButterKnife;
import rejasupotaro.mds.R;
import rejasupotaro.mds.activities.RecipeActivity;
import rejasupotaro.mds.data.models.Recipe;

public class RecipeListAdapter extends BindableAdapter<Recipe> {
    public RecipeListAdapter(Context context, List<Recipe> recipes) {
        super(context, recipes);
    }

    static class ViewHolder {
        @Bind(R.id.recipe_image)
        ImageView recipeImageView;
        @Bind(R.id.user_image)
        ImageView userImageView;
        @Bind(R.id.user_name_text)
        TextView userNameTextView;
        @Bind(R.id.title_text)
        TextView titleTextView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
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
            RecipeActivity.launch(activity, recipe, holder.recipeImageView, "");
        });
    }
}
