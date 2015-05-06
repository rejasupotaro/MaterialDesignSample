package rejasupotaro.mds.data.model;

import com.google.auto.value.AutoValue;

import java.util.List;

import rejasupotaro.mds.data.AutoGson;

@AutoValue
@AutoGson
public abstract class Channel extends Model {
    public abstract String name();

    public abstract List<Recipe> recipes();

    public static Channel dummy() {
        return new AutoValue_Channel("Channel", Recipe.dummies());
    }
}
