package rejasupotaro.mds.view.components;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rejasupotaro.mds.R;
import rejasupotaro.mds.view.adapters.SuggestionListAdapter;
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

    @Bind(R.id.query_input)
    EditText queryInput;
    @Bind(R.id.clear_button)
    View clearButton;
    @Bind(R.id.suggestion_list)
    ListView suggestionListView;

    private SuggestionListAdapter suggestionListAdapter;

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
        ButterKnife.bind(this);

        suggestionListAdapter = new SuggestionListAdapter(getContext());
        suggestionListView.setAdapter(suggestionListAdapter);
    }

    public void updateSuggestions(List<String> suggestions) {
        suggestionListAdapter.update(suggestions);
    }

    @Override
    public void call(Subscriber<? super String> subscriber) {
        Log.e("debugging", "subscriber is comming");
        Assertions.assertUiThread();

        final TextWatcherAdapter listener = new TextWatcherAdapter() {
            @Override
            public void onTextChanged(String text, int length) {
                subscriber.onNext(text);
            }
        };

        final Subscription subscription = AndroidSubscriptions
                .unsubscribeInUiThread(() -> queryInput.removeTextChangedListener(listener));

        queryInput.addTextChangedListener(listener);
        subscriber.add(subscription);
    }

    public Observable<String> observe() {
        return Observable.create(this)
                .map(query -> {
                    if (TextUtils.isEmpty(query)) {
                        clearButton.setVisibility(View.GONE);
                    } else {
                        clearButton.setVisibility(View.VISIBLE);
                    }
                    return query;
                })
                .debounce(DEBOUNCE_WAIT, TimeUnit.MILLISECONDS, Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @OnClick(R.id.clear_button)
    void onClearButtonClick() {
        queryInput.setText("");
    }
}
