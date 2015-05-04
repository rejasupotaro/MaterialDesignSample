package rejasupotaro.mds.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rejasupotaro.mds.R;

public class ItemDetailActivity extends BaseActivity {

    public static final String EXTRA_IMAGE = "ItemDetailActivity:image";

    @InjectView(R.id.item_image)
    ImageView itemImageView;

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
    }
}
