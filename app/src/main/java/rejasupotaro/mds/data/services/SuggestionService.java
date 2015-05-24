package rejasupotaro.mds.data.services;

import android.text.TextUtils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;

public class SuggestionService {
    private static final List<String> DUMMY = new ArrayList<String>() {{
        add("apple");
        add("applesource");
        add("quick and easy dinner");
        add("asian");
        add("apple cake");
        add("apple crisp");
        add("avocado");
        add("ground beef");
        add("banana cake");
        add("bread");
        add("breakfast");
        add("beef");
        add("bbq sauce");
    }};

    public Observable<List<String>> get(String query) {
        if (TextUtils.isEmpty(query)) {
            return Observable.just(new ArrayList<>());
        }

        List<String> suggestions = new ArrayList<>();
        int count = new SecureRandom().nextInt(DUMMY.size() - 1) + 1;
        for (int i = 0; i < count; i++) {
            suggestions.add(DUMMY.get(new SecureRandom().nextInt(DUMMY.size())));
        }
        return Observable.just(suggestions);
    }
}
