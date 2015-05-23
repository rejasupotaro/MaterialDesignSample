package rejasupotaro.mds.view.adapters;

import android.text.Editable;
import android.text.TextWatcher;

public abstract class TextWatcherAdapter implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    public abstract void onTextChanged(String text, int length);

    @Override
    public final void afterTextChanged(Editable editable) {
        String text = editable.toString();
        int length = text.length();
        onTextChanged(text, length);
    }
}