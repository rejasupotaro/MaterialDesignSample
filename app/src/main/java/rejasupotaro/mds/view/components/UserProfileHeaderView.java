package rejasupotaro.mds.view.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import rejasupotaro.mds.R;
import rejasupotaro.mds.data.models.User;

public class UserProfileHeaderView extends FrameLayout {

    @Bind(R.id.user_image)
    ImageView userImageView;
    @Bind(R.id.user_name_text)
    TextView userNameTextView;

    public UserProfileHeaderView(Context context) {
        super(context);
        setup();
    }

    public UserProfileHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public UserProfileHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
    }

    private void setup() {
        inflate(getContext(), R.layout.view_user_profile_header, this);
        ButterKnife.bind(this);
    }

    public void setUser(User user) {
        userImageView.setImageDrawable(getResources().getDrawable(R.drawable.user));
        userNameTextView.setText(user.name());
    }
}
