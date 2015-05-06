package rejasupotaro.mds.data.model;

import com.google.auto.value.AutoValue;

import java.util.ArrayList;
import java.util.List;

import rejasupotaro.mds.data.AutoGson;

@AutoValue @AutoGson
public abstract class Channel {
    public abstract List<Recipe> recipes();

    public static Channel dummy() {
        return new AutoValue_Channel(new ArrayList<Recipe>() {{
            add(Recipe.dummy());
            add(Recipe.dummy());
            add(Recipe.dummy());
            add(Recipe.dummy());
            add(Recipe.dummy());
            add(Recipe.dummy());
            add(Recipe.dummy());
            add(Recipe.dummy());
            add(Recipe.dummy());
            add(Recipe.dummy());
        }});
    }
}
