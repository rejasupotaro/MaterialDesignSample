package rejasupotaro.mds.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rejasupotaro.mds.R;
import rejasupotaro.mds.data.services.SuggestionService;

public class SuggestionListAdapter extends BindableAdapter<String> {

    private SuggestionService suggestionService = new SuggestionService();

    static class ViewHolder {
        @InjectView(R.id.suggestion_text)
        TextView suggestionText;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    public SuggestionListAdapter(Context context) {
        super(context);
    }

    public void append(String query) {
        clear();
        addAll(suggestionService.get(query));
        notifyDataSetChanged();
    }

    @Override
    public View newView(LayoutInflater inflater, int position, ViewGroup container) {
        View view = inflater.inflate(R.layout.list_item_suggestion, null, false);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(String item, int position, View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.suggestionText.setText(item);
    }

}
