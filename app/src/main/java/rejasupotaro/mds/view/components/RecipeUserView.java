package rejasupotaro.mds.view.components;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rejasupotaro.mds.R;
import rejasupotaro.mds.activities.UserProfileActivity;
import rejasupotaro.mds.data.models.User;
import rejasupotaro.mds.view.Transition;

public class RecipeUserView extends FrameLayout {

    @InjectView(R.id.user_image)
    ImageView userImageView;
    @InjectView(R.id.user_name_text)
    TextView userNameTextView;

    public RecipeUserView(Context context) {
        super(context);
        setup();
    }

    public RecipeUserView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public RecipeUserView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
    }

    private void setup() {
        inflate(getContext(), R.layout.view_recipe_user, this);
        ButterKnife.inject(this);
    }

    public void setUser(User user) {
        userNameTextView.setText(user.name());
        userImageView.setImageDrawable(getResources().getDrawable(R.drawable.user));
        setOnClickListener(view -> {
            Activity activity = (Activity) getContext();
            UserProfileActivity.launch(activity, user, Transition.PUSH_RIGHT_TO_LEFT);
        });
    }
}
