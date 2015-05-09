package rejasupotaro.mds.view.components;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rejasupotaro.mds.R;
import rejasupotaro.mds.data.model.Step;

public class StepView extends FrameLayout {
    @InjectView(R.id.step_description_text)
    TextView stepDescriptionTextView;
    @InjectView(R.id.step_image)
    ImageView stepImageView;

    public StepView(Context context, Step step) {
        super(context);
        setup(step);
    }

    private void setup(Step step) {
        inflate(getContext(), R.layout.view_step, this);
        ButterKnife.inject(this);

        stepDescriptionTextView.setText(step.description());
        if (!TextUtils.isEmpty(step.imageUrl())) {
            Picasso.with(getContext())
                    .load(step.imageUrl())
                    .into(stepImageView);
            stepImageView.setVisibility(View.VISIBLE);
        }
    }
}
