package rejasupotaro.mds.view.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import rejasupotaro.mds.R;
import rejasupotaro.mds.data.models.Recipe;

public class RecipeDetailView extends FrameLayout {

    @Bind(R.id.recipe_image)
    ImageView recipeImageView;
    @Bind(R.id.title_wrapper)
    View titleWrapperView;
    @Bind(R.id.title_text)
    TextView titleTextView;
    @Bind(R.id.updated_at_text)
    TextView updatedAtTextView;
    @Bind(R.id.description_text)
    TextView descriptionTextView;
    @Bind(R.id.step_list)
    StepListView stepListView;
    @Bind(R.id.recipe_user)
    RecipeUserView recipeUserView;

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

    public RecipeDetailView(Context context) {
        super(context);
        setup();
    }

    public RecipeDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    private void setup() {
        inflate(getContext(), R.layout.view_recipe_detail, this);
        ButterKnife.bind(this);
    }

    public void setRecipe(Recipe recipe) {
        if (!TextUtils.isEmpty(recipe.imageUrl())) {
            Picasso.with(getContext())
                    .load(recipe.imageUrl())
                    .into(recipeImageView, callback);
        }

        titleTextView.setText(recipe.title());
        updatedAtTextView.setText(recipe.updatedAt());
        descriptionTextView.setText(recipe.description());
        stepListView.setSteps(recipe.steps());
        recipeUserView.setUser(recipe.user());
    }
}
