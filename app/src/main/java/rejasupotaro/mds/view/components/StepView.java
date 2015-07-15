package rejasupotaro.mds.view.components;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import rejasupotaro.mds.R;
import rejasupotaro.mds.data.models.Step;

public class StepView extends FrameLayout {
    @Bind(R.id.step_description_text)
    TextView stepDescriptionTextView;
    @Bind(R.id.step_image)
    ImageView stepImageView;

    public StepView(Context context, Step step) {
        super(context);
        setup(step);
    }

    private void setup(Step step) {
        inflate(getContext(), R.layout.view_step, this);
        ButterKnife.bind(this);

        stepDescriptionTextView.setText(step.description());
        if (!TextUtils.isEmpty(step.imageUrl())) {
            Picasso.with(getContext())
                    .load(step.imageUrl())
                    .into(stepImageView);
            stepImageView.setVisibility(View.VISIBLE);
        }
    }
}
