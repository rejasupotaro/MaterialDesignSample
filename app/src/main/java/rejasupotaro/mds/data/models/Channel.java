package rejasupotaro.mds.data.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
public abstract class Channel extends Model {
    @JsonProperty("name")
    public abstract String name();

    @JsonProperty("recipes")
    public abstract List<Recipe> recipes();

    @JsonCreator
    public static Channel create(@JsonProperty("name") String name,
                                 @JsonProperty("recipes") List<Recipe> recipes) {
        return new AutoValue_Channel(name, recipes);

    }

    public static Channel dummy() {
        return new AutoValue_Channel("Channel", Recipe.dummies());
    }
}
