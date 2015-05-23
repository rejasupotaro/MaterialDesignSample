package rejasupotaro.mds.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rejasupotaro.mds.R;
import rejasupotaro.mds.data.models.Recipe;
import rejasupotaro.mds.utils.DisplayUtils;
import rejasupotaro.mds.view.components.RecipeDetailView;

public class RecipeDetailActivity extends BaseActivity {
    public static final String EXTRA_RECIPE = "recipe";
    public static final String EXTRA_IMAGE = "ItemDetailActivity:image";

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.recipe_detail)
    RecipeDetailView recipeDetailView;

    private Recipe recipe;

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

        ImageView recipeImageView = (ImageView) recipeDetailView.findViewById(R.id.recipe_image);
        ViewCompat.setTransitionName(recipeImageView, EXTRA_IMAGE);

        recipe = Recipe.fromJson(getIntent().getStringExtra(EXTRA_RECIPE), Recipe.class);

        setupActionBar();
        setupViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupActionBar() {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) toolbar.getLayoutParams();
        layoutParams.setMargins(0, DisplayUtils.getStatusBarHeight(this), 0, 0);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void setupViews() {
        recipeDetailView.setRecipe(recipe);
    }
}
