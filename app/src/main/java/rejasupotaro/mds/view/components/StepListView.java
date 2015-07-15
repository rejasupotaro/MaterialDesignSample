package rejasupotaro.mds.view.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rejasupotaro.mds.R;
import rejasupotaro.mds.data.models.Step;

public class StepListView extends FrameLayout {
    @Bind(R.id.steps_container)
    LinearLayout stepsContainer;

    public StepListView(Context context) {
        super(context);
        setup();
    }

    public StepListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public StepListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
    }

    private void setup() {
        inflate(getContext(), R.layout.view_step_list, this);
        ButterKnife.bind(this);
    }

    public void setSteps(List<Step> steps) {
        for (Step step : steps) {
            stepsContainer.addView(new StepView(getContext(), step));
        }
    }
}
