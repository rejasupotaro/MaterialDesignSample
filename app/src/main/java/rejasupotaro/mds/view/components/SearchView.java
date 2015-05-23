package rejasupotaro.mds.view.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import rejasupotaro.mds.R;

public class SearchView extends FrameLayout {
    public SearchView(Context context) {
        super(context);
        setup();
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
    }

    private void setup() {
        View.inflate(getContext(), R.layout.view_search, this);
    }
}
