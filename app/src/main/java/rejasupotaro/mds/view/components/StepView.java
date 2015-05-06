package rejasupotaro.mds.view.components;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rejasupotaro.mds.R;
import rejasupotaro.mds.data.model.Step;

public class StepView extends FrameLayout {
    @InjectView(R.id.step_description_text)
    TextView stepDescriptionTextView;

    public StepView(Context context, Step step) {
        super(context);
        setup(step);
    }

    private void setup(Step step) {
        inflate(getContext(), R.layout.view_step, this);
        ButterKnife.inject(this);

        stepDescriptionTextView.setText(step.description());
    }
}
