package rejasupotaro.mds.view.components;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rejasupotaro.mds.R;
import rejasupotaro.mds.view.adapters.SuggestionListAdapter;
import rejasupotaro.mds.view.adapters.TextWatcherAdapter;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.AndroidSubscriptions;
import rx.android.internal.Assertions;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.view.ViewObservable;
import rx.schedulers.Schedulers;

public class SearchView extends FrameLayout implements Observable.OnSubscribe<String> {

    private static final int DEBOUNCE_WAIT = 300;

    @InjectView(R.id.query_input)
    EditText queryInput;
    @InjectView(R.id.suggestion_list)
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
        ButterKnife.inject(this);

        suggestionListAdapter = new SuggestionListAdapter(getContext());
        suggestionListView.setAdapter(suggestionListAdapter);

        ViewObservable.bindView(this, observe())
                .subscribe(this::updateSuggestions);
    }

    private void updateSuggestions(String query) {
        suggestionListAdapter.append(query);
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

        final Subscription subscription = AndroidSubscriptions.unsubscribeInUiThread(() -> queryInput.removeTextChangedListener(listener));

        queryInput.addTextChangedListener(listener);
        subscriber.add(subscription);
    }

    public Observable<String> observe() {
        return Observable.create(this)
                .debounce(DEBOUNCE_WAIT, TimeUnit.MILLISECONDS, Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
