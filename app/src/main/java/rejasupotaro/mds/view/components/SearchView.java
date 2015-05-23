package rejasupotaro.mds.view.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rejasupotaro.mds.R;
import rejasupotaro.mds.view.adapters.TextWatcherAdapter;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.AndroidSubscriptions;
import rx.android.internal.Assertions;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchView extends FrameLayout implements Observable.OnSubscribe<String> {

    private static final int DEBOUNCE_WAIT = 300;

    @InjectView(R.id.keyword_input)
    EditText keywordInput;

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
        ButterKnife.inject(this);
    }

    @Override
    public void call(Subscriber<? super String> subscriber) {
        Assertions.assertUiThread();

        final TextWatcherAdapter listener = new TextWatcherAdapter() {
            @Override
            public void onTextChanged(String text, int length) {
                subscriber.onNext(text);
            }
        };

        final Subscription subscription = AndroidSubscriptions.unsubscribeInUiThread(() -> keywordInput.removeTextChangedListener(listener));

        keywordInput.addTextChangedListener(listener);
        subscriber.add(subscription);
    }

    public Observable<String> observe() {
        return Observable.create(this)
                .debounce(DEBOUNCE_WAIT, TimeUnit.MILLISECONDS, Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
