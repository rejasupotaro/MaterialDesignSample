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

public class ItemDetailActivity extends BaseActivity {

    public static final String EXTRA_IMAGE = "ItemDetailActivity:image";

    @InjectView(R.id.item_image)
    ImageView itemImageView;
    @InjectView(R.id.title_wrapper)
    View titleWrapperView;
    @InjectView(R.id.title_text)
    TextView titleTextView;
    @InjectView(R.id.updated_at_text)
    TextView updatedAtTextView;
    @InjectView(R.id.user_image)
    ImageView userImageView;

    public static void launch(Activity activity, View transitionView, String url) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,
                transitionView,
                EXTRA_IMAGE);
        Intent intent = new Intent(activity, ItemDetailActivity.class);
        intent.putExtra(EXTRA_IMAGE, url);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ButterKnife.inject(this);
        ViewCompat.setTransitionName(itemImageView, EXTRA_IMAGE);
        itemImageView.setImageResource(R.drawable.pasta);
        setupViews();
    }

    private void setupViews() {
        titleTextView.setText("Title");

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
