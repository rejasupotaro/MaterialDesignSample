package rejasupotaro.mds.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.graphics.Palette;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rejasupotaro.mds.R;
import rejasupotaro.mds.data.model.Recipe;
import rejasupotaro.mds.view.components.StepListView;

public class RecipeDetailActivity extends BaseActivity {
    public static final String EXTRA_RECIPE = "recipe";
    public static final String EXTRA_IMAGE = "ItemDetailActivity:image";

    @InjectView(R.id.recipe_image)
    ImageView recipeImageView;
    @InjectView(R.id.title_wrapper)
    View titleWrapperView;
    @InjectView(R.id.title_text)
    TextView titleTextView;
    @InjectView(R.id.updated_at_text)
    TextView updatedAtTextView;
    @InjectView(R.id.description_text)
    TextView descriptionTextView;
    @InjectView(R.id.step_list)
    StepListView stepListView;
    @InjectView(R.id.user_image)
    ImageView userImageView;
    @InjectView(R.id.user_name_text)
    TextView userNameTextView;

    private Recipe recipe;

    private Callback callback = new Callback() {
        @Override
        public void onSuccess() {
            Bitmap bitmap = ((BitmapDrawable) recipeImageView.getDrawable()).getBitmap();
            new Palette.Builder(bitmap)
                    .generate(palette -> {
                        int darkMutedColor = palette.getDarkMutedColor(R.color.text_color_primary);
                        titleWrapperView.setBackgroundColor(darkMutedColor);
                        int vibrantColor = palette.getVibrantColor(android.R.color.white);
                        titleTextView.setTextColor(vibrantColor);
                        int lightVibrantColor = palette.getLightVibrantColor(R.color.text_color_tertiary);
                        updatedAtTextView.setTextColor(lightVibrantColor);
                    });
        }

        @Override
        public void onError() {

        }
    };

    public static void launch(Activity activity, Recipe recipe, ImageView transitionView, String url) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,
                transitionView,
                EXTRA_IMAGE);

        Intent intent = new Intent(activity, RecipeDetailActivity.class);
        intent.putExtra(EXTRA_RECIPE, recipe.toJson());
        intent.putExtra(EXTRA_IMAGE, url);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.inject(this);
        ViewCompat.setTransitionName(recipeImageView, EXTRA_IMAGE);

        recipe = Recipe.fromJson(getIntent().getStringExtra(EXTRA_RECIPE), Recipe.class);

        setupViews();
    }

    private void setupViews() {
        if (!TextUtils.isEmpty(recipe.imageUrl())) {
            Picasso.with(this)
                    .load(recipe.imageUrl())
                    .into(recipeImageView, callback);
        }

        titleTextView.setText(recipe.title());
        updatedAtTextView.setText(recipe.updatedAt());
        descriptionTextView.setText(recipe.description());
        stepListView.setSteps(recipe.steps());
        userNameTextView.setText(recipe.user().name());
        userImageView.setImageDrawable(getResources().getDrawable(R.drawable.user));
    }
}
