package rejasupotaro.mds.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rejasupotaro.mds.R;
import rejasupotaro.mds.data.model.Recipe;
import rejasupotaro.mds.view.components.StepListView;

public class RecipeDetailActivity extends BaseActivity {
    public static final String EXTRA_RECIPE = "_recipe";
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

    public static void launch(Activity activity, Recipe recipe, View transitionView, String url) {
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
        recipeImageView.setImageResource(R.drawable.recipe);

        recipe = Recipe.fromJson(getIntent().getStringExtra(EXTRA_RECIPE), Recipe.class);

        setupViews();
    }

    private void setupViews() {
        titleTextView.setText(recipe.title());
        updatedAtTextView.setText(recipe.updatedAt());
        descriptionTextView.setText(recipe.description());
        stepListView.setSteps(recipe.steps());
        userNameTextView.setText(recipe.user().name());

        Drawable userDrawable = getResources().getDrawable(R.drawable.user);
        userImageView.setImageDrawable(userDrawable);
        Bitmap userBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.user);
        new Palette.Builder(userBitmap)
                .generate(palette -> {
                    int darkMutedColor = palette.getDarkMutedColor(R.color.text_color_primary);
                    titleWrapperView.setBackgroundColor(darkMutedColor);
                    int vibrantColor = palette.getVibrantColor(android.R.color.white);
                    titleTextView.setTextColor(vibrantColor);
                    int lightVibrantColor = palette.getLightVibrantColor(R.color.text_color_tertiary);
                    updatedAtTextView.setTextColor(lightVibrantColor);
                });
    }
}
